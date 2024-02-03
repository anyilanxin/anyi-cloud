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

package com.anyilanxin.anyicloud.processadapter.model;

import com.anyilanxin.anyicloud.coreprocess.emuns.ProcessInstanceState;
import com.anyilanxin.anyicloud.coreprocess.emuns.UserTaskState;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 流程回调实体
 *
 * @author zxh
 * @version 1.0
 * @date 2021年11月24日 12:25
 */
@Getter
@Setter
@ToString
@EqualsAndHashCode
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
public class ProcessBackModel implements Serializable {
    @Serial
    private static final long serialVersionUID = 1372639813554080863L;

    /**
     * 流程实例id
     */
    private String processInstanceId;

    /**
     * 流程类别编码
     */
    private String categoryCode;

    /**
     * 表单key
     */
    private String startFormKey;

    /**
     * 流程模型定义key
     */
    private String processDefinitionKey;

    /**
     * 流程定义id
     */
    private String processDefinitionId;

    /**
     * 业务key
     */
    private String businessKey;

    /**
     * 流程发起人
     */
    private String startUserId;

    /**
     * 流程发起人信息
     */
    private AnYiProcessUserModel userInfo;

    /**
     * 开始时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime startTime;

    /**
     * 结束时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime endTime;

    /**
     * 流程状态: 0-审批中,1-审批完成,2-审批拒绝(整个流程未通过完成),3-暂停
     */
    private ProcessInstanceState processState;

    /**
     * 下一个节点信息
     */
    private List<TaskNodeModel> nextTaskList;

    /**
     * 当前审批节点信息
     */
    private List<TaskNodeModel> approveTaskList;

    @Getter
    @Setter
    @ToString
    @EqualsAndHashCode
    @SuperBuilder(toBuilder = true)
    @NoArgsConstructor
    public static class TaskNodeModel implements Serializable {
        @Serial
        private static final long serialVersionUID = 1372639813554080863L;

        /**
         * 节点特殊标识
         */
        private String specialLabel;

        /**
         * 流程任务id
         */
        private String taskId;

        /**
         * 父级任务id
         */
        private String parentTaskId;

        /**
         * 任务名称
         */
        private String taskName;

        /**
         * 任务节点key
         */
        private String taskDefinitionKey;

        /**
         * 任务描述
         */
        private String description;

        /**
         * 委托办理人id
         */
        private String owner;

        /**
         * 委托办理人名称
         */
        private String ownerName;

        /**
         * 办理人
         */
        private String assignee;

        /**
         * 办理人名称
         */
        private String assigneeName;

        /**
         * 审批用户信息
         */
        private AnYiProcessUserModel userInfo;

        /**
         * 任务开始时间
         */
        private LocalDateTime startTime;

        /**
         * 任务结束时间
         */
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        private LocalDateTime endTime;

        /**
         * 任务审批意见
         */
        private String approvalOption;

        /**
         * 表单key
         */
        private String taskFormKey;

        /**
         * 审批任务状态：0-待审批,1-通过,2-不通过，3-未审批跳过,默认0，具体与UserTaskState一致
         */
        private UserTaskState taskState;
    }
}
