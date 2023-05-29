

package com.anyilanxin.anyicloud.auth.oauth2.granter;

import com.anyilanxin.anyicloud.auth.utils.Oauth2LogUtils;
import com.anyilanxin.anyicloud.corecommon.constant.impl.AuthorizedGrantTypes;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.provider.*;
import org.springframework.security.oauth2.provider.token.AbstractTokenGranter;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;

/**
 * 刷新token
 *
 * @author zxh
 * @date 2022-02-12 20:41
 * @since 1.0.0
 */
@Slf4j
public class RefreshTokenGranter extends AbstractTokenGranter {

    public RefreshTokenGranter(AuthorizationServerTokenServices tokenServices, ClientDetailsService clientDetailsService, OAuth2RequestFactory requestFactory) {
        super(tokenServices, clientDetailsService, requestFactory, AuthorizedGrantTypes.REFRESH_TOKEN.getType());
    }


    @Override
    protected OAuth2Authentication getOAuth2Authentication(ClientDetails client, TokenRequest tokenRequest) {
        Oauth2LogUtils.setPreAuthLog(client, tokenRequest);
        return super.getOAuth2Authentication(client, tokenRequest);
    }
}
