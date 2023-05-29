

package com.anyilanxin.anyicloud.system.modules.rbac.service.dto;

import static com.anyilanxin.anyicloud.corecommon.constant.CommonCoreConstant.TIME_ZONE_GMT8;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.Set;

import lombok.*;
import lombok.experimental.SuperBuilder;

/**
 * 用户表查询Response
 *
 * @author zxh
 * @copyright zhouxuanhong（https://anyilanxin.com）
 * @date 2022-05-02 16:12:21
 * @since 1.0.0
 */
@Getter
@Setter
@ToString
@EqualsAndHashCode
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@Schema
public class RbacUserDto implements Serializable {
    private static final long serialVersionUID = 228406723692159521L;

    @Schema(name = "userId", title = "用户id")
    private String userId;

    @Schema(name = "userName", title = "用户名")
    private String userName;

    @Schema(name = "openId", title = "开放id")
    private String openId;

    @Schema(name = "nickName", title = "用户昵称")
    private String nickName;

    @Schema(name = "realName", title = "真实姓名")
    private String realName;

    @Schema(name = "shortProfile", title = "个人简介")
    private String shortProfile;

    @Schema(name = "avatar", title = "头像")
    private String avatar;

    @Schema(name = "birthday", title = "生日", type = "string", example = "2020-11-12")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = TIME_ZONE_GMT8)
    private LocalDate birthday;

    @Schema(name = "sex", title = "性别:0-默认未知,1-男,2-女,默认0")
    private Integer sex;

    @Schema(name = "email", title = "邮件")
    private String email;

    @Schema(name = "isInitialPassword", title = "是否初始密码:0-不是,1-是,默认1")
    private Integer isInitialPassword;

    @Schema(name = "phone", title = "电话号码")
    private String phone;

    @Schema(name = "registerSource", title = "来源")
    private String registerSource;

    @Schema(name = "userStatus", title = "状态:0-未激活,1-正常,2-冻结,默认1")
    private Integer userStatus;

    @Schema(name = "workNo", title = "工号，唯一键")
    private String workNo;

    @Schema(name = "telephone", title = "座机号")
    private String telephone;

    @Schema(name = "enableDelete", title = "是否可删除:0-不可删除,1-可删除。默认1(用户系统内置数据不可删除)")
    private Integer enableDelete;

    @Schema(name = "additionalInformation", title = "扩展信息,json object")
    private Map<String, Object> additionalInformation;

    @Schema(name = "positionIds", title = "职位,org_id array")
    private Set<String> positionIds;

    @Schema(name = "managerOrgIds", title = "负责机构,org_id array")
    private Set<String> managerOrgIds;

    @Schema(name = "loginFailErrorNum", title = "连续登录错误次数")
    private Integer loginFailErrorNum;

    @Schema(name = "orgId", title = "机构id(查询由前端传入)")
    private String orgId;

    @Schema(name = "currentOrgId", title = "当前登录部门id")
    private String currentOrgId;

    @Schema(name = "currentLoginIp", title = "最后登录IP")
    private String currentLoginIp;

    @Schema(name = "currentLoginDate", title = "最后登录时间", type = "string", example = "2020-11-12 11:23:59")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = TIME_ZONE_GMT8)
    private LocalDateTime currentLoginDate;

    @Schema(name = "uniqueHelp", title = "唯一索引帮助字段,默认1，如果删除该值为主键")
    private String uniqueHelp;

    @Schema(name = "remark", title = "备注")
    private String remark;

    @Schema(name = "createUserId", title = "创建用户id")
    private String createUserId;

    @Schema(name = "createUserName", title = "创建用户姓名")
    private String createUserName;

    @Schema(name = "createTime", title = "创建时间", type = "string", example = "2020-11-12 11:23:59")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = TIME_ZONE_GMT8)
    private LocalDateTime createTime;

    @Schema(name = "updateUserName", title = "更新用户姓名")
    private String updateUserName;

    @Schema(name = "updateTime", title = "更新时间", type = "string", example = "2020-11-12 11:23:59")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = TIME_ZONE_GMT8)
    private LocalDateTime updateTime;

    @Schema(name = "orgRoleIds", title = "部门角色ids")
    private Set<String> orgRoleIds;

    @Schema(name = "orgRoleInfos", title = "部门角色信息")
    private Set<RbacRoleSimpleDto> orgRoleInfos;

    @Schema(name = "roleIds", title = "用户角色ids")
    private Set<String> roleIds;

    @Schema(name = "roleInfos", title = "用户角色信息")
    private Set<RbacRoleSimpleDto> roleInfos;
}
