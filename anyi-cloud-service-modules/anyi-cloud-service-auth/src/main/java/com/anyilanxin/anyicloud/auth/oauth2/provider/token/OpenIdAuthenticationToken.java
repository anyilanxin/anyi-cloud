

package com.anyilanxin.anyicloud.auth.oauth2.provider.token;

import java.util.Collection;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

/**
 * open id token
 *
 * @author zxh
 * @date 2022-02-13 00:12
 * @since 1.0.0
 */
public class OpenIdAuthenticationToken extends UsernamePasswordAuthenticationToken {
    private final String openId;

    public OpenIdAuthenticationToken(String openId) {
        super(openId, "");
        this.openId = openId;
        setAuthenticated(false);
    }


    public OpenIdAuthenticationToken(String openId, Collection<? extends GrantedAuthority> authorities) {
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
