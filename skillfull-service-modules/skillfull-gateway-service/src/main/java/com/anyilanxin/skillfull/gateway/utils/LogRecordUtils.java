// +----------------------------------------------------------------------
// | SkillFull快速开发平台 [ SkillFull ]
// +----------------------------------------------------------------------
// | 版权所有 2020~2021 zxiaozhou
// +----------------------------------------------------------------------
// | 官方网站: https://www.divisu.com
// +----------------------------------------------------------------------
// | 作者: zxiaozhou <z7630853@163.com>
// +----------------------------------------------------------------------
package com.anyilanxin.skillfull.gateway.utils;

import cn.hutool.core.collection.CollUtil;
import com.alibaba.fastjson.JSONObject;
import com.anyilanxin.skillfull.corecommon.base.Result;
import com.anyilanxin.skillfull.corecommon.base.model.stream.GatewayLogModel;
import com.anyilanxin.skillfull.corecommon.base.model.stream.OperateLogModel;
import com.anyilanxin.skillfull.corecommon.constant.BindingStreamConstant;
import com.anyilanxin.skillfull.corecommon.constant.ServiceConstant;
import com.anyilanxin.skillfull.corecommon.utils.CoreCommonUtils;
import com.anyilanxin.skillfull.corewebflux.component.BindingComponent;
import com.anyilanxin.skillfull.corewebflux.config.properfy.CoreWebFluxAppProperty;
import com.anyilanxin.skillfull.corewebflux.utils.ServletUtils;
import com.anyilanxin.skillfull.gateway.core.constant.CommonGatewayConstant;
import com.anyilanxin.skillfull.oauth2common.authinfo.SkillFullUserDetails;
import com.google.common.base.Joiner;
import lombok.RequiredArgsConstructor;
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
import org.springframework.http.server.reactive.ServerHttpRequestDecorator;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.http.server.reactive.ServerHttpResponseDecorator;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserter;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.annotation.PostConstruct;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static com.anyilanxin.skillfull.corecommon.constant.CommonCoreConstant.X_REQUEST_ID;
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
@RequiredArgsConstructor
@Component
public class LogRecordUtils {
    private static final Joiner JOINER = Joiner.on("");
    private static LogRecordUtils utils;
    private final BindingComponent bindingComponent;
    private final CoreWebFluxAppProperty property;


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
        OperateLogModel gatewayLogModel = new OperateLogModel();
        gatewayLogModel.setDataSources(ServiceConstant.GATEWAY_SERVICE);
        gatewayLogModel.setDataSourcesDescribe("来源于" + ServiceConstant.GATEWAY_SERVICE + "服务统一记录的日志");
        // 读取日志信息
        String methodValue = exchange.getRequest().getMethodValue();
        HttpMethod method = exchange.getRequest().getMethod();
        gatewayLogModel.setRequestUrl(exchange.getRequest().getURI().toString());
        List<String> requestIds = exchange.getRequest().getHeaders().get(X_REQUEST_ID);
        if (CollUtil.isNotEmpty(requestIds)) {
            gatewayLogModel.setLogCode(requestIds.get(0));
        }
        Route route = exchange.getAttribute(ServerWebExchangeUtils.GATEWAY_ROUTE_ATTR);
        gatewayLogModel.setRequestStartTime(LocalDateTime.now());
        GatewayLogModel gatewayLogInfo = new GatewayLogModel();
        if (Objects.nonNull(route)) {
            gatewayLogInfo.setTargetServiceName(route.getId());
        }
        URI targetUri = exchange.getRequiredAttribute(GATEWAY_REQUEST_URL_ATTR);
        gatewayLogInfo.setTargetIp(targetUri.getHost());
        gatewayLogInfo.setTargetRequestUrl(targetUri.toString());
        gatewayLogInfo.setTargetPort(targetUri.getPort());
        gatewayLogModel.setLogType(utils.property.getServiceName());
        gatewayLogModel.setLogTypeDescribe(utils.property.getServiceName() + "日志");
        gatewayLogModel.setRequestIp(ServletUtils.getIpAddr(exchange.getRequest()));
        gatewayLogModel.setRequestMethod(methodValue);
        // 用户信息
        Object info = exchange.getAttribute(CommonGatewayConstant.GATEWAY_USER_INFO);
        if (Objects.nonNull(info) && (info instanceof SkillFullUserDetails)) {
            SkillFullUserDetails userDetails = (SkillFullUserDetails) info;
            gatewayLogModel.setUserId(userDetails.getUserId());
            gatewayLogModel.setUserName(userDetails.getRealName());
        }
        // 用户登录信息
//        LoginOnlineInfoModel loginOnlineInfoModel = exchange.getAttribute(CommonGatewayConstant.GATEWAY_USER_ONLINE);
//        if (Objects.nonNull(loginOnlineInfoModel)) {
//            gatewayLogInfo.setLoginEndpoint(loginOnlineInfoModel.getLoginEndpoint());
//            gatewayLogInfo.setLoginCode(loginOnlineInfoModel.getLoginCode());
//            gatewayLogInfo.setLoginEquipment(loginOnlineInfoModel.getLoginEquipment());
//            gatewayLogInfo.setLoginIp(loginOnlineInfoModel.getLoginIp());
//        }
        gatewayLogModel.setLogOtherData(JSONObject.toJSONString(gatewayLogInfo));
        if (HttpMethod.POST.equals(method) || HttpMethod.PUT.equals(method)) {
            MediaType mediaType = exchange.getRequest().getHeaders().getContentType();
            ServerRequest serverRequest = ServerRequest.create(exchange, messageReaders);
            Mono<String> modifiedBody = serverRequest.bodyToMono(String.class)
                    .flatMap(body -> {
                        if (MediaType.APPLICATION_JSON.isCompatibleWith(mediaType)) {
                            gatewayLogModel.setRequestParam(body);
                            return Mono.just(body);
                        }
                        return Mono.empty();
                    });
            BodyInserter<Mono<String>, ReactiveHttpOutputMessage> bodyInserter = BodyInserters.fromPublisher(modifiedBody, String.class);
            HttpHeaders headers = new HttpHeaders();
            headers.putAll(exchange.getRequest().getHeaders());
            CachedBodyOutputMessage outputMessage = new CachedBodyOutputMessage(exchange, headers);
            return bodyInserter.insert(outputMessage, new BodyInserterContext())
                    .then(Mono.defer(() -> {
                        ServerHttpRequestDecorator decorator = new ServerHttpRequestDecorator(exchange.getRequest()) {
                            @Override
                            public Flux<DataBuffer> getBody() {
                                return outputMessage.getBody();
                            }
                        };
                        exchange.getAttributes().put(GATEWAY_LOG_INFO, gatewayLogModel);
                        return chain.filter(exchange.mutate().request(decorator).build());
                    }));
        } else if (HttpMethod.GET.equals(method) || HttpMethod.DELETE.equals(method)) {
            String query = targetUri.getQuery();
            Map<String, String> queryMap = CoreCommonUtils.getQueryMap(query);
            gatewayLogModel.setRequestParam(JSONObject.toJSONString(queryMap));
            exchange.getAttributes().put(GATEWAY_LOG_INFO, gatewayLogModel);
            return chain.filter(exchange);
        } else {
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
                OperateLogModel gatewayLogModel = exchange.getAttribute(GATEWAY_LOG_INFO);
                if (Objects.isNull(gatewayLogModel)) {
                    return super.writeWith(body);
                }
                GatewayLogModel gatewayLogInfo = JSONObject.parseObject(gatewayLogModel.getLogOtherData(), GatewayLogModel.class);
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
                            gatewayLogModel.setRequestEndTime(LocalDateTime.now());
                            Duration duration = Duration.between(gatewayLogModel.getRequestStartTime(), gatewayLogModel.getRequestEndTime());
                            long millis = duration.toMillis();
                            Result<?> result = JSONObject.parseObject(responseData, Result.class);
                            gatewayLogModel.setCostTime(millis);
                            gatewayLogModel.setRequestResult(responseData);
                            gatewayLogModel.setOperateStatus(result.isSuccess() ? 1 : 0);
                            gatewayLogInfo.setRequestAllResult(responseData);
                            gatewayLogInfo.setRequestStatus(result.getCode());
                            gatewayLogInfo.setRequestHttpStatus(originalResponse.getRawStatusCode());
                            if (gatewayLogModel.getOperateStatus() == 0) {
                                gatewayLogModel.setExceptionMessage(result.getMessage());
                            } else {
                                if (Objects.nonNull(result.getData())) {
                                    gatewayLogInfo.setRequestResultData(result.getData().toString());
                                }
                            }
                        }
                        gatewayLogModel.setLogOtherData(JSONObject.toJSONString(gatewayLogInfo));
                        // 保存日志
                        sendLog(gatewayLogModel);
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
     * 保存日志
     *
     * @param gatewayLogModel ${@link OperateLogModel}
     * @author zxiaozhou
     * @date 2021-07-29 14:50
     */
    private static void sendLog(OperateLogModel gatewayLogModel) {
        log.info("------------LogRecordUtils------待保存日志------>sendLog:\n{}", JSONObject.toJSONString(gatewayLogModel));
        // mq提交日志
        utils.bindingComponent.out(BindingStreamConstant.OPERATE_LOG_PROCESS, gatewayLogModel);
    }


    @PostConstruct
    private void init() {
        utils = this;
    }
}
