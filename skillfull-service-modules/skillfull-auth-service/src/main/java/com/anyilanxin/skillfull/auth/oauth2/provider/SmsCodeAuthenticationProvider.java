// +----------------------------------------------------------------------
// | SkillFull快速开发平台 [ SkillFull ]
// +----------------------------------------------------------------------
// | 版权所有 2020~2021 zxiaozhou
// +----------------------------------------------------------------------
// | 官方网站: https://skillfull.divisu.com
// +----------------------------------------------------------------------
// | 作者: zxiaozhou <z7630853@163.com>
// +----------------------------------------------------------------------
package com.anyilanxin.skillfull.auth.oauth2.provider;

import com.anyilanxin.skillfull.auth.oauth2.provider.token.SmsCodeAuthenticationToken;
import com.anyilanxin.skillfull.auth.oauth2.validate.CheckDto;
import com.anyilanxin.skillfull.auth.oauth2.validate.CheckModel;
import com.anyilanxin.skillfull.auth.oauth2.validate.IValidate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * 电话号码DaoAuthenticationProvider
 *
 * @author zxiaozhou
 * @date 2022-02-12 23:47
 * @since JDK1.8
 */
@Slf4j
public class SmsCodeAuthenticationProvider extends AbstractUserDetailsAuthenticationProvider {
    private final UserDetailsService userDetailsService;
    private final IValidate validate;

    public SmsCodeAuthenticationProvider(final UserDetailsService userDetailsService,
                                         final IValidate validate) {
        this.userDetailsService = userDetailsService;
        this.validate = validate;
    }


    @Override
    protected void additionalAuthenticationChecks(UserDetails userDetails, UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {
        SmsCodeAuthenticationToken smsCodeAuthenticationToken = (SmsCodeAuthenticationToken) authentication;
        String smsCode = smsCodeAuthenticationToken.getCredentials().toString();
        String phone = smsCodeAuthenticationToken.getPrincipal().toString();
        CheckDto checkDto = validate.checkVerification(new CheckModel(phone, smsCode));
        if (!checkDto.isResult()) {
            throw new BadCredentialsException(checkDto.getMsg());
        }
    }


    @Override
    protected UserDetails retrieveUser(String username, UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        if (userDetails == null) {
            throw new InternalAuthenticationServiceException("用户信息不存在");
        }
        return userDetails;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return SmsCodeAuthenticationToken.class.isAssignableFrom(authentication);
    }

    @Override
    public void setHideUserNotFoundExceptions(boolean hideUserNotFoundExceptions) {
        super.setHideUserNotFoundExceptions(hideUserNotFoundExceptions);
    }
}
