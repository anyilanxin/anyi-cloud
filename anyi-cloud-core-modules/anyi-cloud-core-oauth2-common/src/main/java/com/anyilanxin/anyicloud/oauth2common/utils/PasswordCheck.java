

package com.anyilanxin.anyicloud.oauth2common.utils;

import com.anyilanxin.anyicloud.corecommon.utils.CoreCommonUtils;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * 密码校验与生成
 *
 * @author zxh
 * @date 2022-05-02 10:54
 * @since 1.0.0
 */
public class PasswordCheck {
    private final PasswordEncoder passwordEncoder;
    private static volatile PasswordCheck singleton;

    private PasswordCheck(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }


    public static PasswordCheck getSingleton(PasswordEncoder passwordEncoder) {
        if (singleton == null) {
            synchronized (PasswordCheck.class) {
                if (singleton == null) {
                    singleton = new PasswordCheck(passwordEncoder);
                }
            }
        }
        return singleton;
    }


    /**
     * 校验密码
     *
     * @param rawPassword     未编码密码
     * @param salt            密码盐
     * @param encodedPassword 编码后密码
     * @author zxh
     * @date 2022-05-02 11:06
     */
    public boolean matches(CharSequence rawPassword, String salt, String encodedPassword) {
        return passwordEncoder.matches(rawPassword + salt, encodedPassword);
    }


    /**
     * 校验密码(没有盐)
     *
     * @param rawPassword     未编码密码
     * @param encodedPassword 编码后密码
     * @author zxh
     * @date 2022-05-02 11:06
     */
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }


    /**
     * 获取密码
     *
     * @param rawPassword 为编码密码
     * @return PasswordInfo 编码后密码信息
     * @author zxh
     * @date 2022-05-02 11:14
     */
    public PasswordInfo getPasswordInfo(CharSequence rawPassword) {
        String salt = CoreCommonUtils.get32UUId();
        String encodePassword = passwordEncoder.encode(rawPassword + salt);
        return PasswordInfo.builder().salt(salt).encodedPassword(encodePassword).build();
    }


    /**
     * 获取密码（没有盐）
     *
     * @param rawPassword 为编码密码
     * @return PasswordInfo 编码后密码信息
     * @author zxh
     * @date 2022-05-02 11:14
     */
    public String getEncodePassword(CharSequence rawPassword) {
        return passwordEncoder.encode(rawPassword);
    }

    @Getter
    @Setter
    @ToString
    @EqualsAndHashCode
    @SuperBuilder(toBuilder = true)
    @NoArgsConstructor
    public static class PasswordInfo {
        private String encodedPassword;

        private String salt;
    }
}
