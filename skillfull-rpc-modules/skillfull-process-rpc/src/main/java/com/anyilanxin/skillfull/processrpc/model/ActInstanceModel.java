// +----------------------------------------------------------------------
// | SkillFull快速开发平台 [ SkillFull ]
// +----------------------------------------------------------------------
// | 版权所有 2020~2021 zxiaozhou
// +----------------------------------------------------------------------
// | 官方网站: https://skillfull.divisu.com
// +----------------------------------------------------------------------
// | 作者: zxiaozhou <z7630853@163.com>
// +----------------------------------------------------------------------
package com.anyilanxin.skillfull.processrpc.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * 活动实例信息(用于渲染流程图)
 *
 * @author zxiaozhou
 * @date 2021-12-29 11:56
 * @since JDK1.8
 */
@Getter
@Setter
@ToString
@EqualsAndHashCode
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
public class ActInstanceModel implements Serializable, Comparable<ActInstanceModel> {
    private static final long serialVersionUID = -3317408065181369306L;

    @Schema(name = "id", title = "活动实例唯一id")
    private String id;

    @Schema(name = "activityId", title = "活动id")
    private String activityId;

    @Schema(name = "executionId", title = "执行id")
    private String executionId;

    @Schema(name = "assigneeUserInfo", title = "活动名称")
    private String activityName;

    @Schema(name = "activityType", title = "活动类型,与org.camunda.bpm.engine.ActivityTypes一致")
    private String activityType;

    @Schema(name = "userTask", title = "是否用户任务")
    private boolean userTask;

    @Schema(name = "multi", title = "是否多实例")
    private boolean multi;

    @Schema(name = "multiCompleteInfo", title = "多实例完成情况")
    private String multiCompleteInfo;

    @Schema(name = "multiInfos", title = "多实例信息")
    private List<ActInstanceModel> multiInfos;

    @Schema(name = "activityInstanceId", title = "活动实例id")
    private String activityInstanceId;

    @Schema(name = "activityInstanceState", title = "活动状态:0-默认,1-整个范围结束(一般用在结束任务标识整个流程结束),2-取消,3-开始,4-结束,默认0,具体参考引擎ActivityInstanceState类,当为null时说明任务还未执行")
    private int activityInstanceState;

    @Schema(name = "parentActivityInstanceId", title = "父级活动实例id")
    private String parentActivityInstanceId;

    @Schema(name = "taskId", title = "任务id")
    private String taskId;

    @Schema(name = "taskAssignee", title = "任务审批人")
    private String taskAssignee;

    @Schema(name = "taskAssigneeInfo", title = "审批人信息完整信息")
    private ProcessUserModel taskAssigneeInfo;

    @Schema(name = "candidateUsers", title = "流程候选用户信息")
    @Builder.Default
    private List<ProcessUserModel> candidateUsers = Collections.emptyList();

    @Schema(name = "candidateGroups", title = "流程候选用户组信息")
    @Builder.Default
    private List<ProcessRoleModel> candidateGroups = Collections.emptyList();

    @Schema(name = "durationInMillis", title = "消耗时间")
    private Long durationInMillis;

    @Schema(name = "durationInMillisFormat", title = "消耗时间(格式化值)")
    private String durationInMillisFormat;

    @Schema(name = "startTime", title = "开始时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date startTime;

    @Schema(name = "endTime", title = "结束时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date endTime;

    @Schema(name = "dueDate", title = "到期日期")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date dueDate;

    @Schema(name = "followUpDate", title = "跟进日期")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date followUpDate;

    @Schema(name = "dueDateStr", title = "到期日期(模型原始值)")
    private String dueDateStr;

    @Schema(name = "dueFormatDate", title = "到期日期格式化值(模型原始值)")
    private String dueFormatDate;

    @Schema(name = "followUpDateStr", title = "跟进日期(模型原始值)")
    private String followUpDateStr;

    @Schema(name = "followUpFormatDate", title = "跟进时间格式化值(模型原始值)")
    private String followUpFormatDate;

    @Schema(name = "sequenceCounter", title = "序列计数器")
    private long sequenceCounter;

    @Schema(name = "taskTitle", title = "任务标题")
    private String taskTitle;

    @Schema(name = "handleType", title = "任务操作类型，与TaskHandleType一致")
    private String handleType;

    @Schema(name = "handleTypeDescribe", title = "任务操作类型描述，与TaskHandleType一致")
    private String handleTypeDescribe;

    @Schema(name = "propertyInfo", title = "节点信息扩展属性信息")
    private UserTaskPropertyInfoModel propertyInfo;

    @Schema(name = "formModel", title = "任务表单")
    private ProcessFormModel formModel;

    @Schema(name = "approvalStatus", title = "审批状态,与TaskStatus一致")
    private Integer approvalStatus;

    @Schema(name = "optionInfos", title = "审批意见信息")
    private ApprovalInfoModel optionInfos;

    @Schema(name = "owner", title = "委托办理人")
    private String owner;

    @Schema(name = "ownerUserInfo", title = "委托办理人信息")
    private ProcessUserModel ownerUserInfo;


    @Override
    public int compareTo(ActInstanceModel o) {
        return Long.compare(this.sequenceCounter, o.sequenceCounter);
//        if (Objects.isNull(o.getEndTime()) && Objects.isNull(this.getEndTime())) {
//            return Long.compare(this.sequenceCounter, o.sequenceCounter);
//        } else if (Objects.isNull(o.getEndTime())) {
//            return -1;
//        } else if (Objects.isNull(this.getEndTime())) {
//            return 1;
//        }
//        return Long.compare(this.getEndTime().getTime() + this.sequenceCounter, o.getEndTime().getTime() + o.sequenceCounter);
    }
}
