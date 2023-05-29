

package com.anyilanxin.anyicloud.auth.oauth2.provider.token;

import java.util.Collection;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

/**
 * 短信验证码token
 *
 * @author zxh
 * @date 2022-02-13 00:12
 * @since 1.0.0
 */
public class SmsCodeAuthenticationToken extends UsernamePasswordAuthenticationToken {
    private final String phone;
    private String smsCode;

    public SmsCodeAuthenticationToken(String phone, String smsCode) {
        super(phone, smsCode);
        this.phone = phone;
        this.smsCode = smsCode;
        setAuthenticated(false);
    }


    public SmsCodeAuthenticationToken(String phone, String smsCode, Collection<? extends GrantedAuthority> authorities) {
        super(phone, smsCode, authorities);
        this.phone = phone;
        this.smsCode = smsCode;
        super.setAuthenticated(true);
    }


    @Override
    public Object getCredentials() {
        return this.smsCode;
    }


    @Override
    public Object getPrincipal() {
        return this.phone;
    }
}
