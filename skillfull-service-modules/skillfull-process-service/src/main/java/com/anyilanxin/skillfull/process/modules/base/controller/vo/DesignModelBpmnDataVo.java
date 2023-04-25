package com.anyilanxin.skillfull.process.modules.base.controller.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * 流程模型管理添加或修改Request
 *
 * @author zxiaozhou
 * @date 2020-10-15 22:17:54
 * @since JDK11
 */
@Getter
@Setter
@ToString

@SuperBuilder(toBuilder = true)

@NoArgsConstructor
@EqualsAndHashCode
@Schema
public class DesignModelBpmnDataVo implements Serializable {
    private static final long serialVersionUID = -94103370608078362L;

    @Schema(name = "modelId", title = "模型id", required = true)
    @NotBlank(message = "模型id不能为空")
    private String modelId;

    @Schema(name = "diagramData", title = "bpmn模型(转换为base64存储)", required = true)
    @NotBlank(message = "bpmn模型不能为空")
    private String diagramData;


}
