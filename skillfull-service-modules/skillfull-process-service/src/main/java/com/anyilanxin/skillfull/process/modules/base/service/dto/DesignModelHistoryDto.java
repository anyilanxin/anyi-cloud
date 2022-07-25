// +----------------------------------------------------------------------
// | SkillFull快速开发平台 [ SkillFull ]
// +----------------------------------------------------------------------
// | 版权所有 2020~2021 zxiaozhou
// +----------------------------------------------------------------------
// | 官方网站: https://www.divisu.com
// +----------------------------------------------------------------------
// | 作者: zxiaozhou <z7630853@163.com>
// +----------------------------------------------------------------------
package com.anyilanxin.skillfull.process.modules.base.service.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 流程模型历史查询Response
 *
 * @author zxiaozhou
 * @date 2021-11-25 09:52:36
 * @since JDK1.8
 */
@Getter
@Setter
@ToString
@EqualsAndHashCode

@SuperBuilder(toBuilder = true)

@NoArgsConstructor
@Schema
public class DesignModelHistoryDto implements Serializable {
    private static final long serialVersionUID = 174426622776752740L;

    @Schema(name = "historyModelId", title = "历史模型id")
    private String historyModelId;

    @Schema(name = "modelId", title = "模型id")
    private String modelId;

    @Schema(name = "categoryName", title = "类别名称")
    private String categoryName;

    @Schema(name = "diagramNames", title = "模型名称")
    private String diagramNames;

    @Schema(name = "processDefinitionKeys", title = "流程定义key,多个逗号隔开")
    private String processDefinitionKeys;

    @Schema(name = "processDefinitionIds", title = "流程定义ids,多个逗号隔开")
    private String processDefinitionIds;

    @Schema(name = "diagramData", title = "bpmn模型(转换为base64存储)")
    private String diagramData;

    @Schema(name = "deploymentName", title = "部署名称")
    private String deploymentName;

    @Schema(name = "deploymentTime", title = "部署时间", example = "2020-12-21 12:22")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    protected LocalDateTime deploymentTime;

    @Schema(name = "deploymentId", title = "部署id")
    private String deploymentId;

    @Schema(name = "resourceNames", title = "资源名称")
    private String resourceNames;

    @Schema(name = "resourceIds", title = "资源ids")
    private String resourceIds;

    @Schema(name = "category", title = "模型类别")
    private String category;

    @Schema(name = "version", title = "当前模型版本")
    private Integer version;

    @Schema(name = "remark", title = "备注")
    private String remark;

    @Schema(name = "createUserId", title = "创建用户id")
    private String createUserId;

    @Schema(name = "createUserName", title = "创建用户姓名")
    private String createUserName;

    @Schema(name = "active", title = "活动数量")
    private long active;

    @Schema(name = "suspended", title = "挂起数量")
    private long suspended;

    @Schema(name = "completed", title = "完成数量")
    private long completed;

    @Schema(name = "externallyTerminated", title = "外部终止")
    private long externallyTerminated;

    @Schema(name = "internallyTerminated", title = "内部终止")
    private long internallyTerminated;

    @Schema(name = "totalNum", title = "所有状态数量")
    private long totalNum;

    @Schema(name = "createTime", title = "创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime createTime;

    @Schema(name = "havePool", title = "是否pool模型,0-不是,1-是。默认0")
    private Integer havePool;

}
