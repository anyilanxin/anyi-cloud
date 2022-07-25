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

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.OAuth2RequestFactory;
import org.springframework.security.oauth2.provider.code.AuthorizationCodeServices;
import org.springframework.security.oauth2.provider.code.AuthorizationCodeTokenGranter;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;

/**
 * 授权码模式
 *
 * @author zxiaozhou
 * @date 2022-02-12 20:42
 * @since JDK1.8
 */
@Slf4j
public class AuthCodeTokenGranter extends AuthorizationCodeTokenGranter {


    public AuthCodeTokenGranter(AuthorizationServerTokenServices tokenServices, AuthorizationCodeServices authorizationCodeServices, ClientDetailsService clientDetailsService, OAuth2RequestFactory requestFactory) {
        super(tokenServices, authorizationCodeServices, clientDetailsService, requestFactory);
    }
}
