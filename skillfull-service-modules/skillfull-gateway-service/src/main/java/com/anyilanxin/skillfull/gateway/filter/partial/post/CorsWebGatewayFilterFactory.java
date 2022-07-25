// +----------------------------------------------------------------------
// | SkillFull快速开发平台 [ SkillFull ]
// +----------------------------------------------------------------------
// | 版权所有 2020~2021 zxiaozhou
// +----------------------------------------------------------------------
// | 官方网站: https://www.divisu.com
// +----------------------------------------------------------------------
// | 作者: zxiaozhou <z7630853@163.com>
// +----------------------------------------------------------------------
package com.anyilanxin.skillfull.gateway.filter.partial.post;

import com.anyilanxin.skillfull.gateway.utils.CorsWebUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.cloud.gateway.filter.factory.GatewayFilterFactory;
import org.springframework.core.Ordered;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Collections;
import java.util.List;

import static com.anyilanxin.skillfull.corecommon.constant.CoreCommonGatewayConstant.PARAM_ENABLED_KEY;
import static org.springframework.cloud.gateway.support.GatewayToStringStyler.filterToStringCreator;

/**
 * 跨域处理
 *
 * @author zxiaozhou
 * @date 2021-08-19 09:12
 * @since JDK1.8
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
            return filterToStringCreator(obj)
                    .append("New content type", " config.getNewContentType()")
                    .append("In class", " config.getInClass()")
                    .append("Out class", "config.getOutClass()").toString();
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
