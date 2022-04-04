// +----------------------------------------------------------------------
// | SkillFull快速开发平台 [ SkillFull ]
// +----------------------------------------------------------------------
// | 版权所有 2020~2021 zxiaozhou
// +----------------------------------------------------------------------
// | 官方网站: https://www.divisu.com
// +----------------------------------------------------------------------
// | 作者: zxiaozhou <z7630853@163.com>
// +----------------------------------------------------------------------
package indi.zxiaozhou.skillfull.auth.core.constant;

/**
 * 通用常量
 *
 * @author zxiaozhou
 * @date 2020-07-09 16:52
 * @since JDK11
 */
public interface CommonAuthConstant {
    String CONST_PREFIX = "auth_";

    /**
     * 图片验证码key前缀
     */
    String PICTURE_CODE_KEY_PREFIX = "PICTURE_CODE_";

    /**
     * 短信验证码key前缀
     */
    String SMS_CODE_KEY_PREFIX = "SMS_CODE_";

    /**
     * 统一退出登录
     */
    String LOGIN_OUT = "/logout";

    /**
     * web端账号登录
     */
    String WEB_PASSWORD_LOGIN = "/web/login/picture";

    /**
     * web端短信验证码登录地址
     */
    String WEB_SMS_LOGIN = "/web/login/sms";

    /**
     * 微信授权登录
     */
    String WEI_XIN_LOGIN = "/wx/login/auth";
}
