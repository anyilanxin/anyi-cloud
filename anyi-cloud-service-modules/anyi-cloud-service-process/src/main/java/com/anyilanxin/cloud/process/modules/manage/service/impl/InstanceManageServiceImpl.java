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

import com.anyilanxin.cloud.corecommon.base.AnYiResult;
import com.anyilanxin.cloud.corecommon.constant.AnYiResultStatus;
import com.anyilanxin.cloud.corecommon.exception.AnYiResponseException;
import com.anyilanxin.cloud.corecommon.model.auth.RoleInfo;
import com.anyilanxin.cloud.corecommon.model.common.AnYiPageResult;
import com.anyilanxin.cloud.corecommon.utils.AnYiDateUtils;
import com.anyilanxin.cloud.coreprocess.emuns.ProcessVariableType;
import com.anyilanxin.cloud.database.utils.PageUtils;
import com.anyilanxin.cloud.process.extend.constant.impl.IdentityType;
import com.anyilanxin.cloud.process.extend.constant.impl.ProcessInstanceState;
import com.anyilanxin.cloud.process.modules.manage.controller.vo.*;
import com.anyilanxin.cloud.process.modules.manage.service.IInstanceManageService;
import com.anyilanxin.cloud.process.modules.manage.service.dto.*;
import com.anyilanxin.cloud.process.modules.manage.service.mapstruct.InstanceMap;
import com.anyilanxin.cloud.process.modules.manage.service.mapstruct.TaskHistoryDetailMap;
import com.anyilanxin.cloud.process.modules.manage.service.mapstruct.TaskWaitDetailMap;
import com.anyilanxin.cloud.process.utils.Base64FileUtils;
import com.anyilanxin.cloud.systemadapter.model.SimpleUserModel;
import com.anyilanxin.cloud.systemrpcadapter.rpc.SystemRoleRemoteRpc;
import com.anyilanxin.cloud.systemrpcadapter.rpc.SystemUserRemoteRpc;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.camunda.bpm.engine.*;
import org.camunda.bpm.engine.batch.Batch;
import org.camunda.bpm.engine.history.*;
import org.camunda.bpm.engine.impl.persistence.entity.*;
import org.camunda.bpm.engine.migration.MigrationPlan;
import org.camunda.bpm.engine.migration.MigrationPlanBuilder;
import org.camunda.bpm.engine.migration.MigrationPlanExecutionBuilder;
import org.camunda.bpm.engine.repository.ProcessDefinition;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.runtime.ProcessInstanceQuery;
import org.camunda.bpm.engine.task.Comment;
import org.dromara.hutool.core.collection.CollUtil;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.*;

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
public class InstanceManageServiceImpl implements IInstanceManageService {
    private final RuntimeService runtimeService;
    private final TaskService taskService;
    private final FormService formService;
    private final TaskHistoryDetailMap historyDetailMap;
    private final TaskWaitDetailMap waitDetailMap;
    private final IdentityService identityService;
    private final HistoryService historyService;
    private final SystemRoleRemoteRpc remoteRoleService;
    private final SystemUserRemoteRpc remoteUserService;
    private final InstanceMap instanceMap;
    private final RepositoryService repositoryService;

    @Override
    public void deleteProcessInstance(DeleteProcessInstanceVo vo) throws RuntimeException {
        runtimeService.deleteProcessInstances(vo.getProcessInstanceIds(), vo.getReason(), vo.isSkipCustomListeners(), vo.isExternallyTerminated(), vo.isSkipSubprocesses());
    }


    @Override
    public void suspendProcessInstance(ProcessInstanceBatchVo vo) throws RuntimeException {
        runtimeService.updateProcessInstanceSuspensionState().byProcessInstanceIds(vo.getProcessInstanceIds()).suspend();
    }


    @Override
    public void activateProcessInstance(ProcessInstanceBatchVo vo) throws RuntimeException {
        runtimeService.updateProcessInstanceSuspensionState().byProcessInstanceIds(vo.getProcessInstanceIds()).activate();
    }


    @Override
    public AnYiPageResult<InstancePageDto> selectProcessInstance(ProcessInstancePageVo vo) {
        ProcessInstanceQuery processInstanceQuery = runtimeService.createProcessInstanceQuery();
        if (StringUtils.isNotBlank(vo.getProcessDefinitionId())) {
            processInstanceQuery = processInstanceQuery.processDefinitionId(vo.getProcessDefinitionId());
        }
        if (Objects.nonNull(vo.getSuspended()) && vo.getSuspended()) {
            processInstanceQuery = processInstanceQuery.suspended();
        }
        ProcessInstanceQuery query = processInstanceQuery.orderByProcessDefinitionId().asc();
        long count = query.count();
        if (count <= 0) {
            return PageUtils.toPageData();
        }
        List<ProcessInstance> processInstances = query.listPage(vo.getCurrent(), vo.getSize());
        List<InstancePageDto> pageDtos = new ArrayList<>();
        if (CollUtil.isNotEmpty(processInstances)) {
            processInstances.forEach(v -> {
                InstancePageDto dto = new InstancePageDto();
                dto.setProcessInstanceId(v.getProcessInstanceId());
                dto.setBusinessKey(v.getBusinessKey());
                dto.setSuspended(v.isSuspended());
                dto.setProcessDefinitionId(v.getProcessDefinitionId());
                InputStream processModel = repositoryService.getProcessModel(v.getProcessDefinitionId());
                if (Objects.nonNull(processModel)) {
                    dto.setDiagramData(Base64FileUtils.inputStreamToBase64(processModel));
                }
                ProcessDefinition processDefinition = repositoryService.getProcessDefinition(v.getProcessDefinitionId());
                if (Objects.nonNull(processDefinition)) {
                    dto.setDeploymentName(processDefinition.getName());
                    dto.setVersionTag(processDefinition.getVersionTag());
                    dto.setVersion(processDefinition.getVersion());
                }
                pageDtos.add(dto);
            });
        }
        return PageUtils.toPageData(count, pageDtos);
    }


    @Override
    public AnYiPageResult<InstanceHistoryPageDto> selectHistoryProcessInstance(ProcessInstanceHistoryPageQuery vo) {
        HistoricProcessInstanceQuery processInstanceQuery = historyService.createHistoricProcessInstanceQuery();
        if (StringUtils.isNotBlank(vo.getProcessDefinitionId())) {
            processInstanceQuery = processInstanceQuery.processDefinitionId(vo.getProcessDefinitionId());
        }
        Integer instanceState = vo.getInstanceState();
        if (Objects.nonNull(instanceState)) {
            ProcessInstanceState byState = ProcessInstanceState.getByState(vo.getInstanceState());
            if (Objects.nonNull(byState)) {
                switch (byState) {
                    case ACTIVE:
                        processInstanceQuery = processInstanceQuery.active();
                        break;
                    case SUSPENDED:
                        processInstanceQuery = processInstanceQuery.suspended();
                        break;
                    case COMPLETED:
                        processInstanceQuery = processInstanceQuery.completed();
                        break;
                    case EXTERNALLY_TERMINATED:
                        processInstanceQuery = processInstanceQuery.externallyTerminated();
                        break;
                    case INTERNALLY_TERMINATED:
                        processInstanceQuery = processInstanceQuery.internallyTerminated();
                        break;
                }
            }
        }
        if (StringUtils.isNotBlank(vo.getTitle())) {
            processInstanceQuery.variableValueLike(ProcessVariableType.PROCESS_TITLE.getType(), "%" + vo.getTitle() + "%");
        }
        if (Objects.nonNull(vo.getFinished())) {
            if (vo.getFinished()) {
                processInstanceQuery = processInstanceQuery.finished();
            } else {
                processInstanceQuery = processInstanceQuery.unfinished();
            }
        }
        if (CollUtil.isNotEmpty(vo.getAscs())) {
            for (String str : vo.getDescs()) {
                if (str.equals("instanceStartTime")) {
                    processInstanceQuery = processInstanceQuery.orderByProcessInstanceStartTime().asc();
                } else if (str.equals("instanceEndTime")) {
                    processInstanceQuery = processInstanceQuery.orderByProcessInstanceEndTime().asc();
                } else if (str.equals("processDefinitionName")) {
                    processInstanceQuery = processInstanceQuery.orderByProcessDefinitionName().asc();
                } else if (str.equals("durationInMillis")) {
                    processInstanceQuery = processInstanceQuery.orderByProcessInstanceDuration().asc();
                } else if (str.equals("version")) {
                    processInstanceQuery = processInstanceQuery.orderByProcessDefinitionVersion().desc();
                }
            }
        }
        if (CollUtil.isNotEmpty(vo.getDescs())) {
            for (String str : vo.getDescs()) {
                if (str.equals("instanceStartTime")) {
                    processInstanceQuery = processInstanceQuery.orderByProcessInstanceStartTime().desc();
                } else if (str.equals("instanceEndTime")) {
                    processInstanceQuery = processInstanceQuery.orderByProcessInstanceEndTime().desc();
                } else if (str.equals("processDefinitionName")) {
                    processInstanceQuery = processInstanceQuery.orderByProcessDefinitionName().desc();
                } else if (str.equals("durationInMillis")) {
                    processInstanceQuery = processInstanceQuery.orderByProcessInstanceDuration().desc();
                } else if (str.equals("version")) {
                    processInstanceQuery = processInstanceQuery.orderByProcessDefinitionVersion().desc();
                }
            }
        }
        if (Objects.nonNull(vo.getStartTime())) {
            processInstanceQuery = processInstanceQuery.startedAfter(AnYiDateUtils.toDateTime(vo.getStartTime()));
        }
        if (Objects.nonNull(vo.getEndTime())) {
            processInstanceQuery = processInstanceQuery.startedBefore(AnYiDateUtils.toDateTime(vo.getEndTime()));
        }
        if (StringUtils.isNotBlank(vo.getProcessDefinitionName())) {
            processInstanceQuery = processInstanceQuery.processDefinitionNameLike("%" + vo.getProcessDefinitionName() + "%");
        }

        // 组装根据用户信息实例查询
        if (StringUtils.isNotBlank(vo.getRealName())) {
            AnYiResult<List<SimpleUserModel>> userByRealName = remoteUserService.getUserByRealName(vo.getRealName());
            if (userByRealName.isSuccess() && CollUtil.isNotEmpty(userByRealName.getData())) {
                List<SimpleUserModel> data = userByRealName.getData();
                Set<String> processInstanceIds = new HashSet<>(16);
                for (SimpleUserModel user : data) {
                    List<HistoricProcessInstance> list = historyService.createHistoricProcessInstanceQuery().startedBy(user.getUserId()).list();
                    list.forEach(v -> processInstanceIds.add(v.getId()));
                    processInstanceQuery = processInstanceQuery.startedBy(user.getUserId());
                }
                if (CollUtil.isNotEmpty(processInstanceIds)) {
                    processInstanceQuery = processInstanceQuery.processInstanceIds(processInstanceIds);
                } else {
                    return PageUtils.toPageData(0, Collections.emptyList());
                }
            } else {
                return PageUtils.toPageData(0, Collections.emptyList());
            }
        }
        List<HistoricProcessInstance> historicProcessInstances = processInstanceQuery.listPage(vo.getCurrent(), vo.getSize());
        List<InstanceHistoryPageDto> pageDtos = new ArrayList<>(historicProcessInstances.size());
        if (CollUtil.isNotEmpty(historicProcessInstances)) {
            List<String> userIds = new ArrayList<>(historicProcessInstances.size());
            historicProcessInstances.forEach(v -> userIds.add(v.getStartUserId()));
            AnYiResult<List<SimpleUserModel>> userListByIds = remoteUserService.getUserListByIds(userIds);
            Map<String, SimpleUserModel> userDtoMap = new HashMap<>();
            if (userListByIds.isSuccess() && CollUtil.isNotEmpty(userListByIds.getData())) {
                List<SimpleUserModel> data = userListByIds.getData();
                data.forEach(v -> userDtoMap.put(v.getUserId(), v));
            }
            historicProcessInstances.forEach(v -> {
                List<HistoricVariableInstance> list = historyService.createHistoricVariableInstanceQuery().processInstanceId(v.getRootProcessInstanceId()).variableName(ProcessVariableType.PROCESS_TITLE.getVariableKey()).list();
                HistoricProcessInstanceEntity entity = (HistoricProcessInstanceEntity) v;
                InstanceHistoryPageDto dto = new InstanceHistoryPageDto();
                dto.setBusinessKey(entity.getBusinessKey());
                ProcessInstanceState byStrState = ProcessInstanceState.getByStrState(entity.getState(), v.getDeleteReason());
                if (Objects.nonNull(byStrState)) {
                    dto.setInstanceState(byStrState.getState());
                }
                dto.setProcessInstanceId(entity.getProcessInstanceId());
                dto.setProcessDefinitionId(entity.getProcessDefinitionId());
                dto.setVersion(entity.getProcessDefinitionVersion());
                dto.setIsFinish(Objects.nonNull(entity.getEndTime()));
                dto.setInstanceStartTime(AnYiDateUtils.toLocalDateTime(entity.getStartTime()));
                dto.setInstanceEndTime(AnYiDateUtils.toLocalDateTime(entity.getEndTime()));
                dto.setDurationStr(AnYiDateUtils.timeDifferenceToStr(dto.getInstanceStartTime(), dto.getInstanceEndTime()));
                dto.setInstanceRemovalTime(AnYiDateUtils.toLocalDateTime(entity.getRemovalTime()));
                dto.setDeleteReason(entity.getDeleteReason());
                dto.setStartActivityId(entity.getStartActivityId());
                dto.setEndActivityId(entity.getEndActivityId());
                dto.setProcessDefinitionName(entity.getProcessDefinitionName());
                dto.setDurationInMillis(entity.getDurationInMillis());
                dto.setStartUserInfo(userDtoMap.get(v.getStartUserId()));
                List<HistoricTaskInstance> taskInstances = historyService.createHistoricTaskInstanceQuery().processInstanceId(dto.getProcessInstanceId()).orderByHistoricActivityInstanceStartTime().desc().listPage(0, 1);
                if (CollUtil.isNotEmpty(taskInstances)) {
                    HistoricTaskInstanceEntity historicTaskInstanceEntity = (HistoricTaskInstanceEntity) taskInstances.get(0);
                    dto.setCurrentTaskId(historicTaskInstanceEntity.getId());
                    dto.setCurrentTaskName(historicTaskInstanceEntity.getName());
                    // 获取当前办理人信息
                    Set<String> currentUserIds = new HashSet<>(2);
                    if (StringUtils.isNotBlank(historicTaskInstanceEntity.getOwner())) {
                        currentUserIds.add(historicTaskInstanceEntity.getOwner());
                    }
                    if (StringUtils.isNotBlank(historicTaskInstanceEntity.getAssignee())) {
                        currentUserIds.add(historicTaskInstanceEntity.getAssignee());
                    }
                    if (CollUtil.isNotEmpty(currentUserIds)) {
                        AnYiResult<List<SimpleUserModel>> userListByIds1 = remoteUserService.getUserListByIds(new ArrayList<>(currentUserIds));
                        if (userListByIds1.isSuccess()) {
                            List<SimpleUserModel> data = userListByIds1.getData();
                            data.forEach(sv -> {
                                if (sv.getUserId().equals(historicTaskInstanceEntity.getOwner())) {
                                    dto.setCurrentOwnerUserId(sv.getUserId());
                                    dto.setCurrentOwnerUserName(sv.getUserName());
                                }
                                if (sv.getUserId().equals(historicTaskInstanceEntity.getAssignee())) {
                                    dto.setCurrentAssigneeUserId(sv.getUserId());
                                    dto.setCurrentAssigneeName(sv.getUserName());
                                }
                            });
                        }
                    }
                }
                pageDtos.add(dto);
            });
        }
        return PageUtils.toPageData(processInstanceQuery.count(), pageDtos);
    }


    @Override
    public String instanceMigration(MigrationVo vo) {
        MigrationPlanBuilder migrationPlanBuilder = runtimeService.createMigrationPlan(vo.getSourceProcessDefinitionId(), vo.getTargetProcessDefinitionId());
        if (vo.isAutoActivities()) {
            migrationPlanBuilder = migrationPlanBuilder.mapEqualActivities();
        } else if (CollUtil.isNotEmpty(vo.getActivitiesMap())) {
            Set<Map.Entry<String, String>> activitiesMap = vo.getActivitiesMap().entrySet();
            for (Map.Entry<String, String> activities : activitiesMap) {
                migrationPlanBuilder = migrationPlanBuilder.mapActivities(activities.getKey(), activities.getValue());
            }
        }
        MigrationPlan migrationPlan = migrationPlanBuilder.build();
        MigrationPlanExecutionBuilder migrationPlanExecutionBuilder = runtimeService.newMigration(migrationPlan);
        List<ProcessInstance> processInstanceList = runtimeService.createProcessInstanceQuery().processDefinitionId(vo.getSourceProcessDefinitionId()).active().list();
        if (CollUtil.isEmpty(processInstanceList)) {
            throw new AnYiResponseException(AnYiResultStatus.VERIFICATION_FAILED, "当前没有活动的实例可迁移");
        }
        Set<String> allActiveProcessInstanceIds = new HashSet<>(processInstanceList.size());
        processInstanceList.forEach(v -> allActiveProcessInstanceIds.add(v.getProcessInstanceId()));
        // 判断当前设定的流程迁移实例是否都存在
        if (CollUtil.isNotEmpty(vo.getProcessInstanceIds())) {
            boolean b = allActiveProcessInstanceIds.containsAll(vo.getProcessInstanceIds());
            if (!b) {
                throw new AnYiResponseException(AnYiResultStatus.VERIFICATION_FAILED, "部分迁移实例不存在或不是活动的，请重新选择");
            }
        } else {
            vo.setProcessInstanceIds(allActiveProcessInstanceIds);
        }
        migrationPlanExecutionBuilder.processInstanceIds(new ArrayList<>(vo.getProcessInstanceIds()));
        if (vo.isSkipCustomListener()) {
            migrationPlanExecutionBuilder = migrationPlanExecutionBuilder.skipCustomListeners();
        }
        if (vo.isSkipIoMapping()) {
            migrationPlanExecutionBuilder = migrationPlanExecutionBuilder.skipIoMappings();
        }
        String batchJobDefinitionId = "";
        if (vo.isAsync()) {
            Batch batch = migrationPlanExecutionBuilder.executeAsync();
            batchJobDefinitionId = batch.getBatchJobDefinitionId();
        } else {
            migrationPlanExecutionBuilder.execute();
        }
        return batchJobDefinitionId;
    }


    @Override
    public InstanceDetailDto getInstanceDetail(String processInstanceId) throws RuntimeException {
        InstanceDetailDto instanceDetailDto = null;
        String startUserId = null;
        HistoricProcessInstance historicProcessInstance = historyService.createHistoricProcessInstanceQuery().processInstanceId(processInstanceId).singleResult();
        if (Objects.nonNull(historicProcessInstance) && historicProcessInstance instanceof HistoricProcessInstanceEntity historicProcessInstanceEntity) {
            instanceDetailDto = instanceMap.aToB(historicProcessInstanceEntity);
            ProcessInstanceState byStrState = ProcessInstanceState.getByStrState(historicProcessInstanceEntity.getState(), historicProcessInstanceEntity.getDeleteReason());
            if (Objects.nonNull(byStrState)) {
                instanceDetailDto.setInstanceState(byStrState.getState());
            }
            instanceDetailDto.setIsFinish(Objects.nonNull(historicProcessInstanceEntity.getEndTime()));
            instanceDetailDto.setInstanceEndTime(AnYiDateUtils.toLocalDateTime(historicProcessInstance.getEndTime()));
            instanceDetailDto.setInstanceStartTime(AnYiDateUtils.toLocalDateTime(historicProcessInstance.getStartTime()));
            instanceDetailDto.setDurationStr(AnYiDateUtils.timeDifferenceToStr(instanceDetailDto.getInstanceStartTime(), instanceDetailDto.getInstanceEndTime()));
            instanceDetailDto.setInstanceRemovalTime(AnYiDateUtils.toLocalDateTime(historicProcessInstance.getRemovalTime()));
            startUserId = historicProcessInstance.getStartUserId();
        }
        if (Objects.isNull(instanceDetailDto)) {
            throw new AnYiResponseException(AnYiResultStatus.ERROR, "未查询到流程实例");
        }
        // 补全开始用户信息
        if (Objects.nonNull(startUserId)) {
            AnYiResult<SimpleUserModel> userListByIds = remoteUserService.getUserById(startUserId);
            if (userListByIds.isSuccess() && Objects.nonNull(userListByIds.getData())) {
                instanceDetailDto.setStartUserInfo(userListByIds.getData());
            }
        }
        // 获取模型信息
        InputStream processModel = repositoryService.getProcessModel(instanceDetailDto.getProcessDefinitionId());
        instanceDetailDto.setDiagramData(Base64FileUtils.inputStreamToBase64(processModel));
        // 获取部署信息
        ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery().processDefinitionId(instanceDetailDto.getProcessDefinitionId()).singleResult();
        if (Objects.nonNull(processDefinition) && processDefinition instanceof ProcessDefinitionEntity processDefinitionEntity) {
            instanceDetailDto.setDeploymentName(processDefinitionEntity.getName());
            instanceDetailDto.setVersionTag(processDefinitionEntity.getVersionTag());
        }
        // 获取任务实例详细信息
        List<HistoricActivityInstance> activityInstanceList = historyService.createHistoricActivityInstanceQuery().processInstanceId(processInstanceId).orderByHistoricActivityInstanceStartTime().asc().orderByActivityType().asc().list();
        if (CollUtil.isNotEmpty(activityInstanceList)) {
            List<TasksHistoryDetailDto> historyTaskInfos = new ArrayList<>(activityInstanceList.size());
            activityInstanceList.forEach(v -> {
                HistoricActivityInstanceEntity entity = (HistoricActivityInstanceEntity) v;
                TasksHistoryDetailDto dto = historyDetailMap.bToA(entity);
                dto.setTaskStartTime(AnYiDateUtils.toLocalDateTime(entity.getStartTime()));
                dto.setTaskEndTime(AnYiDateUtils.toLocalDateTime(entity.getEndTime()));
                dto.setDurationStr(AnYiDateUtils.timeDifferenceToStr(dto.getTaskStartTime(), dto.getTaskEndTime()));
                dto.setTaskRemovalTime(AnYiDateUtils.toLocalDateTime(entity.getRemovalTime()));
                historyTaskInfos.add(dto);
            });
            List<HistoricIdentityLinkLog> identityLinkLogs = historyService.createHistoricIdentityLinkLogQuery().processDefinitionId(instanceDetailDto.getProcessDefinitionId()).list();
            List<HistoricIdentityLinkLogEntity> identityLinkList = new ArrayList<>(identityLinkLogs.size());
            if (CollUtil.isNotEmpty(identityLinkLogs)) {
                identityLinkLogs.forEach(v -> {
                    if (v instanceof HistoricIdentityLinkLogEntity historic) {
                        identityLinkList.add(historic);
                    }
                });
            }
            // 补全审批意见
            List<Comment> processInstanceComments = taskService.getProcessInstanceComments(instanceDetailDto.getProcessInstanceId());

            if (CollUtil.isNotEmpty(processInstanceComments)) {
                Map<String, String> commentMap = new HashMap<>(processInstanceComments.size());
                processInstanceComments.forEach(v -> commentMap.put(v.getTaskId(), v.getFullMessage()));
                historyTaskInfos.forEach(v -> {
                    String comment = commentMap.get(v.getTaskId());
                    v.setApprovalOpinions(comment);
                });
            }
            instanceDetailDto.setHistoryTaskInfos(toHistoryTaskDetail(historyTaskInfos, identityLinkList));
            // 获取用户任务
            List<TasksHistoryDetailDto> historyTaskCommentInfos = new ArrayList<>(instanceDetailDto.getHistoryTaskInfos().size());
            instanceDetailDto.getHistoryTaskInfos().forEach(v -> {
                if (ActivityTypes.TASK_USER_TASK.equals(v.getActivityType())) {
                    historyTaskCommentInfos.add(v);
                }
            });
            instanceDetailDto.setHistoryTaskCommentInfos(historyTaskCommentInfos);
        }
        // 查询历史变量
        List<HistoricVariableInstance> list = historyService.createHistoricVariableInstanceQuery().processInstanceId(processInstanceId).list();
        if (CollUtil.isNotEmpty(list)) {
            List<HistoricVariableDto> historyVariableInfos = new ArrayList<>(list.size());
            list.forEach(v -> {
                HistoricVariableInstanceEntity variableInstanceEntity = (HistoricVariableInstanceEntity) v;
                HistoricVariableDto variableDto = HistoricVariableDto.builder().id(variableInstanceEntity.getId()).name(variableInstanceEntity.getName()).value(variableInstanceEntity.getTextValue()).createTime(AnYiDateUtils.toLocalDateTime(variableInstanceEntity.getCreateTime())).removalTime(AnYiDateUtils.toLocalDateTime(variableInstanceEntity.getRemovalTime())).variableState(HistoricVariableInstance.STATE_CREATED.equals(variableInstanceEntity.getState()) ? 1 : 0).build();
                historyVariableInfos.add(variableDto);
            });
            instanceDetailDto.setHistoryVariableInfos(historyVariableInfos);
        }
        return instanceDetailDto;
    }


    /**
     * 获取历史task信息
     *
     * @param historyTaskInfos ${@link List<TasksHistoryDetailDto>}
     * @param identityLinkList ${@link List<HistoricIdentityLinkLogEntity>}
     * @return List<TasksHistoryDetailDto> ${@link List<TasksHistoryDetailDto>}
     * @author zxh
     * @date 2021-07-31 12:58
     */
    private List<TasksHistoryDetailDto> toHistoryTaskDetail(List<TasksHistoryDetailDto> historyTaskInfos, List<HistoricIdentityLinkLogEntity> identityLinkList) {
        if (CollUtil.isNotEmpty(identityLinkList)) {
            List<String> userInfoIds = new ArrayList<>(identityLinkList.size());
            List<String> groupInfoIds = new ArrayList<>(identityLinkList.size());
            identityLinkList.forEach(v -> {
                IdentityType byTypeName = IdentityType.getByTypeName(v.getType());
                if (byTypeName == IdentityType.ASSIGNEE) {
                    userInfoIds.add(v.getAssignerId());
                }
                if (byTypeName == IdentityType.CANDIDATE) {
                    groupInfoIds.add(v.getGroupId());
                }
            });
            historyTaskInfos.forEach(v -> userInfoIds.add(v.getTaskAssignee()));
            Map<String, RoleInfo> groupInfoMap = new HashMap<>(groupInfoIds.size());
            if (CollUtil.isNotEmpty(groupInfoIds)) {
                AnYiResult<List<RoleInfo>> roleListByIds = remoteRoleService.getRoleListByIds(groupInfoIds);
                if (roleListByIds.isSuccess() && CollUtil.isNotEmpty(roleListByIds.getData())) {
                    List<RoleInfo> data = roleListByIds.getData();
                    data.forEach(v -> groupInfoMap.put(v.getRoleId(), v));
                }
            }

            Map<String, SimpleUserModel> userInfoMap = new HashMap<>(userInfoIds.size());
            if (CollUtil.isNotEmpty(userInfoIds)) {
                AnYiResult<List<SimpleUserModel>> userListByIds = remoteUserService.getUserListByIds(userInfoIds);
                if (userListByIds.isSuccess() && CollUtil.isNotEmpty(userListByIds.getData())) {
                    List<SimpleUserModel> data = userListByIds.getData();
                    data.forEach(v -> userInfoMap.put(v.getUserId(), v));
                }
            }
            historyTaskInfos.forEach(v -> {
                v.setTaskAssigneeInfo(userInfoMap.get(v.getTaskAssignee()));
                if (CollUtil.isNotEmpty(identityLinkList) && (CollUtil.isNotEmpty(groupInfoMap) || CollUtil.isNotEmpty(userInfoMap))) {
                    List<SimpleUserModel> userInfos = new ArrayList<>(identityLinkList.size());
                    List<RoleInfo> groupInfos = new ArrayList<>(identityLinkList.size());
                    identityLinkList.forEach(sv -> {
                        if (sv.getTaskId().equals(v.getTaskId())) {
                            SimpleUserModel rbacUserDto = userInfoMap.get(sv.getUserId());
                            if (Objects.nonNull(rbacUserDto)) {
                                userInfos.add(rbacUserDto);
                            }
                            RoleInfo rbacRoleDto = groupInfoMap.get(sv.getGroupId());
                            if (Objects.nonNull(rbacRoleDto)) {
                                groupInfos.add(rbacRoleDto);
                            }
                        }
                    });
                    v.setUserInfos(userInfos);
                    v.setGroupInfos(groupInfos);
                }
            });
        }
        return historyTaskInfos;
    }


    @Override
    public InstanceStaticDto instanceStatic() {
        HistoricProcessInstanceQuery processInstanceQuery = historyService.createHistoricProcessInstanceQuery();
        // 获取总实例
        long total = processInstanceQuery.count();
        // 获取完结实例
        long finish = processInstanceQuery.finished().count();
        InstanceStaticDto dto = new InstanceStaticDto();
        dto.setTotal(total);
        dto.setFinish(finish);
        dto.setUnFinish(total - finish);
        return dto;
    }
}
