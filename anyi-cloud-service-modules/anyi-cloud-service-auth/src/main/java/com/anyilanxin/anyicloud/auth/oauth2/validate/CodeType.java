

package com.anyilanxin.anyicloud.auth.oauth2.validate;

/**
 * 验证码类型
 *
 * @author zxh
 * @date 2020-06-30 16:22
 * @since 1.0.0
 */
public interface CodeType {
    /**
     * 短信验证码
     */
    String SMS_CODE = "sms_code";
    /**
     * 图片验证码
     */
    String PICTURE_CODE = "picture_code";

    /**
     * 邮件验证码
     */
    String EMAIL_CODE = "email_code";
}
