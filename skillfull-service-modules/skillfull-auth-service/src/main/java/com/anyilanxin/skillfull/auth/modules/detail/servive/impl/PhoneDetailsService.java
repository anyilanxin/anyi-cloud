// +----------------------------------------------------------------------
// | SkillFull快速开发平台 [ SkillFull ]
// +----------------------------------------------------------------------
// | 版权所有 2020~2021 zxiaozhou
// +----------------------------------------------------------------------
// | 官方网站: https://www.divisu.com
// +----------------------------------------------------------------------
// | 作者: zxiaozhou <z7630853@163.com>
// +----------------------------------------------------------------------
package com.anyilanxin.skillfull.auth.modules.detail.servive.impl;

import com.anyilanxin.skillfull.auth.modules.login.service.IUserAuthService;
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
        return Oauth2CommonUtils.toUserDetails(authService.getUserByPhone(phone));
    }
}
