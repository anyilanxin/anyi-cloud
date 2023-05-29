

package com.anyilanxin.anyicloud.process.modules.base.controller.vo;

import com.anyilanxin.anyicloud.corecommon.validation.annotation.NotBlankOrNull;
import io.swagger.v3.oas.annotations.media.Schema;

import java.io.Serializable;

import lombok.*;
import lombok.experimental.SuperBuilder;

/**
 * 流程类别添加或修改Request
 *
 * @author zxh
 * @date 2021-11-19 10:47:01
 * @since 1.0.0
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
