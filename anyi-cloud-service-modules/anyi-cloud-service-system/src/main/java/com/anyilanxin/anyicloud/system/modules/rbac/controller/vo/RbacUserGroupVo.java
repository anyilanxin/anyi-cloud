

package com.anyilanxin.anyicloud.system.modules.rbac.controller.vo;

import com.anyilanxin.anyicloud.corecommon.validation.annotation.NotBlankOrNull;
import io.swagger.v3.oas.annotations.media.Schema;

import java.io.Serializable;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

import lombok.*;
import lombok.experimental.SuperBuilder;

/**
 * 用户组添加或修改Request
 *
 * @author zxh
 * @copyright zhouxuanhong（https://anyilanxin.com）
 * @date 2022-05-02 16:12:20
 * @since 1.0.0
 */
@Getter
@Setter
@ToString
@SuperBuilder(toBuilder = true)
@EqualsAndHashCode
@NoArgsConstructor
@Schema
public class RbacUserGroupVo implements Serializable {
    private static final long serialVersionUID = 175609396613145057L;

    @Schema(name = "groupName", title = "用户组名称", required = true)
    @NotBlankOrNull(message = "用户组名称不能为空")
    private String groupName;

    @Schema(name = "groupStatus", title = "用户组状态:0-禁用,1-启用。默认0", required = true)
    @NotBlankOrNull(message = "用户组状态:0-禁用,1-启用。默认0不能为空")
    @Min(value = 0, message = "状态只能为0、1")
    @Max(value = 1, message = "状态只能为0、1")
    private Integer groupStatus;

    @Schema(name = "groupCode", title = "用户组编码", required = true)
    @NotBlankOrNull(message = "用户组编码不能为空")
    private String groupCode;

    @Schema(name = "remark", title = "备注")
    private String remark;
}
