// +----------------------------------------------------------------------
// | SkillFull快速开发平台 [ SkillFull ]
// +----------------------------------------------------------------------
// | 版权所有 2020~2021 zxiaozhou
// +----------------------------------------------------------------------
// | 官方网站: https://www.divisu.com
// +----------------------------------------------------------------------
// | 作者: zxiaozhou <z7630853@163.com>
// +----------------------------------------------------------------------
package com.anyilanxin.skillfull.process.modules.manage.service.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 流程定义信息
 *
 * @author zxiaozhou
 * @date 2020-10-14 20:49
 * @since JDK11
 */
@Getter
@Setter
@ToString
@EqualsAndHashCode
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
public class ProcessDefinitionPageDto implements Serializable {
    private static final long serialVersionUID = 8611965226387452452L;

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

    @Schema(name = "haveRunTask", title = "是否有任务运行")
    private boolean haveRunTask;

    @Schema(name = "isSuspended", title = "是否挂起")
    private boolean isSuspended;

    @Schema(name = "suspensionState", title = "流程状态,SuspensionState,1-active,2-suspended")
    private int suspensionState;

    @Schema(name = "instanceNum", title = "流程实例数")
    private long instanceNum;

    @Schema(name = "instanceCompleteNum", title = "流程实例完成数")
    private long instanceCompleteNum;
}
