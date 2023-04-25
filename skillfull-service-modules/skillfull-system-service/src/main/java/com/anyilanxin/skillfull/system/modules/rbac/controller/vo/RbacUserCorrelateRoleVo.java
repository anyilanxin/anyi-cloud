package com.anyilanxin.skillfull.system.modules.rbac.controller.vo;

import com.anyilanxin.skillfull.corecommon.validation.annotation.NotBlankOrNull;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;

/**
 * 用户-关联关系角色表添加或修改Request
 *
 * @author zxiaozhou
 * @copyright zxiaozhou（https://skillfull.divisu.com）
 * @date 2022-05-02 16:12:21
 * @since JDK1.8
 */
@Getter
@Setter
@ToString
@SuperBuilder(toBuilder = true)
@EqualsAndHashCode
@NoArgsConstructor
@Schema
public class RbacUserCorrelateRoleVo implements Serializable {
    private static final long serialVersionUID = 462319281864512870L;

    @Schema(name = "correlateId", title = "关联id", required = true)
    @NotBlankOrNull(message = "关联id不能为空")
    private String correlateId;

    @Schema(name = "roleId", title = "角色id", required = true)
    @NotBlankOrNull(message = "角色id不能为空")
    private String roleId;

    @Schema(name = "correlateType", title = "关联类型：1-组织机构角色,2-职位角色,4-用户组角色", required = true)
    @NotBlankOrNull(message = "关联类型：1-组织机构角色,2-职位角色,4-用户组角色不能为空")
    private Integer correlateType;

    @Schema(name = "userId", title = "用户id", required = true)
    @NotBlankOrNull(message = "用户id不能为空")
    private String userId;

}
