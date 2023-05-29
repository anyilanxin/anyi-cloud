

package com.anyilanxin.anyicloud.oauth2common.authinfo;

import static com.anyilanxin.anyicloud.corecommon.constant.CommonCoreConstant.TIME_ZONE_GMT8;

import com.alibaba.fastjson.annotation.JSONField;
import com.anyilanxin.anyicloud.corecommon.model.auth.RoleInfo;
import com.anyilanxin.anyicloud.corecommon.model.auth.UserAgent;
import com.anyilanxin.anyicloud.corecommon.model.auth.UserIdentity;
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
 * @author zxh
 * @date 2022-02-14 17:16
 * @since 1.0.0
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
