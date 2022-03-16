// +----------------------------------------------------------------------
// | SkillFull快速开发平台 [ SkillFull ]
// +----------------------------------------------------------------------
// | 版权所有 2020~2021 zxiaozhou
// +----------------------------------------------------------------------
// | 官方网站: https://www.divisu.com
// +----------------------------------------------------------------------
// | 作者: zxiaozhou <z7630853@163.com>
// +----------------------------------------------------------------------
package indi.zxiaozhou.skillfull.auth.security.validate;

/**
 * 验证码类型
 *
 * @author zxiaozhou
 * @date 2020-06-30 16:22
 * @since JDK11
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
