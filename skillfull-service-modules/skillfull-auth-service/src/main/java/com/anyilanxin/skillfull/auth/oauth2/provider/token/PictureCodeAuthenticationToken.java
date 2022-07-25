// +----------------------------------------------------------------------
// | SkillFull快速开发平台 [ SkillFull ]
// +----------------------------------------------------------------------
// | 版权所有 2020~2021 zxiaozhou
// +----------------------------------------------------------------------
// | 官方网站: https://www.divisu.com
// +----------------------------------------------------------------------
// | 作者: zxiaozhou <z7630853@163.com>
// +----------------------------------------------------------------------
package com.anyilanxin.skillfull.auth.oauth2.provider.token;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

/**
 * 图片验证码token
 *
 * @author zxiaozhou
 * @date 2022-02-13 00:11
 * @since JDK1.8
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


    public PictureCodeAuthenticationToken(String accountOrPhone, String password, String pictureCode, String pictureCodeId,
                                          Collection<? extends GrantedAuthority> authorities) {
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
