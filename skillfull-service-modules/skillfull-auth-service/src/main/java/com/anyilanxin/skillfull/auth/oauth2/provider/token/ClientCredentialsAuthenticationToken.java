// +----------------------------------------------------------------------
// | SkillFull快速开发平台 [ SkillFull ]
// +----------------------------------------------------------------------
// | 版权所有 2020~2021 zxiaozhou
// +----------------------------------------------------------------------
// | 官方网站: https://skillfull.divisu.com
// +----------------------------------------------------------------------
// | 作者: zxiaozhou <z7630853@163.com>
// +----------------------------------------------------------------------
package com.anyilanxin.skillfull.auth.oauth2.provider.token;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

/**
 * open id token
 *
 * @author zxiaozhou
 * @date 2022-02-13 00:12
 * @since JDK1.8
 */
public class ClientCredentialsAuthenticationToken extends UsernamePasswordAuthenticationToken {
    private final String clientId;
    private String credentials;

    public ClientCredentialsAuthenticationToken(String clientId, String credentials) {
        super(clientId, credentials);
        this.clientId = clientId;
        this.credentials = credentials;
        setAuthenticated(false);
    }


    public ClientCredentialsAuthenticationToken(String clientId, String credentials,
                                                Collection<? extends GrantedAuthority> authorities) {
        super(clientId, credentials);
        this.clientId = clientId;
        this.credentials = credentials;
        super.setAuthenticated(true);
    }


    @Override
    public Object getCredentials() {
        return this.credentials;
    }


    @Override
    public Object getPrincipal() {
        return this.clientId;
    }
}
