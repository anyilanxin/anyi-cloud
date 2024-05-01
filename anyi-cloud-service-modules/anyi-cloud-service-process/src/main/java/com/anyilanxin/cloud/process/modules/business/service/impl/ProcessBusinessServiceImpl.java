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

package com.anyilanxin.cloud.process.modules.business.service.impl;

import com.alibaba.fastjson2.JSONObject;
import com.anyilanxin.cloud.corecommon.constant.AnYiResultStatus;
import com.anyilanxin.cloud.corecommon.exception.AnYiResponseException;
import com.anyilanxin.cloud.corecommon.model.common.AnYiPageResult;
import com.anyilanxin.cloud.corecommon.utils.AnYiDateUtils;
import com.anyilanxin.cloud.corecommon.utils.tree.AnYiTreeToolUtils;
import com.anyilanxin.cloud.coremvc.utils.AnYiUserContextUtils;
import com.anyilanxin.cloud.coreprocess.constant.AnYiBpmnConstant;
import com.anyilanxin.cloud.coreprocess.emuns.ElementInstanceState;
import com.anyilanxin.cloud.coreprocess.emuns.ProcessVariableType;
import com.anyilanxin.cloud.coreprocess.emuns.UserTaskState;
import com.anyilanxin.cloud.coreprocess.model.AnYiBpmnProcessInstanceModel;
import com.anyilanxin.cloud.coreprocess.model.task.SubmitApprovalModel;
import com.anyilanxin.cloud.database.utils.PageUtils;
import com.anyilanxin.cloud.process.extend.constant.impl.*;
import com.anyilanxin.cloud.process.extend.model.AnYiUserTaskPropertyModel;
import com.anyilanxin.cloud.process.modules.auxiliary.entity.SequenceFlowEntity;
import com.anyilanxin.cloud.process.modules.auxiliary.mapper.SequenceFlowMapper;
import com.anyilanxin.cloud.process.modules.auxiliary.service.ISequenceFlowService;
import com.anyilanxin.cloud.process.modules.business.controller.vo.*;
import com.anyilanxin.cloud.process.modules.business.service.IProcessBusinessCommonService;
import com.anyilanxin.cloud.process.modules.business.service.IProcessBusinessService;
import com.anyilanxin.cloud.process.modules.business.service.dto.*;
import com.anyilanxin.cloud.process.modules.business.service.mapstruct.*;
import com.anyilanxin.cloud.process.modules.manage.service.IProcessCategoryService;
import com.anyilanxin.cloud.process.modules.manage.service.dto.ProcessCategoryDto;
import com.anyilanxin.cloud.process.modules.manage.service.dto.ProcessTaskInfoDto;
import com.anyilanxin.cloud.process.utils.Base64FileUtils;
import com.anyilanxin.cloud.process.utils.ProcessBpmnUtils;
import com.anyilanxin.cloud.process.utils.ProcessCommonUtils;
import com.anyilanxin.cloud.process.utils.TaskUtils;
import com.anyilanxin.cloud.processadapter.model.*;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import io.seata.spring.annotation.GlobalTransactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.camunda.bpm.engine.*;
import org.camunda.bpm.engine.form.FormField;
import org.camunda.bpm.engine.form.TaskFormData;
import org.camunda.bpm.engine.history.*;
import org.camunda.bpm.engine.impl.persistence.entity.*;
import org.camunda.bpm.engine.impl.pvm.runtime.ActivityInstanceState;
import org.camunda.bpm.engine.repository.ProcessDefinition;
import org.camunda.bpm.engine.repository.ProcessDefinitionQuery;
import org.camunda.bpm.engine.runtime.Execution;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.runtime.ProcessInstanceModificationInstantiationBuilder;
import org.camunda.bpm.engine.task.Comment;
import org.camunda.bpm.engine.task.Task;
import org.camunda.bpm.engine.variable.VariableMap;
import org.camunda.bpm.model.bpmn.Bpmn;
import org.camunda.bpm.model.bpmn.BpmnModelInstance;
import org.camunda.bpm.model.bpmn.instance.InclusiveGateway;
import org.camunda.bpm.model.bpmn.instance.ParallelGateway;
import org.camunda.bpm.model.bpmn.instance.SequenceFlow;
import org.dromara.hutool.core.collection.CollUtil;
import org.dromara.hutool.core.date.BetweenFormatter;
import org.dromara.hutool.core.date.DateUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import static com.anyilanxin.cloud.corecommon.utils.AnYiDateUtils.localDateTimeToStamp;

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
public class ProcessBusinessServiceImpl implements IProcessBusinessService {
    private final RuntimeService runtimeService;
    private final ActivityBpmnTreeMap activityBpmnMap;
    private final ISequenceFlowService sequenceFlowService;
    private final SequenceFlowMapper sequenceFlowMapper;
    private final TaskService taskService;
    private final IProcessBusinessCommonService businessCommonService;
    private final FormService formService;
    private final HistoryService historyService;
    private final RepositoryService repositoryService;
    private final IProcessCategoryService categoryService;
    private final ActivityInstanceInfoMap instanceInfoMap;
    private final ActivityMultiInstanceMap multiInstanceMap;
    private final ActivityInstanceMap activityInstanceMap;
    private final ActivityInfoMap activityInfoMap;

    @Override
    @GlobalTransactional
    public ProcessCallbackResultModel submitProcess(FormSubmitProcessModel vo) {
        if (StringUtils.isBlank(vo.getProcessDefinitionKey()) && StringUtils.isBlank(vo.getProcessDefinitionId())) {
            throw new AnYiResponseException(AnYiResultStatus.VERIFICATION_FAILED, "流程定义key与流程定义id不能同时为空");
        }
        // 查询流程定义
        ProcessDefinitionQuery processDefinitionQuery = repositoryService.createProcessDefinitionQuery();
        ProcessDefinition processDefinition;
        if (StringUtils.isNotBlank(vo.getProcessDefinitionId())) {
            processDefinition = processDefinitionQuery.processDefinitionId(vo.getProcessDefinitionId()).singleResult();
        } else {
            processDefinition = processDefinitionQuery.processDefinitionKey(vo.getProcessDefinitionKey()).latestVersion().singleResult();
        }
        if (Objects.isNull(processDefinition)) {
            throw new AnYiResponseException(AnYiResultStatus.VERIFICATION_FAILED, "当前流程模型信息不存在");
        }
        // 合并表单数据与变量数据
        Map<String, Object> properties = new HashMap<>();
        if (CollUtil.isNotEmpty(vo.getApplyFormData())) {
            properties.putAll(vo.getApplyFormData());
        }
        if (CollUtil.isNotEmpty(vo.getProcessVariables())) {
            properties.putAll(vo.getProcessVariables());
        }
        if (Objects.nonNull(vo.getAttachmentInfo()) && CollUtil.isNotEmpty(vo.getAttachmentInfo().getAttachments())) {
            properties.put(ProcessVariableType.PROCESS_ATTACHMENT.getVariableKey(), vo.getAttachmentInfo());
        }
        // 启动实例
        ProcessInstance processInstance;
        String startFormKey = formService.getStartFormKey(processDefinition.getId());
        if (CollUtil.isNotEmpty(vo.getApplyFormData())) {
            // 表单数据处理
            properties.putAll(vo.getApplyFormData());
            // 保存一份表单原始值方便后续查询
            properties.put(ProcessVariableType.PROCESS_FORM.getVariableKey(), JSONObject.toJSONString(vo.getApplyFormData()));
            if (StringUtils.isNotBlank(vo.getBusinessKey())) {
                processInstance = formService.submitStartForm(processDefinition.getId(), vo.getBusinessKey(), properties);
            } else {
                processInstance = formService.submitStartForm(processDefinition.getId(), properties);
            }
        } else {
            if (StringUtils.isNotBlank(vo.getBusinessKey())) {
                processInstance = runtimeService.startProcessInstanceById(processDefinition.getId(), vo.getBusinessKey(), properties);
            } else {
                processInstance = runtimeService.startProcessInstanceById(processDefinition.getId(), properties);
            }
        }
        return getSubmitResult(processInstance.getId(), startFormKey, vo.getBusinessKey());
    }


    /**
     * 构建发起流程成功信息
     *
     * @param processInstanceId ${@link String} 实例信息
     * @param startFormKey      ${@link String} 表单key
     * @param businessKey       ${@link String} 业务id
     * @return ProcessSubmitResultModel ${@link ProcessCallbackResultModel}
     * @author zxh
     * @date 2021-12-07 15:22
     */
    private ProcessCallbackResultModel getSubmitResult(String processInstanceId, String startFormKey, String businessKey) {
        HistoricProcessInstance historicProcessInstance = historyService.createHistoricProcessInstanceQuery().processInstanceId(processInstanceId).singleResult();
        // 构建返回信息
        ProcessCallbackResultModel resultModel = new ProcessCallbackResultModel();
        resultModel.setProcessDefinitionId(historicProcessInstance.getProcessDefinitionId());
        resultModel.setBusinessKey(businessKey);
        resultModel.setProcessStartTime(AnYiDateUtils.toLocalDateTime(historicProcessInstance.getStartTime()));
        resultModel.setProcessInstanceId(processInstanceId);
        resultModel.setStartFormKey(startFormKey);
        List<Task> taskList = taskService.createTaskQuery().processInstanceId(processInstanceId).initializeFormKeys().list();
        List<ProcessCallbackResultModel.TaskInfo> taskInfoList = new ArrayList<>();
        if (CollUtil.isNotEmpty(taskList)) {
            taskList.forEach(v -> {
                TaskEntity taskEntity = (TaskEntity) v;
                ProcessCallbackResultModel.TaskInfo taskInfo = new ProcessCallbackResultModel.TaskInfo();
                taskInfo.setTaskId(taskEntity.getId());
                taskInfo.setAssignee(taskEntity.getAssignee());
                taskInfo.setStartTime(AnYiDateUtils.toLocalDateTime(taskEntity.getCreateTime()));
                taskInfo.setOwner(taskEntity.getOwner());
                taskInfo.setName(taskEntity.getName());
                taskInfo.setExecutionId(taskEntity.getExecutionId());
                taskInfo.setTaskDefinitionKey(taskEntity.getTaskDefinitionKey());
                taskInfo.setTaskFormKey(taskEntity.getFormKey());
                // 表单数据信息
                TaskFormData taskFormData = formService.getTaskFormData(taskEntity.getId());
                if (Objects.nonNull(taskFormData) && CollUtil.isNotEmpty(taskFormData.getFormFields())) {
                    List<FormField> formFields = taskFormData.getFormFields();
                    VariableMap taskFormVariables = formService.getTaskFormVariables(taskEntity.getId());
                }
                taskInfoList.add(taskInfo);
            });
        }
        resultModel.setTaskInfoList(taskInfoList);
        return resultModel;
    }


    @Override
    @GlobalTransactional
    public ProcessCallbackResultModel submitProcess(MsgSubmitProcessModel vo) {
        ProcessDefinitionQuery processDefinitionQuery = repositoryService.createProcessDefinitionQuery().incidentMessage(vo.getMessageName()).latestVersion();
        ProcessDefinition processDefinition = processDefinitionQuery.singleResult();
        if (Objects.isNull(processDefinition)) {
            throw new AnYiResponseException(AnYiResultStatus.VERIFICATION_FAILED, "当前流程模型信息不存在");
        }
        // 合并表单数据与变量数据
        Map<String, Object> properties = new HashMap<>();
        if (CollUtil.isNotEmpty(vo.getProcessVariables())) {
            properties.putAll(vo.getProcessVariables());
        }
        if (Objects.nonNull(vo.getAttachmentInfo()) && CollUtil.isNotEmpty(vo.getAttachmentInfo().getAttachments())) {
            properties.put(ProcessVariableType.PROCESS_ATTACHMENT.getVariableKey(), vo.getAttachmentInfo());
        }
        String startFormKey = formService.getStartFormKey(processDefinition.getId());
        if (CollUtil.isNotEmpty(vo.getStartFormData())) {
            // 表单数据处理
            vo.getStartFormData().forEach((k, v) -> {
                // 如果表单有时间类型会自动转为时间戳
//                if (v.isTime()) {
//                    LocalDateTime localDateTime = AnYiDateUtils.strToDate((String) v.getValue(), v.getTimeFormat());
//                    properties.put(k, AnYiDateUtils.localDateTimeToStamp(localDateTime));
//                } else {
//                    properties.put(k, v);
//                }
            });
            // 保存一份表单原始值方便后续查询
            properties.put(ProcessVariableType.PROCESS_FORM.getVariableKey(), JSONObject.toJSONString(vo.getStartFormData()));
        }
        ProcessInstance processInstance;
        if (StringUtils.isNotBlank(vo.getBusinessKey())) {
            processInstance = runtimeService.startProcessInstanceByMessageAndProcessDefinitionId(vo.getMessageName(), processDefinition.getId(), vo.getBusinessKey(), properties);
        } else {
            processInstance = runtimeService.startProcessInstanceByMessageAndProcessDefinitionId(vo.getMessageName(), processDefinition.getId(), properties);
        }
        return getSubmitResult(processInstance.getId(), startFormKey, vo.getBusinessKey());
    }


    @Override
    public ProcessCallbackResultModel reSubmit(FormReSubmitProcessModel vo) {
        ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(vo.getProcessInstanceId()).singleResult();
        if (Objects.isNull(processInstance)) {
            throw new AnYiResponseException(AnYiResultStatus.DATABASE_BASE_ERROR, "当前流程不存在，请检查");
        }
        boolean suspended = processInstance.isSuspended();
        if (suspended) {
            throw new AnYiResponseException(AnYiResultStatus.DATABASE_BASE_ERROR, "当前流程处理挂起状态，无法操作");
        }
        Object variable = runtimeService.getVariable(vo.getProcessInstanceId(), ProcessTagType.resubmit.getVariableKey());
        if (Objects.isNull(variable)) {
            throw new AnYiResponseException(AnYiResultStatus.DATABASE_BASE_ERROR, "当前流程不需要重新提交，请检查");
        }
        if (!ProcessTagType.resubmit.getType().equals(variable.toString())) {
            throw new AnYiResponseException(AnYiResultStatus.DATABASE_BASE_ERROR, "当前流程不需要重新提交，请检查");
        }
        // 查询最近审批任务是否存在为申请人任务
        List<Task> list = taskService.createTaskQuery().processInstanceId(vo.getProcessInstanceId()).taskVariableValueEquals(TaskType.APPLY_TASK.getVariableKey(), TaskType.APPLY_TASK.getValue()).taskAssignee(AnYiUserContextUtils.getUserId()).list();
        if (CollUtil.isEmpty(list)) {
            throw new AnYiResponseException(AnYiResultStatus.DATABASE_BASE_ERROR, "当前流程不需要重新提交，请检查");
        }
        SubmitApprovalModel model = new SubmitApprovalModel();
        model.setApprovalStatus(UserTaskState.AGREE.getValue());
        model.setApprovalOpinion("打回重填提交");
        // 执行重新任务提交
        list.forEach(v -> {
            // 提交流程审批意见
            TaskUtils.setApprovalAndComment(v, model);
            taskService.complete(v.getId());
        });
        return getSubmitResult(processInstance.getId(), null, processInstance.getBusinessKey());
    }


    @Override
    @Transactional(rollbackFor = {Exception.class, Error.class})
    public void cancelProcess(CancelProcessVo vo) {
        // 查询流程任务是否审批完成
        ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(vo.getProcessInstanceId()).singleResult();
        if (Objects.isNull(processInstance)) {
            throw new AnYiResponseException(AnYiResultStatus.VERIFICATION_FAILED, "审批已经完成或不存在,无法取消");
        }
        // 标记任务为作废
        List<Task> taskList = taskService.createTaskQuery().processInstanceId(vo.getProcessInstanceId()).list();
        taskList.forEach(v -> {
            // 提交流程审批意见
            SubmitApprovalModel model = SubmitApprovalModel.builder().approvalStatus(UserTaskState.INVALID.getValue()).approvalOpinion("审批被" + AnYiUserContextUtils.getRealName() + "取消").build();
            TaskUtils.setApprovalAndComment(v, model);
        });
        // 删除流程实例
        ProcessCommonUtils.customDeleteProcess(ProcessInstanceState.INVALID, runtimeService, vo.getProcessInstanceId(), processInstance.getId());
    }


    @Override
    @Transactional(rollbackFor = {Exception.class, Error.class}, readOnly = true)
    public AnYiPageResult<ProcessPageDto> selectProcessPage(ProcessQueryPageVo vo) {
        HistoricProcessInstanceQuery historicProcessInstanceQuery = historyService.createHistoricProcessInstanceQuery().startedBy(AnYiUserContextUtils.getUserId());
        // 处理查询条件问题
        if (Objects.nonNull(vo.getStartTime())) {
            historicProcessInstanceQuery.startedAfter(AnYiDateUtils.toDateTime(vo.getStartTime()));
        }
        if (Objects.nonNull(vo.getEndTime())) {
            historicProcessInstanceQuery.startedBefore(AnYiDateUtils.toDateTime(vo.getEndTime()));
        }
        if (StringUtils.isNotBlank(vo.getTitle())) {
            historicProcessInstanceQuery.variableValueLike(ProcessVariableType.PROCESS_TITLE.getVariableKey(), "%" + vo.getTitle() + "%");
        }
        if (StringUtils.isNotBlank(vo.getCategory())) {
            historicProcessInstanceQuery.variableValueEquals(ProcessVariableType.PROCESS_CATEGORY.getVariableKey(), vo.getCategory());
        }
        if (StringUtils.isNotBlank(vo.getBusinessKey())) {
            historicProcessInstanceQuery.processInstanceBusinessKey(vo.getBusinessKey());
        }
        if (Objects.nonNull(vo.getProcessState())) {
            switch (vo.getProcessState()) {
                case 1:
                    historicProcessInstanceQuery.active();
                    break;
                case 2:
                    historicProcessInstanceQuery.suspended();
                    break;
                case 3:
                    historicProcessInstanceQuery.completed();
                    break;
                case 4:
                    historicProcessInstanceQuery.variableValueEquals(ProcessInstanceState.REFUSED.getStrState(), 4);
                    break;
                case 5:
                    historicProcessInstanceQuery.variableValueEquals(ProcessInstanceState.INVALID.getStrState(), 5);
                    break;
                case 6:
                    historicProcessInstanceQuery.externallyTerminated();
                    break;
                case 7:
                    historicProcessInstanceQuery.internallyTerminated();
                    break;
            }
        }
        if (Objects.nonNull(vo.getFinishState())) {
            if (vo.getFinishState() == 0) {
                historicProcessInstanceQuery.unfinished();
            } else {
                historicProcessInstanceQuery.finished();
            }
        }
        // 表单精确匹配条件
        if (CollUtil.isNotEmpty(vo.getProcessFormEquals())) {
            vo.getProcessFormEquals().forEach((k, v) -> {
                if (StringUtils.isNotBlank(k) && StringUtils.isNotBlank(v)) {
                    historicProcessInstanceQuery.variableValueEquals(k, v);
                }
            });
        }

        // 表单模糊条件
        if (CollUtil.isNotEmpty(vo.getProcessFormLike())) {
            vo.getProcessFormLike().forEach((k, v) -> {
                if (StringUtils.isNotBlank(k) && StringUtils.isNotBlank(v)) {
                    historicProcessInstanceQuery.variableValueLike(k, "%" + v + "%");
                }
            });
        }
        // 表单时间条件
        if (CollUtil.isNotEmpty(vo.getProcessFormTime())) {
            vo.getProcessFormTime().forEach((k, v) -> {
                if (StringUtils.isNotBlank(k) && Objects.nonNull(v)) {
                    LocalDateTime startTime = v.getStartTime();
                    LocalDateTime endTime = v.getEndTime();
                    if (Objects.nonNull(startTime)) {
                        historicProcessInstanceQuery.variableValueGreaterThanOrEqual(k, localDateTimeToStamp(startTime));
                    }
                    if (Objects.nonNull(endTime)) {
                        historicProcessInstanceQuery.variableValueGreaterThanOrEqual(k, localDateTimeToStamp(endTime));
                    }
                }
            });
        }
        // 处理排序问题
        Set<String> ascSet = vo.getAscs();
        Set<String> descSet = vo.getDescs();
        if (CollUtil.isNotEmpty(ascSet)) {
            if (ascSet.contains("processEndTime")) {
                historicProcessInstanceQuery.orderByProcessInstanceEndTime().asc();
            }
            if (ascSet.contains("processStartTime")) {
                historicProcessInstanceQuery.orderByProcessInstanceStartTime().asc();
            }
            if (ascSet.contains("durationInMillis")) {
                historicProcessInstanceQuery.orderByProcessInstanceDuration().asc();
            }
        } else if (CollUtil.isNotEmpty(descSet)) {
            if (descSet.contains("processEndTime")) {
                historicProcessInstanceQuery.orderByProcessInstanceEndTime().desc();
            }
            if (descSet.contains("processStartTime")) {
                historicProcessInstanceQuery.orderByProcessInstanceStartTime().desc();
            }
            if (descSet.contains("durationInMillis")) {
                historicProcessInstanceQuery.orderByProcessInstanceDuration().desc();
            }
        } else {
            historicProcessInstanceQuery.orderByProcessInstanceStartTime().desc();
        }
        // 执行查询
        long count = historicProcessInstanceQuery.count();
        if (count <= 0) {
            return PageUtils.toPageData();
        }
        List<HistoricProcessInstance> historicProcessInstances = historicProcessInstanceQuery.listPage(vo.getCurrent(), vo.getSize());
        List<ProcessPageDto> processPageList = new ArrayList<>();
        // 查询流程变量以及数据转换
        Set<String> processInstanceIdSet = new HashSet<>(historicProcessInstances.size());
        historicProcessInstances.forEach(v -> {
            processInstanceIdSet.add(v.getId());
            ProcessPageDto dto = ProcessPageDto.builder().durationInMillis(v.getDurationInMillis()).processInstanceId(v.getId()).deleteReason(v.getDeleteReason()).processName(v.getProcessDefinitionName()).processEndTime(AnYiDateUtils.toLocalDateTime(v.getEndTime())).processStartTime(AnYiDateUtils.toLocalDateTime(v.getStartTime())).businessKey(v.getBusinessKey()).tenantId(v.getTenantId()).finishState(Objects.nonNull(v.getEndTime()) ? 1 : 0).build();
            if (Objects.nonNull(v.getDurationInMillis())) {
                dto.setDurationInMillisFormat(DateUtil.formatBetween(v.getDurationInMillis(), BetweenFormatter.Level.SECOND));
            }
            ProcessInstanceState byStrState = ProcessInstanceState.getByStrState(v.getState(), v.getDeleteReason());
            if (Objects.nonNull(byStrState)) {
                dto.setProcessState(byStrState.getState());
            }
            processPageList.add(dto);
        });
        Set<String> processCategorySet = new HashSet<>(historicProcessInstances.size());
        Map<String, Map<String, Map<String, HistoricVariableInstanceEntity>>> instanceVariables = businessCommonService.getInstanceVariableById(processInstanceIdSet, processCategorySet);
        // 获取流程类别信息并转换为map方便取值
        List<ProcessCategoryDto> processCategoryList = categoryService.selectListByCodes(processCategorySet);
        Map<String, String> processCategoryMap = new HashMap<>(16);
        processCategoryList.forEach(v -> processCategoryMap.put(v.getCategoryCode(), v.getCategoryName()));
        // 处理流程最后变量数据
        processPageList.forEach(v -> {
            Map<String, Map<String, HistoricVariableInstanceEntity>> currentInstanceMap = instanceVariables.get(v.getProcessInstanceId());
            if (CollUtil.isNotEmpty(currentInstanceMap)) {
                Map<String, HistoricVariableInstanceEntity> instanceHistoricVariableInstanceEntityMap = currentInstanceMap.get(v.getProcessInstanceId());
                // 流程类别
                HistoricVariableInstanceEntity instanceEntity = instanceHistoricVariableInstanceEntityMap.get(ProcessVariableType.PROCESS_CATEGORY.getVariableKey());
                if (Objects.nonNull(instanceEntity) && Objects.nonNull(instanceEntity.getValue())) {
                    v.setProcessCategory(instanceEntity.getValue().toString());
                    v.setProcessCategoryName(processCategoryMap.get(v.getProcessCategory()));
                }
                // 流程申请人完整信息
                instanceEntity = instanceHistoricVariableInstanceEntityMap.get(ProcessUserVariableType.ALL_USER_INFO_STR.getVariableKey());
                if (Objects.nonNull(instanceEntity) && Objects.nonNull(instanceEntity.getValue())) {
                    v.setApplyUserInfo(JSONObject.parseObject(instanceEntity.getValue().toString(), AnYiProcessUserModel.class));
                }
                // 流程标题
                instanceEntity = instanceHistoricVariableInstanceEntityMap.get(ProcessVariableType.PROCESS_TITLE.getVariableKey());
                if (Objects.nonNull(instanceEntity) && Objects.nonNull(instanceEntity.getValue())) {
                    v.setProcessTitle(instanceEntity.getValue().toString());
                }
                // 流程开始表单数据
                // Object startFormData =
                // currentInstanceMap.get(ProcessVariableType.PROCESS_FORM.getVariableKey());
                // if (Objects.nonNull(startFormData)) {
                // Map<String, Object> stringSubmitFormVoMap =
                // JSONObject.parseObject(applyUserInfo.toString(), new
                // TypeReference<Map<String, Object>>() {
                // });
                // v.setStartFormData(stringSubmitFormVoMap);
                // }
            }
            // 获取当前任务信息
            List<HistoricActivityInstance> list = historyService.createHistoricActivityInstanceQuery().processInstanceId(v.getProcessInstanceId()).activityType(ActivityTypes.TASK_USER_TASK).orderByHistoricActivityInstanceStartTime().asc().list();
            if (CollUtil.isNotEmpty(list)) {
                list.sort((o1, o2) -> {
                    HistoricActivityInstanceEntity o1Entity = (HistoricActivityInstanceEntity) o1;
                    HistoricActivityInstanceEntity o2Entity = (HistoricActivityInstanceEntity) o2;
                    long nowSequenceCounter = Objects.nonNull(o1Entity.getEndTime()) ? (o1Entity.getSequenceCounter() + o1Entity.getEndTime().getTime()) : o1Entity.getSequenceCounter() + o1Entity.getStartTime().getTime();
                    long currentSequenceCounter = Objects.nonNull(o2Entity.getEndTime()) ? (o2Entity.getSequenceCounter() + o2Entity.getEndTime().getTime()) : o2Entity.getSequenceCounter() + o2Entity.getStartTime().getTime();
                    return Long.compare(currentSequenceCounter, nowSequenceCounter);
                });
                HistoricActivityInstance activityInstance = list.get(0);
                v.setCurrentActivityName(activityInstance.getActivityName());
                v.setCurrentActivityType(activityInstance.getActivityType());
                v.setCurrentAssignee(activityInstance.getAssignee());
                v.setCurrentTaskId(activityInstance.getTaskId());
                v.setCurrentStartTime(activityInstance.getStartTime());
                v.setCurrentEndTime(activityInstance.getEndTime());
                v.setCurrentDurationInMillis(activityInstance.getDurationInMillis());
                if (Objects.nonNull(activityInstance.getDurationInMillis())) {
                    v.setCurrentDurationInMillisFormat(DateUtil.formatBetween(activityInstance.getDurationInMillis(), BetweenFormatter.Level.SECOND));
                }
                v.setCurrentId(activityInstance.getId());
            }
            // 审批意见
            Map<String, HistoricVariableInstanceEntity> taskHistoricVariableInstanceEntityMap = currentInstanceMap.get(v.getCurrentTaskId());
            if (CollUtil.isNotEmpty(taskHistoricVariableInstanceEntityMap)) {
                HistoricVariableInstanceEntity taskVariable = taskHistoricVariableInstanceEntityMap.get(TaskVariableType.NR_OF_INSTANCE_APPROVE_INFO.getVariableKey());
                if (Objects.nonNull(taskVariable) && Objects.nonNull(taskVariable.getValue())) {
                    SubmitApprovalModel submitApprovalModel = JSONObject.parseObject(taskVariable.getValue().toString(), SubmitApprovalModel.class);
                    ApprovalInfoModel optionInfos = new ApprovalInfoModel();
                    optionInfos.setApprovalStatus(submitApprovalModel.getApprovalStatus());
                    v.setCurrentApprovalStatus(submitApprovalModel.getApprovalStatus());
                    v.setCurrentOptionInfo(optionInfos);
                }
                // 当前任务类型
                taskVariable = taskHistoricVariableInstanceEntityMap.get(TaskVariableType.TASK_TYPE.getVariableKey());
                if (Objects.nonNull(taskVariable) && Objects.nonNull(taskVariable.getValue())) {
                    v.setCurrentTaskType(taskVariable.getValue().toString());
                }
            }
        });
        return PageUtils.toPageData(count, processPageList);
    }


    @Override
    @Transactional(rollbackFor = {Exception.class, Error.class}, readOnly = true)
    public InstanceApplyInfoDto selectInstanceApplyInfo(String processInstanceId) {
        HistoricProcessInstanceEntity instanceEntity = getHistoricProcessInstance(processInstanceId);
        InstanceApplyInfoDto result = InstanceApplyInfoDto.builder().durationInMillis(instanceEntity.getDurationInMillis()).processInstanceId(instanceEntity.getId()).processName(instanceEntity.getProcessDefinitionName()).processEndTime(AnYiDateUtils.toLocalDateTime(instanceEntity.getEndTime())).processStartTime(AnYiDateUtils.toLocalDateTime(instanceEntity.getStartTime())).businessKey(instanceEntity.getBusinessKey()).tenantId(instanceEntity.getTenantId()).finishState(Objects.nonNull(instanceEntity.getEndTime()) ? 1 : 0).build();
        if (Objects.nonNull(instanceEntity.getDurationInMillis())) {
            result.setDurationInMillisFormat(DateUtil.formatBetween(instanceEntity.getDurationInMillis(), BetweenFormatter.Level.SECOND));
        }
        List<HistoricVariableInstance> variableInstances = historyService.createHistoricVariableInstanceQuery().processInstanceId(processInstanceId).list();
        if (CollUtil.isNotEmpty(variableInstances)) {
            // 变量转为map方便处理
            Map<String, Object> processVariableMap = new HashMap<>(variableInstances.size());
            variableInstances.forEach(v -> processVariableMap.put(v.getName(), v.getValue()));
            // 流程类型信息
            Object processCategory = processVariableMap.get(ProcessVariableType.PROCESS_CATEGORY.getVariableKey());
            if (Objects.nonNull(processCategory)) {
                result.setProcessCategory(processCategory.toString());
                ProcessCategoryDto processCategoryDto = categoryService.selectByCode(result.getProcessCategory());
                if (Objects.nonNull(processCategoryDto)) {
                    result.setProcessCategoryName(processCategoryDto.getCategoryName());
                }
            }
            // 流程申请人信息
            Object applyUserInfo = processVariableMap.get(ProcessUserVariableType.ALL_USER_INFO_STR.getVariableKey());
            if (Objects.nonNull(applyUserInfo)) {
                result.setUserInfo(JSONObject.parseObject(applyUserInfo.toString(), AnYiProcessUserModel.class));
            }
            // 流程标题信息
            Object startTitle = processVariableMap.get(ProcessVariableType.PROCESS_TITLE.getVariableKey());
            if (Objects.nonNull(startTitle)) {
                result.setStartTitle(startTitle.toString());
            }
            // 流程申请表单信息
            Object startFormData = processVariableMap.get(ProcessVariableType.PROCESS_FORM.getVariableKey());
            if (Objects.nonNull(startFormData)) {
                result.setStartFormData(JSONObject.parseObject(startFormData.toString(), ProcessFormModel.class));
            }
        }
        return result;
    }


    @Override
    @Transactional(rollbackFor = {Exception.class, Error.class}, readOnly = true)
    public List<InstanceUserTaskDto> selectUserTaskInstance(String processInstanceId, boolean checkInstance) {
        if (checkInstance) {
            getHistoricProcessInstance(processInstanceId);
        }
        List<InstanceUserTaskDto> result = new ArrayList<>(128);
        Set<String> activityInstanceParentIdSet = new HashSet<>(64);
        Map<String, InstanceUserTaskDto> allUserTaskActivityMap = new HashMap<>(64);
        // 分离用户任务与多实例
        getActInstance(processInstanceId).forEach(v -> {
            InstanceUserTaskDto instanceUserTaskDto = activityInfoMap.dToE(v);
            if (Objects.nonNull(v.getDurationInMillis())) {
                instanceUserTaskDto.setDurationInMillisFormat(DateUtil.formatBetween(v.getDurationInMillis(), BetweenFormatter.Level.SECOND));
            }
            instanceUserTaskDto.setName(v.getActivityName());
            if (ActivityTypes.MULTI_INSTANCE_BODY.equals(v.getActivityType())) {
                instanceUserTaskDto.setMultiInstance(true);
                result.add(instanceUserTaskDto);
                activityInstanceParentIdSet.add(v.getId());
            } else if (ActivityTypes.TASK_USER_TASK.equals(v.getActivityType())) {
                allUserTaskActivityMap.put(v.getTaskId(), instanceUserTaskDto);
            }
        });
        // 查询流程用户任务
        List<HistoricTaskInstance> taskInstanceList = historyService.createHistoricTaskInstanceQuery().or().taskVariableValueEquals(TaskVariableType.TASK_TYPE.getVariableKey(), TaskType.APPROVAL_TASK.getValue()).taskVariableValueEquals(TaskVariableType.TASK_TYPE.getVariableKey(), TaskType.TURN_OVER_TASK.getValue()).taskVariableValueEquals(TaskVariableType.TASK_TYPE.getVariableKey(), TaskType.URGENT_TASK.getValue()).taskVariableValueEquals(TaskVariableType.TASK_TYPE.getVariableKey(), TaskType.DELEGATE_TASK.getValue()).endOr().processInstanceId(processInstanceId).list();
        if (CollUtil.isNotEmpty(taskInstanceList)) {
            taskInstanceList.forEach(v -> {
                InstanceUserTaskDto instanceUserTaskDto = allUserTaskActivityMap.get(v.getId());
                if (Objects.nonNull(instanceUserTaskDto)) {
                    HistoricTaskInstanceEntity instance = (HistoricTaskInstanceEntity) v;
                    instanceUserTaskDto = instanceUserTaskDto.toBuilder().owner(instance.getOwner()).description(instance.getDescription()).dueDate(instance.getDueDate()).followUpDate(instance.getFollowUpDate()).priority(instance.getPriority()).parentTaskId(instance.getParentTaskId()).activityInstanceId(instance.getActivityInstanceId()).taskDefinitionKey(instance.getTaskDefinitionKey()).build();
                    if (Objects.nonNull(instance.getDurationInMillis())) {
                        instanceUserTaskDto.setDurationInMillisFormat(DateUtil.formatBetween(instance.getDurationInMillis(), BetweenFormatter.Level.SECOND));
                    }
                    // 补全审批意见
                    List<Comment> taskComments = taskService.getTaskComments(instanceUserTaskDto.getTaskId());
                    if (CollUtil.isNotEmpty(taskComments)) {
                        ApprovalInfoModel model = new ApprovalInfoModel();
                        List<TaskOptionInfoModel> approvalOpinions = new ArrayList<>(taskComments.size());
                        List<String> allCommentStr = new ArrayList<>(taskComments.size());
                        taskComments.forEach(sv -> {
                            if (StringUtils.isNotBlank(sv.getFullMessage())) {
                                allCommentStr.add(sv.getFullMessage());
                            }
                            TaskOptionInfoModel infoDto = TaskOptionInfoModel.builder().comment(sv.getFullMessage()).submitTime(sv.getTime()).build();
                            approvalOpinions.add(infoDto);
                            model.setApprovalOpinions(approvalOpinions);
                        });
                        instanceUserTaskDto.setOptionInfos(model);
                        if (CollUtil.isNotEmpty(allCommentStr)) {
                            instanceUserTaskDto.setAllCommentStr(StringUtils.join(allCommentStr, "<br/>"));
                        }
                    }
                    allUserTaskActivityMap.put(v.getId(), instanceUserTaskDto);
                }
            });
        }
        // 流程实例变量
        List<HistoricVariableInstance> variableInstanceList = historyService.createHistoricVariableInstanceQuery().processInstanceId(processInstanceId).list();
        if (CollUtil.isNotEmpty(variableInstanceList)) {
            // 数据处理
            Map<String, Map<String, HistoricVariableInstanceEntity>> historicTaskVariableInstanceMap = new HashMap<>(256);
            variableInstanceList.forEach(v -> {
                String taskId = v.getTaskId();
                HistoricVariableInstanceEntity instanceEntity = (HistoricVariableInstanceEntity) v;
                if (StringUtils.isNotBlank(taskId)) {
                    Map<String, HistoricVariableInstanceEntity> variableInstanceTaskEntityMap = historicTaskVariableInstanceMap.get(taskId);
                    if (CollUtil.isEmpty(variableInstanceTaskEntityMap)) {
                        variableInstanceTaskEntityMap = new HashMap<>(64);
                    }
                    variableInstanceTaskEntityMap.put(v.getName(), instanceEntity);
                    historicTaskVariableInstanceMap.put(taskId, variableInstanceTaskEntityMap);
                }
            });
            // 补全流程其余信息并且分离多实例与子任务
            Map<String, List<InstanceUserTaskDto>> subActivityInstanceMap = new HashMap<>(128);
            allUserTaskActivityMap.forEach((k, v) -> {
                // 用户任务变量
                Map<String, HistoricVariableInstanceEntity> variableInstanceTaskEntityMap = historicTaskVariableInstanceMap.get(k);
                if (CollUtil.isNotEmpty(variableInstanceTaskEntityMap)) {
                    // 审批状态以及审批附件信息
                    if (Objects.nonNull(v.getEndTime())) {
                        HistoricVariableInstanceEntity historicVariableInstanceEntity = variableInstanceTaskEntityMap.get(TaskVariableType.NR_OF_INSTANCE_APPROVE_INFO.getVariableKey());
                        if (Objects.nonNull(historicVariableInstanceEntity) && Objects.nonNull(historicVariableInstanceEntity.getValue())) {
                            SubmitApprovalModel model = JSONObject.parseObject(historicVariableInstanceEntity.getValue().toString(), SubmitApprovalModel.class);
                            ApprovalInfoModel optionInfos = v.getOptionInfos();
                            if (Objects.isNull(optionInfos)) {
                                optionInfos = new ApprovalInfoModel();
                            }
                            optionInfos.setApprovalStatus(model.getApprovalStatus());
                            optionInfos.setAttachmentInfos(model.getAttachmentInfos());
                            v.setOptionInfos(optionInfos);
                            v.setApprovalStatus(model.getApprovalStatus());
                        }
                    }
                    // 审批人信息
                    HistoricVariableInstanceEntity historicVariableInstanceEntity = variableInstanceTaskEntityMap.get(TaskUserVariableType.ALL_USER_INFO_STR.getVariableKey());
                    if (Objects.nonNull(historicVariableInstanceEntity) && Objects.nonNull(historicVariableInstanceEntity.getValue())) {
                        v.setAssigneeUserInfo(JSONObject.parseObject(historicVariableInstanceEntity.getValue().toString(), AnYiProcessUserModel.class));
                    }
                    // 委托代理人信息
                    historicVariableInstanceEntity = variableInstanceTaskEntityMap.get(TaskVariableType.NR_OF_INSTANCE_DELEGATE_USER_INFO.getVariableKey());
                    if (Objects.nonNull(historicVariableInstanceEntity) && Objects.nonNull(historicVariableInstanceEntity.getValue())) {
                        v.setOwnerUserInfo(JSONObject.parseObject(historicVariableInstanceEntity.getValue().toString(), AnYiProcessUserModel.class));
                    }
                    // 流程任务标题
                    historicVariableInstanceEntity = variableInstanceTaskEntityMap.get(TaskVariableType.TASK_TITLE.getVariableKey());
                    if (Objects.nonNull(historicVariableInstanceEntity) && Objects.nonNull(historicVariableInstanceEntity.getValue())) {
                        v.setTaskTitle(historicVariableInstanceEntity.getValue().toString());
                    }
                    // 任务操作类型
                    historicVariableInstanceEntity = variableInstanceTaskEntityMap.get(TaskVariableType.NR_OF_INSTANCE_HANDLE_TYPE.getVariableKey());
                    if (Objects.nonNull(historicVariableInstanceEntity) && Objects.nonNull(historicVariableInstanceEntity.getValue())) {
                        TaskHandleType handleType = TaskHandleType.getByType(historicVariableInstanceEntity.getValue().toString());
                        if (Objects.nonNull(handleType)) {
                            v.setHandleType(handleType.getType());
                            v.setHandleTypeDescribe(handleType.getDescribe());
                        }
                    }
                    // 任务表单
                    historicVariableInstanceEntity = variableInstanceTaskEntityMap.get(TaskVariableType.TASK_FORM.getVariableKey());
                    if (Objects.nonNull(historicVariableInstanceEntity) && Objects.nonNull(historicVariableInstanceEntity.getValue())) {
                        v.setFormModel(JSONObject.parseObject(historicVariableInstanceEntity.getValue().toString(), ProcessFormModel.class));
                    }
                    // 模型user task property信息
                    historicVariableInstanceEntity = variableInstanceTaskEntityMap.get(AnYiUserTaskPropertyModel.USER_TASK);
                    if (Objects.nonNull(historicVariableInstanceEntity) && Objects.nonNull(historicVariableInstanceEntity.getValue())) {
                        AnYiUserTaskPropertyModel userTaskPropertyModel = JSONObject.parseObject(historicVariableInstanceEntity.getValue().toString(), AnYiUserTaskPropertyModel.class);
                        v.setPropertyModel(userTaskPropertyModel);
                        v.setFollowUpDateStr(userTaskPropertyModel.getFollowUpDate().getValue().getValue());
                        v.setFollowUpFormatDate(userTaskPropertyModel.getFollowUpDate().getValue().getValueDescribe());
                        v.setDueDateStr(userTaskPropertyModel.getDueDate().getValue().getValue());
                        v.setDueFormatDate(userTaskPropertyModel.getDueDate().getValue().getValueDescribe());
                    }
                }
                // 分离多实例子任务
                String parentActivityInstanceId = v.getParentActivityInstanceId();
                if (activityInstanceParentIdSet.contains(parentActivityInstanceId)) {
                    List<InstanceUserTaskDto> userTaskDtos = subActivityInstanceMap.get(parentActivityInstanceId);
                    if (CollUtil.isEmpty(userTaskDtos)) {
                        userTaskDtos = new ArrayList<>(32);
                    }
                    userTaskDtos.add(v);
                    subActivityInstanceMap.put(parentActivityInstanceId, userTaskDtos);
                } else {
                    result.add(v);
                }
            });
            // 多实例信息组装到返回结果
            result.forEach(v -> {
                if (v.isMultiInstance()) {
                    List<InstanceUserTaskDto> userTaskList = subActivityInstanceMap.get(v.getId());
                    if (CollUtil.isNotEmpty(userTaskList)) {
                        Collections.sort(userTaskList);
                        InstanceUserTaskDto instanceUserTaskDto = userTaskList.get(0);
                        // 多实例父级基本信息
                        v.setName(instanceUserTaskDto.getName());
                        v.setPropertyModel(instanceUserTaskDto.getPropertyModel());
                        v.setTaskDefinitionKey(instanceUserTaskDto.getTaskDefinitionKey());
                        v.setDueDate(instanceUserTaskDto.getDueDate());
                        v.setDescription(instanceUserTaskDto.getDescription());
                        v.setFollowUpDate(instanceUserTaskDto.getFollowUpDate());
                        v.setPriority(instanceUserTaskDto.getPriority());
                        v.setParentTaskId(instanceUserTaskDto.getParentTaskId());
                        v.setMultiInstanceTasks(userTaskList);
                    }
                }
            });
        }
        Collections.sort(result);
        return result;
    }


    /**
     * 查询流程历史实例
     *
     * @param processInstanceId ${@link String} 流程实例id
     * @return HistoricProcessInstanceEntity ${@link HistoricProcessInstanceEntity}
     * @author zxh
     * @date 2022-01-04 00:58
     */
    private HistoricProcessInstanceEntity getHistoricProcessInstance(String processInstanceId) {
        HistoricProcessInstance historicProcessInstance = historyService.createHistoricProcessInstanceQuery().processInstanceId(processInstanceId).singleResult();
        if (Objects.isNull(historicProcessInstance)) {
            throw new AnYiResponseException(AnYiResultStatus.DATABASE_BASE_ERROR, "未查询到流程实例信息");
        }
        return (HistoricProcessInstanceEntity) historicProcessInstance;
    }


    /**
     * 获取活动信息
     *
     * @param processInstanceId ${@link String} 流程实例id
     * @return List<HistoricActivityInstanceEntity> ${@link List<HistoricActivityInstanceEntity>}
     * @author zxh
     * @date 2021-12-29 12:26
     */
    private List<HistoricActivityInstanceEntity> getActInstance(String processInstanceId) {
        List<HistoricActivityInstance> list = historyService.createHistoricActivityInstanceQuery().processInstanceId(processInstanceId).list();
        if (CollUtil.isEmpty(list)) {
            throw new AnYiResponseException(AnYiResultStatus.DATABASE_BASE_ERROR, "未查询到当前流程历史活动信息");
        }
        return list.stream().map(v -> (HistoricActivityInstanceEntity) v).collect(Collectors.toList());
    }


    /**
     * 获取运行的执行信息
     *
     * @param processInstanceId
     * @return Execution>
     * @author zxh
     * @date 2022-06-13 11:36
     */
    private Map<String, ExecutionEntity> getExecutionMap(String processInstanceId) {
        Map<String, ExecutionEntity> result = new HashMap<>(32);
        List<Execution> list = runtimeService.createExecutionQuery().processInstanceId(processInstanceId).list();
        if (CollUtil.isNotEmpty(list)) {
            list.forEach(v -> {
                ExecutionEntity executionEntity = (ExecutionEntity) v;
                result.put(executionEntity.getId(), executionEntity);
            });
        }
        return result;
    }


    @Override
    @Transactional(rollbackFor = {Exception.class, Error.class})
    public void urgentTask(ProcessUrgentVo vo) {
        // 查询所有待办任务
        List<Task> list = taskService.createTaskQuery().processInstanceId(vo.getProcessInstanceId()).active().list();
        if (CollUtil.isEmpty(list)) {
            throw new AnYiResponseException(AnYiResultStatus.VERIFICATION_FAILED, "当前流程不存在待办任务,无法催办");
        }
        // 标记任务为催办任务(必须有审批人)
        list.forEach(v -> {
            // 标记任务为催办任务
            TaskUtils.setTaskType(v, TaskType.URGENT_TASK);
            // 发起通知
        });
    }


    @Override
    public List<ProcessTaskInfoDto> selectUserTaskByProcessKeyOrId(String processKeyOrId) {
        InputStream processModel = repositoryService.getProcessModel(processKeyOrId);
        if (Objects.isNull(processModel)) {
            ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery().processDefinitionKey(processKeyOrId).latestVersion().singleResult();
            if (Objects.isNull(processDefinition)) {
                throw new AnYiResponseException(AnYiResultStatus.VERIFICATION_FAILED, "模型不存在");
            }
            processModel = repositoryService.getProcessModel(processDefinition.getId());
        }
        Map<String, ProcessTaskInfoDto> bpmnUserTaskMap = ProcessBpmnUtils.getBpmnUserTaskToMap(processModel);
        if (CollUtil.isNotEmpty(bpmnUserTaskMap)) {
            List<ProcessTaskInfoDto> userTaskInfoList = new ArrayList<>(bpmnUserTaskMap.size());
            bpmnUserTaskMap.forEach((k, v) -> userTaskInfoList.add(v));
            Collections.sort(userTaskInfoList);
            return userTaskInfoList;
        }
        return Collections.emptyList();
    }


    @Override
    public AnYiBpmnProcessInstanceModel queryBpmnProcessInstance(String processInstanceId) {
        AnYiBpmnProcessInstanceModel result = new AnYiBpmnProcessInstanceModel();
        // 基本信息
        HistoricProcessInstanceEntity historicProcessInstance = getHistoricProcessInstance(processInstanceId);
        InputStream processModel = repositoryService.getProcessModel(historicProcessInstance.getProcessDefinitionId());
        if (Objects.isNull(processModel)) {
            throw new AnYiResponseException(AnYiResultStatus.DATABASE_BASE_ERROR, "流程模型不存在");
        }
        result.setId(processInstanceId);
        result.setBpmnProcessDefinitionKey(historicProcessInstance.getProcessDefinitionKey());
        result.setBpmnProcessName(historicProcessInstance.getProcessDefinitionName());
        result.setProcessVersion(historicProcessInstance.getProcessDefinitionVersion());
        ProcessDefinitionEntity processDefinition = (ProcessDefinitionEntity) repositoryService.createProcessDefinitionQuery().processDefinitionId(historicProcessInstance.getProcessDefinitionId()).singleResult();
        BpmnModelInstance bpmnModelInstance = repositoryService.getBpmnModelInstance(processDefinition.getId());
        result.setCategory(processDefinition.getCategory());
        ProcessCategoryDto processCategoryDto = categoryService.selectByCode(result.getCategory());
        if (Objects.nonNull(processCategoryDto)) {
            result.setCategoryName(processCategoryDto.getCategoryName());
        }
        result.setFinished(Objects.nonNull(historicProcessInstance.getEndTime()) ? 1 : 0);
        if (Objects.nonNull(historicProcessInstance.getDurationInMillis())) {
            result.setDurationInMillisFormat(DateUtil.formatBetween(historicProcessInstance.getDurationInMillis(), BetweenFormatter.Level.SECOND));
        }
        result.setEndDate(AnYiDateUtils.toLocalDateTime(historicProcessInstance.getEndTime()));
        result.setStartDate(AnYiDateUtils.toLocalDateTime(historicProcessInstance.getStartTime()));
        result.setBpmnBase64(Base64FileUtils.inputStreamToBase64(processModel));
        List<HistoricActivityInstanceEntity> activityInstanceList = getActInstance(processInstanceId);

        List<AnYiBpmnProcessInstanceModel.BpmnActivityInstanceModel> parentActivityInstanceList = new ArrayList<>(activityInstanceList.size());
        List<AnYiBpmnProcessInstanceModel.BpmnActivityInstanceModel> childActivityInstanceList = new ArrayList<>(activityInstanceList.size());
        List<AnYiBpmnProcessInstanceModel.BpmnActivityInstanceModel> activityInstanceAllList = new ArrayList<>(activityInstanceList.size());
        Map<String, Integer> multiInstance = new HashMap<>(activityInstanceList.size());
        Map<String, String> multiInstanceIds = new HashMap<>(activityInstanceList.size());
        Map<String, Set<String>> multiInstanceSubTaskIds = new HashMap<>(activityInstanceList.size());
        Map<String, Set<String>> multiInstanceSubActivityInstanceIds = new HashMap<>(activityInstanceList.size());
        Map<String, Integer> multiInstanceComplete = new HashMap<>(activityInstanceList.size());
        Map<String, Set<String>> processInstanceSequenceFlow = sequenceFlowService.getSequenceFlowByInstanceId(processInstanceId);
        // 最终流程实例信息
        Map<String, Map<String, HistoricVariableInstanceEntity>> instanceVariableById = businessCommonService.getInstanceVariableById(processInstanceId);
        for (HistoricActivityInstanceEntity v : activityInstanceList) {
            AnYiBpmnProcessInstanceModel.BpmnActivityInstanceModel detailDto = new AnYiBpmnProcessInstanceModel.BpmnActivityInstanceModel();
            detailDto.setId(v.getId());
            detailDto.setTaskId(v.getTaskId());
            Map<String, HistoricVariableInstanceEntity> stringHistoricVariableInstanceEntityMap = instanceVariableById.get(v.getId());
            if (CollUtil.isNotEmpty(stringHistoricVariableInstanceEntityMap)) {
                HistoricVariableInstanceEntity historicVariableInstanceEntity = stringHistoricVariableInstanceEntityMap.get(TaskVariableType.NR_OF_INSTANCE_HANDLE_TYPE.getVariableKey());
                if (Objects.nonNull(historicVariableInstanceEntity)) {
                    Object value = historicVariableInstanceEntity.getValue();
                    if (Objects.nonNull(value) && TaskHandleType.SUBTRACT_SIGN.getType().equals(value.toString())) {
                        continue;
                    }
                }
            }
            detailDto.setParentActivityInstanceId(v.getParentActivityInstanceId());
            detailDto.setBpmnElementId(v.getActivityId());
            if (StringUtils.isNotBlank(v.getParentActivityInstanceId())) {
                Set<String> subActivityInstanceIds = multiInstanceSubTaskIds.get(v.getParentActivityInstanceId());
                if (CollUtil.isEmpty(subActivityInstanceIds)) {
                    subActivityInstanceIds = new HashSet<>();
                }
                subActivityInstanceIds.add(v.getId());
                multiInstanceSubActivityInstanceIds.put(v.getParentActivityInstanceId(), subActivityInstanceIds);
                if (StringUtils.isNotBlank(v.getTaskId())) {
                    Set<String> subTaskIds = multiInstanceSubTaskIds.get(v.getParentActivityInstanceId());
                    if (CollUtil.isEmpty(subTaskIds)) {
                        subTaskIds = new HashSet<>();
                    }
                    subTaskIds.add(v.getTaskId());
                    multiInstanceSubTaskIds.put(v.getParentActivityInstanceId(), subTaskIds);
                }
            }

            detailDto.setBpmnElementType(v.getActivityType());
            detailDto.setBpmnElementName(v.getActivityName());
            detailDto.setFinished(Objects.nonNull(v.getEndTime()) ? 1 : 0);
            if (Objects.nonNull(v.getDurationInMillis())) {
                detailDto.setDurationInMillisFormat(DateUtil.formatBetween(v.getDurationInMillis(), BetweenFormatter.Level.SECOND));
            }
            detailDto.setEndDate(AnYiDateUtils.toLocalDateTime(v.getEndTime()));
            detailDto.setStartDate(AnYiDateUtils.toLocalDateTime(v.getStartTime()));
            if (detailDto.getBpmnElementType().endsWith(AnYiBpmnConstant.MULTI_INSTANCE_BODY)) {
                detailDto.setMultiInstance(1);
                multiInstanceIds.put(v.getId(), v.getId());
                multiInstance.put(v.getId(), 0);
                multiInstanceComplete.put(v.getId(), 0);
            }
            detailDto.setSourceSequenceFlows(processInstanceSequenceFlow.get(v.getId()));
            switch (v.getActivityInstanceState()) {
                case 0:
                case 3:
                    detailDto.setElementState(ElementInstanceState.ELEMENT_ACTIVATING);
                    break;
                case 2:
                    detailDto.setElementState(ElementInstanceState.ELEMENT_TERMINATED);
                    break;
                default:
                    detailDto.setElementState(ElementInstanceState.ELEMENT_COMPLETED);
            }
            if (ActivityTypes.GATEWAY_INCLUSIVE.equals(detailDto.getBpmnElementType()) || ActivityTypes.GATEWAY_PARALLEL.equals(detailDto.getBpmnElementType())) {
                if (v.getActivityInstanceState() == ActivityInstanceState.DEFAULT.getStateCode()) {
                    if (Objects.nonNull(v.getStartTime()) && Objects.nonNull(v.getEndTime())) {
                        detailDto.setElementState(ElementInstanceState.ELEMENT_COMPLETED);
                    }
                }
            }
            activityInstanceAllList.add(detailDto);
            if (StringUtils.isBlank(detailDto.getParentActivityInstanceId())) {
                parentActivityInstanceList.add(detailDto);
            } else {
                childActivityInstanceList.add(detailDto);
            }
        }
        activityInstanceAllList.forEach(v -> {
            Integer multiInstanceNum = multiInstance.get(v.getParentActivityInstanceId());
            if (Objects.nonNull(multiInstanceNum)) {
                multiInstance.put(v.getParentActivityInstanceId(), multiInstanceNum + 1);
                if (v.getFinished() == 1) {
                    multiInstanceComplete.put(v.getParentActivityInstanceId(), multiInstanceComplete.get(v.getParentActivityInstanceId()) + 1);
                }
            }
        });
        parentActivityInstanceList.forEach(v -> {
            if (v.getMultiInstance() == 1) {
                v.setMultiInstanceNum(multiInstance.get(v.getId()));
                //v.setBpmnElementId(StringUtils.removeEnd(v.getBpmnElementId(), "#" + AnYiBpmnConstant.MULTI_INSTANCE_BODY));
                v.setMultiCompleteInstanceNum(multiInstanceComplete.get(v.getId()));
            }
        });
        activityInstanceAllList.forEach(v -> {
            if (v.getMultiInstance() == 1) {
                v.setMultiInstanceNum(multiInstance.get(v.getId()));
                v.setMultiCompleteInstanceNum(multiInstanceComplete.get(v.getId()));
            }
        });
        // 分离多实例与普通任务
        List<AnYiBpmnProcessInstanceModel.BpmnActivityInstanceModel> multiInstanceListParent = parentActivityInstanceList.stream()
                .filter(v -> v.getBpmnElementId().endsWith("#" + AnYiBpmnConstant.MULTI_INSTANCE_BODY))
                .peek(v -> {
                    Set<String> subActivityInstanceIds = multiInstanceSubActivityInstanceIds.get(v.getId());
                    Set<String> subTaskIds = multiInstanceSubTaskIds.get(v.getId());
                    v.setSubTaskIds(subTaskIds);
                    v.setSubActivityInstanceIds(subActivityInstanceIds);
                    v.setBpmnElementId(StringUtils.removeEnd(v.getBpmnElementId(), "#" + AnYiBpmnConstant.MULTI_INSTANCE_BODY));
                })
                .collect(Collectors.toList());
        List<AnYiBpmnProcessInstanceModel.BpmnActivityInstanceModel> activityInstanceListParent = parentActivityInstanceList.stream()
                .filter(v -> !v.getBpmnElementId().endsWith("#" + AnYiBpmnConstant.MULTI_INSTANCE_BODY) && StringUtils.isBlank(multiInstanceIds.get(v.getParentActivityInstanceId())))
                .collect(Collectors.toList());


        List<AnYiBpmnProcessInstanceModel.BpmnActivityInstanceModel> multiInstanceListSub = activityInstanceAllList.stream()
                .filter(v -> v.getBpmnElementId().endsWith("#" + AnYiBpmnConstant.MULTI_INSTANCE_BODY))
                .peek(v -> {
                    Set<String> subActivityInstanceIds = multiInstanceSubActivityInstanceIds.get(v.getId());
                    Set<String> subTaskIds = multiInstanceSubTaskIds.get(v.getId());
                    v.setSubTaskIds(subTaskIds);
                    v.setSubActivityInstanceIds(subActivityInstanceIds);
                    v.setBpmnElementId(StringUtils.removeEnd(v.getBpmnElementId(), "#" + AnYiBpmnConstant.MULTI_INSTANCE_BODY));
                })
                .collect(Collectors.toList());
        List<AnYiBpmnProcessInstanceModel.BpmnActivityInstanceModel> activityInstanceListSub = activityInstanceAllList.stream()
                .filter(v -> !v.getBpmnElementId().endsWith("#" + AnYiBpmnConstant.MULTI_INSTANCE_BODY) && StringUtils.isBlank(multiInstanceIds.get(v.getParentActivityInstanceId())))
                .collect(Collectors.toList());


        List<AnYiBpmnProcessInstanceModel.BpmnActivityInstanceModel> activityInstanceListParentFinally = new ArrayList<>();
        activityInstanceListParentFinally.addAll(multiInstanceListParent);
        activityInstanceListParentFinally.addAll(activityInstanceListParent);
        List<AnYiBpmnProcessInstanceModel.BpmnActivityInstanceModel> activityInstanceListSubFinally = new ArrayList<>();
        activityInstanceListSubFinally.addAll(multiInstanceListSub);
        activityInstanceListSubFinally.addAll(activityInstanceListSub);
        // 构建树形
        AnYiTreeToolUtils<AnYiBpmnProcessInstanceModel.BpmnActivityInstanceModel> treeToolUtils = new AnYiTreeToolUtils<>(activityInstanceListParentFinally, activityInstanceListSubFinally, new AnYiTreeToolUtils.TreeId<AnYiBpmnProcessInstanceModel.BpmnActivityInstanceModel>() {
            @Override
            public String getId(AnYiBpmnProcessInstanceModel.BpmnActivityInstanceModel model) {
                return model.getId();
            }


            @Override
            public String getParentId(AnYiBpmnProcessInstanceModel.BpmnActivityInstanceModel model) {
                return model.getFlowScopeKey();
            }
        });
        result.setActivityInstanceList(treeToolUtils.getTree());
        return result;
    }


    @Override
    public List<EnableDismissDto> selectEnableDismiss(EnableDismissVo vo) {
        List<HistoricActivityInstance> list = historyService.createHistoricActivityInstanceQuery().processInstanceId(vo.getProcessInstanceId()).finished().list();
        if (CollUtil.isEmpty(list)) {
            return Collections.emptyList();
        }
        List<EnableDismissDto> instanceEntities = list.stream().map(v -> {
            HistoricActivityInstanceEntity instanceEntity = (HistoricActivityInstanceEntity) v;
            return EnableDismissDto.builder().sequenceCounter(instanceEntity.getSequenceCounter()).activityName(instanceEntity.getActivityName()).activityId(instanceEntity.getActivityId()).activityType(instanceEntity.getActivityType()).build();
        }).collect(Collectors.toList());
        instanceEntities.sort(Comparator.comparingLong(EnableDismissDto::getSequenceCounter));
        Map<String, GatewayInfoDto> activityMap = getActivity(vo.getProcessInstanceId());
        // 排除并行网关与兼容网关两次需要汇聚从而导致无法从单个节点启动
        List<EnableDismissDto> enableEntities = new ArrayList<>(instanceEntities.size());
        int count = 0;
        for (EnableDismissDto dto : instanceEntities) {
            if (dto.getActivityType().equals(ActivityTypes.GATEWAY_INCLUSIVE) || dto.getActivityType().equals(ActivityTypes.GATEWAY_PARALLEL)) {
                // 判断是fork还是join(fork多个出点，join单个出点)
                GatewayInfoDto infoDto = activityMap.get(dto.getActivityId());
                if (!infoDto.isJoin()) {
                    count += infoDto.getOutgoing().size();
                } else {
                    count--;
                }
            }
            dto.setDisabled(true);
            if (count == 0) {
                dto.setDisabled(false);
            }
            if (dto.getActivityType().equals(ActivityTypes.TASK_USER_TASK)) {
                enableEntities.add(dto);
            }
        }
        return enableEntities;
    }


    /**
     * 获取网关信息
     *
     * @param processInstanceId
     * @return GatewayInfoDto>
     * @author zxh
     * @date 2022-06-21 19:47
     */
    private Map<String, GatewayInfoDto> getActivity(String processInstanceId) {
        HistoricProcessInstanceEntity historicProcessInstance = getHistoricProcessInstance(processInstanceId);
        InputStream processModel = repositoryService.getProcessModel(historicProcessInstance.getProcessDefinitionId());
        if (Objects.isNull(processModel)) {
            throw new AnYiResponseException(AnYiResultStatus.DATABASE_BASE_ERROR, "流程模型不存在");
        }
        BpmnModelInstance bpmnModelInstance = Bpmn.readModelFromStream(processModel);
        Collection<ParallelGateway> parallelGateways = bpmnModelInstance.getModelElementsByType(ParallelGateway.class);
        Collection<InclusiveGateway> inclusiveGateways = bpmnModelInstance.getModelElementsByType(InclusiveGateway.class);
        Map<String, GatewayInfoDto> gatewayInfoDtoMap = new HashMap<>(64);
        if (CollUtil.isNotEmpty(parallelGateways)) {
            parallelGateways.forEach(v -> {
                GatewayInfoDto infoDto = GatewayInfoDto.builder().activityId(v.getId()).activityName(v.getName()).activityType(ActivityTypes.GATEWAY_PARALLEL).build();
                Collection<SequenceFlow> incomingElement = v.getIncoming();
                List<String> incoming = new ArrayList<>();
                if (CollUtil.isNotEmpty(incomingElement)) {
                    incomingElement.forEach(sv -> incoming.add(sv.getId()));
                }
                Collection<SequenceFlow> outgoingElement = v.getOutgoing();
                List<String> outgoing = new ArrayList<>();
                if (CollUtil.isNotEmpty(outgoingElement)) {
                    outgoingElement.forEach(sv -> outgoing.add(sv.getId()));
                }
                infoDto.setIncoming(incoming);
                infoDto.setOutgoing(outgoing);
                if (infoDto.getOutgoing().size() == 1) {
                    infoDto.setJoin(true);
                }
                gatewayInfoDtoMap.put(v.getId(), infoDto);
            });
        }
        if (CollUtil.isNotEmpty(inclusiveGateways)) {
            inclusiveGateways.forEach(v -> {
                GatewayInfoDto infoDto = GatewayInfoDto.builder().activityId(v.getId()).activityName(v.getName()).activityType(ActivityTypes.GATEWAY_INCLUSIVE).build();
                Collection<SequenceFlow> incomingElement = v.getIncoming();
                List<String> incoming = new ArrayList<>();
                if (CollUtil.isNotEmpty(incomingElement)) {
                    incomingElement.forEach(sv -> incoming.add(sv.getId()));
                }
                Collection<SequenceFlow> outgoingElement = v.getOutgoing();
                List<String> outgoing = new ArrayList<>();
                if (CollUtil.isNotEmpty(outgoingElement)) {
                    outgoingElement.forEach(sv -> outgoing.add(sv.getId()));
                }
                infoDto.setIncoming(incoming);
                infoDto.setOutgoing(outgoing);
                if (infoDto.getOutgoing().size() == 1) {
                    infoDto.setJoin(true);
                }
                gatewayInfoDtoMap.put(v.getId(), infoDto);
            });
        }
        return gatewayInfoDtoMap;
    }


    @Override
    @Transactional(rollbackFor = {Exception.class, Error.class})
    public void deleteHistoryProcess(String processInstanceId) {
        // 查询流程是否结束
        HistoricProcessInstance historicProcessInstance = historyService.createHistoricProcessInstanceQuery().processInstanceId(processInstanceId).singleResult();
        if (Objects.isNull(historicProcessInstance)) {
            throw new AnYiResponseException(AnYiResultStatus.DATABASE_BASE_ERROR, "流程不存在");
        }
        String state = historicProcessInstance.getState();
        if (HistoricProcessInstance.STATE_ACTIVE.equals(state) || HistoricProcessInstance.STATE_SUSPENDED.equals(state)) {
            throw new AnYiResponseException(AnYiResultStatus.DATABASE_BASE_ERROR, "流程未结束无法删除");
        }
        historyService.deleteHistoricProcessInstance(processInstanceId);
        // 删除连线信息
        LambdaQueryWrapper<SequenceFlowEntity> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(SequenceFlowEntity::getProcessInstanceId, processInstanceId);
        List<SequenceFlowEntity> list = sequenceFlowService.list(lambdaQueryWrapper);
        if (CollUtil.isNotEmpty(list)) {
            Set<String> sequenceFlowIds = new HashSet<>(list.size());
            list.forEach(v -> sequenceFlowIds.add(v.getSequenceFlowId()));
            sequenceFlowMapper.anyiPhysicalDeleteBatchIds(sequenceFlowIds);
        }
    }


    @Override
    @Transactional(rollbackFor = {Exception.class, Error.class})
    public void deleteHistoryProcessBatch(Set<String> processInstanceIds) {
        List<HistoricProcessInstance> list = historyService.createHistoricProcessInstanceQuery().processInstanceIds(processInstanceIds).list();
        if (CollUtil.isEmpty(list)) {
            throw new AnYiResponseException(AnYiResultStatus.DATABASE_BASE_ERROR, "流程不存在");
        }
        List<HistoricProcessInstance> collect = list.stream().filter(v -> HistoricProcessInstance.STATE_ACTIVE.equals(v.getState()) || HistoricProcessInstance.STATE_SUSPENDED.equals(v.getState())).collect(Collectors.toList());
        if (CollUtil.isNotEmpty(collect)) {
            StringBuilder sb = new StringBuilder("无法删除流程，因为以下未结束:");
            collect.forEach(v -> sb.append(v.getId()).append("、"));
            String errorMsg = sb.toString();
            errorMsg = StringUtils.removeEnd(errorMsg, "、");
            throw new AnYiResponseException(AnYiResultStatus.VERIFICATION_FAILED, errorMsg);
        }
        List<String> waitDeleteCollect = list.stream().map(HistoricProcessInstance::getId).collect(Collectors.toList());
        historyService.deleteHistoricProcessInstances(waitDeleteCollect);
        // 删除连线信息
        LambdaQueryWrapper<SequenceFlowEntity> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.in(SequenceFlowEntity::getProcessInstanceId, waitDeleteCollect);
        List<SequenceFlowEntity> sequenceFlowEntities = sequenceFlowService.list(lambdaQueryWrapper);
        if (CollUtil.isNotEmpty(sequenceFlowEntities)) {
            Set<String> sequenceFlowIds = new HashSet<>(list.size());
            sequenceFlowEntities.forEach(v -> sequenceFlowIds.add(v.getSequenceFlowId()));
            sequenceFlowMapper.anyiPhysicalDeleteBatchIds(sequenceFlowIds);
        }
    }


    @Override
    public void addMultiInstanceTask(MultiInstanceTaskVo vo) {
        // 查询当前多实例任务是否完结
        HistoricActivityInstance activityInstance = historyService.createHistoricActivityInstanceQuery().activityInstanceId(vo.getActivityInstanceId()).activityType(ActivityTypes.MULTI_INSTANCE_BODY).unfinished().singleResult();
        if (Objects.isNull(activityInstance)) {
            throw new AnYiResponseException(AnYiResultStatus.VERIFICATION_FAILED, "当前活动已经结束，无法加签");
        }
        String bpmnActivityId = StringUtils.removeEnd(activityInstance.getActivityId(), "#" + ActivityTypes.MULTI_INSTANCE_BODY);
        // 读取模型中多实例的变量信息
        InputStream processModel = repositoryService.getProcessModel(activityInstance.getProcessDefinitionId());
        if (Objects.isNull(processModel)) {
            throw new AnYiResponseException(AnYiResultStatus.DATABASE_BASE_ERROR, "流程模型不存在");
        }
        String elementVariable = vo.getElementVariable();
        if (StringUtils.isBlank(elementVariable)) {
            // 获取多实例元素变量名称
            elementVariable = ProcessBpmnUtils.getMutiElementVariable(processModel, bpmnActivityId);
        }
        ProcessInstanceModificationInstantiationBuilder processInstanceModificationInstantiationBuilder = runtimeService.createProcessInstanceModification(activityInstance.getProcessInstanceId()).startBeforeActivity(bpmnActivityId, activityInstance.getId()).setVariable(elementVariable, vo.getUseId());
        if (CollUtil.isNotEmpty(vo.getLocalVariable())) {
            processInstanceModificationInstantiationBuilder.setVariablesLocal(vo.getLocalVariable());
        }
        if (CollUtil.isNotEmpty(vo.getVariable())) {
            processInstanceModificationInstantiationBuilder.setVariables(vo.getVariable());
        }
        processInstanceModificationInstantiationBuilder.execute();
    }
}
