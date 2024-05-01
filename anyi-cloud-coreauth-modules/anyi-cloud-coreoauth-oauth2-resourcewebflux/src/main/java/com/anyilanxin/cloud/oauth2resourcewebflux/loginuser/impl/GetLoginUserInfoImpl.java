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

package com.anyilanxin.cloud.oauth2resourcewebflux.loginuser.impl;

import com.anyilanxin.cloud.corecommon.constant.AnYiResultStatus;
import com.anyilanxin.cloud.corecommon.exception.AnYiResponseException;
import com.anyilanxin.cloud.corecommon.model.auth.AnYiUserInfo;
import com.anyilanxin.cloud.corecommon.model.auth.RoleInfo;
import com.anyilanxin.cloud.corecommon.model.auth.UserIdentity;
import com.anyilanxin.cloud.corewebflux.loginuser.IGetLoginUserInfo;
import com.anyilanxin.cloud.oauth2common.authinfo.AnYiUserDetails;
import com.anyilanxin.cloud.oauth2common.mapstruct.OauthUserAndUserDetailsCopyMap;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import reactor.core.publisher.Mono;

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
    public Mono<AnYiUserInfo> getUserInfo() {
        return ReactiveSecurityContextHolder.getContext().handle((v, sink) -> {
            if (Objects.nonNull(v.getAuthentication())) {
                Authentication principal = v.getAuthentication();
                Object userPrincipal = principal.getPrincipal();
                if (userPrincipal instanceof AnYiUserDetails) {
                    sink.next(detailsCopyMap.aToB((AnYiUserDetails) userPrincipal));
                    return;
                }
            }
            sink.error(new AnYiResponseException(AnYiResultStatus.ERROR, "获取用户信息失败"));
        });
    }


    @Override
    public Mono<Integer> getIdentityStatus() {
        return getUserInfo().map(AnYiUserInfo::getIdentityStatus);
    }


    @Override
    public Mono<UserIdentity> getIdentity() {
        return getUserInfo().map(AnYiUserInfo::getIdentity);
    }


    @Override
    public Mono<Boolean> superRole() {
        return getUserInfo().map(AnYiUserInfo::isSuperAdmin);
    }


    @Override
    public Mono<Set<RoleInfo>> getRoleInfos() {
        return getUserInfo().map(AnYiUserInfo::getRoleInfos);
    }


    @Override
    public Mono<Set<String>> getRoleCodes() {
        return getUserInfo().map(AnYiUserInfo::getRoleCodes);
    }


    @Override
    public Mono<Set<String>> getRoleIds() {
        return getUserInfo().map(AnYiUserInfo::getRoleIds);
    }


    @Override
    public Mono<String> getUserId() {
        return getUserInfo().map(AnYiUserInfo::getUserId);
    }


    @Override
    public Mono<String> getUserName() {
        return getUserInfo().map(AnYiUserInfo::getUserName);
    }


    @Override
    public Mono<String> getNickName() {
        return getUserInfo().map(AnYiUserInfo::getNickName);
    }


    @Override
    public Mono<String> getRealName() {
        return getUserInfo().map(AnYiUserInfo::getRealName);
    }


    @Override
    public Mono<String> getCurrentOrgId() {
        return getUserInfo().map(AnYiUserInfo::getCurrentOrgId);
    }


    @Override
    public Mono<String> getCurrentOrgCode() {
        return getUserInfo().map(AnYiUserInfo::getCurrentOrgCode);
    }


    @Override
    public Mono<String> getCurrentAreaCode() {
        return getUserInfo().map(AnYiUserInfo::getCurrentAreaCode);
    }


    @Override
    public Mono<String> getCurrentTenantId() {
        return getUserInfo().map(AnYiUserInfo::getCurrentTenantId);
    }


    @Override
    public Mono<String> getPhone() {
        return getUserInfo().map(AnYiUserInfo::getPhone);
    }
}
