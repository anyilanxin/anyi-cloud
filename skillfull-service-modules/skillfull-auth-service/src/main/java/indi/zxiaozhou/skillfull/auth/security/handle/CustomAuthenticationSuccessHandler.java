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
import com.alibaba.fastjson.serializer.SerializerFeature;
import indi.zxiaozhou.skillfull.auth.security.login.service.model.TokenInfo;
import indi.zxiaozhou.skillfull.auth.security.token.LoginSuccessAuthenticationToken;
import indi.zxiaozhou.skillfull.auth.utils.LoginUtil;
import indi.zxiaozhou.skillfull.corecommon.base.Result;
import indi.zxiaozhou.skillfull.corecommon.base.model.login.LoginTokenSecurityModel;
import indi.zxiaozhou.skillfull.coremvc.base.controller.BaseController;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static cn.hutool.core.util.CharsetUtil.UTF_8;

/**
 * 认证成功处理器
 *
 * @author zxiaozhou
 * @date 2019-05-14 16:24
 * @since JDK11
 */
@Component
@Slf4j
@RequiredArgsConstructor
public class CustomAuthenticationSuccessHandler extends BaseController implements AuthenticationSuccessHandler {


    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding(UTF_8);
        if (authentication instanceof LoginSuccessAuthenticationToken) {
            LoginSuccessAuthenticationToken loginSuccessAuthenticationToken = (LoginSuccessAuthenticationToken) authentication;
            TokenInfo tokenInfo = LoginUtil.getTokenInfo();
            LoginTokenSecurityModel securityModel = loginSuccessAuthenticationToken.getPrincipal().getSecurityModel();
            tokenInfo.setToken(securityModel.getToken());
            tokenInfo.setExpiresAt(securityModel.getExpiresAt());
            tokenInfo.setValidityInSeconds(securityModel.getTokenValidityInSeconds());
            Result<TokenInfo> success = ok(tokenInfo);
            response.getWriter().print(JSONObject.toJSONString(success, SerializerFeature.WriteMapNullValue));
        } else {
            String successInfo = "授权成功，但缺少LoginSuccessAuthenticationToken,无法获取token";
            // 授权信息返回前端
            Result<Object> success = fail(successInfo);
            response.getWriter().print(JSONObject.toJSONString(success));
        }
    }
}
