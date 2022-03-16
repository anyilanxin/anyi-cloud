// +----------------------------------------------------------------------
// | SkillFull快速开发平台 [ SkillFull ]
// +----------------------------------------------------------------------
// | 版权所有 2020~2021 zxiaozhou
// +----------------------------------------------------------------------
// | 官方网站: https://www.divisu.com
// +----------------------------------------------------------------------
// | 作者: zxiaozhou <z7630853@163.com>
// +----------------------------------------------------------------------
package indi.zxiaozhou.skillfull.corecommon.utils.encryption;

import indi.zxiaozhou.skillfull.corecommon.constant.Status;
import indi.zxiaozhou.skillfull.corecommon.exception.ResponseException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.UUID;


/**
 * AES Utils
 *
 * @author zhouxuanhong
 * @date 2019-10-23 14:07
 * @since JDK11
 */
@Slf4j
public class AESUtils {
    private static final Integer KEY_LENGTH = 128;

    /**
     * 获取256位AES加密密钥
     *
     * @return String {@link String} 256位加密密钥
     * @author zhouxuanhong
     * @date 2019-10-23 14:07
     */
    public static String getKey() {
        return getUUID();
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
        return Base64.encodeBase64String(encryptToBytes(key, plaintext));
    }


    /**
     * 明文加密为byte数组
     *
     * @param key       {@link String} 密钥
     * @param plaintext {@link String} 明文
     * @return byte[]-密文数组
     * @author zhouxuanhong
     * @date 2019-10-23 14:07
     */
    private static byte[] encryptToBytes(String key, String plaintext) {
        byte[] ciphertextByte;
        try {
            KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
            keyGenerator.init(KEY_LENGTH);
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(key.getBytes(), "AES"));
            ciphertextByte = cipher.doFinal(plaintext.getBytes(StandardCharsets.UTF_8));
        } catch (Exception e) {
            log.error("----------AESUtils---------->明文加密为byte数组异常:{}", e.getMessage());
            throw new ResponseException(Status.VERIFICATION_FAILED, e.getMessage());
        }
        return ciphertextByte;
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
        return decryptByBytes(key, Base64.decodeBase64(ciphertext));
    }


    /**
     * 密文数组解密为明文字符串
     *
     * @param key                 {@link String} 密钥
     * @param ciphertextByte-密文数组
     * @return String {@link String} 明文
     * @author zhouxuanhong
     * @date 2019-10-23 14:07
     */
    private static String decryptByBytes(String key, byte[] ciphertextByte) {
        String plaintext;
        try {
            KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
            keyGenerator.init(KEY_LENGTH);
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(key.getBytes(), "AES"));
            byte[] decryptBytes = cipher.doFinal(ciphertextByte);
            plaintext = new String(decryptBytes);
        } catch (Exception e) {
            log.error("----------AESUtils---------->密文byte数组解密异常:{}", e.getMessage());
            throw new ResponseException(Status.VERIFICATION_FAILED, e.getMessage());
        }
        return plaintext;
    }


    /**
     * 获取16位随机字符串
     *
     * @return String
     */
    public static String getUUID() {
        String uuid = UUID.randomUUID().toString();
        char[] cs = new char[32];
        char c = 0;
        for (int i = uuid.length() / 2, j = 1; i-- > 0; ) {
            if ((c = uuid.charAt(i)) != '-') {
                cs[j++] = c;
            }
        }
        String uid = String.valueOf(cs);
        return uid.trim();
    }


    public static void main(String[] args) {
//        System.out.println(getKey());
//        JSONObject jsonObject = new JSONObject();
//        jsonObject.put("dictName", "加密测试");
//        jsonObject.put("dictCode", "加密测试" + System.currentTimeMillis());
//        jsonObject.put("dictType", 0);
//        jsonObject.put("dictStatus", 0);
//        jsonObject.put("remark", "备注说明");
//        String s = jsonObject.toJSONString();
//        System.out.println(encrypt("84d4e94cf59c4484", s));


//        JSONObject jsonObject = new JSONObject();
//        jsonObject.put("code", "5ye2");
//        jsonObject.put("codeId", "cc49d51141cf70b1b28b223b6f99cbb4");
//        jsonObject.put("password", "admin");
//        jsonObject.put("userName", "admin");
//        String s = jsonObject.toJSONString();
//        System.out.println(encrypt("84d4e94cf59c4484", s));
//
        String msdkf = "e0BbqjZhRZLPv6ffiujy+qGV2XTBVDh9zX4jacEGMl9/JdEiJ3cRtPugCxxI5oHzSKIV987ZeA5E8+g4cWTrlKup/67jqf61156HaUa75gBDxXy7B59i+c5TUeNOjPD6ZqCH+JIkPsf7sh0KBRjrrk84rvSbT8c6K2W+cCzgGvXGpUy3fLzy+6yYMwZMQjLkzb4LCbdbR6Qwc5l+mE+GyIJp0PQ6UdfuudkEhRSKQ/b/o/18mD4iXE8XqkOiG6m/gAtIi7Hxv0GQLwT/s68JVQ==";
        String decrypt = decrypt("84d4e94cf59c4484", msdkf);
        System.out.println(decrypt);
    }
}