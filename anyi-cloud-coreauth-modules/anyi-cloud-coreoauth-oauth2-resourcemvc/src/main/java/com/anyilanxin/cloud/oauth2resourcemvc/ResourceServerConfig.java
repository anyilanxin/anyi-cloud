/*
 * Copyright (c) 2023-present ZHOUXUANHONG(安一老厨)<anyilanxin@aliyun.com>
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
 *   1.请不要删除和修改根目录下的LICENSE.txt文件；
 *   2.请不要删除和修改 AnYi Cloud 源码头部的版权声明；
 *   3.请保留源码和相关描述文件的项目出处，作者声明等；
 *   4.分发源码时候，请注明软件出处 https://github.com/anyilanxin/anyi-cloud；
 *   5.在修改包名，模块名称，项目代码等时，请注明软件出处 https://github.com/anyilanxin/anyi-cloud；
 *   6.本软件不允许在国家法律规定范围外使用，如出现违法行为原作者本人不承担任何法律风险；
 *   7.进行商用时，不得基于AnYi Cloud的基础，修改包装而成一个与AnYi Cloud EE、AnYi Zeebe EE、AnYi Standalone EE功能类似的程序，进行销售或发布，参与同类软件产品市场的竞争；
 *   8.本软件使用的第三方依赖皆为开源软件，如需要修改第三方源码请遵循第三方源码附带开源协议；
 *   9.本软件流程部分请遵循camunda开源协议：
 *     https://docs.camunda.org/manual/latest/introduction/third-party-libraries
 *     https://github.com/camunda/camunda-bpm-platform/blob/master/LICENSE
 *   10.若您的项目无法满足以上几点，可申请商业授权。
 */

package com.anyilanxin.cloud.oauth2resourcemvc;

import com.anyilanxin.cloud.oauth2common.config.properties.AnYiResourceProperties;
import com.anyilanxin.cloud.oauth2common.store.IAuthStore;
import com.anyilanxin.cloud.oauth2commonmvc.config.AnYiAccessDeniedHandler;
import com.anyilanxin.cloud.oauth2commonmvc.config.AnYiDefaultBearerTokenResolver;
import com.anyilanxin.cloud.oauth2commonmvc.config.AnYiJwtAuthenticationConverter;
import com.anyilanxin.cloud.oauth2commonmvc.config.AnYiLoginUrlAuthenticationEntryPoint;
import com.anyilanxin.cloud.oauth2commonmvc.utils.Oauth2Utils;
import lombok.RequiredArgsConstructor;
import org.dromara.hutool.core.collection.CollUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.actuate.autoconfigure.security.servlet.EndpointRequest;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.servlet.util.matcher.MvcRequestMatcher;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;

import java.util.Objects;
import java.util.Set;

/**
 * OAuth2 Resource Server
 *
 * @author Yu jin xiang 2023/6/13
 */
@AutoConfiguration
@EnableWebSecurity
@EnableMethodSecurity(jsr250Enabled = true, securedEnabled = true)
@RequiredArgsConstructor
public class ResourceServerConfig {
    private final AnYiResourceProperties securityProperties;
    private final ApplicationContext applicationContext;

    /**
     * 流程引擎需要特殊处理
     * <p>
     * <a href="https://github.com/camunda/camunda-bpm-platform/issues/3914">流程引擎特殊处理说明</a>
     * </p>
     */
    @Value("${camunda.bpm.enabled:false}")
    private boolean camunda;

    @Bean
    @ConditionalOnExpression("${camunda.bpm.enabled:false}")
    MvcRequestMatcher.Builder mvc(HandlerMappingIntrospector introspector) {
        return new MvcRequestMatcher.Builder(introspector);
    }


    @Bean
    public SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http,
                                                          HandlerMappingIntrospector introspector,
                                                          final IAuthStore authStore,
                                                          AnYiDefaultBearerTokenResolver bearerTokenResolver) throws Exception {
        MvcRequestMatcher.Builder mvc;
        if (camunda) {
            mvc = mvc(introspector);
        } else {
            mvc = null;
        }
        // 禁用csrf与cors
        http.csrf(AbstractHttpConfigurer::disable);
        http.authorizeHttpRequests(authorize -> {
                    // 健康检测断点放过
                    authorize.requestMatchers(EndpointRequest.toAnyEndpoint()).permitAll();
                    // 白名单以及注释白名单处理
                    Set<Oauth2Utils.WhiteListInfo> whiteList = Oauth2Utils.getWhiteList(applicationContext, securityProperties);
                    if (CollUtil.isNotEmpty(whiteList)) {
                        for (Oauth2Utils.WhiteListInfo whiteListInfo : whiteList) {
                            Set<HttpMethod> methods = whiteListInfo.getMethods();
                            Set<String> urls = whiteListInfo.getUrls();
                            if (CollUtil.isNotEmpty(methods)) {
                                for (HttpMethod method : methods) {
                                    if (Objects.nonNull(mvc)) {
                                        for (String url : urls) {
                                            authorize.requestMatchers(mvc.pattern(method, url)).permitAll();
                                        }
                                    } else {
                                        authorize.requestMatchers(method, urls.toArray(new String[]{})).permitAll();
                                    }
                                }
                            } else {
                                if (Objects.nonNull(mvc)) {
                                    for (String url : urls) {
                                        authorize.requestMatchers(mvc.pattern(url)).permitAll();
                                    }
                                } else {
                                    authorize.requestMatchers(urls.toArray(new String[]{})).permitAll();
                                }
                            }
                        }
                    }
                    // 所有其他自定义鉴权
                    authorize.anyRequest().authenticated();
                }
        );
        http.oauth2ResourceServer((resourceServer) ->
                resourceServer.bearerTokenResolver(bearerTokenResolver)
                        .jwt(jwtSpec ->
                                jwtSpec.jwtAuthenticationConverter(new AnYiJwtAuthenticationConverter(authStore))
                        )
                        .accessDeniedHandler(new AnYiAccessDeniedHandler())
                        .authenticationEntryPoint(new AnYiLoginUrlAuthenticationEntryPoint(securityProperties.getLoginUrl()))
        );
        return http.build();
    }

}

