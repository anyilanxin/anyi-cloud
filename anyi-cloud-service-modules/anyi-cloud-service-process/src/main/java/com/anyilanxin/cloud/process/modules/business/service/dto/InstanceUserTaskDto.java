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

package com.anyilanxin.cloud.process.modules.business.service.dto;

import com.anyilanxin.cloud.process.extend.model.AnYiUserTaskPropertyModel;
import com.anyilanxin.cloud.processadapter.model.AnYiProcessUserModel;
import com.anyilanxin.cloud.processadapter.model.ApprovalInfoModel;
import com.anyilanxin.cloud.processadapter.model.ProcessFormModel;
import com.anyilanxin.cloud.processadapter.model.ProcessRoleModel;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.io.Serial;
import java.io.Serializable;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * 审批已经
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
public class InstanceUserTaskDto implements Serializable, Comparable<InstanceUserTaskDto> {
    @Serial
    private static final long serialVersionUID = -9172543358400492801L;

    @Schema(name = "id", title = "活动实例id")
    protected String id;

    @Schema(name = "taskId", title = "任务id")
    protected String taskId;

    @Schema(name = "assignee", title = "审批人")
    protected String assignee;

    @Schema(name = "assigneeUserInfo", title = "审批人信息")
    private AnYiProcessUserModel assigneeUserInfo;

    @Schema(name = "owner", title = "委托办理人")
    protected String owner;

    @Schema(name = "ownerUserInfo", title = "委托办理人信息")
    private AnYiProcessUserModel ownerUserInfo;

    @Schema(name = "handleType", title = "任务操作类型，与TaskHandleType一致")
    private String handleType;

    @Schema(name = "handleTypeDescribe", title = "任务操作类型描述，与TaskHandleType一致")
    private String handleTypeDescribe;

    @Schema(name = "taskTitle", title = "任务标题")
    private String taskTitle;

    @Schema(name = "propertyModel", title = "节点信息扩展属性信息")
    private AnYiUserTaskPropertyModel propertyModel;

    @Schema(name = "candidateUsers", title = "流程候选用户信息")
    @Builder.Default
    private List<AnYiProcessUserModel> candidateUsers = Collections.emptyList();

    @Schema(name = "candidateGroups", title = "流程候选用户组信息")
    @Builder.Default
    private List<ProcessRoleModel> candidateGroups = Collections.emptyList();

    @Schema(name = "name", title = "任务名称")
    protected String name;

    @Schema(name = "description", title = "任务描述")
    protected String description;

    @Schema(name = "formModel", title = "任务表单")
    protected ProcessFormModel formModel;

    @Schema(name = "dueDate", title = "到期日期")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    protected Date dueDate;

    @Schema(name = "followUpDate", title = "跟进日期")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    protected Date followUpDate;

    @Schema(name = "priority", title = "优先级")
    protected int priority;

    @Schema(name = "parentTaskId", title = "父级任务id")
    protected String parentTaskId;

    @Schema(name = "taskDefinitionKey", title = "任务定义id")
    protected String taskDefinitionKey;

    @Schema(name = "tenantId", title = "租户id")
    protected String tenantId;

    @Schema(name = "durationInMillis", title = "消耗时间")
    protected Long durationInMillis;

    @Schema(name = "durationInMillisFormat", title = "消耗时间(格式化值)")
    protected String durationInMillisFormat;

    @Schema(name = "startTime", title = "开始时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    protected Date startTime;

    @Schema(name = "endTime", title = "结束时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    protected Date endTime;

    @Schema(name = "dueDateStr", title = "到期日期(模型原始值)")
    protected String dueDateStr;

    @Schema(name = "dueFormatDate", title = "到期日期格式化值(模型原始值)")
    protected String dueFormatDate;

    @Schema(name = "followUpDateStr", title = "跟进日期(模型原始值)")
    protected String followUpDateStr;

    @Schema(name = "followUpFormatDate", title = "跟进时间格式化值(模型原始值)")
    protected String followUpFormatDate;

    @Schema(name = "activityInstanceId", title = "活动实例id")
    protected String activityInstanceId;

    @Schema(name = "activityId", title = "活动id")
    protected String activityId;

    @Schema(name = "activityInstanceState", title = "任务活动状态:0-默认,1-整个范围结束(一般用在结束任务标识整个流程结束),2-取消,3-开始,4-结束,默认0,具体参考引擎ActivityInstanceState类,当为null时说明任务还未执行")
    protected Integer activityInstanceState;

    @Schema(name = "parentActivityInstanceId", title = "父级活动实例id")
    protected String parentActivityInstanceId;

    @Schema(name = "approvalStatus", title = "审批状态,与UserTaskState一致")
    private Integer approvalStatus;

    @Schema(name = "optionInfos", title = "审批意见信息")
    private ApprovalInfoModel optionInfos;

    @Schema(name = "allCommentStr", title = "所有审批意见")
    private String allCommentStr;

    @Schema(name = "sequenceCounter", title = "任务序列计数")
    protected long sequenceCounter;

    @Schema(name = "multiInstance", title = "是否多实例")
    private boolean multiInstance;

    @Schema(name = "multiInstanceTasks", title = "多实例明细")
    @Builder.Default
    private List<InstanceUserTaskDto> multiInstanceTasks = Collections.emptyList();

    @Override
    public int compareTo(InstanceUserTaskDto o) {
        long nowSequenceCounter = Objects.nonNull(this.startTime) ? (this.sequenceCounter + this.startTime.getTime()) : this.sequenceCounter;
        long currentSequenceCounter = Objects.nonNull(o.startTime) ? (o.sequenceCounter + o.startTime.getTime()) : o.sequenceCounter;
        return Long.compare(nowSequenceCounter, currentSequenceCounter);
    }
}
