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

package com.anyilanxin.cloud.process.utils;

import com.alibaba.fastjson2.JSONObject;
import com.anyilanxin.cloud.coreprocess.model.task.SubmitApprovalModel;
import com.anyilanxin.cloud.process.extend.constant.impl.TaskHandleType;
import com.anyilanxin.cloud.process.extend.constant.impl.TaskType;
import com.anyilanxin.cloud.process.extend.constant.impl.TaskVariableType;
import org.apache.commons.lang3.StringUtils;
import org.camunda.bpm.engine.HistoryService;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.delegate.DelegateTask;
import org.camunda.bpm.engine.history.HistoricVariableInstance;
import org.camunda.bpm.engine.variable.value.IntegerValue;

import java.util.Objects;

/**
 * task工具类
 *
 * @author zxh
 * @date 2021-12-12 00:04
 * @since 1.0.0
 */
public class TaskBaseUtils {

    /**
     * 提交流程审批状态以及审批意见
     *
     * @param taskService       ${@link TaskService}
     * @param taskId            ${@link String}
     * @param processInstanceId ${@link String}
     * @param model             ${@link SubmitApprovalModel}
     * @author zxh
     * @date 2021-12-12 00:24
     */
    public static void setApprovalAndComment(TaskService taskService, String taskId, String processInstanceId, SubmitApprovalModel model) {
        setComment(taskService, taskId, processInstanceId, model.getApprovalOpinion());
        setApprovalStatus(taskService, taskId, model);
    }


    /**
     * 获取某个任务的审批状态
     *
     * @param taskService ${@link TaskService}
     * @param taskId      ${@link String}
     * @return Integer ${@link Integer}
     * @author zxh
     * @date 2021-12-12 00:24
     */
    public static Integer getApprovalStatus(TaskService taskService, String taskId) {
        IntegerValue instanceStatus = taskService.getVariableLocalTyped(taskId, TaskVariableType.NR_OF_INSTANCE_STATUS.getVariableKey());
        if (Objects.nonNull(instanceStatus)) {
            return instanceStatus.getValue();
        }
        return null;
    }


    /**
     * 获取某个任务的审批状态
     *
     * @param historyService ${@link HistoryService}
     * @param taskId         ${@link String}
     * @return Integer ${@link Integer}
     * @author zxh
     * @date 2021-12-12 00:24
     */
    public static Integer getApprovalStatus(HistoryService historyService, String taskId) {
        HistoricVariableInstance historicVariableInstance = historyService.createHistoricVariableInstanceQuery().taskIdIn(taskId).variableName(TaskVariableType.NR_OF_INSTANCE_STATUS.getVariableKey()).singleResult();
        Object instanceStatus = historicVariableInstance.getValue();
        if (Objects.nonNull(instanceStatus)) {
            return (Integer) instanceStatus;
        }
        return null;
    }


    /**
     * 提交流程审批状态以及审批意见
     *
     * @param taskService ${@link TaskService}
     * @param taskId      ${@link String}
     * @param model       ${@link SubmitApprovalModel}
     * @author zxh
     * @date 2021-12-12 00:24
     */
    public static void setApprovalStatus(TaskService taskService, String taskId, SubmitApprovalModel model) {
        taskService.setVariableLocal(taskId, TaskVariableType.NR_OF_INSTANCE_STATUS.getVariableKey(), model.getApprovalStatus());
        taskService.setVariableLocal(taskId, TaskVariableType.NR_OF_INSTANCE_APPROVE_INFO.getVariableKey(), JSONObject.toJSONString(model));
    }


    /**
     * 提交流程审批状态以及审批意见
     *
     * @param delegateTask ${@link DelegateTask}
     * @param model        ${@link SubmitApprovalModel}
     * @author zxh
     * @date 2021-12-12 00:24
     */
    public static void setApprovalStatus(DelegateTask delegateTask, SubmitApprovalModel model) {
        delegateTask.setVariableLocal(TaskVariableType.NR_OF_INSTANCE_STATUS.getVariableKey(), model.getApprovalStatus());
        delegateTask.setVariableLocal(TaskVariableType.NR_OF_INSTANCE_APPROVE_INFO.getVariableKey(), JSONObject.toJSONString(model));
    }


    /**
     * 提交流程审批意见
     *
     * @param taskService       ${@link TaskService}
     * @param taskId            ${@link String}
     * @param processInstanceId ${@link String}
     * @param comment           ${@link String}
     * @author zxh
     * @date 2021-12-12 00:25
     */
    public static void setComment(TaskService taskService, String taskId, String processInstanceId, String comment) {
        if (StringUtils.isNotBlank(taskId) && StringUtils.isNotBlank(comment)) {
            taskService.createComment(taskId, processInstanceId, comment);
        }
    }


    /**
     * 设置任务标题
     *
     * @param taskService ${@link TaskService}
     * @param taskId      ${@link String}
     * @param title       ${@link String}
     * @author zxh
     * @date 2021-12-12 00:25
     */
    public static void setTaskTitle(TaskService taskService, String taskId, String title) {
        if (StringUtils.isNotBlank(taskId) && StringUtils.isNotBlank(title)) {
            taskService.setVariableLocal(taskId, TaskVariableType.TASK_TITLE.getType(), title);
        }
    }


    /**
     * 设置任务操作类型
     *
     * @param taskService ${@link TaskService}
     * @param taskId      ${@link String}
     * @param handleType  ${@link TaskHandleType}
     * @author zxh
     * @date 2021-12-12 00:25
     */
    public static void setTaskHandleType(TaskService taskService, String taskId, TaskHandleType handleType) {
        if (StringUtils.isNotBlank(taskId) && Objects.nonNull(handleType)) {
            taskService.setVariableLocal(taskId, TaskVariableType.NR_OF_INSTANCE_HANDLE_TYPE.getType(), handleType.getType());
        }
    }


    /**
     * 设置任务操作类型
     *
     * @param delegateTask ${@link DelegateTask}
     * @param handleType   ${@link TaskHandleType}
     * @author zxh
     * @date 2021-12-12 00:25
     */
    public static void setTaskHandleType(DelegateTask delegateTask, TaskHandleType handleType) {
        if (Objects.nonNull(handleType)) {
            delegateTask.setVariableLocal(TaskVariableType.NR_OF_INSTANCE_HANDLE_TYPE.getType(), handleType.getType());
        }
    }


    /**
     * 设置任务类型
     *
     * @param taskService ${@link TaskService}
     * @param taskId      ${@link String}
     * @param taskType    ${@link TaskType}
     * @author zxh
     * @date 2021-12-12 00:25
     */
    public static void setTaskType(TaskService taskService, String taskId, TaskType taskType) {
        if (StringUtils.isNotBlank(taskId) && Objects.nonNull(taskType)) {
            taskService.setVariableLocal(taskId, TaskVariableType.TASK_TYPE.getVariableKey(), taskType.getValue());
        }
    }


    /**
     * 设置任务类型
     *
     * @param delegateTask ${@link DelegateTask}
     * @param taskType     ${@link TaskType}
     * @author zxh
     * @date 2021-12-12 00:25
     */
    public static void setTaskType(DelegateTask delegateTask, TaskType taskType) {
        if (Objects.nonNull(taskType)) {
            delegateTask.setVariableLocal(TaskVariableType.TASK_TYPE.getVariableKey(), taskType.getValue());
        }
    }

}
