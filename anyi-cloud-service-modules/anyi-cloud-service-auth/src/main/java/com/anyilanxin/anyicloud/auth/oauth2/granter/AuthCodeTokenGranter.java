

package com.anyilanxin.anyicloud.auth.oauth2.granter;

import com.anyilanxin.anyicloud.auth.utils.Oauth2LogUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.provider.*;
import org.springframework.security.oauth2.provider.code.AuthorizationCodeServices;
import org.springframework.security.oauth2.provider.code.AuthorizationCodeTokenGranter;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;

/**
 * 授权码模式
 *
 * @author zxh
 * @date 2022-02-12 20:42
 * @since 1.0.0
 */
@Slf4j
public class AuthCodeTokenGranter extends AuthorizationCodeTokenGranter {

    public AuthCodeTokenGranter(AuthorizationServerTokenServices tokenServices, AuthorizationCodeServices authorizationCodeServices, ClientDetailsService clientDetailsService, OAuth2RequestFactory requestFactory) {
        super(tokenServices, authorizationCodeServices, clientDetailsService, requestFactory);
    }


    @Override
    protected OAuth2Authentication getOAuth2Authentication(ClientDetails client, TokenRequest tokenRequest) {
        Oauth2LogUtils.setPreAuthLog(client, tokenRequest);
        return super.getOAuth2Authentication(client, tokenRequest);
    }
}
