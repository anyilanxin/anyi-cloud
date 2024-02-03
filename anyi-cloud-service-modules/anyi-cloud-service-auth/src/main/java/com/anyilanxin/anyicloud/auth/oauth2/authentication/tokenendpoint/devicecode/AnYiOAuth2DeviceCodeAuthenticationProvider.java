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

package com.anyilanxin.anyicloud.auth.oauth2.authentication.tokenendpoint.devicecode;

import com.anyilanxin.anyicloud.auth.utils.AnYiOAuth2AuthenticationProviderUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.core.log.LogMessage;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.core.*;
import org.springframework.security.oauth2.core.endpoint.OAuth2ParameterNames;
import org.springframework.security.oauth2.server.authorization.OAuth2Authorization;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationService;
import org.springframework.security.oauth2.server.authorization.OAuth2TokenType;
import org.springframework.security.oauth2.server.authorization.authentication.OAuth2AccessTokenAuthenticationToken;
import org.springframework.security.oauth2.server.authorization.authentication.OAuth2ClientAuthenticationToken;
import org.springframework.security.oauth2.server.authorization.authentication.OAuth2DeviceCodeAuthenticationToken;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.context.AuthorizationServerContextHolder;
import org.springframework.security.oauth2.server.authorization.token.DefaultOAuth2TokenContext;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenContext;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenGenerator;
import org.springframework.util.Assert;

import java.security.Principal;

/**
 * @author zxiaozhou
 * @copyright zhouxuanhong（https://anyilanxin.com）
 * @date 2023-05-27 06:06
 * @since 1.0.0
 */
public class AnYiOAuth2DeviceCodeAuthenticationProvider implements AuthenticationProvider {

    private static final String DEFAULT_ERROR_URI = "https://datatracker.ietf.org/doc/html/rfc6749#section-5.2";
    private static final String DEVICE_ERROR_URI = "https://datatracker.ietf.org/doc/html/rfc8628#section-3.5";
    static final OAuth2TokenType DEVICE_CODE_TOKEN_TYPE = new OAuth2TokenType(OAuth2ParameterNames.DEVICE_CODE);
    static final String EXPIRED_TOKEN = "expired_token";
    static final String AUTHORIZATION_PENDING = "authorization_pending";

    private final Log logger = LogFactory.getLog(getClass());
    private final OAuth2AuthorizationService authorizationService;
    private final OAuth2TokenGenerator<? extends OAuth2Token> tokenGenerator;

    /**
     * Constructs an {@code OAuth2DeviceCodeAuthenticationProvider} using the provided parameters.
     *
     * @param authorizationService the authorization service
     * @param tokenGenerator       the token generator
     */
    public AnYiOAuth2DeviceCodeAuthenticationProvider(
            OAuth2AuthorizationService authorizationService,
            OAuth2TokenGenerator<? extends OAuth2Token> tokenGenerator) {
        Assert.notNull(authorizationService, "authorizationService cannot be null");
        Assert.notNull(tokenGenerator, "tokenGenerator cannot be null");
        this.authorizationService = authorizationService;
        this.tokenGenerator = tokenGenerator;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        OAuth2DeviceCodeAuthenticationToken deviceCodeAuthentication =
                (OAuth2DeviceCodeAuthenticationToken) authentication;

        OAuth2ClientAuthenticationToken clientPrincipal =
                AnYiOAuth2AuthenticationProviderUtils.getAuthenticatedClientElseThrowInvalidClient(deviceCodeAuthentication);
        RegisteredClient registeredClient = clientPrincipal.getRegisteredClient();

        if (this.logger.isTraceEnabled()) {
            this.logger.trace("Retrieved registered client");
        }

        OAuth2Authorization authorization = this.authorizationService.findByToken(
                deviceCodeAuthentication.getDeviceCode(), DEVICE_CODE_TOKEN_TYPE);
        if (authorization == null) {
            throw new OAuth2AuthenticationException(OAuth2ErrorCodes.INVALID_GRANT);
        }

        if (this.logger.isTraceEnabled()) {
            this.logger.trace("Retrieved authorization with device code");
        }

        OAuth2Authorization.Token<OAuth2UserCode> userCode = authorization.getToken(OAuth2UserCode.class);
        OAuth2Authorization.Token<OAuth2DeviceCode> deviceCode = authorization.getToken(OAuth2DeviceCode.class);

        if (!registeredClient.getId().equals(authorization.getRegisteredClientId())) {
            if (!deviceCode.isInvalidated()) {
                // Invalidate the device code given that a different client is attempting to use it
                authorization = AnYiOAuth2AuthenticationProviderUtils.invalidate(authorization, deviceCode.getToken());
                this.authorizationService.save(authorization);
                if (this.logger.isWarnEnabled()) {
                    this.logger.warn(LogMessage.format(
                            "Invalidated device code used by registered client '%s'", authorization.getRegisteredClientId()));
                }
            }
            throw new OAuth2AuthenticationException(OAuth2ErrorCodes.INVALID_GRANT);
        }

        // In https://www.rfc-editor.org/rfc/rfc8628.html#section-3.5,
        // the following error codes are defined:

        //   authorization_pending
        //      The authorization request is still pending as the end user hasn't
        //      yet completed the user-interaction steps (Section 3.3).  The
        //      client SHOULD repeat the access token request to the token
        //      endpoint (a process known as polling).  Before each new request,
        //      the client MUST wait at least the number of seconds specified by
        //      the "interval" parameter of the device authorization response (see
        //      Section 3.2), or 5 seconds if none was provided, and respect any
        //      increase in the polling interval required by the "slow_down"
        //      error.
        if (!userCode.isInvalidated()) {
            OAuth2Error error = new OAuth2Error(AUTHORIZATION_PENDING, null, DEVICE_ERROR_URI);
            throw new OAuth2AuthenticationException(error);
        }

        //   slow_down
        //      A variant of "authorization_pending", the authorization request is
        //      still pending and polling should continue, but the interval MUST
        //      be increased by 5 seconds for this and all subsequent requests.
        //	NOTE: This error is not handled in the framework.

        //   access_denied
        //      The authorization request was denied.
        if (deviceCode.isInvalidated()) {
            OAuth2Error error = new OAuth2Error(OAuth2ErrorCodes.ACCESS_DENIED, null, DEVICE_ERROR_URI);
            throw new OAuth2AuthenticationException(error);
        }

        //   expired_token
        //      The "device_code" has expired, and the device authorization
        //      session has concluded.  The client MAY commence a new device
        //      authorization request but SHOULD wait for user interaction before
        //      restarting to avoid unnecessary polling.
        if (deviceCode.isExpired()) {
            // Invalidate the device code
            authorization = AnYiOAuth2AuthenticationProviderUtils.invalidate(authorization, deviceCode.getToken());
            this.authorizationService.save(authorization);
            if (this.logger.isWarnEnabled()) {
                this.logger.warn(LogMessage.format(
                        "Invalidated device code used by registered client '%s'", authorization.getRegisteredClientId()));
            }
            OAuth2Error error = new OAuth2Error(EXPIRED_TOKEN, null, DEVICE_ERROR_URI);
            throw new OAuth2AuthenticationException(error);
        }

        if (this.logger.isTraceEnabled()) {
            this.logger.trace("Validated device token request parameters");
        }

        // @formatter:off
        DefaultOAuth2TokenContext.Builder tokenContextBuilder = DefaultOAuth2TokenContext.builder()
                .registeredClient(registeredClient)
                .put("id",authorization.getId())
                .principal(authorization.getAttribute(Principal.class.getName()))
                .authorizationServerContext(AuthorizationServerContextHolder.getContext())
                .authorization(authorization)
                .authorizedScopes(authorization.getAuthorizedScopes())
                .authorizationGrantType(AuthorizationGrantType.DEVICE_CODE)
                .authorizationGrant(deviceCodeAuthentication);
        // @formatter:on

        // @formatter:off
        OAuth2Authorization.Builder authorizationBuilder = OAuth2Authorization.from(authorization)
                // Invalidate the device code as it can only be used (successfully) once
                .token(deviceCode.getToken(), metadata ->
                        metadata.put(OAuth2Authorization.Token.INVALIDATED_METADATA_NAME, true));
        // @formatter:on

        // ----- Access token -----
        OAuth2TokenContext tokenContext = tokenContextBuilder.tokenType(OAuth2TokenType.ACCESS_TOKEN).build();
        OAuth2Token generatedAccessToken = this.tokenGenerator.generate(tokenContext);
        if (generatedAccessToken == null) {
            OAuth2Error error = new OAuth2Error(OAuth2ErrorCodes.SERVER_ERROR,
                    "The token generator failed to generate the access token.", DEFAULT_ERROR_URI);
            throw new OAuth2AuthenticationException(error);
        }

        if (this.logger.isTraceEnabled()) {
            this.logger.trace("Generated access token");
        }

        OAuth2AccessToken accessToken = new OAuth2AccessToken(OAuth2AccessToken.TokenType.BEARER,
                generatedAccessToken.getTokenValue(), generatedAccessToken.getIssuedAt(),
                generatedAccessToken.getExpiresAt(), tokenContext.getAuthorizedScopes());
        if (generatedAccessToken instanceof ClaimAccessor) {
            authorizationBuilder.token(accessToken, (metadata) ->
                    metadata.put(OAuth2Authorization.Token.CLAIMS_METADATA_NAME, ((ClaimAccessor) generatedAccessToken).getClaims()));
        } else {
            authorizationBuilder.accessToken(accessToken);
        }

        // ----- Refresh token -----
        OAuth2RefreshToken refreshToken = null;
        if (registeredClient.getAuthorizationGrantTypes().contains(AuthorizationGrantType.REFRESH_TOKEN)) {
            tokenContext = tokenContextBuilder.tokenType(OAuth2TokenType.REFRESH_TOKEN).build();
            OAuth2Token generatedRefreshToken = this.tokenGenerator.generate(tokenContext);
            if (!(generatedRefreshToken instanceof OAuth2RefreshToken)) {
                OAuth2Error error = new OAuth2Error(OAuth2ErrorCodes.SERVER_ERROR,
                        "The token generator failed to generate the refresh token.", DEFAULT_ERROR_URI);
                throw new OAuth2AuthenticationException(error);
            }

            if (this.logger.isTraceEnabled()) {
                this.logger.trace("Generated refresh token");
            }

            refreshToken = (OAuth2RefreshToken) generatedRefreshToken;
            authorizationBuilder.refreshToken(refreshToken);
        }

        authorization = authorizationBuilder.build();

        this.authorizationService.save(authorization);

        if (this.logger.isTraceEnabled()) {
            this.logger.trace("Saved authorization");
        }

        if (this.logger.isTraceEnabled()) {
            this.logger.trace("Authenticated device token request");
        }

        return new OAuth2AccessTokenAuthenticationToken(registeredClient, clientPrincipal, accessToken, refreshToken);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return OAuth2DeviceCodeAuthenticationToken.class.isAssignableFrom(authentication);
    }

}
