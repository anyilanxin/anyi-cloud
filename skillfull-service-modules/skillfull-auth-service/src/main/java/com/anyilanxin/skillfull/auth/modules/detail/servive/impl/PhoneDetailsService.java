package com.anyilanxin.skillfull.auth.modules.detail.servive.impl;

import com.anyilanxin.skillfull.auth.modules.login.service.IUserAuthService;
import com.anyilanxin.skillfull.auth.utils.Oauth2LogUtils;
import com.anyilanxin.skillfull.oauth2common.authinfo.SkillFullUserDetails;
import com.anyilanxin.skillfull.oauth2common.utils.Oauth2CommonUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * 电话号码模式UserDetailsService实现
 *
 * @author zxiaozhou
 * @date 2022-02-11 09:32
 * @since JDK1.8
 */
@Service
@RequiredArgsConstructor
public class PhoneDetailsService implements UserDetailsService {
    private final IUserAuthService authService;


    @Override
    public UserDetails loadUserByUsername(String phone) throws UsernameNotFoundException {
        SkillFullUserDetails userDetails = Oauth2CommonUtils.toUserDetails(authService.getUserByPhone(phone));
        Oauth2LogUtils.setUserDetailInfo(userDetails);
        return userDetails;
    }
}
