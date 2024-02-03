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

package com.anyilanxin.anyicloud.oauth2resourcewebflux.config;

import com.anyilanxin.anyicloud.corecommon.constant.AuthConstant;
import com.anyilanxin.anyicloud.oauth2common.config.properties.AnYiResourceProperties;
import com.anyilanxin.anyicloud.oauth2common.store.IAuthStore;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.actuate.autoconfigure.security.reactive.EndpointRequest;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authorization.ReactiveAuthorizationManager;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusReactiveJwtDecoder;
import org.springframework.security.oauth2.jwt.ReactiveJwtDecoder;
import org.springframework.security.oauth2.server.resource.web.server.authentication.ServerBearerTokenAuthenticationConverter;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.authorization.AuthorizationContext;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * 资源服务器配置
 *
 * @author vains
 */
@AutoConfiguration
@EnableWebFluxSecurity
@EnableReactiveMethodSecurity
@RequiredArgsConstructor
public class ResourceServerConfig {
    private final AnYiResourceProperties securityProperties;
    private final WebClient.Builder webClient;
    private final OAuth2ResourceServerProperties properties;

    /**
     * 配置认证相关的过滤器链
     *
     * @param http Spring Security的核心配置类
     * @return 过滤器链
     */
    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http, final IAuthStore authStore) {
        // 禁用csrf与cors
        http.csrf(ServerHttpSecurity.CsrfSpec::disable);
        http.cors(ServerHttpSecurity.CorsSpec::disable);
        // 开启全局验证
        http.authorizeExchange((authorize) -> authorize
                //全部需要认证
                .matchers(EndpointRequest.toAnyEndpoint()).permitAll()
                .pathMatchers("swagger-ui/**").permitAll()
                .pathMatchers("v3/api-docs/**").permitAll()
                .anyExchange().access(securityAuthorizationManager())
        );
        http.oauth2ResourceServer((resourceServer) -> resourceServer
                // 使用jwt
                .bearerTokenConverter(bearerTokenAuthenticationConverter())
                .jwt(jwtSpec -> jwtSpec
                        // 设置jwt解析器适配器
                        .jwtAuthenticationConverter(new AnYiJwtAuthenticationConverter(authStore))
                )
        );
        return http.build();
    }


//    /**
//     * 自定义jwt解析器，设置解析出来的权限信息的前缀与在jwt中的key
//     *
//     * @return jwt解析器适配器 ReactiveJwtAuthenticationConverterAdapter
//     */
//    public Converter<Jwt, Mono<AbstractAuthenticationToken>> grantedAuthoritiesExtractor() {
//        JwtGrantedAuthoritiesConverter grantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();
//        // 设置解析权限信息的前缀，设置为空是去掉前缀
//        grantedAuthoritiesConverter.setAuthorityPrefix("");
//        // 设置权限信息在jwt claims中的key
//        grantedAuthoritiesConverter.setAuthoritiesClaimName("authorities");
//        JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
//        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(grantedAuthoritiesConverter);
//        return new ReactiveJwtAuthenticationConverterAdapter(jwtAuthenticationConverter);
//    }


    @Bean
    @ConditionalOnExpression("!(anYiCoreCommonStr.notBlank('${spring.security.oauth2.resourceserver.jwt.jwk-set-uri:}') && '${spring.security.oauth2.resourceserver.jwt.lb:true}')")
    public ReactiveJwtDecoder anYiReactiveJwtDecoder(JwtDecoder jwtDecoder) {
        return new AnYiReactiveJwtDecoder(jwtDecoder);
    }


    @Bean
    @ConditionalOnExpression("anYiCoreCommonStr.notBlank('${spring.security.oauth2.resourceserver.jwt.jwk-set-uri:}') && '${spring.security.oauth2.resourceserver.jwt.lb:true}'")
    public ReactiveJwtDecoder reactiveJwtDecoder() {
        return NimbusReactiveJwtDecoder
                .withJwkSetUri(properties.getJwt().getJwkSetUri())
                .webClient(webClient.build())
                .build();
    }


    @Bean
    public ReactiveAuthorizationManager<AuthorizationContext> securityAuthorizationManager() {
        return new AnYiSecurityAuthorizationManager(securityProperties);
    }


    @Bean
    public ServerBearerTokenAuthenticationConverter bearerTokenAuthenticationConverter() {
        AnYiCustomServerBearerTokenAuthenticationConverter bearerTokenAuthenticationConverter = new AnYiCustomServerBearerTokenAuthenticationConverter();
        bearerTokenAuthenticationConverter.setAccessTokenQueryName(AuthConstant.ACCESS_TOKEN_QUERY_NAME);
        bearerTokenAuthenticationConverter.setBearerTokenHeaderName(AuthConstant.BEARER_TOKEN_HEADER_NAME);
        bearerTokenAuthenticationConverter.setAllowUriQueryParameter(true);
        return bearerTokenAuthenticationConverter;
    }
}
