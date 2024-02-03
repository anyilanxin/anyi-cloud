/*
 * Copyright (c) 2023-present ZHOUXUANHONG(安一老厨)<anyilanxin@aliyun.com>
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
 *   1.请不要删除和修改根目录下的LICENSE.txt文件；
 *   2.请不要删除和修改 AnYi Cloud 源码头部的版权声明；
 *   3.请保留源码和相关描述文件的项目出处，作者声明等；
 *   4.分发源码时候，请注明软件出处 https://github.com/anyilanxin/anyi-cloud；
 *   5.在修改包名，模块名称，项目代码等时，请注明软件出处 https://github.com/anyilanxin/anyi-cloud；
 *   6.本软件不允许在国家法律规定范围外使用，如出现违法行为原作者本人不承担任何法律风险；
 *   7.进行商用时，不得基于AnYi Cloud的基础，修改包装而成一个与AnYi Cloud EE、AnYi Zeebe EE、AnYi Standalone EE功能类似的程序，进行销售或发布，参与同类软件产品市场的竞争；
 *   8.本软件使用的第三方依赖皆为开源软件，如需要修改第三方源码请遵循第三方源码附带开源协议；
 *   9.本软件流程部分请遵循camunda开源协议：
 *     https://docs.camunda.org/manual/latest/introduction/third-party-libraries
 *     https://github.com/camunda/camunda-bpm-platform/blob/master/LICENSE
 *   10.若您的项目无法满足以上几点，可申请商业授权。
 */

package com.anyilanxin.anyicloud.oauth2common.utils;

import com.anyilanxin.anyicloud.corecommon.constant.AuthConstant;
import com.anyilanxin.anyicloud.corecommon.model.system.AnYiUserAndResourceAuthModel;
import com.anyilanxin.anyicloud.oauth2common.authinfo.AnYiGrantedAuthority;
import com.anyilanxin.anyicloud.oauth2common.authinfo.AnYiUserDetails;
import com.anyilanxin.anyicloud.oauth2common.mapstruct.ClientDetailsCopyMap;
import com.anyilanxin.anyicloud.oauth2common.mapstruct.UserDetailsCopyMap;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.dromara.hutool.core.collection.CollUtil;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

/**
 * @author zxh
 * @date 2022-03-03 14:19
 * @since 1.0.0
 */
@Component
@RequiredArgsConstructor
public class Oauth2CommonUtils {
    private static Oauth2CommonUtils utils;
    private final UserDetailsCopyMap userDetailsCopyMap;
    private final ClientDetailsCopyMap clientDetailsCopyMap;

    @PostConstruct
    void init() {
        utils = this;
    }
//
//
//    /**
//     * resourceAction 转换为security Attribute
//     *
//     * @param userAll             ${@link Boolean} 是否用全url,网关时需要使用下游全url
//     * @param resourcePermissions ${@link List< ResourceActionModel >}
//     * @return LinkedHashMap<RequestMatcher, Collection < ConfigAttribute>> ${@link LinkedHashMap<RequestMatcher, Collection<ConfigAttribute>>}
//     * @author zxh
//     * @date 2022-03-03 14:23
//     */
//    public static LinkedHashMap<RequestMatcher, Collection<ConfigAttribute>> resourceActionToAttribute(List<ResourceActionModel> resourcePermissions, boolean userAll) {
//        if (CollUtil.isEmpty(resourcePermissions)) {
//            return new LinkedHashMap<>();
//        }
//        LinkedHashMap<RequestMatcher, Collection<ConfigAttribute>> authConfigAttributeMap = new LinkedHashMap<>(resourcePermissions.size());
//        resourcePermissions.forEach(v -> {
//            String requestMethodInfo = v.getRequestMethod();
//            String apiUri = v.getApiUri();
//            String apiUriAll = v.getApiUriAll();
//            String permissionExpress = v.getPermissionExpress();
//            if (v.getAuthType() != AuthType.GATEWAY.getType()) {
//                if (StringUtils.isNotBlank(requestMethodInfo)) {
//                    String[] requestMethods = requestMethodInfo.split("[,，]");
//                    for (String requestMethod : requestMethods) {
//                        HttpMethod httpMethod = HttpMethod.valueOf(requestMethod.toUpperCase());
//                        AntPathRequestMatcher matcher = new AntPathRequestMatcher(userAll ? apiUriAll : apiUri, httpMethod.name());
//                        List<ConfigAttribute> configAttributes = SecurityConfig.createList(permissionExpress);
//                        authConfigAttributeMap.put(matcher, configAttributes);
//                    }
//                } else {
//                    AntPathRequestMatcher matcher = new AntPathRequestMatcher(userAll ? apiUriAll : apiUri);
//                    List<ConfigAttribute> configAttributes = SecurityConfig.createList(permissionExpress);
//                    authConfigAttributeMap.put(matcher, configAttributes);
//                }
//            }
//        });
//        return authConfigAttributeMap;
//    }
//
//

    /**
     * SystemUserAndActionModel转换为CustomUserDetails
     *
     * @param model ${@link AnYiUserAndResourceAuthModel}
     * @return CustomUserDetails ${@link AnYiUserDetails}
     * @author zxh
     * @date 2022-03-04 01:51
     */
    public static AnYiUserDetails toUserDetails(AnYiUserAndResourceAuthModel model) {
        if (Objects.isNull(model)) {
            return null;
        }
        AnYiUserDetails userDetails = utils.userDetailsCopyMap.aToB(model);
        Integer userStatus = model.getUserStatus();
        userDetails.setUserId(model.getUserId());
        userDetails.setAccountNonLocked(userStatus != 2);
        userDetails.setAccountNonExpired(userStatus != 0);
        userDetails.setEnabled(userStatus == 1);
        userDetails.setCredentialsNonExpired(true);
        List<GrantedAuthority> authorities = new ArrayList<>();
        if (CollUtil.isNotEmpty(model.getActions())) {
            model.getActions().forEach((k, v) -> v.forEach(sv -> {
                AnYiGrantedAuthority authority = new AnYiGrantedAuthority(sv, k, new HashMap<>());
                authorities.add(authority);
            }));
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


//    /**
//     * ManageClientDetailsModel转换为CustomClientDetails
//     *
//     * @param model ${@link ClientAndResourceAuthModel}
//     * @return CustomClientDetails ${@link AnYiClientDetails}
//     * @author zxh
//     * @date 2022-03-04 01:51
//     */
//    public static AnYiClientDetails toClientDetails(ClientAndResourceAuthModel model) {
//        AnYiClientDetails customClientDetails = utils.clientDetailsCopyMap.aToB(model);
//        customClientDetails.setScoped(model.getHavaScoped() == CommonNotHaveType.HAVE.getType());
//        if (CollUtil.isNotEmpty(model.getScopes())) {
//            customClientDetails.setScope(new HashSet<>(model.getScopes()));
//        }
//        customClientDetails.setClientSecret(model.getClientSecurity());
//        if (StringUtils.isNotBlank(model.getWebRegisteredRedirectUri())) {
//            customClientDetails.setRegisteredRedirectUri(Set.of(model.getWebRegisteredRedirectUri()));
//        }
//        Set<GrantedAuthority> authorities = new HashSet<>();
//        if (CollUtil.isNotEmpty(model.getActions())) {
//            model.getActions().forEach((k, v) -> v.forEach(sv -> {
//                AnYiGrantedAuthority authority = new AnYiGrantedAuthority(sv, k);
//                authorities.add(authority);
//            }));
//        }
//        if (CollUtil.isNotEmpty(model.getRoleInfos())) {
//            Set<String> roleCodes = new HashSet<>(model.getRoleInfos().size());
//            Set<String> roleIds = new HashSet<>(model.getRoleInfos().size());
//            model.getRoleInfos().forEach(v -> {
//                roleCodes.add(v.getRoleCode());
//                roleIds.add(v.getRoleId());
//                SimpleGrantedAuthority authority = new SimpleGrantedAuthority(AuthConstant.DEFAULT_ROLE_PREFIX + v.getRoleCode());
//                authorities.add(authority);
//            });
//            customClientDetails.setRoleCodes(roleCodes);
//            customClientDetails.setRoleIds(roleIds);
//        }
//        if (CollUtil.isNotEmpty(authorities)) {
//            customClientDetails.setAuthorities(authorities);
//        }
//        return customClientDetails;
//    }

    //
//
//    /**
//     * 根据token刷新用户信息
//     *
//     * @param model ${@link AnYiUserAndResourceAuthModel}
//     * @author zxh
//     * @date 2022-03-11 01:15
//     */
//    public static void refreshUserOauth(AnYiUserAndResourceAuthModel model, String token) {
//    }
//
//
//    /**
//     * 根据userId刷新用户信息
//     *
//     * @param model ${@link AnYiUserAndResourceAuthModel}
//     * @author zxh
//     * @date 2022-03-11 01:15
//     */
//    public static void refreshUserOauth(String userId, AnYiUserAndResourceAuthModel model) {
//    }
//
//
//    /**
//     * 判断是否为超级管理员
//     *
//     * @param authentication
//     * @author zxh
//     * @date 2022-09-02 15:02
//     */
    public static boolean isSuperAdmin(Authentication authentication) {
//        if (authentication instanceof OAuth2AccessTokenAuthenticationToken) {
//            OAuth2AccessTokenAuthenticationToken oAuth2Authentication = (OAuth2AccessTokenAuthenticationToken) authentication;
//            Object principal = oAuth2Authentication.getPrincipal();
//            if (principal instanceof AnYiUserDetails) {
//                AnYiUserDetails userDetails = (AnYiUserDetails) principal;
//                return userDetails.isSuperAdmin();
//            }
//        }
        return false;
    }
}
