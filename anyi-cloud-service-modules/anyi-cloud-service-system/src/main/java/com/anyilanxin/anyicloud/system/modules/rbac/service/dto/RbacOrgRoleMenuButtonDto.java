

package com.anyilanxin.anyicloud.system.modules.rbac.service.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.io.Serializable;
import java.util.Objects;
import java.util.Set;

import lombok.*;
import lombok.experimental.Accessors;
import lombok.experimental.SuperBuilder;

/**
 * 角色菜单按钮
 *
 * @author zxh
 * @date 2022-01-28 09:22
 * @since 1.0.0
 */
@Getter
@Setter
@ToString
@Accessors(chain = true)
@SuperBuilder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@Schema
public class RbacOrgRoleMenuButtonDto implements Serializable {
    private static final long serialVersionUID = -63399996952662860L;

    @Schema(name = "roleCode", title = "角色编码")
    private String roleCode;

    @Schema(name = "roleId", title = "角色id")
    private String roleId;

    @Schema(name = "menuId", title = "权限id")
    private String menuId;

    @Schema(name = "metaTitle", title = "菜单名称")
    private String metaTitle;

    @Schema(name = "actionSet", title = "菜单按钮权限")
    private Set<Action> actionSet;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof RbacOrgRoleMenuButtonDto)) {
            return false;
        }
        RbacOrgRoleMenuButtonDto that = (RbacOrgRoleMenuButtonDto) o;
        return Objects.equals(getMenuId(), that.getMenuId());
    }


    @Override
    public int hashCode() {
        return Objects.hash(getMenuId());
    }

    @Getter
    @Setter
    @ToString
    @Accessors(chain = true)
    @SuperBuilder(toBuilder = true)
    @AllArgsConstructor
    @NoArgsConstructor
    @Schema
    public static class Action implements Serializable {
        private static final long serialVersionUID = 6588630894991792617L;

        @Schema(name = "menuId", title = "权限id")
        private String menuId;

        @Schema(name = "actions", title = "按钮权限标识")
        private String action;

        @Schema(name = "roleId", title = "角色id")
        private String roleId;

        @Schema(name = "actionType", title = "按钮权限类型:1-前端型,2-后端型,3-前后端型")
        private Integer actionType;

        @Schema(name = "metaTitle", title = "菜单名称")
        private String metaTitle;

        @Schema(name = "parentId", title = "父id")
        private String parentId;

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (!(o instanceof Action)) {
                return false;
            }
            Action action = (Action) o;
            return Objects.equals(getMenuId(), action.getMenuId());
        }


        @Override
        public int hashCode() {
            return Objects.hash(getMenuId());
        }
    }
}
