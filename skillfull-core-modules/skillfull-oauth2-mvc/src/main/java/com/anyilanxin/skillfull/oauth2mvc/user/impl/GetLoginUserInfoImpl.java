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
 *   1.请不要删除和修改根目录下的LICENSE文件。
 *   2.请不要删除和修改 AnYi Cloud 源码头部的版权声明。
 *   3.请保留源码和相关描述文件的项目出处，作者声明等。
 *   4.分发源码时候，请注明软件出处 https://github.com/anyilanxin/anyi-cloud
 *   5.在修改包名，模块名称，项目代码等时，请注明软件出处 https://github.com/anyilanxin/anyi-cloud
 *   6.若您的项目无法满足以上几点，可申请商业授权
 */

package com.anyilanxin.skillfull.oauth2mvc.user.impl;

import cn.hutool.core.collection.CollUtil;
import com.anyilanxin.skillfull.corecommon.model.auth.RoleInfo;
import com.anyilanxin.skillfull.corecommon.model.auth.UserIdentity;
import com.anyilanxin.skillfull.corecommon.model.auth.UserInfo;
import com.anyilanxin.skillfull.oauth2common.authinfo.SkillFullUserDetails;
import com.anyilanxin.skillfull.oauth2common.mapstruct.OauthUserAndUserDetailsCopyMap;
import com.anyilanxin.skillfull.oauth2mvc.user.IGetLoginUserInfo;
import java.util.Collections;
import java.util.Objects;
import java.util.Set;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.exceptions.UnauthorizedUserException;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenStore;

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

  public GetLoginUserInfoImpl(
      final TokenStore tokenStore, final OauthUserAndUserDetailsCopyMap detailsCopyMap) {
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
