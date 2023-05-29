

package com.anyilanxin.anyicloud.oauth2mvc.utils;

import com.anyilanxin.anyicloud.corecommon.base.Result;
import com.anyilanxin.anyicloud.corecommon.constant.Status;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

/**
 * 响应工具
 *
 * @author zxh
 * @date 2022-03-02 14:58
 * @since 1.0.0
 */
public class ResponseUtils {
    private static final ObjectMapper OBJECTMAPPER = new ObjectMapper();

    /**
     * 流写响应消息
     *
     * @param response ${@link HttpServletResponse}
     * @param msg      ${@link String}
     * @param status   ${@link Status}
     * @author zxh
     * @date 2022-03-02 15:01
     */
    public static void writeResult(HttpServletResponse response, String msg, Status status) throws IOException {
        writeResult(response, msg, status, status.getStatus());
    }


    /**
     * 流写响应消息
     *
     * @param response ${@link HttpServletResponse}
     * @param msg      ${@link String}
     * @param status   ${@link Status}
     * @author zxh
     * @date 2022-03-02 15:01
     */
    public static void writeResult(HttpServletResponse response, String msg, Status status, HttpStatus httpStatus) throws IOException {
        Result<String> result = new Result<>(status, msg);
        response.setStatus(httpStatus.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.getWriter().print(OBJECTMAPPER.writeValueAsString(result));
        response.getWriter().flush();
    }
}
