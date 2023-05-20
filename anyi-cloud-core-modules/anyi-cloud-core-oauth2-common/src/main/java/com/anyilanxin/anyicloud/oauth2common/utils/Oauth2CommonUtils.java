/*
 * Copyright (c) 2021-2022 ZHOUXUANHONG(安一老厨)<anyilanxin@aliyun.com>
 *
 * AnYi Cloud Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * AnYi Cloud 采用APACHE LICENSE 2.0开源协议，您在使用过程中，需要注意以下几点：
 *   1.请不要删除和修改根目录下的LICENSE文件；
 *   2.请不要删除和修改 AnYi Cloud 源码头部的版权声明；
 *   3.请保留源码和相关描述文件的项目出处，作者声明等；
 *   4.分发源码时候，请注明软件出处 https://github.com/anyilanxin/anyi-cloud；
 *   5.在修改包名，模块名称，项目代码等时，请注明软件出处 https://github.com/anyilanxin/anyi-cloud；
 *   6.本软件不允许在国家法律规定范围外使用，如出现违法行为原作者本人不承担任何法律风险；
 *   7.本软件使用的第三方依赖皆为开源软件，如需要修改第三方源码请遵循第三方源码附带开源协议；
 *   8.本软件流程部分请遵循camunda开源协议：
 *     https://docs.camunda.org/manual/latest/introduction/third-party-libraries
 *     https://github.com/camunda/camunda-bpm-platform/blob/master/LICENSE
 *   9.若您的项目无法满足以上几点，可申请商业授权。
 */

package com.anyilanxin.anyicloud.oauth2common.utils;

import cn.hutool.core.collection.CollUtil;
import com.anyilanxin.anyicloud.corecommon.constant.AuthConstant;
import com.anyilanxin.anyicloud.corecommon.constant.impl.CommonNotHaveType;
import com.anyilanxin.anyicloud.corecommon.model.system.ClientAndResourceAuthModel;
import com.anyilanxin.anyicloud.corecommon.model.system.UserAndResourceAuthModel;
import com.anyilanxin.anyicloud.oauth2common.authinfo.SkillFullClientDetails;
import com.anyilanxin.anyicloud.oauth2common.authinfo.SkillFullGrantedAuthority;
import com.anyilanxin.anyicloud.oauth2common.authinfo.SkillFullUserDetails;
import com.anyilanxin.anyicloud.oauth2common.mapstruct.ClientDetailsCopyMap;
import com.anyilanxin.anyicloud.oauth2common.mapstruct.UserDetailsCopyMap;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import javax.annotation.PostConstruct;
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

/**
 * @author zxh
 * @date 2022-03-03 14:19
 * @since 1.0.0
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
     * @author zxh
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
                SimpleGrantedAuthority authority = new SimpleGrantedAuthority(AuthConstant.DEFAULT_ROLE_PREFIX + v.getRoleCode());
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
     * @author zxh
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
                SimpleGrantedAuthority authority = new SimpleGrantedAuthority(AuthConstant.DEFAULT_ROLE_PREFIX + v.getRoleCode());
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
     * @author zxh
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
     * @author zxh
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
     * @author zxh
     * @date 2022-03-11 01:15
     */
    public static void refreshUserOauth(UserAndResourceAuthModel model, String token) {
    }


    /**
     * 根据userId刷新用户信息
     *
     * @param model ${@link UserAndResourceAuthModel}
     * @author zxh
     * @date 2022-03-11 01:15
     */
    public static void refreshUserOauth(String userId, UserAndResourceAuthModel model) {
    }
}
