package com.anyilanxin.skillfull.auth.oauth2.granter;

import com.anyilanxin.skillfull.auth.oauth2.provider.token.OpenIdAuthenticationToken;
import com.anyilanxin.skillfull.auth.utils.Oauth2LogUtils;
import com.anyilanxin.skillfull.corecommon.constant.impl.AuthorizedGrantTypes;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.provider.*;
import org.springframework.security.oauth2.provider.token.AbstractTokenGranter;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;

import java.util.Map;

/**
 * 开放ip模式
 *
 * @author zxiaozhou
 * @date 2022-02-12 20:43
 * @since JDK1.8
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
