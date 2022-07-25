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

import com.anyilanxin.skillfull.corecommon.auth.model.RoleInfo;
import com.anyilanxin.skillfull.systemapi.model.SimpleUserModel;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 任务详情
 *
 * @author zxiaozhou
 * @date 2020-10-19 19:40
 * @since JDK11
 */
@Getter
@Setter
@ToString
@EqualsAndHashCode

@SuperBuilder(toBuilder = true)

@NoArgsConstructor
public class TasksHistoryDetailDto implements Serializable {
    private static final long serialVersionUID = -9172543358400492801L;

    @Schema(name = "activityId", title = "任务活动id")
    protected String activityId;

    @Schema(name = "activityName", title = "任务活动名称")
    protected String activityName;

    @Schema(name = "activityType", title = "任务活动类型,org.camunda.bpm.engine.ActivityTypes")
    protected String activityType;

    @Schema(name = "approvalOpinions", title = "审批意见")
    private String approvalOpinions;

    @Schema(name = "activityInstanceId", title = "任务活动实例id")
    protected String activityInstanceId;

    @Schema(name = "activityInstanceState", title = "任务活动状态:0-默认,1-整个范围结束(一般用在结束任务标识整个流程结束),2-取消,3-开始,4-结束,默认0,具体参考引擎ActivityInstanceState类,当为null时说明任务还未执行")
    protected Integer activityInstanceState;

    @Schema(name = "taskId", title = "任务id")
    protected String taskId;

    @Schema(name = "taskStartTime", title = "任务开始时间", example = "2020-12-21 12:22:21")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime taskStartTime;

    @Schema(name = "taskEndTime", title = "任务结束时间", example = "2020-12-21 12:22:21")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime taskEndTime;

    @Schema(name = "durationInMillis", title = "消耗时间(s)")
    private Long durationInMillis;

    @Schema(name = "durationStr", title = "消耗时间自带单位")
    private String durationStr;

    @Schema(name = "userInfos", title = "流程候选用户信息")
    private List<SimpleUserModel> userInfos;

    @Schema(name = "groupInfos", title = "流程候选用户组信息")
    private List<RoleInfo> groupInfos;

    @Schema(name = "taskAssignee", title = "审批人", hidden = true)
    @JsonIgnore
    protected String taskAssignee;

    @Schema(name = "taskAssigneeInfo", title = "办理人信息")
    private SimpleUserModel taskAssigneeInfo;

    @Schema(name = "sequenceCounter", title = "序列计数器")
    protected Long sequenceCounter;

    @Schema(name = "taskRemovalTime", title = "删除时间", example = "2020-12-21 12:22:21")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime taskRemovalTime;
}
