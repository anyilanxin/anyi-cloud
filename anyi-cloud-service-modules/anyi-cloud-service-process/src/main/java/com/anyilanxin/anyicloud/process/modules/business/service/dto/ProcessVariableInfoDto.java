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

import com.anyilanxin.anyicloud.process.extend.model.AnYiProcessPropertyModel;
import com.anyilanxin.anyicloud.process.extend.model.AnYiUserTaskPropertyModel;
import com.anyilanxin.anyicloud.processadapter.model.AnYiProcessUserModel;
import com.anyilanxin.anyicloud.processadapter.model.ApprovalInfoModel;
import com.anyilanxin.anyicloud.processadapter.model.ProcessFormModel;
import com.anyilanxin.anyicloud.processadapter.model.ProcessRoleModel;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.io.Serial;
import java.io.Serializable;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * 流程变量信息
 *
 * @author zxh
 * @date 2022-01-28 19:11
 * @since 1.0.0
 */
@Getter
@Setter
@ToString
@EqualsAndHashCode
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
public class ProcessVariableInfoDto implements Serializable {

    @Serial
    private static final long serialVersionUID = 7446997920585324466L;

    @Schema(name = "processProperty", title = "流程扩展属性信息")
    private AnYiProcessPropertyModel processProperty;

    @Schema(name = "applyUserInfo", title = "流程申请人完整信息")
    private AnYiProcessUserModel applyUserInfo;

    @Schema(name = "processCategory", title = "流程类型")
    private String processCategory;

    @Schema(name = "processCategoryName", title = "流程类型名称")
    private String processCategoryName;

    @Schema(name = "startFormData", title = "流程开始表单数据")
    private ProcessFormModel startFormData;

    @Schema(name = "startTitle", title = "流程发起标题")
    private String startTitle;

    @Schema(name = "taskVariableInfos", title = "流程任务变量")
    private Map<String, TaskVariableInfoDto> taskVariableInfos;

    @Getter
    @Setter
    @ToString
    @EqualsAndHashCode
    @SuperBuilder(toBuilder = true)
    @NoArgsConstructor
    public static class TaskVariableInfoDto implements Serializable {
        @Serial
        private static final long serialVersionUID = 7446997920585324466L;

        @Schema(name = "taskType", title = "任务类型，与TaskType一致")
        private String taskType;

        @Schema(name = "taskTypeDescribe", title = "任务类型描述，与TaskType描述信息一致")
        private String taskTypeDescribe;

        @Schema(name = "assignee", title = "审批人")
        protected String assignee;

        @Schema(name = "assigneeUserInfo", title = "审批人完整信息")
        private AnYiProcessUserModel assigneeUserInfo;

        @Schema(name = "owner", title = "委托办理人")
        protected String owner;

        @Schema(name = "ownerUserInfo", title = "委托办理人完整信息")
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

        @Schema(name = "formModel", title = "任务表单")
        protected ProcessFormModel formModel;

        @Schema(name = "approvalStatus", title = "审批状态,与UserTaskState一致")
        private Integer approvalStatus;

        @Schema(name = "optionInfos", title = "审批意见信息")
        private ApprovalInfoModel optionInfos;

        @Schema(name = "allCommentStr", title = "所有审批意见")
        private String allCommentStr;
    }
}
