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

import com.anyilanxin.skillfull.systemapi.model.SimpleUserModel;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 实例分页信息
 *
 * @author zxiaozhou
 * @date 2021-07-30 21:51
 * @since JDK1.8
 */
@Getter
@Setter
@ToString
@EqualsAndHashCode

@SuperBuilder(toBuilder = true)

@NoArgsConstructor
public class InstanceHistoryPageDto implements Serializable {
    private static final long serialVersionUID = -796467855703929445L;

    @Schema(name = "deleteReason", title = "删除原因")
    private String deleteReason;

    @Schema(name = "instanceState", title = "实例状态,来源于常量字典:ProcessInstanceState")
    private Integer instanceState;

    @Schema(name = "version", title = "部署版本")
    private Integer version;

    @Schema(name = "startUserInfo", title = "开始用户信息")
    private SimpleUserModel startUserInfo;

    @Schema(name = "processInstanceId", title = "流程实例id")
    private String processInstanceId;

    @Schema(name = "processDefinitionId", title = "流程定义id")
    private String processDefinitionId;

    @Schema(name = "businessKey", title = "业务id")
    private String businessKey;

    @Schema(name = "startActivityId", title = "开始活动id")
    private String startActivityId;

    @Schema(name = "endActivityId", title = "结束活动id")
    private String endActivityId;

    @Schema(name = "currentTaskId", title = "当前任务id")
    private String currentTaskId;

    @Schema(name = "endActivityId", title = "当前任务名称")
    private String currentTaskName;

    @Schema(name = "currentAssigneeUserId", title = "当前任务处理人id")
    private String currentAssigneeUserId;

    @Schema(name = "currentAssigneeName", title = "当前任务处理人名称")
    private String currentAssigneeName;

    @Schema(name = "currentOwnerUserId", title = "当前任务代办人id")
    private String currentOwnerUserId;

    @Schema(name = "currentOwnerUserName", title = "当前任务代办人名称")
    private String currentOwnerUserName;

    @Schema(name = "processDefinitionName", title = "流程定义名称")
    private String processDefinitionName;

    @Schema(name = "instanceStartTime", title = "实例开始时间", example = "2020-12-21 12:22:21")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime instanceStartTime;

    @Schema(name = "instanceRemovalTime", title = "删除时间", example = "2020-12-21 12:22:21")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime instanceRemovalTime;

    @Schema(name = "isFinish", title = "是否结束:false-未结束,true-结束")
    private Boolean isFinish;

    @Schema(name = "durationInMillis", title = "消耗时间(s)")
    private Long durationInMillis;

    @Schema(name = "durationStr", title = "消耗时间自带单位")
    private String durationStr;

    @Schema(name = "instanceEndTime", title = "实例结束时间", example = "2020-12-21 12:22:21")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime instanceEndTime;
}
