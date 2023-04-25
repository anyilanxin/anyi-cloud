package com.anyilanxin.skillfull.system.modules.rbac.service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;

/**
 * 机构角色-菜单表分页查询Response
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
@EqualsAndHashCode
@Schema
public class RbacOrgRoleMenuPageDto implements Serializable {
    private static final long serialVersionUID = 617120570184625894L;

    @Schema(name = "orgRoleMenuId", title = "机构权限角色id")
    private String orgRoleMenuId;

    @Schema(name = "menuId", title = "权限id")
    private String menuId;

    @Schema(name = "orgRoleId", title = "机构角色id")
    private String orgRoleId;

    @Schema(name = "orgId", title = "机构id")
    private String orgId;

}
