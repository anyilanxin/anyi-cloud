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

package com.anyilanxin.cloud.auth.oauth2.repository.redis;

import com.anyilanxin.cloud.oauth2common.authinfo.AnYiAuthClientModel;
import com.anyilanxin.cloud.oauth2common.authinfo.AnYiAuthClientSettingModel;
import com.anyilanxin.cloud.oauth2common.authinfo.AnYiAuthTokenSettingModel;
import com.anyilanxin.cloud.oauth2common.authinfo.AnYiUserDetails;
import com.anyilanxin.cloud.oauth2common.constant.AnYiSecurityConstants;
import com.anyilanxin.cloud.oauth2common.store.AnYiAuthentication;
import com.anyilanxin.cloud.oauth2common.store.IAuthStore;
import com.anyilanxin.cloud.oauth2common.store.jackson2.OAuth2AuthorizationModule;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Setter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.jackson2.SecurityJackson2Modules;
import org.springframework.security.oauth2.server.authorization.OAuth2Authorization;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationCode;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationService;
import org.springframework.security.oauth2.server.authorization.OAuth2TokenType;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.settings.ClientSettings;
import org.springframework.security.oauth2.server.authorization.settings.TokenSettings;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.Duration;
import java.util.*;

/**
 * @author zxh
 * @date 2023-09-26 09:27
 * @since 1.0.0
 */
public abstract class AnYiOAuth2AuthorizationService implements OAuth2AuthorizationService {
    private final IAuthStore authStore;
    private final RegisteredClientRepository clientRepository;
    private static final String AUTH_PRINCIPAL_INFO = "auth_principal_info:";
    private static final MessageDigest DIGEST;
    @Setter
    protected String prefix = "";

    @Setter
    protected ObjectMapper objectMapper = new ObjectMapper();

    static {
        try {
            DIGEST = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }


    protected String write(Object data) {
        try {
            return this.objectMapper.writeValueAsString(data);
        } catch (Exception ex) {
            throw new IllegalArgumentException(ex.getMessage(), ex);
        }
    }

    protected OAuth2Authorization parse(String data) {
        try {
            return this.objectMapper.readValue(data, new TypeReference<>() {
            });
        } catch (Exception ex) {
            throw new IllegalArgumentException(ex.getMessage(), ex);
        }
    }


    public AnYiOAuth2AuthorizationService(final IAuthStore authStore, final RegisteredClientRepository clientRepository) {
        this.authStore = authStore;
        this.clientRepository = clientRepository;
        ClassLoader classLoader = this.getClass().getClassLoader();
        List<Module> securityModules = SecurityJackson2Modules.getModules(classLoader);
        objectMapper.registerModules(securityModules);
        objectMapper.registerModules(new OAuth2AuthorizationModule());
    }

    private String getIdToAuthPrincipalKey(String token) {
        return prefix + AUTH_PRINCIPAL_INFO + generateKey(token);
    }

    protected static String generateKey(String rawKey) {
        byte[] bytes = DIGEST.digest(rawKey.getBytes(StandardCharsets.UTF_8));
        return String.format("%032x", new BigInteger(1, bytes));
    }

    @Override
    public void save(OAuth2Authorization authorization) {
        String tokenValue;
        if (Objects.nonNull(authorization.getAccessToken())) {
            tokenValue = authorization.getAccessToken().getToken().getTokenValue();
        } else {
            OAuth2Authorization.Token<OAuth2AuthorizationCode> token = authorization.getToken(OAuth2AuthorizationCode.class);
            tokenValue = token.getToken().getTokenValue();
        }
        final String clientId = authorization.getRegisteredClientId();
        RegisteredClient registeredClient = clientRepository.findById(clientId);
        AnYiAuthClientModel authClientModel = new AnYiAuthClientModel();
        authClientModel.setId(registeredClient.getId());
        authClientModel.setClientId(registeredClient.getClientId());
        authClientModel.setScopeInfos(new ArrayList<>(registeredClient.getScopes()));
        ClientSettings clientSettings = registeredClient.getClientSettings();
        TokenSettings tokenSettings = registeredClient.getTokenSettings();
        AnYiAuthClientSettingModel clientSetting = new AnYiAuthClientSettingModel();
        clientSetting.setJwkSetUrl(clientSetting.getJwkSetUrl());
        clientSetting.setLimitResource(clientSettings.getSetting(AnYiSecurityConstants.LIMIT_RESOURCE));
        clientSetting.setResourceIds(new ArrayList<>(clientSettings.getSetting(AnYiSecurityConstants.RESOURCE_IDS)));
        AnYiAuthTokenSettingModel tokenSetting = new AnYiAuthTokenSettingModel();
        tokenSetting.setAccessTokenTimeToLive(tokenSettings.getAccessTokenTimeToLive().toSeconds());
        tokenSetting.setRefreshTokenTimeToLive(tokenSettings.getAccessTokenTimeToLive().toSeconds());
        tokenSetting.setAuthorizationCodeTimeToLive(tokenSettings.getAuthorizationCodeTimeToLive().toSeconds());
        tokenSetting.setDeviceCodeTimeToLive(tokenSetting.getDeviceCodeTimeToLive());

        authClientModel.setClientSetting(clientSetting);
        authClientModel.setTokenSetting(tokenSetting);

        AnYiAuthentication authentication = new AnYiAuthentication(Collections.emptyList());
        Object attribute = authorization.getAttribute("java.security.Principal");
        if (attribute instanceof AbstractAuthenticationToken token) {
            String name = clientId;
            Collection<? extends GrantedAuthority> authorities = Collections.emptyList();
            if (token.getPrincipal() instanceof AnYiUserDetails userDetails) {
                name = userDetails.getUserId();
                authorities = userDetails.getAuthorities();
            }
            authentication = new AnYiAuthentication(authorities);
            authentication.setPrincipal(token.getPrincipal());
            authentication.setName(name);
        }
        authentication.setAuthenticated(true);
        authentication.setDetails(authentication.getDetails());
        authentication.setAuthorizedScopes(new ArrayList<>(authorization.getAuthorizedScopes()));
        authentication.setClientInfo(authClientModel);
        authentication.setAuthorizationGrantType(authorization.getAuthorizationGrantType());

        Duration refreshTokenTtl = registeredClient.getTokenSettings().getRefreshTokenTimeToLive();
        Duration accessTokenTtl = registeredClient.getTokenSettings().getAccessTokenTimeToLive();
        Duration max = authorization.getRefreshToken() == null ?
                accessTokenTtl : Collections.max(Arrays.asList(accessTokenTtl, refreshTokenTtl));
        authStore.saveAuthentication(tokenValue, max, authentication);
    }


    @Override
    public void remove(OAuth2Authorization authorization) {
        authStore.remove(authorization.getAccessToken().getToken().getTokenValue());
    }


    @Override
    public abstract OAuth2Authorization findById(String id);


    @Override
    public abstract OAuth2Authorization findByToken(String token, OAuth2TokenType tokenType);
}
