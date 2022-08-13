// +----------------------------------------------------------------------
// | SkillFull快速开发平台 [ SkillFull ]
// +----------------------------------------------------------------------
// | 版权所有 2020~2021 zxiaozhou
// +----------------------------------------------------------------------
// | 官方网站: https://www.divisu.com
// +----------------------------------------------------------------------
// | 作者: zxiaozhou <z7630853@163.com>
// +----------------------------------------------------------------------
package com.anyilanxin.skillfull.auth.oauth2.granter;

import com.anyilanxin.skillfull.auth.oauth2.provider.token.ClientCredentialsAuthenticationToken;
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
 * 客户端模式
 *
 * @author zxiaozhou
 * @date 2022-02-12 20:42
 * @since JDK1.8
 */
@Slf4j
public class ClientCredentialsTokenGranter extends AbstractTokenGranter {
    private final AuthenticationManager authenticationManager;

    public ClientCredentialsTokenGranter(AuthenticationManager authenticationManager, AuthorizationServerTokenServices tokenServices, ClientDetailsService clientDetailsService, OAuth2RequestFactory requestFactory) {
        super(tokenServices, clientDetailsService, requestFactory, AuthorizedGrantTypes.CLIENT_CREDENTIALS.getType());
        this.authenticationManager = authenticationManager;
    }

    @Override
    protected OAuth2Authentication getOAuth2Authentication(ClientDetails client, TokenRequest tokenRequest) {
        Oauth2LogUtils.setPreAuthLog(client, tokenRequest);
        Map<String, String> requestParameters = tokenRequest.getRequestParameters();
        String clientId = requestParameters.get("client_id");
        String clientSecurity = requestParameters.get("client_security");
        ClientCredentialsAuthenticationToken credentialsAuthenticationToken = new ClientCredentialsAuthenticationToken(clientId, clientSecurity);
        Authentication authenticate = authenticationManager.authenticate(credentialsAuthenticationToken);
        OAuth2Request oAuth2Request = getRequestFactory().createOAuth2Request(client, tokenRequest);
        return new OAuth2Authentication(oAuth2Request, authenticate);
    }
}
