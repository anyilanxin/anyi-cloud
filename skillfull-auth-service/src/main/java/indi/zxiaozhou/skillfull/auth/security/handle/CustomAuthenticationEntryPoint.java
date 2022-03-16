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
import indi.zxiaozhou.skillfull.corecommon.exception.ResponseException;
import indi.zxiaozhou.skillfull.coremvc.utils.ServletUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static cn.hutool.core.util.CharsetUtil.UTF_8;

/**
 * 匿名用户访问无权限资源时异常
 *
 * @author zxiaozhou
 * @date 2020-06-28 18:55
 * @since JDK11
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class CustomAuthenticationEntryPoint extends BaseController implements AuthenticationEntryPoint {


    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) throws IOException {
        String token = ServletUtils.getSubToken(request);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding(UTF_8);
        Result<Object> fail;
        log.error("------------CustomAuthenticationEntryPoint------用户未登录------>commence:{}", e.getMessage());
        // 对token进行校验
        if (StringUtils.isNotBlank(token)) {
            try {
//                if (!ContextHolderUtils.checkUserTokenAndTime()) {
//                    fail = fail(Status.TOKEN_EXPIRED);
//                } else {
//                    fail = fail(Status.TOKEN_EXPIRED, "token无效");
//                }
                fail = fail(Status.TOKEN_EXPIRED, "token无效");
            } catch (ResponseException re) {
                fail = re.getResult();
            }
        } else {
            fail = fail(Status.TOKEN_EXPIRED, "未登录,请先登录");
        }
        response.setStatus(Status.TOKEN_EXPIRED.getStatus().value());
        response.getWriter().print(JSONObject.toJSONString(fail));
    }
}
