// +----------------------------------------------------------------------
// | SkillFull快速开发平台 [ SkillFull ]
// +----------------------------------------------------------------------
// | 版权所有 2020~2021 zxiaozhou
// +----------------------------------------------------------------------
// | 官方网站: https://www.divisu.com
// +----------------------------------------------------------------------
// | 作者: zxiaozhou <z7630853@163.com>
// +----------------------------------------------------------------------
package indi.zxiaozhou.skillfull.auth.security.authorization.web.sms;

import cn.hutool.http.Method;
import com.alibaba.fastjson.JSONObject;
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
import java.io.IOException;
import java.util.Objects;

import static indi.zxiaozhou.skillfull.auth.core.constant.CommonAuthConstant.WEB_SMS_LOGIN;

/**
 * 短信验证码
 *
 * @author zxiaozhou
 * @date 2020-06-29 02:44
 * @since JDK11
 */
@Slf4j
public class SmsAuthenticationProcessFilter extends AbstractAuthenticationProcessingFilter {
    private String phoneParameter = "phone";
    private String smsCodeParameter = "code";
    private boolean postOnly = true;
    private final static String LOGIN_URI = WEB_SMS_LOGIN;

    public SmsAuthenticationProcessFilter() {
        super(new AntPathRequestMatcher(LOGIN_URI, "POST"));
    }

    @Override
    @AutoLog(note = "短信登录", type = AutoLog.LOGIN)
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException {
        request = ServletUtils.cloneRequest(request);
        String phone = this.obtainPhone(request);
        String code = this.obtainSmsCode(request);
        SmsAuthenticationToken authRequest = new SmsAuthenticationToken(phone, code);
        this.setDetails(request, authRequest);
        return this.getAuthenticationManager().authenticate(authRequest);
    }


    @Nullable
    protected String obtainSmsCode(HttpServletRequest request) {
        String smsCode = request.getParameter(this.smsCodeParameter);
        if (StringUtils.isBlank(smsCode) && !Method.GET.name().equals(request.getMethod())) {
            if (request instanceof BodyReaderRequestWrapper) {
                BodyReaderRequestWrapper bodyReaderRequestWrapper = (BodyReaderRequestWrapper) request;
                JSONObject bodyParams = bodyReaderRequestWrapper.getJsonObjectBody();
                if (Objects.nonNull(bodyParams) && !bodyParams.isEmpty() && bodyParams.containsKey(this.smsCodeParameter)) {
                    smsCode = bodyParams.getString(this.smsCodeParameter);
                }
            }

        }
        return StringUtils.isNotBlank(smsCode) ? smsCode.trim() : "";
    }

    @Nullable
    protected String obtainPhone(HttpServletRequest request) {
        String phone = request.getParameter(this.phoneParameter);
        if (StringUtils.isBlank(phone) && !Method.GET.name().equals(request.getMethod())) {
            if (request instanceof BodyReaderRequestWrapper) {
                BodyReaderRequestWrapper bodyReaderRequestWrapper = (BodyReaderRequestWrapper) request;
                JSONObject bodyParams = bodyReaderRequestWrapper.getJsonObjectBody();
                if (Objects.nonNull(bodyParams) && !bodyParams.isEmpty() && bodyParams.containsKey(this.phoneParameter)) {
                    phone = bodyParams.getString(this.phoneParameter);
                }
            }

        }
        return StringUtils.isNotBlank(phone) ? phone.trim() : "";
    }


    protected void setDetails(HttpServletRequest request, SmsAuthenticationToken authRequest) {
        authRequest.setDetails(this.authenticationDetailsSource.buildDetails(request));
    }

    public void setPhoneParameter(String phoneParameter) {
        Assert.hasText(phoneParameter, "phoneParameter参数不能为空");
        this.phoneParameter = phoneParameter;
    }

    public void setSmsCodeParameter(String smsCodeParameter) {
        Assert.hasText(smsCodeParameter, "smsCodeParameter参数不能为空");
        this.smsCodeParameter = smsCodeParameter;
    }

    public void setPostOnly(boolean postOnly) {
        this.postOnly = postOnly;
    }

    public final String getPhoneParameter() {
        return this.phoneParameter;
    }

    public final String getSmsCodeParameter() {
        return this.smsCodeParameter;
    }


}
