/*
 * Copyright (c) 2023-present ZHOUXUANHONG(安一老厨)<anyilanxin@aliyun.com>
 *
 * AnYi Cloud Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * AnYi Cloud 采用APACHE LICENSE 2.0开源协议，您在使用过程中，需要注意以下几点：
 *   1.请不要删除和修改根目录下的LICENSE.txt文件；
 *   2.请不要删除和修改 AnYi Cloud 源码头部的版权声明；
 *   3.请保留源码和相关描述文件的项目出处，作者声明等；
 *   4.分发源码时候，请注明软件出处 https://github.com/anyilanxin/anyi-cloud；
 *   5.在修改包名，模块名称，项目代码等时，请注明软件出处 https://github.com/anyilanxin/anyi-cloud；
 *   6.本软件不允许在国家法律规定范围外使用，如出现违法行为原作者本人不承担任何法律风险；
 *   7.进行商用时，不得基于AnYi Cloud的基础，修改包装而成一个与AnYi Cloud EE、AnYi Zeebe EE、AnYi Standalone EE功能类似的程序，进行销售或发布，参与同类软件产品市场的竞争；
 *   8.本软件使用的第三方依赖皆为开源软件，如需要修改第三方源码请遵循第三方源码附带开源协议；
 *   9.本软件流程部分请遵循camunda开源协议：
 *     https://docs.camunda.org/manual/latest/introduction/third-party-libraries
 *     https://github.com/camunda/camunda-bpm-platform/blob/master/LICENSE
 *   10.若您的项目无法满足以上几点，可申请商业授权。
 */

package com.anyilanxin.cloud.processadapter.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.io.Serial;
import java.io.Serializable;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * 活动实例信息(用于渲染流程图)
 *
 * @author zxh
 * @date 2021-12-29 11:56
 * @since 1.0.0
 */
@Getter
@Setter
@ToString
@EqualsAndHashCode
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
public class ActInstanceModel implements Serializable, Comparable<ActInstanceModel> {
    @Serial
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
    private AnYiProcessUserModel taskAssigneeInfo;

    @Schema(name = "candidateUsers", title = "流程候选用户信息")
    @Builder.Default
    private List<AnYiProcessUserModel> candidateUsers = Collections.emptyList();

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

    @Schema(name = "approvalStatus", title = "审批状态,与UserTaskState一致")
    private Integer approvalStatus;

    @Schema(name = "optionInfos", title = "审批意见信息")
    private ApprovalInfoModel optionInfos;

    @Schema(name = "owner", title = "委托办理人")
    private String owner;

    @Schema(name = "ownerUserInfo", title = "委托办理人信息")
    private AnYiProcessUserModel ownerUserInfo;

    @Override
    public int compareTo(ActInstanceModel o) {
        return Long.compare(this.sequenceCounter, o.sequenceCounter);
        // if (Objects.isNull(o.getEndTime()) && Objects.isNull(this.getEndTime())) {
        // return Long.compare(this.sequenceCounter, o.sequenceCounter);
        // } else if (Objects.isNull(o.getEndTime())) {
        // return -1;
        // } else if (Objects.isNull(this.getEndTime())) {
        // return 1;
        // }
        // return Long.compare(this.getEndTime().getTime() + this.sequenceCounter,
        // o.getEndTime().getTime() + o.sequenceCounter);
    }
}
