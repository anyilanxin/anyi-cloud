package com.anyilanxin.skillfull.process.modules.base.controller.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 流程模型历史条件查询Request
 *
 * @author zxiaozhou
 * @date 2021-11-25 09:52:37
 * @since JDK1.8
 */
@Getter
@Setter
@ToString

@SuperBuilder(toBuilder = true)

@NoArgsConstructor
@Schema
public class DesignModelHistoryQueryVo implements Serializable {
    private static final long serialVersionUID = 801514399118905434L;

    @Schema(name = "historyModelId", title = "历史模型id")
    private String historyModelId;

    @Schema(name = "modelId", title = "模型id")
    private String modelId;

    @Schema(name = "processDefinitionKeys", title = "流程定义key,多个逗号隔开")
    private String processDefinitionKeys;

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

    @Schema(name = "createAreaCode", title = "创建区域编码")
    private String createAreaCode;

    @Schema(name = "createPositionCode", title = "创建职位编码")
    private String createPositionCode;

    @Schema(name = "createOrgSysCode", title = "创建机构系统编码")
    private String createOrgSysCode;

    @Schema(name = "createSystemCode", title = "创建系统编码")
    private String createSystemCode;

    @Schema(name = "createTenantId", title = "创建租户id")
    private String createTenantId;

    @Schema(name = "createUserId", title = "创建用户id")
    private String createUserId;

    @Schema(name = "createUserName", title = "创建用户姓名")
    private String createUserName;

    @Schema(name = "createTime", title = "创建时间")
    private LocalDateTime createTime;

    @Schema(name = "updateUserId", title = "更新用户id")
    private String updateUserId;

    @Schema(name = "updateUserName", title = "更新用户姓名")
    private String updateUserName;

    @Schema(name = "updateTime", title = "更新时间")
    private LocalDateTime updateTime;

    @Schema(name = "delFlag", title = "删除状态:0-正常,1-已删除,默认0")
    private Integer delFlag;

    @Schema(name = "havePool", title = "是否pool模型,0-不是,1-是。默认0")
    private Integer havePool;

}
