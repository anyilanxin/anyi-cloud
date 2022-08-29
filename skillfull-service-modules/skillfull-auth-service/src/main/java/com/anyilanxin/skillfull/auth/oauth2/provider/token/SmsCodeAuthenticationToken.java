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
 * 短信验证码token
 *
 * @author zxiaozhou
 * @date 2022-02-13 00:12
 * @since JDK1.8
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


    public SmsCodeAuthenticationToken(String phone, String smsCode,
                                      Collection<? extends GrantedAuthority> authorities) {
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
