

package com.anyilanxin.anyicloud.auth.oauth2.filter;

import static com.anyilanxin.anyicloud.corecommon.utils.I18nUtil.getLocalMessage;

import com.anyilanxin.anyicloud.corecommon.constant.Status;
import com.anyilanxin.anyicloud.oauth2mvc.utils.ResponseUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

/**
 * @author zhou
 * @date 2022-06-04 15:19
 * @since 1.0.0
 */
@Slf4j
public class CustomAuthenticationFailureHandler implements AuthenticationFailureHandler {
    private static final Map<String, String> LOCAL = new HashMap<>(64);

    static {
        LOCAL.put("Full authentication is required to access this resource", "CustomOAuthEntryPoint.FullAuthentication");
    }

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        log.error("------------CustomOAuthEntryPoint------------>commence--->异常消息:\n{}", exception.getMessage());
        ResponseUtils.writeResult(response, getLocalMessage(LOCAL, exception.getMessage()), Status.ACCESS_INFO_ERROR);
    }
}
