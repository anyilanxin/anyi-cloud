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

package com.anyilanxin.anyicloud.process.modules.manage.service.impl;

import com.anyilanxin.anyicloud.corecommon.constant.AnYiResultStatus;
import com.anyilanxin.anyicloud.corecommon.exception.AnYiResponseException;
import com.anyilanxin.anyicloud.corecommon.model.common.AnYiPageResult;
import com.anyilanxin.anyicloud.corecommon.utils.AnYiDateUtils;
import com.anyilanxin.anyicloud.coremvc.utils.AnYiUserContextUtils;
import com.anyilanxin.anyicloud.database.utils.PageUtils;
import com.anyilanxin.anyicloud.process.core.constant.CommonProcessConstant;
import com.anyilanxin.anyicloud.process.extend.constant.impl.ProcessInstanceState;
import com.anyilanxin.anyicloud.process.modules.manage.controller.vo.*;
import com.anyilanxin.anyicloud.process.modules.manage.entity.DesignDraftEntity;
import com.anyilanxin.anyicloud.process.modules.manage.entity.ReDeploymentEntity;
import com.anyilanxin.anyicloud.process.modules.manage.mapper.DesignDraftMapper;
import com.anyilanxin.anyicloud.process.modules.manage.mapper.ReDeploymentMapper;
import com.anyilanxin.anyicloud.process.modules.manage.service.IDefinitionManageService;
import com.anyilanxin.anyicloud.process.modules.manage.service.IProcessCategoryService;
import com.anyilanxin.anyicloud.process.modules.manage.service.dto.*;
import com.anyilanxin.anyicloud.process.modules.manage.service.mapstruct.ProcessDefinitionPageMap;
import com.anyilanxin.anyicloud.process.modules.manage.service.mapstruct.ProcessInfoCopyMap;
import com.anyilanxin.anyicloud.process.utils.Base64FileUtils;
import com.anyilanxin.anyicloud.process.utils.ProcessBpmnUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.camunda.bpm.engine.HistoryService;
import org.camunda.bpm.engine.RepositoryService;
import org.camunda.bpm.engine.history.HistoricProcessInstance;
import org.camunda.bpm.engine.impl.persistence.entity.DeploymentEntity;
import org.camunda.bpm.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.camunda.bpm.engine.repository.*;
import org.dromara.hutool.core.collection.CollUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.InputStream;
import java.util.*;

/**
 * 流程定义管理实现
 *
 * @author zxh
 * @date 2020-10-14 20:59
 * @since 1.0.0
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class DefinitionManageServiceImpl implements IDefinitionManageService {
    private static final String VERSION = "version";
    private final DesignDraftMapper draftMapper;
    private static final String DEPLOYMENT_TIME = "deploymentTime";
    private final RepositoryService repositoryService;
    private final HistoryService historyService;
    private final IProcessCategoryService categoryService;
    private final ReDeploymentMapper deploymentMapper;
    private final ProcessInfoCopyMap processInfoCopyMap;
    private final ProcessDefinitionPageMap definitionPageMap;

    @Override
    public ProcessInfoDto getProcessInfo(ProcessInfoVo vo) {
        // 获取流程定义信息
        if (StringUtils.isBlank(vo.getProcessDefinitionId()) && StringUtils.isBlank(vo.getProcessDefinitionKey())) {
            throw new AnYiResponseException(AnYiResultStatus.VERIFICATION_FAILED, "流程定义id与流程定义key必须填写一个");
        }
        ProcessDefinitionQuery processDefinitionQuery = repositoryService.createProcessDefinitionQuery();
        if (StringUtils.isNotBlank(vo.getProcessDefinitionId())) {
            processDefinitionQuery.processDefinitionId(vo.getProcessDefinitionId());
        } else {
            processDefinitionQuery.processDefinitionKey(vo.getProcessDefinitionKey()).latestVersion();
        }
        ProcessDefinition processDefinition = processDefinitionQuery.singleResult();
        if (Objects.isNull(processDefinition)) {
            throw new AnYiResponseException(AnYiResultStatus.DATABASE_BASE_ERROR, "流程定义不存在");
        }
        ProcessDefinitionEntity processDefinitionEntity = (ProcessDefinitionEntity) processDefinition;
        ProcessInfoDto processInfoDto = processInfoCopyMap.bToA((ProcessDefinitionEntity) processDefinition);
        processInfoDto.setProcessDefinitionKey(processDefinitionEntity.getKey());
        processInfoDto.setStartableInTasklist(processDefinitionEntity.isStartableInTasklist());
        processInfoDto.setProcessDefinitionId(processDefinitionEntity.getId());
        Deployment deployment = repositoryService.createDeploymentQuery().deploymentId(processDefinitionEntity.getDeploymentId()).singleResult();
        if (Objects.nonNull(deployment)) {
            processInfoDto.setDeploymentName(deployment.getName());
            processInfoDto.setDeploymentTime(AnYiDateUtils.toLocalDateTime(deployment.getDeploymentTime()));
        }
        // 补全类别信息
        ProcessCategoryDto processCategoryDto = categoryService.selectByCode(processInfoDto.getCategory());
        if (Objects.nonNull(processCategoryDto)) {
            processInfoDto.setCategoryName(processCategoryDto.getCategoryName());
        }
        // 获取节点任务信息
        InputStream processModel = repositoryService.getProcessModel(processDefinition.getId());
        if (Objects.nonNull(processModel)) {
            List<ProcessTaskInfoDto> bpmnUserTask = ProcessBpmnUtils.getBpmnUserTaskToList(processModel);
            processInfoDto.setUserTasks(bpmnUserTask);
        }
        processInfoDto.setDiagramData(Base64FileUtils.inputStreamToBase64(processModel));
        return processInfoDto;
    }


    @Override
    @Transactional(rollbackFor = {Exception.class, Error.class}, readOnly = true)
    public AnYiPageResult<ProcessDefinitionInfoPageDto> selectPageDefinition(ProcessDefinitionPageVo pageVo) throws RuntimeException {
        // 构建查询条件
        ProcessDefinitionQuery leave = repositoryService.createProcessDefinitionQuery();
        if (!AnYiUserContextUtils.superRole()) {
            leave.startablePermissionCheck();
            leave.startableByUser(AnYiUserContextUtils.getUserId());
        }
        if (StringUtils.isNotBlank(pageVo.getProcessDefinitionKey())) {
            leave.processDefinitionKey(pageVo.getProcessDefinitionKey());
        }
        if (Objects.nonNull(pageVo.getStartTime())) {
            leave.deployedAfter(AnYiDateUtils.toDateTime(pageVo.getStartTime()));
        }
        if (Objects.nonNull(pageVo.getSuspensionState())) {
            if (pageVo.getSuspensionState() == 1) {
                leave.active();
            }
            if (pageVo.getSuspensionState() == 0) {
                leave.suspended();
            }
        }
        if (StringUtils.isNotBlank(pageVo.getName())) {
            leave.processDefinitionNameLike('%' + pageVo.getName() + "%");
        }
        if (StringUtils.isNotBlank(AnYiUserContextUtils.getCurrentTenantId())) {
            leave.tenantIdIn(AnYiUserContextUtils.getCurrentTenantId());
        }
        if (StringUtils.isNotBlank(pageVo.getCategory())) {
            leave.processDefinitionCategory(pageVo.getCategory());
        }
        if (Objects.nonNull(pageVo.getNewVersion()) && pageVo.getNewVersion()) {
            leave.latestVersion();
        }
        // 构建排序条件
        if (CollUtil.isNotEmpty(pageVo.getDescs()) || CollUtil.isNotEmpty(pageVo.getAscs())) {
            if (CollUtil.isNotEmpty(pageVo.getDescs())) {
                if (pageVo.getDescs().contains(DEPLOYMENT_TIME)) {
                    leave.orderByDeploymentTime().desc();
                } else if (pageVo.getDescs().contains(VERSION)) {
                    leave.orderByProcessDefinitionVersion().desc();
                }
            } else {
                if (pageVo.getAscs().contains(DEPLOYMENT_TIME)) {
                    leave.orderByDeploymentTime().asc();
                } else if (pageVo.getAscs().contains(VERSION)) {
                    leave.orderByProcessDefinitionVersion().asc();
                }
            }
        }
        leave.orderByProcessDefinitionVersion().desc();
        long count = leave.count();
        if (count <= 0) {
            return PageUtils.toPageData();
        }
        // 分页查询
        List<ProcessDefinition> processDefinitions = leave.listPage(pageVo.getCurrent(), pageVo.getSize());
        // 转换为自定义类型
        List<ProcessDefinitionInfoPageDto> processDefinitionPageDtoList = Collections.emptyList();
        Set<String> processDefinitionKeys = new HashSet<>(32);
        Set<String> deploymentIds = new HashSet<>(32);
        Set<String> categoryCodes = new HashSet<>(32);
        if (CollUtil.isNotEmpty(processDefinitions)) {
            processDefinitionPageDtoList = new ArrayList<>();
            for (ProcessDefinition definition : processDefinitions) {
                if (definition instanceof ProcessDefinitionEntity processDefinitionEntity) {
                    ProcessDefinitionInfoPageDto processDefinitionPageDto = definitionPageMap.bToA(processDefinitionEntity);
                    processDefinitionPageDto.setProcessDefinitionKey(processDefinitionEntity.getKey());
                    processDefinitionPageDto.setStartableInTasklist(processDefinitionEntity.isStartableInTasklist());
                    processDefinitionPageDto.setProcessDefinitionId(processDefinitionEntity.getId());
                    deploymentIds.add(processDefinitionEntity.getDeploymentId());
                    processDefinitionKeys.add(processDefinitionEntity.getKey());
                    categoryCodes.add(processDefinitionEntity.getCategory());
                    processDefinitionPageDtoList.add(processDefinitionPageDto);
                }
            }
        }
        // 补全流程部署信息以及统计活动实例情况
        if (CollUtil.isNotEmpty(processDefinitionPageDtoList)) {
            // 查询流程历史实例同时处理数据方便后续使用
            List<HistoricProcessInstance> processInstanceList = historyService.createHistoricProcessInstanceQuery().processDefinitionKeyIn(processDefinitionKeys.toArray(new String[]{})).list();
            Map<String, List<HistoricProcessInstance>> processInstanceMap = new HashMap<>(16);
            if (CollUtil.isNotEmpty(processInstanceList)) {
                processInstanceList.forEach(v -> {
                    List<HistoricProcessInstance> processInstances = processInstanceMap.get(v.getProcessDefinitionId());
                    if (CollUtil.isEmpty(processInstances)) {
                        processInstances = new ArrayList<>(16);
                    }
                    processInstances.add(v);
                    processInstanceMap.put(v.getProcessDefinitionId(), processInstances);
                });
            }
            // 获取模型部署信息同时处理数据方便后续使用
            List<ReDeploymentEntity> reDeploymentEntities = deploymentMapper.selectBatchIds(deploymentIds);
            Map<String, ReDeploymentEntity> deploymentEntityMap = new HashMap<>(16);
            if (CollUtil.isNotEmpty(reDeploymentEntities)) {
                reDeploymentEntities.forEach(v -> deploymentEntityMap.put(v.getId(), v));
            }
            // 获取类别信息
            Map<String, ProcessCategoryDto> processCategoryMap = categoryService.selectMapByCodes(categoryCodes);
            // 补全信息
            processDefinitionPageDtoList.forEach(v -> {
                List<HistoricProcessInstance> processInstances = processInstanceMap.get(v.getProcessDefinitionId());
                if (CollUtil.isNotEmpty(processInstances)) {
                    long completedNum = processInstances.stream().filter(sv -> ProcessInstanceState.COMPLETED.getStrState().equals(sv.getState())).count();
                    v.setInstanceCompleteNum(completedNum);
                    v.setInstanceNum(processInstances.size());
                    v.setHaveRunTask(v.getInstanceNum() != completedNum);
                }
                ReDeploymentEntity reDeploymentEntity = deploymentEntityMap.get(v.getDeploymentId());
                if (Objects.nonNull(reDeploymentEntity)) {
                    v.setDeploymentName(reDeploymentEntity.getName());
                    v.setDeploymentTime(reDeploymentEntity.getDeployTime());
                }
                // 补全类别信息
                ProcessCategoryDto processCategoryDto = processCategoryMap.get(v.getCategory());
                if (Objects.nonNull(processCategoryDto)) {
                    v.setCategoryName(processCategoryDto.getCategoryName());
                }
            });
        }
        return PageUtils.toPageData(count, processDefinitionPageDtoList);
    }


    @Override
    @Transactional(rollbackFor = {Exception.class, Error.class})
    public void createDeployment(DeploymentVo vo) throws RuntimeException {
        if (StringUtils.isBlank(vo.getBpmnBase64()) && StringUtils.isBlank(vo.getDraftId())) {
            throw new AnYiResponseException(AnYiResultStatus.VERIFICATION_FAILED, "流程定义与base64模型数据不能同时为空");
        }
        String baseBpmn = vo.getBpmnBase64();
        if (StringUtils.isNotBlank(vo.getDraftId()) && StringUtils.isBlank(vo.getBpmnBase64())) {
            DesignDraftEntity designDraftEntity = draftMapper.selectById(vo.getDraftId());
            if (Objects.isNull(designDraftEntity)) {
                throw new AnYiResponseException(AnYiResultStatus.DATABASE_BASE_ERROR, "当前模型草稿信息不存在:" + vo.getDraftId());
            }
            baseBpmn = designDraftEntity.getBpmnBase64();
            if (StringUtils.isNotBlank(vo.getDeploymentName())) {
                vo.setDeploymentName(designDraftEntity.getBpmnProcessNames());
            }
        }
        if (StringUtils.isNotBlank(vo.getDeploymentName())) {
            vo.setDeploymentName("未知流程");
        }
        DeploymentBuilder deploymentBuilder = repositoryService.createDeployment().name(vo.getDeploymentName());
        deploymentBuilder.addInputStream(vo.getDeploymentName() + CommonProcessConstant.MODEL_RESOURCE_SUFFIX, Base64FileUtils.base64ToInputStream(baseBpmn));
        if (StringUtils.isNotBlank(AnYiUserContextUtils.getCurrentTenantId())) {
            deploymentBuilder.tenantId(AnYiUserContextUtils.getCurrentTenantId());
        }
        if (Objects.nonNull(vo.getActivateProcessDate())) {
            deploymentBuilder.activateProcessDefinitionsOn(AnYiDateUtils.toDateTime(vo.getActivateProcessDate()));
        }
        deploymentBuilder.deploy();
        if (StringUtils.isNotBlank(vo.getDraftId())) {
            draftMapper.deleteById(vo.getDraftId());
        }
    }


    @Override
    @Transactional(rollbackFor = {Exception.class, Error.class})
    public void historyDeployment(DeploymentHistoryVo vo) {
        ProcessDefinitionEntity processDefinition = (ProcessDefinitionEntity) repositoryService.createProcessDefinitionQuery().processDefinitionId(vo.getProcessDefinitionKey()).singleResult();
        if (Objects.isNull(processDefinition)) {
            throw new AnYiResponseException(AnYiResultStatus.DATABASE_BASE_ERROR, "流程定义不存在");
        }
        InputStream processModel = repositoryService.getProcessModel(processDefinition.getId());
        DeploymentVo deploymentVo = new DeploymentVo();
        deploymentVo.setDeploymentName(vo.getDeploymentName());
        deploymentVo.setActivateProcessDate(vo.getActivateProcessDate());
        deploymentVo.setBpmnBase64(Base64FileUtils.inputStreamToBase64(processModel));
        this.createDeployment(deploymentVo);
    }


    @Override
    public void processDefinitionUpdateState(UpdateProcessDefinitionStateVo vo) throws RuntimeException {
        if (vo.getState()) {
            repositoryService.activateProcessDefinitionById(vo.getProcessDefinitionId(), vo.getProcessInstances(), new Date());
        } else {
            repositoryService.suspendProcessDefinitionById(vo.getProcessDefinitionId(), vo.getProcessInstances(), new Date());
        }
    }


    @Override
    public void deleteDeployment(String deploymentId) throws RuntimeException {
        queryDeploymentById(deploymentId);
        repositoryService.deleteDeployment(deploymentId);
    }


    @Override
    public void deleteProcessDefinition(DeleteProcessDefinitionVo vo) throws RuntimeException {
        repositoryService.deleteProcessDefinition(vo.getProcessDefinitionId(), vo.isCascade(), vo.isSkipCustomListeners(), vo.isSkipIoMappings());
    }


    @Override
    public DeploymentDetailDto getDeploymentDetail(String processKeywordId) {
        ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery().processDefinitionKey(processKeywordId).latestVersion().singleResult();
        if (Objects.isNull(processDefinition)) {
            processDefinition = repositoryService.createProcessDefinitionQuery().processDefinitionId(processKeywordId).singleResult();
        }
        if (Objects.isNull(processDefinition)) {
            throw new AnYiResponseException(AnYiResultStatus.DATABASE_BASE_ERROR, "当前流程部署信息不存在");
        }
        ProcessDefinitionEntity processDefinitionEntity = (ProcessDefinitionEntity) processDefinition;
        DeploymentEntity deploymentEntity = queryDeploymentById(processDefinition.getDeploymentId());
        DeploymentDetailDto deploymentDetailDto = new DeploymentDetailDto();
        deploymentDetailDto.setNew(deploymentEntity.isNew());
        deploymentDetailDto.setProcessDefinitionKey(processDefinitionEntity.getKey());
        deploymentDetailDto.setSuspensionState(processDefinitionEntity.getSuspensionState());
        deploymentDetailDto.setCategory(processDefinitionEntity.getCategory());
        deploymentDetailDto.setDeploymentId(processDefinition.getDeploymentId());
        deploymentDetailDto.setProcessDefinitionId(processDefinitionEntity.getId());
        Integer historyTimeToLive = processDefinition.getHistoryTimeToLive();
        InputStream processModel = repositoryService.getProcessModel(processDefinitionEntity.getId());
        deploymentDetailDto.setDiagramData(Base64FileUtils.inputStreamToBase64(processModel));
        deploymentDetailDto.setHistoryTimeToLive(historyTimeToLive);
        deploymentDetailDto.setResourceNames(processDefinitionEntity.getDiagramResourceName());
        deploymentDetailDto.setDeploymentName(deploymentEntity.getName());
        deploymentDetailDto.setVersion(processDefinitionEntity.getVersion());
        deploymentDetailDto.setVersionTag(processDefinitionEntity.getVersionTag());
        return deploymentDetailDto;
    }


    /**
     * 通过部署id查询部署信息
     *
     * @param deploymentId ${@link String}
     * @author zxh
     * @date 2020-10-15 09:45
     */
    private DeploymentEntity queryDeploymentById(String deploymentId) {
        DeploymentQuery deploymentQuery = repositoryService.createDeploymentQuery().deploymentId(deploymentId);
        if (StringUtils.isNotBlank(AnYiUserContextUtils.getCurrentTenantId())) {
            deploymentQuery = deploymentQuery.tenantIdIn(AnYiUserContextUtils.getCurrentTenantId());
        }
        Deployment deployment = deploymentQuery.singleResult();
        if (Objects.isNull(deployment) || !(deployment instanceof DeploymentEntity)) {
            throw new AnYiResponseException(AnYiResultStatus.DATABASE_BASE_ERROR, "流程部署信息不存在");
        }
        return (DeploymentEntity) deployment;
    }


    @Override
    public ProcessDefinitionInfoDto selectProcessById(String processDefinitionKey) {
        return null;
    }
}
