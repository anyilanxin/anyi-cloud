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

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.io.Serializable;

/**
 * 用户组添加或修改Request
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
