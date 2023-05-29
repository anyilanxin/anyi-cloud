

package com.anyilanxin.anyicloud.process.modules.base.controller.vo;

import com.anyilanxin.anyicloud.corecommon.validation.annotation.NotBlankOrNull;
import io.swagger.v3.oas.annotations.media.Schema;

import java.io.Serializable;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

import lombok.*;
import lombok.experimental.SuperBuilder;

/**
 * 流程模型管理添加或修改Request
 *
 * @author zxh
 * @date 2021-11-25 05:22:56
 * @since 1.0.0
 */
@Getter
@Setter
@ToString
@SuperBuilder(toBuilder = true)
@EqualsAndHashCode
@NoArgsConstructor
@Schema
public class DesignModelVo implements Serializable {
    private static final long serialVersionUID = 153779364144994832L;

    @Schema(name = "diagramData", title = "bpmn模型(转换为base64存储)", required = true)
    @NotBlankOrNull(message = "bpmn模型(转换为base64存储)不能为空")
    private String diagramData;

    @Schema(name = "diagramNames", title = "模型名称")
    private String diagramNames;

    @Schema(name = "processDefinitionKeys", title = "流程定义key")
    private String processDefinitionKeys;

    @Schema(name = "category", title = "模型类别", required = true)
    @NotBlankOrNull(message = "模型类别不能为空")
    private String category;

    @Schema(name = "havePool", title = "是否pool模型,0-不是,1-是。默认0", required = true)
    @NotBlankOrNull(message = "是否pool不能为空")
    @Min(value = 0, message = "是否pool只能为0、1")
    @Max(value = 1, message = "是否pool只能为0、1")
    private Integer havePool;
}
