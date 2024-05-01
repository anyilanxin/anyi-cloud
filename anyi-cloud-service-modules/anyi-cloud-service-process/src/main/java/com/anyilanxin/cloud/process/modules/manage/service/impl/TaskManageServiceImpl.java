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

package com.anyilanxin.cloud.process.modules.manage.service.impl;

import com.anyilanxin.cloud.corecommon.constant.AnYiResultStatus;
import com.anyilanxin.cloud.corecommon.exception.AnYiResponseException;
import com.anyilanxin.cloud.corecommon.model.common.AnYiPageResult;
import com.anyilanxin.cloud.corecommon.utils.AnYiDateUtils;
import com.anyilanxin.cloud.database.utils.PageUtils;
import com.anyilanxin.cloud.process.modules.business.service.dto.MyWaitTaskPageDto;
import com.anyilanxin.cloud.process.modules.business.service.dto.WaitTaskPageDto;
import com.anyilanxin.cloud.process.modules.business.service.mapstruct.MyWaitTaskPageMap;
import com.anyilanxin.cloud.process.modules.manage.controller.vo.AllTaskPageQuery;
import com.anyilanxin.cloud.process.modules.manage.service.ITaskManageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.camunda.bpm.engine.HistoryService;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.history.HistoricTaskInstanceQuery;
import org.camunda.bpm.engine.impl.persistence.entity.TaskEntity;
import org.camunda.bpm.engine.task.Task;
import org.camunda.bpm.engine.task.TaskQuery;
import org.dromara.hutool.core.collection.CollUtil;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * 路程逻辑处理实现
 *
 * @author zxh
 * @date 2020-10-15 19:53
 * @since 1.0.0
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class TaskManageServiceImpl implements ITaskManageService {
    private final TaskService taskService;
    private final HistoryService historyService;
    private final MyWaitTaskPageMap pageMap;

    @Override
    public void addCandidateUser(String taskId, String userId) throws RuntimeException {
        // 查询任务是否被签收
        checkClaim(taskId);
        // 添加候选人
        Task task = taskService.newTask();
        task.setParentTaskId(taskId);
        taskService.addCandidateUser(taskId, userId);
    }


    @Override
    public void deleteCandidateUser(String taskId, String userId) throws RuntimeException {
        // 查询任务是否被签收
        checkClaim(taskId);
        // 删除候选人
        taskService.deleteCandidateUser(taskId, userId);
    }


    @Override
    public void addCandidateGroup(String taskId, String groupId) throws RuntimeException {
        // 查询任务是否被签收
        checkClaim(taskId);
        // 添加候选组
        taskService.addCandidateGroup(taskId, groupId);
    }


    @Override
    public void deleteCandidateGroup(String taskId, String groupId) throws RuntimeException {
        // 查询任务是否被签收
        checkClaim(taskId);
        // 删除候选组
        taskService.deleteCandidateGroup(taskId, groupId);
    }


    /**
     * 检测任务是否签收
     *
     * @param taskId ${@link String} 任务id
     * @author zxh
     * @date 2021-05-22 21:49
     */
    private void checkClaim(String taskId) {
        // 查询任务
        TaskEntity task = getTaskById(taskId);
        if (StringUtils.isNotBlank(task.getAssignee())) {
            throw new AnYiResponseException(AnYiResultStatus.VERIFICATION_FAILED, "流程任务已经被签收");
        }
    }


    /**
     * 查询流程任务是否存在
     *
     * @param taskId ${@link String} 流程任务id
     * @author zxh
     * @date 2021-05-21 04:51
     */
    private TaskEntity getTaskById(String taskId) {
        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
        if (Objects.isNull(task)) {
            throw new AnYiResponseException(AnYiResultStatus.DATABASE_BASE_ERROR, "流程任务不存在");
        }
        return (TaskEntity) task;
    }


    /**
     * 组装为自定义类型task
     *
     * @param tasks ${@link List<Task>}
     * @return List<TasksPageDto> ${@link List< MyWaitTaskPageDto >}
     * @author zxh
     * @date 2020-10-19 21:41
     */
    private List<WaitTaskPageDto> getTaskPage(List<Task> tasks) {
        if (CollUtil.isEmpty(tasks)) {
            return Collections.emptyList();
        }
        List<WaitTaskPageDto> pageDtos = new ArrayList<>();
        tasks.forEach(v -> {
            if (v instanceof TaskEntity taskEntity) {
                WaitTaskPageDto pageDto = pageMap.bToA(taskEntity);
                pageDto.setCreateTime(taskEntity.getCreateTime());
                pageDto.setDueTime(taskEntity.getDueDate());
                pageDto.setFollowUpTime(taskEntity.getFollowUpDate());
                pageDtos.add(pageDto);
            }
        });
        return pageDtos;
    }


    @Override
    public AnYiPageResult<WaitTaskPageDto> getAllTasks(AllTaskPageQuery vo) throws RuntimeException {
        TaskQuery taskQuery = taskService.createTaskQuery();
        if (StringUtils.isNotBlank(vo.getAssignee())) {
            taskQuery.taskAssignee(vo.getAssignee());
        }
        if (StringUtils.isNotBlank(vo.getRoleCode())) {
            taskQuery.taskCandidateGroup(vo.getRoleCode());
        }
        if (StringUtils.isNotBlank(vo.getCandidateUser())) {
            taskQuery.taskCandidateUser(vo.getCandidateUser());
        }
        // 创建开始时间
        if (Objects.nonNull(vo.getStartTime())) {
            taskQuery.taskCreatedAfter(AnYiDateUtils.toDateTime(vo.getStartTime()));
        }
        // 创建结束时间
        if (Objects.nonNull(vo.getEndTime())) {
            taskQuery.taskCreatedBefore(AnYiDateUtils.toDateTime(vo.getEndTime()));
        }
        if (StringUtils.isNotBlank(vo.getTaskName())) {
            String taskName = "%" + vo.getTaskName() + "%";
            taskQuery.taskNameLike(taskName);
        }
        long count = taskQuery.count();
        if (count <= 0) {
            return PageUtils.toPageData();
        }
        List<Task> tasks = taskQuery.orderByTaskCreateTime().desc().listPage(vo.getCurrent(), vo.getSize());
        return PageUtils.toPageData(count, getTaskPage(tasks));
    }


    @Override
    public void getAllTaskHistory() throws RuntimeException {
        HistoricTaskInstanceQuery historicTaskInstanceQuery = historyService.createHistoricTaskInstanceQuery();
        // String taskOwnerLike
        historicTaskInstanceQuery.taskOwnerLike("");
        // String taskAssigneeLike
        historicTaskInstanceQuery.taskAssigneeLike("");
        // String taskNameLike
        historicTaskInstanceQuery.taskNameLike("");
        //
        historicTaskInstanceQuery.taskVariableValueEquals("", "");
    }
}
