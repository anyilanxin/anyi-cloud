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

package com.anyilanxin.anyicloud.auth.oauth2.repository.redis.jackson2.deserializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.MissingNode;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.OAuth2RefreshToken;
import org.springframework.security.oauth2.core.OAuth2Token;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.server.authorization.OAuth2Authorization;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationCode;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.util.Assert;

import java.io.IOException;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import static org.springframework.security.oauth2.server.authorization.OAuth2Authorization.*;

public class OAuth2AuthorizationDeserializer extends JsonDeserializer<OAuth2Authorization> {

    private final RegisteredClientRepository registeredClientRepository;

    private static final TypeReference<Set<String>> SET_TYPE_REFERENCE = new TypeReference<>() {
    };

    private static final TypeReference<Map<String, Object>> MAP_TYPE_REFERENCE = new TypeReference<>() {
    };

    private static final TypeReference<AuthorizationGrantType> GRANT_TYPE_TYPE_REFERENCE = new TypeReference<>() {
    };

    public OAuth2AuthorizationDeserializer(RegisteredClientRepository registeredClientRepository) {
        this.registeredClientRepository = registeredClientRepository;
    }

    @Override
    public OAuth2Authorization deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {
        ObjectMapper mapper = (ObjectMapper) jp.getCodec();
        JsonNode jsonNode = mapper.readTree(jp);

        Set<String> authorizedScopes = mapper.convertValue(jsonNode.get("authorizedScopes"), SET_TYPE_REFERENCE);
        Map<String, Object> attributes = mapper.convertValue(jsonNode.get("attributes"), MAP_TYPE_REFERENCE);
        Map<String, Object> tokens = mapper.convertValue(jsonNode.get("tokens"), MAP_TYPE_REFERENCE);
        AuthorizationGrantType grantType = mapper.convertValue(jsonNode.get("authorizationGrantType"), GRANT_TYPE_TYPE_REFERENCE);

        String id = readJsonNode(jsonNode, "id").asText();
        String registeredClientId = readJsonNode(jsonNode, "registeredClientId").asText();
        String principalName = readJsonNode(jsonNode, "principalName").asText();

        RegisteredClient registeredClient = registeredClientRepository.findById(registeredClientId);
        Assert.notNull(registeredClient, "Registered client must not be null");

        Builder builder = withRegisteredClient(registeredClient)
                .id(id)
                .principalName(principalName)
                .authorizationGrantType(grantType)
                .authorizedScopes(authorizedScopes)
                .attributes(map -> map.putAll(attributes));

        Optional.ofNullable(tokens.get(OAuth2AuthorizationCode.class.getName())).ifPresent(
                token -> addToken((Token) token, builder));
        Optional.ofNullable(tokens.get(OAuth2AccessToken.class.getName())).ifPresent(
                token -> addToken((Token) token, builder));
        Optional.ofNullable(tokens.get(OAuth2RefreshToken.class.getName())).ifPresent(
                token -> addToken((Token) token, builder));
        Optional.ofNullable(tokens.get(OidcIdToken.class.getName())).ifPresent(
                token -> addToken((Token) token, builder));

        return builder.build();
    }

    public void addToken(Token<OAuth2Token> token, Builder builder) {
        builder.token(token.getToken(), map -> map.putAll(token.getMetadata()));
    }

    private JsonNode readJsonNode(JsonNode jsonNode, String field) {
        return jsonNode.has(field) ? jsonNode.get(field) : MissingNode.getInstance();
    }

}
