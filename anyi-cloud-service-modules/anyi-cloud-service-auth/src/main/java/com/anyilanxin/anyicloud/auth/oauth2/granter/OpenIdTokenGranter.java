

package com.anyilanxin.anyicloud.auth.oauth2.granter;

import com.anyilanxin.anyicloud.auth.oauth2.provider.token.OpenIdAuthenticationToken;
import com.anyilanxin.anyicloud.auth.utils.Oauth2LogUtils;
import com.anyilanxin.anyicloud.corecommon.constant.impl.AuthorizedGrantTypes;

import java.util.Map;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.provider.*;
import org.springframework.security.oauth2.provider.token.AbstractTokenGranter;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;

/**
 * 开放ip模式
 *
 * @author zxh
 * @date 2022-02-12 20:43
 * @since 1.0.0
 */
@Slf4j
public class OpenIdTokenGranter extends AbstractTokenGranter {
    private final AuthenticationManager authenticationManager;

    public OpenIdTokenGranter(AuthenticationManager authenticationManager, AuthorizationServerTokenServices tokenServices, ClientDetailsService clientDetailsService, OAuth2RequestFactory requestFactory) {
        super(tokenServices, clientDetailsService, requestFactory, AuthorizedGrantTypes.OPEN_ID.getType());
        this.authenticationManager = authenticationManager;
    }


    @Override
    protected OAuth2Authentication getOAuth2Authentication(ClientDetails client, TokenRequest tokenRequest) {
        Oauth2LogUtils.setPreAuthLog(client, tokenRequest);
        Map<String, String> requestParameters = tokenRequest.getRequestParameters();
        String openId = requestParameters.get("open_id");
        OpenIdAuthenticationToken openIdAuthenticationToken = new OpenIdAuthenticationToken(openId);
        Authentication authenticate = authenticationManager.authenticate(openIdAuthenticationToken);
        OAuth2Request oAuth2Request = getRequestFactory().createOAuth2Request(client, tokenRequest);
        return new OAuth2Authentication(oAuth2Request, authenticate);
    }
}
