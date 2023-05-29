

package com.anyilanxin.anyicloud.auth.modules.detail.servive.impl;

import com.anyilanxin.anyicloud.auth.modules.login.service.IUserAuthService;
import com.anyilanxin.anyicloud.auth.utils.Oauth2LogUtils;
import com.anyilanxin.anyicloud.oauth2common.authinfo.SkillFullUserDetails;
import com.anyilanxin.anyicloud.oauth2common.utils.Oauth2CommonUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * 密码模式UserDetailsService实现
 *
 * @author zxh
 * @date 2022-02-11 09:32
 * @since 1.0.0
 */
@Service
@RequiredArgsConstructor
public class PasswordDetailsService implements UserDetailsService {
    private final IUserAuthService authService;

    @Override
    public UserDetails loadUserByUsername(String accountOrPhone) throws UsernameNotFoundException {
        SkillFullUserDetails userDetails = Oauth2CommonUtils.toUserDetails(authService.getUserByAccountPhone(accountOrPhone));
        Oauth2LogUtils.setUserDetailInfo(userDetails);
        return userDetails;
    }
}
