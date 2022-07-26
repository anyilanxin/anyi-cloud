// +----------------------------------------------------------------------
// | SkillFull快速开发平台 [ SkillFull ]
// +----------------------------------------------------------------------
// | 版权所有 2020~2021 zxiaozhou
// +----------------------------------------------------------------------
// | 官方网站: https://www.divisu.com
// +----------------------------------------------------------------------
// | 作者: zxiaozhou <z7630853@163.com>
// +----------------------------------------------------------------------
package com.anyilanxin.skillfull.gateway.filter.partial.pre;

import cn.hutool.core.collection.CollUtil;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.anyilanxin.skillfull.corecommon.constant.CoreCommonGatewayConstant;
import com.anyilanxin.skillfull.gateway.core.config.properties.CustomSecurityProperties;
import com.anyilanxin.skillfull.gateway.filter.partial.pre.checkauth.AccessDecisionManager;
import com.anyilanxin.skillfull.gateway.filter.partial.pre.checkauth.AccessDecisionVoter;
import com.anyilanxin.skillfull.gateway.filter.partial.pre.checkauth.ConfigAttribute;
import com.anyilanxin.skillfull.gateway.filter.partial.pre.checkauth.matcher.RequestMatcher;
import com.anyilanxin.skillfull.gateway.utils.AuthorizeUtils;
import com.anyilanxin.skillfull.gateway.utils.Oauth2CommonUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.cloud.gateway.filter.factory.GatewayFilterFactory;
import org.springframework.core.Ordered;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.*;

import static com.anyilanxin.skillfull.corecommon.constant.CoreCommonGatewayConstant.ATTRIBUTES_KEY;
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
    private final Map<Integer, AccessDecisionManager> accessDecisionManager = new HashMap<>(3);
    private final CustomSecurityProperties securityProperties;

    public AuthorizeGatewayFilterFactory(final List<AccessDecisionVoter<?>> decisionVoters, CustomSecurityProperties securityProperties) {
        super(AuthorizeGatewayFilterFactory.Config.class);
        this.securityProperties = securityProperties;
    }

    @Override
    public List<String> shortcutFieldOrder() {
        return Arrays.asList(CoreCommonGatewayConstant.PARAM_TYPE_KEY, ATTRIBUTES_KEY);
    }


    @Override
    public GatewayFilter apply(Config config) {
        AuthorizeGatewayFilter gatewayFilter = new AuthorizeGatewayFilter(config, accessDecisionManager, securityProperties);
        gatewayFilter.setFactory(this);
        return gatewayFilter;
    }


    private static class AuthorizeGatewayFilter implements GatewayFilter, Ordered {
        private final Config config;
        private final AccessDecisionManager accessDecisionManager;
        private final boolean enabled;
        private final LinkedHashMap<RequestMatcher, Collection<ConfigAttribute>> whiteList;
        private GatewayFilterFactory<Config> gatewayFilterFactory;
        private final Authentication anonymous = new AnonymousAuthenticationToken("key", "anonymous", AuthorityUtils.createAuthorityList("ROLE_ANONYMOUS"));

        public AuthorizeGatewayFilter(Config config,
                                      Map<Integer, AccessDecisionManager> accessDecisionManager,
                                      CustomSecurityProperties securityProperties) {
            this.config = config;
            this.accessDecisionManager = accessDecisionManager.get(this.config.getDecision());
            this.enabled = securityProperties.isEnabled();
            this.whiteList = AuthorizeUtils.createWhiteList(securityProperties);
        }

        @Override
        public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        }

        @Override
        public int getOrder() {
            return 2;
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
         * 决策类型: 0：一票赞成即通过，1：大多数投票赞成即通过，2：所有投票赞成才通过,默认0
         */
        private int decision;
        /**
         * 资源权限List<ResourceActionModel>
         */
        private LinkedHashMap<RequestMatcher, Collection<ConfigAttribute>> attributes = new LinkedHashMap<>();


        public LinkedHashMap<RequestMatcher, Collection<ConfigAttribute>> getAttributes(LinkedHashMap<RequestMatcher, Collection<ConfigAttribute>> whiteList) {
            if (CollUtil.isNotEmpty(whiteList)) {
                this.attributes.putAll(whiteList);
            }
            return this.attributes;
        }

        public int getDecision() {
            return this.decision;
        }

        public Config setAttributes(String attributes) {
            if (StringUtils.isNotBlank(attributes)) {
                TypeReference<List<ResourceActionModel>> typeReference = new TypeReference<>() {
                };
                List<ResourceActionModel> resourcePermissions = JSONObject.parseObject(attributes, typeReference);
                this.attributes = Oauth2CommonUtils.resourceActionToAttribute(resourcePermissions, true);
            }
            return this;
        }

        public Config setDecision(int decision) {
            this.decision = decision;
            return this;
        }
    }
}
