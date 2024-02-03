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

package com.anyilanxin.anyicloud.process.modules.business.service.dto;

import com.anyilanxin.anyicloud.processadapter.model.AnYiProcessUserModel;
import com.anyilanxin.anyicloud.processadapter.model.ApprovalInfoModel;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

/**
 * 任务列表
 *
 * @author zxh
 * @date 2020-10-19 19:40
 * @since 1.0.0
 */
@Getter
@Setter
@ToString
@EqualsAndHashCode
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
public class HistoryTaskPageDto implements Serializable {
    @Serial
    private static final long serialVersionUID = -9172543358400492801L;

    @Schema(name = "dueTime", title = "跟进日期(支持排序)", example = "2020-12-21 12:22:21")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    protected Date dueDate;

    @Schema(name = "id", title = "执行id")
    private String id;

    @Schema(name = "followUpTime", title = "到期日期(支持排序)", example = "2020-12-21 12:22:21")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    protected Date followUpDate;

    @Schema(name = "processTitle", title = "流程标题")
    private String processTitle;

    @Schema(name = "parentTaskId", title = "父级任务id")
    private String parentTaskId;

    @Schema(name = "processName", title = "流程名称")
    private String processName;

    @Schema(name = "applyUserName", title = "流程申请人")
    private String applyUserName;

    @Schema(name = "applyTime", title = "提交申请时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    protected Date applyTime;

    @Schema(name = "applyUserInfo", title = "流程申请人完整信息")
    private AnYiProcessUserModel applyUserInfo;

    @Schema(name = "processCategory", title = "流程类型")
    private String processCategory;

    @Schema(name = "processCategoryName", title = "流程类型名称")
    private String processCategoryName;

    @Schema(name = "durationInMillis", title = "消耗时间")
    protected Long durationInMillis;

    @Schema(name = "durationInMillisFormat", title = "消耗时间")
    protected String durationInMillisFormat;

    @Schema(name = "startTime", title = "开始时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    protected Date startTime;

    @Schema(name = "endTime", title = "结束时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    protected Date endTime;

    @Schema(name = "category", title = "流程类型")
    private String category;

    @Schema(name = "categoryName", title = "流程类型名称")
    private String categoryName;

    @Schema(name = "taskName", title = "任务名称(支持排序)")
    private String taskName;

    @Schema(name = "priority", title = "优先级(支持排序)")
    private Integer priority;

    @Schema(name = "description", title = "任务描述")
    private String description;

    @Schema(name = "taskId", title = "任务id")
    private String taskId;

    @Schema(name = "businessKey", title = "业务id")
    private String businessKey;

    @Schema(name = "processInstanceId", title = "流程实例id")
    private String processInstanceId;

    @Schema(name = "processDefinitionId", title = "流程定义id")
    private String processDefinitionId;

    @Schema(name = "taskDefinitionKey", title = "任务定义id")
    private String taskDefinitionKey;

    @Schema(name = "taskTitle", title = "任务标题")
    private String taskTitle;

    @Schema(name = "processState", title = "1-活动,2-挂起,3-完成,4-拒绝(特权，直接取消流程),5-作废,6-外部终止,7-内部终止,具体参考ProcessInstanceState")
    private Integer processState;

    @Schema(name = "approvalStatus", title = "审批状态,与UserTaskState一致")
    private Integer approvalStatus;

    @Schema(name = "optionInfos", title = "审批意见信息")
    private ApprovalInfoModel optionInfos;

    @Schema(name = "allCommentStr", title = "所有审批意见")
    private String allCommentStr;
}
