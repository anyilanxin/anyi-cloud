package com.anyilanxin.skillfull.system.modules.rbac.controller.vo;

import com.anyilanxin.skillfull.corecommon.validation.annotation.NotBlankOrNull;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;

/**
 * 机构角色-菜单表添加或修改Request
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
@EqualsAndHashCode
@NoArgsConstructor
@Schema
public class RbacOrgRoleMenuVo implements Serializable {
    private static final long serialVersionUID = 641659832375461555L;

    @Schema(name = "menuId", title = "权限id", required = true)
    @NotBlankOrNull(message = "权限id不能为空")
    private String menuId;

    @Schema(name = "orgRoleId", title = "机构角色id", required = true)
    @NotBlankOrNull(message = "机构角色id不能为空")
    private String orgRoleId;

    @Schema(name = "orgId", title = "机构id", required = true)
    @NotBlankOrNull(message = "机构id不能为空")
    private String orgId;

}
