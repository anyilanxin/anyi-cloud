

package com.anyilanxin.anyicloud.oauth2webflux.user.impl;

import com.anyilanxin.anyicloud.corecommon.model.auth.RoleInfo;
import com.anyilanxin.anyicloud.corecommon.model.auth.UserIdentity;
import com.anyilanxin.anyicloud.corecommon.model.auth.UserInfo;
import com.anyilanxin.anyicloud.oauth2common.mapstruct.OauthUserAndUserDetailsCopyMap;
import com.anyilanxin.anyicloud.oauth2webflux.user.IGetLoginUserInfo;

import java.util.Set;

import org.springframework.security.oauth2.provider.token.TokenStore;
import reactor.core.publisher.Mono;

/**
 * 获取用户信息
 *
 * @author zxh
 * @date 2022-04-09 10:02
 * @since 1.0.0
 */
public class GetLoginUserInfoImpl implements IGetLoginUserInfo {
    private final TokenStore tokenStore;
    private final OauthUserAndUserDetailsCopyMap detailsCopyMap;

    public GetLoginUserInfoImpl(final TokenStore tokenStore, final OauthUserAndUserDetailsCopyMap detailsCopyMap) {
        this.tokenStore = tokenStore;
        this.detailsCopyMap = detailsCopyMap;
    }


    @Override
    public Mono<UserInfo> getUserInfo(String token) {
        return null;
    }


    @Override
    public Mono<UserInfo> getUserInfo() {
        return null;
    }


    @Override
    public Mono<Integer> getIdentityStatus() {
        return null;
    }


    @Override
    public Mono<UserIdentity> getIdentity() {
        return null;
    }


    @Override
    public Mono<Boolean> superRole() {
        return null;
    }


    @Override
    public Mono<Set<RoleInfo>> getRoleInfos() {
        return null;
    }


    @Override
    public Mono<Set<String>> getRoleCodes() {
        return null;
    }


    @Override
    public Mono<Set<String>> getRoleIds() {
        return null;
    }


    @Override
    public Mono<String> getUserId() {
        return null;
    }


    @Override
    public Mono<String> getUserName() {
        return null;
    }


    @Override
    public Mono<String> getNickName() {
        return null;
    }


    @Override
    public Mono<String> getRealName() {
        return null;
    }


    @Override
    public Mono<String> getCurrentOrgId() {
        return null;
    }


    @Override
    public Mono<String> getCurrentOrgCode() {
        return null;
    }


    @Override
    public Mono<String> getCurrentAreaCode() {
        return null;
    }


    @Override
    public Mono<String> getCurrentTenantId() {
        return null;
    }


    @Override
    public Mono<String> getPhone() {
        return null;
    }
}
