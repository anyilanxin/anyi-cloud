/*
 * Copyright (c) 2021-present ZHOUXUANHONG(安一老厨)<anyilanxin@aliyun.com>
 *
 * AnYi Cloud Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * AnYi Cloud 采用APACHE LICENSE 2.0开源协议，您在使用过程中，需要注意以下几点：
 *   1.请不要删除和修改根目录下的LICENSE文件；
 *   2.请不要删除和修改 AnYi Cloud 源码头部的版权声明；
 *   3.请保留源码和相关描述文件的项目出处，作者声明等；
 *   4.分发源码时候，请注明软件出处 https://github.com/anyilanxin/anyi-cloud；
 *   5.在修改包名，模块名称，项目代码等时，请注明软件出处 https://github.com/anyilanxin/anyi-cloud；
 *   6.本软件不允许在国家法律规定范围外使用，如出现违法行为原作者本人不承担任何法律风险；
 *   7.进行商用时，不得基于AnYi Cloud的基础，修改包装而成一个与AnYi Zeebe EE、AnYi Cloud EE功能类似的程序，进行销售或发布，参与同类软件产品市场的竞争；
 *   8.本软件使用的第三方依赖皆为开源软件，如需要修改第三方源码请遵循第三方源码附带开源协议；
 *   9.本软件流程部分请遵循camunda开源协议：
 *     https://docs.camunda.org/manual/latest/introduction/third-party-libraries
 *     https://github.com/camunda/camunda-bpm-platform/blob/master/LICENSE
 *   10.若您的项目无法满足以上几点，可申请商业授权。
 */
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
