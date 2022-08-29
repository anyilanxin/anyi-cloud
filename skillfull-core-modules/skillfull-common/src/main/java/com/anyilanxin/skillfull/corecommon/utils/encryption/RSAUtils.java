// +----------------------------------------------------------------------
// | SkillFull快速开发平台 [ SkillFull ]
// +----------------------------------------------------------------------
// | 版权所有 2020~2021 zxiaozhou
// +----------------------------------------------------------------------
// | 官方网站: https://skillfull.divisu.com
// +----------------------------------------------------------------------
// | 作者: zxiaozhou <z7630853@163.com>
// +----------------------------------------------------------------------
package com.anyilanxin.skillfull.corecommon.utils.encryption;

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
 * @since JDK11
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
