package com.anyilanxin.skillfull.system.modules.rbac.controller.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;

/**
 * 机构角色-菜单表条件查询Request
 *
 * @author zxiaozhou
 * @copyright zxiaozhou（https://skillfull.divisu.com）
 * @date 2022-07-05 00:22:57
 * @since JDK1.8
 */
@Getter
@Setter
@ToString
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@Schema
public class RbacOrgRoleMenuQueryVo implements Serializable {
    private static final long serialVersionUID = -40363597212430157L;

    @Schema(name = "orgRoleMenuId", title = "机构权限角色id")
    private String orgRoleMenuId;

    @Schema(name = "menuId", title = "权限id")
    private String menuId;

    @Schema(name = "orgRoleId", title = "机构角色id")
    private String orgRoleId;

    @Schema(name = "orgId", title = "机构id")
    private String orgId;

}
