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
import com.anyilanxin.cloud.processadapter.model.CustomIdentityLink;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

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
public class WaitTaskPageDto implements Serializable {
    @Serial
    private static final long serialVersionUID = -9172543358400492801L;

    @Schema(name = "dueTime", title = "跟进日期(支持排序)", example = "2020-12-21 12:22:21")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    protected Date dueTime;

    @Schema(name = "id", title = "执行id")
    private String id;

    @Schema(name = "followUpTime", title = "到期日期(支持排序)", example = "2020-12-21 12:22:21")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    protected Date followUpTime;

    @Schema(name = "processTitle", title = "流程标题(支持排序)")
    private String processTitle;

    @Schema(name = "processName", title = "流程名称")
    private String processName;

    @Schema(name = "applyTime", title = "提交申请时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    protected Date applyTime;

    @Schema(name = "processCategory", title = "流程类型(支持排序)")
    private String processCategory;

    @Schema(name = "processCategoryName", title = "流程类型名称")
    private String processCategoryName;

    @Schema(name = "applyUserName", title = "流程申请人(支持排序)")
    private String applyUserName;

    @Schema(name = "applyUserInfo", title = "流程申请人完整信息")
    private AnYiProcessUserModel applyUserInfo;

    @Schema(name = "createTime", title = "创建时间(支持排序)")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    protected Date createTime;

    @Schema(name = "taskName", title = "任务名称(支持排序)")
    private String taskName;

    @Schema(name = "priority", title = "优先级(支持排序)")
    private Integer priority;

    @Schema(name = "description", title = "任务描述")
    private String description;

    @Schema(name = "taskId", title = "任务id")
    private String taskId;

    @Schema(name = "parentTaskId", title = "父级任务id")
    private String parentTaskId;

    @Schema(name = "businessKey", title = "业务id")
    private String businessKey;

    @Schema(name = "processInstanceId", title = "流程实例id")
    private String processInstanceId;

    @Schema(name = "processDefinitionId", title = "流程定义id")
    private String processDefinitionId;

    @Schema(name = "taskDefinitionKey", title = "任务定义id")
    private String taskDefinitionKey;

    @Schema(name = "claim", title = "是否签收")
    private boolean claim;

    @Schema(name = "enableUnClaim", title = "是否可以归还")
    private boolean enableUnClaim;

    @Schema(name = "identityLinks", title = "审批用户信息")
    private List<CustomIdentityLink> customIdentityLinks;

    @Schema(name = "suspensionState", title = "暂停状态:1-活动,2-暂停，与SuspensionState一致")
    private Integer suspensionState;

    @Schema(name = "delegationStates", title = "委托状态:0-未委托,1-等待,2-已处理")
    private int delegationStates;

    @Schema(name = "taskTitle", title = "任务标题")
    private String taskTitle;
}
