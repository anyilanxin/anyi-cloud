

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
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

/**
 * 授权用户鉴权异常
 *
 * @author zxh
 * @date 2022-03-02 14:36
 * @since 1.0.0
 */
@Slf4j
public class CustomAccessDeniedHandler implements AccessDeniedHandler {
    private static final Map<String, String> LOCAL = new HashMap<>(64);

    static {
        LOCAL.put("Access is denied", "CustomAccessDeniedHandler.accessIsDenied");
    }

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException {
        log.error("------------CustomAccessDeniedHandler------------>handle:异常消息:\n{}", accessDeniedException.getLocalizedMessage());
        ResponseUtils.writeResult(response, getLocalMessage(LOCAL, accessDeniedException.getMessage()), Status.ACCESS_DENIED);
    }
}
