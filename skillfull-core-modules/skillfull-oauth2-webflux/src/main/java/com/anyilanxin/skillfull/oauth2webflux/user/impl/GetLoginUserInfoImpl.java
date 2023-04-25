/**
* Copyright (c) 2021-2022 ZHOUXUANHONG(安一老厨)<anyilanxin@aliyun.com>
*
* <p>AnYi Cloud Licensed under the Apache License, Version 2.0 (the "License"); you may not use
* this file except in compliance with the License. You may obtain a copy of the License at
*
* <p>http://www.apache.org/licenses/LICENSE-2.0
*
* <p>Unless required by applicable law or agreed to in writing, software distributed under the
* License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
* express or implied. See the License for the specific language governing permissions and
* limitations under the License.
*
* <p>AnYi Cloud 采用APACHE LICENSE 2.0开源协议，您在使用过程中，需要注意以下几点：
*
* <p>1.请不要删除和修改根目录下的LICENSE文件。 2.请不要删除和修改 AnYi Cloud 源码头部的版权声明。 3.请保留源码和相关描述文件的项目出处，作者声明等。
* 4.分发源码时候，请注明软件出处 https://github.com/anyilanxin/anyi-cloud 5.在修改包名，模块名称，项目代码等时，请注明软件出处
* https://github.com/anyilanxin/anyi-cloud 6.若您的项目无法满足以上几点，可申请商业授权
*/
package com.anyilanxin.skillfull.oauth2webflux.user.impl;

import com.anyilanxin.skillfull.corecommon.model.auth.RoleInfo;
import com.anyilanxin.skillfull.corecommon.model.auth.UserIdentity;
import com.anyilanxin.skillfull.corecommon.model.auth.UserInfo;
import com.anyilanxin.skillfull.oauth2common.mapstruct.OauthUserAndUserDetailsCopyMap;
import com.anyilanxin.skillfull.oauth2webflux.user.IGetLoginUserInfo;
import java.util.Set;
import org.springframework.security.oauth2.provider.token.TokenStore;
import reactor.core.publisher.Mono;

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
