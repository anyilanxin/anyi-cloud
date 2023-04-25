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
package com.anyilanxin.skillfull.oauth2common.authinfo;

import static com.anyilanxin.skillfull.corecommon.constant.CommonCoreConstant.TIME_ZONE_GMT8;

import com.alibaba.fastjson.annotation.JSONField;
import com.anyilanxin.skillfull.corecommon.model.auth.RoleInfo;
import com.anyilanxin.skillfull.corecommon.model.auth.UserAgent;
import com.anyilanxin.skillfull.corecommon.model.auth.UserIdentity;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDate;
import java.util.Collection;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/**
* 自定义用户信息
*
* @author zxiaozhou
* @date 2022-02-14 17:16
* @since JDK1.8
*/
@Setter
@Getter
@ToString
public class SkillFullUserDetails implements UserDetails {
    @JsonIgnore
    @Schema(name = "password", title = "用户密码", hidden = true)
    @JSONField(serialize = false)
    private String password;

    @JsonIgnore
    @Schema(name = "salt", title = "密码盐", hidden = true)
    @JSONField(serialize = false)
    private String salt;

    @Schema(name = "userName", title = "用户id")
    private String userId;

    @Schema(name = "userName", title = "用户名")
    private String userName;

    @Schema(name = "nickName", title = "用户昵称")
    private String nickName;

    @Schema(name = "realName", title = "真实姓名")
    private String realName;

    @Schema(name = "identityStatus", title = "实名状态:0-待提交,1-审核中,2-未通过(审核失败),3-通过(审核成功),默认0")
    private int identityStatus;

    @Schema(name = "identity", title = "实名认证信息")
    private UserIdentity identity;

    @Schema(name = "loginFailErrorNum", title = "连续登录错误次数")
    private Integer loginFailErrorNum;

    @Schema(name = "avatar", title = "头像")
    private String avatar;

    @Schema(name = "birthday", title = "生日", type = "string", example = "2020-12-21")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = TIME_ZONE_GMT8)
    private LocalDate birthday;

    @Schema(name = "shortProfile", title = "个人简介")
    private String shortProfile;

    @Schema(name = "sex", title = "性别:0-默认未知,1-男,2-女,默认0")
    private Integer sex;

    @Schema(name = "email", title = "邮件")
    private String email;

    @Schema(name = "phone", title = "电话号码")
    private String phone;

    @Schema(name = "currentOrgId", title = "当前机构id")
    private String currentOrgId;

    @Schema(name = "superAdmin", title = "是否为超级管理员")
    private boolean superAdmin;

    @Schema(name = "currentOrgName", title = "当前机构名称")
    private String currentOrgName;

    @Schema(name = "currentOrgCode", title = "当前机构code")
    private String currentOrgCode;

    @Schema(name = "currentAreaCode", title = "当前区域编码")
    private String currentAreaCode;

    @Schema(name = "currentTenantId", title = "当前租户id")
    private String currentTenantId;

    @Schema(name = "roleInfos", title = "当前角色信息")
    private Set<RoleInfo> roleInfos;

    @Schema(name = "roleCodes", title = "当前角色编码信息")
    private Set<String> roleCodes;

    @Schema(name = "roleIds", title = "当前角色id信息")
    private Set<String> roleIds;

    @Schema(name = "agentInfos", title = "代理人信息")
    private Set<UserAgent> agentInfos;

    @Schema(name = "extendInfo", title = "扩展信息")
    private Object extendInfo;

    @Schema(name = "authorities", title = "用户权限", hidden = true)
    private Set<? extends GrantedAuthority> authorities;

    private boolean accountNonExpired;

    private boolean accountNonLocked;

    private boolean credentialsNonExpired;

    private boolean enabled;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.userName;
    }

    @Override
    public boolean isAccountNonExpired() {
        return this.accountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return this.accountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return this.credentialsNonExpired;
    }

    @Override
    public boolean isEnabled() {
        return this.enabled;
    }
}
