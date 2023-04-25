package com.anyilanxin.skillfull.auth.oauth2.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 授权成功处理
 *
 * @author zxiaozhou
 * @date 2022-04-16 17:23
 * @since JDK1.8
 */
@Slf4j
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        log.info("------------CustomAuthenticationSuccessHandler------------>onAuthenticationSuccess:\n{}", authentication.getDetails());
    }
}
