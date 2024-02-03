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

package com.anyilanxin.anyicloud.oauth2common.store.jackson2;

import com.anyilanxin.anyicloud.corecommon.model.auth.RoleInfo;
import com.anyilanxin.anyicloud.corecommon.model.auth.UserAgent;
import com.anyilanxin.anyicloud.corecommon.model.auth.UserIdentity;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import org.springframework.security.core.GrantedAuthority;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS)
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY, getterVisibility = JsonAutoDetect.Visibility.NONE,
        isGetterVisibility = JsonAutoDetect.Visibility.NONE)
abstract class AnYiUserDetailsMixin {

    @JsonCreator
    AnYiUserDetailsMixin(@JsonProperty("userId") String userId,
                         @JsonProperty("userName") String userName,
                         @JsonProperty("nickName") String nickName,
                         @JsonProperty("realName") String realName,
                         @JsonProperty("identityStatus") Integer identityStatus,
                         @JsonProperty("identity") UserIdentity identity,
                         @JsonProperty("loginFailErrorNum") Integer loginFailErrorNum,
                         @JsonProperty("avatar") String avatar,
                         @JsonProperty("birthday") LocalDate birthday,
                         @JsonProperty("shortProfile") String shortProfile,
                         @JsonProperty("sex") Integer sex,
                         @JsonProperty("email") String email,
                         @JsonProperty("phone") String phone,
                         @JsonProperty("currentOrgId") String currentOrgId,
                         @JsonProperty("superAdmin") boolean superAdmin,
                         @JsonProperty("currentOrgName") String currentOrgName,
                         @JsonProperty("currentOrgCode") String currentOrgCode,
                         @JsonProperty("currentAreaCode") String currentAreaCode,
                         @JsonProperty("currentTenantId") String currentTenantId,
                         @JsonProperty("roleInfos") List<RoleInfo> roleInfos,
                         @JsonProperty("roleCodes") List<String> roleCodes,
                         @JsonProperty("roleIds") List<String> roleIds,
                         @JsonProperty("agentInfos") List<UserAgent> agentInfos,
                         @JsonProperty("extendInfo") Map<String, Object> extendInfo,
                         @JsonProperty("authorities") List<? extends GrantedAuthority> authorities,
                         @JsonProperty("accountNonExpired") boolean accountNonExpired,
                         @JsonProperty("accountNonLocked") boolean accountNonLocked,
                         @JsonProperty("credentialsNonExpired") boolean credentialsNonExpired,
                         @JsonProperty("enabled") boolean enabled) {

    }

}
