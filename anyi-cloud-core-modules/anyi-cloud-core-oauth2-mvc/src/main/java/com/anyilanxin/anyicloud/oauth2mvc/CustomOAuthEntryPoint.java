

package com.anyilanxin.anyicloud.oauth2mvc;

import static com.anyilanxin.anyicloud.corecommon.utils.I18nUtil.getLocalMessage;

import com.anyilanxin.anyicloud.corecommon.constant.Status;
import com.anyilanxin.anyicloud.oauth2mvc.utils.ResponseUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

/**
 * 匿名账户鉴权异常
 *
 * @author zxh
 * @date 2022-03-02 14:50
 * @since 1.0.0
 */
@Slf4j
public class CustomOAuthEntryPoint implements AuthenticationEntryPoint {
    private static final Map<String, String> LOCAL = new HashMap<>(64);

    static {
        LOCAL.put("Full authentication is required to access this resource", "CustomOAuthEntryPoint.FullAuthentication");
    }

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
        log.error("------------CustomOAuthEntryPoint------------>commence--->异常消息:\n{}", authException.getMessage());
        ResponseUtils.writeResult(response, getLocalMessage(LOCAL, authException.getMessage()), Status.ACCESS_ERROR);
    }
}
