

package com.anyilanxin.anyicloud.gateway.filter.partial.post;

import static com.anyilanxin.anyicloud.corecommon.constant.CoreCommonGatewayConstant.PARAM_ENABLED_KEY;
import static org.springframework.cloud.gateway.support.GatewayToStringStyler.filterToStringCreator;

import com.anyilanxin.anyicloud.gateway.utils.CorsWebUtils;

import java.util.Collections;
import java.util.List;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.cloud.gateway.filter.factory.GatewayFilterFactory;
import org.springframework.core.Ordered;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * 跨域处理
 *
 * @author zxh
 * @date 2021-08-19 09:12
 * @since 1.0.0
 */
@Slf4j
public class CorsWebGatewayFilterFactory extends AbstractGatewayFilterFactory<CorsWebGatewayFilterFactory.Config> {

    public CorsWebGatewayFilterFactory() {
        super(CorsWebGatewayFilterFactory.Config.class);
    }


    @Override
    public List<String> shortcutFieldOrder() {
        return Collections.singletonList(PARAM_ENABLED_KEY);
    }


    @Override
    public GatewayFilter apply(CorsWebGatewayFilterFactory.Config config) {
        CorsWebGatewayFilterFactory.CorsWebGatewayFilter gatewayFilter = new CorsWebGatewayFilterFactory.CorsWebGatewayFilter(config);
        gatewayFilter.setFactory(this);
        return gatewayFilter;
    }

    private static class CorsWebGatewayFilter implements GatewayFilter, Ordered {

        private final CorsWebGatewayFilterFactory.Config config;

        private GatewayFilterFactory<CorsWebGatewayFilterFactory.Config> gatewayFilterFactory;

        public CorsWebGatewayFilter(CorsWebGatewayFilterFactory.Config config) {
            this.config = config;
        }


        @Override
        public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
            log.debug("------------CorsWebGatewayFilter------------>filter:{}", "跨域处理过滤器");
            return CorsWebUtils.corsHandle(chain, exchange);
        }


        @Override
        public int getOrder() {
            return 1;
        }


        @Override
        public String toString() {
            Object obj = (this.gatewayFilterFactory != null) ? this.gatewayFilterFactory : this;
            return filterToStringCreator(obj).append("New content type", " config.getNewContentType()").append("In class", " config.getInClass()").append("Out class", "config.getOutClass()").toString();
        }


        public void setFactory(GatewayFilterFactory<CorsWebGatewayFilterFactory.Config> gatewayFilterFactory) {
            this.gatewayFilterFactory = gatewayFilterFactory;
        }
    }

    public static class Config {
        // 控制是否开启认证
        private boolean enabled;

        public Config() {
        }


        public boolean isEnabled() {
            return enabled;
        }


        public CorsWebGatewayFilterFactory.Config setEnabled(boolean enabled) {
            this.enabled = enabled;
            return this;
        }
    }
}
