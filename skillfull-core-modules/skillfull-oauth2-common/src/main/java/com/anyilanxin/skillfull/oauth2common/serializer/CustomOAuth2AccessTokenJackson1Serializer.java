/**
 * Copyright (c) 2021-2022 ZHOUXUANHONG(安一老厨)<anyilanxin@aliyun.com>
 *
 * AnYi Cloud Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * AnYi Cloud 采用APACHE LICENSE 2.0开源协议，您在使用过程中，需要注意以下几点：
 *
 * 1.请不要删除和修改根目录下的LICENSE文件。
 * 2.请不要删除和修改 AnYi Cloud 源码头部的版权声明。
 * 3.请保留源码和相关描述文件的项目出处，作者声明等。
 * 4.分发源码时候，请注明软件出处 https://github.com/anyilanxin/anyi-cloud
 * 5.在修改包名，模块名称，项目代码等时，请注明软件出处 https://github.com/anyilanxin/anyi-cloud
 * 6.若您的项目无法满足以上几点，可申请商业授权
 */
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
