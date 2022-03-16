// +----------------------------------------------------------------------
// | SkillFull快速开发平台 [ SkillFull ]
// +----------------------------------------------------------------------
// | 版权所有 2020~2021 zxiaozhou
// +----------------------------------------------------------------------
// | 官方网站: https://www.divisu.com
// +----------------------------------------------------------------------
// | 作者: zxiaozhou <z7630853@163.com>
// +----------------------------------------------------------------------
package indi.zxiaozhou.skillfull.auth.security.handle;

import com.alibaba.fastjson.JSONObject;
import indi.zxiaozhou.skillfull.corecommon.base.Result;
import indi.zxiaozhou.skillfull.coremvc.base.controller.BaseController;
import indi.zxiaozhou.skillfull.corecommon.constant.Status;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static cn.hutool.core.util.CharsetUtil.UTF_8;

/**
 * 认证过的用户访问无权限资源时的异常
 *
 * @author zxiaozhou
 * @date 2020-06-28 18:55
 * @since JDK11
 */
@Component
@Slf4j
public class CustomAccessDeniedHandler extends BaseController implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException {
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding(UTF_8);
        log.error("------------CustomAccessDeniedHandler------无权限访问------>handle:{}", accessDeniedException.getMessage());
        System.out.println("----CustomAccessDeniedHandler-------" + response.getStatus());
        Result<Object> fail = fail(Status.ACCESS_DENIED, "访问资源权限不足,请联系管理员");
        response.getWriter().print(JSONObject.toJSONString(fail));
    }
}
