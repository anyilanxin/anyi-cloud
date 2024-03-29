/*
 * Copyright (c) 2023-present ZHOUXUANHONG(安一老厨)<anyilanxin@aliyun.com>
 *
 * AnYi Cloud Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * AnYi Cloud 采用APACHE LICENSE 2.0开源协议，您在使用过程中，需要注意以下几点：
 *   1.请不要删除和修改根目录下的LICENSE.txt文件；
 *   2.请不要删除和修改 AnYi Cloud 源码头部的版权声明；
 *   3.请保留源码和相关描述文件的项目出处，作者声明等；
 *   4.分发源码时候，请注明软件出处 https://github.com/anyilanxin/anyi-cloud；
 *   5.在修改包名，模块名称，项目代码等时，请注明软件出处 https://github.com/anyilanxin/anyi-cloud；
 *   6.本软件不允许在国家法律规定范围外使用，如出现违法行为原作者本人不承担任何法律风险；
 *   7.进行商用时，不得基于AnYi Cloud的基础，修改包装而成一个与AnYi Cloud EE、AnYi Zeebe EE、AnYi Standalone EE功能类似的程序，进行销售或发布，参与同类软件产品市场的竞争；
 *   8.本软件使用的第三方依赖皆为开源软件，如需要修改第三方源码请遵循第三方源码附带开源协议；
 *   9.本软件流程部分请遵循camunda开源协议：
 *     https://docs.camunda.org/manual/latest/introduction/third-party-libraries
 *     https://github.com/camunda/camunda-bpm-platform/blob/master/LICENSE
 *   10.若您的项目无法满足以上几点，可申请商业授权。
 */

package com.anyilanxin.anyicloud.gateway.utils;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.alibaba.fastjson2.TypeReference;
import com.anyilanxin.anyicloud.corecommon.base.AnYiResult;
import com.anyilanxin.anyicloud.corecommon.constant.AnYiResultStatus;
import com.anyilanxin.anyicloud.corecommon.constant.CoreCommonCacheConstant;
import com.anyilanxin.anyicloud.corecommon.model.system.ConfigDataSecurityModel;
import com.anyilanxin.anyicloud.corecommon.model.system.UserDataSecurityModel;
import com.anyilanxin.anyicloud.corecommon.utils.AnYiCoreCommonUtils;
import com.anyilanxin.anyicloud.corecommon.utils.encryption.AESUtils;
import com.anyilanxin.anyicloud.corecommon.utils.encryption.RSAUtils;
import com.anyilanxin.anyicloud.gateway.core.constant.CommonGatewayConstant;
import com.anyilanxin.anyicloud.gateway.filter.mapstruct.EncryptDecryptAndUserSecurityMap;
import com.anyilanxin.anyicloud.gateway.filter.model.EncryptDecryptModel;
import com.google.common.base.Joiner;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.Charsets;
import org.apache.commons.lang3.StringUtils;
import org.dromara.hutool.core.collection.CollUtil;
import org.reactivestreams.Publisher;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.factory.rewrite.CachedBodyOutputMessage;
import org.springframework.cloud.gateway.support.BodyInserterContext;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.core.io.buffer.DefaultDataBufferFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.*;
import org.springframework.http.codec.HttpMessageReader;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpRequestDecorator;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.http.server.reactive.ServerHttpResponseDecorator;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserter;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import static org.springframework.cloud.gateway.support.ServerWebExchangeUtils.GATEWAY_REQUEST_URL_ATTR;

/**
 * 加解密工具
 *
 * @author zxh
 * @date 2021-07-28 22:44
 * @since 1.0.0
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class DecryptEncryptUtils {
    private static final Joiner JOINER = Joiner.on("");
    private static DecryptEncryptUtils utils;
    private final RedisTemplate<String, Object> redisTemplate;
    private final ConfigDataSecurityModel configDataSecurityModel;
    private final EncryptDecryptAndUserSecurityMap userSecurityMap;

    /**
     * 加密操作
     *
     * @param exchange ${@link ServerWebExchange}
     * @return ServerHttpResponseDecorator ${@link ServerHttpResponseDecorator}
     * @author zxh
     * @date 2021-07-14 22:22
     */
    public static ServerHttpResponseDecorator encrypt(ServerWebExchange exchange) {
        ServerHttpResponse originalResponse = exchange.getResponse();
        DataBufferFactory bufferFactory = originalResponse.bufferFactory();
        return new ServerHttpResponseDecorator(originalResponse) {
            @Override
            public Mono<Void> writeWith(Publisher<? extends DataBuffer> body) {
                EncryptDecryptModel encryptDecryptModel = exchange.getAttribute(CommonGatewayConstant.GATEWAY_ENCRYPT_DECRYPT_INFO);
                if (Objects.isNull(encryptDecryptModel) || !encryptDecryptModel.isSuccess() || StringUtils.isBlank(encryptDecryptModel.getSecret())) {
                    return super.writeWith(body);
                }
                // 获取ContentType，判断是否返回JSON格式数据
                if (body instanceof Flux) {
                    Flux<? extends DataBuffer> fluxBody = Flux.from(body);
                    return super.writeWith(fluxBody.buffer().map(dataBuffers -> {// 解决返回体分段传输
                        DataBufferFactory dataBufferFactory = new DefaultDataBufferFactory();
                        DataBuffer join = dataBufferFactory.join(dataBuffers);
                        byte[] content = new byte[join.readableByteCount()];
                        join.read(content);
                        DataBufferUtils.release(join);
                        String responseData = new String(content, Charsets.UTF_8);
                        if (StringUtils.isNotBlank(responseData)) {
                            AnYiResult<String> result = JSONObject.parseObject(responseData, new TypeReference<>() {
                            });
                            if (result.isSuccess() && StringUtils.isNotBlank(result.getData())) {
                                log.info("------------DecryptEncryptUtils------加密前数据------>writeWith:\n{}", result.getData());
                                String data = AESUtils.encrypt(encryptDecryptModel.getSecret(), result.getData());
                                result.setData(data);
                                responseData = JSONObject.toJSONString(result);
                            }
                        }
                        log.debug("------------DecryptEncryptUtils------加密后数据------>writeWith:{}", responseData);
                        // 二次处理（加密/过滤等）如果不需要做二次处理可直接跳过下行
                        byte[] uppedContent = new String(responseData.getBytes(), StandardCharsets.UTF_8).getBytes();
                        originalResponse.getHeaders().setContentLength(uppedContent.length);
                        return bufferFactory.wrap(uppedContent);
                    }));
                }
                return super.writeWith(body);
            }
        };
    }


    /**
     * 解密操作
     *
     * @param exchange       ${@link ServerWebExchange}
     * @param chain          ${@link GatewayFilterChain}
     * @param messageReaders ${@link List<HttpMessageReader<?>>}
     * @return Mono<Void> ${@link Mono<Void>}
     * @author zxh
     * @date 2021-07-29 14:57
     */
    public static Mono<Void> decrypt(ServerWebExchange exchange, GatewayFilterChain chain, List<HttpMessageReader<?>> messageReaders) {
        // 获取请求序列
        EncryptDecryptModel encryptDecryptModel = DecryptEncryptUtils.getEncryptDecryptModel(exchange);
        if (!encryptDecryptModel.isSuccess()) {
            if (Objects.nonNull(encryptDecryptModel.getCode())) {
                return GatewayCommonUtils.responseWrite(exchange, encryptDecryptModel.getCode().getStatus(), encryptDecryptModel.getCode(), encryptDecryptModel.getErrorMsg(), null);
            }
            return GatewayCommonUtils.responseWrite(exchange, HttpStatus.NOT_ACCEPTABLE, AnYiResultStatus.VERIFICATION_FAILED, encryptDecryptModel.getErrorMsg(), null);
        }
        // 开始解密操作
        HttpMethod method = exchange.getRequest().getMethod();
        // get或delete请求参数放query中
        if (HttpMethod.GET.equals(method) || HttpMethod.DELETE.equals(method)) {
            return DecryptEncryptUtils.operationQueryExchange(exchange, chain, encryptDecryptModel);
        }
        // post或put请求参数放body中
        else if (HttpMethod.POST.equals(method) || HttpMethod.PUT.equals(method)) {
            return DecryptEncryptUtils.operationBodyExchange(exchange, chain, encryptDecryptModel, messageReaders);
        }
        // 其余不处理
        else {
            return chain.filter(exchange);
        }
    }


    /**
     * 获取加解密信息(获取密钥并解密密钥)
     *
     * @param exchange ${@link ServerWebExchange}
     * @return EncryptDecryptModel ${@link EncryptDecryptModel}
     * @author zxh
     * @date 2021-07-14 16:01
     */
    private static EncryptDecryptModel getEncryptDecryptModel(ServerWebExchange exchange) {
        String serialNumberKey = utils.configDataSecurityModel.getSerialNumberKey();
        URI uri = exchange.getRequest().getURI();
        String query = uri.getQuery();
        Map<String, String> queryMap = AnYiCoreCommonUtils.getQueryMap(query);
        String serialNumber = queryMap.remove(serialNumberKey);
        if (StringUtils.isBlank(serialNumber)) {
            serialNumber = exchange.getRequest().getHeaders().getFirst(serialNumberKey);
        }
        EncryptDecryptModel decryptModel = new EncryptDecryptModel();
        if (StringUtils.isBlank(serialNumber)) {
            decryptModel.setSuccess(false);
            decryptModel.setErrorMsg("未在请求头或者地址参数中查询到请求序列信息,请调用相关接口获取,或检测是否打开加密");
            return decryptModel;
        }
        Object objectUserDataSecurity = utils.redisTemplate.opsForValue().get(CoreCommonCacheConstant.USER_DATA_SECURITY_CACHE + serialNumber);
        if (Objects.nonNull(objectUserDataSecurity) && objectUserDataSecurity instanceof UserDataSecurityModel userDataSecurityModel) {
            EncryptDecryptModel encryptDecryptModel = utils.userSecurityMap.aToB(userDataSecurityModel);
            encryptDecryptModel.setSuccess(true);
            return encryptDecryptModel;
        } else {
            decryptModel.setSuccess(false);
            decryptModel.setCode(AnYiResultStatus.NEED_REFRESH);
            decryptModel.setErrorMsg("当前请求序列查询到的内容异常,或已经过期。请检查或重试");
            return decryptModel;
        }
    }


    /**
     * 更新加解密密钥
     *
     * @param encryptDecryptModel ${@link EncryptDecryptModel}
     * @author zxh
     * @date 2021-07-15 21:30
     */
    private static void updateRedisEncryptDecrypt(EncryptDecryptModel encryptDecryptModel) {
        if (Objects.nonNull(encryptDecryptModel) && StringUtils.isNotBlank(encryptDecryptModel.getSerialNumber())) {
            String redisKey = CoreCommonCacheConstant.USER_DATA_SECURITY_CACHE + encryptDecryptModel.getSerialNumber();
            Long expire = utils.redisTemplate.getExpire(redisKey);
            Object objectUserDataSecurity = utils.redisTemplate.opsForValue().get(redisKey);
            if (Objects.nonNull(objectUserDataSecurity) && objectUserDataSecurity instanceof UserDataSecurityModel userDataSecurityModel) {
                utils.userSecurityMap.updateBToA(encryptDecryptModel, userDataSecurityModel);
                utils.redisTemplate.delete(redisKey);
                if (Objects.nonNull(expire) && expire > 0) {
                    utils.redisTemplate.opsForValue().set(redisKey, userDataSecurityModel, expire, TimeUnit.SECONDS);
                } else {
                    utils.redisTemplate.opsForValue().set(redisKey, userDataSecurityModel);
                }
            }
        }
    }


    /**
     * 处理post、put请求
     *
     * @param exchange            ${@link ServerWebExchange}
     * @param chain               ${@link GatewayFilterChain}
     * @param encryptDecryptModel ${@link EncryptDecryptModel}
     * @return Mono<Void> ${@link Mono<Void>}
     * @author zxh
     * @date 2021-07-14 16:58
     */
    private static Mono<Void> operationBodyExchange(ServerWebExchange exchange, GatewayFilterChain chain, EncryptDecryptModel encryptDecryptModel, List<HttpMessageReader<?>> messageReaders) {
        // mediaType
        MediaType mediaType = exchange.getRequest().getHeaders().getContentType();
        // read & modify body
        ServerRequest serverRequest = ServerRequest.create(exchange, messageReaders);
        Mono<String> modifiedBody = serverRequest.bodyToMono(String.class).flatMap(body -> {
            if (MediaType.APPLICATION_JSON.isCompatibleWith(mediaType)) {
                JSONObject jsonObject = JSON.parseObject(body);
                String ciphertext = jsonObject.getString(encryptDecryptModel.getCiphertextKey());
                String secret = jsonObject.getString(encryptDecryptModel.getSecretKey());
                if (StringUtils.isNotBlank(secret)) {
                    try {
                        secret = RSAUtils.decryptPrivateKey(encryptDecryptModel.getPrivateKey(), secret);
                        encryptDecryptModel.setSecret(secret);
                        encryptDecryptModel.setSuccess(true);
                        updateRedisEncryptDecrypt(encryptDecryptModel);
                    } catch (Exception e) {
                        log.error("------------DecryptEncryptUtils------------>operationBodyExchange--->\n参数:{},异常消息:{}", encryptDecryptModel, e.getMessage());
                        encryptDecryptModel.setSuccess(false);
                        encryptDecryptModel.setErrorMsg("请求关键信息不匹配:" + e.getMessage());
                    }
                }
                if (encryptDecryptModel.isSuccess()) {
                    if (StringUtils.isBlank(encryptDecryptModel.getSecret())) {
                        String msg = encryptDecryptModel.getErrorMsg();
                        encryptDecryptModel.setSuccess(false);
                        encryptDecryptModel.setErrorMsg(StringUtils.isNotBlank(msg) ? msg : "数据缺少关键信息");
                    }
                    if (!jsonObject.isEmpty() && encryptDecryptModel.isSuccess()) {
                        if (StringUtils.isNotBlank(ciphertext)) {
                            String decrypt = AESUtils.decrypt(encryptDecryptModel.getSecret(), ciphertext);
                            log.debug("------------DecryptEncryptUtils------解密后数据------>operationBodyExchange:{}", decrypt);
                            jsonObject = JSON.parseObject(decrypt);
                            encryptDecryptModel.setSuccess(true);
                        } else {
                            encryptDecryptModel.setSuccess(false);
                            encryptDecryptModel.setErrorMsg("请求数据封装异常,当前需要安全处理");
                        }
                    }
                }
                return Mono.just(jsonObject.toJSONString());
            }
            return Mono.empty();
        });
        BodyInserter<Mono<String>, ReactiveHttpOutputMessage> bodyInserter = BodyInserters.fromPublisher(modifiedBody, String.class);
        HttpHeaders headers = new HttpHeaders();
        headers.putAll(exchange.getRequest().getHeaders());
        headers.remove(HttpHeaders.CONTENT_LENGTH);
        CachedBodyOutputMessage outputMessage = new CachedBodyOutputMessage(exchange, headers);
        exchange.getAttributes().put(CommonGatewayConstant.GATEWAY_ENCRYPT_DECRYPT_INFO, encryptDecryptModel);
        return bodyInserter.insert(outputMessage, new BodyInserterContext()).then(Mono.defer(() -> {
            ServerHttpRequestDecorator decorator = new ServerHttpRequestDecorator(exchange.getRequest()) {
                @Override
                public HttpHeaders getHeaders() {
                    long contentLength = headers.getContentLength();
                    HttpHeaders httpHeaders = new HttpHeaders();
                    httpHeaders.putAll(super.getHeaders());
                    if (contentLength > 0) {
                        httpHeaders.setContentLength(contentLength);
                    } else {
                        httpHeaders.set(HttpHeaders.TRANSFER_ENCODING, "chunked");
                    }
                    return httpHeaders;
                }


                @Override
                public Flux<DataBuffer> getBody() {
                    return outputMessage.getBody();
                }
            };
            if (!encryptDecryptModel.isSuccess()) {
                String msg = encryptDecryptModel.getErrorMsg();
                return GatewayCommonUtils.responseWrite(exchange, HttpStatus.NOT_ACCEPTABLE, AnYiResultStatus.VERIFICATION_FAILED, StringUtils.isNotBlank(msg) ? msg : "数据缺少关键信息", null);
            }
            return chain.filter(exchange.mutate().request(decorator).build());
        }));
    }


    /**
     * 处理get、delete请求
     *
     * @param exchange            ${@link ServerWebExchange}
     * @param chain               ${@link GatewayFilterChain}
     * @param encryptDecryptModel ${@link EncryptDecryptModel}
     * @return Mono<Void> ${@link Mono<Void>}
     * @author zxh
     * @date 2021-07-14 16:58
     */
    private static Mono<Void> operationQueryExchange(ServerWebExchange exchange, GatewayFilterChain chain, EncryptDecryptModel encryptDecryptModel) {
        URI uri = exchange.getRequiredAttribute(GATEWAY_REQUEST_URL_ATTR);
        String query = uri.getQuery();
        Map<String, String> queryMap = AnYiCoreCommonUtils.getQueryMap(query);
        String ciphertext = queryMap.remove(encryptDecryptModel.getCiphertextKey());
        String secret = queryMap.remove(encryptDecryptModel.getSecretKey());
        if (StringUtils.isNotBlank(secret)) {
            try {
                secret = RSAUtils.decryptPrivateKey(encryptDecryptModel.getPrivateKey(), secret);
                encryptDecryptModel.setSecret(secret);
                DecryptEncryptUtils.updateRedisEncryptDecrypt(encryptDecryptModel);
            } catch (Exception e) {
                log.error("------------DecryptEncryptUtils------------>operationQueryExchange--->\n参数:{},异常消息:{}", encryptDecryptModel, e.getMessage());
                return GatewayCommonUtils.responseWrite(exchange, HttpStatus.NOT_ACCEPTABLE, AnYiResultStatus.VERIFICATION_FAILED, "请求关键信息不匹配:" + e.getMessage(), null);
            }
        }
        if (StringUtils.isBlank(encryptDecryptModel.getSecret())) {
            String msg = encryptDecryptModel.getErrorMsg();
            return GatewayCommonUtils.responseWrite(exchange, HttpStatus.NOT_ACCEPTABLE, AnYiResultStatus.VERIFICATION_FAILED, StringUtils.isNotBlank(msg) ? msg : "数据缺少关键信息", null);
        }
        if (StringUtils.isNotBlank(ciphertext)) {
            String decrypt = AESUtils.decrypt(encryptDecryptModel.getSecret(), ciphertext);
            log.debug("------------DecryptEncryptUtils------解密后数据------>operationQueryExchange:{}", decrypt);
            JSONObject jsonObject = JSONObject.parseObject(decrypt);
            // 解密出来是一个json,解密后需要json key作为get请求key
            jsonObject.forEach((k, v) -> queryMap.put(k, v.toString()));
        } else {
            if (CollUtil.isNotEmpty(queryMap)) {
                if (!(StringUtils.isNotBlank(encryptDecryptModel.getQueryOtherKey()) && queryMap.size() == 1 && queryMap.containsKey(encryptDecryptModel.getQueryOtherKey()))) {
                    return GatewayCommonUtils.responseWrite(exchange, HttpStatus.NOT_ACCEPTABLE, AnYiResultStatus.VERIFICATION_FAILED, "请求数据封装异常,当前需要安全处理", null);
                }
            }
        }
        String newQueryParam = AnYiCoreCommonUtils.queryToString(queryMap);
        URI newUri = UriComponentsBuilder.fromUri(uri).replaceQuery(newQueryParam).build(true).toUri();
        exchange.getAttributes().put(GATEWAY_REQUEST_URL_ATTR, newUri);
        exchange.getAttributes().put(CommonGatewayConstant.GATEWAY_ENCRYPT_DECRYPT_INFO, encryptDecryptModel);
        ServerHttpRequest updatedRequest = exchange.getRequest().mutate().uri(newUri).build();
        return chain.filter(exchange.mutate().request(updatedRequest).build());
    }


    @PostConstruct
    void init() {
        utils = this;
    }
}
