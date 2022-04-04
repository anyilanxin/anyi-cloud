// +----------------------------------------------------------------------
// | SkillFull快速开发平台 [ SkillFull ]
// +----------------------------------------------------------------------
// | 版权所有 2020~2021 zxiaozhou
// +----------------------------------------------------------------------
// | 官方网站: https://www.divisu.com
// +----------------------------------------------------------------------
// | 作者: zxiaozhou <z7630853@163.com>
// +----------------------------------------------------------------------
package indi.zxiaozhou.skillfull.auth.modules.rbac.service.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.Accessors;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Set;

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
public class RbacRoleDto implements Serializable {
    private static final long serialVersionUID = -17389117269525927L;

    @Schema(name = "roleId", title = "角色id")
    private String roleId;

    @Schema(name = "roleName", title = "角色名称")
    private String roleName;

    @Schema(name = "roleCode", title = "角色编码")
    private String roleCode;

    @Schema(name = "roleStatus", title = "角色状态:0-禁用,1-启用,默认0")
    private Integer roleStatus;

    @Schema(name = "enableDelete", title = "是否可删除:0-不可删除,1-可删除。默认1(用户系统内置数据不可删除)")
    private Integer enableDelete;

    @Schema(name = "autoBind", title = "绑定方式:0-手动,1-自动。默认0")
    private Integer autoBind;

    @Schema(name = "roleSysCode", title = "角色系统编码(系统自动创建)")
    private String roleSysCode;

    @Schema(name = "remark", title = "备注")
    private String remark;

    @Schema(name = "createUserName", title = "创建用户姓名")
    private String createUserName;

    @Schema(name = "parentRoleId", title = "上级角色id", hidden = true)
    private String parentRoleId;

    @Schema(name = "createTime", title = "创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime createTime;

    @Schema(name = "permissionInfos", title = "按钮绑定权限")
    private Set<RbacRolePermissionInfoDto> permissionInfos;

    @Schema(name = "permissionIds", title = "所有关联的角色权限信息")
    private Set<String> permissionIds;


    @Getter
    @Setter
    @ToString
    @Accessors(chain = true)
    @SuperBuilder(toBuilder = true)
    @AllArgsConstructor
    @NoArgsConstructor
    @Schema
    public static class RbacRolePermissionInfoDto implements Serializable {
        private static final long serialVersionUID = -63399996952662860L;

        @Schema(name = "roleCode", title = "角色编码")
        private String roleCode;

        @Schema(name = "roleSysCode", title = "角色系统编码(系统自动创建)")
        private String roleSysCode;

        @Schema(name = "roleId", title = "角色id")
        private String roleId;

        @Schema(name = "permissionId", title = "权限id")
        private String permissionId;

        @Schema(name = "title", title = "菜单名称")
        private String title;

        @Schema(name = "actionSet", title = "菜单按钮权限")
        private Set<Action> actionSet;

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (!(o instanceof RbacRolePermissionInfoDto)) {
                return false;
            }
            RbacRolePermissionInfoDto that = (RbacRolePermissionInfoDto) o;
            return Objects.equals(getPermissionId(), that.getPermissionId());
        }

        @Override
        public int hashCode() {
            return Objects.hash(getPermissionId());
        }
    }

    @Getter
    @Setter
    @ToString
    @Accessors(chain = true)
    @SuperBuilder(toBuilder = true)
    @AllArgsConstructor
    @NoArgsConstructor
    @EqualsAndHashCode
    @Schema
    public static class Action implements Serializable {
        private static final long serialVersionUID = 6588630894991792617L;

        @Schema(name = "permissionId", title = "权限id")
        private String permissionId;

        @Schema(name = "actions", title = "按钮权限编码，例如：“sys:schedule:list”,多个逗号隔开")
        private String actions;

        @Schema(name = "actionType", title = "按钮权限类型:1-前端型,2-后端型,3-前后端型")
        private Integer actionType;

        @Schema(name = "title", title = "菜单名称")
        private String title;

        @Schema(name = "parentId", title = "父id")
        private String parentId;
    }

}