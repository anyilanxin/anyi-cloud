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

package com.anyilanxin.anyicloud.process.modules.business.service.impl;

import com.alibaba.fastjson2.JSONObject;
import com.anyilanxin.anyicloud.corecommon.utils.AnYiDateUtils;
import com.anyilanxin.anyicloud.coreprocess.emuns.ProcessVariableType;
import com.anyilanxin.anyicloud.coreprocess.emuns.UserTaskVariableType;
import com.anyilanxin.anyicloud.coreprocess.model.task.SubmitApprovalModel;
import com.anyilanxin.anyicloud.process.extend.constant.impl.ProcessInstanceState;
import com.anyilanxin.anyicloud.process.extend.constant.impl.TaskVariableType;
import com.anyilanxin.anyicloud.process.modules.business.service.IProcessBusinessCommonService;
import com.anyilanxin.anyicloud.processadapter.model.AnYiProcessUserModel;
import com.anyilanxin.anyicloud.processadapter.model.ProcessBackModel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.camunda.bpm.engine.FormService;
import org.camunda.bpm.engine.HistoryService;
import org.camunda.bpm.engine.RepositoryService;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.history.HistoricProcessInstance;
import org.camunda.bpm.engine.history.HistoricTaskInstance;
import org.camunda.bpm.engine.history.HistoricVariableInstance;
import org.camunda.bpm.engine.impl.persistence.entity.*;
import org.camunda.bpm.engine.repository.ProcessDefinition;
import org.camunda.bpm.engine.task.Comment;
import org.camunda.bpm.engine.task.Task;
import org.dromara.hutool.core.collection.CollUtil;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * 流程外部任务相关
 *
 * @author zxh
 * @date 2022-04-24 01:26
 * @since 1.0.0
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class ProcessBusinessCommonServiceImpl implements IProcessBusinessCommonService {
    private final HistoryService historyService;
    private final TaskService taskService;
    private final RepositoryService repositoryService;
    private final FormService formService;

    @Override
    public Map<String, Map<String, Map<String, HistoricVariableInstanceEntity>>> getInstanceVariableById(Set<String> instanceIds, Set<String> processCategorySet) {
        Map<String, Map<String, Map<String, HistoricVariableInstanceEntity>>> resultMap = new HashMap<>(64);
        if (CollUtil.isNotEmpty(instanceIds)) {
            List<HistoricVariableInstance> variableInstances = historyService.createHistoricVariableInstanceQuery().processInstanceIdIn(instanceIds.toArray(new String[]{})).list();
            if (CollUtil.isNotEmpty(variableInstances)) {
                variableInstances.forEach(v -> {
                    Map<String, Map<String, HistoricVariableInstanceEntity>> variableInstanceMap = resultMap.get(v.getProcessInstanceId());
                    if (CollUtil.isEmpty(variableInstanceMap)) {
                        variableInstanceMap = new HashMap<>(128);
                    }
                    String key = v.getProcessInstanceId();
                    Map<String, HistoricVariableInstanceEntity> historicVariableInstanceEntityMap = variableInstanceMap.get(v.getProcessInstanceId());
                    if (StringUtils.isNotBlank(v.getTaskId())) {
                        historicVariableInstanceEntityMap = variableInstanceMap.get(v.getTaskId());
                        key = v.getTaskId();
                    }
                    if (CollUtil.isEmpty(historicVariableInstanceEntityMap)) {
                        historicVariableInstanceEntityMap = new HashMap<>();
                    }
                    if (StringUtils.isNotBlank(v.getName()) && Objects.nonNull(v.getValue())) {
                        historicVariableInstanceEntityMap.put(v.getName(), (HistoricVariableInstanceEntity) v);
                        if (ProcessVariableType.PROCESS_CATEGORY.getVariableKey().equals(v.getName())) {
                            String processCategory = Objects.nonNull(v.getValue()) ? v.getValue().toString() : "";
                            if (StringUtils.isNotBlank(processCategory)) {
                                processCategorySet.add(processCategory);
                            }
                        }
                    }
                    variableInstanceMap.put(key, historicVariableInstanceEntityMap);
                    resultMap.put(v.getProcessInstanceId(), variableInstanceMap);
                });
            }
        }
        return resultMap;
    }


    @Override
    public Map<String, Map<String, HistoricVariableInstanceEntity>> getInstanceVariableById(Set<String> taskIds) {
        Map<String, Map<String, HistoricVariableInstanceEntity>> resultMap = new HashMap<>(64);
        if (CollUtil.isNotEmpty(taskIds)) {
            List<HistoricVariableInstance> variableInstances = historyService.createHistoricVariableInstanceQuery().taskIdIn(taskIds.toArray(new String[]{})).list();
            if (CollUtil.isNotEmpty(variableInstances)) {
                variableInstances.forEach(v -> {
                    Map<String, HistoricVariableInstanceEntity> variableInstanceMap = resultMap.get(v.getTaskId());
                    if (CollUtil.isEmpty(variableInstanceMap)) {
                        variableInstanceMap = new HashMap<>(128);
                    }
                    if (StringUtils.isNotBlank(v.getName()) && Objects.nonNull(v.getValue())) {
                        variableInstanceMap.put(v.getName(), (HistoricVariableInstanceEntity) v);
                    }
                    resultMap.put(v.getTaskId(), variableInstanceMap);
                });
            }
        }
        return resultMap;
    }


    @Override
    public Map<String, Map<String, HistoricVariableInstanceEntity>> getInstanceVariableById(String processInstanceId) {
        Map<String, Map<String, HistoricVariableInstanceEntity>> result = new HashMap<>(128);
        List<HistoricVariableInstance> variableInstances = historyService.createHistoricVariableInstanceQuery().processInstanceId(processInstanceId).list();
        if (CollUtil.isNotEmpty(variableInstances)) {
            variableInstances.forEach(v -> {
                Map<String, HistoricVariableInstanceEntity> historicVariableMap = null;
                String key = null;
                if (StringUtils.isNotBlank(v.getActivityInstanceId())) {
                    historicVariableMap = result.get(v.getActivityInstanceId());
                    key = v.getActivityInstanceId();
                    if (Objects.isNull(historicVariableMap)) {
                        historicVariableMap = new HashMap<>(64);
                    }
                } else if (v.getProcessInstanceId().equals(v.getActivityInstanceId())) {
                    historicVariableMap = result.get(v.getProcessInstanceId());
                    key = v.getProcessInstanceId();
                    if (Objects.isNull(historicVariableMap)) {
                        historicVariableMap = new HashMap<>(128);
                    }
                }
                if (Objects.nonNull(historicVariableMap)) {
                    String name = v.getName();
                    Object value = v.getValue();
                    if (StringUtils.isNotBlank(name) && Objects.nonNull(value)) {
                        historicVariableMap.put(name, (HistoricVariableInstanceEntity) v);
                    }
                    result.put(key, historicVariableMap);
                }
            });
        }
        return result;
    }


    @Override
    public Map<String, List<CommentEntity>> getInstanceCommentsById(String processInstanceId) {
        List<Comment> processInstanceComments = taskService.getProcessInstanceComments(processInstanceId);
        Map<String, List<CommentEntity>> resultMap = new HashMap<>(64);
        if (CollUtil.isNotEmpty(processInstanceComments)) {
            processInstanceComments.forEach(v -> {
                String taskId = v.getTaskId();
                if (StringUtils.isNotBlank(taskId)) {
                    List<CommentEntity> commentEntities = resultMap.get(taskId);
                    if (Objects.isNull(commentEntities)) {
                        commentEntities = new ArrayList<>();
                    }
                    commentEntities.add((CommentEntity) v);
                    resultMap.put(taskId, commentEntities);
                }
            });
        }
        return resultMap;
    }


    @Override
    public Map<String, Map<String, List<CommentEntity>>> getInstanceCommentsByIds(Set<String> processInstanceIds) {
        Map<String, Map<String, List<CommentEntity>>> processInstanceComments = new HashMap<>(processInstanceIds.size());
        processInstanceIds.forEach(v -> {
            Map<String, List<CommentEntity>> instanceCommentsById = getInstanceCommentsById(v);
            if (CollUtil.isNotEmpty(instanceCommentsById)) {
                processInstanceComments.put(v, instanceCommentsById);
            }
        });
        return processInstanceComments;
    }


    @Override
    public Map<String, HistoricProcessInstanceEntity> getProcessInstanceById(Set<String> processInstanceIds) {
        Map<String, HistoricProcessInstanceEntity> resultMap = new HashMap<>(64);
        if (CollUtil.isNotEmpty(processInstanceIds)) {
            List<HistoricProcessInstance> processInstanceList = historyService.createHistoricProcessInstanceQuery().processInstanceIds(processInstanceIds).list();
            if (CollUtil.isNotEmpty(processInstanceList)) {
                processInstanceList.forEach(v -> resultMap.put(v.getId(), (HistoricProcessInstanceEntity) v));
            }
        }
        return resultMap;
    }


    @Override
    public Map<String, HistoricVariableInstanceEntity> getInstanceVariableByTaskId(String taskId) {
        List<HistoricVariableInstance> list = historyService.createHistoricVariableInstanceQuery().taskIdIn(taskId).list();
        Map<String, HistoricVariableInstanceEntity> taskVariableMap = new HashMap<>(64);
        if (CollUtil.isNotEmpty(list)) {
            list.forEach(v -> {
                if (Objects.nonNull(v.getValue())) {
                    taskVariableMap.put(v.getName(), (HistoricVariableInstanceEntity) v);
                }
            });
        }
        return taskVariableMap;
    }


    @Override
    public ProcessBackModel getProcessBackInfo(String processInstanceId) {
        // 查询流程实例
        HistoricProcessInstanceEntity historicProcessInstance = (HistoricProcessInstanceEntity) historyService.createHistoricProcessInstanceQuery().processInstanceId(processInstanceId).singleResult();
        ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery().processDefinitionId(historicProcessInstance.getProcessDefinitionId()).singleResult();
        String startFormKey = formService.getStartFormKey(historicProcessInstance.getProcessDefinitionId());
        // 构建返回信息
        Map<String, Map<String, HistoricVariableInstanceEntity>> processVariableAll = getInstanceVariableById(processInstanceId);
        ProcessBackModel resultModel = new ProcessBackModel();
        Map<String, HistoricVariableInstanceEntity> processVariables = processVariableAll.get(processInstanceId);
        if (CollUtil.isNotEmpty(processVariables)) {
            HistoricVariableInstanceEntity initiateUserInfo = processVariables.get(ProcessVariableType.INITIATE_USER.getVariableKey());
            if (Objects.nonNull(initiateUserInfo)) {
                String textValue = initiateUserInfo.getTextValue();
                if (StringUtils.isNotBlank(textValue)) {
                    AnYiProcessUserModel processUserModel = JSONObject.parseObject(textValue, AnYiProcessUserModel.class);
                    resultModel.setUserInfo(processUserModel);
                }
            }
        }
        resultModel.setCategoryCode(processDefinition.getCategory());
        resultModel.setStartFormKey(startFormKey);
        resultModel.setProcessDefinitionId(historicProcessInstance.getProcessDefinitionId());
        resultModel.setBusinessKey(historicProcessInstance.getBusinessKey());
        resultModel.setProcessInstanceId(processInstanceId);
        resultModel.setStartUserId(historicProcessInstance.getStartUserId());
        resultModel.setProcessDefinitionKey(historicProcessInstance.getProcessDefinitionKey());
        resultModel.setProcessDefinitionId(historicProcessInstance.getProcessDefinitionId());
        if (Objects.nonNull(historicProcessInstance.getStartTime())) {
            resultModel.setStartTime(AnYiDateUtils.toLocalDateTime(historicProcessInstance.getStartTime()));
        }
        if (Objects.nonNull(historicProcessInstance.getEndTime())) {
            resultModel.setEndTime(AnYiDateUtils.toLocalDateTime(historicProcessInstance.getEndTime()));
        }
        ProcessInstanceState byStrState = ProcessInstanceState.getByStrState(historicProcessInstance.getState(), historicProcessInstance.getDeleteReason());
        switch (Objects.requireNonNull(byStrState)) {
            case SUSPENDED:
                resultModel.setProcessState(com.anyilanxin.anyicloud.coreprocess.emuns.ProcessInstanceState.SUSPENDED);
                break;
            case COMPLETED:
                resultModel.setProcessState(com.anyilanxin.anyicloud.coreprocess.emuns.ProcessInstanceState.COMPLETED);
                break;
            case REFUSED:
                resultModel.setProcessState(com.anyilanxin.anyicloud.coreprocess.emuns.ProcessInstanceState.REFUSED);
                break;
            default:
                resultModel.setProcessState(com.anyilanxin.anyicloud.coreprocess.emuns.ProcessInstanceState.ACTIVE);
        }
        // 下一个节点审批信息
        List<Task> taskList = taskService.createTaskQuery().processInstanceId(processInstanceId).initializeFormKeys().list();
        List<ProcessBackModel.TaskNodeModel> taskInfoList = new ArrayList<>();
        if (CollUtil.isNotEmpty(taskList)) {
            taskList.forEach(v -> {
                TaskEntity taskEntity = (TaskEntity) v;
                ProcessBackModel.TaskNodeModel taskInfo = new ProcessBackModel.TaskNodeModel();
                taskInfo.setTaskId(taskEntity.getId());
                taskInfo.setAssignee(taskEntity.getAssignee());
                taskInfo.setOwner(taskEntity.getOwner());
                taskInfo.setTaskName(taskEntity.getName());
                taskInfo.setTaskDefinitionKey(taskEntity.getTaskDefinitionKey());
                taskInfo.setTaskFormKey(taskEntity.getFormKey());
                taskInfoList.add(taskInfo);
            });
        }
        resultModel.setNextTaskList(taskInfoList);
        // 当前已经审批完的节点审批信息
        List<HistoricTaskInstance> all = historyService.createHistoricTaskInstanceQuery().processInstanceId(processInstanceId).finished().list();
        if (CollUtil.isNotEmpty(all)) {
            List<ProcessBackModel.TaskNodeModel> approveTaskList = new ArrayList<>();
            all.forEach(v -> {
                String taskFormKey = formService.getTaskFormKey(v.getProcessDefinitionId(), v.getTaskDefinitionKey());
                HistoricTaskInstanceEntity taskEntity = (HistoricTaskInstanceEntity) v;
                ProcessBackModel.TaskNodeModel taskInfo = new ProcessBackModel.TaskNodeModel();
                taskInfo.setTaskId(taskEntity.getId());
                taskInfo.setAssignee(taskEntity.getAssignee());
                taskInfo.setOwner(taskEntity.getOwner());
                taskInfo.setTaskName(taskEntity.getName());
                taskInfo.setTaskDefinitionKey(taskEntity.getTaskDefinitionKey());
                taskInfo.setTaskFormKey(taskFormKey);
                taskInfo.setParentTaskId(taskEntity.getParentTaskId());
                taskInfo.setDescription(taskEntity.getDescription());
                if (Objects.nonNull(taskEntity.getStartTime())) {
                    taskInfo.setStartTime(AnYiDateUtils.toLocalDateTime(taskEntity.getStartTime()));
                }
                if (Objects.nonNull(taskEntity.getEndTime())) {
                    taskInfo.setEndTime(AnYiDateUtils.toLocalDateTime(taskEntity.getEndTime()));
                }
                Map<String, HistoricVariableInstanceEntity> taskVariables = processVariableAll.get(v.getId());
                if (CollUtil.isNotEmpty(taskVariables)) {
                    HistoricVariableInstanceEntity historicVariableInstanceEntity = taskVariables.get(TaskVariableType.NR_OF_INSTANCE_APPROVE_INFO.getVariableKey());
                    if (Objects.nonNull(historicVariableInstanceEntity)) {
                        String textValue = historicVariableInstanceEntity.getTextValue();
                        SubmitApprovalModel submitApprovalModel = JSONObject.parseObject(textValue, SubmitApprovalModel.class);
                        taskInfo.setApprovalOption(submitApprovalModel.getApprovalOpinion());
                        taskInfo.setTaskState(submitApprovalModel.getStatus());
                    }
                    HistoricVariableInstanceEntity userInfo = taskVariables.get(UserTaskVariableType.TASK_ASSIGNEE_USER_INFO_STR.getVariableKey());
                    if (Objects.nonNull(userInfo)) {
                        String textValue = userInfo.getTextValue();
                        if (StringUtils.isNotBlank(textValue)) {
                            AnYiProcessUserModel processUserModel = JSONObject.parseObject(textValue, AnYiProcessUserModel.class);
                            taskInfo.setUserInfo(processUserModel);
                            taskInfo.setAssigneeName(processUserModel.getRealName());
                        }
                    }
                }
                approveTaskList.add(taskInfo);
            });
            resultModel.setApproveTaskList(approveTaskList);
        }
        return resultModel;
    }
}
