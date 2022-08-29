// +----------------------------------------------------------------------
// | SkillFull快速开发平台 [ SkillFull ]
// +----------------------------------------------------------------------
// | 版权所有 2020~2021 zxiaozhou
// +----------------------------------------------------------------------
// | 官方网站: https://skillfull.divisu.com
// +----------------------------------------------------------------------
// | 作者: zxiaozhou <z7630853@163.com>
// +----------------------------------------------------------------------
package com.anyilanxin.skillfull.gateway.filter.partial.post;

import com.alibaba.fastjson.JSONObject;
import com.anyilanxin.skillfull.corecommon.model.stream.router.RouteMetaSpecialUrlModel;
import com.anyilanxin.skillfull.gateway.utils.LogRecordUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.cloud.gateway.filter.factory.GatewayFilterFactory;
import org.springframework.core.Ordered;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Collections;
import java.util.List;

import static com.anyilanxin.skillfull.corecommon.constant.CoreCommonGatewayConstant.PARAM_SPECIAL_URL_KEY;
import static org.springframework.cloud.gateway.support.GatewayToStringStyler.filterToStringCreator;

/**
 * 日志记录过滤器(后置)
 *
 * @author zxiaozhou
 * @date 2020-09-11 22:34
 * @since JDK11
 */
@Slf4j
public class LogResponseGatewayFilterFactory extends AbstractGatewayFilterFactory<LogResponseGatewayFilterFactory.Config> {
    public LogResponseGatewayFilterFactory() {
        super(Config.class);
    }

    @Override
    public List<String> shortcutFieldOrder() {
        return Collections.singletonList(PARAM_SPECIAL_URL_KEY);
    }


    @Override
    public GatewayFilter apply(Config config) {
        LogGatewayFilter gatewayFilter = new LogGatewayFilter(config);
        gatewayFilter.setFactory(this);
        return gatewayFilter;
    }


    private static class LogGatewayFilter implements GatewayFilter, Ordered {

        private final Config config;

        private GatewayFilterFactory<Config> gatewayFilterFactory;

        public LogGatewayFilter(Config config) {
            this.config = config;
        }

        @Override
        public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
            log.debug("------------LogGatewayFilter------------>filter:{}", "响应数据处理过滤器");
            return chain.filter(exchange.mutate().response(LogRecordUtils.getResponseInfo(exchange)).build());
        }

        @Override
        public int getOrder() {
            return -2;
        }

        @Override
        public String toString() {
            Object obj = (this.gatewayFilterFactory != null) ? this.gatewayFilterFactory : this;
            return filterToStringCreator(obj)
                    .append("New content type", " config.getNewContentType()")
                    .append("In class", " config.getInClass()")
                    .append("Out class", "config.getOutClass()").toString();
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
