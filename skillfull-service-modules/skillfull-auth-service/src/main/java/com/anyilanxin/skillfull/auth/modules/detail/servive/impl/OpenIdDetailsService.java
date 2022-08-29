// +----------------------------------------------------------------------
// | SkillFull快速开发平台 [ SkillFull ]
// +----------------------------------------------------------------------
// | 版权所有 2020~2021 zxiaozhou
// +----------------------------------------------------------------------
// | 官方网站: https://skillfull.divisu.com
// +----------------------------------------------------------------------
// | 作者: zxiaozhou <z7630853@163.com>
// +----------------------------------------------------------------------
package com.anyilanxin.skillfull.auth.modules.detail.servive.impl;

import com.anyilanxin.skillfull.auth.modules.login.service.IUserAuthService;
import com.anyilanxin.skillfull.auth.utils.Oauth2LogUtils;
import com.anyilanxin.skillfull.oauth2common.authinfo.SkillFullUserDetails;
import com.anyilanxin.skillfull.oauth2common.utils.Oauth2CommonUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * open id模式UserDetailsService实现
 *
 * @author zxiaozhou
 * @date 2022-02-11 09:32
 * @since JDK1.8
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class OpenIdDetailsService implements UserDetailsService {
    private final IUserAuthService authService;


    @Override
    public UserDetails loadUserByUsername(String openId) throws UsernameNotFoundException {
        SkillFullUserDetails userDetails = Oauth2CommonUtils.toUserDetails(authService.getUserByOpenId(openId));
        Oauth2LogUtils.setUserDetailInfo(userDetails);
        return userDetails;
    }
}
