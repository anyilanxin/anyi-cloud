

package com.anyilanxin.anyicloud.gateway.filter.partial.pre;

import static com.anyilanxin.anyicloud.corecommon.constant.CoreCommonGatewayConstant.PARAM_SPECIAL_URL_KEY;
import static org.springframework.cloud.gateway.filter.ReactiveLoadBalancerClientFilter.LOAD_BALANCER_CLIENT_FILTER_ORDER;
import static org.springframework.cloud.gateway.support.GatewayToStringStyler.filterToStringCreator;

import com.alibaba.fastjson.JSONObject;
import com.anyilanxin.anyicloud.corecommon.model.stream.router.RouteMetaSpecialUrlModel;
import com.anyilanxin.anyicloud.gateway.utils.GatewayCommonUtils;
import com.anyilanxin.anyicloud.gateway.utils.LogRecordUtils;

import java.util.Collections;
import java.util.List;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.cloud.gateway.filter.factory.GatewayFilterFactory;
import org.springframework.core.Ordered;
import org.springframework.http.codec.HttpMessageReader;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.reactive.function.server.HandlerStrategies;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * 日志记录过滤器(前置)
 *
 * @author zxh
 * @date 2020-09-11 22:34
 * @since 1.0.0
 */
@Slf4j
public class LogRequestGatewayFilterFactory extends AbstractGatewayFilterFactory<LogRequestGatewayFilterFactory.Config> {

    private final List<HttpMessageReader<?>> messageReaders;

    public LogRequestGatewayFilterFactory() {
        super(Config.class);
        this.messageReaders = HandlerStrategies.withDefaults().messageReaders();
    }


    @Override
    public List<String> shortcutFieldOrder() {
        return Collections.singletonList(PARAM_SPECIAL_URL_KEY);
    }


    @Override
    public GatewayFilter apply(Config config) {
        LogGatewayFilter gatewayFilter = new LogGatewayFilter(config, this.messageReaders);
        gatewayFilter.setFactory(this);
        return gatewayFilter;
    }

    private static class LogGatewayFilter implements GatewayFilter, Ordered {

        private final Config config;

        private final List<HttpMessageReader<?>> messageReaders;

        private GatewayFilterFactory<Config> gatewayFilterFactory;

        public LogGatewayFilter(Config config, List<HttpMessageReader<?>> messageReaders) {
            this.config = config;
            this.messageReaders = messageReaders;
        }


        @Override
        public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
            log.debug("------------LogGatewayFilter------------>filter:{}", "日志记录过滤器");
            // 验证特殊url
            GatewayCommonUtils.CheckUrlInfo haveUrl = GatewayCommonUtils.isHaveUrl(exchange, config.specialUrl);
            // 没有url没有任何类型直接运行获取日志
            if (haveUrl.getSpecialUrlType() == 0) {
                return LogRecordUtils.getRequestInfo(exchange, chain, messageReaders);
            }
            // 如果是白名单并且检测成功，这不获取日志
            else if (haveUrl.getSpecialUrlType() == 1 && haveUrl.isResult()) {
                return chain.filter(exchange);
            }
            // 如果是黑名单，并且成功则运行获取日志
            else if (haveUrl.getSpecialUrlType() == 2 && haveUrl.isResult()) {
                return LogRecordUtils.getRequestInfo(exchange, chain, messageReaders);
            }
            // 其余全部读取日志
            return LogRecordUtils.getRequestInfo(exchange, chain, messageReaders);
        }


        @Override
        /**
         * 必须大于LOAD_BALANCER_CLIENT_FILTER_ORDER(10150)，即负载均衡过滤器(ReactiveLoadBalancerClientFilter)的order,否则拿不到真实目标服务ip(request数据)
         */
        public int getOrder() {
            return LOAD_BALANCER_CLIENT_FILTER_ORDER + 1;
        }


        @Override
        public String toString() {
            Object obj = (this.gatewayFilterFactory != null) ? this.gatewayFilterFactory : this;
            return filterToStringCreator(obj).append("New content type", " config.getNewContentType()").append("In class", " config.getInClass()").append("Out class", "config.getOutClass()").toString();
        }


        public void setFactory(GatewayFilterFactory<Config> gatewayFilterFactory) {
            this.gatewayFilterFactory = gatewayFilterFactory;
        }
    }

    @Validated
    public static class Config {
        /**
         * 特殊url
         */
        private RouteMetaSpecialUrlModel specialUrl;

        public RouteMetaSpecialUrlModel getSpecialUrl() {
            return specialUrl;
        }


        public Config setSpecialUrl(String specialUrl) {
            if (StringUtils.isNotBlank(specialUrl)) {
                this.specialUrl = JSONObject.parseObject(specialUrl, RouteMetaSpecialUrlModel.class);
            }
            return this;
        }
    }
}
