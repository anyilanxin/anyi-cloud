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

package com.anyilanxin.anyicloud.oauth2mvc;

import java.util.Enumeration;
import javax.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.provider.authentication.BearerTokenExtractor;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;

/**
 * 获取token逻辑
 *
 * @author 安一老厨
 * @date 2022-02-23 21:28
 * @since 1.0.0
 */
@Slf4j
public class CustomBearerTokenExtractor extends BearerTokenExtractor {
    private boolean allowUriQueryParameter = false;
    private String bearerTokenHeaderName = HttpHeaders.AUTHORIZATION;

    private String accessTokenQueryName = "access_token";

    @Override
    public Authentication extract(HttpServletRequest request) {
        String tokenValue = this.extractToken(request);
        if (tokenValue != null) {
            return new PreAuthenticatedAuthenticationToken(tokenValue, "");
        } else {
            return null;
        }
    }


    @Override
    protected String extractToken(HttpServletRequest request) {
        String token = this.extractHeaderToken(request);
        if (token == null && isParameterTokenSupportedForRequest()) {
            log.debug("Token not found in headers. Trying request parameters.");
            token = request.getParameter(this.accessTokenQueryName);
            if (token == null) {
                log.debug("Token not found in request parameters.  Not an OAuth2 request.");
            } else {
                request.setAttribute(OAuth2AuthenticationDetails.ACCESS_TOKEN_TYPE, "Bearer");
            }
        }
        return token;
    }


    @Override
    protected String extractHeaderToken(HttpServletRequest request) {
        Enumeration<String> headers = request.getHeaders(bearerTokenHeaderName);
        String value;
        do {
            if (!headers.hasMoreElements()) {
                return null;
            }
            value = (String) headers.nextElement();
        } while (!value.toLowerCase().startsWith("Bearer".toLowerCase()));

        String authHeaderValue = value.substring("Bearer".length()).trim();
        request.setAttribute(OAuth2AuthenticationDetails.ACCESS_TOKEN_TYPE, value.substring(0, "Bearer".length()).trim());
        int commaIndex = authHeaderValue.indexOf(44);
        if (commaIndex > 0) {
            authHeaderValue = authHeaderValue.substring(0, commaIndex);
        }
        return authHeaderValue;
    }


    public void setAllowUriQueryParameter(boolean allowUriQueryParameter) {
        this.allowUriQueryParameter = allowUriQueryParameter;
    }


    public void setAccessTokenQueryName(String accessTokenQueryName) {
        this.accessTokenQueryName = accessTokenQueryName;
    }


    public void setBearerTokenHeaderName(String bearerTokenHeaderName) {
        this.bearerTokenHeaderName = bearerTokenHeaderName;
    }


    private boolean isParameterTokenSupportedForRequest() {
        return this.allowUriQueryParameter;
    }
}
