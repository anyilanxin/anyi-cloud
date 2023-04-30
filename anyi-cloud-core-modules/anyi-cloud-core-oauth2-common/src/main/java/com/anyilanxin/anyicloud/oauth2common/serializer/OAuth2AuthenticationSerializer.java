/*
 * Copyright (c) 2021-2022 ZHOUXUANHONG(安一老厨)<anyilanxin@aliyun.com>
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
 *   1.请不要删除和修改根目录下的LICENSE文件；
 *   2.请不要删除和修改 AnYi Cloud 源码头部的版权声明；
 *   3.请保留源码和相关描述文件的项目出处，作者声明等；
 *   4.分发源码时候，请注明软件出处 https://github.com/anyilanxin/anyi-cloud；
 *   5.在修改包名，模块名称，项目代码等时，请注明软件出处 https://github.com/anyilanxin/anyi-cloud；
 *   6.本软件不允许在国家法律规定范围外使用，如出现违法行为原作者本人不承担任何法律风险；
 *   7.本软件使用的第三方依赖皆为开源软件，如需要修改第三方源码请遵循第三方源码附带开源协议；
 *   8.本软件流程部分请遵循camunda开源协议：
 *     https://docs.camunda.org/manual/latest/introduction/third-party-libraries
 *     https://github.com/camunda/camunda-bpm-platform/blob/master/LICENSE
 *   9.若您的项目无法满足以上几点，可申请商业授权。
 */

package com.anyilanxin.anyicloud.oauth2common.serializer;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.parser.Feature;
import com.alibaba.fastjson.parser.deserializer.ObjectDeserializer;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.*;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.OAuth2Request;
import org.springframework.security.oauth2.provider.TokenRequest;

/**
 * @author 安一老厨
 * @date 2022-02-14 16:28
 * @since 1.0.0
 */
public class OAuth2AuthenticationSerializer implements ObjectDeserializer {

    @Override
    public <T> T deserialze(DefaultJSONParser parser, Type type, Object fieldName) {
        if (type == OAuth2Authentication.class) {
            try {
                Object o = parse(parser);
                if (o == null) {
                    return null;
                } else if (o instanceof OAuth2Authentication) {
                    return (T) o;
                }
                JSONObject jsonObject = (JSONObject) o;
                OAuth2Request request = parseOAuth2Request(jsonObject);
                if (Objects.isNull(request)) {
                    return null;
                }
                // 判断json节点userAuthentication的类型，根据类型动态取值
                // UsernamePasswordAuthenticationToken
                // 密码模式/授权码模式下，存储类型为UsernamePasswordAuthenticationToken
                // PreAuthenticatedAuthenticationToken
                // 刷新token模式下，存储类型为PreAuthenticatedAuthenticationToken
                Object autoType = jsonObject.get("userAuthentication");
                return (T) new OAuth2Authentication(request, jsonObject.getObject("userAuthentication", (Type) autoType.getClass()));
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
        return null;
    }


    private OAuth2Request parseOAuth2Request(JSONObject jsonObject) {
        JSONObject json = jsonObject.getObject("oAuth2Request", JSONObject.class);
        if (Objects.isNull(json)) {
            return null;
        }
        Map<String, String> requestParameters = json.getObject("requestParameters", Map.class);
        String clientId = json.getString("clientId");
        String grantType = json.getString("grantType");
        String redirectUri = json.getString("redirectUri");
        Boolean approved = json.getBoolean("approved");
        Set<String> responseTypes = json.getObject("responseTypes", new TypeReference<HashSet<String>>() {
        });
        Set<String> scope = json.getObject("scope", new TypeReference<HashSet<String>>() {
        });
        Set<String> authorities = json.getObject("authorities", new TypeReference<HashSet<String>>() {
        });
        Set<GrantedAuthority> grantedAuthorities = new HashSet<>(0);
        if (authorities != null && !authorities.isEmpty()) {
            authorities.forEach(s -> grantedAuthorities.add(new SimpleGrantedAuthority(s)));
        }
        Set<String> resourceIds = json.getObject("resourceIds", new TypeReference<HashSet<String>>() {
        });
        Map<String, Serializable> extensions = json.getObject("extensions", new TypeReference<HashMap<String, Serializable>>() {
        });
        OAuth2Request request = new OAuth2Request(requestParameters, clientId, grantedAuthorities, approved, scope, resourceIds, redirectUri, responseTypes, extensions);
        TokenRequest tokenRequest = new TokenRequest(requestParameters, clientId, scope, grantType);
        request.refresh(tokenRequest);
        return request;
    }


    @Override
    public int getFastMatchToken() {
        return 0;
    }


    private Object parse(DefaultJSONParser parse) {
        JSONObject object = new JSONObject(parse.lexer.isEnabled(Feature.OrderedField));
        Object parsedObject = parse.parseObject(object);
        if (parsedObject instanceof JSONObject) {
            return parsedObject;
        } else if (parsedObject instanceof OAuth2Authentication) {
            return parsedObject;
        } else {
            return parsedObject == null ? null : new JSONObject((Map) parsedObject);
        }
    }
}
