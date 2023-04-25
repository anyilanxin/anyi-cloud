package com.anyilanxin.skillfull.process.modules.base.controller.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 流程模型历史添加或修改Request
 *
 * @author zxiaozhou
 * @date 2021-11-25 09:52:36
 * @since JDK1.8
 */
@Getter
@Setter
@ToString

@SuperBuilder(toBuilder = true)
@EqualsAndHashCode

@NoArgsConstructor
@Schema
public class DesignModelHistoryVo implements Serializable {
    private static final long serialVersionUID = -72656266455161054L;

    @Schema(name = "deploymentTime", title = "部署时间")
    protected LocalDateTime deploymentTime;

    @Schema(name = "modelId", title = "模型id")
    private String modelId;

    @Schema(name = "diagramNames", title = "模型名称")
    private String diagramNames;

    @Schema(name = "processDefinitionKeys", title = "流程定义key,多个逗号隔开")
    private String processDefinitionKeys;

    @Schema(name = "resourceNames", title = "资源名称")
    private String resourceNames;

    @Schema(name = "resourceIds", title = "资源ids")
    private String resourceIds;

    @Schema(name = "processDefinitionIds", title = "流程定义ids,多个逗号隔开")
    private String processDefinitionIds;

    @Schema(name = "diagramData", title = "bpmn模型(转换为base64存储)")
    private String diagramData;

    @Schema(name = "deploymentName", title = "部署名称")
    private String deploymentName;

    @Schema(name = "deploymentId", title = "部署id")
    private String deploymentId;

    @Schema(name = "category", title = "模型类别")
    private String category;

    @Schema(name = "version", title = "当前模型版本")
    private Integer version;

    @Schema(name = "remark", title = "备注")
    private String remark;

    @Schema(name = "havePool", title = "是否pool模型,0-不是,1-是。默认0")
    private Integer havePool;

}
