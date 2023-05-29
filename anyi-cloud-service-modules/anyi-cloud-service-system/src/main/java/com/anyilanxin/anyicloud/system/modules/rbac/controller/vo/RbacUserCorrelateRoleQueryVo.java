

package com.anyilanxin.anyicloud.system.modules.rbac.controller.vo;

import io.swagger.v3.oas.annotations.media.Schema;

import java.io.Serializable;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

/**
 * 用户-关联关系角色表条件查询Request
 *
 * @author zxh
 * @copyright zhouxuanhong（https://anyilanxin.com）
 * @date 2022-05-02 16:12:21
 * @since 1.0.0
 */
@Getter
@Setter
@ToString
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@Schema
public class RbacUserCorrelateRoleQueryVo implements Serializable {
    private static final long serialVersionUID = -16419691984292060L;

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
