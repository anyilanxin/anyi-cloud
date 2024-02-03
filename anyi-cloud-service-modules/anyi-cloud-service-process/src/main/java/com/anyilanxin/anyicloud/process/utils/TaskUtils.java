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

package com.anyilanxin.anyicloud.process.utils;

import com.anyilanxin.anyicloud.coreprocess.model.AnYiProcessSimpleUserModel;
import com.anyilanxin.anyicloud.coreprocess.model.task.SubmitApprovalModel;
import com.anyilanxin.anyicloud.process.extend.constant.impl.TaskHandleType;
import com.anyilanxin.anyicloud.process.extend.constant.impl.TaskType;
import com.anyilanxin.anyicloud.process.extend.constant.impl.TaskVariableType;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.impl.persistence.entity.TaskEntity;
import org.camunda.bpm.engine.task.Task;
import org.springframework.stereotype.Component;

/**
 * 流程任务工具
 *
 * @author zxh
 * @date 2021-08-02 12:27
 * @since 1.0.0
 */
@Component
@RequiredArgsConstructor
public class TaskUtils extends TaskBaseUtils {
    private static TaskUtils utils;
    private final TaskService taskService;

    @PostConstruct
    void init() {
        utils = this;
    }


    /**
     * 提交流程审批状态以及审批意见
     *
     * @param task  ${@link Task}
     * @param model ${@link SubmitApprovalModel}
     * @author zxh
     * @date 2021-12-12 00:08
     */
    public static void setApprovalAndComment(Task task, SubmitApprovalModel model) {
        setApprovalAndComment(utils.taskService, task.getId(), task.getProcessInstanceId(), model);
    }


    /**
     * 提交流程审批意见
     *
     * @param task    ${@link Task} 任务
     * @param comment ${@link String} 意见信息
     * @author zxh
     * @date 2021-12-01 15:45
     */
    public static void setComment(Task task, String comment) {
        setComment(utils.taskService, task.getId(), task.getProcessInstanceId(), comment);
    }


    /**
     * 设置任务标题
     *
     * @param task  ${@link Task} 任务
     * @param title ${@link String} 任务标题
     * @author zxh
     * @date 2021-12-01 15:45
     */
    public static void setTaskTitle(Task task, String title) {
        setTaskTitle(utils.taskService, task.getId(), title);
    }


    /**
     * 设置任务类型
     *
     * @param task     ${@link Task} 任务
     * @param taskType ${@link TaskType} 任务类型
     * @author zxh
     * @date 2021-12-01 15:45
     */
    public static void setTaskType(Task task, TaskType taskType) {
        setTaskType(utils.taskService, task.getId(), taskType);
    }


    /**
     * 设置任务操作
     *
     * @param task       ${@link Task} 任务
     * @param handleType ${@link TaskHandleType} 任务操作类型
     * @author zxh
     * @date 2021-12-01 15:45
     */
    public static void setTaskHandleType(Task task, TaskHandleType handleType) {
        setTaskHandleType(utils.taskService, task.getId(), handleType);
    }


    /**
     * 创建子任务并保存
     */
    public static void createSubTaskAndSave(Task parentTask, String subTaskTitle, AnYiProcessSimpleUserModel userModel, TaskType taskType) {
        Task subTask = createSubTask(parentTask, subTaskTitle, userModel.getUserId());
        utils.taskService.saveTask(subTask);
        setTaskType(subTask, taskType);
        // 设置任务标题
        utils.taskService.setVariableLocal(subTask.getId(), TaskVariableType.TASK_TITLE.getVariableKey(), subTaskTitle);
    }


    /**
     * 创建子任务
     */
    public static Task createSubTask(Task parentTask, String subTaskTitle, String assignee) {
        TaskEntity parentTaskEntity = (TaskEntity) parentTask;
        TaskEntity subTask = (TaskEntity) utils.taskService.newTask();
        subTask.setCaseInstanceId(parentTaskEntity.getCaseInstanceId());
        subTask.setParentTaskId(parentTaskEntity.getId());
        subTask.setProcessDefinitionId(parentTask.getProcessDefinitionId());
        subTask.setDueDate(parentTaskEntity.getDueDate());
        subTask.setFollowUpDate(parentTaskEntity.getFollowUpDate());
        subTask.setPriority(parentTaskEntity.getPriority());
        subTask.setTenantId(parentTaskEntity.getTenantId());
        if (StringUtils.isBlank(subTaskTitle)) {
            String name = parentTaskEntity.getName();
            subTaskTitle = StringUtils.isNotBlank(name) ? name + "的子任务" : parentTaskEntity.getTaskDefinitionKey() + "的子任务";
        }
        subTask.setName(subTaskTitle);
        subTask.setAssignee(assignee);
        subTask.setProcessInstanceId(parentTaskEntity.getProcessInstanceId());
        subTask.setDescription(parentTaskEntity.getDescription());
        if (StringUtils.isBlank(parentTaskEntity.getDescription())) {
            subTask.setDescription(subTaskTitle);
        }
        return subTask;
    }

}
