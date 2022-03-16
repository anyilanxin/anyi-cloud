// +----------------------------------------------------------------------
// | SkillFull快速开发平台 [ SkillFull ]
// +----------------------------------------------------------------------
// | 版权所有 2020~2021 zxiaozhou
// +----------------------------------------------------------------------
// | 官方网站: https://www.divisu.com
// +----------------------------------------------------------------------
// | 作者: zxiaozhou <z7630853@163.com>
// +----------------------------------------------------------------------
package indi.zxiaozhou.skillfull.auth.security.authorization.web.picture;


import indi.zxiaozhou.skillfull.auth.modules.rbac.entity.RbacUserEntity;
import indi.zxiaozhou.skillfull.auth.security.config.properties.SecurityProperties;
import indi.zxiaozhou.skillfull.auth.security.login.service.IWebLoginUserCenterService;
import indi.zxiaozhou.skillfull.auth.security.login.service.model.CustomUserDetails;
import indi.zxiaozhou.skillfull.auth.security.token.LoginSuccessAuthenticationToken;
import indi.zxiaozhou.skillfull.auth.security.token.TokenStore;
import indi.zxiaozhou.skillfull.auth.security.validate.CheckDto;
import indi.zxiaozhou.skillfull.auth.security.validate.CheckModel;
import indi.zxiaozhou.skillfull.auth.security.validate.IValidate;
import indi.zxiaozhou.skillfull.auth.security.validate.impl.PictureValidate;
import indi.zxiaozhou.skillfull.auth.utils.CryptAuthUtils;
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
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.time.LocalDateTime;

/**
 * 图片验证码
 *
 * @author zxiaozhou
 * @date 2020-06-28 20:23
 * @since JDK11
 */
@RequiredArgsConstructor
@Slf4j
public class PictureAuthenticationProvider implements AuthenticationProvider {
    private final UserDetailsService service;
    private IValidate validate;
    private final TokenStore tokenStore;
    private final SecurityProperties properties;
    private final IWebLoginUserCenterService centerService;

    @Autowired
    public void setValidate(PictureValidate validate) {
        this.validate = validate;
    }


    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        PictureAuthenticationToken token = (PictureAuthenticationToken) authentication;
        CheckModel model = (CheckModel) token.getValidate();
        if (StringUtils.isBlank(model.getCodeId())) {
            throw new BadCredentialsException("图片验证码不能为空");
        }
        if (StringUtils.isBlank(model.getCodeValue())) {
            throw new BadCredentialsException("图片验证码不能为空");
        }
        CheckDto result = validate.checkVerification(model);
        if (!result.isResult()) {
            throw new BadCredentialsException(result.getMsg());
        }
        String userName = authentication.getPrincipal().toString();
        String password = authentication.getCredentials().toString();
        if (StringUtils.isBlank(userName)) {
            throw new UsernameNotFoundException("用户名不能为空");
        }
        if (StringUtils.isBlank(password)) {
            throw new BadCredentialsException("密码不能为空");
        }
        //获取用户信息
        CustomUserDetails userDetails = (CustomUserDetails) service.loadUserByUsername(userName);
        LoginUserInfoModel loginUserInfoModel = userDetails.getUserInfoModel();
        // 验证用户登录次数
        String failMsg = "登录失败,密码不正确";
        if (properties.isOpenAuthLoginErrorLimit() && properties.getMaxLoginErrorNum() > 0) {
            if (loginUserInfoModel.getLoginFailErrorNum() > properties.getMaxLoginErrorNum()) {
                throw new BadCredentialsException("密码错误次数过多,账号已经锁定,请通知管理员或使用短信验证码登录");
            }
            failMsg += ",剩余" + (properties.getMaxLoginErrorNum() - loginUserInfoModel.getLoginFailErrorNum() - 1) + "次重试机会";
        }
        RbacUserEntity rbacUserEntity = new RbacUserEntity();
        rbacUserEntity.setUserId(loginUserInfoModel.getUserId());
        rbacUserEntity.setUpdateUserName(loginUserInfoModel.getRealName());
        rbacUserEntity.setCurrentLoginTime(LocalDateTime.now());
        rbacUserEntity.setUpdateUserId(loginUserInfoModel.getUserId());
        if (!CryptAuthUtils.matches(password, loginUserInfoModel.getSalt(), userDetails.getPassword())) {
            // 设置登录信息
            rbacUserEntity.setLoginFailErrorNum(loginUserInfoModel.getLoginFailErrorNum() + 1);
            boolean b = centerService.updateById(rbacUserEntity);
            if (!b) {
                log.error("------------PictureAuthenticationProvider------------>authenticate:{},{}", "更新用户登录状态失败", authentication);
            }
            throw new BadCredentialsException(failMsg);
        }
        // 设置登录信息
        rbacUserEntity.setLoginFailErrorNum(0);
        boolean b = centerService.updateById(rbacUserEntity);
        if (!b) {
            log.error("------------PictureAuthenticationProvider------------>authenticate:{},{}", "更新用户登录状态失败", authentication);
        }
        return createSuccessAuthentication(authentication, userDetails);
    }


    /**
     * 创建授权信息
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
        return aClass.equals(PictureAuthenticationToken.class);
    }
}
