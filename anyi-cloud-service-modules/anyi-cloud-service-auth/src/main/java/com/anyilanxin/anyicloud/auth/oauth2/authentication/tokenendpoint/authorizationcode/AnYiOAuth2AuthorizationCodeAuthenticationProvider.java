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

package com.anyilanxin.anyicloud.auth.oauth2.authentication.tokenendpoint.authorizationcode;

import com.anyilanxin.anyicloud.auth.utils.AnYiOAuth2AuthenticationProviderUtils;
import com.anyilanxin.anyicloud.oauth2common.exception.AnYiParamValidExpiredException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.core.log.LogMessage;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.session.SessionInformation;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.oauth2.core.*;
import org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationRequest;
import org.springframework.security.oauth2.core.endpoint.OAuth2ParameterNames;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.core.oidc.OidcScopes;
import org.springframework.security.oauth2.core.oidc.endpoint.OidcParameterNames;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.authorization.OAuth2Authorization;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationCode;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationService;
import org.springframework.security.oauth2.server.authorization.OAuth2TokenType;
import org.springframework.security.oauth2.server.authorization.authentication.OAuth2AccessTokenAuthenticationToken;
import org.springframework.security.oauth2.server.authorization.authentication.OAuth2AuthorizationCodeAuthenticationToken;
import org.springframework.security.oauth2.server.authorization.authentication.OAuth2ClientAuthenticationToken;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.context.AuthorizationServerContextHolder;
import org.springframework.security.oauth2.server.authorization.token.DefaultOAuth2TokenContext;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenContext;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenGenerator;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.Principal;
import java.util.*;

/**
 * @author zxiaozhou
 * @copyright zhouxuanhong（https://anyilanxin.com）
 * @date 2023-05-27 06:11
 * @since 1.0.0
 */
public class AnYiOAuth2AuthorizationCodeAuthenticationProvider implements AuthenticationProvider {
    private static final String ERROR_URI = "https://datatracker.ietf.org/doc/html/rfc6749#section-5.2";
    private static final OAuth2TokenType AUTHORIZATION_CODE_TOKEN_TYPE =
            new OAuth2TokenType(OAuth2ParameterNames.CODE);
    private static final OAuth2TokenType ID_TOKEN_TOKEN_TYPE =
            new OAuth2TokenType(OidcParameterNames.ID_TOKEN);
    private final Log logger = LogFactory.getLog(getClass());
    private final OAuth2AuthorizationService authorizationService;
    private final OAuth2TokenGenerator<? extends OAuth2Token> tokenGenerator;
    private SessionRegistry sessionRegistry;

    /**
     * Constructs an {@code OAuth2AuthorizationCodeAuthenticationProvider} using the provided parameters.
     *
     * @param authorizationService the authorization service
     * @param tokenGenerator       the token generator
     * @since 0.2.3
     */
    public AnYiOAuth2AuthorizationCodeAuthenticationProvider(OAuth2AuthorizationService authorizationService,
                                                             OAuth2TokenGenerator<? extends OAuth2Token> tokenGenerator) {
        Assert.notNull(authorizationService, "authorizationService cannot be null");
        Assert.notNull(tokenGenerator, "tokenGenerator cannot be null");
        this.authorizationService = authorizationService;
        this.tokenGenerator = tokenGenerator;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        OAuth2AuthorizationCodeAuthenticationToken authorizationCodeAuthentication =
                (OAuth2AuthorizationCodeAuthenticationToken) authentication;

        OAuth2ClientAuthenticationToken clientPrincipal = AnYiOAuth2AuthenticationProviderUtils.getAuthenticatedClientElseThrowInvalidClient(authorizationCodeAuthentication);
        RegisteredClient registeredClient = clientPrincipal.getRegisteredClient();
        if (this.logger.isTraceEnabled()) {
            this.logger.trace("Retrieved registered client");
        }

        OAuth2Authorization authorization = this.authorizationService.findByToken(
                authorizationCodeAuthentication.getCode(), AUTHORIZATION_CODE_TOKEN_TYPE);
        if (authorization == null) {
            throw new AnYiParamValidExpiredException(OAuth2ErrorCodes.INVALID_GRANT);
        }

        if (this.logger.isTraceEnabled()) {
            this.logger.trace("Retrieved authorization with authorization code");
        }

        OAuth2Authorization.Token<OAuth2AuthorizationCode> authorizationCode =
                authorization.getToken(OAuth2AuthorizationCode.class);

        OAuth2AuthorizationRequest authorizationRequest = authorization.getAttribute(
                OAuth2AuthorizationRequest.class.getName());

        if (!registeredClient.getClientId().equals(authorizationRequest.getClientId())) {
            if (!authorizationCode.isInvalidated()) {
                // Invalidate the authorization code given that a different client is attempting to use it
                authorization = AnYiOAuth2AuthenticationProviderUtils.invalidate(authorization, authorizationCode.getToken());
                this.authorizationService.save(authorization);
                if (this.logger.isWarnEnabled()) {
                    this.logger.warn(LogMessage.format("Invalidated authorization code used by registered client '%s'", registeredClient.getId()));
                }
            }
            throw new OAuth2AuthenticationException(OAuth2ErrorCodes.INVALID_GRANT);
        }

        if (StringUtils.hasText(authorizationRequest.getRedirectUri()) &&
                !authorizationRequest.getRedirectUri().equals(authorizationCodeAuthentication.getRedirectUri())) {
            throw new OAuth2AuthenticationException(OAuth2ErrorCodes.INVALID_GRANT);
        }

        if (!authorizationCode.isActive()) {
            if (authorizationCode.isInvalidated()) {
                OAuth2Token token = authorization.getRefreshToken() != null ?
                        authorization.getRefreshToken().getToken() :
                        authorization.getAccessToken().getToken();
                if (token != null) {
                    // Invalidate the access (and refresh) token as the client is attempting to use the authorization code more than once
                    authorization = AnYiOAuth2AuthenticationProviderUtils.invalidate(authorization, token);
                    this.authorizationService.save(authorization);
                    if (this.logger.isWarnEnabled()) {
                        this.logger.warn(LogMessage.format("Invalidated authorization token(s) previously issued to registered client '%s'", registeredClient.getId()));
                    }
                }
            }
            throw new OAuth2AuthenticationException(OAuth2ErrorCodes.INVALID_GRANT);
        }

        if (this.logger.isTraceEnabled()) {
            this.logger.trace("Validated token request parameters");
        }

        Authentication principal = authorization.getAttribute(Principal.class.getName());

        // @formatter:off
        DefaultOAuth2TokenContext.Builder tokenContextBuilder = DefaultOAuth2TokenContext.builder()
                .registeredClient(registeredClient)
                .principal(principal)
                .put("id",authorization.getId())
                .authorizationServerContext(AuthorizationServerContextHolder.getContext())
                .authorization(authorization)
                .authorizedScopes(authorization.getAuthorizedScopes())
                .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
                .authorizationGrant(authorizationCodeAuthentication);
        // @formatter:on

        OAuth2Authorization.Builder authorizationBuilder = OAuth2Authorization.from(authorization);

        // ----- Access token -----
        OAuth2TokenContext tokenContext = tokenContextBuilder.tokenType(OAuth2TokenType.ACCESS_TOKEN).build();
        OAuth2Token generatedAccessToken = this.tokenGenerator.generate(tokenContext);
        if (generatedAccessToken == null) {
            OAuth2Error error = new OAuth2Error(OAuth2ErrorCodes.SERVER_ERROR,
                    "The token generator failed to generate the access token.", ERROR_URI);
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
        if (registeredClient.getAuthorizationGrantTypes().contains(AuthorizationGrantType.REFRESH_TOKEN) &&
                // Do not issue refresh token to public client
                !clientPrincipal.getClientAuthenticationMethod().equals(ClientAuthenticationMethod.NONE)) {

            tokenContext = tokenContextBuilder.tokenType(OAuth2TokenType.REFRESH_TOKEN).build();
            OAuth2Token generatedRefreshToken = this.tokenGenerator.generate(tokenContext);
            if (!(generatedRefreshToken instanceof OAuth2RefreshToken)) {
                OAuth2Error error = new OAuth2Error(OAuth2ErrorCodes.SERVER_ERROR,
                        "The token generator failed to generate the refresh token.", ERROR_URI);
                throw new OAuth2AuthenticationException(error);
            }

            if (this.logger.isTraceEnabled()) {
                this.logger.trace("Generated refresh token");
            }

            refreshToken = (OAuth2RefreshToken) generatedRefreshToken;
            authorizationBuilder.refreshToken(refreshToken);
        }

        // ----- ID token -----
        OidcIdToken idToken;
        if (authorizationRequest.getScopes().contains(OidcScopes.OPENID)) {
            SessionInformation sessionInformation = getSessionInformation(principal);
            if (sessionInformation != null) {
                try {
                    // Compute (and use) hash for Session ID
                    sessionInformation = new SessionInformation(sessionInformation.getPrincipal(),
                            createHash(sessionInformation.getSessionId()), sessionInformation.getLastRequest());
                } catch (NoSuchAlgorithmException ex) {
                    OAuth2Error error = new OAuth2Error(OAuth2ErrorCodes.SERVER_ERROR,
                            "Failed to compute hash for Session ID.", ERROR_URI);
                    throw new OAuth2AuthenticationException(error);
                }
                tokenContextBuilder.put(SessionInformation.class, sessionInformation);
            }
            // @formatter:off
            tokenContext = tokenContextBuilder
                    .tokenType(ID_TOKEN_TOKEN_TYPE)
                    .authorization(authorizationBuilder.build())	// ID token customizer may need access to the access token and/or refresh token
                    .build();
            // @formatter:on
            OAuth2Token generatedIdToken = this.tokenGenerator.generate(tokenContext);
            if (!(generatedIdToken instanceof Jwt)) {
                OAuth2Error error = new OAuth2Error(OAuth2ErrorCodes.SERVER_ERROR,
                        "The token generator failed to generate the ID token.", ERROR_URI);
                throw new OAuth2AuthenticationException(error);
            }

            if (this.logger.isTraceEnabled()) {
                this.logger.trace("Generated id token");
            }

            idToken = new OidcIdToken(generatedIdToken.getTokenValue(), generatedIdToken.getIssuedAt(),
                    generatedIdToken.getExpiresAt(), ((Jwt) generatedIdToken).getClaims());
            authorizationBuilder.token(idToken, (metadata) ->
                    metadata.put(OAuth2Authorization.Token.CLAIMS_METADATA_NAME, idToken.getClaims()));
        } else {
            idToken = null;
        }

        authorization = authorizationBuilder.build();

        // Invalidate the authorization code as it can only be used once
        authorization = AnYiOAuth2AuthenticationProviderUtils.invalidate(authorization, authorizationCode.getToken());

        this.authorizationService.save(authorization);

        if (this.logger.isTraceEnabled()) {
            this.logger.trace("Saved authorization");
        }

        Map<String, Object> additionalParameters = Collections.emptyMap();
        if (idToken != null) {
            additionalParameters = new HashMap<>();
            additionalParameters.put(OidcParameterNames.ID_TOKEN, idToken.getTokenValue());
        }

        if (this.logger.isTraceEnabled()) {
            this.logger.trace("Authenticated token request");
        }

        return new OAuth2AccessTokenAuthenticationToken(
                registeredClient, clientPrincipal, accessToken, refreshToken, additionalParameters);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return OAuth2AuthorizationCodeAuthenticationToken.class.isAssignableFrom(authentication);
    }

    /**
     * Sets the {@link SessionRegistry} used to track OpenID Connect sessions.
     *
     * @param sessionRegistry the {@link SessionRegistry} used to track OpenID Connect sessions
     * @since 1.1
     */
    public void setSessionRegistry(SessionRegistry sessionRegistry) {
        Assert.notNull(sessionRegistry, "sessionRegistry cannot be null");
        this.sessionRegistry = sessionRegistry;
    }

    private SessionInformation getSessionInformation(Authentication principal) {
        SessionInformation sessionInformation = null;
        if (this.sessionRegistry != null) {
            List<SessionInformation> sessions = this.sessionRegistry.getAllSessions(principal.getPrincipal(), false);
            if (!CollectionUtils.isEmpty(sessions)) {
                sessionInformation = sessions.get(0);
                if (sessions.size() > 1) {
                    // Get the most recent session
                    sessions = new ArrayList<>(sessions);
                    sessions.sort(Comparator.comparing(SessionInformation::getLastRequest));
                    sessionInformation = sessions.get(sessions.size() - 1);
                }
            }
        }
        return sessionInformation;
    }

    private static String createHash(String value) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        byte[] digest = md.digest(value.getBytes(StandardCharsets.US_ASCII));
        return Base64.getUrlEncoder().withoutPadding().encodeToString(digest);
    }

}
