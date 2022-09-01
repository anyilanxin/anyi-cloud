// +----------------------------------------------------------------------
// | SkillFull快速开发平台 [ SkillFull ]
// +----------------------------------------------------------------------
// | 版权所有 2020~2021 zxiaozhou
// +----------------------------------------------------------------------
// | 官方网站: https://skillfull.divisu.com
// +----------------------------------------------------------------------
// | 作者: zxiaozhou <z7630853@163.com>
// +----------------------------------------------------------------------
package com.anyilanxin.skillfull.oauth2webflux.user.impl;

import com.anyilanxin.skillfull.corecommon.model.auth.RoleInfo;
import com.anyilanxin.skillfull.corecommon.model.auth.UserIdentity;
import com.anyilanxin.skillfull.corecommon.model.auth.UserInfo;
import com.anyilanxin.skillfull.oauth2common.mapstruct.OauthUserAndUserDetailsCopyMap;
import com.anyilanxin.skillfull.oauth2webflux.user.IGetLoginUserInfo;
import org.springframework.security.oauth2.provider.token.TokenStore;
import reactor.core.publisher.Mono;

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
