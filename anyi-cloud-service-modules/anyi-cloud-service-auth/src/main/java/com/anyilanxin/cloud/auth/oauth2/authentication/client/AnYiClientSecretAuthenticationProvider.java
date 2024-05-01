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

package com.anyilanxin.cloud.auth.oauth2.authentication.client;

import com.anyilanxin.cloud.coremvc.utils.ServletUtils;
import com.anyilanxin.cloud.oauth2common.constant.AnYiSecurityConstants;
import com.anyilanxin.cloud.oauth2common.enums.DataFormatType;
import com.anyilanxin.cloud.oauth2common.model.DataTypeModel;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.OAuth2Error;
import org.springframework.security.oauth2.core.OAuth2ErrorCodes;
import org.springframework.security.oauth2.core.endpoint.OAuth2ParameterNames;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationService;
import org.springframework.security.oauth2.server.authorization.authentication.OAuth2ClientAuthenticationToken;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.settings.ClientSettings;
import org.springframework.util.Assert;

import java.time.Instant;
import java.util.Objects;

/**
 * @author zxiaozhou
 * @copyright zhouxuanhong（https://anyilanxin.com）
 * @date 2023-05-25 18:20
 * @since 1.0.0
 */
@Slf4j
public final class AnYiClientSecretAuthenticationProvider implements AuthenticationProvider {
    private static final String ERROR_URI = "https://datatracker.ietf.org/doc/html/rfc6749#section-3.2.1";
    private final Log logger = LogFactory.getLog(getClass());
    private final RegisteredClientRepository registeredClientRepository;
    private final AnYiCodeVerifierAuthenticator codeVerifierAuthenticator;
    private PasswordEncoder passwordEncoder;

    /**
     * Constructs a {@code ClientSecretAuthenticationProvider} using the provided parameters.
     *
     * @param registeredClientRepository the repository of registered clients
     * @param authorizationService       the authorization service
     */
    public AnYiClientSecretAuthenticationProvider(RegisteredClientRepository registeredClientRepository,
                                                  OAuth2AuthorizationService authorizationService) {
        Assert.notNull(registeredClientRepository, "registeredClientRepository cannot be null");
        Assert.notNull(authorizationService, "authorizationService cannot be null");
        this.registeredClientRepository = registeredClientRepository;
        this.codeVerifierAuthenticator = new AnYiCodeVerifierAuthenticator(authorizationService);
        this.passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    /**
     * Sets the {@link PasswordEncoder} used to validate
     * the {@link RegisteredClient#getClientSecret() client secret}.
     * If not set, the client secret will be compared using
     * {@link PasswordEncoderFactories#createDelegatingPasswordEncoder()}.
     *
     * @param passwordEncoder the {@link PasswordEncoder} used to validate the client secret
     */
    public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
        Assert.notNull(passwordEncoder, "passwordEncoder cannot be null");
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        OAuth2ClientAuthenticationToken clientAuthentication = (OAuth2ClientAuthenticationToken) authentication;

        if (!ClientAuthenticationMethod.CLIENT_SECRET_BASIC.equals(clientAuthentication.getClientAuthenticationMethod()) &&
                !ClientAuthenticationMethod.CLIENT_SECRET_POST.equals(clientAuthentication.getClientAuthenticationMethod())) {
            return null;
        }

        String clientId = clientAuthentication.getPrincipal().toString();
        RegisteredClient registeredClient = this.registeredClientRepository.findByClientId(clientId);
        if (registeredClient == null) {
            throwInvalidClient(OAuth2ParameterNames.CLIENT_ID);
        }
        ClientSettings clientSettings = registeredClient.getClientSettings();
        if (Objects.nonNull(clientSettings)) {
            DataFormatType type = clientSettings.getSetting(AnYiSecurityConstants.DATA_FORMAT);
            DataTypeModel dataTypeModel = new DataTypeModel();
            dataTypeModel.setClientId(clientId);
            dataTypeModel.setType(type);
            HttpServletRequest request = ServletUtils.getRequest();
            if (Objects.nonNull(request)) {
                request.setAttribute(AnYiSecurityConstants.DATA_FORMAT, dataTypeModel);
            }

        }
        if (this.logger.isTraceEnabled()) {
            this.logger.trace("Retrieved registered client");
        }

        if (!registeredClient.getClientAuthenticationMethods().contains(
                clientAuthentication.getClientAuthenticationMethod())) {
            throwInvalidClient("authentication_method");
        }

        if (clientAuthentication.getCredentials() == null) {
            throwInvalidClient("credentials");
        }

        String clientSecret = clientAuthentication.getCredentials().toString();
        if (!this.passwordEncoder.matches(clientSecret, registeredClient.getClientSecret())) {
            throwInvalidClient(OAuth2ParameterNames.CLIENT_SECRET);
        }

        if (registeredClient.getClientSecretExpiresAt() != null &&
                Instant.now().isAfter(registeredClient.getClientSecretExpiresAt())) {
            throwInvalidClient("client_secret_expires_at");
        }

        if (this.passwordEncoder.upgradeEncoding(registeredClient.getClientSecret())) {
            registeredClient = RegisteredClient.from(registeredClient)
                    .clientSecret(this.passwordEncoder.encode(clientSecret))
                    .build();
            this.registeredClientRepository.save(registeredClient);
        }

        if (this.logger.isTraceEnabled()) {
            this.logger.trace("Validated client authentication parameters");
        }

        // Validate the "code_verifier" parameter for the confidential client, if available
        this.codeVerifierAuthenticator.authenticateIfAvailable(clientAuthentication, registeredClient);

        if (this.logger.isTraceEnabled()) {
            this.logger.trace("Authenticated client secret");
        }

        return new OAuth2ClientAuthenticationToken(registeredClient, clientAuthentication.getClientAuthenticationMethod(), clientAuthentication.getCredentials());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return OAuth2ClientAuthenticationToken.class.isAssignableFrom(authentication);
    }

    private static void throwInvalidClient(String parameterName) {
        OAuth2Error error = new OAuth2Error(
                OAuth2ErrorCodes.INVALID_CLIENT,
                "Client authentication failed: " + parameterName,
                ERROR_URI
        );
        throw new OAuth2AuthenticationException(error);
    }

}
