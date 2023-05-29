

package com.anyilanxin.anyicloud.auth.oauth2.validate;

import com.alibaba.fastjson.JSONObject;

import javax.servlet.http.HttpServletRequest;

/**
 * 验证
 *
 * @author zxh
 * @date 2020-06-29 02:23
 * @since 1.0.0
 */
public interface IValidate {
    /**
     * 获取验证码信息 redis key构建:picture:codeKey+system,sms:codeKey+system+phone
     *
     * @param parameter ${@link JSONObject} 获取验证码参数
     * @param request   ${@link HttpServletRequest} HttpServletRequest
     * @return ValidateDto ${@link ValidateDto} 验证码结果
     * @author zxh
     * @date 2020-06-29 02:25
     */
    ValidateDto getVerification(JSONObject parameter, HttpServletRequest request);


    /**
     * 验证码验证
     *
     * @param parameter ${@link CheckModel} 参数
     * @return CheckDto 验证结果
     * @author zxh
     * @date 2020-06-29 02:25
     */
    CheckDto checkVerification(CheckModel parameter);
}
