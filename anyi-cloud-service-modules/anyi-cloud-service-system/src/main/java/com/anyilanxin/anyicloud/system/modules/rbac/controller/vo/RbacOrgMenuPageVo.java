

package com.anyilanxin.anyicloud.system.modules.rbac.controller.vo;

import com.anyilanxin.anyicloud.database.datasource.base.controller.vo.BasePageVo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.SuperBuilder;

/**
 * 机构-菜单表分页查询Request
 *
 * @author zxh
 * @copyright zhouxuanhong（https://anyilanxin.com）
 * @date 2022-07-02 23:01:20
 * @since 1.0.0
 */
@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@Schema
public class RbacOrgMenuPageVo extends BasePageVo {
    private static final long serialVersionUID = 559220562724391775L;

    @Schema(name = "roleMenuId", title = "权限角色id")
    private String roleMenuId;

    @Schema(name = "menuId", title = "权限id")
    private String menuId;

    @Schema(name = "orgId", title = "机构id")
    private String orgId;
}
