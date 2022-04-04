// +----------------------------------------------------------------------
// | SkillFull快速开发平台 [ SkillFull ]
// +----------------------------------------------------------------------
// | 版权所有 2020~2021 zxiaozhou
// +----------------------------------------------------------------------
// | 官方网站: https://www.divisu.com
// +----------------------------------------------------------------------
// | 作者: zxiaozhou <z7630853@163.com>
// +----------------------------------------------------------------------
package indi.zxiaozhou.skillfull.auth.security.login.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import indi.zxiaozhou.skillfull.auth.core.constant.impl.LoginType;
import indi.zxiaozhou.skillfull.auth.modules.rbac.entity.RbacUserEntity;
import indi.zxiaozhou.skillfull.auth.security.config.properties.SecurityProperties;
import indi.zxiaozhou.skillfull.auth.security.login.service.IWebLoginUserCenterService;
import indi.zxiaozhou.skillfull.auth.security.login.service.model.CustomUserDetails;
import indi.zxiaozhou.skillfull.corecommon.base.model.login.LoginUserInfoModel;
import indi.zxiaozhou.skillfull.corecommon.constant.impl.LoginEndpointType;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;


/**
 * security user detail
 *
 * @author zxiaozhou
 * @date 2020-06-28 12:17
 * @since JDK11
 */
@RequiredArgsConstructor
@Service("webLoginUserDetailsService")
@Slf4j
public class WebLoginUserDetailsServiceImpl implements UserDetailsService {
    private final IWebLoginUserCenterService service;
    private final SecurityProperties properties;


    @SneakyThrows
    @Override
    @Transactional(rollbackFor = {Exception.class, Error.class}, readOnly = true)
    public CustomUserDetails loadUserByUsername(String principal) {
        log.debug("------------WebLoginUserDetailsServiceImpl------这是web端登录------>loadUserByUsername:{}", principal);
        // 获取用户信息
        LambdaQueryWrapper<RbacUserEntity> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.and(v -> v.eq(RbacUserEntity::getUserName, principal).or()
                .eq(RbacUserEntity::getEmail, principal).or()
                .eq(RbacUserEntity::getPhone, principal));
        RbacUserEntity user = service.getOne(queryWrapper);
        if (Objects.isNull(user)) {
            throw new UsernameNotFoundException("用户不存在");
        } else if (user.getUserStatus() == 0) {
            throw new BadCredentialsException("账号未激活,请联系管理员");
        } else if (user.getUserStatus() == 2) {
            throw new BadCredentialsException("账号已经被冻结,请联系管理员");
        }
        // 组装用户信息
        LoginUserInfoModel userInfo = service.getLoinUserInfo(user);
        return new CustomUserDetails(LoginEndpointType.WEB,
                userInfo,
                properties.getTokenDetectInSeconds(),
                properties.getTokenValidityInSeconds(),
                properties.getTokenRenewInSeconds(),
                LoginType.MORE_EQUIPMENT
        );
    }
}

