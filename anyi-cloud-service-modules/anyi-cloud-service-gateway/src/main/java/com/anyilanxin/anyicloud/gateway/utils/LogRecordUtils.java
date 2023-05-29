/*
 * Copyright (c) 2021-present ZHOUXUANHONG(安一老厨)<anyilanxin@aliyun.com>
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
 *   1.请不要删除和修改根目录下的LICENSE文件；
 *   2.请不要删除和修改 AnYi Cloud 源码头部的版权声明；
 *   3.请保留源码和相关描述文件的项目出处，作者声明等；
 *   4.分发源码时候，请注明软件出处 https://github.com/anyilanxin/anyi-cloud；
 *   5.在修改包名，模块名称，项目代码等时，请注明软件出处 https://github.com/anyilanxin/anyi-cloud；
 *   6.本软件不允许在国家法律规定范围外使用，如出现违法行为原作者本人不承担任何法律风险；
 *   7.进行商用时，不得基于AnYi Cloud的基础，修改包装而成一个与AnYi Zeebe EE、AnYi Cloud EE功能类似的程序，进行销售或发布，参与同类软件产品市场的竞争；
 *   8.本软件使用的第三方依赖皆为开源软件，如需要修改第三方源码请遵循第三方源码附带开源协议；
 *   9.本软件流程部分请遵循camunda开源协议：
 *     https://docs.camunda.org/manual/latest/introduction/third-party-libraries
 *     https://github.com/camunda/camunda-bpm-platform/blob/master/LICENSE
 *   10.若您的项目无法满足以上几点，可申请商业授权。
 */
package com.anyilanxin.anyicloud.gateway.utils;

import static org.springframework.cloud.gateway.support.ServerWebExchangeUtils.GATEWAY_REQUEST_URL_ATTR;

import cn.hutool.core.collection.CollUtil;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.anyilanxin.anyicloud.corecommon.constant.CommonCoreConstant;
import com.anyilanxin.anyicloud.corecommon.utils.CoreCommonUtils;
import com.anyilanxin.anyicloud.corewebflux.utils.ServletUtils;
import com.anyilanxin.anyicloud.gateway.core.constant.CommonGatewayConstant;
import com.anyilanxin.anyicloud.loggingcommon.model.OperateLogModel;
import com.anyilanxin.anyicloud.loggingcommon.utils.LogUtils;
import com.anyilanxin.anyicloud.oauth2common.authinfo.SkillFullUserDetails;
import java.net.URI;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.reactivestreams.Publisher;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.factory.rewrite.CachedBodyOutputMessage;
import org.springframework.cloud.gateway.route.Route;
import org.springframework.cloud.gateway.support.BodyInserterContext;
import org.springframework.cloud.gateway.support.ServerWebExchangeUtils;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.core.io.buffer.DefaultDataBufferFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ReactiveHttpOutputMessage;
import org.springframework.http.codec.HttpMessageReader;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpRequestDecorator;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.http.server.reactive.ServerHttpResponseDecorator;
import org.springframework.web.reactive.function.BodyInserter;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * 鉴权工具
 *
 * @author zxh
 * @date 2021-07-28 22:44
 * @since 1.0.0
 */
@Slf4j
public class LogRecordUtils {

    /**
     * 获取请求数据
     *
     * @param exchange       ${@link ServerWebExchange}
     * @param chain          ${@link GatewayFilterChain}
     * @param messageReaders ${@link List<HttpMessageReader<?>>}
     * @return Mono<Void> ${@link Mono<Void>}
     * @author zxh
     * @date 2021-07-29 14:54
     */
    public static Mono<Void> getRequestInfo(ServerWebExchange exchange, GatewayFilterChain chain, List<HttpMessageReader<?>> messageReaders) {
        OperateLogModel operateLogModel = new OperateLogModel();
        ServerHttpRequest request = exchange.getRequest();
        // 读取日志信息
        String methodValue = request.getMethodValue();
        HttpMethod method = request.getMethod();
        LinkedHashSet<URI> sourceUri = exchange.getRequiredAttribute(ServerWebExchangeUtils.GATEWAY_ORIGINAL_REQUEST_URL_ATTR);
        operateLogModel.setRequestUrl(URLDecoder.decode(sourceUri.toArray(new URI[]{})[0].toString(), StandardCharsets.UTF_8));
        List<String> requestIds = request.getHeaders().get(CommonCoreConstant.X_REQUEST_ID);
        if (CollUtil.isNotEmpty(requestIds)) {
            operateLogModel.setLogCode(requestIds.get(0));
        }
        Route route = exchange.getAttribute(ServerWebExchangeUtils.GATEWAY_ROUTE_ATTR);
        operateLogModel.setRequestStartTime(LocalDateTime.now());
        if (Objects.nonNull(route)) {
            operateLogModel.setTargetServiceCode(route.getId().split(":")[0]);
        }
        URI targetUri = exchange.getRequiredAttribute(GATEWAY_REQUEST_URL_ATTR);
        operateLogModel.setTargetUrl(URLDecoder.decode(targetUri.toString(), StandardCharsets.UTF_8));
        operateLogModel.setRequestIp(ServletUtils.getIpAddr(request));
        operateLogModel.setRequestMethod(methodValue);
        // 用户登录信息
        SkillFullUserDetails userDetails = exchange.getAttribute(CommonGatewayConstant.GATEWAY_USER_INFO);
        if (Objects.nonNull(userDetails)) {
            operateLogModel.setUserId(userDetails.getUserId());
            operateLogModel.setUserName(userDetails.getUsername());
        }
        // 客户端信息
        MediaType mediaType = request.getHeaders().getContentType();
        if (Objects.nonNull(mediaType)) {
            operateLogModel.setContentType(mediaType.getType());
        } else {
            operateLogModel.setContentType(MediaType.APPLICATION_JSON_VALUE);
        }
        if ((HttpMethod.POST.equals(method) || HttpMethod.PUT.equals(method)) && isData(request)) {
            ServerRequest serverRequest = ServerRequest.create(exchange, messageReaders);
            Mono<String> modifiedBody = serverRequest.bodyToMono(String.class).flatMap(body -> {
                operateLogModel.setRequestParam(body);
                return Mono.just(body);
            });
            BodyInserter<Mono<String>, ReactiveHttpOutputMessage> bodyInserter = BodyInserters.fromPublisher(modifiedBody, String.class);
            HttpHeaders headers = new HttpHeaders();
            headers.putAll(request.getHeaders());
            CachedBodyOutputMessage outputMessage = new CachedBodyOutputMessage(exchange, headers);
            return bodyInserter.insert(outputMessage, new BodyInserterContext()).then(Mono.defer(() -> {
                ServerHttpRequestDecorator decorator = new ServerHttpRequestDecorator(request) {
                    @Override
                    public Flux<DataBuffer> getBody() {
                        return outputMessage.getBody();
                    }
                };
                exchange.getAttributes().put(CommonGatewayConstant.GATEWAY_LOG_INFO, operateLogModel);
                return chain.filter(exchange.mutate().request(decorator).build());
            }));
        } else if (HttpMethod.GET.equals(method) || HttpMethod.DELETE.equals(method)) {
            String query = targetUri.getQuery();
            Map<String, String> queryMap = CoreCommonUtils.getQueryMap(query);
            operateLogModel.setRequestParam(JSONObject.toJSONString(queryMap, SerializerFeature.WriteMapNullValue));
            exchange.getAttributes().put(CommonGatewayConstant.GATEWAY_LOG_INFO, operateLogModel);
            return chain.filter(exchange);
        } else {
            exchange.getAttributes().put(CommonGatewayConstant.GATEWAY_LOG_INFO, operateLogModel);
            return chain.filter(exchange);
        }
    }


    /**
     * 获取响应数据
     *
     * @param exchange ${@link ServerWebExchange}
     * @return ServerHttpResponseDecorator ${@link ServerHttpResponseDecorator}
     * @author zxh
     * @date 2021-07-14 22:22
     */
    public static ServerHttpResponseDecorator getResponseInfo(ServerWebExchange exchange) {
        ServerHttpResponse originalResponse = exchange.getResponse();
        DataBufferFactory bufferFactory = originalResponse.bufferFactory();
        return new ServerHttpResponseDecorator(originalResponse) {
            @Override
            public Mono<Void> writeWith(Publisher<? extends DataBuffer> body) {
                OperateLogModel operateLogModel = exchange.getAttributeOrDefault(CommonGatewayConstant.GATEWAY_LOG_INFO, new OperateLogModel());
                // 获取ContentType，判断是否返回JSON格式数据
                if (body instanceof Flux) {
                    Flux<? extends DataBuffer> fluxBody = Flux.from(body);
                    // 解决返回体分段传输
                    return super.writeWith(fluxBody.buffer().map(dataBuffers -> {
                        DataBufferFactory dataBufferFactory = new DefaultDataBufferFactory();
                        DataBuffer join = dataBufferFactory.join(dataBuffers);
                        byte[] content = new byte[join.readableByteCount()];
                        join.read(content);
                        DataBufferUtils.release(join);
                        String responseData = new String(content, StandardCharsets.UTF_8);
                        if (StringUtils.isNotBlank(responseData)) {
                            operateLogModel.setRequestEndTime(LocalDateTime.now());
                            operateLogModel.setRequestResult(responseData);
                            if (Objects.nonNull(originalResponse.getRawStatusCode())) {
                                operateLogModel.setOperateStatus(originalResponse.getRawStatusCode() == 200 ? 1 : 0);
                            } else {
                                operateLogModel.setOperateStatus(0);
                            }
                        }
                        // 保存日志
                        LogUtils.sendOperateLog(operateLogModel);
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
     * 判断是否为数据而不是流
     *
     * @param request
     * @author zxh
     * @date 2022-07-22 10:17
     */
    public static boolean isData(ServerHttpRequest request) {
        MediaType contentType = request.getHeaders().getContentType();
        if (Objects.isNull(contentType) || contentType == MediaType.APPLICATION_JSON || contentType == MediaType.APPLICATION_FORM_URLENCODED || contentType == MediaType.APPLICATION_XML || contentType == MediaType.TEXT_PLAIN || contentType == MediaType.TEXT_XML || contentType == MediaType.TEXT_HTML) {
            return true;
        }
        return false;
    }
}
