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

import com.anyilanxin.cloud.processadapter.model.AnYiProcessUserModel;
import com.anyilanxin.cloud.processadapter.model.ApprovalInfoModel;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Map;

/**
 * @author zxh
 * @date 2021-08-02 17:16
 * @since 1.0.0
 */
@Getter
@Setter
@ToString
@EqualsAndHashCode
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
public class ProcessPageDto implements Serializable {
    @Serial
    private static final long serialVersionUID = 7446997920585324466L;

    @Schema(name = "durationInMillis", title = "消耗时间(支持排序)")
    private Long durationInMillis;

    @Schema(name = "durationInMillisFormat", title = "消耗时间(格式化值)")
    private String durationInMillisFormat;

    @Schema(name = "processStartTime", title = "流程发起时间(支持排序)", example = "2020-12-21 12:22")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime processStartTime;

    @Schema(name = "processEndTime", title = "流程结束时间(支持排序)", example = "2020-12-21 12:22")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime processEndTime;

    @Schema(name = "processTitle", title = "流程发起标题")
    private String processTitle;

    @Schema(name = "processCategory", title = "流程类型")
    private String processCategory;

    @Schema(name = "processCategoryName", title = "流程类型名称")
    private String processCategoryName;

    @Schema(name = "processName", title = "流程名称")
    private String processName;

    @Schema(name = "businessKey", title = "业务id")
    private String businessKey;

    @Schema(name = "processInstanceId", title = "流程实例id")
    private String processInstanceId;

    @Schema(name = "tenantId", title = "租户id")
    private String tenantId;

    @Schema(name = "finishState", title = "完成状态:0-未完成,1-完成")
    private Integer finishState;

    @Schema(name = "processState", title = "1-活动,2-挂起,3-完成,4-拒绝(特权，直接取消流程),5-作废,6-外部终止,7-内部终止,具体参考ProcessInstanceState")
    private Integer processState;

    @Schema(name = "deleteReason", title = "删除原因,当外部终止时使用，completed-完成，invalid-作废,refused-拒绝")
    protected String deleteReason;

    @Schema(name = "startFormData", title = "流程开始表单数据")
    private Map<String, Object> startFormData;

    @Schema(name = "applyUserInfo", title = "流程申请人完整信息")
    private AnYiProcessUserModel applyUserInfo;

    @Schema(name = "currentActivityName", title = "当前活动名称")
    private String currentActivityName;

    @Schema(name = "currentActivityType", title = "当前活动类型")
    private String currentActivityType;

    @Schema(name = "currentAssignee", title = "当前活动处理人")
    private String currentAssignee;

    @Schema(name = "currentTaskId", title = "当前活动任务id")
    private String currentTaskId;

    @Schema(name = "currentStartTime", title = "当前活动开始时间", example = "2020-12-21 12:22")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private Date currentStartTime;

    @Schema(name = "currentEndTime", title = "当前活动结束时间", example = "2020-12-21 12:22")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private Date currentEndTime;

    @Schema(name = "currentApprovalStatus", title = "最近审批状态,与UserTaskState一致")
    private Integer currentApprovalStatus;

    @Schema(name = "currentTaskType", title = "最近任务类型，与TaskType一致")
    private String currentTaskType;

    @Schema(name = "currentOptionInfo", title = "最近审批意见信息")
    private ApprovalInfoModel currentOptionInfo;

    @Schema(name = "currentDurationInMillis", title = "当前活动消耗时间(s)")
    private Long currentDurationInMillis;

    @Schema(name = "currentDurationInMillisFormat", title = "当前活动消耗时间(格式化值)")
    private String currentDurationInMillisFormat;

    @Schema(name = "currentId", title = "当前活动id")
    private String currentId;
}
