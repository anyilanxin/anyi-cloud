// +----------------------------------------------------------------------
// | SkillFull快速开发平台 [ SkillFull ]
// +----------------------------------------------------------------------
// | 版权所有 2020~2021 zxiaozhou
// +----------------------------------------------------------------------
// | 官方网站: https://www.divisu.com
// +----------------------------------------------------------------------
// | 作者: zxiaozhou <z7630853@163.com>
// +----------------------------------------------------------------------
package com.anyilanxin.skillfull.oauth2mvc.user.impl;

import cn.hutool.core.collection.CollUtil;
import com.anyilanxin.skillfull.corecommon.auth.model.RoleInfo;
import com.anyilanxin.skillfull.corecommon.auth.model.UserIdentity;
import com.anyilanxin.skillfull.corecommon.auth.model.UserInfo;
import com.anyilanxin.skillfull.oauth2common.authinfo.SkillFullUserDetails;
import com.anyilanxin.skillfull.oauth2common.mapstruct.OauthUserAndUserDetailsCopyMap;
import com.anyilanxin.skillfull.oauth2mvc.user.IGetLoginUserInfo;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.exceptions.UnauthorizedUserException;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenStore;

import java.util.Collections;
import java.util.Objects;
import java.util.Set;

/**
 * 获取用户信息
 *
 * @author zxiaozhou
 * @date 2022-04-09 10:02
 * @since JDK1.8
 */
public class GetLoginUserInfoImpl implements IGetLoginUserInfo {
    private final TokenStore tokenStore;
    private final OauthUserAndUserDetailsCopyMap detailsCopyMap;

    public GetLoginUserInfoImpl(final TokenStore tokenStore,
                                final OauthUserAndUserDetailsCopyMap detailsCopyMap) {
        this.tokenStore = tokenStore;
        this.detailsCopyMap = detailsCopyMap;
    }

    @Override
    public UserInfo getUserInfo(String token) {
        OAuth2AccessToken accessToken = tokenStore.readAccessToken(token);
        if (Objects.nonNull(accessToken)) {
            OAuth2Authentication oAuth2Authentication = tokenStore.readAuthentication(accessToken);
            Authentication userAuthentication = oAuth2Authentication.getUserAuthentication();
            Object userPrincipal = userAuthentication.getPrincipal();
            if (userPrincipal instanceof SkillFullUserDetails) {
                return detailsCopyMap.aToB((SkillFullUserDetails) userPrincipal);
            }
        }
        throw new UnauthorizedUserException("当前用户未授权");
    }


    @Override
    public UserInfo getUserInfo() {
        SecurityContext context = SecurityContextHolder.getContext();
        if (Objects.nonNull(context.getAuthentication())) {
            Authentication principal = context.getAuthentication();
            Object userPrincipal = principal.getPrincipal();
            if (userPrincipal instanceof SkillFullUserDetails) {
                return detailsCopyMap.aToB((SkillFullUserDetails) userPrincipal);
            }
        }
        throw new UnauthorizedUserException("当前用户未授权");
    }

    @Override
    public boolean superRole() {
        return getUserInfo().isSuperAdmin();
    }

    @Override
    public Set<RoleInfo> getRoleInfos() {
        Set<RoleInfo> roleInfos = getUserInfo().getRoleInfos();
        return CollUtil.isNotEmpty(roleInfos) ? roleInfos : Collections.emptySet();
    }

    @Override
    public Set<String> getRoleIds() {
        Set<String> roleIds = getUserInfo().getRoleIds();
        return CollUtil.isNotEmpty(roleIds) ? roleIds : Collections.emptySet();
    }


    @Override
    public Set<String> getRoleCodes() {
        Set<String> roleCodes = getUserInfo().getRoleCodes();
        return CollUtil.isNotEmpty(roleCodes) ? roleCodes : Collections.emptySet();
    }

    @Override
    public String getUserId() {
        return getUserInfo().getUserId();
    }

    @Override
    public String getUserName() {
        return getUserInfo().getUserName();
    }

    @Override
    public String getNickName() {
        return getUserInfo().getNickName();
    }

    @Override
    public String getRealName() {
        return getUserInfo().getRealName();
    }

    @Override
    public String getCurrentOrgId() {
        return getUserInfo().getCurrentOrgId();
    }

    @Override
    public String getCurrentOrgCode() {
        return getUserInfo().getCurrentOrgCode();
    }

    @Override
    public String getCurrentAreaCode() {
        return getUserInfo().getCurrentAreaCode();
    }

    @Override
    public String getCurrentTenantId() {
        return getUserInfo().getCurrentTenantId();
    }

    @Override
    public String getPhone() {
        return getUserInfo().getPhone();
    }


    @Override
    public int getIdentityStatus() {
        return getUserInfo().getIdentityStatus();
    }

    @Override
    public UserIdentity getIdentity() {
        return getUserInfo().getIdentity();
    }
}
