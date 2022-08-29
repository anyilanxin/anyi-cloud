// +----------------------------------------------------------------------
// | SkillFull快速开发平台 [ SkillFull ]
// +----------------------------------------------------------------------
// | 版权所有 2020~2021 zxiaozhou
// +----------------------------------------------------------------------
// | 官方网站: https://skillfull.divisu.com
// +----------------------------------------------------------------------
// | 作者: zxiaozhou <z7630853@163.com>
// +----------------------------------------------------------------------
package com.anyilanxin.skillfull.auth.core.properties;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 授权相关配置
 *
 * @author zxiaozhou
 * @date 2022-03-30 00:58
 * @since JDK1.8
 */
@Getter
@Setter
@ToString
@Component
@ConfigurationProperties(prefix = "auth")
public class AuthProperty {
    /**
     * 图片验证码有效时间(单位s)
     */
    private long codePictureSeconds;
}
