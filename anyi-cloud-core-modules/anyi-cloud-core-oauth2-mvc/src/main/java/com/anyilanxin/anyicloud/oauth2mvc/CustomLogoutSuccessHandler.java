

package com.anyilanxin.anyicloud.oauth2mvc;

import com.anyilanxin.anyicloud.corecommon.constant.Status;
import com.anyilanxin.anyicloud.oauth2mvc.utils.ResponseUtils;

import java.io.IOException;
import java.util.Objects;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.authentication.TokenExtractor;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

/**
 * @author zxh
 * @date 2022-04-09 22:40
 * @since 1.0.0
 */
@RequiredArgsConstructor
public class CustomLogoutSuccessHandler implements LogoutSuccessHandler {
    private final TokenExtractor bearerTokenExtractor;
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
