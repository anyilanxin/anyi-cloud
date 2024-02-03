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

import com.anyilanxin.anyicloud.corecommon.utils.tree.model.BaseTree;
import com.anyilanxin.anyicloud.process.extend.constant.impl.ProcessInstanceState;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 流程act以及模型信息
 *
 * @author zxh
 * @date 2022-01-07 07:25
 * @since 1.0.0
 */
@Getter
@Setter
@ToString
@EqualsAndHashCode
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
public class BpmnInstanceInfoDto implements Serializable {
    @Serial
    private static final long serialVersionUID = -3317408065181369306L;

    @Schema(name = "bpmnBase64", title = "bpmn blob文件(base64编码)")
    private String bpmnBase64;

    @Schema(name = "id", title = "流程实例id")
    private String id;

    @Schema(name = "processName", title = "模型名称")
    private String processName;

    @Schema(name = "bpmnProcessId", title = "bpmnId")
    private String bpmnProcessId;

    @Schema(name = "processDefinitionId", title = "流程定义id")
    private String processDefinitionId;

    @Schema(name = "categoryName", title = "当前模型类型名称")
    private String categoryName;

    @Schema(name = "businessKey", title = "流程业务key")
    private String businessKey;

    @Schema(name = "finishState", title = "完成状态:0-未完成,1-完成")
    private Integer finishState;

    @Schema(name = "processState", title = "1-活动,2-挂起,3-完成,4-拒绝(特权，直接取消流程),5-作废,6-外部终止,7-内部终止,具体参考ProcessInstanceState")
    private ProcessInstanceState processState;

    @Schema(name = "suspended", title = "是否挂起")
    private boolean suspended;

    @Schema(name = "durationInMillisFormat", title = "消耗时间")
    private String durationInMillisFormat;

    @Schema(name = "processStartTime", title = "流程发起时间", example = "2020-12-21 12:22:21")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime processStartTime;

    @Schema(name = "processEndTime", title = "流程结束时间", example = "2020-12-21 12:22:21")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime processEndTime;

    @Schema(name = "startTitle", title = "申请标题")
    private String startTitle;

    @Schema(name = "activityInstanceList", title = "流程实例信息(用于选择树渲染)")
    private List<ActTreeInstanceDto> activityInstanceList;

    @Schema(name = "currentActivityInstanceMap", title = "最近活动实例信息(用于bpmn渲染)")
    private Map<String, ActSimpleInstanceDto> currentActivityInstanceMap;

    @Getter
    @Setter
    @ToString
    @EqualsAndHashCode(callSuper = false)
    @SuperBuilder(toBuilder = true)
    @NoArgsConstructor
    public static class ActTreeInstanceDto extends BaseTree<ActTreeInstanceDto> implements Serializable {
        @Serial
        private static final long serialVersionUID = -3317408065181369306L;

        @Schema(name = "id", title = "活动实例唯一id")
        private String id;

        @Schema(name = "parentActivityInstanceId", title = "父级活动实例id")
        private String parentActivityInstanceId;

        @Schema(name = "bpmnActivityId", title = "活动id")
        private String bpmnActivityId;

        @Schema(name = "bpmnActivityName", title = "活动名称")
        private String bpmnActivityName;

        @Schema(name = "activityType", title = "活动类型,与org.camunda.bpm.engine.ActivityTypes一致")
        private String activityType;

        @Schema(name = "state", title = "活动状态:0-默认,1-整个范围结束(一般用在结束任务标识整个流程结束),2-取消,3-开始,4-结束,默认0,具体参考引擎ActivityInstanceState类,当为null时说明任务还未执行")
        private int state;

        @Schema(name = "multiInstanceNum", title = "多实例数量")
        private Long multiInstanceNum;

        @Schema(name = "multiCompleteInstanceNum", title = "多实例数量(完成)")
        private Long multiCompleteInstanceNum;

        @Schema(name = "multiInstance", title = "多实例")
        private boolean multiInstance;

        @Schema(name = "taskId", title = "任务id")
        private String taskId;

        @Schema(name = "durationInMillisFormat", title = "消耗时间(格式化值)")
        private String durationInMillisFormat;

        @Schema(name = "startTime", title = "开始时间")
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        private Date startTime;

        @Schema(name = "endTime", title = "结束时间")
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        private Date endTime;

        @Schema(name = "sequenceCounter", title = "序列计数器")
        private long sequenceCounter;

        @Schema(name = "taskTitle", title = "任务标题")
        private String taskTitle;
    }

    @Getter
    @Setter
    @ToString
    @EqualsAndHashCode(callSuper = false)
    @SuperBuilder(toBuilder = true)
    @NoArgsConstructor
    public static class ActSimpleInstanceDto implements Serializable {
        @Serial
        private static final long serialVersionUID = -3317408065181369306L;

        @Schema(name = "id", title = "活动实例唯一id")
        private String id;

        @Schema(name = "parentActivityInstanceId", title = "父级活动实例id")
        private String parentActivityInstanceId;

        @Schema(name = "bpmnActivityId", title = "活动id")
        private String bpmnActivityId;

        @Schema(name = "bpmnActivityName", title = "活动名称")
        private String bpmnActivityName;

        @Schema(name = "activityType", title = "活动类型,与org.camunda.bpm.engine.ActivityTypes一致")
        private String activityType;

        @Schema(name = "state", title = "活动状态:0-默认,1-整个范围结束(一般用在结束任务标识整个流程结束),2-取消,3-开始,4-结束,默认0,具体参考引擎ActivityInstanceState类,当为null时说明任务还未执行")
        private int state;

        @Schema(name = "multiInstanceNum", title = "多实例数量")
        private Long multiInstanceNum;

        @Schema(name = "multiCompleteInstanceNum", title = "多实例数量(完成)")
        private Long multiCompleteInstanceNum;

        @Schema(name = "multiInstance", title = "多实例")
        private boolean multiInstance;

        @Schema(name = "taskId", title = "任务id")
        private String taskId;

        @Schema(name = "durationInMillisFormat", title = "消耗时间(格式化值)")
        private String durationInMillisFormat;

        @Schema(name = "startTime", title = "开始时间")
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        private Date startTime;

        @Schema(name = "endTime", title = "结束时间")
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        private Date endTime;

        @Schema(name = "sequenceCounter", title = "序列计数器")
        private long sequenceCounter;

        @Schema(name = "taskTitle", title = "任务标题")
        private String taskTitle;

        @Schema(title = "活动连线信息<连线id,连线信息>")
        private Map<String, SequenceFlowDto> currentSequenceMap;
    }

    @Getter
    @Setter
    @ToString
    @EqualsAndHashCode
    @SuperBuilder(toBuilder = true)
    @NoArgsConstructor
    @Schema
    public static class SequenceFlowDto implements Serializable {
        @Serial
        private static final long serialVersionUID = -96142459317027820L;

        @Schema(name = "targetActivityInstanceId", title = "目标活动实例id")
        private String targetActivityInstanceId;

        @Schema(name = "targetBpmnActivityId", title = "目标活动id")
        private String targetBpmnActivityId;

        @Schema(name = "targetActivityState", title = "连线目标活动状态")
        private Integer targetActivityState;

        @Schema(name = "bpmnSequenceFlowId", title = "连线bpmn id")
        private String bpmnSequenceFlowId;
    }
}
