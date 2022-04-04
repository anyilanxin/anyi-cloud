// +----------------------------------------------------------------------
// | SkillFull快速开发平台 [ SkillFull ]
// +----------------------------------------------------------------------
// | 版权所有 2020~2021 zxiaozhou
// +----------------------------------------------------------------------
// | 官方网站: https://www.divisu.com
// +----------------------------------------------------------------------
// | 作者: zxiaozhou <z7630853@163.com>
// +----------------------------------------------------------------------
package indi.zxiaozhou.skillfull.auth.utils;

import indi.zxiaozhou.skillfull.auth.security.config.properties.SecurityProperties;
import indi.zxiaozhou.skillfull.auth.security.login.service.model.CustomUserDetails;
import indi.zxiaozhou.skillfull.auth.security.login.service.model.TokenInfo;
import indi.zxiaozhou.skillfull.auth.security.mapstruct.TokenInfoMap;
import indi.zxiaozhou.skillfull.auth.security.token.LoginSuccessAuthenticationToken;
import indi.zxiaozhou.skillfull.corecommon.base.model.login.LoginOnlineInfoModel;
import indi.zxiaozhou.skillfull.corecommon.base.model.login.LoginTokenSecurityModel;
import indi.zxiaozhou.skillfull.corecommon.base.model.system.BaseSecurityModel;
import indi.zxiaozhou.skillfull.corecommon.constant.impl.LoginEndpointType;
import indi.zxiaozhou.skillfull.corecommon.utils.TokenUtils;
import indi.zxiaozhou.skillfull.corecommon.utils.encryption.RSAUtils;
import indi.zxiaozhou.skillfull.coremvc.utils.ServletUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.mapping.GrantedAuthoritiesMapper;
import org.springframework.security.core.authority.mapping.NullAuthoritiesMapper;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

/**
 * 登录工具类
 *
 * @author zxiaozhou
 * @date 2021-06-04 00:49
 * @since JDK1.8
 */
@Component
@RequiredArgsConstructor
public class LoginUtil {
    private static LoginUtil util;
    private final HttpServletRequest request;
    private final TokenInfoMap infoMap;
    private final SecurityProperties properties;
    GrantedAuthoritiesMapper AUTHORITIES_MAPPER = new NullAuthoritiesMapper();

    @PostConstruct
    private void init() {
        util = this;
    }

    /**
     * 创建登录信息
     *
     * @param endpointType ${@link LoginEndpointType} 登录端点
     * @return LoginOnlineInfoModel ${@link LoginOnlineInfoModel}
     * @author zxiaozhou
     * @date 2021-06-04 01:02
     */
    public static LoginOnlineInfoModel createOnline(LoginEndpointType endpointType) {
        LoginOnlineInfoModel model = new LoginOnlineInfoModel();
        model.setLoginEndpoint(endpointType.getType());
        model.setLoginIp(ServletUtils.getIpAddr(util.request));
        model.setLoginTime(LocalDateTime.now());
        model.setLoginEquipment(ServletUtils.getUserAgent(util.request));
        return model;
    }


    /**
     * 创建token加解密信息
     *
     * @return LoginOnlineInfoModel ${@link LoginOnlineInfoModel}
     * @author zxiaozhou
     * @date 2021-06-04 01:02
     */
    public static LoginTokenSecurityModel createTokenSecurity() {
        LoginTokenSecurityModel model = new LoginTokenSecurityModel();
        BaseSecurityModel securityModel = RSAUtils.getX509AndPKCS8Key();
        model.setPrivateKey(securityModel.getPrivateKey());
        model.setPublicKey(securityModel.getPublicKey());
        return model;
    }


    /**
     * 创建token基本信息
     *
     * @return TokenInfo ${@link TokenInfo}
     * @author zxiaozhou
     * @date 2021-06-04 09:15
     */
    public static TokenInfo getTokenInfo() {
        return util.infoMap.aToB(util.properties);
    }


    /**
     * 创建token
     *
     * @param authentication ${@link Authentication}
     * @param userDetails    ${@link CustomUserDetails}
     * @return LoginSuccessAuthenticationToken ${@link LoginSuccessAuthenticationToken}
     * @author zxiaozhou
     * @date 2020-09-29 00:41
     */
    public static LoginSuccessAuthenticationToken createToken(Authentication authentication, CustomUserDetails userDetails) {
        // token加解密信息
        LoginTokenSecurityModel securityModel = userDetails.getSecurityModel();
        // 用户在线信息
        LoginOnlineInfoModel onlineInfoModel = userDetails.getOnlineInfoModel();
        onlineInfoModel.setCredentials(authentication.getCredentials());
        // 计算token有效期止
        long tokenDetectInSeconds = securityModel.getTokenValidityInSeconds();
        LocalDateTime expiresAt = Instant.ofEpochMilli(System.currentTimeMillis() + tokenDetectInSeconds * 1000).atZone(ZoneOffset.ofHours(8)).toLocalDateTime();
        onlineInfoModel.setExpiresAt(expiresAt);
        securityModel.setExpiresAt(expiresAt);
        // 生成token
        String token = TokenUtils.createToken(onlineInfoModel, securityModel);
        // 生成token info信息
        TokenInfo tokenInfo = LoginUtil.getTokenInfo();
        tokenInfo.setToken(token);
        tokenInfo.setExpiresAt(onlineInfoModel.getExpiresAt());
        tokenInfo.setValidityInSeconds(securityModel.getTokenValidityInSeconds());
        // 构建登录成功token
        return new LoginSuccessAuthenticationToken(
                userDetails,
                onlineInfoModel.getDetails(),
                util.AUTHORITIES_MAPPER.mapAuthorities(userDetails.getAuthorities()));
    }
}
