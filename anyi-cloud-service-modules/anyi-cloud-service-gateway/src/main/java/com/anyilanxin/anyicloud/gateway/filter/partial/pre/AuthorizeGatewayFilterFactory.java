/*
 * Copyright (c) 2021-present ZHOUXUANHONG(安一老厨)<anyilanxin@aliyun.com>
 *
 * AnYi Cloud Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * AnYi Cloud 采用APACHE LICENSE 2.0开源协议，您在使用过程中，需要注意以下几点：
 *   1.请不要删除和修改根目录下的LICENSE文件；
 *   2.请不要删除和修改 AnYi Cloud 源码头部的版权声明；
 *   3.请保留源码和相关描述文件的项目出处，作者声明等；
 *   4.分发源码时候，请注明软件出处 https://github.com/anyilanxin/anyi-cloud；
 *   5.在修改包名，模块名称，项目代码等时，请注明软件出处 https://github.com/anyilanxin/anyi-cloud；
 *   6.本软件不允许在国家法律规定范围外使用，如出现违法行为原作者本人不承担任何法律风险；
 *   7.进行商用时，不得基于AnYi Cloud的基础，修改包装而成一个与AnYi Zeebe EE、AnYi Cloud EE功能类似的程序，进行销售或发布，参与同类软件产品市场的竞争；
 *   8.本软件使用的第三方依赖皆为开源软件，如需要修改第三方源码请遵循第三方源码附带开源协议；
 *   9.本软件流程部分请遵循camunda开源协议：
 *     https://docs.camunda.org/manual/latest/introduction/third-party-libraries
 *     https://github.com/camunda/camunda-bpm-platform/blob/master/LICENSE
 *   10.若您的项目无法满足以上几点，可申请商业授权。
 */

package com.anyilanxin.anyicloud.gateway.filter.partial.pre;

import static com.anyilanxin.anyicloud.corecommon.constant.CoreCommonGatewayConstant.ATTRIBUTES_KEY;
import static org.springframework.cloud.gateway.support.GatewayToStringStyler.filterToStringCreator;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.CollectionUtil;
import com.anyilanxin.anyicloud.corecommon.constant.CommonCoreConstant;
import com.anyilanxin.anyicloud.corecommon.constant.CoreCommonGatewayConstant;
import com.anyilanxin.anyicloud.gateway.core.config.properties.CustomSecurityProperties;
import com.anyilanxin.anyicloud.gateway.core.constant.CommonGatewayConstant;
import com.anyilanxin.anyicloud.oauth2common.authinfo.SkillFullUserDetails;
import com.anyilanxin.anyicloud.oauth2common.constant.OAuth2RequestExtendConstant;

import java.io.Serializable;
import java.net.URI;
import java.util.*;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.cloud.gateway.filter.factory.GatewayFilterFactory;
import org.springframework.cloud.gateway.route.Route;
import org.springframework.cloud.gateway.support.ServerWebExchangeUtils;
import org.springframework.core.Ordered;
import org.springframework.http.HttpMethod;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.OAuth2Request;
import org.springframework.util.AntPathMatcher;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * 鉴权过滤器
 *
 * @author zxh
 * @date 2020-09-11 22:34
 * @since 1.0.0
 */
@Slf4j
public class AuthorizeGatewayFilterFactory extends AbstractGatewayFilterFactory<AuthorizeGatewayFilterFactory.Config> {
    private final CustomSecurityProperties securityProperties;
    private final AntPathMatcher antPathMatcher;

    public AuthorizeGatewayFilterFactory(CustomSecurityProperties securityProperties, AntPathMatcher antPathMatcher) {
        super(AuthorizeGatewayFilterFactory.Config.class);
        this.securityProperties = securityProperties;
        this.antPathMatcher = antPathMatcher;
    }


    @Override
    public List<String> shortcutFieldOrder() {
        return Arrays.asList(CoreCommonGatewayConstant.PARAM_TYPE_KEY, ATTRIBUTES_KEY);
    }


    @Override
    public GatewayFilter apply(Config config) {
        AuthorizeGatewayFilter gatewayFilter = new AuthorizeGatewayFilter(config, antPathMatcher, securityProperties);
        gatewayFilter.setFactory(this);
        return gatewayFilter;
    }

    private static class AuthorizeGatewayFilter implements GatewayFilter, Ordered {
        private final Config config;
        private final CustomSecurityProperties properties;
        private final AntPathMatcher antPathMatcher;
        private GatewayFilterFactory<Config> gatewayFilterFactory;
        private final Authentication anonymous = new AnonymousAuthenticationToken("key", "anonymous", AuthorityUtils.createAuthorityList("ROLE_ANONYMOUS"));

        public AuthorizeGatewayFilter(Config config, AntPathMatcher antPathMatcher, CustomSecurityProperties securityProperties) {
            this.config = config;
            this.properties = securityProperties;
            this.antPathMatcher = antPathMatcher;
        }


        @Override
        public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
            if (!this.properties.isEnabled()) {
                return chain.filter(exchange);
            }
            return ReactiveSecurityContextHolder.getContext().map(SecurityContext::getAuthentication).defaultIfEmpty(this.anonymous).filter(a -> {
                if (a instanceof OAuth2Authentication) {
                    // 缓存用户信息到上下文
                    OAuth2Authentication authentication = (OAuth2Authentication) a;
                    Object principal = authentication.getPrincipal();
                    if (principal instanceof SkillFullUserDetails) {
                        SkillFullUserDetails userDetails = (SkillFullUserDetails) principal;
                        // 存储上下文
                        exchange.getAttributes().put(CommonGatewayConstant.GATEWAY_USER_INFO, userDetails);
                        // 如果是超级管理员直接放行
                        if (userDetails.isSuperAdmin()) {
                            return true;
                        }
                    }
                    // 验证白名单
                    if (isWhiteList(antPathMatcher, properties, exchange)) {
                        return true;
                    }
                    // 验证资源权限
                    Route route = exchange.getAttribute(ServerWebExchangeUtils.GATEWAY_ROUTE_ATTR);
                    if (Objects.isNull(route)) {
                        return true;
                    }
                    String resourceId = route.getId();
                    OAuth2Request oAuth2Request = authentication.getOAuth2Request();
                    Map<String, Serializable> extensions = oAuth2Request.getExtensions();
                    if (CollUtil.isNotEmpty(extensions) && Objects.nonNull(extensions.get(OAuth2RequestExtendConstant.LIMIT_RESOURCE))) {
                        if ((Integer) extensions.get(OAuth2RequestExtendConstant.LIMIT_RESOURCE) == 1) {
                            Set<String> resourceIds = authentication.getOAuth2Request().getResourceIds();
                            if (CollectionUtil.isEmpty(resourceIds) || !resourceIds.contains(resourceId)) {
                                throw new AccessDeniedException("当前用户没有访问该资源的权限:" + resourceId);
                            }
                        }
                    } else {
                        Set<String> resourceIds = authentication.getOAuth2Request().getResourceIds();
                        if (CollectionUtil.isEmpty(resourceIds) || !resourceIds.contains(resourceId)) {
                            throw new AccessDeniedException("当前用户没有访问该资源的权限:" + resourceId);
                        }
                    }
                }
                // 验证白名单
                if (isWhiteList(antPathMatcher, properties, exchange)) {
                    return true;
                }
                return false;
            }).map(authModel -> exchange.mutate().build()).defaultIfEmpty(exchange).flatMap(chain::filter);
        }


        @Override
        public int getOrder() {
            return 2;
        }


        @Override
        public String toString() {
            Object obj = (this.gatewayFilterFactory != null) ? this.gatewayFilterFactory : this;
            return filterToStringCreator(obj).append("New content type", " config.getNewContentType()").append("In class", " config.getInClass()").append("Out class", "config.getOutClass()").toString();
        }


        public void setFactory(GatewayFilterFactory<Config> gatewayFilterFactory) {
            this.gatewayFilterFactory = gatewayFilterFactory;
        }


        /**
         * 验证是否为白名单
         *
         * @param antPathMatcher
         * @param properties
         * @author zxh
         * @date 2022-07-28 07:57
         */
        private static boolean isWhiteList(AntPathMatcher antPathMatcher, CustomSecurityProperties properties, ServerWebExchange exchange) {
            URI uri = exchange.getRequiredAttribute(ServerWebExchangeUtils.GATEWAY_REQUEST_URL_ATTR);
            HttpMethod method = exchange.getRequest().getMethod();
            Set<String> commonWhiteList = properties.getCommonWhiteList();
            if (CollUtil.isEmpty(commonWhiteList)) {
                commonWhiteList = new HashSet<>();
            }
            if (CommonCoreConstant.PRO.equalsIgnoreCase(properties.getActive()) && CollUtil.isNotEmpty(properties.getProWhiteList())) {
                commonWhiteList.addAll(properties.getProWhiteList());
            } else if (CommonCoreConstant.TEST.equalsIgnoreCase(properties.getActive()) && CollUtil.isNotEmpty(properties.getTestWhiteList())) {
                commonWhiteList.addAll(properties.getTestWhiteList());
            } else if (CommonCoreConstant.DEV.equalsIgnoreCase(properties.getActive()) && CollUtil.isNotEmpty(properties.getDevWhiteList())) {
                commonWhiteList.addAll(properties.getDevWhiteList());
            }
            if (CollUtil.isNotEmpty(commonWhiteList)) {
                for (String whiteUrlInfo : commonWhiteList) {
                    String[] split = whiteUrlInfo.split("[;；]");
                    String whiteUrl = whiteUrlInfo;
                    if (split.length > 1) {
                        whiteUrl = split[0];
                        String requestMethod = split[1];
                        if (!method.matches(requestMethod)) {
                            continue;
                        }
                    }
                    if (antPathMatcher.match(whiteUrl, uri.getPath())) {
                        return true;
                    }
                }
            }
            return false;
        }
    }

    @Validated
    public static class Config {
    }
}
