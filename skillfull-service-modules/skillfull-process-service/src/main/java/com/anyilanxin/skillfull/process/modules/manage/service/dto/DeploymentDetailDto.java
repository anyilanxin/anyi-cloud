// +----------------------------------------------------------------------
// | SkillFull快速开发平台 [ SkillFull ]
// +----------------------------------------------------------------------
// | 版权所有 2020~2021 zxiaozhou
// +----------------------------------------------------------------------
// | 官方网站: https://skillfull.divisu.com
// +----------------------------------------------------------------------
// | 作者: zxiaozhou <z7630853@163.com>
// +----------------------------------------------------------------------
package com.anyilanxin.skillfull.process.modules.manage.service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;

/**
 * 部署信息
 *
 * @author zxiaozhou
 * @date 2020-10-15 08:45
 * @since JDK11
 */
@Getter
@Setter
@ToString
@EqualsAndHashCode
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
public class DeploymentDetailDto implements Serializable {
    private static final long serialVersionUID = 8084599820581857851L;

    @Schema(name = "modelId", title = "模型id")
    private String modelId;

    @Schema(name = "deploymentId", title = "部署id")
    private String deploymentId;

    @Schema(name = "deploymentName", title = "部署名称")
    private String deploymentName;

    @Schema(name = "diagramNames", title = "模型名称")
    private String diagramNames;

    @Schema(name = "processDefinitionKey", title = "流程定义key")
    private String processDefinitionKey;

    @Schema(name = "processDefinitionId", title = "最新流程定义Id")
    private String processDefinitionId;

    @Schema(name = "suspensionState", title = "定义暂停状态")
    private int suspensionState;

    @Schema(name = "isNew", title = "是否最新版本")
    private boolean isNew;

    @Schema(name = "version", title = "当前版本")
    private int version;

    @Schema(name = "versionTag", title = "流程版本tag")
    private String versionTag;

    @Schema(name = "resourceNames", title = "流程资源名称")
    private String resourceNames;

    @Schema(name = "diagramData", title = "bpmn blob文件")
    private String diagramData;

    @Schema(name = "hasStartFormKey", title = "是否存在开始表单key")
    private boolean hasStartFormKey;

    @Schema(name = "startFormKey", title = "开始表单key")
    private String startFormKey;

    @Schema(name = "category", title = "类别")
    private String category;

    @Schema(name = "historyTimeToLive", title = "历史存活时间(单位:天，引擎自定义不可变更单位)")
    private Integer historyTimeToLive;
}
