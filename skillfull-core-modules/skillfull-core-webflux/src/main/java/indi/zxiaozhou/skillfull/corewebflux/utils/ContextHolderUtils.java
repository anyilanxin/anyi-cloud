// +----------------------------------------------------------------------
// | SkillFull快速开发平台 [ SkillFull ]
// +----------------------------------------------------------------------
// | 版权所有 2020~2021 zxiaozhou
// +----------------------------------------------------------------------
// | 官方网站: https://www.divisu.com
// +----------------------------------------------------------------------
// | 作者: zxiaozhou <z7630853@163.com>
// +----------------------------------------------------------------------
package indi.zxiaozhou.skillfull.corewebflux.utils;

import cn.hutool.core.collection.CollectionUtil;
import indi.zxiaozhou.skillfull.corecommon.base.model.auth.RoleModel;
import indi.zxiaozhou.skillfull.corecommon.base.model.login.LoginOnlineInfoModel;
import indi.zxiaozhou.skillfull.corecommon.base.model.login.LoginTokenClaimSubModel;
import indi.zxiaozhou.skillfull.corecommon.base.model.login.LoginTokenSecurityModel;
import indi.zxiaozhou.skillfull.corecommon.base.model.login.LoginUserInfoModel;
import indi.zxiaozhou.skillfull.corecommon.constant.Status;
import indi.zxiaozhou.skillfull.corecommon.constant.SysBaseConstant;
import indi.zxiaozhou.skillfull.corecommon.exception.ResponseException;
import indi.zxiaozhou.skillfull.corecommon.utils.TokenUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.*;

/**
 * 上下文持有者
 *
 * @author zxiaozhou
 * @date 2020-10-07 09:07
 * @since JDK11
 */
@RequiredArgsConstructor
@Component
@Slf4j
public class ContextHolderUtils {
    private static ContextHolderUtils utils;
    private final RedisTemplate<String, Object> redisTemplate;

    /**
     * 获取登录用户信息
     *
     * @return LoginUserInfoModel ${@link LoginUserInfoModel}
     * @author zxiaozhou
     * @date 2020-10-07 09:08
     */
    public static LoginUserInfoModel getUserAndAuthModel() {
        String token = ServletUtils.getSubToken();
        return getUserAndAuthModel(token);
    }


    /**
     * 获取登录用户信息
     *
     * @return LoginUserInfoModel ${@link LoginUserInfoModel}
     * @author zxiaozhou
     * @date 2020-10-07 09:08
     */
    public static LoginUserInfoModel getUserAndAuthModel(String token) {
        if (StringUtils.isNotBlank(token)) {
            LoginTokenClaimSubModel tokenClaimSubModel = getTokenClaimSubModel(token);
            // 获取用户信息
            Object objectUserInfo = utils.redisTemplate.opsForValue().get(SysBaseConstant.LOGIN_USER_PREFIX + tokenClaimSubModel.getUserId() + ":" + tokenClaimSubModel.getLoginCode());
            if (Objects.nonNull(objectUserInfo) && objectUserInfo instanceof LoginUserInfoModel) {
                return (LoginUserInfoModel) objectUserInfo;
            }
            throw new ResponseException(Status.TOKEN_EXPIRED, "登录过期,请重新登录");
        }
        throw new ResponseException(Status.TOKEN_EXPIRED, "未登录,请先登录");
    }


    /**
     * 获取登录信息
     *
     * @return LoginUserInfoModel ${@link LoginUserInfoModel}
     * @author zxiaozhou
     * @date 2020-10-07 09:08
     */
    public static LoginOnlineInfoModel getLoginOnlineInfoModel(String token) {
        if (StringUtils.isNotBlank(token)) {
            LoginTokenClaimSubModel tokenClaimSubModel = getTokenClaimSubModel(token);
            // 获取用户信息
            Object objectOnlineInfo = utils.redisTemplate.opsForValue().get(SysBaseConstant.LOGIN_ONLINE_PREFIX + tokenClaimSubModel.getUserId() + ":" + tokenClaimSubModel.getLoginCode());
            if (Objects.nonNull(objectOnlineInfo) && objectOnlineInfo instanceof LoginOnlineInfoModel) {
                return (LoginOnlineInfoModel) objectOnlineInfo;
            }
            throw new ResponseException(Status.TOKEN_EXPIRED, "登录过期,请重新登录");
        }
        throw new ResponseException(Status.TOKEN_EXPIRED, "未登录,请先登录");
    }


    /**
     * 获取用户id
     *
     * @return String ${@link String}
     * @author zxiaozhou
     * @date 2020-10-22 20:35
     */
    public static String getUserId() {
        return getUserAndAuthModel().getUserId();
    }


    /**
     * 获取当前租户id
     *
     * @return String ${@link String} 租户id
     * @author zxiaozhou
     * @date 2021-05-21 03:18
     */
    public static String getTenantId() {
        return getUserAndAuthModel().getCurrentTenantId();
    }

    /**
     * 获取用户角色信息
     *
     * @return String ${@link String}
     * @author zxiaozhou
     * @date 2020-10-22 20:35
     */
    public static List<RoleModel> getRoleInfo() {
        Set<RoleModel> roles = getUserAndAuthModel().getRoleInfo();
        if (CollectionUtil.isNotEmpty(roles)) {
            return new ArrayList<>(roles);
        }
        return Collections.emptyList();
    }

    /**
     * 获取用户角色编码
     *
     * @return String ${@link String}
     * @author zxiaozhou
     * @date 2020-10-22 20:35
     */
    public static List<String> getRoleCode() {
        List<RoleModel> roles = getRoleInfo();
        if (CollectionUtil.isNotEmpty(roles)) {
            List<String> roleCode = new ArrayList<>();
            roles.forEach(v -> roleCode.add(v.getRoleCode()));
            return roleCode;
        }
        return Collections.emptyList();
    }

    /**
     * 获取用户真实姓名
     *
     * @return String ${@link String}
     * @author zxiaozhou
     * @date 2020-10-22 20:36
     */
    public static String getRealName() {
        return getUserAndAuthModel().getRealName();
    }


    /**
     * 获取解密后的token信息
     *
     * @param token ${@link String} token信息
     * @return LoginOnlineInfoModel ${@link LoginOnlineInfoModel} 用户登录在线信息
     * @author zxiaozhou
     * @date 2020-10-20 18:02
     */
    public static LoginTokenClaimSubModel getTokenClaimSubModel(String token) {
        LoginTokenClaimSubModel tokenSubInfo = TokenUtils.getTokenSubInfoNoDecryption(token);
        // 获取token加解密信息
        LoginTokenSecurityModel tokenSecurity = getTokenSecurity(token);
        // 验证token
        boolean b = TokenUtils.verifyAndTime(token, tokenSecurity);
        if (!b) {
            throw new ResponseException(Status.TOKEN_EXPIRED, "登录过期,请重新登录");
        }
        // 解密数据
        TokenUtils.decryptionTokenSubInfo(tokenSubInfo, tokenSecurity);
        return tokenSubInfo;
    }


    /**
     * 获取token解密信息
     *
     * @param token ${@link String} token
     * @return LoginTokenSecurityModel ${@link LoginTokenSecurityModel}
     * @author zxiaozhou
     * @date 2021-06-03 09:36
     */
    public static LoginTokenSecurityModel getTokenSecurity(String token) {
        LoginTokenClaimSubModel tokenSubInfo = TokenUtils.getTokenSubInfoNoDecryption(token);
        // 获取token加解密信息
        Object objectTokenSecurity = utils.redisTemplate.opsForValue().get(SysBaseConstant.LOGIN_TOKEN_SECURITY_PREFIX + tokenSubInfo.getLoginCode());
        LoginTokenSecurityModel securityModel;
        if (Objects.isNull(objectTokenSecurity) || !(objectTokenSecurity instanceof LoginTokenSecurityModel)) {
            throw new ResponseException(Status.TOKEN_EXPIRED, "登录过期,请重新登录");
        } else {
            securityModel = (LoginTokenSecurityModel) objectTokenSecurity;
        }
        return securityModel;
    }

    @PostConstruct
    private void init() {
        utils = this;
    }
}
