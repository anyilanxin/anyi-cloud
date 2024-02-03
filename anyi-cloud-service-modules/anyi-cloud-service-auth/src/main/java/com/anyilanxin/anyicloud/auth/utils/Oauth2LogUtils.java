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

import com.alibaba.fastjson2.JSONObject;
import com.alibaba.fastjson2.JSONWriter;
import com.anyilanxin.anyicloud.auth.oauth2.authentication.tokenendpoint.password.AnYiOAuth2PasswordAuthenticationConverter;
import com.anyilanxin.anyicloud.auth.oauth2.authentication.tokenendpoint.sms.AnYiOAuth2SmsAuthenticationConverter;
import com.anyilanxin.anyicloud.corecommon.constant.CommonCoreConstant;
import com.anyilanxin.anyicloud.corecommon.constant.impl.AuthorizedGrantTypes;
import com.anyilanxin.anyicloud.corecommon.model.auth.client.ClientSettingModel;
import com.anyilanxin.anyicloud.corecommon.model.auth.client.TokenSettingModel;
import com.anyilanxin.anyicloud.corecommon.model.system.ClientAndResourceAuthModel;
import com.anyilanxin.anyicloud.coremvc.utils.ServletUtils;
import com.anyilanxin.anyicloud.loggingcommon.model.AuthLogModel;
import com.anyilanxin.anyicloud.loggingcommon.utils.AnYiLogUtils;
import com.anyilanxin.anyicloud.oauth2common.authinfo.AnYiAccessToken;
import com.anyilanxin.anyicloud.oauth2common.authinfo.AnYiClientDetails;
import com.anyilanxin.anyicloud.oauth2common.authinfo.AnYiUserDetails;
import com.anyilanxin.anyicloud.oauth2common.constant.AnYiSecurityConstants;
import com.anyilanxin.anyicloud.oauth2common.enums.DataFormatType;
import io.undertow.util.Headers;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import org.dromara.hutool.core.collection.CollUtil;
import org.dromara.hutool.http.useragent.UserAgent;
import org.dromara.hutool.http.useragent.UserAgentUtil;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.jose.jws.SignatureAlgorithm;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.settings.ClientSettings;
import org.springframework.security.oauth2.server.authorization.settings.OAuth2TokenFormat;
import org.springframework.security.oauth2.server.authorization.settings.TokenSettings;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * oauth2 授权日志处理
 *
 * @author zxh
 * @date 2022-08-13 19:27
 * @since 1.0.0
 */
public class Oauth2LogUtils {
    private final static String AUTH_LOG_CONTENT_KEY = "AUTH_LOG_CONTENT";

    /**
     * 前置提交授权日志
     *
     * @param client
     * @param grantType
     * @author zxh
     * @date 2022-08-13 19:55
     */
    public static void setPreAuthLog(RegisteredClient client, String grantType) {
        AuthLogModel authLogModel = getAuthLogModel();
        if (Objects.nonNull(authLogModel)) {
            authLogModel.setAuthType(grantType);
            AuthorizedGrantTypes byType = AuthorizedGrantTypes.getByType(grantType);
            if (Objects.nonNull(byType)) {
                authLogModel.setAuthTypeDescribe(byType.getDescribe());
            }
            if (StringUtils.isBlank(authLogModel.getAuthType())) {
                authLogModel.setAuthType("unknown");
                authLogModel.setAuthTypeDescribe("未知授权类型");
            }
            authLogModel.setAuthClientCode(client.getClientId());
            authLogModel.setRequestIp(ServletUtils.getIpAddr());
            authLogModel.setRequestStartTime(LocalDateTime.now());
        }
    }


    /**
     * 获取authLog
     *
     * @return AuthLogModel
     * @author zxh
     * @date 2022-08-13 20:45
     */
    public static AuthLogModel getAuthLogModel() {
        HttpServletRequest request = ServletUtils.getRequest();
        if (Objects.nonNull(request)) {
            Object attribute = request.getAttribute(AUTH_LOG_CONTENT_KEY);
            AuthLogModel authLogModel;
            if (Objects.isNull(attribute)) {
                authLogModel = new AuthLogModel();
                UserAgent parse = UserAgentUtil.parse(request.getHeader(Headers.USER_AGENT_STRING));
                authLogModel.setLogOtherData(JSONObject.toJSONString(parse, JSONWriter.Feature.WriteMapNullValue));
                String requestId = request.getHeader(CommonCoreConstant.X_REQUEST_ID);
                if (StringUtils.isNotBlank(requestId)) {
                    authLogModel.setLogCode(requestId);
                }
                request.setAttribute(AUTH_LOG_CONTENT_KEY, authLogModel);
            } else {
                authLogModel = (AuthLogModel) attribute;
            }
            return authLogModel;
        }
        return null;
    }


    /**
     * 设置用户信息
     *
     * @param userDetails
     * @author zxh
     * @date 2022-08-13 20:59
     */
    public static void setUserDetailInfo(AnYiUserDetails userDetails) {
        AuthLogModel authLogModel = Oauth2LogUtils.getAuthLogModel();
        if (Objects.nonNull(authLogModel) && Objects.nonNull(userDetails)) {
            authLogModel.setAuthUserId(userDetails.getUserId());
            authLogModel.setAuthUserName((StringUtils.isNotBlank(userDetails.getRealName())) ? userDetails.getRealName() : StringUtils.isNotBlank(userDetails.getNickName()) ? userDetails.getNickName() : userDetails.getUsername());
        }
    }


    /**
     * 设置客户端信息
     *
     * @param clientDetailInfo
     * @author zxh
     * @date 2022-08-13 21:00
     */
    public static void setClientDetailInfo(AnYiClientDetails clientDetailInfo) {
        AuthLogModel authLogModel = Oauth2LogUtils.getAuthLogModel();
        if (Objects.nonNull(authLogModel) && Objects.nonNull(clientDetailInfo)) {
            authLogModel.setAuthClientCode(clientDetailInfo.getClientId());
            authLogModel.setAuthClientName(clientDetailInfo.getClientName());
        }
    }


    /**
     * 转换为RegisteredClient
     *
     * @return {@link RegisteredClient }
     * @author zxh
     * @date 2023-09-21 17:28:43
     */
    public static RegisteredClient getRegisteredClient(ClientAndResourceAuthModel client) {
        Set<String> clientAuthenticationMethods = client.getClientAuthenticationMethodInfos();
        if (CollUtil.isEmpty(clientAuthenticationMethods)) {
            clientAuthenticationMethods = new HashSet<>();
        }
        Set<String> authorizationGrantTypes = client.getAuthorizationGrantTypeInfos();
        if (CollUtil.isEmpty(authorizationGrantTypes)) {
            authorizationGrantTypes = new HashSet<>();
        }
        Set<String> redirectUris = client.getRedirectUriInfos();
        if (CollUtil.isEmpty(redirectUris)) {
            redirectUris = new HashSet<>();
        }
        Set<String> postLogoutRedirectUris = client.getPostLogoutRedirectUriInfos();
        if (CollUtil.isEmpty(postLogoutRedirectUris)) {
            postLogoutRedirectUris = new HashSet<>();
        }
        Set<String> clientScopes = client.getScopeInfos();
        if (CollUtil.isEmpty(clientScopes)) {
            clientScopes = new HashSet<>();
        }
        Set<String> finalClientAuthenticationMethods = clientAuthenticationMethods;
        Set<String> finalAuthorizationGrantTypes = authorizationGrantTypes;
        Set<String> finalRedirectUris = redirectUris;
        Set<String> finalPostLogoutRedirectUris = postLogoutRedirectUris;
        Set<String> finalClientScopes = clientScopes;
        RegisteredClient.Builder builder = RegisteredClient.withId(client.getId())
                .clientId(client.getClientId())
                .clientSecret(client.getClientSecurity())
                .clientName(client.getClientName())
                .clientAuthenticationMethods(authenticationMethods ->
                        finalClientAuthenticationMethods.forEach(authenticationMethod ->
                                authenticationMethods.add(resolveClientAuthenticationMethod(authenticationMethod))))
                .authorizationGrantTypes((grantTypes) ->
                        finalAuthorizationGrantTypes.forEach(grantType ->
                                grantTypes.add(resolveAuthorizationGrantType(grantType))))
                .redirectUris((uris) -> uris.addAll(finalRedirectUris))
                .postLogoutRedirectUris((uris) -> uris.addAll(finalPostLogoutRedirectUris))
                .scopes((scopes) -> scopes.addAll(finalClientScopes));
        // client setting信息
        ClientSettingModel clientSettingInfo = client.getClientSettingInfo();
        ClientSettings.Builder clientBuild = ClientSettings.builder();
        clientBuild.setting(AnYiSecurityConstants.LIMIT_RESOURCE, false);
        clientBuild.setting(AnYiSecurityConstants.RESOURCE_IDS, Collections.emptySet());
        if (Objects.nonNull(clientSettingInfo)) {
            if (StringUtils.isNotBlank(clientSettingInfo.getJwkSetUrl())) {
                clientBuild.jwkSetUrl(clientSettingInfo.getJwkSetUrl());
            }
//            if (StringUtils.isNotBlank(clientSettingInfo.getTokenEndpointAuthenticationSigningAlgorithm())) {
//                clientBuild.tokenEndpointAuthenticationSigningAlgorithm(clientSettingInfo.getTokenEndpointAuthenticationSigningAlgorithm());
//            }
            clientBuild.requireAuthorizationConsent(clientSettingInfo.isRequireAuthorizationConsent());
            clientBuild.requireProofKey(clientSettingInfo.isRequireProofKey());
            clientBuild.setting(AnYiSecurityConstants.LIMIT_RESOURCE, clientSettingInfo.isLimitResource());
            if (clientSettingInfo.isLimitResource()) {
                clientBuild.setting(AnYiSecurityConstants.RESOURCE_IDS, clientSettingInfo.getResourceIds());
            }
        }
        builder.clientSettings(clientBuild.build());
        // token setting
        TokenSettingModel tokenSettingInfo = client.getTokenSettingInfo();
        TokenSettings.Builder tokenSetting = TokenSettings.builder();
        tokenSetting.setting(AnYiSecurityConstants.DATA_FORMAT, DataFormatType.NORMAL);
        if (Objects.nonNull(tokenSettingInfo)) {
            if (Objects.nonNull(tokenSettingInfo.getAuthorizationCodeTimeToLive()) && tokenSettingInfo.getAuthorizationCodeTimeToLive() > 0) {
                tokenSetting.authorizationCodeTimeToLive(Duration.ofSeconds(tokenSettingInfo.getAuthorizationCodeTimeToLive()));
            }
            if (Objects.nonNull(tokenSettingInfo.getAccessTokenTimeToLive()) && tokenSettingInfo.getAccessTokenTimeToLive() > 0) {
                tokenSetting.accessTokenTimeToLive(Duration.ofSeconds(tokenSettingInfo.getAccessTokenTimeToLive()));
            }
            if (Objects.nonNull(tokenSettingInfo.getRefreshTokenTimeToLive()) && tokenSettingInfo.getRefreshTokenTimeToLive() > 0) {
                tokenSetting.refreshTokenTimeToLive(Duration.ofSeconds(tokenSettingInfo.getRefreshTokenTimeToLive()));
            }
            if (Objects.nonNull(tokenSettingInfo.getReuseRefreshTokens())) {
                tokenSetting.reuseRefreshTokens(tokenSettingInfo.getReuseRefreshTokens());
            }
            if (Objects.nonNull(tokenSettingInfo.getDeviceCodeTimeToLive()) && tokenSettingInfo.getDeviceCodeTimeToLive() > 0) {
                tokenSetting.deviceCodeTimeToLive(Duration.ofSeconds(tokenSettingInfo.getDeviceCodeTimeToLive()));
            }
            if (StringUtils.isNotBlank(tokenSettingInfo.getIdTokenSignatureAlgorithm())) {
                tokenSetting.idTokenSignatureAlgorithm(SignatureAlgorithm.from(tokenSettingInfo.getIdTokenSignatureAlgorithm()));
            }
            if (StringUtils.isNotBlank(tokenSettingInfo.getAccessTokenFormat())) {
                tokenSetting.accessTokenFormat(new OAuth2TokenFormat(tokenSettingInfo.getAccessTokenFormat()));
            }
            if (StringUtils.isNotBlank(tokenSettingInfo.getDataFormat())) {
                DataFormatType type = DataFormatType.valueOf(tokenSettingInfo.getDataFormat());
                tokenSetting.setting(AnYiSecurityConstants.DATA_FORMAT, type);
            }
        }
        tokenSetting.accessTokenFormat(OAuth2TokenFormat.SELF_CONTAINED);
        builder.tokenSettings(tokenSetting.build());
        return builder.build();
    }


    private static ClientAuthenticationMethod resolveClientAuthenticationMethod(String clientAuthenticationMethod) {
        if (ClientAuthenticationMethod.CLIENT_SECRET_BASIC.getValue().equals(clientAuthenticationMethod)) {
            return ClientAuthenticationMethod.CLIENT_SECRET_BASIC;
        } else if (ClientAuthenticationMethod.CLIENT_SECRET_POST.getValue().equals(clientAuthenticationMethod)) {
            return ClientAuthenticationMethod.CLIENT_SECRET_POST;
        } else if (ClientAuthenticationMethod.NONE.getValue().equals(clientAuthenticationMethod)) {
            return ClientAuthenticationMethod.NONE;
        }
        return new ClientAuthenticationMethod(clientAuthenticationMethod);      // Custom client authentication method
    }


    private static AuthorizationGrantType resolveAuthorizationGrantType(String authorizationGrantType) {
        if (AuthorizationGrantType.AUTHORIZATION_CODE.getValue().equals(authorizationGrantType)) {
            return AuthorizationGrantType.AUTHORIZATION_CODE;
        } else if (AuthorizationGrantType.CLIENT_CREDENTIALS.getValue().equals(authorizationGrantType)) {
            return AuthorizationGrantType.CLIENT_CREDENTIALS;
        } else if (AuthorizationGrantType.REFRESH_TOKEN.getValue().equals(authorizationGrantType)) {
            return AuthorizationGrantType.REFRESH_TOKEN;
        } else if (AnYiOAuth2PasswordAuthenticationConverter.PASSWORD_GRANT_TYPE.getValue().equals(authorizationGrantType)) {
            return AnYiOAuth2PasswordAuthenticationConverter.PASSWORD_GRANT_TYPE;
        } else if (AnYiOAuth2SmsAuthenticationConverter.SMS_GRANT_TYPE.getValue().equals(authorizationGrantType)) {
            return AnYiOAuth2SmsAuthenticationConverter.SMS_GRANT_TYPE;
        }
        // Custom authorization grant type
        return new AuthorizationGrantType(authorizationGrantType);
    }

    /**
     * 后置提交授权日志并发送大日志服务
     *
     * @param success
     * @param msg
     * @param errMsg
     * @author zxh
     * @date 2022-08-13 19:56
     */
    public static void setPostAuthLog(boolean success, String msg, String errMsg, AnYiAccessToken token) {
        AuthLogModel authLogModel = getAuthLogModel();
        if (Objects.nonNull(authLogModel)) {
            authLogModel.setRequestEndTime(LocalDateTime.now());
            authLogModel.setAuthStatus(success ? 1 : 0);
            authLogModel.setLogData(msg);
            authLogModel.setExceptionMessage(errMsg);
            if (Objects.nonNull(token)) {
                authLogModel.setAuthToken(token.getValue());
                authLogModel.setLogData(JSONObject.toJSONString(token, JSONWriter.Feature.WriteMapNullValue));
            }
            AnYiLogUtils.sendAuthLog(authLogModel);
        }
    }
}
