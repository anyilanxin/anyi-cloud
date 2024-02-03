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

package com.anyilanxin.anyicloud.auth.utils;


import com.anyilanxin.anyicloud.auth.oauth2.AnYiLoginUrlAuthenticationEntryPoint;
import com.anyilanxin.anyicloud.auth.oauth2.authentication.AnYiAuthorizationEndpointErrorHandler;
import com.anyilanxin.anyicloud.auth.oauth2.authentication.client.AnYiClientSecretAuthenticationProvider;
import com.anyilanxin.anyicloud.auth.oauth2.authentication.client.AnYiClientSecretPostAuthenticationConverter;
import com.anyilanxin.anyicloud.auth.oauth2.authentication.device.DeviceClientAuthenticationConverter;
import com.anyilanxin.anyicloud.auth.oauth2.authentication.device.DeviceClientAuthenticationProvider;
import com.anyilanxin.anyicloud.auth.oauth2.authentication.tokenendpoint.authorizationcode.AnYiOAuth2AuthorizationCodeAuthenticationConverter;
import com.anyilanxin.anyicloud.auth.oauth2.authentication.tokenendpoint.authorizationcode.AnYiOAuth2AuthorizationCodeAuthenticationProvider;
import com.anyilanxin.anyicloud.auth.oauth2.authentication.tokenendpoint.clientcredentials.AnYiOAuth2ClientCredentialsAuthenticationConverter;
import com.anyilanxin.anyicloud.auth.oauth2.authentication.tokenendpoint.clientcredentials.AnYiOAuth2ClientCredentialsAuthenticationProvider;
import com.anyilanxin.anyicloud.auth.oauth2.authentication.tokenendpoint.devicecode.AnYiOAuth2DeviceCodeAuthenticationConverter;
import com.anyilanxin.anyicloud.auth.oauth2.authentication.tokenendpoint.devicecode.AnYiOAuth2DeviceCodeAuthenticationProvider;
import com.anyilanxin.anyicloud.auth.oauth2.config.property.AnYiCustomSecurityProperties;
import com.anyilanxin.anyicloud.auth.oauth2.handler.ConsentAuthenticationFailureHandler;
import com.anyilanxin.anyicloud.auth.oauth2.handler.DeviceAuthorizationResponseHandler;
import com.anyilanxin.anyicloud.auth.oauth2.token.AnYiTokenEndpointErrorHandler;
import com.anyilanxin.anyicloud.auth.oauth2.token.AnYiTokenEndpointResponseHandler;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationService;
import org.springframework.security.oauth2.server.authorization.authentication.ClientSecretAuthenticationProvider;
import org.springframework.security.oauth2.server.authorization.authentication.OAuth2AuthorizationCodeAuthenticationProvider;
import org.springframework.security.oauth2.server.authorization.authentication.OAuth2ClientCredentialsAuthenticationProvider;
import org.springframework.security.oauth2.server.authorization.authentication.OAuth2DeviceCodeAuthenticationProvider;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configuration.OAuth2AuthorizationServerConfiguration;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configurers.OAuth2AuthorizationServerConfigurer;
import org.springframework.security.oauth2.server.authorization.settings.AuthorizationServerSettings;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenGenerator;
import org.springframework.security.oauth2.server.authorization.web.authentication.ClientSecretPostAuthenticationConverter;
import org.springframework.security.oauth2.server.authorization.web.authentication.OAuth2AuthorizationCodeAuthenticationConverter;
import org.springframework.security.oauth2.server.authorization.web.authentication.OAuth2ClientCredentialsAuthenticationConverter;
import org.springframework.security.oauth2.server.authorization.web.authentication.OAuth2DeviceCodeAuthenticationConverter;
import org.springframework.security.web.authentication.AuthenticationConverter;
import org.springframework.security.web.util.UrlUtils;
import org.springframework.security.web.util.matcher.MediaTypeRequestMatcher;

/**
 * 认证鉴权工具
 *
 * @author vains
 */
@Slf4j
public class AnYiSecurityUtils {

    private static final String CUSTOM_DEVICE_REDIRECT_URI = "/activate/redirect";

    private static final String CUSTOM_CONSENT_REDIRECT_URI = "/oauth2/consent/redirect";

    private AnYiSecurityUtils() {
        // 禁止实例化工具类
        throw new UnsupportedOperationException("Utility classes cannot be instantiated.");
    }

    @SneakyThrows
    public static void applyBasicSecurity(HttpSecurity http,
                                          RegisteredClientRepository registeredClientRepository,
                                          OAuth2AuthorizationService authorizationService,
                                          PasswordEncoder passwordEncoder,
                                          OAuth2TokenGenerator<?> tokenGenerator,
                                          AuthorizationServerSettings authorizationServerSettings,
                                          AnYiCustomSecurityProperties customSecurityProperties) {
        OAuth2AuthorizationServerConfiguration.applyDefaultSecurity(http);
        http.getConfigurer(OAuth2AuthorizationServerConfigurer.class)
                // 客户端身份验证的配置器
                .clientAuthentication(clientAuthentication ->
                                clientAuthentication
                                        .authenticationConverters(authenticationConverters -> {
                                            int length = authenticationConverters.size();
                                            for (int index = 0; index < length; index++) {
                                                AuthenticationConverter authenticationConverter = authenticationConverters.get(index);
                                                if (authenticationConverter.getClass().getSimpleName().equals(ClientSecretPostAuthenticationConverter.class.getSimpleName())) {
                                                    authenticationConverters.remove(index);
                                                    authenticationConverters.add(index, new AnYiClientSecretPostAuthenticationConverter(customSecurityProperties));
                                                    break;
                                                }
                                            }
                                        })
                                        .authenticationProviders(authenticationProviders -> {
                                            int length = authenticationProviders.size();
                                            for (int index = 0; index < length; index++) {
                                                AuthenticationProvider authenticationProvider = authenticationProviders.get(index);
                                                if (authenticationProvider.getClass().getSimpleName().equals(ClientSecretAuthenticationProvider.class.getSimpleName())) {
                                                    authenticationProviders.remove(index);
                                                    AnYiClientSecretAuthenticationProvider clientSecretAuthenticationProvider = new AnYiClientSecretAuthenticationProvider(registeredClientRepository, authorizationService);
                                                    if (passwordEncoder != null) {
                                                        clientSecretAuthenticationProvider.setPasswordEncoder(passwordEncoder);
                                                    }
                                                    authenticationProviders.add(index, clientSecretAuthenticationProvider);
                                                    break;
                                                }
                                            }
                                        })
                                        .errorResponseHandler(new AnYiAuthorizationEndpointErrorHandler())
//                                .authenticationSuccessHandler(new AnYiAuthorizationEndpointResponseHandler())
                                        .authenticationConverter(new DeviceClientAuthenticationConverter(authorizationServerSettings.getDeviceAuthorizationEndpoint()))
                                        .authenticationProvider(new DeviceClientAuthenticationProvider(registeredClientRepository))

                )
//                // 授权端点
                .authorizationEndpoint(authorizationEndpoint -> {
                            authorizationEndpoint
                                    .authorizationRequestConverters(authenticationConverters -> {
                                    })
                                    .authenticationProviders(authenticationProviders -> {
//                                        int length = authenticationProviders.size();
//                                        for (int index = 0; index < length; index++) {
//                                            AuthenticationProvider authenticationProvider = authenticationProviders.get(index);
//                                            if (authenticationProvider.getClass().getSimpleName().equals(OAuth2AuthorizationCodeRequestAuthenticationProvider.class.getSimpleName())) {
//                                                authenticationProviders.remove(index);
//                                                AnYiOAuth2AuthorizationCodeRequestAuthenticationProvider clientSecretAuthenticationProvider = new AnYiClientSecretAuthenticationProvider(registeredClientRepository, authorizationService);
//                                                if (passwordEncoder != null) {
//                                                    clientSecretAuthenticationProvider.setPasswordEncoder(passwordEncoder);
//                                                }
//                                                authenticationProviders.add(index, clientSecretAuthenticationProvider);
//                                                break;
//                                            }
//                                        }
                                    });
                            // 校验授权确认页面是否为完整路径；是否是前后端分离的页面
//                            boolean absoluteUrl = UrlUtils.isAbsoluteUrl(customSecurityProperties.getConsentPageUri());
//                            // 如果是分离页面则重定向，否则转发请求
//                            authorizationEndpoint.consentPage(customSecurityProperties.getConsentPageUri());
//                            if (absoluteUrl) {
//                                // 适配前后端分离的授权确认页面，成功/失败响应json
//                                authorizationEndpoint.errorResponseHandler(new ConsentAuthenticationFailureHandler(customSecurityProperties.getConsentPageUri()));
//                                authorizationEndpoint.authorizationResponseHandler(new ConsentAuthorizationResponseHandler(customSecurityProperties.getConsentPageUri()));
//                            }
                        }
                )
                // 设备授权端点
                .deviceAuthorizationEndpoint(deviceAuthorizationEndpoint ->
                        deviceAuthorizationEndpoint.verificationUri(UrlUtils.isAbsoluteUrl(customSecurityProperties.getDeviceActivatedUri()) ? CUSTOM_DEVICE_REDIRECT_URI : customSecurityProperties.getDeviceActivateUri())
                )
//                // 设备验证端点
                .deviceVerificationEndpoint(deviceVerificationEndpoint -> {
                            // 校验授权确认页面是否为完整路径；是否是前后端分离的页面
                            boolean absoluteUrl = UrlUtils.isAbsoluteUrl(customSecurityProperties.getConsentPageUri());
                            // 如果是分离页面则重定向，否则转发请求
                            deviceVerificationEndpoint.consentPage(absoluteUrl ? CUSTOM_CONSENT_REDIRECT_URI : customSecurityProperties.getConsentPageUri());
                            if (absoluteUrl) {
                                // 适配前后端分离的授权确认页面，失败响应json
                                deviceVerificationEndpoint.errorResponseHandler(new ConsentAuthenticationFailureHandler(customSecurityProperties.getConsentPageUri()));
                            }
                            // 如果授权码验证页面或者授权确认页面是前后端分离的
                            if (UrlUtils.isAbsoluteUrl(customSecurityProperties.getDeviceActivateUri()) || absoluteUrl) {
                                log.info("授权码验证页面是否为分离页面：{}，授权确认页面是否为分离页面：{}", UrlUtils.isAbsoluteUrl(customSecurityProperties.getDeviceActivateUri()), absoluteUrl);
                                // 添加响应json处理
                                deviceVerificationEndpoint.deviceVerificationResponseHandler(new DeviceAuthorizationResponseHandler(customSecurityProperties.getDeviceActivatedUri()));
                            }
                        }

                )
//                // 令牌端点
                .tokenEndpoint(tokenEndpoint ->
                        tokenEndpoint
                                .accessTokenRequestConverters(authenticationConverters -> {
                                    int length = authenticationConverters.size();
                                    for (int index = 0; index < length; index++) {
                                        AuthenticationConverter authenticationConverter = authenticationConverters.get(index);
                                        if (authenticationConverter.getClass().getSimpleName().equals(OAuth2AuthorizationCodeAuthenticationConverter.class.getSimpleName())) {
                                            authenticationConverters.remove(index);
                                            authenticationConverters.add(index, new AnYiOAuth2AuthorizationCodeAuthenticationConverter());
                                            index--;
                                        } else if (authenticationConverter.getClass().getSimpleName().equals(OAuth2ClientCredentialsAuthenticationConverter.class.getSimpleName())) {
                                            authenticationConverters.remove(index);
                                            authenticationConverters.add(index, new AnYiOAuth2ClientCredentialsAuthenticationConverter());
                                            index--;
                                        } else if (authenticationConverter.getClass().getSimpleName().equals(OAuth2DeviceCodeAuthenticationConverter.class.getSimpleName())) {
                                            authenticationConverters.remove(index);
                                            authenticationConverters.add(index, new AnYiOAuth2DeviceCodeAuthenticationConverter());
                                            index--;
                                        }
                                    }
                                })
                                .authenticationProviders(authenticationProviders -> {
                                    int length = authenticationProviders.size();
                                    for (int index = 0; index < length; index++) {
                                        AuthenticationProvider authenticationProvider = authenticationProviders.get(index);
                                        if (authenticationProvider.getClass().getSimpleName().equals(OAuth2AuthorizationCodeAuthenticationProvider.class.getSimpleName())) {
                                            authenticationProviders.remove(index);
                                            authenticationProviders.add(index, new AnYiOAuth2AuthorizationCodeAuthenticationProvider(authorizationService, tokenGenerator));
                                            index--;
                                        } else if (authenticationProvider.getClass().getSimpleName().equals(OAuth2ClientCredentialsAuthenticationProvider.class.getSimpleName())) {
                                            authenticationProviders.remove(index);
                                            authenticationProviders.add(index, new AnYiOAuth2ClientCredentialsAuthenticationProvider(authorizationService, tokenGenerator));
                                            index--;
                                        } else if (authenticationProvider.getClass().getSimpleName().equals(OAuth2DeviceCodeAuthenticationProvider.class.getSimpleName())) {
                                            authenticationProviders.remove(index);
                                            authenticationProviders.add(index, new AnYiOAuth2DeviceCodeAuthenticationProvider(authorizationService, tokenGenerator));
                                            index--;
                                        }
                                    }
                                })
                                .accessTokenResponseHandler(new AnYiTokenEndpointResponseHandler())
                                .errorResponseHandler(new AnYiTokenEndpointErrorHandler())
                )
        // 令牌自省端点
//                .tokenIntrospectionEndpoint(tokenIntrospectionEndpoint ->
//                        tokenIntrospectionEndpoint
//                                .introspectionRequestConverter(introspectionRequestConverter)
//                                .authenticationProvider(authenticationProvider)
//                                .introspectionResponseHandler(introspectionResponseHandler)
//                                .errorResponseHandler(errorResponseHandler)
//                )
//                // 令牌撤销端点
//                .tokenRevocationEndpoint(tokenRevocationEndpoint ->
//                        tokenRevocationEndpoint
//                                .revocationRequestConverter(revocationRequestConverter)
//                                .authenticationProvider(authenticationProvider)
//                                .revocationResponseHandler(revocationResponseHandler)
//                                .errorResponseHandler(errorResponseHandler)
//                )
        // open id提供端点
//                .oidc(oidc ->
//                        oidc
//                                // open id提供端点
//                                .providerConfigurationEndpoint(providerConfigurationEndpoint ->
//                                        providerConfigurationEndpoint
//                                                .providerConfigurationCustomizer(providerConfigurationCustomizer)
//                                )
//                                // open id注销端点
//                                .logoutEndpoint(logoutEndpoint ->
//                                        logoutEndpoint
//                                                .logoutRequestConverter(logoutRequestConverter)
//                                                .authenticationProvider(authenticationProvider)
//                                                .logoutResponseHandler(logoutResponseHandler)
//                                                .errorResponseHandler(errorResponseHandler)
//                                )
//                                // open id用户信息端点
//                                .userInfoEndpoint(userInfoEndpoint ->
//                                        userInfoEndpoint
//                                                .userInfoRequestConverter(userInfoRequestConverter)
//                                                .authenticationProvider(authenticationProvider)
//                                                .userInfoResponseHandler(userInfoResponseHandler)
//                                                .errorResponseHandler(errorResponseHandler)
//                                                .userInfoMapper(userInfoMapper)
//                                )
//                )
        ;
        AnYiLoginUrlAuthenticationEntryPoint loginUrlAuthenticationEntryPoint = new AnYiLoginUrlAuthenticationEntryPoint("/login");
        loginUrlAuthenticationEntryPoint.setUseForward(true);
        http.exceptionHandling((exceptions) -> exceptions
                .defaultAuthenticationEntryPointFor(
                        loginUrlAuthenticationEntryPoint,
                        new MediaTypeRequestMatcher(MediaType.TEXT_HTML)
                )
        );
    }


}
