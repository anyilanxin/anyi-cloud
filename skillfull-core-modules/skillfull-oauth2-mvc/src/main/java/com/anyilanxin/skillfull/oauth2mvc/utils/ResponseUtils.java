// +----------------------------------------------------------------------
// | SkillFull快速开发平台 [ SkillFull ]
// +----------------------------------------------------------------------
// | 版权所有 2020~2021 zxiaozhou
// +----------------------------------------------------------------------
// | 官方网站: https://www.divisu.com
// +----------------------------------------------------------------------
// | 作者: zxiaozhou <z7630853@163.com>
// +----------------------------------------------------------------------
package com.anyilanxin.skillfull.oauth2mvc.utils;

import com.anyilanxin.skillfull.corecommon.base.Result;
import com.anyilanxin.skillfull.corecommon.constant.Status;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 响应工具
 *
 * @author zxiaozhou
 * @date 2022-03-02 14:58
 * @since JDK1.8
 */
public class ResponseUtils {
    private final static ObjectMapper OBJECTMAPPER = new ObjectMapper();

    /**
     * 流写响应消息
     *
     * @param response ${@link HttpServletResponse}
     * @param msg      ${@link String}
     * @param status   ${@link Status}
     * @author zxiaozhou
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
     * @author zxiaozhou
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
