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

package com.anyilanxin.anyicloud.auth.oauth2.config;


import com.anyilanxin.anyicloud.auth.modules.login.service.impl.PasswordUserDetailsService;
import com.anyilanxin.anyicloud.auth.modules.login.service.impl.PhoneUserDetailsService;
import com.anyilanxin.anyicloud.auth.oauth2.authentication.tokenendpoint.password.AnYiOAuth2PasswordAuthenticationConverter;
import com.anyilanxin.anyicloud.auth.oauth2.authentication.tokenendpoint.password.AnYiOAuth2PasswordAuthenticationProvider;
import com.anyilanxin.anyicloud.auth.oauth2.authentication.tokenendpoint.sms.AnYiOAuth2SmsAuthenticationConverter;
import com.anyilanxin.anyicloud.auth.oauth2.authentication.tokenendpoint.sms.AnYiOAuth2SmsAuthenticationProvider;
import com.anyilanxin.anyicloud.auth.oauth2.config.property.AnYiCustomSecurityProperties;
import com.anyilanxin.anyicloud.auth.oauth2.federation.FederatedIdentityIdTokenCustomizer;
import com.anyilanxin.anyicloud.auth.oauth2.repository.redis.authorization.RedisOAuth2AuthorizationService;
import com.anyilanxin.anyicloud.auth.oauth2.repository.redis.authorizationConsent.RedisOAuth2AuthorizationConsentService;
import com.anyilanxin.anyicloud.auth.oauth2.validate.impl.PictureValidate;
import com.anyilanxin.anyicloud.auth.oauth2.validate.impl.SmsValidate;
import com.anyilanxin.anyicloud.auth.utils.AnYiSecurityUtils;
import com.anyilanxin.anyicloud.oauth2common.constant.AnYiSecurityConstants;
import com.anyilanxin.anyicloud.oauth2common.store.IAuthStore;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.oauth2.server.authorization.OAuth2Authorization;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationConsentService;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationService;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configurers.OAuth2AuthorizationServerConfigurer;
import org.springframework.security.oauth2.server.authorization.settings.AuthorizationServerSettings;
import org.springframework.security.oauth2.server.authorization.token.*;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.Collections;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * @author Joe Grandja
 * @author Daniel Garnier-Moiroux
 * @author Steve Riesenberg
 * @since 1.1
 */
@Configuration(proxyBeanMethods = false)
@RequiredArgsConstructor
public class AuthorizationServerConfig {
    private final PasswordUserDetailsService passwordDetailsService;
    private final PictureValidate pictureValidate;
    private final PhoneUserDetailsService phoneDetailsService;
    private final SmsValidate smsValidate;
    private final AnYiCustomSecurityProperties securityProperties;


    @Bean
    @Order(Ordered.HIGHEST_PRECEDENCE)
    public SecurityFilterChain authorizationServerSecurityFilterChain(
            HttpSecurity http,
            CorsFilter filter,
            RegisteredClientRepository registeredClientRepository,
            OAuth2AuthorizationService authorizationService,
            PasswordEncoder passwordEncoder,
            AuthorizationServerSettings authorizationServerSettings,
            OAuth2TokenGenerator<?> tokenGenerator) throws Exception {
        // 基础配置
        AnYiSecurityUtils.applyBasicSecurity(http, registeredClientRepository, authorizationService, passwordEncoder, tokenGenerator, authorizationServerSettings, securityProperties);
        http.addFilter(filter);
        // 自定义令牌授权grant type
        http.getConfigurer(OAuth2AuthorizationServerConfigurer.class)
                .tokenEndpoint(tokenEndpoint -> tokenEndpoint
                        // 密码
                        .accessTokenRequestConverter(new AnYiOAuth2PasswordAuthenticationConverter())
                        .authenticationProvider(new AnYiOAuth2PasswordAuthenticationProvider(
                                pictureValidate,
                                authorizationService,
                                passwordDetailsService,
                                tokenGenerator,
                                passwordEncoder)
                        )
                        // 验证码
                        .accessTokenRequestConverter(new AnYiOAuth2SmsAuthenticationConverter())
                        .authenticationProvider(new AnYiOAuth2SmsAuthenticationProvider(smsValidate, authorizationService, phoneDetailsService, tokenGenerator))
                );
        return http.build();
    }


    @Bean
    public OAuth2TokenCustomizer<JwtEncodingContext> idTokenCustomizer() {
        return new FederatedIdentityIdTokenCustomizer();
    }


    @Bean
    OAuth2TokenGenerator<?> tokenGenerator(JWKSource<SecurityContext> jwkSource) {
        JwtGenerator jwtGenerator = new JwtGenerator(new NimbusJwtEncoder(jwkSource));
        jwtGenerator.setJwtCustomizer(jwtCustomizer());
        OAuth2AccessTokenGenerator accessTokenGenerator = new OAuth2AccessTokenGenerator();
        OAuth2RefreshTokenGenerator refreshTokenGenerator = new OAuth2RefreshTokenGenerator();
        return new DelegatingOAuth2TokenGenerator(jwtGenerator, accessTokenGenerator, refreshTokenGenerator);
    }


    public OAuth2TokenCustomizer<JwtEncodingContext> jwtCustomizer() {
        return context -> {
            JwtClaimsSet.Builder claims = context.getClaims();
            OAuth2Authorization authorization = context.getAuthorization();
            String id = authorization.getId();
            Authentication principal = context.getPrincipal();
            principal.getName();
            claims.id(id);
            AuthorizationGrantType authorizationGrantType = context.getAuthorizationGrantType();
            claims.claim("authorizationGrantType", authorizationGrantType);
            RegisteredClient registeredClient = context.getRegisteredClient();
            Map<String, Object> settings = registeredClient.getClientSettings().getSettings();
            Object resourceIdsObject = settings.get(AnYiSecurityConstants.RESOURCE_IDS);
            if (Objects.nonNull(resourceIdsObject)) {
                Set<String> resourceIds = (Set<String>) resourceIdsObject;
                claims.claims((claim) -> {
                    claim.put("resourceIds", resourceIds);
                });
            }
        };
    }


    @Bean
    public OAuth2AuthorizationService authorizationService(RedisConnectionFactory redisConnectionFactory,
                                                           RegisteredClientRepository clientRepository,
                                                           IAuthStore authStore,
                                                           AutowireCapableBeanFactory beanFactory) {
        RedisTemplate<String, String> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        redisTemplate.setKeySerializer(StringRedisSerializer.UTF_8);
        redisTemplate.setValueSerializer(StringRedisSerializer.UTF_8);
        redisTemplate.afterPropertiesSet();
        RedisOAuth2AuthorizationService redisOAuth2AuthorizationService = new RedisOAuth2AuthorizationService(redisTemplate, authStore, clientRepository, beanFactory);
        redisOAuth2AuthorizationService.setPrefix("ANYI_LANXIN_CLOUD_EE:");
        return redisOAuth2AuthorizationService;
    }


    @Bean
    public CorsFilter corsFilter() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        //1,允许任何来源
        corsConfiguration.setAllowedOriginPatterns(Collections.singletonList("*"));
        //2,允许任何请求头
        corsConfiguration.addAllowedHeader(CorsConfiguration.ALL);
        //3,允许任何方法
        corsConfiguration.addAllowedMethod(CorsConfiguration.ALL);
        //4,允许凭证
        corsConfiguration.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfiguration);
        return new CorsFilter(source);
    }

    @Bean
    public OAuth2AuthorizationConsentService oAuth2AuthorizationConsentService() {
        return new RedisOAuth2AuthorizationConsentService();
    }


    /**
     * 添加认证服务器配置，设置jwt签发者、默认端点请求地址等
     *
     * @return AuthorizationServerSettings
     */
    @Bean
    public AuthorizationServerSettings authorizationServerSettings() {
        return AuthorizationServerSettings.builder()
                .build();
    }

}
