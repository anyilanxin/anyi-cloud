// +----------------------------------------------------------------------
// | SkillFull快速开发平台 [ SkillFull ]
// +----------------------------------------------------------------------
// | 版权所有 2020~2021 zxiaozhou
// +----------------------------------------------------------------------
// | 官方网站: https://www.divisu.com
// +----------------------------------------------------------------------
// | 作者: zxiaozhou <z7630853@163.com>
// +----------------------------------------------------------------------
package indi.zxiaozhou.skillfull.gateway.modules.filter.partial.pre;

import com.alibaba.fastjson.JSONObject;
import indi.zxiaozhou.skillfull.corecommon.base.model.stream.router.RouteMetaSpecialUrlModel;
import indi.zxiaozhou.skillfull.gateway.core.constant.typeimpl.FilterCustomPreType;
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

import static indi.zxiaozhou.skillfull.corecommon.constant.CoreCustomGatewayFilterParamKeyConstant.PARAM_SPECIAL_URL_KEY;
import static org.springframework.cloud.gateway.support.GatewayToStringStyler.filterToStringCreator;

/**
 * 验签过滤器
 *
 * @author zxiaozhou
 * @date 2020-09-11 22:34
 * @since JDK11
 */
@Slf4j
public class VerifySignGatewayFilterFactory extends AbstractGatewayFilterFactory<VerifySignGatewayFilterFactory.Config> {

    public VerifySignGatewayFilterFactory() {
        super(Config.class);
    }

    @Override
    public List<String> shortcutFieldOrder() {
        return Collections.singletonList(PARAM_SPECIAL_URL_KEY);
    }


    @Override
    public GatewayFilter apply(Config config) {
        VerifySignGatewayFilter gatewayFilter = new VerifySignGatewayFilter(config);
        gatewayFilter.setFactory(this);
        return gatewayFilter;
    }


    private static class VerifySignGatewayFilter implements GatewayFilter, Ordered {

        private final Config config;

        private GatewayFilterFactory<Config> gatewayFilterFactory;

        public VerifySignGatewayFilter(Config config) {
            this.config = config;
        }

        @Override
        public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
            log.debug("------------VerifySignGatewayFilter------------>filter:{}", "验签过滤器");
            // 继续运行
            return chain.filter(exchange);
        }

        @Override
        public int getOrder() {
            return FilterCustomPreType.VERIFY_SIGN.getOrder();
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
