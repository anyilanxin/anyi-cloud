// +----------------------------------------------------------------------
// | SkillFull快速开发平台 [ SkillFull ]
// +----------------------------------------------------------------------
// | 版权所有 2020~2021 zxiaozhou
// +----------------------------------------------------------------------
// | 官方网站: https://www.divisu.com
// +----------------------------------------------------------------------
// | 作者: zxiaozhou <z7630853@163.com>
// +----------------------------------------------------------------------
package com.anyilanxin.skillfull.system.modules.rbac.controller.vo;

import com.anyilanxin.skillfull.corecommon.validation.annotation.NotBlankOrNull;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;

/**
 * 角色-关联关系表添加或修改Request
 *
 * @author zxiaozhou
 * @copyright zxiaozhou（https://divisu.com）
 * @date 2022-05-02 16:12:20
 * @since JDK1.8
 */
@Getter
@Setter
@ToString
@SuperBuilder(toBuilder = true)
@EqualsAndHashCode
@NoArgsConstructor
@Schema
public class RbacCorrelateRoleVo implements Serializable {
    private static final long serialVersionUID = 220546550176306625L;

    @Schema(name = "correlateId", title = "关联id", required = true)
    @NotBlankOrNull(message = "关联id不能为空")
    private String correlateId;

    @Schema(name = "correlateType", title = "关联类型：1-组织机构,2-职位,3-个人,4-用户组,具体与RoleCorrelateType一致", required = true)
    @NotBlankOrNull(message = "关联类型：1-组织机构,2-职位,3-个人,4-用户组,具体与RoleCorrelateType一致不能为空")
    private Integer correlateType;

    @Schema(name = "roleId", title = "角色id", required = true)
    @NotBlankOrNull(message = "角色id不能为空")
    private String roleId;

}
