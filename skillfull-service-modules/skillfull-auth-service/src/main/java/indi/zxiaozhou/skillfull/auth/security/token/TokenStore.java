// +----------------------------------------------------------------------
// | SkillFull快速开发平台 [ SkillFull ]
// +----------------------------------------------------------------------
// | 版权所有 2020~2021 zxiaozhou
// +----------------------------------------------------------------------
// | 官方网站: https://www.divisu.com
// +----------------------------------------------------------------------
// | 作者: zxiaozhou <z7630853@163.com>
// +----------------------------------------------------------------------
package indi.zxiaozhou.skillfull.auth.security.token;

import indi.zxiaozhou.skillfull.auth.security.login.service.model.CustomUserDetails;
import indi.zxiaozhou.skillfull.corecommon.base.model.login.LoginUserInfoModel;
import org.springframework.security.core.Authentication;

/**
 * token存储
 *
 * @author zxiaozhou
 * @date 2020-07-17 14:36
 * @since JDK11
 */
public interface TokenStore {


    /**
     * 创建授权
     *
     * @param authentication ${@link Authentication} 授权信息
     * @param userDetails    ${@link CustomUserDetails} 授权成功响应信息
     * @return LoginSuccessAuthenticationToken ${@link LoginSuccessAuthenticationToken} 登录成功授权信息
     * @author zxiaozhou
     * @date 2020-07-17 15:46
     */
    LoginSuccessAuthenticationToken createAuthentication(Authentication authentication, CustomUserDetails userDetails);


    /**
     * 刷新授权信息
     *
     * @param userInfo ${@link LoginUserInfoModel}
     * @author zxiaozhou
     * @date 2021-07-12 16:49
     */
    void refreshAuthenticationInfo(Authentication authentication, LoginUserInfoModel userInfo);


    /**
     * 刷新授权信息与token
     *
     * @param userDetails ${@link CustomUserDetails}
     * @param token       ${@link String}
     * @return LoginSuccessAuthenticationToken ${@link LoginSuccessAuthenticationToken}
     * @author zxiaozhou
     * @date 2021-07-12 16:49
     */
    LoginSuccessAuthenticationToken refreshAuthentication(CustomUserDetails userDetails, String token);


    /**
     * 获取授权信息
     *
     * @param token ${@link String} token信息
     * @return LoginSuccessAuthenticationToken ${@link LoginSuccessAuthenticationToken} 登录成功授权信息
     * @author zxiaozhou
     * @date 2020-07-17 15:46
     */
    LoginSuccessAuthenticationToken getAuthentication(String token);


    /**
     * 移除授权信息
     *
     * @param token ${@link String} token
     * @author zxiaozhou
     * @date 2021-06-03 08:29
     */
    void removeAuthentication(String token);
}
