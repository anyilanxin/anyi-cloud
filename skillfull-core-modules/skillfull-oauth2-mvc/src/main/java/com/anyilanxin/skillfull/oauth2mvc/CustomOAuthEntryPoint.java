package com.anyilanxin.skillfull.oauth2mvc;


import com.anyilanxin.skillfull.corecommon.constant.Status;
import com.anyilanxin.skillfull.oauth2mvc.utils.ResponseUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static com.anyilanxin.skillfull.corecommon.utils.I18nUtil.getLocalMessage;

/**
 * 匿名账户鉴权异常
 *
 * @author zxiaozhou
 * @date 2022-03-02 14:50
 * @since JDK1.8
 */
@Slf4j
public class CustomOAuthEntryPoint implements AuthenticationEntryPoint {
    private final static Map<String, String> LOCAL = new HashMap<>(64);

    static {
        LOCAL.put("Full authentication is required to access this resource", "CustomOAuthEntryPoint.FullAuthentication");
    }


    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
        log.error("------------CustomOAuthEntryPoint------------>commence--->异常消息:\n{}", authException.getMessage());
        ResponseUtils.writeResult(response, getLocalMessage(LOCAL, authException.getMessage()), Status.ACCESS_ERROR);
    }


}

