/**
* Copyright (c) 2021-2022 ZHOUXUANHONG(安一老厨)<anyilanxin@aliyun.com>
*
* <p>AnYi Cloud Licensed under the Apache License, Version 2.0 (the "License"); you may not use
* this file except in compliance with the License. You may obtain a copy of the License at
*
* <p>http://www.apache.org/licenses/LICENSE-2.0
*
* <p>Unless required by applicable law or agreed to in writing, software distributed under the
* License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
* express or implied. See the License for the specific language governing permissions and
* limitations under the License.
*
* <p>AnYi Cloud 采用APACHE LICENSE 2.0开源协议，您在使用过程中，需要注意以下几点：
*
* <p>1.请不要删除和修改根目录下的LICENSE文件。 2.请不要删除和修改 AnYi Cloud 源码头部的版权声明。 3.请保留源码和相关描述文件的项目出处，作者声明等。
* 4.分发源码时候，请注明软件出处 https://github.com/anyilanxin/anyi-cloud 5.在修改包名，模块名称，项目代码等时，请注明软件出处
* https://github.com/anyilanxin/anyi-cloud 6.若您的项目无法满足以上几点，可申请商业授权
*/
package com.anyilanxin.skillfull.oauth2common.utils;

import com.anyilanxin.skillfull.corecommon.utils.CoreCommonUtils;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
* 密码校验与生成
*
* @author zxiaozhou
* @date 2022-05-02 10:54
* @since JDK1.8
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
    * @param rawPassword 未编码密码
    * @param salt 密码盐
    * @param encodedPassword 编码后密码
    * @author zxiaozhou
    * @date 2022-05-02 11:06
    */
    public boolean matches(CharSequence rawPassword, String salt, String encodedPassword) {
        return passwordEncoder.matches(rawPassword + salt, encodedPassword);
    }

    /**
    * 校验密码(没有盐)
    *
    * @param rawPassword 未编码密码
    * @param encodedPassword 编码后密码
    * @author zxiaozhou
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
    * @author zxiaozhou
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
    * @author zxiaozhou
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
