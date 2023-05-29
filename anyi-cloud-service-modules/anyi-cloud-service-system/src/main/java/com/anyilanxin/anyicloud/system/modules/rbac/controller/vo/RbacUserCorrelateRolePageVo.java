

package com.anyilanxin.anyicloud.system.modules.rbac.controller.vo;

import com.anyilanxin.anyicloud.database.datasource.base.controller.vo.BasePageVo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.SuperBuilder;

/**
 * 用户-关联关系角色表分页查询Request
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
public class RbacUserCorrelateRolePageVo extends BasePageVo {
    private static final long serialVersionUID = -54427114666085195L;

    @Schema(name = "userCorrelateRoleId", title = "用户关联关系角色id")
    private String userCorrelateRoleId;

    @Schema(name = "correlateId", title = "关联id")
    private String correlateId;

    @Schema(name = "roleId", title = "角色id")
    private String roleId;

    @Schema(name = "correlateType", title = "关联类型：1-组织机构角色,2-职位角色,4-用户组角色")
    private Integer correlateType;

    @Schema(name = "userId", title = "用户id")
    private String userId;
}
