// +----------------------------------------------------------------------
// | SkillFull快速开发平台 [ SkillFull ]
// +----------------------------------------------------------------------
// | 版权所有 2020~2021 zxiaozhou
// +----------------------------------------------------------------------
// | 官方网站: https://www.divisu.com
// +----------------------------------------------------------------------
// | 作者: zxiaozhou <z7630853@163.com>
// +----------------------------------------------------------------------
package indi.zxiaozhou.skillfull.corecommon.utils;

import cn.hutool.core.util.IdUtil;
import com.alibaba.fastjson.JSONObject;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import indi.zxiaozhou.skillfull.corecommon.base.model.login.LoginOnlineInfoModel;
import indi.zxiaozhou.skillfull.corecommon.base.model.login.LoginTokenClaimSubModel;
import indi.zxiaozhou.skillfull.corecommon.base.model.login.LoginTokenSecurityModel;
import indi.zxiaozhou.skillfull.corecommon.constant.Status;
import indi.zxiaozhou.skillfull.corecommon.exception.ResponseException;
import indi.zxiaozhou.skillfull.corecommon.utils.encryption.RSAUtils;
import org.apache.commons.lang3.StringUtils;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static indi.zxiaozhou.skillfull.corecommon.constant.SysBaseConstant.CLAIM_SUB_KEY;

/**
 * token工具类
 *
 * @author zxiaozhou
 * @date 2020-09-29 00:55
 * @since JDK11
 */
public class TokenUtils {
    /**
     * 检验token是否有效(同时验证是否过期)
     *
     * @param token         ${@link String} token
     * @param securityModel ${@link LoginTokenSecurityModel} token 加解密信息
     * @return true-有效,false-无效
     */
    public static boolean verifyAndTime(String token, LoginTokenSecurityModel securityModel) {
        if (!token.equals(securityModel.getToken())) {
            return false;
        }
        try {
            JWTVerifier verifier = JWT.require(getAlgorithm(securityModel)).build();
            DecodedJWT jwt = verifier.verify(token);
            Date expiresAt = jwt.getExpiresAt();
            Date nowDate = new Date();
            return nowDate.compareTo(expiresAt) < 0;
        } catch (Exception e) {
            return false;
        }
    }


    /**
     * 检验token是否有效(不检验过期时间)
     *
     * @param token         ${@link String} token
     * @param securityModel ${@link LoginTokenSecurityModel} token 加解密信息
     * @return true-有效,false-无效
     */
    public static boolean verify(String token, LoginTokenSecurityModel securityModel) {
        if (!token.equals(securityModel.getToken())) {
            return false;
        }
        try {
            JWTVerifier verifier = JWT.require(getAlgorithm(securityModel)).build();
            verifier.verify(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }


    /**
     * 创建token
     *
     * @param onlineInfoModel ${@link LoginOnlineInfoModel} online信息
     * @param securityModel   ${@link LoginTokenSecurityModel} token加解密信息
     * @return String ${@link String} 生成token
     * @author zxiaozhou
     * @date 2020-06-30 00:57
     */
    public static String createToken(LoginOnlineInfoModel onlineInfoModel, LoginTokenSecurityModel securityModel) {
        // 设置头部信息
        Map<String, Object> header = new HashMap<>(2);
        header.put("Type", "Jwt");
        header.put("alg", "RSA256");
        LoginTokenClaimSubModel loginTokenClaimSubModel = new LoginTokenClaimSubModel();
        loginTokenClaimSubModel.setLoginCode(onlineInfoModel.getLoginCode());
        loginTokenClaimSubModel.setLoginEndpoint(onlineInfoModel.getLoginEndpoint());
        loginTokenClaimSubModel.setUserId(onlineInfoModel.getUserId());
        loginTokenClaimSubModel.setTenantId(onlineInfoModel.getTenantId());
        if (StringUtils.isNotBlank(onlineInfoModel.getUserId())) {
            loginTokenClaimSubModel.setUserId(RSAUtils.encryptPublicKey(securityModel.getPublicKey(), onlineInfoModel.getUserId()));
        }
        String token = JWT.create()
                .withHeader(header)
                .withExpiresAt(CoreCommonDateUtils.localDateTimeToDateTime(securityModel.getExpiresAt()))
                .withClaim(CLAIM_SUB_KEY, CoreCommonUtils.objectToJsonStr(loginTokenClaimSubModel))
                .withIssuer("auth service")
                .withIssuedAt(new Date())
                .withJWTId(IdUtil.simpleUUID())
                .sign(getAlgorithm(securityModel));
        securityModel.setToken(token);
        return token;
    }


    /**
     * 获取token过期时间
     *
     * @param token         ${@link String} token
     * @param securityModel ${@link LoginTokenSecurityModel} token解密信息
     * @return 剩余到期时间(单位毫秒)
     */
    public static long getExpires(String token, LoginTokenSecurityModel securityModel) {
        if (!token.equals(securityModel.getToken())) {
            return 0L;
        }
        try {
            Algorithm algorithm = getAlgorithm(securityModel);
            JWTVerifier verifier = JWT.require(algorithm).build();
            Date expiresAt = verifier.verify(token).getExpiresAt();
            return expiresAt.getTime() - System.currentTimeMillis();
        } catch (Exception e) {
            return 0L;
        }
    }

    /**
     * 获取加密信息
     *
     * @param securityModel ${@link LoginTokenSecurityModel}
     * @return Algorithm ${@link Algorithm}
     * @author zxiaozhou
     * @date 2020-10-20 15:49
     */
    private static Algorithm getAlgorithm(LoginTokenSecurityModel securityModel) {
        RSAPublicKey publicKey = RSAUtils.getPublicKey(securityModel.getPublicKey());
        RSAPrivateKey privateKey = RSAUtils.getPrivateKey(securityModel.getPrivateKey());
        return Algorithm.RSA256(publicKey, privateKey);
    }


    /**
     * 获取jwt中编码信息
     *
     * @param token ${@link String}
     * @return DecodedJWT ${@link DecodedJWT}
     * @author zxiaozhou
     * @date 2020-10-20 16:52
     */
    public static LoginTokenClaimSubModel getTokenSubInfoNoDecryption(String token) {
        if (StringUtils.isNotBlank(token)) {
            String sub;
            try {
                DecodedJWT decoded = JWT.decode(token);
                sub = decoded.getClaims().get(CLAIM_SUB_KEY).asString();
            } catch (Exception e) {
                throw new ResponseException(Status.TOKEN_EXPIRED, "token无效，请重新登录");
            }
            if (StringUtils.isNotBlank(sub)) {
                return JSONObject.parseObject(sub, LoginTokenClaimSubModel.class);
            } else {
                throw new ResponseException(Status.TOKEN_EXPIRED, "token无效，请重新登录");
            }
        } else {
            throw new ResponseException(Status.TOKEN_EXPIRED, "当前用户未登录，请登录");
        }
    }


    /**
     * 获取jwt中登录序号
     *
     * @param token ${@link String}
     * @return DecodedJWT ${@link DecodedJWT}
     * @author zxiaozhou
     * @date 2020-10-20 16:52
     */
    public static String getLoginCode(String token) {
        LoginTokenClaimSubModel tokenSubInfoNoDecryption = getTokenSubInfoNoDecryption(token);
        return tokenSubInfoNoDecryption.getLoginCode();
    }


    /**
     * token中用户信息解密
     *
     * @param userTokenSubInfoModel ${@link LoginTokenClaimSubModel}  LoginTokenClaimSubModel 信息
     * @param securityModel         ${@link LoginTokenSecurityModel} 解密密钥
     * @author zxiaozhou
     * @date 2020-10-20 17:14
     */
    public static void decryptionTokenSubInfo(LoginTokenClaimSubModel userTokenSubInfoModel, LoginTokenSecurityModel securityModel) {
        userTokenSubInfoModel.setUserId(RSAUtils.decryptPrivateKey(securityModel.getPrivateKey(), userTokenSubInfoModel.getUserId()));
    }
}
