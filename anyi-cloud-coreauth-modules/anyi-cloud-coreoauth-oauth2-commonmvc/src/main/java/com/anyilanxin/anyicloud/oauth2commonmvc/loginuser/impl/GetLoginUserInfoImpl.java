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

package com.anyilanxin.anyicloud.oauth2commonmvc.loginuser.impl;

import com.anyilanxin.anyicloud.corecommon.constant.AnYiResultStatus;
import com.anyilanxin.anyicloud.corecommon.exception.AnYiResponseException;
import com.anyilanxin.anyicloud.corecommon.model.auth.AnYiUserInfo;
import com.anyilanxin.anyicloud.corecommon.model.auth.RoleInfo;
import com.anyilanxin.anyicloud.corecommon.model.auth.UserIdentity;
import com.anyilanxin.anyicloud.coremvc.loginuser.IGetLoginUserInfo;
import com.anyilanxin.anyicloud.oauth2common.authinfo.AnYiUserDetails;
import com.anyilanxin.anyicloud.oauth2common.mapstruct.OauthUserAndUserDetailsCopyMap;
import org.dromara.hutool.core.collection.CollUtil;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Collections;
import java.util.Objects;
import java.util.Set;

/**
 * 获取用户信息
 *
 * @author zxh
 * @date 2022-04-09 10:02
 * @since 1.0.0
 */
public class GetLoginUserInfoImpl implements IGetLoginUserInfo {
    private final OauthUserAndUserDetailsCopyMap detailsCopyMap;

    public GetLoginUserInfoImpl(final OauthUserAndUserDetailsCopyMap detailsCopyMap) {
        this.detailsCopyMap = detailsCopyMap;
    }


    @Override
    public AnYiUserInfo getUserInfo(String token) {
        return new AnYiUserInfo();
    }


    @Override
    public AnYiUserInfo getUserInfo() {
        SecurityContext context = SecurityContextHolder.getContext();
        if (Objects.nonNull(context.getAuthentication())) {
            Authentication principal = context.getAuthentication();
            Object userPrincipal = principal.getPrincipal();
            if (userPrincipal instanceof AnYiUserDetails userInfo) {
                return detailsCopyMap.aToB(userInfo);
            }
        }
        throw new AnYiResponseException(AnYiResultStatus.ERROR, "获取用户信息失败");
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
