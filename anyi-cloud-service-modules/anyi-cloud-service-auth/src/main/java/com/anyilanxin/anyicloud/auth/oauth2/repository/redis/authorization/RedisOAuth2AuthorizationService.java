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

package com.anyilanxin.anyicloud.auth.oauth2.repository.redis.authorization;

import com.anyilanxin.anyicloud.auth.oauth2.repository.redis.AnYiOAuth2AuthorizationService;
import com.anyilanxin.anyicloud.auth.oauth2.repository.redis.jackson2.OAuth2AuthorizationModule;
import com.anyilanxin.anyicloud.oauth2common.store.IAuthStore;
import com.fasterxml.jackson.databind.Module;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.http.converter.json.SpringHandlerInstantiator;
import org.springframework.security.jackson2.SecurityJackson2Modules;
import org.springframework.security.oauth2.core.endpoint.OAuth2ParameterNames;
import org.springframework.security.oauth2.server.authorization.OAuth2Authorization;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationCode;
import org.springframework.security.oauth2.server.authorization.OAuth2TokenType;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.jackson2.OAuth2AuthorizationServerJackson2Module;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import java.time.Duration;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class RedisOAuth2AuthorizationService extends AnYiOAuth2AuthorizationService {

    private static final String ID_TO_AUTHORIZATION = "id_to_authorization:";

    private static final String STATE_TO_AUTHORIZATION = "state_to_authorization:";

    private static final String CODE_TO_AUTHORIZATION = "code_to_authorization:";

    private static final String ACCESS_TO_AUTHORIZATION = "access_to_authorization:";

    private static final String REFRESH_TO_AUTHORIZATION = "refresh_to_authorization:";

    private static final String ID_TO_CORRELATIONS = "id_to_correlations:";

    private static final String UID_TO_AUTHORIZATIONS = "uid_to_authorizations:";

    private static final String CID_TO_AUTHORIZATIONS = "cid_to_authorizations:";

    private final RedisOperations<String, String> redisOperations;

    private final RegisteredClientRepository clientRepository;


    public RedisOAuth2AuthorizationService(RedisOperations<String, String> redisOperations,
                                           final IAuthStore authStore,
                                           RegisteredClientRepository clientRepository,
                                           AutowireCapableBeanFactory beanFactory) {
        super(authStore, clientRepository);
        Assert.notNull(redisOperations, "redisOperations mut not be null");
        this.redisOperations = redisOperations;
        this.clientRepository = clientRepository;

        ClassLoader classLoader = this.getClass().getClassLoader();
        List<Module> securityModules = SecurityJackson2Modules.getModules(classLoader);
        objectMapper.registerModules(securityModules);
        objectMapper.registerModule(new OAuth2AuthorizationServerJackson2Module());
        objectMapper.registerModule(new OAuth2AuthorizationModule());
        objectMapper.setHandlerInstantiator(new SpringHandlerInstantiator(beanFactory));
    }

    @Override
    public void save(OAuth2Authorization authorization) {
        final String clientId = authorization.getRegisteredClientId();
        RegisteredClient registeredClient = clientRepository.findById(clientId);
        Assert.notNull(registeredClient, "Registered client must not be null");
        Duration codeTtl = registeredClient.getTokenSettings().getAuthorizationCodeTimeToLive();
        Duration accessTokenTtl = registeredClient.getTokenSettings().getAccessTokenTimeToLive();
        Duration refreshTokenTtl = registeredClient.getTokenSettings().getRefreshTokenTimeToLive();
        Duration stateTtl = codeTtl;
        Duration max = authorization.getRefreshToken() == null ?
                accessTokenTtl : Collections.max(Arrays.asList(accessTokenTtl, refreshTokenTtl));
        Duration authorizationTtl = max;
        Duration correlationsTtl = max;
        Duration uidTtl = max;
        Duration cidTtl = max;

        final String authorizationId = authorization.getId();
        final String idToAuthorizationKey = getIdToAuthorizationKey(authorizationId);
        final String cidToAuthorizationsKey = getCidToAuthorizations(clientId);

        redisOperations.opsForValue().set(idToAuthorizationKey, write(authorization),
                authorizationTtl.getSeconds(), TimeUnit.SECONDS);

        redisOperations.opsForSet().add(cidToAuthorizationsKey, authorizationId);
        redisOperations.expire(cidToAuthorizationsKey, cidTtl);

        Set<String> correlationValues = new HashSet<>();
        Optional.ofNullable(authorization.getAttribute(OAuth2ParameterNames.STATE)).ifPresent(token -> {
            final String stateToAuthorizationKey = getStateToAuthorization((String) token);
            redisOperations.opsForValue().set(stateToAuthorizationKey, authorizationId,
                    stateTtl.getSeconds(), TimeUnit.SECONDS);
            correlationValues.add(stateToAuthorizationKey);
        });
        Optional.ofNullable(authorization.getToken(OAuth2AuthorizationCode.class)).ifPresent(token -> {
            final String codeToAuthorizationKey = getCodeToAuthorization(token.getToken().getTokenValue());
            redisOperations.opsForValue().set(codeToAuthorizationKey, authorizationId,
                    codeTtl.getSeconds(), TimeUnit.SECONDS);
            correlationValues.add(codeToAuthorizationKey);
        });
        Optional.ofNullable(authorization.getAccessToken()).ifPresent(token -> {
            final String accessToAuthorization = getAccessToAuthorization(token.getToken().getTokenValue());
            redisOperations.opsForValue().set(accessToAuthorization, authorizationId,
                    accessTokenTtl.getSeconds(), TimeUnit.SECONDS);
            correlationValues.add(accessToAuthorization);
        });
        Optional.ofNullable(authorization.getRefreshToken()).ifPresent(token -> {
            final String refreshToAuthorization = getRefreshToAuthorization(token.getToken().getTokenValue());
            redisOperations.opsForValue().set(refreshToAuthorization, authorizationId,
                    refreshTokenTtl.getSeconds(), TimeUnit.SECONDS);
            correlationValues.add(refreshToAuthorization);
        });
        if (!CollectionUtils.isEmpty(correlationValues)) {
            redisOperations.opsForSet().add(getIdToCorrelations(authorizationId), correlationValues.toArray(String[]::new));
            redisOperations.expire(getIdToCorrelations(authorizationId), correlationsTtl);
        }
        super.save(authorization);
    }

    @Override
    public void remove(OAuth2Authorization authorization) {
        List<String> keysToRemove = new ArrayList<>();
        keysToRemove.add(getIdToAuthorizationKey(authorization.getId()));
        keysToRemove.add(getIdToCorrelations(authorization.getId()));
        Optional.ofNullable(redisOperations.opsForSet().members(getIdToCorrelations(authorization.getId())))
                .ifPresent(keysToRemove::addAll);
        redisOperations.delete(keysToRemove);

        final String clientId = authorization.getRegisteredClientId();
        redisOperations.opsForSet().remove(getCidToAuthorizations(clientId), authorization.getId());
        super.remove(authorization);
    }

    @Override
    public OAuth2Authorization findById(String id) {
        return Optional.ofNullable(redisOperations.opsForValue().get(getIdToAuthorizationKey(id))).map(this::parse)
                .orElse(null);
    }

    @Override
    public OAuth2Authorization findByToken(String token, OAuth2TokenType tokenType) {
        Assert.hasText(token, "token cannot be empty");
        if (tokenType == null) {
            return Optional.ofNullable(redisOperations.opsForValue().get(getStateToAuthorization(token)))
                    .or(() -> Optional.ofNullable(redisOperations.opsForValue().get(getCodeToAuthorization(token))))
                    .or(() -> Optional.ofNullable(redisOperations.opsForValue().get(getAccessToAuthorization(token))))
                    .or(() -> Optional.ofNullable(redisOperations.opsForValue().get(getRefreshToAuthorization(token))))
                    .map(this::findById).orElse(null);
        } else if (OAuth2ParameterNames.STATE.equals(tokenType.getValue())) {
            return Optional.ofNullable(redisOperations.opsForValue().get(getStateToAuthorization(token)))
                    .map(this::findById).orElse(null);
        } else if (OAuth2ParameterNames.CODE.equals(tokenType.getValue())) {
            return Optional.ofNullable(redisOperations.opsForValue().get(getCodeToAuthorization(token)))
                    .map(this::findById).orElse(null);
        } else if (OAuth2TokenType.ACCESS_TOKEN.equals(tokenType)) {
            return Optional.ofNullable(redisOperations.opsForValue().get(getAccessToAuthorization(token)))
                    .map(this::findById).orElse(null);
        } else if (OAuth2TokenType.REFRESH_TOKEN.equals(tokenType)) {
            return Optional.ofNullable(redisOperations.opsForValue().get(getRefreshToAuthorization(token)))
                    .map(this::findById).orElse(null);
        }
        return null;
    }

    private String getIdToAuthorizationKey(String authorizationId) {
        return prefix + ID_TO_AUTHORIZATION + authorizationId;
    }

    private String getStateToAuthorization(String state) {
        return prefix + STATE_TO_AUTHORIZATION + generateKey(state);
    }

    private String getCodeToAuthorization(String code) {
        return prefix + CODE_TO_AUTHORIZATION + generateKey(code);
    }

    private String getAccessToAuthorization(String accessToken) {
        return prefix + ACCESS_TO_AUTHORIZATION + generateKey(accessToken);
    }

    private String getRefreshToAuthorization(String refreshToken) {
        return prefix + REFRESH_TO_AUTHORIZATION + generateKey(refreshToken);
    }

    private String getIdToCorrelations(String authorizationId) {
        return prefix + ID_TO_CORRELATIONS + authorizationId;
    }

    public String getCidToAuthorizations(String clientId) {
        return prefix + CID_TO_AUTHORIZATIONS + clientId;
    }


}
