/*
 * Copyright (c) 2021-present ZHOUXUANHONG(安一老厨)<anyilanxin@aliyun.com>
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
 *   7.进行商用时，不得基于AnYi Cloud的基础，修改包装而成一个与AnYi Zeebe EE、AnYi Cloud EE功能类似的程序，进行销售或发布，参与同类软件产品市场的竞争；
 *   8.本软件使用的第三方依赖皆为开源软件，如需要修改第三方源码请遵循第三方源码附带开源协议；
 *   9.本软件流程部分请遵循camunda开源协议：
 *     https://docs.camunda.org/manual/latest/introduction/third-party-libraries
 *     https://github.com/camunda/camunda-bpm-platform/blob/master/LICENSE
 *   10.若您的项目无法满足以上几点，可申请商业授权。
 */

package com.anyilanxin.anyicloud.oauth2webflux.oauth2;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.server.resource.BearerTokenAuthenticationToken;
import org.springframework.security.oauth2.server.resource.BearerTokenError;
import org.springframework.security.oauth2.server.resource.BearerTokenErrors;
import org.springframework.security.oauth2.server.resource.web.server.ServerBearerTokenAuthenticationConverter;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * 自定义token获取
 *
 * @author zxh
 * @copyright zhouxuanhong（https://anyilanxin.com）
 * @date 2022-08-30 20:01
 * @since 1.0.0
 */
public class CustomServerBearerTokenAuthenticationConverter extends ServerBearerTokenAuthenticationConverter {
    private static final Pattern authorizationPattern = Pattern.compile("^Bearer (?<token>[a-zA-Z0-9-._~+/]+=*)$", Pattern.CASE_INSENSITIVE);

    private boolean allowUriQueryParameter = false;

    private String bearerTokenHeaderName = HttpHeaders.AUTHORIZATION;

    private String accessTokenQueryName = "access_token";

    @Override
    public Mono<Authentication> convert(ServerWebExchange exchange) {
        return Mono.fromCallable(() -> token(exchange.getRequest())).map((token) -> {
            if (token.isEmpty()) {
                BearerTokenError error = invalidTokenError();
                throw new OAuth2AuthenticationException(error);
            }
            return new BearerTokenAuthenticationToken(token);
        });
    }


    private String token(ServerHttpRequest request) {
        String authorizationHeaderToken = resolveFromAuthorizationHeader(request.getHeaders());
        String parameterToken = resolveAccessTokenFromRequest(request);

        if (authorizationHeaderToken != null) {
            if (parameterToken != null) {
                BearerTokenError error = BearerTokenErrors.invalidRequest("Found multiple bearer tokens in the request");
                throw new OAuth2AuthenticationException(error);
            }
            return authorizationHeaderToken;
        }
        if (parameterToken != null && isParameterTokenSupportedForRequest()) {
            return parameterToken;
        }
        return null;
    }


    private String resolveAccessTokenFromRequest(ServerHttpRequest request) {
        List<String> parameterTokens = request.getQueryParams().get(this.accessTokenQueryName);
        if (CollectionUtils.isEmpty(parameterTokens)) {
            return null;
        }
        if (parameterTokens.size() == 1) {
            return parameterTokens.get(0);
        }

        BearerTokenError error = BearerTokenErrors.invalidRequest("Found multiple bearer tokens in the request");
        throw new OAuth2AuthenticationException(error);
    }


    /**
     * Set if transport of access token using URI query parameter is supported. Defaults to {@code
     * false}.
     *
     * <p>The spec recommends against using this mechanism for sending bearer tokens, and even goes
     * as far as stating that it was only included for completeness.
     *
     * @param allowUriQueryParameter if the URI query parameter is supported
     */
    public void setAllowUriQueryParameter(boolean allowUriQueryParameter) {
        this.allowUriQueryParameter = allowUriQueryParameter;
    }


    /**
     * Set this value to configure what header is checked when resolving a Bearer Token. This value
     * is defaulted to {@link HttpHeaders#AUTHORIZATION}.
     *
     * <p>This allows other headers to be used as the Bearer Token source such as {@link
     * HttpHeaders#PROXY_AUTHORIZATION}
     *
     * @param bearerTokenHeaderName the header to check when retrieving the Bearer Token.
     * @since 5.4
     */
    public void setBearerTokenHeaderName(String bearerTokenHeaderName) {
        this.bearerTokenHeaderName = bearerTokenHeaderName;
    }


    public void setAccessTokenQueryName(String accessTokenQueryName) {
        this.accessTokenQueryName = accessTokenQueryName;
    }


    private String resolveFromAuthorizationHeader(HttpHeaders headers) {
        String authorization = headers.getFirst(this.bearerTokenHeaderName);
        if (!StringUtils.startsWithIgnoreCase(authorization, "bearer")) {
            return null;
        }
        Matcher matcher = authorizationPattern.matcher(authorization);
        if (!matcher.matches()) {
            BearerTokenError error = invalidTokenError();
            throw new OAuth2AuthenticationException(error);
        }
        return matcher.group("token");
    }


    private static BearerTokenError invalidTokenError() {
        return BearerTokenErrors.invalidToken("Bearer token is malformed");
    }


    private boolean isParameterTokenSupportedForRequest() {
        return this.allowUriQueryParameter;
    }
}
