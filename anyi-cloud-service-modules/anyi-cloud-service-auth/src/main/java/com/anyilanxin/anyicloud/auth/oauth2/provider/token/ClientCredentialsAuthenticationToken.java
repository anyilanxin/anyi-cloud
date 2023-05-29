

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
public class ClientCredentialsAuthenticationToken extends UsernamePasswordAuthenticationToken {
    private final String clientId;
    private String credentials;

    public ClientCredentialsAuthenticationToken(String clientId, String credentials) {
        super(clientId, credentials);
        this.clientId = clientId;
        this.credentials = credentials;
        setAuthenticated(false);
    }


    public ClientCredentialsAuthenticationToken(String clientId, String credentials, Collection<? extends GrantedAuthority> authorities) {
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
