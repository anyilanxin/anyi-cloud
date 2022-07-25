// +----------------------------------------------------------------------
// | SkillFull快速开发平台 [ SkillFull ]
// +----------------------------------------------------------------------
// | 版权所有 2020~2021 zxiaozhou
// +----------------------------------------------------------------------
// | 官方网站: https://www.divisu.com
// +----------------------------------------------------------------------
// | 作者: zxiaozhou <z7630853@163.com>
// +----------------------------------------------------------------------
package com.anyilanxin.skillfull.process.modules.base.controller.vo;

import com.anyilanxin.skillfull.corecommon.validation.annotation.NotBlankOrNull;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;

/**
 * 流程表达式添加或修改Request
 *
 * @author zxiaozhou
 * @copyright zxiaozhou（https://divisu.com）
 * @date 2022-03-27 00:20:29
 * @since JDK1.8
 */
@Getter
@Setter
@ToString

@SuperBuilder(toBuilder = true)
@EqualsAndHashCode

@NoArgsConstructor
@Schema
public class DesignExpressionVo implements Serializable {
    private static final long serialVersionUID = -96603288767354418L;

    @Schema(name = "handleType", title = "处理类型:0-人员,1-用户组,2-时间,3-条件,4-其他", required = true)
    @NotBlankOrNull(message = "处理类型:0-人员,1-用户组,2-时间,3-条件,4-其他不能为空")
    private Integer handleType;

    @Schema(name = "implementClass", title = "实现class路径")
    private String implementClass;

    @Schema(name = "example", title = "示例")
    private String example;

    @Schema(name = "remark", title = "备注")
    private String remark;

    @Schema(name = "expressionName", title = "表达式名称", required = true)
    @NotBlankOrNull(message = "表达式名称不能为空")
    private String expressionName;

    @Schema(name = "expressionType", title = "表达式类型:1-表达式,2-委托表达式", required = true)
    @NotBlankOrNull(message = "表达式类型:1-表达式,2-委托表达式不能为空")
    private Integer expressionType;

    @Schema(name = "elExpressionValue", title = "El编码值,‘${’开头’}‘结尾，唯一", required = true)
    @NotBlankOrNull(message = "El编码值,‘${’开头’}‘结尾，唯一不能为空")
    private String elExpressionValue;

    @Schema(name = "expressionState", title = "表达式状态:0-禁用,1-启用", required = true)
    @NotBlankOrNull(message = "表达式状态:0-禁用,1-启用不能为空")
    private Integer expressionState;

}
