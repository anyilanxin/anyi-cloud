// +----------------------------------------------------------------------
// | SkillFull快速开发平台 [ SkillFull ]
// +----------------------------------------------------------------------
// | 版权所有 2020~2021 zxiaozhou
// +----------------------------------------------------------------------
// | 官方网站: https://www.divisu.com
// +----------------------------------------------------------------------
// | 作者: zxiaozhou <z7630853@163.com>
// +----------------------------------------------------------------------
package indi.zxiaozhou.skillfull.auth.security.token.store;


import indi.zxiaozhou.skillfull.auth.core.constant.impl.LoginType;
import indi.zxiaozhou.skillfull.auth.security.login.service.model.CustomUserDetails;
import indi.zxiaozhou.skillfull.auth.security.token.LoginSuccessAuthenticationToken;
import indi.zxiaozhou.skillfull.auth.security.token.TokenStore;
import indi.zxiaozhou.skillfull.auth.utils.LoginUtil;
import indi.zxiaozhou.skillfull.corecommon.base.model.login.LoginOnlineInfoModel;
import indi.zxiaozhou.skillfull.corecommon.base.model.login.LoginTokenClaimSubModel;
import indi.zxiaozhou.skillfull.corecommon.base.model.login.LoginTokenSecurityModel;
import indi.zxiaozhou.skillfull.corecommon.base.model.login.LoginUserInfoModel;
import indi.zxiaozhou.skillfull.corecommon.constant.Status;
import indi.zxiaozhou.skillfull.corecommon.exception.ResponseException;
import indi.zxiaozhou.skillfull.corecommon.utils.TokenUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.mapping.NullAuthoritiesMapper;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.validation.constraints.NotNull;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import static indi.zxiaozhou.skillfull.corecommon.constant.SysBaseConstant.*;


/**
 * redis存储
 *
 * @author zxiaozhou
 * @date 2020-07-17 15:39
 * @since JDK11
 */
@Slf4j
public class RedisTokenStore implements TokenStore {
    private final RedisTemplate<String, Object> redisTemplate;

    public RedisTokenStore(@NotNull RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }


    @Override
    public LoginSuccessAuthenticationToken createAuthentication(Authentication authentication, CustomUserDetails userDetails) {
        // 创建token
        LoginSuccessAuthenticationToken token = LoginUtil.createToken(authentication, userDetails);
        // 如果是单设备登录则需要清空以前用户登录信息
        if (userDetails.getOnlineInfoModel().getLoginType() == LoginType.ONE_EQUIPMENT.getType()) {
            // 清空现有登录信息
            System.out.println("等待清空用户信息");
        }
        this.saveLogin(userDetails);
        return token;
    }


    private void saveLogin(CustomUserDetails userDetails) {
        LoginOnlineInfoModel onlineInfoModel = userDetails.getOnlineInfoModel();
        LoginUserInfoModel userInfoModel = userDetails.getUserInfoModel();
        LoginTokenSecurityModel securityModel = userDetails.getSecurityModel();
        // 用户在线信息
        redisTemplate.opsForValue().set(LOGIN_ONLINE_PREFIX + onlineInfoModel.getUserId() + ":" + onlineInfoModel.getLoginCode(), onlineInfoModel, securityModel.getTokenValidityInSeconds() + 60, TimeUnit.SECONDS);
        // 用户基本信息
        redisTemplate.opsForValue().set(LOGIN_USER_PREFIX + userInfoModel.getUserId() + ":" + onlineInfoModel.getLoginCode(), userInfoModel, securityModel.getTokenValidityInSeconds() + 60, TimeUnit.SECONDS);
        // 用户token解密信息
        redisTemplate.opsForValue().set(LOGIN_TOKEN_SECURITY_PREFIX + onlineInfoModel.getLoginCode(), securityModel, securityModel.getTokenValidityInSeconds() + 120, TimeUnit.SECONDS);
    }


    @Override
    public LoginSuccessAuthenticationToken refreshAuthentication(CustomUserDetails userDetails, String oldToken) {
        return null;
    }


    @Override
    public LoginSuccessAuthenticationToken getAuthentication(String token) {
        LoginTokenClaimSubModel tokenClaimSubModel = TokenUtils.getTokenSubInfoNoDecryption(token);
        Object objectTokenSecurity = redisTemplate.opsForValue().get(LOGIN_TOKEN_SECURITY_PREFIX + tokenClaimSubModel.getLoginCode());
        LoginTokenSecurityModel securityModel;
        LoginOnlineInfoModel onlineInfoModel;
        LoginUserInfoModel userInfoModel;
        if (Objects.isNull(objectTokenSecurity) || !(objectTokenSecurity instanceof LoginTokenSecurityModel)) {
            throw new ResponseException(Status.TOKEN_EXPIRED, "token无效");
        } else {
            securityModel = (LoginTokenSecurityModel) objectTokenSecurity;
        }

        // 数据解密
        TokenUtils.decryptionTokenSubInfo(tokenClaimSubModel, securityModel);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        // 用户在线信息
        Object objectOnline = redisTemplate.opsForValue().get(LOGIN_ONLINE_PREFIX + tokenClaimSubModel.getUserId() + ":" + tokenClaimSubModel.getLoginCode());
        if (Objects.isNull(objectOnline) || !(objectOnline instanceof LoginOnlineInfoModel)) {
            throw new ResponseException(Status.TOKEN_EXPIRED, "token无效");
        } else {
            onlineInfoModel = (LoginOnlineInfoModel) objectOnline;
        }
        // 用户基本信息
        Object objectUserInfo = redisTemplate.opsForValue().get(LOGIN_USER_PREFIX + tokenClaimSubModel.getUserId() + ":" + tokenClaimSubModel.getLoginCode());
        if (Objects.isNull(objectUserInfo) || !(objectUserInfo instanceof LoginUserInfoModel)) {
            throw new ResponseException(Status.TOKEN_EXPIRED, "token无效");
        } else {
            userInfoModel = (LoginUserInfoModel) objectUserInfo;
        }
        CustomUserDetails userDetails = new CustomUserDetails(onlineInfoModel, userInfoModel, securityModel);
        return new LoginSuccessAuthenticationToken(
                userDetails,
                onlineInfoModel.getDetails(),
                new NullAuthoritiesMapper().mapAuthorities(userDetails.getAuthorities()));
    }


    @Override
    public void refreshAuthenticationInfo(Authentication authentication, LoginUserInfoModel userInfo) {
        if (Objects.nonNull(authentication) && authentication instanceof LoginSuccessAuthenticationToken) {
            LoginSuccessAuthenticationToken token = (LoginSuccessAuthenticationToken) authentication;
            CustomUserDetails principal = token.getPrincipal();
            LoginOnlineInfoModel onlineInfoModel = principal.getOnlineInfoModel();
            String userInfoKey = LOGIN_USER_PREFIX + onlineInfoModel.getUserId() + ":" + onlineInfoModel.getLoginCode();
            Long expire = redisTemplate.getExpire(userInfoKey, TimeUnit.SECONDS);
            Boolean delete = redisTemplate.delete(userInfoKey);
            if (Objects.nonNull(delete) && delete && Objects.nonNull(expire)) {
                redisTemplate.opsForValue().set(userInfoKey, userInfo, expire, TimeUnit.SECONDS);
            } else {
                log.error("------------RedisTokenStore------------>refreshAuthenticationInfo--->\n参数:{},异常消息:{}", userInfo, "更新用户登录信息失败");
            }
        } else {
            throw new ResponseException(Status.TOKEN_EXPIRED, "登录过期或者授权信息异常");
        }
    }


    @Override
    public void removeAuthentication(String token) {
        try {
            LoginTokenClaimSubModel tokenClaimSubModel = TokenUtils.getTokenSubInfoNoDecryption(token);
            Object objectTokenSecurity = redisTemplate.opsForValue().get(LOGIN_TOKEN_SECURITY_PREFIX + tokenClaimSubModel.getLoginCode());
            LoginTokenSecurityModel securityModel;
            if (Objects.isNull(objectTokenSecurity) || !(objectTokenSecurity instanceof LoginTokenSecurityModel)) {
                throw new ResponseException(Status.TOKEN_EXPIRED, "token无效");
            } else {
                securityModel = (LoginTokenSecurityModel) objectTokenSecurity;
            }
            // 数据解密
            TokenUtils.decryptionTokenSubInfo(tokenClaimSubModel, securityModel);
            // 用户在线信息删除
            redisTemplate.delete(LOGIN_ONLINE_PREFIX + tokenClaimSubModel.getUserId() + ":" + tokenClaimSubModel.getLoginCode());
            // 用户基本信息删除
            redisTemplate.delete(LOGIN_USER_PREFIX + tokenClaimSubModel.getUserId() + ":" + tokenClaimSubModel.getLoginCode());
            // 用户token解密信息删除
            redisTemplate.delete(LOGIN_TOKEN_SECURITY_PREFIX + tokenClaimSubModel.getLoginCode());
        } catch (Exception e) {
            log.error("------------RedisTokenStore------移除授权信息失败------>removeAuthentication--->\n参数:{},异常消息:{}", token, e.getMessage());
        }
    }
}
