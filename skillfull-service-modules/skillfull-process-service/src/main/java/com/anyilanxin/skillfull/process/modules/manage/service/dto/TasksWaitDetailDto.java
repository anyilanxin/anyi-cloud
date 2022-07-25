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

import com.anyilanxin.skillfull.processapi.model.ProcessRoleModel;
import com.anyilanxin.skillfull.processapi.model.ProcessUserModel;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.camunda.bpm.engine.form.FormField;

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
public class TasksWaitDetailDto implements Serializable {
    private static final long serialVersionUID = -9172543358400492801L;

    @Schema(name = "eventName", title = "事件名称")
    protected String eventName;

    @Schema(name = "formFields", title = "表单信息")
    private List<FormField> formFields;

    @Schema(name = "taskFormKey", title = "表单key")
    protected String taskFormKey;

    @Schema(name = "dueTime", title = "跟进日期", example = "2020-12-21 12:22:21")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    protected LocalDateTime dueTime;

    @Schema(name = "followUpTime", title = "到期日期", example = "2020-12-21 12:22:21")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    protected LocalDateTime followUpTime;

    @Schema(name = "taskId", title = "任务id")
    private String taskId;

    @Schema(name = "mustClaim", title = "是否需要签收")
    private boolean mustClaim;

    @Schema(name = "approvalStatus", title = "办理状态:true-完成,false-未完成")
    private int approvalStatus;

    @Schema(name = "name", title = "任务名称")
    private String name;

    @Schema(name = "description", title = "任务描述")
    private String description;

    @Schema(name = "createTime", title = "任务创建时间", example = "2020-12-21 12:22:21")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    @Schema(name = "startTime", title = "审批开始时间", example = "2020-12-21 12:22:21")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime startTime;

    @Schema(name = "userInfos", title = "流程候选用户信息")
    private List<ProcessUserModel> userInfos;

    @Schema(name = "groupInfos", title = "流程候选用户组信息")
    private List<ProcessRoleModel> groupInfos;

    @Schema(name = "assigneeInfo", title = "办理人信息")
    private ProcessUserModel assigneeInfo;
}
