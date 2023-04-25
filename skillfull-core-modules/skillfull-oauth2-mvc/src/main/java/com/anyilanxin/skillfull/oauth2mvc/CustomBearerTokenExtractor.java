package com.anyilanxin.skillfull.oauth2mvc;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.provider.authentication.BearerTokenExtractor;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;

/**
 * 获取token逻辑
 *
 * @author zxiaozhou
 * @date 2022-02-23 21:28
 * @since JDK1.8
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
