// +----------------------------------------------------------------------
// | SkillFull快速开发平台 [ SkillFull ]
// +----------------------------------------------------------------------
// | 版权所有 2020~2021 zxiaozhou
// +----------------------------------------------------------------------
// | 官方网站: https://www.divisu.com
// +----------------------------------------------------------------------
// | 作者: zxiaozhou <z7630853@163.com>
// +----------------------------------------------------------------------
package indi.zxiaozhou.skillfull.auth.security.authorization.wx;


import indi.zxiaozhou.skillfull.auth.security.login.service.model.CustomUserDetails;
import indi.zxiaozhou.skillfull.auth.security.token.LoginSuccessAuthenticationToken;
import indi.zxiaozhou.skillfull.auth.security.token.TokenStore;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * 图片验证码
 *
 * @author zxiaozhou
 * @date 2020-06-28 20:23
 * @since JDK11
 */
@RequiredArgsConstructor
@Slf4j
public class WeiXinAuthenticationProvider implements AuthenticationProvider {
    private final UserDetailsService service;
    private final TokenStore tokenStore;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        WeiXinAuthenticationToken token = (WeiXinAuthenticationToken) authentication;
        //获取用户信息
        CustomUserDetails userDetails = (CustomUserDetails) service.loadUserByUsername(token.getPrincipal().toString());
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
        return aClass.equals(WeiXinAuthenticationToken.class);
    }
}
