// +----------------------------------------------------------------------
// | SkillFull快速开发平台 [ SkillFull ]
// +----------------------------------------------------------------------
// | 版权所有 2020~2021 zxiaozhou
// +----------------------------------------------------------------------
// | 官方网站: https://skillfull.divisu.com
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
 * 流程类别添加或修改Request
 *
 * @author zxiaozhou
 * @date 2021-11-19 10:47:01
 * @since JDK1.8
 */
@Getter
@Setter
@ToString

@SuperBuilder(toBuilder = true)
@EqualsAndHashCode

@NoArgsConstructor
@Schema
public class ProcessCategoryVo implements Serializable {
    private static final long serialVersionUID = -22683744684289103L;

    @Schema(name = "categoryCode", title = "类别编码(唯一)", required = true)
    @NotBlankOrNull(message = "类别编码(唯一)不能为空")
    private String categoryCode;

    @Schema(name = "categoryName", title = "类别名称", required = true)
    @NotBlankOrNull(message = "类别名称不能为空")
    private String categoryName;

    @Schema(name = "categoryState", title = "类别状态:0-禁用,1-启用。默认0", required = true)
    @NotBlankOrNull(message = "类别状态:0-禁用,1-启用。默认0不能为空")
    private Integer categoryState;

    @Schema(name = "categoryDescribe", title = "类别描述")
    private String categoryDescribe;

    @Schema(name = "pictures", title = "类别logo")
    private String pictures;

    @Schema(name = "remark", title = "备注")
    private String remark;
}
