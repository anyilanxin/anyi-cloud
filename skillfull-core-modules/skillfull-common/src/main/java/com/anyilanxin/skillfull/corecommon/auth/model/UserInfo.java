// +----------------------------------------------------------------------
// | SkillFull快速开发平台 [ SkillFull ]
// +----------------------------------------------------------------------
// | 版权所有 2020~2021 zxiaozhou
// +----------------------------------------------------------------------
// | 官方网站: https://www.divisu.com
// +----------------------------------------------------------------------
// | 作者: zxiaozhou <z7630853@163.com>
// +----------------------------------------------------------------------
package com.anyilanxin.skillfull.corecommon.auth.model;

import com.anyilanxin.skillfull.corecommon.constant.CommonCoreConstant;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Set;

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
@SuperBuilder(toBuilder = true)
@EqualsAndHashCode
@NoArgsConstructor
public class UserInfo implements Serializable {
    private static final long serialVersionUID = 1654336717080L;

    @Schema(name = "userId", title = "用户id")
    private String userId;

    @Schema(name = "superAdmin", title = "是否为超级管理员")
    private boolean superAdmin;

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
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = CommonCoreConstant.TIME_ZONE_GMT8)
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

    @Schema(name = "currentOrgName", title = "当前机构名称")
    private String currentOrgName;

    @Schema(name = "currentOrgCode", title = "当前机构code")
    private String currentOrgCode;

    @Schema(name = "currentAreaCode", title = "当前区域编码")
    private String currentAreaCode;

    @Schema(name = "currentAreaName", title = "当前区域名称")
    private String currentAreaName;

    @Schema(name = "orgInfo", title = "机构基本信息")
    private OrgSimpleInfo orgInfo;

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
}
