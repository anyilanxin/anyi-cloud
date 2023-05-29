

package com.anyilanxin.anyicloud.system.modules.rbac.controller.vo;

import com.anyilanxin.anyicloud.corecommon.validation.annotation.NotBlankOrNull;
import io.swagger.v3.oas.annotations.media.Schema;

import java.io.Serializable;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

import lombok.*;
import lombok.experimental.SuperBuilder;

/**
 * 角色表添加或修改Request
 *
 * @author zxh
 * @copyright zhouxuanhong（https://anyilanxin.com）
 * @date 2022-05-02 19:29:58
 * @since 1.0.0
 */
@Getter
@Setter
@ToString
@SuperBuilder(toBuilder = true)
@EqualsAndHashCode
@NoArgsConstructor
@Schema
public class RbacRoleVo implements Serializable {
    private static final long serialVersionUID = -68621005002247307L;

    @Schema(name = "roleName", title = "角色名称", required = true)
    @NotBlankOrNull(message = "角色名称不能为空")
    private String roleName;

    @Schema(name = "parentRoleId", title = "上级角色id", hidden = true)
    private String parentRoleId;

    @Schema(name = "autoBind", title = "绑定方式:0-手动,1-自动。默认0(用户创建时自动挂接)")
    private Integer autoBind;

    @Schema(name = "roleCode", title = "角色编码", required = true)
    @NotBlankOrNull(message = "角色编码不能为空")
    private String roleCode;

    @Schema(name = "roleStatus", title = "角色状态:0-禁用,1-启用,默认0", required = true)
    @NotBlankOrNull(message = "角色状态不能为空")
    @Min(value = 0, message = "角色状态只能为0、1")
    @Max(value = 1, message = "角色状态只能为0、1")
    private Integer roleStatus;

    @Schema(name = "remark", title = "备注")
    private String remark;
}
