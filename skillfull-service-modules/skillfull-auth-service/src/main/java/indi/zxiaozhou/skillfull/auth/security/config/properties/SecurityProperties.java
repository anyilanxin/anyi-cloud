// +----------------------------------------------------------------------
// | SkillFull快速开发平台 [ SkillFull ]
// +----------------------------------------------------------------------
// | 版权所有 2020~2021 zxiaozhou
// +----------------------------------------------------------------------
// | 官方网站: https://www.divisu.com
// +----------------------------------------------------------------------
// | 作者: zxiaozhou <z7630853@163.com>
// +----------------------------------------------------------------------
package indi.zxiaozhou.skillfull.auth.security.config.properties;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Set;

/**
 * Jwt参数配置
 *
 * @author zxiaozhou
 * @date 2020-06-29 00:25
 * @since JDK11
 */
@Getter
@Setter
@ToString
@Component
@ConfigurationProperties(prefix = "security")
public class SecurityProperties implements Serializable {
    private static final long serialVersionUID = 713575253040294540L;
    /**
     * 非正式环境
     */
    Set<String> noProWhiteList;
    /**
     * 正式环境白名单
     */
    Set<String> proWhiteList;
    /**
     * 令牌放入请求头key
     */
    private String tokenHeaderKey = "Authorization";
    /**
     * 请求头令牌前缀
     */
    private String tokenHeaderStartWith = "Bearer ";
    /**
     * 刷新令牌key
     */
    private String refreshTokenKey = "refresh-token";
    /**
     * 令牌类型
     */
    private String tokenType = "Bearer";
    /**
     * 令牌放入地址时key
     */
    private String tokenQueryKey = "access-token";
    /**
     * 令牌过期时间(单位:秒)
     */
    private long tokenValidityInSeconds = 3600L;
    /**
     * 触发令牌续期检测最小时间(单位:秒)
     */
    private long tokenDetectInSeconds = 1800L;
    /**
     * 令牌续期时间(单位:秒)
     */
    private long tokenRenewInSeconds = 3600L;
    /**
     * 数据解密密钥key
     */
    private String securityKey = "security";
    /**
     * 数据密文key
     */
    private String securityCipherKey = "data";
    /**
     * 请求头刷新密钥标识
     */
    private String refreshSecurityKey = "refresh-security";
    /**
     * 验证码有效时间(单位:秒)
     */
    private long codeValidityInSeconds = 300L;
    /**
     * 打开鉴权
     */
    private boolean openAuth = true;
    /**
     * 是否单设备登录:true-单设备，false-多设备登录
     */
    private boolean oneEquipmentLogin = false;
    /**
     * 打开登录错误限制
     */
    private boolean openAuthLoginErrorLimit = false;
    /**
     * 最大允许登录错误次数(当打开登录错误限制时使用),默认3次
     */
    private int maxLoginErrorNum = 3;
    /**
     * 环境
     */
    @Value("${spring.profiles.active}")
    private String profilesActive;
}
