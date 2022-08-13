// +----------------------------------------------------------------------
// | SkillFull快速开发平台 [ SkillFull ]
// +----------------------------------------------------------------------
// | 版权所有 2020~2021 zxiaozhou
// +----------------------------------------------------------------------
// | 官方网站: https://www.divisu.com
// +----------------------------------------------------------------------
// | 作者: zxiaozhou <z7630853@163.com>
// +----------------------------------------------------------------------
package com.anyilanxin.skillfull.oauth2common.utils;

import cn.hutool.core.collection.CollUtil;
import com.anyilanxin.skillfull.corecommon.constant.impl.CommonNotHaveType;
import com.anyilanxin.skillfull.corecommon.model.system.ClientAndResourceAuthModel;
import com.anyilanxin.skillfull.corecommon.model.system.UserAndResourceAuthModel;
import com.anyilanxin.skillfull.oauth2common.authinfo.SkillFullClientDetails;
import com.anyilanxin.skillfull.oauth2common.authinfo.SkillFullGrantedAuthority;
import com.anyilanxin.skillfull.oauth2common.authinfo.SkillFullUserDetails;
import com.anyilanxin.skillfull.oauth2common.constant.Oauth2CommonConstant;
import com.anyilanxin.skillfull.oauth2common.mapstruct.ClientDetailsCopyMap;
import com.anyilanxin.skillfull.oauth2common.mapstruct.UserDetailsCopyMap;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * @author zxiaozhou
 * @date 2022-03-03 14:19
 * @since JDK1.8
 */
@Component
@RequiredArgsConstructor
public class Oauth2CommonUtils {
    private static Oauth2CommonUtils utils;
    private final TokenStore tokenStore;
    private final UserDetailsCopyMap userDetailsCopyMap;
    private final ClientDetailsCopyMap clientDetailsCopyMap;

    @PostConstruct
    private void init() {
        utils = this;
    }

    /**
     * SystemUserAndActionModel转换为CustomUserDetails
     *
     * @param model ${@link UserAndResourceAuthModel}
     * @return CustomUserDetails ${@link SkillFullUserDetails}
     * @author zxiaozhou
     * @date 2022-03-04 01:51
     */
    public static SkillFullUserDetails toUserDetails(UserAndResourceAuthModel model) {
        SkillFullUserDetails userDetails = utils.userDetailsCopyMap.aToB(model);
        Integer userStatus = model.getUserStatus();
        userDetails.setUserId(model.getUserId());
        userDetails.setAccountNonLocked(userStatus != 2);
        userDetails.setAccountNonExpired(userStatus != 0);
        userDetails.setEnabled(userStatus == 1);
        userDetails.setCredentialsNonExpired(true);
        Set<GrantedAuthority> authorities = new HashSet<>();
        if (CollUtil.isNotEmpty(model.getActions())) {
            model.getActions().forEach((k, v) -> {
                v.forEach(sv -> {
                    SkillFullGrantedAuthority authority = new SkillFullGrantedAuthority(k, sv);
                    authorities.add(authority);
                });
            });
        }
        if (CollUtil.isNotEmpty(model.getRoleInfos())) {
            model.getRoleInfos().forEach(v -> {
                SimpleGrantedAuthority authority = new SimpleGrantedAuthority(Oauth2CommonConstant.DEFAULT_ROLE_PREFIX + v.getRoleCode());
                authorities.add(authority);
            });
        }
        if (CollUtil.isNotEmpty(authorities)) {
            userDetails.setAuthorities(authorities);
        }
        return userDetails;
    }


    /**
     * ManageClientDetailsModel转换为CustomClientDetails
     *
     * @param model ${@link ClientAndResourceAuthModel}
     * @return CustomClientDetails ${@link SkillFullClientDetails}
     * @author zxiaozhou
     * @date 2022-03-04 01:51
     */
    public static SkillFullClientDetails toClientDetails(ClientAndResourceAuthModel model) {
        SkillFullClientDetails customClientDetails = utils.clientDetailsCopyMap.aToB(model);
        customClientDetails.setScoped(model.getHavaScoped() == CommonNotHaveType.HAVE.getType());
        if (CollUtil.isNotEmpty(model.getScopes())) {
            customClientDetails.setScope(new HashSet<>(model.getScopes()));
        }
        customClientDetails.setClientSecret(model.getClientSecurity());
        if (StringUtils.isNotBlank(model.getWebRegisteredRedirectUri())) {
            customClientDetails.setRegisteredRedirectUri(Set.of(model.getWebRegisteredRedirectUri()));
        }
        Set<GrantedAuthority> authorities = new HashSet<>();
        if (CollUtil.isNotEmpty(model.getActions())) {
            model.getActions().forEach((k, v) -> {
                v.forEach(sv -> {
                    SkillFullGrantedAuthority authority = new SkillFullGrantedAuthority(k, sv);
                    authorities.add(authority);
                });
            });
        }
        if (CollUtil.isNotEmpty(model.getRoleInfos())) {
            Set<String> roleCodes = new HashSet<>(model.getRoleInfos().size());
            Set<String> roleIds = new HashSet<>(model.getRoleInfos().size());
            model.getRoleInfos().forEach(v -> {
                roleCodes.add(v.getRoleCode());
                roleIds.add(v.getRoleId());
                SimpleGrantedAuthority authority = new SimpleGrantedAuthority(Oauth2CommonConstant.DEFAULT_ROLE_PREFIX + v.getRoleCode());
                authorities.add(authority);
            });
            customClientDetails.setRoleCodes(roleCodes);
            customClientDetails.setRoleIds(roleIds);
        }
        if (CollUtil.isNotEmpty(authorities)) {
            customClientDetails.setAuthorities(authorities);
        }
        return customClientDetails;
    }


    /**
     * 刷新当前用户信息
     *
     * @param model ${@link UserAndResourceAuthModel}
     * @author zxiaozhou
     * @date 2022-03-11 01:15
     */
    public static void refreshUserOauth(UserAndResourceAuthModel model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        refreshUserOauth(authentication, model);
    }


    /**
     * 刷新用户信息
     *
     * @param authentication ${@link Authentication}权限信息
     * @param model          ${@link UserAndResourceAuthModel} 新的用户信息
     * @author zxiaozhou
     * @date 2022-04-05 23:23
     */
    private static void refreshUserOauth(Authentication authentication, UserAndResourceAuthModel model) {
        // 在获取用户信息
        if (authentication instanceof OAuth2Authentication) {
            OAuth2Authentication auth2Authentication = (OAuth2Authentication) authentication;
            Authentication userAuthentication = auth2Authentication.getUserAuthentication();
            if (Objects.nonNull(userAuthentication) && userAuthentication instanceof UsernamePasswordAuthenticationToken) {
                SkillFullUserDetails customUserDetails = toUserDetails(model);
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(customUserDetails, userAuthentication.getCredentials(), userAuthentication.getAuthorities());
                auth2Authentication = new OAuth2Authentication(auth2Authentication.getOAuth2Request(), usernamePasswordAuthenticationToken);
            }
            DefaultOAuth2AccessToken accessToken = (DefaultOAuth2AccessToken) utils.tokenStore.getAccessToken(auth2Authentication);
            utils.tokenStore.storeAccessToken(accessToken, auth2Authentication);
            SecurityContextHolder.getContext().setAuthentication(auth2Authentication);
        }
    }


    /**
     * 根据token刷新用户信息
     *
     * @param model ${@link UserAndResourceAuthModel}
     * @author zxiaozhou
     * @date 2022-03-11 01:15
     */
    public static void refreshUserOauth(UserAndResourceAuthModel model, String token) {
    }


    /**
     * 根据userId刷新用户信息
     *
     * @param model ${@link UserAndResourceAuthModel}
     * @author zxiaozhou
     * @date 2022-03-11 01:15
     */
    public static void refreshUserOauth(String userId, UserAndResourceAuthModel model) {
    }
}
