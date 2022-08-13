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

import com.anyilanxin.skillfull.auth.utils.Oauth2LogUtils;
import com.anyilanxin.skillfull.corecommon.constant.impl.AuthorizedGrantTypes;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.provider.*;
import org.springframework.security.oauth2.provider.token.AbstractTokenGranter;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;

/**
 * 刷新token
 *
 * @author zxiaozhou
 * @date 2022-02-12 20:41
 * @since JDK1.8
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
