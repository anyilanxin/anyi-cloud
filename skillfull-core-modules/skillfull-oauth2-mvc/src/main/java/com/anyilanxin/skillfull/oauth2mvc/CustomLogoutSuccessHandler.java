// +----------------------------------------------------------------------
// | SkillFull快速开发平台 [ SkillFull ]
// +----------------------------------------------------------------------
// | 版权所有 2020~2021 zxiaozhou
// +----------------------------------------------------------------------
// | 官方网站: https://www.divisu.com
// +----------------------------------------------------------------------
// | 作者: zxiaozhou <z7630853@163.com>
// +----------------------------------------------------------------------
package com.anyilanxin.skillfull.oauth2mvc;

import com.anyilanxin.skillfull.corecommon.constant.Status;
import com.anyilanxin.skillfull.oauth2mvc.utils.ResponseUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.authentication.BearerTokenExtractor;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

/**
 * @author zxiaozhou
 * @date 2022-04-09 22:40
 * @since JDK1.8
 */
@RequiredArgsConstructor
public class CustomLogoutSuccessHandler implements LogoutSuccessHandler {
    private final BearerTokenExtractor bearerTokenExtractor;
    private final TokenStore tokenStore;

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        Authentication extract = bearerTokenExtractor.extract(request);
        Object principal = extract.getPrincipal();
        if (Objects.nonNull(principal)) {
            String token = principal.toString();
            OAuth2AccessToken accessToken = tokenStore.readAccessToken(token);
            if (Objects.nonNull(accessToken)) {
                tokenStore.removeAccessToken(accessToken);
            }
        }
        ResponseUtils.writeResult(response, "退出登录成功", Status.SUCCESS);
    }
}
