/*
 * Copyright (c) 2021-2022 ZHOUXUANHONG(安一老厨)<anyilanxin@aliyun.com>
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
 *   7.本软件使用的第三方依赖皆为开源软件，如需要修改第三方源码请遵循第三方源码附带开源协议；
 *   8.本软件流程部分请遵循camunda开源协议：
 *     https://docs.camunda.org/manual/latest/introduction/third-party-libraries
 *     https://github.com/camunda/camunda-bpm-platform/blob/master/LICENSE
 *   9.若您的项目无法满足以上几点，可申请商业授权。
 */

package com.anyilanxin.anyicloud.corecommon.utils.encryption;

import cn.hutool.core.codec.Base64;
import cn.hutool.core.util.CharsetUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.symmetric.SymmetricAlgorithm;
import cn.hutool.crypto.symmetric.SymmetricCrypto;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

/**
 * AES Utils
 *
 * @author zhouxuanhong
 * @date 2019-10-23 14:07
 * @since 1.0.0
 */
@Slf4j
public class AESUtils {

    /**
     * 获取256位AES加密密钥
     *
     * @return String {@link String} 1024位加密密钥
     * @author zhouxuanhong
     * @date 2019-10-23 14:07
     */
    public static String getKey() {
        return Base64.encode(SecureUtil.generateKey(SymmetricAlgorithm.AES.getValue()).getEncoded());
    }


    /**
     * 明文加密
     *
     * @param key       {@link String} 密钥
     * @param plaintext {@link String} 明文
     * @return String {@link String} 密文
     * @author zhouxuanhong
     * @date 2019-10-23 14:07
     */
    public static String encrypt(String key, String plaintext) {
        if (StringUtils.isNotBlank(plaintext) && StringUtils.isNotBlank(key)) {
            SymmetricCrypto aes = new SymmetricCrypto(SymmetricAlgorithm.AES, key.getBytes());
            return aes.encryptBase64(plaintext);
        }
        return "";
    }


    /**
     * 密文解密
     *
     * @param key        {@link String} 密文
     * @param ciphertext {@link String} 密文
     * @return String {@link String} 明文
     * @author zhouxuanhong
     * @date 2019-10-23 14:09
     */
    public static String decrypt(String key, String ciphertext) {
        if (StringUtils.isNotBlank(ciphertext) && StringUtils.isNotBlank(key)) {
            SymmetricCrypto aes = new SymmetricCrypto(SymmetricAlgorithm.AES, key.getBytes());
            return aes.decryptStr(ciphertext, CharsetUtil.CHARSET_UTF_8);
        }
        return "";
    }


    public static void main(String[] args) {
        // wpOHS3T1/KVz/HnUXCrlPx8fYYY+kyrl6TsT8nH/qTU=
        String key = getKey();
        String encryptInfo = encrypt(key, "oiuoiuoiuouoit热痛");
        System.out.println(encryptInfo);
        String decrypt = decrypt(key, encryptInfo);
        System.out.println(decrypt);
    }
}
