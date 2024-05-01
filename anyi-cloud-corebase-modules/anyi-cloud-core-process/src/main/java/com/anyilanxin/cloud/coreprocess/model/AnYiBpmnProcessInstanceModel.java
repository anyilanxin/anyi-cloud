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

package com.anyilanxin.cloud.coreprocess.model;

import com.anyilanxin.cloud.corecommon.utils.tree.model.BaseTree;
import com.anyilanxin.cloud.coreprocess.emuns.ElementInstanceState;
import com.anyilanxin.cloud.coreprocess.emuns.ProcessInstanceState;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import static com.anyilanxin.cloud.corecommon.constant.CommonCoreConstant.TIME_ZONE_GMT8;

/**
 * bpmn实例渲染model
 *
 * @author zxh
 * @date 2022-01-03 12:58
 * @since 1.0.0
 */
@Getter
@Setter
@ToString
@EqualsAndHashCode
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
public class AnYiBpmnProcessInstanceModel implements Serializable {

    @Serial
    private static final long serialVersionUID = -4821231748877266884L;

    @Schema(title = "流程实例id")
    private String id;

    @Schema(title = "父级流程实例id")
    private String parentId;

    @Schema(title = "流程标题")
    private String anyiProcessTitle;

    @Schema(title = "流程xml转base64后数据")
    private String bpmnBase64;

    @Schema(title = "流程定义key")
    private String bpmnProcessDefinitionKey;

    @Schema(title = "流程名称")
    private String bpmnProcessName;

    @Schema(title = "流程描述文本")
    private String bpmnProcessDescription;

    @Schema(title = "参与人信息")
    private AnYiParticipantModel participant;

    @Schema(title = "流程版本")
    private Integer processVersion;

    @Schema(title = "bpmn流程id")
    private String bpmnProcessId;

    @Schema(title = "流程开始时间", example = "2022-11-10 12:23:23")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = TIME_ZONE_GMT8)
    private LocalDateTime startDate;

    @Schema(title = "流程结束时间", example = "2022-11-10 12:23:23")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = TIME_ZONE_GMT8)
    private LocalDateTime endDate;

    @Schema(title = "流程状态")
    private ProcessInstanceState state;

    @Schema(title = "格式化后时间(最小单位毫秒)")
    private String durationInMillisFormat;

    @Schema(title = "是否完成")
    private int finished;

    @Schema(title = "流程类别")
    private String category;

    @Schema(title = "流程类别名称")
    private String categoryName;

    @Schema(title = "错误信息")
    private String errorMessage;

    @Schema(title = "错误实例key")
    private String incidentId;

    @Schema(title = "是否事件")
    private int incident;

    @Schema(title = "活动实例信息")
    private List<BpmnActivityInstanceModel> activityInstanceList;

    @Getter
    @Setter
    @ToString(callSuper = true)
    @EqualsAndHashCode(callSuper = true)
    @SuperBuilder(toBuilder = true)
    @NoArgsConstructor
    public static class BpmnActivityInstanceModel extends BaseTree<BpmnActivityInstanceModel> implements Serializable {
        @Serial
        private static final long serialVersionUID = 1666533656454L;

        @Schema(title = "活动实例id")
        private String id;

        @Schema(title = "任务id")
        private String taskId;

        @Schema(title = "父级活动实例id")
        private String parentActivityInstanceId;

        @Schema(title = "子流程实例id,或与之关联的流程实例id")
        private String subProcessInstanceId;

        @Schema(title = "活动 bpmn id")
        private String bpmnElementId;

        @Schema(title = "多实例子节点任务id")
        private Set<String> subTaskIds;

        @Schema(title = "多实例子节点实例id")
        private Set<String> subActivityInstanceIds;

        @Schema(title = "流程范围")
        private String flowScopeKey;

        @Schema(title = "元素类型")
        private String bpmnElementType;

        @Schema(title = "活动 bpmn name")
        private String bpmnElementName;

        @Schema(title = " 活动 bpmn 描述")
        private String bpmnElementDescription;

        @Schema(title = "元素实例状态")
        private ElementInstanceState elementState;

        @Schema(title = "多实例数量")
        private int multiInstanceNum;

        @Schema(title = "多实例完成数量")
        private int multiCompleteInstanceNum;

        @Schema(title = "是否多实例,0-不是，1-是。默认0")
        private int multiInstance;

        @Schema(title = "是否事件：0-不是，1-是。默认0")
        private int incident;

        @Schema(title = "错误信息")
        private IncidentInfoModel incidentInfo;

        @Schema(title = "开始时间", example = "2022-11-10 12:23:23")
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = TIME_ZONE_GMT8)
        private LocalDateTime startDate;

        @Schema(title = "结束时间", example = "2022-11-10 12:23:23")
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = TIME_ZONE_GMT8)
        private LocalDateTime endDate;

        @Schema(title = "格式化后时间(最小单位毫秒)")
        private String durationInMillisFormat;

        @Schema(title = "是否完结:0-未完结,1-完结.默认0")
        private int finished;

        @Schema(title = "来源连线bpmn id")
        private Set<String> sourceSequenceFlows;
    }

    @Getter
    @Setter
    @ToString
    @EqualsAndHashCode
    @SuperBuilder(toBuilder = true)
    @NoArgsConstructor
    public static class IncidentInfoModel implements Serializable {
        @Serial
        private static final long serialVersionUID = 1680350688436L;

        @Schema(title = "错误id")
        private String id;

        @Schema(title = "元素实例id")
        private String elementInstanceId;

        @Schema(title = "错误消息")
        private String errorMessage;

        @Schema(title = "job key")
        private String jobKey;

        @Schema(title = "变量范围key")
        private String variableScopeKey;
    }
}
