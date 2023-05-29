

package com.anyilanxin.anyicloud.process.modules.manage.service.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;

import java.io.Serializable;
import java.time.LocalDateTime;

import lombok.*;
import lombok.experimental.SuperBuilder;

/**
 * 流程图用户任务信息
 *
 * @author zxh
 * @date 2022-01-03 10:58
 * @since 1.0.0
 */
@Getter
@Setter
@ToString
@EqualsAndHashCode
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
public class ProcessInfoDto implements Serializable {
    private static final long serialVersionUID = -9172543358400492801L;

    @Schema(name = "processDefinitionKey", title = "流程定义key")
    private String processDefinitionKey;

    @Schema(name = "versionTag", title = "版本标签")
    private String versionTag;

    @Schema(name = "version", title = "版本号")
    private Integer version;

    @Schema(name = "category", title = "类别")
    private String category;

    @Schema(name = "categoryName", title = "类别名称")
    private String categoryName;

    @Schema(name = "deploymentId", title = "部署id")
    private String deploymentId;

    @Schema(name = "resourceNames", title = "资源名称")
    private String resourceNames;

    @Schema(name = "diagramData", title = "bpmn blob文件")
    private String diagramData;

    @Schema(name = "name", title = "模型名称")
    private String name;

    @Schema(name = "hasStartFormKey", title = "是否有开始表单")
    private Boolean hasStartFormKey;

    @Schema(name = "processDefinitionId", title = "流程定义id")
    private String processDefinitionId;

    @Schema(name = "isStartableInTasklist", title = "是否可启动")
    private boolean isStartableInTasklist;

    @Schema(name = "deploymentTime", title = "部署时间", example = "2020-12-21 12:22")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    protected LocalDateTime deploymentTime;

    @Schema(name = "deploymentName", title = "部署名称")
    private String deploymentName;
}
