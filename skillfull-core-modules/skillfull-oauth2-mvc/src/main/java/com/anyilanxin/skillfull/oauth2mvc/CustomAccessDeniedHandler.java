package com.anyilanxin.skillfull.oauth2mvc;

import com.anyilanxin.skillfull.corecommon.constant.Status;
import com.anyilanxin.skillfull.oauth2mvc.utils.ResponseUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static com.anyilanxin.skillfull.corecommon.utils.I18nUtil.getLocalMessage;

/**
 * 授权用户鉴权异常
 *
 * @author zxiaozhou
 * @date 2022-03-02 14:36
 * @since JDK1.8
 */
@Slf4j
public class CustomAccessDeniedHandler implements AccessDeniedHandler {
    private final static Map<String, String> LOCAL = new HashMap<>(64);

    static {
        LOCAL.put("Access is denied", "CustomAccessDeniedHandler.accessIsDenied");
    }

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException {
        log.error("------------CustomAccessDeniedHandler------------>handle:异常消息:\n{}", accessDeniedException.getLocalizedMessage());
        ResponseUtils.writeResult(response, getLocalMessage(LOCAL, accessDeniedException.getMessage()), Status.ACCESS_DENIED);
    }
}
