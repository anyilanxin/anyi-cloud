package com.anyilanxin.skillfull.system.modules.rbac.controller.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;

/**
 * 角色-关联关系表条件查询Request
 *
 * @author zxiaozhou
 * @copyright zxiaozhou（https://skillfull.divisu.com）
 * @date 2022-05-02 16:12:20
 * @since JDK1.8
 */
@Getter
@Setter
@ToString
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@Schema
public class RbacCorrelateRoleQueryVo implements Serializable {
    private static final long serialVersionUID = -86342606217090877L;

    @Schema(name = "correlateRoleId", title = "角色关联关系id")
    private String correlateRoleId;

    @Schema(name = "correlateId", title = "关联id")
    private String correlateId;

    @Schema(name = "correlateType", title = "关联类型：1-组织机构,2-职位,3-个人,4-用户组,具体与RoleCorrelateType一致")
    private Integer correlateType;

    @Schema(name = "roleId", title = "角色id")
    private String roleId;

}
