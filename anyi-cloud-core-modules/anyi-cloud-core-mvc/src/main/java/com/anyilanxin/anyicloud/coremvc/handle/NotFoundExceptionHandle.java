

package com.anyilanxin.anyicloud.coremvc.handle;

import com.anyilanxin.anyicloud.corecommon.base.Result;
import com.anyilanxin.anyicloud.corecommon.constant.Status;
import com.anyilanxin.anyicloud.coremvc.base.controller.BaseController;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.tags.Tag;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 404异常处理
 *
 * @author zxh
 * @date 2021-05-28 01:56
 * @since 1.0.0
 */
@Controller
@Slf4j
@Tag(name = "NotFoundExceptionHandle", description = "404处理")
@Hidden
public class NotFoundExceptionHandle extends BaseController implements ErrorController {

    @RequestMapping("/error")
    @ResponseBody
    public Result<String> error(HttpServletRequest request, HttpServletResponse response) {
        response.setStatus(HttpStatus.NOT_FOUND.value());
        return fail(Status.REQUEST_NOT_FOUND, "请求页面不存在:" + request.getMethod() + " " + request.getRequestURI());
    }
}
