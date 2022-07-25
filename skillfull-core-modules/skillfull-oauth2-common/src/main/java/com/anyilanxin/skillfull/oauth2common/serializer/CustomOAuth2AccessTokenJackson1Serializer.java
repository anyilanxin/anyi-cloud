// +----------------------------------------------------------------------
// | SkillFull快速开发平台 [ SkillFull ]
// +----------------------------------------------------------------------
// | 版权所有 2020~2021 zxiaozhou
// +----------------------------------------------------------------------
// | 官方网站: https://www.divisu.com
// +----------------------------------------------------------------------
// | 作者: zxiaozhou <z7630853@163.com>
// +----------------------------------------------------------------------
package com.anyilanxin.skillfull.oauth2common.serializer;

import com.alibaba.fastjson.JSONObject;
import com.anyilanxin.skillfull.corecommon.base.Result;
import com.anyilanxin.skillfull.corecommon.constant.Status;
import com.anyilanxin.skillfull.oauth2common.authinfo.SkillFullAccessToken;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2RefreshToken;
import org.springframework.util.Assert;

import java.io.IOException;
import java.util.Date;
import java.util.Map;
import java.util.Set;

/**
 * @author zxiaozhou
 * @date 2022-02-14 05:43
 * @since JDK1.8
 */
public class CustomOAuth2AccessTokenJackson1Serializer extends StdSerializer<SkillFullAccessToken> {

    protected CustomOAuth2AccessTokenJackson1Serializer() {
        super(SkillFullAccessToken.class);
    }

    @Override
    public void serialize(SkillFullAccessToken token, JsonGenerator gen, SerializerProvider provider) throws IOException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put(OAuth2AccessToken.ACCESS_TOKEN, token.getValue());
        jsonObject.put(OAuth2AccessToken.TOKEN_TYPE, token.getTokenType());
        OAuth2RefreshToken refreshToken = token.getRefreshToken();
        if (refreshToken != null) {
            jsonObject.put(OAuth2AccessToken.REFRESH_TOKEN, refreshToken.getValue());
        }
        Date expiration = token.getExpiration();
        if (expiration != null) {
            long now = System.currentTimeMillis();
            jsonObject.put(OAuth2AccessToken.EXPIRES_IN, (expiration.getTime() - now) / 1000);
        }
        Set<String> scope = token.getScope();
        if (scope != null && !scope.isEmpty()) {
            StringBuilder scopes = new StringBuilder();
            for (String s : scope) {
                Assert.hasLength(s, "Scopes cannot be null or empty. Got " + scope + "");
                scopes.append(s);
                scopes.append(" ");
            }
            jsonObject.put(OAuth2AccessToken.SCOPE, scopes.substring(0, scopes.length() - 1));
        }
        Map<String, Object> additionalInformation = token.getAdditionalInformation();
        for (String key : additionalInformation.keySet()) {
            jsonObject.put(key, additionalInformation.get(key));
        }
        Result<JSONObject> result = new Result<>(Status.SUCCESS, jsonObject);
        gen.writeObject(result);
    }
}
