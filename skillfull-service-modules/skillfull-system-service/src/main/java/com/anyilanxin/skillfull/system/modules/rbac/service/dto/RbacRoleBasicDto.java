package com.anyilanxin.skillfull.system.modules.rbac.service.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.Accessors;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Set;

import static com.anyilanxin.skillfull.corecommon.constant.CommonCoreConstant.TIME_ZONE_GMT8;

/**
 * 角色表查询Response
 *
 * @author zxiaozhou
 * @date 2020-10-08 13:44:02
 * @since JDK11
 */
@Getter
@Setter
@ToString
@EqualsAndHashCode
@Accessors(chain = true)
@SuperBuilder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@Schema
public class RbacRoleBasicDto implements Serializable {
    private static final long serialVersionUID = -17389117269525927L;

    @Schema(name = "roleId", title = "角色id")
    private String roleId;

    @Schema(name = "roleType", title = "角色类型:0-特殊隐藏类,1-正常类,2-业务角色,默认1,0-主要用于设置某些表默认角色,具体与RoleType一致")
    private Integer roleType;

    @Schema(name = "roleName", title = "角色名称")
    private String roleName;

    @Schema(name = "roleSysCode", title = "角色系统编码(系统自动创建)")
    private String roleSysCode;

    @Schema(name = "superRole", title = "是否为超级管理员角色")
    private boolean superRole;

    @Schema(name = "dataAuthType", title = "数据权限类型：1-全部,2-机构,3-机构及以下,4-机构自定义,5-区域,6-区域及以下,7-区域自定义,8-仅自己")
    private Integer dataAuthType;

    @Schema(name = "customDataAuthData", title = "自定义类数据权限数据")
    private Set<String> customDataAuthData;

    @Schema(name = "roleCode", title = "角色编码")
    private String roleCode;

    @Schema(name = "parentRoleId", title = "上级角色id")
    private String parentRoleId;

    @Schema(name = "enableDelete", title = "是否可删除:0-不可删除,1-可删除。默认1(用户系统内置数据不可删除)")
    private Integer enableDelete;

    @Schema(name = "autoBind", title = "绑定方式:0-手动,1-自动。默认0")
    private Integer autoBind;

    @Schema(name = "roleStatus", title = "角色状态:0-禁用,1-启用,默认0")
    private Integer roleStatus;

    @Schema(name = "remark", title = "备注")
    private String remark;

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

    @Schema(name = "roleBusinessId", title = "所属业务id")
    private String roleBusinessId;
}
