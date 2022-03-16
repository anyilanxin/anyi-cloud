// +----------------------------------------------------------------------
// | SkillFull快速开发平台 [ SkillFull ]
// +----------------------------------------------------------------------
// | 版权所有 2020~2021 zxiaozhou
// +----------------------------------------------------------------------
// | 官方网站: https://www.divisu.com
// +----------------------------------------------------------------------
// | 作者: zxiaozhou <z7630853@163.com>
// +----------------------------------------------------------------------
package indi.zxiaozhou.skillfull.auth.security.authorization.wx;

import cn.hutool.http.Method;
import indi.zxiaozhou.skillfull.corecommon.annotation.AutoLog;
import indi.zxiaozhou.skillfull.coremvc.utils.BodyReaderRequestWrapper;
import indi.zxiaozhou.skillfull.coremvc.utils.ServletUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.Nullable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static indi.zxiaozhou.skillfull.auth.core.constant.CommonAuthConstant.WEI_XIN_LOGIN;

/**
 * 图片验证码
 *
 * @author zxiaozhou
 * @date 2020-06-29 02:44
 * @since JDK11
 */
@Slf4j
public class WeiXinAuthenticationProcessFilter extends AbstractAuthenticationProcessingFilter {
    private boolean postOnly = true;
    private final static String LOGIN_URI = WEI_XIN_LOGIN;

    public WeiXinAuthenticationProcessFilter() {
        super(new AntPathRequestMatcher(LOGIN_URI, "POST"));
    }


    @Override
    @AutoLog(note = "微信登录", type = AutoLog.LOGIN)
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        request = ServletUtils.cloneRequest(request);
        WeiXinAuthenticationToken token = new WeiXinAuthenticationToken(this.getWxInfo(request));
        this.setDetails(request, token);
        return this.getAuthenticationManager().authenticate(token);
    }


    @Nullable
    protected String getWxInfo(HttpServletRequest request) {
        String stringBody = "";
        if (!Method.GET.name().equals(request.getMethod())) {
            if (request instanceof BodyReaderRequestWrapper) {
                BodyReaderRequestWrapper bodyReaderRequestWrapper = (BodyReaderRequestWrapper) request;
                try {
                    stringBody = bodyReaderRequestWrapper.getStringBody();
                } catch (Exception e) {
                    stringBody = "";
                }
            }
        }
        return stringBody;
    }


    protected void setDetails(HttpServletRequest request, WeiXinAuthenticationToken authRequest) {
        authRequest.setDetails(this.authenticationDetailsSource.buildDetails(request));
    }

    public void setPostOnly(boolean postOnly) {
        this.postOnly = postOnly;
    }
}
