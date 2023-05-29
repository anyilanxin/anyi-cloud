

package com.anyilanxin.anyicloud.system.modules.rbac.controller.vo;

import com.anyilanxin.anyicloud.database.datasource.base.controller.vo.BasePageVo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.SuperBuilder;

/**
 * 角色-菜单表分页查询Request
 *
 * @author zxh
 * @copyright zhouxuanhong（https://anyilanxin.com）
 * @date 2022-05-02 16:12:21
 * @since 1.0.0
 */
@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@Schema
public class RbacRoleMenuPageVo extends BasePageVo {
    private static final long serialVersionUID = -60528072000405002L;

    @Schema(name = "roleMenuId", title = "权限角色id")
    private String roleMenuId;

    @Schema(name = "menuId", title = "权限id")
    private String menuId;

    @Schema(name = "roleId", title = "角色id")
    private String roleId;
}
