/**
* Copyright (c) 2021-2022 ZHOUXUANHONG(安一老厨)<anyilanxin@aliyun.com>
*
* <p>AnYi Cloud Licensed under the Apache License, Version 2.0 (the "License"); you may not use
* this file except in compliance with the License. You may obtain a copy of the License at
*
* <p>http://www.apache.org/licenses/LICENSE-2.0
*
* <p>Unless required by applicable law or agreed to in writing, software distributed under the
* License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
* express or implied. See the License for the specific language governing permissions and
* limitations under the License.
*
* <p>AnYi Cloud 采用APACHE LICENSE 2.0开源协议，您在使用过程中，需要注意以下几点：
*
* <p>1.请不要删除和修改根目录下的LICENSE文件。 2.请不要删除和修改 AnYi Cloud 源码头部的版权声明。 3.请保留源码和相关描述文件的项目出处，作者声明等。
* 4.分发源码时候，请注明软件出处 https://github.com/anyilanxin/anyi-cloud 5.在修改包名，模块名称，项目代码等时，请注明软件出处
* https://github.com/anyilanxin/anyi-cloud 6.若您的项目无法满足以上几点，可申请商业授权
*/
package com.anyilanxin.skillfull.oauth2webflux.oauth2;

import com.anyilanxin.skillfull.corecommon.constant.AuthConstant;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.authentication.AuthenticationWebFilter;

/**
* @author zxiaozhou
* @date 2022-02-19 20:42
* @since JDK1.8
*/
@Configuration
@EnableWebFluxSecurity
@RequiredArgsConstructor
@Slf4j
public class ResourceServerConfig {
    private final TokenStore tokenStore;
    private final CustomServerAccessDeniedHandler customServerAccessDeniedHandler;
    private final CustomServerAuthenticationEntryPoint authenticationEntryPoint;

    @Bean
    public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
        // token管理器
        ReactiveAuthenticationManager authenticationManager =
                new CustomReactiveAuthenticationManager(tokenStore);
        // 认证过滤器
        AuthenticationWebFilter authenticationWebFilter =
                new AuthenticationWebFilter(authenticationManager);
        CustomServerBearerTokenAuthenticationConverter serverBearerTokenAuthenticationConverter =
                new CustomServerBearerTokenAuthenticationConverter();
        serverBearerTokenAuthenticationConverter.setAllowUriQueryParameter(true);
        serverBearerTokenAuthenticationConverter.setAccessTokenQueryName(
                AuthConstant.ACCESS_TOKEN_QUERY_NAME);
        serverBearerTokenAuthenticationConverter.setBearerTokenHeaderName(
                AuthConstant.BEARER_TOKEN_HEADER_NAME);
        authenticationWebFilter.setServerAuthenticationConverter(
                serverBearerTokenAuthenticationConverter);
        http.httpBasic()
                .disable()
                .addFilterBefore(authenticationWebFilter, SecurityWebFiltersOrder.AUTHENTICATION)
                .authorizeExchange()
                .anyExchange()
                .permitAll()
                .and()
                .exceptionHandling()
                .accessDeniedHandler(customServerAccessDeniedHandler) // 处理未授权
                .authenticationEntryPoint(authenticationEntryPoint)
                .and()
                .csrf()
                .disable();
        return http.build();
    }
}
