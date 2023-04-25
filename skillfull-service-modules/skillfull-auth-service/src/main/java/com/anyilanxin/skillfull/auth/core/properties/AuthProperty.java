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


    /**
     * 生成token方式,0-相同用户每次登录生成一样，1-相同用户每次登录生成都不一样，默认0
     */
    private int tokenGeneratorType;
}
