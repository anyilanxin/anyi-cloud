// +----------------------------------------------------------------------
// | SkillFull快速开发平台 [ SkillFull ]
// +----------------------------------------------------------------------
// | 版权所有 2020~2021 zxiaozhou
// +----------------------------------------------------------------------
// | 官方网站: https://www.divisu.com
// +----------------------------------------------------------------------
// | 作者: zxiaozhou <z7630853@163.com>
// +----------------------------------------------------------------------
package indi.zxiaozhou.skillfull.auth.security.authorization.web.sms;


import indi.zxiaozhou.skillfull.auth.modules.rbac.entity.RbacUserEntity;
import indi.zxiaozhou.skillfull.auth.security.login.service.IWebLoginUserCenterService;
import indi.zxiaozhou.skillfull.auth.security.login.service.model.CustomUserDetails;
import indi.zxiaozhou.skillfull.auth.security.token.LoginSuccessAuthenticationToken;
import indi.zxiaozhou.skillfull.auth.security.token.TokenStore;
import indi.zxiaozhou.skillfull.auth.security.validate.CheckDto;
import indi.zxiaozhou.skillfull.auth.security.validate.CheckModel;
import indi.zxiaozhou.skillfull.auth.security.validate.IValidate;
import indi.zxiaozhou.skillfull.auth.security.validate.impl.SmsValidate;
import indi.zxiaozhou.skillfull.corecommon.base.model.login.LoginUserInfoModel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.time.LocalDateTime;

/**
 * 短信验证码
 *
 * @author zxiaozhou
 * @date 2020-06-28 20:23
 * @since JDK11
 */
@RequiredArgsConstructor
@Slf4j
public class SmsAuthenticationProvider implements AuthenticationProvider {
    private final UserDetailsService service;
    private IValidate validate;
    private final TokenStore tokenStore;
    private final IWebLoginUserCenterService centerService;

    @Autowired
    public void setValidate(SmsValidate validate) {
        this.validate = validate;
    }


    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        SmsAuthenticationToken token = (SmsAuthenticationToken) authentication;
        if (StringUtils.isBlank(token.getPrincipal().toString())) {
            throw new BadCredentialsException("手机号不能为空");
        }
        if (StringUtils.isBlank(token.getCredentials().toString())) {
            throw new BadCredentialsException("短信验证码不能为空");
        }
        CheckModel model = new CheckModel(token.getPrincipal().toString(), token.getCredentials().toString());
        CheckDto result = validate.checkVerification(model);
        if (!result.isResult()) {
            throw new BadCredentialsException(result.getMsg());
        }
        // 获取用户信息
        CustomUserDetails userDetails = (CustomUserDetails) service.loadUserByUsername(token.getPrincipal().toString());
        LoginUserInfoModel loginUserInfoModel = userDetails.getUserInfoModel();
        // 设置登录信息
        RbacUserEntity rbacUserEntity = new RbacUserEntity();
        rbacUserEntity.setUserId(loginUserInfoModel.getUserId());
        rbacUserEntity.setCurrentLoginTime(LocalDateTime.now());
        rbacUserEntity.setUpdateUserName(loginUserInfoModel.getRealName());
        rbacUserEntity.setUpdateUserId(loginUserInfoModel.getUserId());
        rbacUserEntity.setLoginFailErrorNum(0);
        boolean b = centerService.updateById(rbacUserEntity);
        if (!b) {
            log.error("------------PictureAuthenticationProvider------------>authenticate:{}", "更新用户登录状态失败");
        }
        return createSuccessAuthentication(authentication, userDetails);
    }


    /**
     * 创建登录成功授权信息
     *
     * @param authentication ${@link Authentication} 授权信息
     * @param userDetails    ${@link UserDetails} 用户详细
     * @return Authentication ${@link Authentication} 返回新的授权信息
     * @author zxiaozhou
     * @date 2020-06-29 15:37
     */
    private LoginSuccessAuthenticationToken createSuccessAuthentication(Authentication authentication, CustomUserDetails userDetails) {
        return tokenStore.createAuthentication(authentication, userDetails);
    }


    @Override
    public boolean supports(Class<?> aClass) {
        return aClass.equals(SmsAuthenticationToken.class);
    }
}
