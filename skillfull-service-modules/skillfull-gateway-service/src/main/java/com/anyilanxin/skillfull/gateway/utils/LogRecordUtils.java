// +----------------------------------------------------------------------
// | SkillFull快速开发平台 [ SkillFull ]
// +----------------------------------------------------------------------
// | 版权所有 2020~2021 zxiaozhou
// +----------------------------------------------------------------------
// | 官方网站: https://skillfull.divisu.com
// +----------------------------------------------------------------------
// | 作者: zxiaozhou <z7630853@163.com>
// +----------------------------------------------------------------------
package com.anyilanxin.skillfull.gateway.utils;

import cn.hutool.core.collection.CollUtil;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.anyilanxin.skillfull.corecommon.constant.CommonCoreConstant;
import com.anyilanxin.skillfull.corecommon.utils.CoreCommonUtils;
import com.anyilanxin.skillfull.corewebflux.utils.ServletUtils;
import com.anyilanxin.skillfull.gateway.core.constant.CommonGatewayConstant;
import com.anyilanxin.skillfull.loggingcommon.model.OperateLogModel;
import com.anyilanxin.skillfull.loggingcommon.utils.LogUtils;
import com.anyilanxin.skillfull.oauth2common.authinfo.SkillFullUserDetails;
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

import java.net.URI;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static com.anyilanxin.skillfull.gateway.core.constant.CommonGatewayConstant.GATEWAY_LOG_INFO;
import static org.springframework.cloud.gateway.support.ServerWebExchangeUtils.GATEWAY_REQUEST_URL_ATTR;

/**
 * 鉴权工具
 *
 * @author zxiaozhou
 * @date 2021-07-28 22:44
 * @since JDK1.8
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
     * @author zxiaozhou
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
            Mono<String> modifiedBody = serverRequest.bodyToMono(String.class)
                    .flatMap(body -> {
                        operateLogModel.setRequestParam(body);
                        return Mono.just(body);
                    });
            BodyInserter<Mono<String>, ReactiveHttpOutputMessage> bodyInserter = BodyInserters.fromPublisher(modifiedBody, String.class);
            HttpHeaders headers = new HttpHeaders();
            headers.putAll(request.getHeaders());
            CachedBodyOutputMessage outputMessage = new CachedBodyOutputMessage(exchange, headers);
            return bodyInserter.insert(outputMessage, new BodyInserterContext())
                    .then(Mono.defer(() -> {
                        ServerHttpRequestDecorator decorator = new ServerHttpRequestDecorator(request) {
                            @Override
                            public Flux<DataBuffer> getBody() {
                                return outputMessage.getBody();
                            }
                        };
                        exchange.getAttributes().put(GATEWAY_LOG_INFO, operateLogModel);
                        return chain.filter(exchange.mutate().request(decorator).build());
                    }));
        } else if (HttpMethod.GET.equals(method) || HttpMethod.DELETE.equals(method)) {
            String query = targetUri.getQuery();
            Map<String, String> queryMap = CoreCommonUtils.getQueryMap(query);
            operateLogModel.setRequestParam(JSONObject.toJSONString(queryMap, SerializerFeature.WriteMapNullValue));
            exchange.getAttributes().put(GATEWAY_LOG_INFO, operateLogModel);
            return chain.filter(exchange);
        } else {
            exchange.getAttributes().put(GATEWAY_LOG_INFO, operateLogModel);
            return chain.filter(exchange);
        }
    }


    /**
     * 获取响应数据
     *
     * @param exchange ${@link ServerWebExchange}
     * @return ServerHttpResponseDecorator ${@link ServerHttpResponseDecorator}
     * @author zxiaozhou
     * @date 2021-07-14 22:22
     */
    public static ServerHttpResponseDecorator getResponseInfo(ServerWebExchange exchange) {
        ServerHttpResponse originalResponse = exchange.getResponse();
        DataBufferFactory bufferFactory = originalResponse.bufferFactory();
        return new ServerHttpResponseDecorator(originalResponse) {
            @Override
            public Mono<Void> writeWith(Publisher<? extends DataBuffer> body) {
                OperateLogModel operateLogModel = exchange.getAttributeOrDefault(GATEWAY_LOG_INFO, new OperateLogModel());
                // 获取ContentType，判断是否返回JSON格式数据
                if (body instanceof Flux) {
                    Flux<? extends DataBuffer> fluxBody = Flux.from(body);
                    //解决返回体分段传输
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
     * @author zxiaozhou
     * @date 2022-07-22 10:17
     */
    public static boolean isData(ServerHttpRequest request) {
        MediaType contentType = request.getHeaders().getContentType();
        if (Objects.isNull(contentType) ||
                contentType == MediaType.APPLICATION_JSON ||
                contentType == MediaType.APPLICATION_FORM_URLENCODED ||
                contentType == MediaType.APPLICATION_XML ||
                contentType == MediaType.TEXT_PLAIN ||
                contentType == MediaType.TEXT_XML ||
                contentType == MediaType.TEXT_HTML) {
            return true;
        }
        return false;
    }
}
