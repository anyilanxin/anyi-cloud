/**
 * Copyright (c) 2021-2022 ZHOUXUANHONG(安一老厨)<anyilanxin@aliyun.com>
 *
 * AnYi Cloud Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * AnYi Cloud 采用APACHE LICENSE 2.0开源协议，您在使用过程中，需要注意以下几点：
 *
 * 1.请不要删除和修改根目录下的LICENSE文件。
 * 2.请不要删除和修改 AnYi Cloud 源码头部的版权声明。
 * 3.请保留源码和相关描述文件的项目出处，作者声明等。
 * 4.分发源码时候，请注明软件出处 https://github.com/anyilanxin/anyi-cloud
 * 5.在修改包名，模块名称，项目代码等时，请注明软件出处 https://github.com/anyilanxin/anyi-cloud
 * 6.若您的项目无法满足以上几点，可申请商业授权
 */
package com.anyilanxin.skillfull.system.utils;


import com.anyilanxin.skillfull.corecommon.utils.CoreCommonUtils;
import lombok.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.validation.constraints.NotNull;

/**
 * 加密工具
 *
 * @author zxiaozhou
 * @date 2020-06-29 15:58
 * @since JDK11
 */
@Component
@RequiredArgsConstructor
public class CryptAuthUtils {
    private static CryptAuthUtils utils;
    private final PasswordEncoder encoder;


    /**
     * 加密明文密码
     *
     * @param password  ${@link String} 明文密码
     * @param secretKey ${@link String} 盐
     * @return String ${@link String}
     * @author zxiaozhou
     * @date 2019-05-19 00:05
     */
    public static String getEncoderPassword(@NotNull String password, String secretKey) {
        if (StringUtils.isNotBlank(password)) {
            password = password.trim();
        } else {
            password = "";
        }
        if (StringUtils.isNotBlank(secretKey)) {
            secretKey = secretKey.trim();
        } else {
            secretKey = "";
        }
        return utils.encoder.encode(password + secretKey);
    }


    /**
     * 密码匹配
     *
     * @param rawPassword     ${@link CharSequence} 原密码
     * @param encodedPassword ${@link String} 加密后的密码
     * @author zxiaozhou
     * @date 2019-05-19 00:41
     */
    public static boolean matches(CharSequence rawPassword, String encodedPassword) {
        return utils.encoder.matches(rawPassword, encodedPassword);
    }


    /**
     * 密码匹配
     *
     * @param password        ${@link String} 明文密码
     * @param salt            ${@link String} 密码盐
     * @param encodedPassword ${@link String} 加密后的密码
     * @author zxiaozhou
     * @date 2019-05-19 00:41
     */
    public static boolean matches(String password, String salt, String encodedPassword) {
        CharSequence rawPassword = password + salt;
        return matches(rawPassword, encodedPassword);
    }


    /**
     * 通过明文密码获取密码信息
     *
     * @param password ${@link String} 明文密码
     * @return PasswordInfo ${@link PasswordInfo} 密码信息
     * @author zxiaozhou
     * @date 2020-11-12 17:35
     */
    public static PasswordInfo getPasswordInfo(String password) {
        String secretKey = CoreCommonUtils.get32UUId();
        String secretPassword = getEncoderPassword(password, secretKey);
        PasswordInfo passwordInfo = new PasswordInfo();
        passwordInfo.setSalt(secretKey);
        passwordInfo.setEncodedPassword(secretPassword);
        return passwordInfo;
    }


    @PostConstruct
    private void init() {
        utils = this;
    }


    @Setter
    @Getter
    @ToString
    @EqualsAndHashCode
    public static class PasswordInfo {
        /**
         * 密码盐
         */
        private String salt;

        /**
         * 密文密码
         */
        private String encodedPassword;
    }


}
