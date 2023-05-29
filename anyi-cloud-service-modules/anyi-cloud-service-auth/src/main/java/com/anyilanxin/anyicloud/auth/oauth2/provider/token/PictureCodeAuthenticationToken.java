

package com.anyilanxin.anyicloud.auth.oauth2.provider.token;

import java.util.Collection;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

/**
 * 图片验证码token
 *
 * @author zxh
 * @date 2022-02-13 00:11
 * @since 1.0.0
 */
public class PictureCodeAuthenticationToken extends UsernamePasswordAuthenticationToken {
    private final String accountOrPhone;
    private String password;
    private String pictureCode;
    private String pictureCodeId;

    public PictureCodeAuthenticationToken(String accountOrPhone, String password, String pictureCode, String pictureCodeId) {
        super(accountOrPhone, password);
        this.accountOrPhone = accountOrPhone;
        this.password = password;
        this.pictureCode = pictureCode;
        this.pictureCodeId = pictureCodeId;
        setAuthenticated(false);
    }


    public PictureCodeAuthenticationToken(String accountOrPhone, String password, String pictureCode, String pictureCodeId, Collection<? extends GrantedAuthority> authorities) {
        super(accountOrPhone, password, authorities);
        this.accountOrPhone = accountOrPhone;
        this.password = password;
        this.pictureCode = pictureCode;
        this.pictureCodeId = pictureCodeId;
        super.setAuthenticated(true);
    }


    @Override
    public Object getCredentials() {
        return this.password;
    }


    public String getPictureCode() {
        return this.pictureCode;
    }


    public String getPictureCodeId() {
        return this.pictureCodeId;
    }


    @Override
    public Object getPrincipal() {
        return this.accountOrPhone;
    }
}
