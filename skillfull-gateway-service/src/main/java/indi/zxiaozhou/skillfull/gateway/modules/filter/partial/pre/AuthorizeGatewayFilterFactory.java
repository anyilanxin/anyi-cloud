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
import com.alibaba.fastjson.TypeReference;
import indi.zxiaozhou.skillfull.corecommon.base.model.common.ActionModel;
import indi.zxiaozhou.skillfull.corecommon.base.model.stream.router.RouteMetaSpecialUrlModel;
import indi.zxiaozhou.skillfull.corecommon.constant.CoreCustomGatewayFilterParamKeyConstant;
import indi.zxiaozhou.skillfull.gateway.core.constant.typeimpl.FilterCustomPreType;
import indi.zxiaozhou.skillfull.gateway.modules.authcheck.model.AuthorizeCheckResult;
import indi.zxiaozhou.skillfull.gateway.utils.AuthorizeUtils;
import indi.zxiaozhou.skillfull.gateway.utils.GatewayCommonUtils;
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

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static indi.zxiaozhou.skillfull.corecommon.constant.CoreCustomGatewayFilterParamKeyConstant.PARAM_ACTION_KEY;
import static indi.zxiaozhou.skillfull.corecommon.constant.CoreCustomGatewayFilterParamKeyConstant.PARAM_SPECIAL_URL_KEY;
import static org.springframework.cloud.gateway.support.GatewayToStringStyler.filterToStringCreator;

/**
 * 鉴权过滤器
 *
 * @author zxiaozhou
 * @date 2020-09-11 22:34
 * @since JDK11
 */
@Slf4j
public class AuthorizeGatewayFilterFactory extends AbstractGatewayFilterFactory<AuthorizeGatewayFilterFactory.Config> {


    public AuthorizeGatewayFilterFactory() {
        super(AuthorizeGatewayFilterFactory.Config.class);
    }

    @Override
    public List<String> shortcutFieldOrder() {
        return Arrays.asList(CoreCustomGatewayFilterParamKeyConstant.PARAM_TYPE_KEY, PARAM_SPECIAL_URL_KEY, PARAM_ACTION_KEY);
    }


    @Override
    public GatewayFilter apply(Config config) {
        AuthorizeGatewayFilter gatewayFilter = new AuthorizeGatewayFilter(config);
        gatewayFilter.setFactory(this);
        return gatewayFilter;
    }


    private static class AuthorizeGatewayFilter implements GatewayFilter, Ordered {

        private final Config config;

        private GatewayFilterFactory<Config> gatewayFilterFactory;

        public AuthorizeGatewayFilter(Config config) {
            this.config = config;
        }

        @Override
        public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
            log.debug("------------AuthorizeGatewayFilter------------>filter:{}", "鉴权过滤器");
            // 检测权限
            AuthorizeCheckResult authorizeCheckResult = AuthorizeUtils.checkAuth(config, exchange);
            if (!authorizeCheckResult.isResult()) {
                return GatewayCommonUtils.responseWrite(exchange, authorizeCheckResult.getErrorCode().getStatus(), authorizeCheckResult.getErrorCode(), authorizeCheckResult.getErrorMsg(), null);
            }
            // 继续运行
            return chain.filter(exchange);
        }

        @Override
        public int getOrder() {
            return FilterCustomPreType.AUTHORIZE.getOrder();
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
         * 检测类型:1-登录,2-按钮与登录(当为2时按钮与登录都要检测),默认1
         */
        private int type = 2;

        /**
         * 特殊url
         */
        private RouteMetaSpecialUrlModel specialUrl;

        /**
         * 按钮权限map<path,set<ActionModel>>
         */
        private Map<String, Set<ActionModel>> actions;

        public int getType() {
            return type;
        }

        public Config setType(int type) {
            this.type = type;
            return this;
        }

        public RouteMetaSpecialUrlModel getSpecialUrl() {
            return specialUrl;
        }

        public Config setSpecialUrl(String specialUrl) {
            if (StringUtils.isNotBlank(specialUrl)) {
                this.specialUrl = JSONObject.parseObject(specialUrl, RouteMetaSpecialUrlModel.class);
            }
            return this;
        }

        public Map<String, Set<ActionModel>> getActions() {
            return actions;
        }

        public Config setActions(String actions) {
            if (StringUtils.isNotBlank(actions)) {
                this.actions = JSONObject.parseObject(actions, new TypeReference<Map<String, Set<ActionModel>>>() {
                });
            }
            return this;
        }
    }
}
