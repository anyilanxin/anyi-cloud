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

package com.anyilanxin.cloud.oauth2common.store.impl;

import com.anyilanxin.cloud.oauth2common.store.AnYiAuthentication;
import com.anyilanxin.cloud.oauth2common.store.IAuthStore;
import com.anyilanxin.cloud.oauth2common.store.jackson2.AnYiWebAuthenticationDetails;
import com.anyilanxin.cloud.oauth2common.store.jackson2.OAuth2AuthorizationModule;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.security.jackson2.SecurityJackson2Modules;
import org.springframework.security.web.authentication.WebAuthenticationDetails;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.Duration;
import java.util.List;
import java.util.Objects;

/**
 * @author zxh
 * @date 2023-09-26 03:02
 * @since 1.0.0
 */
public class AuthStoreImpl implements IAuthStore {
    private final RedisOperations<String, String> redisOperations;
    private static final String AUTH_PRINCIPAL_INFO = "auth_principal_info:";
    private static final MessageDigest DIGEST;
    @Setter
    protected String prefix = "";

    @Setter
    protected ObjectMapper objectMapper = new ObjectMapper();

    protected String write(Object data) {
        try {
            return this.objectMapper.writeValueAsString(data);
        } catch (Exception ex) {
            throw new IllegalArgumentException(ex.getMessage(), ex);
        }
    }

    protected AnYiAuthentication parse(String data) {
        try {
            TypeReference<AnYiAuthentication> typeReference = new TypeReference<>() {
            };
            return this.objectMapper.readValue(data, typeReference);
        } catch (Exception ex) {
            throw new IllegalArgumentException(ex.getMessage(), ex);
        }
    }


    public AuthStoreImpl(RedisOperations<String, String> redisOperations) {
        ClassLoader classLoader = this.getClass().getClassLoader();
        List<Module> securityModules = SecurityJackson2Modules.getModules(classLoader);
        objectMapper.registerModules(securityModules);
        objectMapper.registerModules(new OAuth2AuthorizationModule());
        this.redisOperations = redisOperations;
    }


    static {
        try {
            DIGEST = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    private String getIdToAuthPrincipalKey(String token) {
        return prefix + AUTH_PRINCIPAL_INFO + generateKey(token);
    }

    protected static String generateKey(String rawKey) {
        byte[] bytes = DIGEST.digest(rawKey.getBytes(StandardCharsets.UTF_8));
        return String.format("%032x", new BigInteger(1, bytes));
    }


    @Override
    public AnYiAuthentication findAuthentication(String tokenValue) {
        String idToAuthPrincipalKey = getIdToAuthPrincipalKey(tokenValue);
        String data = redisOperations.opsForValue().get(idToAuthPrincipalKey);
        if (StringUtils.isBlank(data)) {
            return null;
        }
        return parse(data);
    }


    @Override
    public void remove(String tokenValue) {
        String idToAuthPrincipalKey = getIdToAuthPrincipalKey(tokenValue);
        redisOperations.delete(idToAuthPrincipalKey);
    }


    @Override
    public void updateAuthentication(String tokenValue, AnYiAuthentication authentication) {
        String idToAuthPrincipalKey = getIdToAuthPrincipalKey(tokenValue);
        Object details = authentication.getDetails();
        if (Objects.nonNull(details) && details instanceof WebAuthenticationDetails detailsInfo) {
            AnYiWebAuthenticationDetails anYiWebAuthenticationDetails = new AnYiWebAuthenticationDetails();
            anYiWebAuthenticationDetails.setRemoteAddress(detailsInfo.getRemoteAddress());
            anYiWebAuthenticationDetails.setSessionId(detailsInfo.getSessionId());
            authentication.setDetails(anYiWebAuthenticationDetails);
        }
        redisOperations.opsForValue().set(idToAuthPrincipalKey, write(authentication));
    }


    @Override
    public void saveAuthentication(String tokenValue, Duration duration, AnYiAuthentication authentication) {
        String idToAuthPrincipalKey = getIdToAuthPrincipalKey(tokenValue);
        Object details = authentication.getDetails();
        if (Objects.nonNull(details) && details instanceof WebAuthenticationDetails detailsInfo) {
            AnYiWebAuthenticationDetails anYiWebAuthenticationDetails = new AnYiWebAuthenticationDetails();
            anYiWebAuthenticationDetails.setRemoteAddress(detailsInfo.getRemoteAddress());
            anYiWebAuthenticationDetails.setSessionId(detailsInfo.getSessionId());
            authentication.setDetails(anYiWebAuthenticationDetails);
        }
        redisOperations.opsForValue().set(idToAuthPrincipalKey, write(authentication), duration);
    }
}
