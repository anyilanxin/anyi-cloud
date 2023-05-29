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

package com.anyilanxin.anyicloud.corecommon.utils.encryption;

import cn.hutool.core.util.CharsetUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.asymmetric.KeyType;
import cn.hutool.crypto.asymmetric.RSA;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

/**
 * RSA处理工具类
 *
 * @author zhouxuanhong
 * @date 2019-10-22 09:54
 * @since 1.0.0
 */
@Slf4j
public class RSAUtils {

    @Setter
    @Getter
    @ToString
    public static class RsaKey {
        private String base64PublicKey;
        private String base64PrivateKey;
    }

    public static RsaKey getRsaKey() {
        RSA rsa = new RSA();
        RsaKey rsaKey = new RsaKey();
        rsaKey.setBase64PrivateKey(rsa.getPrivateKeyBase64());
        rsaKey.setBase64PublicKey(rsa.getPublicKeyBase64());
        return rsaKey;
    }


    /**
     * 公钥加密
     *
     * @param publicKey {@link String} X509格式公钥
     * @param plaintext {@link String} 明文
     * @return String {@link String} 密文
     * @author zhouxuanhong
     * @date 2019-10-22 09:54
     */
    public static String encryptPublicKey(String publicKey, String plaintext) {
        RSA rsa = new RSA(null, publicKey);
        return rsa.encryptBase64(StrUtil.bytes(plaintext, CharsetUtil.CHARSET_UTF_8), KeyType.PublicKey);
    }


    /**
     * 公钥解密
     *
     * @param publicKey  {@link String} X509格式公钥
     * @param ciphertext {@link String} 密文
     * @return String {@link String} 明文
     * @author zhouxuanhong
     * @date 2019-10-22 09:54
     */
    public static String decryptPublicKey(String publicKey, String ciphertext) {
        RSA rsa = new RSA(null, publicKey);
        return StrUtil.str(rsa.decrypt(ciphertext, KeyType.PublicKey), CharsetUtil.CHARSET_UTF_8);
    }


    /**
     * 私钥加密
     *
     * @param privateKey {@link String} PKCS8格式私钥
     * @param plaintext  {@link String} 明文
     * @return String {@link String} 密文
     * @author zhouxuanhong
     * @date 2019-10-22 09:54
     */
    public static String encryptPrivateKey(String privateKey, String plaintext) {
        RSA rsa = new RSA(privateKey, null);
        return rsa.encryptBase64(StrUtil.bytes(plaintext, CharsetUtil.CHARSET_UTF_8), KeyType.PrivateKey);
    }


    /**
     * 私钥解密
     *
     * @param privateKey {@link String} PKCS8格式私钥
     * @param ciphertext {@link String} 密文
     * @return String {@link String}
     * @author zhouxuanhong
     * @date 2019-10-22 09:54
     */
    public static String decryptPrivateKey(String privateKey, String ciphertext) {
        RSA rsa = new RSA(privateKey, null);
        return StrUtil.str(rsa.decrypt(ciphertext, KeyType.PrivateKey), CharsetUtil.CHARSET_UTF_8);
    }
}
