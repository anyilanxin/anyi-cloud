// +----------------------------------------------------------------------
// | SkillFull快速开发平台 [ SkillFull ]
// +----------------------------------------------------------------------
// | 版权所有 2020~2021 zxiaozhou
// +----------------------------------------------------------------------
// | 官方网站: https://www.divisu.com
// +----------------------------------------------------------------------
// | 作者: zxiaozhou <z7630853@163.com>
// +----------------------------------------------------------------------
package indi.zxiaozhou.skillfull.coremvc.handle;

import indi.zxiaozhou.skillfull.corecommon.base.Result;
import indi.zxiaozhou.skillfull.corecommon.constant.Status;
import indi.zxiaozhou.skillfull.coremvc.base.controller.BaseController;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 404异常处理
 *
 * @author zxiaozhou
 * @date 2021-05-28 01:56
 * @since JDK1.8
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
