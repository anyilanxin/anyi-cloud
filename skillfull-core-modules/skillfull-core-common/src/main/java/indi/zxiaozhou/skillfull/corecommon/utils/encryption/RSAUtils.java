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

import indi.zxiaozhou.skillfull.corecommon.base.model.system.BaseSecurityModel;
import indi.zxiaozhou.skillfull.corecommon.constant.Status;
import indi.zxiaozhou.skillfull.corecommon.exception.ResponseException;
import org.apache.commons.codec.binary.Base64;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.Cipher;
import java.io.ByteArrayOutputStream;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.*;
import java.util.HashMap;
import java.util.Map;


/**
 * RSA处理工具类
 *
 * @author zhouxuanhong
 * @date 2019-10-22 09:54
 * @since JDK11
 */
public class RSAUtils {
    private static final Logger LOGGER = LoggerFactory.getLogger(RSAUtils.class);

    private static final KeyPair KEY_PAIR;

    static {
        try {
            Security.addProvider(new BouncyCastleProvider());
            SecureRandom random = new SecureRandom();
            KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA", "BC");
            generator.initialize(1024, random);
            KEY_PAIR = generator.generateKeyPair();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    /**
     * 生成rsa公钥和私钥
     *
     * @return Map {@link HashMap}: publicKey-公钥(RSAPublicKey),privateKey-私钥(RSAPrivateKey)
     * @author zhouxuanhong
     * @date 2019-10-22 09:54
     */
    public static Map<String, Object> getRsaKey() {
        Map<String, Object> keyInfo = new HashMap<>(2);
        // 公钥
        RSAPublicKey publicKey = (RSAPublicKey) KEY_PAIR.getPublic();
        keyInfo.put("publicKey", publicKey);
        // 私钥
        RSAPrivateKey privateKey = (RSAPrivateKey) KEY_PAIR.getPrivate();
        keyInfo.put("privateKey", privateKey);
        return keyInfo;
    }


    /**
     * 生成rsa公钥和私钥
     *
     * @return Map {@link HashMap}: publicKey-公钥(X509格式),privateKey-私钥(PKCS8格式)
     * @author zhouxuanhong
     * @date 2019-10-22 09:54
     */
    public static BaseSecurityModel getX509AndPKCS8Key() {
        Map<String, Object> keyInfo = getRsaKey();
        BaseSecurityModel infoModel = new BaseSecurityModel();
        // 公钥
        RSAPublicKey publicKey = (RSAPublicKey) keyInfo.get("publicKey");
        String rsaPublicKey = Base64.encodeBase64String(publicKey.getEncoded());
        infoModel.setPublicKey(rsaPublicKey);
        // 私钥
        RSAPrivateKey privateKey = (RSAPrivateKey) keyInfo.get("privateKey");
        String rsaPrivateKey = Base64.encodeBase64String(privateKey.getEncoded());
        infoModel.setPrivateKey(rsaPrivateKey);
        return infoModel;
    }


    /**
     * PKCS8的私钥字符串还原为RSA私钥
     *
     * @param pkcs8Key {@link String} 待还原私钥字符串
     * @return RSAPrivateKey {@link RSAPrivateKey}
     * @author zhouxuanhong
     * @date 2019-10-22 09:54
     */
    public static RSAPrivateKey getPrivateKey(String pkcs8Key) {
        PrivateKey privateKey = null;
        try {
            byte[] decodeKey = Base64.decodeBase64(pkcs8Key);
            PKCS8EncodedKeySpec pkcs8 = new PKCS8EncodedKeySpec(decodeKey);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA", "BC");
            privateKey = keyFactory.generatePrivate(pkcs8);
        } catch (NoSuchProviderException | NoSuchAlgorithmException | InvalidKeySpecException e) {
            LOGGER.error("----------RSAUtils---------->PKCS8的私钥字符串还原为RSA私钥出错:{}", e.getMessage());
        }
        return (RSAPrivateKey) privateKey;
    }


    /**
     * X509的公钥字符串还原为RSA公钥
     *
     * @param x509Key {@link String} 待还原公钥字符串
     * @return RSAPublicKey {@link RSAPublicKey}
     * @author zhouxuanhong
     * @date 2019-10-22 09:54
     */
    public static RSAPublicKey getPublicKey(String x509Key) {
        PublicKey publicKey = null;
        try {
            byte[] decodeKey = Base64.decodeBase64(x509Key);
            X509EncodedKeySpec x509 = new X509EncodedKeySpec(decodeKey);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA", "BC");
            publicKey = keyFactory.generatePublic(x509);
        } catch (NoSuchProviderException | NoSuchAlgorithmException | InvalidKeySpecException e) {
            LOGGER.error("----------RSAUtils---------->X509的公钥字符串还原为RSA公钥:{}", e.getMessage());
        }
        return (RSAPublicKey) publicKey;
    }


    /**
     * <p>使用模和指数生成RSA公钥
     * 注意：【此代码用了默认补位方式，为RSA/None/PKCS1Padding，不同JDK默认的补位方式可能不同，如Android默认是RSA/None/NoPadding】
     * </p>
     *
     * @param modulus        {@link BigInteger} 模
     * @param publicExponent {@link BigInteger} 公钥指数
     * @return RSAPublicKey {@link RSAPublicKey}
     * @author zhouxuanhong
     * @date 2019-10-22 09:54
     */
    public static RSAPublicKey getPublicKey(BigInteger modulus, BigInteger publicExponent) {
        RSAPublicKey publicKey = null;
        try {
            KeyFactory keyFactory = KeyFactory.getInstance("RSA", "BC");
            RSAPublicKeySpec keySpec = new RSAPublicKeySpec(modulus, publicExponent);
            publicKey = (RSAPublicKey) keyFactory.generatePublic(keySpec);
            LOGGER.info("----------RSAUtils---------->生成公钥:{}", publicKey);
        } catch (NoSuchProviderException | NoSuchAlgorithmException | InvalidKeySpecException e) {
            LOGGER.error("----------RSAUtils---------->生成公钥异常:{}", e.getMessage());
        }
        return publicKey;
    }


    /**
     * <p>
     * 使用模和指数生成RSA私钥
     * 注意：【此代码用了默认补位方式，为RSA/None/PKCS1Padding，不同JDK默认的补位方式可能不同，如Android默认是RSA/None/NoPadding】
     * </p>
     *
     * @param modulus         {@link BigInteger} 模
     * @param privateExponent {@link BigInteger} 私钥指数
     * @return RSAPrivateKey {@link RSAPrivateKey}
     * @author zhouxuanhong
     * @date 2019-10-22 09:54
     */
    public static RSAPrivateKey getPrivateKey(BigInteger modulus, BigInteger privateExponent) {
        RSAPrivateKey privateKey = null;
        try {
            KeyFactory keyFactory = KeyFactory.getInstance("RSA", "BC");
            RSAPrivateKeySpec keySpec = new RSAPrivateKeySpec(modulus, privateExponent);
            privateKey = (RSAPrivateKey) keyFactory.generatePrivate(keySpec);
        } catch (Exception e) {
            LOGGER.error("----------RSAUtils---------->生成私钥异常:{}", e.getMessage());
        }
        return privateKey;
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
        RSAPublicKey rsaPublicKey = getPublicKey(publicKey);
        String result = "";
        try {
            byte[] bytes = encryptByRsaKey(rsaPublicKey, plaintext.getBytes());
            result = Base64.encodeBase64String(bytes);
        } catch (Exception e) {
            LOGGER.error("----------RSAUtils---------->公钥加密异常:{}", e.getMessage());
            throw new ResponseException(Status.VERIFICATION_FAILED, e.getMessage());
        }
        return result;
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
        byte[] bytes = Base64.decodeBase64(ciphertext);
        RSAPublicKey rsaPublicKey = getPublicKey(publicKey);
        String result;
        try {
            result = new String(decryptByRsaKey(rsaPublicKey, bytes), StandardCharsets.UTF_8);
        } catch (Exception e) {
            LOGGER.error("----------RSAUtils---------->私钥解密异常:{}", e.getMessage());
            throw new ResponseException(Status.VERIFICATION_FAILED, e.getMessage());
        }
        return result;
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
        RSAPrivateKey rsaPrivateKey = getPrivateKey(privateKey);
        String result;
        try {
            byte[] bytes = encryptByRsaKey(rsaPrivateKey, plaintext.getBytes());
            result = Base64.encodeBase64String(bytes);
        } catch (Exception e) {
            LOGGER.error("----------RSAUtils---------->公钥加密异常:{}", e.getMessage());
            throw new ResponseException(Status.VERIFICATION_FAILED, e.getMessage());
        }
        return result;
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
        byte[] bytes = Base64.decodeBase64(ciphertext);
        RSAPrivateKey rsaPrivateKey = getPrivateKey(privateKey);
        String result;
        try {
            result = new String(decryptByRsaKey(rsaPrivateKey, bytes), StandardCharsets.UTF_8);
        } catch (Exception e) {
            LOGGER.error("----------RSAUtils---------->私钥解密异常:{}", e.getMessage());
            throw new ResponseException(Status.VERIFICATION_FAILED, e.getMessage());
        }
        return result;
    }


    /**
     * 数据分组解密
     *
     * @param key                 {@link Key} RSA公钥或者私钥
     * @param ciphertext-密文byte数组
     * @return byte[] 明文byte数组
     * @throws Exception {@link Exception} 异常数据
     * @author zhouxuanhong
     * @date 2019-10-22 09:54
     */
    private static byte[] decryptByRsaKey(Key key, byte[] ciphertext) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA/None/PKCS1Padding", "BC");
        cipher.init(2, key);
        int inputLen = ciphertext.length;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offSet = 0;
        for (int i = 0; inputLen - offSet > 0; offSet = i * 256) {
            byte[] cache;
            if (inputLen - offSet > 256) {
                cache = cipher.doFinal(ciphertext, offSet, 256);
            } else {
                cache = cipher.doFinal(ciphertext, offSet, inputLen - offSet);
            }
            out.write(cache, 0, cache.length);
            ++i;
        }
        byte[] decryptedData = out.toByteArray();
        out.close();
        return decryptedData;
    }


    /**
     * 数据分组加密
     *
     * @param key                {@link Key} Rsa公钥或私钥
     * @param plaintext-明文byte数组
     * @return byte[] 密文byte数组
     * @throws Exception {@link Exception} 异常数据
     * @author zhouxuanhong
     * @date 2019-10-22 09:54
     */
    private static byte[] encryptByRsaKey(Key key, byte[] plaintext) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA/None/PKCS1Padding", "BC");
        cipher.init(1, key);
        int inputLen = plaintext.length;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offSet = 0;
        for (int i = 0; inputLen - offSet > 0; offSet = i * 244) {
            byte[] cache;
            if (inputLen - offSet > 244) {
                cache = cipher.doFinal(plaintext, offSet, 244);
            } else {
                cache = cipher.doFinal(plaintext, offSet, inputLen - offSet);
            }
            out.write(cache, 0, cache.length);
            ++i;
        }
        byte[] encryptedData = out.toByteArray();
        out.close();
        return encryptedData;
    }

    public static void main(String[] args) {
//        BaseSecurityModel x509AndPKCS8Key = getX509AndPKCS8Key();
//
//        String publicKey = x509AndPKCS8Key.getPublicKey();
//        System.out.println("------------RSA------公钥------>main:" + "公钥");
//        System.out.println(publicKey);
//        System.out.println();
//        System.out.println("------------RSA------------>main:" + "私钥");
//        String privateKey = x509AndPKCS8Key.getPrivateKey();
//        System.out.println(privateKey);
//
//        String str = "测试数据";
//        String s = encryptPublicKey(publicKey, str);
//        System.out.println("------------RSA------公钥加密------>main:" + s);
//
//
//        System.out.println("------------RSA------私钥解密------>main:" + decryptPrivateKey(privateKey, s));

        String publieKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCdzrWK/xmQuoQUIofMrueGYdN+5CFfv6vWtliphE0Kzjwy9JKh02ofP6yjW9xak9PTqT3gkFfcUnoxVLu77Uxq0pETu3b7if5ep460MuLkFp3xy0fGSRqkDMjxJdoItWwA5wfMDx0SPBIMIt2GAtOEsbDbcLLfkCLP8lTaIeHmAwIDAQAB";
        String s = encryptPublicKey(publieKey, "84d4e94cf59c4484");
        System.out.println("----密文----" + s);
        String privateKey = "MIICeAIBADANBgkqhkiG9w0BAQEFAASCAmIwggJeAgEAAoGBAKwhXqKVTT8dtfDHKpIL5dUdBgjwwCI0dslmiKxa6qT4TYs9Anx36YAFhBOAoYVrJliGbaiJ6M8rltfHPU2xm1yy+nsjsKYyVRAC2aNUyMHDR43AldC6yvQLmNQATXJGiOuC/aFW1wbCKZ8ptX5sWR8AKzN1l5gXxfkSgBhnmfDlAgMBAAECgYAAjTLSbowK0buNg/Xc9fDMmAU27h6PfX+L8aoqczghQzJsacuiPngfEuVzcowR81/yFKwxSA/Y6hjXpCnMH++RgSCLobIfdX84x4a+mRm3HiwqWXC2g9KXW4c/Sz6t3HDxRMsHxyt/iCfAUQ9UyoVpOcXhSt8OTvTJEXRi2wVPGQJBANvo+iIy6CN6HD0zhkuhKySXKirz904gYCsDr8EmgAd3TylDfQC6U3YYbohy306peul6ZuPje5jvLGuLD92v4ecCQQDIYQk+vinMMmMDLn6/oTob59Q3sIhQf7D9GjwHG9h/6vRxvlJqf86J5Lqc0DZ1Ngt7viD8jhoK6kZDqRMDnlVTAkEAhfcjifnhGNumoFHnGeOQ2LWN5qTAn0Nx0kJoqw29KM8x1j1axTq85VB05YK1LsJoMhty2pkWDlCFiu4oODCROQJBAJ05voZTsfo+QwB5GA0G1dCVnCfCRYNcXiSK1DUDvWHJmtow6vwTop0CMwbePQzFRkdnDPz+ByMvZcirPnclYm8CQQCxXEoz6kBdRVbQ90PR97BwAveTh+bj2Mh5BYbP7sfaCLSMlIYwUjWl3wb3HwH6Ka6wob5vR/ZeYCi7+8iuN1wb";
        String text = "";
        System.out.println("----明文----" + decryptPrivateKey(privateKey, s));
    }
}