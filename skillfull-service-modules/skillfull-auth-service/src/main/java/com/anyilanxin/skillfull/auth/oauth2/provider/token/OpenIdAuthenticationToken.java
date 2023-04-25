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
public class OpenIdAuthenticationToken extends UsernamePasswordAuthenticationToken {
    private final String openId;

    public OpenIdAuthenticationToken(String openId) {
        super(openId, "");
        this.openId = openId;
        setAuthenticated(false);
    }


    public OpenIdAuthenticationToken(String openId,
                                     Collection<? extends GrantedAuthority> authorities) {
        super(openId, "", authorities);
        this.openId = openId;
        super.setAuthenticated(true);
    }


    @Override
    public Object getCredentials() {
        return null;
    }


    @Override
    public Object getPrincipal() {
        return this.openId;
    }
}
