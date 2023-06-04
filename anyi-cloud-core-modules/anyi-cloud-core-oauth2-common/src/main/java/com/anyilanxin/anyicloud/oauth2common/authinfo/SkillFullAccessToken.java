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
package com.anyilanxin.anyicloud.oauth2common.authinfo;

import com.anyilanxin.anyicloud.oauth2common.serializer.CustomOAuth2AccessTokenJackson1Serializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import java.util.*;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.DefaultOAuth2RefreshToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2RefreshToken;

/**
 * @author zxh
 * @date 2022-02-14 05:42
 * @since 1.0.0
 */
@JsonSerialize(using = CustomOAuth2AccessTokenJackson1Serializer.class)
public class SkillFullAccessToken extends DefaultOAuth2AccessToken {

    private static final long serialVersionUID = 914967629530462926L;

    private String value;

    private Date expiration;

    private String tokenType = BEARER_TYPE.toLowerCase();

    private OAuth2RefreshToken refreshToken;

    private Set<String> scope;

    private Map<String, Object> additionalInformation = Collections.emptyMap();

    /**
     * Create an access token from the value provided.
     */
    public SkillFullAccessToken(String value) {
        super(value);
        this.value = value;
    }


    /**
     * Private constructor for JPA and other serialization tools.
     */
    @SuppressWarnings("unused")
    private SkillFullAccessToken() {
        this((String) null);
    }


    /**
     * Copy constructor for access token.
     *
     * @param accessToken
     */
    public SkillFullAccessToken(OAuth2AccessToken accessToken) {
        this(accessToken.getValue());
        setAdditionalInformation(accessToken.getAdditionalInformation());
        setRefreshToken(accessToken.getRefreshToken());
        setExpiration(accessToken.getExpiration());
        setScope(accessToken.getScope());
        setTokenType(accessToken.getTokenType());
    }


    @Override
    public void setValue(String value) {
        this.value = value;
    }


    /**
     * The token value.
     *
     * @return The token value.
     */
    @Override
    public String getValue() {
        return value;
    }


    @Override
    public int getExpiresIn() {
        return expiration != null ? Long.valueOf((expiration.getTime() - System.currentTimeMillis()) / 1000L).intValue() : 0;
    }


    @Override
    protected void setExpiresIn(int delta) {
        setExpiration(new Date(System.currentTimeMillis() + delta));
    }


    /**
     * The instant the token expires.
     *
     * @return The instant the token expires.
     */
    @Override
    public Date getExpiration() {
        return expiration;
    }


    /**
     * The instant the token expires.
     *
     * @param expiration The instant the token expires.
     */
    @Override
    public void setExpiration(Date expiration) {
        this.expiration = expiration;
    }


    /**
     * Convenience method for checking expiration
     *
     * @return true if the expiration is befor ethe current time
     */
    @Override
    public boolean isExpired() {
        return expiration != null && expiration.before(new Date());
    }


    /**
     * The token type, as introduced in draft 11 of the OAuth 2 spec. The spec doesn't define (yet)
     * that the valid token types are, but says it's required so the default will just be
     * "undefined".
     *
     * @return The token type, as introduced in draft 11 of the OAuth 2 spec.
     */
    @Override
    public String getTokenType() {
        return tokenType;
    }


    /**
     * The token type, as introduced in draft 11 of the OAuth 2 spec.
     *
     * @param tokenType The token type, as introduced in draft 11 of the OAuth 2 spec.
     */
    @Override
    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }


    /**
     * The refresh token associated with the access token, if any.
     *
     * @return The refresh token associated with the access token, if any.
     */
    @Override
    public OAuth2RefreshToken getRefreshToken() {
        return refreshToken;
    }


    /**
     * The refresh token associated with the access token, if any.
     *
     * @param refreshToken The refresh token associated with the access token, if any.
     */
    @Override
    public void setRefreshToken(OAuth2RefreshToken refreshToken) {
        this.refreshToken = refreshToken;
    }


    /**
     * The scope of the token.
     *
     * @return The scope of the token.
     */
    @Override
    public Set<String> getScope() {
        return scope;
    }


    /**
     * The scope of the token.
     *
     * @param scope The scope of the token.
     */
    @Override
    public void setScope(Set<String> scope) {
        this.scope = scope;
    }


    @Override
    public boolean equals(Object obj) {
        return obj != null && toString().equals(obj.toString());
    }


    @Override
    public int hashCode() {
        return toString().hashCode();
    }


    @Override
    public String toString() {
        return String.valueOf(getValue());
    }


    public static OAuth2AccessToken valueOf(Map<String, String> tokenParams) {
        DefaultOAuth2AccessToken token = new DefaultOAuth2AccessToken(tokenParams.get(ACCESS_TOKEN));

        if (tokenParams.containsKey(EXPIRES_IN)) {
            long expiration = 0;
            try {
                expiration = Long.parseLong(String.valueOf(tokenParams.get(EXPIRES_IN)));
            } catch (NumberFormatException e) {
                // fall through...
            }
            token.setExpiration(new Date(System.currentTimeMillis() + (expiration * 1000L)));
        }

        if (tokenParams.containsKey(REFRESH_TOKEN)) {
            String refresh = tokenParams.get(REFRESH_TOKEN);
            DefaultOAuth2RefreshToken refreshToken = new DefaultOAuth2RefreshToken(refresh);
            token.setRefreshToken(refreshToken);
        }

        if (tokenParams.containsKey(SCOPE)) {
            Set<String> scope = new TreeSet<>();
            for (StringTokenizer tokenizer = new StringTokenizer(tokenParams.get(SCOPE), " ,"); tokenizer.hasMoreTokens();) {
                scope.add(tokenizer.nextToken());
            }
            token.setScope(scope);
        }

        if (tokenParams.containsKey(TOKEN_TYPE)) {
            token.setTokenType(tokenParams.get(TOKEN_TYPE));
        }

        return token;
    }


    @Override
    public Map<String, Object> getAdditionalInformation() {
        return additionalInformation;
    }


    @Override
    public void setAdditionalInformation(Map<String, Object> additionalInformation) {
        this.additionalInformation = new LinkedHashMap<>(additionalInformation);
    }
}
