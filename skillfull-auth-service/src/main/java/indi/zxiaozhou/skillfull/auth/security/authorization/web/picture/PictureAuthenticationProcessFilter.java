// +----------------------------------------------------------------------
// | SkillFull快速开发平台 [ SkillFull ]
// +----------------------------------------------------------------------
// | 版权所有 2020~2021 zxiaozhou
// +----------------------------------------------------------------------
// | 官方网站: https://www.divisu.com
// +----------------------------------------------------------------------
// | 作者: zxiaozhou <z7630853@163.com>
// +----------------------------------------------------------------------
package indi.zxiaozhou.skillfull.auth.security.authorization.web.picture;

import cn.hutool.http.Method;
import com.alibaba.fastjson.JSONObject;
import indi.zxiaozhou.skillfull.auth.security.validate.CheckModel;
import indi.zxiaozhou.skillfull.corecommon.annotation.AutoLog;
import indi.zxiaozhou.skillfull.coremvc.utils.BodyReaderRequestWrapper;
import indi.zxiaozhou.skillfull.coremvc.utils.ServletUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.lang.Nullable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.util.Assert;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Objects;

import static indi.zxiaozhou.skillfull.auth.core.constant.CommonAuthConstant.WEB_PASSWORD_LOGIN;

/**
 * 图片验证码
 *
 * @author zxiaozhou
 * @date 2020-06-29 02:44
 * @since JDK11
 */
@Slf4j
public class PictureAuthenticationProcessFilter extends AbstractAuthenticationProcessingFilter {
    private String usernameParameter = "userName";
    private String passwordParameter = "password";
    private String codeIdParameter = "codeId";
    private String codeParameter = "code";
    private boolean postOnly = true;
    private final static String LOGIN_URI = WEB_PASSWORD_LOGIN;


    public PictureAuthenticationProcessFilter() {
        super(new AntPathRequestMatcher(LOGIN_URI, "POST"));
    }

    @Override
    @AutoLog(note = "用户密码登录", type = AutoLog.LOGIN)
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        request = ServletUtils.cloneRequest(request);
        String userName = this.obtainUserName(request);
        String password = this.obtainPassword(request);
        CheckModel model = new CheckModel(this.obtainCodeId(request), this.obtainCode(request));
        PictureAuthenticationToken token = new PictureAuthenticationToken(userName, password, model);
        this.setDetails(request, token);
        return this.getAuthenticationManager().authenticate(token);
    }


    @Nullable
    protected String obtainPassword(HttpServletRequest request) {
        String password = request.getParameter(this.passwordParameter);
        if (StringUtils.isBlank(password) && !Method.GET.name().equals(request.getMethod())) {
            if (request instanceof BodyReaderRequestWrapper) {
                BodyReaderRequestWrapper bodyReaderRequestWrapper = (BodyReaderRequestWrapper) request;
                JSONObject bodyParams = bodyReaderRequestWrapper.getJsonObjectBody();
                if (Objects.nonNull(bodyParams) && !bodyParams.isEmpty() && bodyParams.containsKey(this.passwordParameter)) {
                    password = bodyParams.getString(this.passwordParameter);
                }
            }
        }
        return StringUtils.isNotBlank(password) ? password.trim() : "";
    }

    @Nullable
    protected String obtainUserName(HttpServletRequest request) {

        String userName = request.getParameter(this.usernameParameter);
        if (StringUtils.isBlank(userName) && !Method.GET.name().equals(request.getMethod())) {
            if (request instanceof BodyReaderRequestWrapper) {
                BodyReaderRequestWrapper bodyReaderRequestWrapper = (BodyReaderRequestWrapper) request;
                JSONObject bodyParams = bodyReaderRequestWrapper.getJsonObjectBody();
                if (Objects.nonNull(bodyParams) && !bodyParams.isEmpty() && bodyParams.containsKey(this.usernameParameter)) {
                    userName = bodyParams.getString(this.usernameParameter);
                }
            }

        }
        return StringUtils.isNotBlank(userName) ? userName.trim() : "";
    }


    @Nullable
    protected String obtainCodeId(HttpServletRequest request) {
        String codeId = request.getParameter(this.codeIdParameter);
        if (StringUtils.isBlank(codeId) && !Method.GET.name().equals(request.getMethod())) {
            if (request instanceof BodyReaderRequestWrapper) {
                BodyReaderRequestWrapper bodyReaderRequestWrapper = (BodyReaderRequestWrapper) request;
                JSONObject bodyParams = bodyReaderRequestWrapper.getJsonObjectBody();
                if (Objects.nonNull(bodyParams) && !bodyParams.isEmpty() && bodyParams.containsKey(this.codeIdParameter)) {
                    codeId = bodyParams.getString(this.codeIdParameter);
                }
            }
        }
        return StringUtils.isNotBlank(codeId) ? codeId.trim() : "";
    }

    @Nullable
    protected String obtainCode(HttpServletRequest request) {
        String code = request.getParameter(this.codeParameter);
        if (StringUtils.isBlank(code) && !Method.GET.name().equals(request.getMethod())) {
            if (request instanceof BodyReaderRequestWrapper) {
                BodyReaderRequestWrapper bodyReaderRequestWrapper = (BodyReaderRequestWrapper) request;
                JSONObject bodyParams = bodyReaderRequestWrapper.getJsonObjectBody();
                if (Objects.nonNull(bodyParams) && !bodyParams.isEmpty() && bodyParams.containsKey(this.codeParameter)) {
                    code = bodyParams.getString(this.codeParameter);
                }
            }
        }
        return StringUtils.isNotBlank(code) ? code.trim() : "";
    }

    protected void setDetails(HttpServletRequest request, PictureAuthenticationToken authRequest) {
        authRequest.setDetails(this.authenticationDetailsSource.buildDetails(request));
    }

    public void setUsernameParameter(String usernameParameter) {
        Assert.hasText(usernameParameter, "usernameParameter不能为空");
        this.usernameParameter = usernameParameter;
    }

    public void setPasswordParameter(String passwordParameter) {
        Assert.hasText(passwordParameter, "passwordParameter不能为空");
        this.passwordParameter = passwordParameter;
    }


    public void setCodeIdParameter(String codeIdParameter) {
        Assert.hasText(codeIdParameter, "codeIdParameter不能为空");
        this.codeIdParameter = codeIdParameter;
    }

    public void setCodeParameter(String codeParameter) {
        Assert.hasText(codeParameter, "passwordParameter不能为空");
        this.codeParameter = codeParameter;
    }

    public final String getUsernameParameter() {
        return this.usernameParameter;
    }

    public final String getPasswordParameter() {
        return this.passwordParameter;
    }

    public final String getCodeIdParameter() {
        return this.codeIdParameter;
    }

    public final String getCodeParameter() {
        return this.codeParameter;
    }

    public void setPostOnly(boolean postOnly) {
        this.postOnly = postOnly;
    }

}
