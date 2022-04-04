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
import indi.zxiaozhou.skillfull.gateway.utils.GatewayCommonUtils;
import indi.zxiaozhou.skillfull.gateway.utils.LogRecordUtils;
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

import java.util.Collections;
import java.util.List;

import static indi.zxiaozhou.skillfull.corecommon.constant.CoreCustomGatewayFilterParamKeyConstant.PARAM_SPECIAL_URL_KEY;
import static org.springframework.cloud.gateway.support.GatewayToStringStyler.filterToStringCreator;

/**
 * 日志记录过滤器(前置)
 *
 * @author zxiaozhou
 * @date 2020-09-11 22:34
 * @since JDK11
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
        public int getOrder() {
            return FilterCustomPreType.LOG_REQUEST.getOrder();
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
