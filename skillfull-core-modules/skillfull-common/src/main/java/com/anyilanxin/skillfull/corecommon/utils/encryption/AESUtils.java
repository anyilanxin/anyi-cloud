package com.anyilanxin.skillfull.corecommon.utils.encryption;

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
 * @since JDK11
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
