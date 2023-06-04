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

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.CollectionUtil;
import com.anyilanxin.anyicloud.corecommon.constant.Status;
import com.anyilanxin.anyicloud.corecommon.exception.ResponseException;
import com.anyilanxin.anyicloud.corecommon.utils.CoreCommonDateUtils;
import com.anyilanxin.anyicloud.database.datasource.base.service.dto.PageDto;
import com.anyilanxin.anyicloud.oauth2mvc.utils.UserContextUtils;
import com.anyilanxin.anyicloud.process.core.constant.CommonProcessConstant;
import com.anyilanxin.anyicloud.process.core.constant.ModelStateType;
import com.anyilanxin.anyicloud.process.core.constant.ProcessInstanceState;
import com.anyilanxin.anyicloud.process.modules.base.controller.vo.DesignModelHistoryVo;
import com.anyilanxin.anyicloud.process.modules.base.entity.DesignModelEntity;
import com.anyilanxin.anyicloud.process.modules.base.entity.DesignModelHistoryEntity;
import com.anyilanxin.anyicloud.process.modules.base.mapper.DesignModelHistoryMapper;
import com.anyilanxin.anyicloud.process.modules.base.mapper.DesignModelMapper;
import com.anyilanxin.anyicloud.process.modules.base.service.IProcessCategoryService;
import com.anyilanxin.anyicloud.process.modules.base.service.dto.ProcessCategoryDto;
import com.anyilanxin.anyicloud.process.modules.base.service.mapstruct.DesignModelToHistoryCopyMap;
import com.anyilanxin.anyicloud.process.modules.manage.controller.vo.*;
import com.anyilanxin.anyicloud.process.modules.manage.entity.ReDeploymentEntity;
import com.anyilanxin.anyicloud.process.modules.manage.mapper.ReDeploymentMapper;
import com.anyilanxin.anyicloud.process.modules.manage.service.IDefinitionManageService;
import com.anyilanxin.anyicloud.process.modules.manage.service.dto.DeploymentDetailDto;
import com.anyilanxin.anyicloud.process.modules.manage.service.dto.ProcessDefinitionPageDto;
import com.anyilanxin.anyicloud.process.modules.manage.service.dto.ProcessInfoDto;
import com.anyilanxin.anyicloud.process.modules.manage.service.mapstruct.ProcessDefinitionPageMap;
import com.anyilanxin.anyicloud.process.modules.manage.service.mapstruct.ProcessInfoCopyMap;
import com.anyilanxin.anyicloud.process.utils.Base64FileUtils;
import com.anyilanxin.skillfull.process.modules.manage.controller.vo.*;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import java.io.InputStream;
import java.util.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.camunda.bpm.engine.FormService;
import org.camunda.bpm.engine.HistoryService;
import org.camunda.bpm.engine.RepositoryService;
import org.camunda.bpm.engine.history.HistoricProcessInstance;
import org.camunda.bpm.engine.impl.persistence.entity.DeploymentEntity;
import org.camunda.bpm.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.camunda.bpm.engine.repository.*;
import org.camunda.bpm.model.bpmn.impl.BpmnParser;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    private static final String DEPLOYMENT_TIME = "deploymentTime";
    private final RepositoryService repositoryService;
    private final DesignModelMapper modelMapper;
    private final HistoryService historyService;
    private final IProcessCategoryService categoryService;
    private final ReDeploymentMapper deploymentMapper;
    private final FormService formService;
    private final ProcessInfoCopyMap processInfoCopyMap;
    private final ProcessDefinitionPageMap definitionPageMap;
    private final BpmnParser bpmnParser;
    private final DesignModelHistoryMapper modelHistoryMapper;
    private final DesignModelToHistoryCopyMap toHistoryCopyMap;

    @Override
    public ProcessInfoDto getProcessInfo(ProcessInfoVo vo) {
        // 获取流程定义信息
        if (StringUtils.isBlank(vo.getProcessDefinitionId()) && StringUtils.isBlank(vo.getProcessDefinitionKey())) {
            throw new ResponseException(Status.VERIFICATION_FAILED, "流程定义id与流程定义key必须填写一个");
        }
        ProcessDefinitionQuery processDefinitionQuery = repositoryService.createProcessDefinitionQuery();
        if (StringUtils.isNotBlank(vo.getProcessDefinitionId())) {
            processDefinitionQuery.processDefinitionId(vo.getProcessDefinitionId());
        } else {
            processDefinitionQuery.processDefinitionKey(vo.getProcessDefinitionKey()).latestVersion();
        }
        ProcessDefinition processDefinition = processDefinitionQuery.singleResult();
        if (Objects.isNull(processDefinition)) {
            throw new ResponseException(Status.DATABASE_BASE_ERROR, "流程定义不存在");
        }
        ProcessDefinitionEntity processDefinitionEntity = (ProcessDefinitionEntity) processDefinition;
        ProcessInfoDto processInfoDto = processInfoCopyMap.bToA((ProcessDefinitionEntity) processDefinition);
        processInfoDto.setProcessDefinitionKey(processDefinitionEntity.getKey());
        processInfoDto.setStartableInTasklist(processDefinitionEntity.isStartableInTasklist());
        processInfoDto.setProcessDefinitionId(processDefinitionEntity.getId());
        Deployment deployment = repositoryService.createDeploymentQuery().deploymentId(processDefinitionEntity.getDeploymentId()).singleResult();
        if (Objects.nonNull(deployment)) {
            processInfoDto.setDeploymentName(deployment.getName());
            processInfoDto.setDeploymentTime(CoreCommonDateUtils.toLocalDateTime(deployment.getDeploymentTime()));
        }
        // 补全类别信息
        ProcessCategoryDto processCategoryDto = categoryService.selectByCode(processInfoDto.getCategory());
        if (Objects.nonNull(processCategoryDto)) {
            processInfoDto.setCategoryName(processCategoryDto.getCategoryName());
        }
        // 获取节点任务信息
        InputStream processModel = repositoryService.getProcessModel(processDefinition.getId());
        processInfoDto.setDiagramData(Base64FileUtils.inputStreamToBase64(processModel));
        return processInfoDto;
    }


    @Override
    @Transactional(rollbackFor = {Exception.class, Error.class}, readOnly = true)
    public PageDto<ProcessDefinitionPageDto> selectPageDefinition(ProcessDefinitionPageVo pageVo) throws RuntimeException {
        // 构建查询条件
        ProcessDefinitionQuery leave = repositoryService.createProcessDefinitionQuery();
        if (StringUtils.isNotBlank(pageVo.getProcessDefinitionKey())) {
            leave.processDefinitionKey(pageVo.getProcessDefinitionKey());
        }
        if (Objects.nonNull(pageVo.getStartTime())) {
            leave.deployedAfter(CoreCommonDateUtils.toDateTime(pageVo.getStartTime()));
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
        if (StringUtils.isNotBlank(UserContextUtils.getCurrentTenantId())) {
            leave.tenantIdIn(UserContextUtils.getCurrentTenantId());
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
            return new PageDto<>();
        }
        // 分页查询
        List<ProcessDefinition> processDefinitions = leave.listPage(pageVo.getCurrent(), pageVo.getSize());
        // 转换为自定义类型
        List<ProcessDefinitionPageDto> processDefinitionPageDtoList = Collections.emptyList();
        Set<String> processDefinitionKeys = new HashSet<>(32);
        Set<String> deploymentIds = new HashSet<>(32);
        Set<String> categoryCodes = new HashSet<>(32);
        if (CollUtil.isNotEmpty(processDefinitions)) {
            processDefinitionPageDtoList = new ArrayList<>();
            for (ProcessDefinition definition : processDefinitions) {
                if (definition instanceof ProcessDefinitionEntity) {
                    ProcessDefinitionEntity processDefinitionEntity = (ProcessDefinitionEntity) definition;
                    ProcessDefinitionPageDto processDefinitionPageDto = definitionPageMap.bToA(processDefinitionEntity);
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
                    if (CollectionUtil.isEmpty(processInstances)) {
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
            List<ProcessCategoryDto> processCategoryList = categoryService.selectListByCodes(categoryCodes);
            Map<String, ProcessCategoryDto> processCategoryMap = new HashMap<>();
            if (CollUtil.isNotEmpty(processCategoryList)) {
                processCategoryList.forEach(v -> processCategoryMap.put(v.getCategoryCode(), v));
            }
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
        return new PageDto<>(count, processDefinitionPageDtoList);
    }


    @Override
    @Transactional(rollbackFor = {Exception.class, Error.class})
    public void createDeployment(DeploymentVo vo) throws RuntimeException {
        // 查询模型是否存在
        DesignModelEntity designModelEntity = modelMapper.selectById(vo.getModelId());
        if (Objects.isNull(designModelEntity)) {
            throw new ResponseException(Status.DATABASE_BASE_ERROR, "未查询到数据");
        }
        if (Objects.isNull(designModelEntity.getDiagramData())) {
            throw new ResponseException(Status.DATABASE_BASE_ERROR, "模型不存在无法部署");
        }
        if (designModelEntity.getModelState() == ModelStateType.DEPLOYMENT.getType()) {
            throw new ResponseException(Status.DATABASE_BASE_ERROR, "模型已经部署");
        }
        DeploymentBuilder deploymentBuilder = repositoryService.createDeployment().name(vo.getDeploymentName());
        deploymentBuilder.addInputStream(vo.getDeploymentName() + CommonProcessConstant.MODEL_RESOURCE_SUFFIX, Base64FileUtils.base64ToInputStream(designModelEntity.getDiagramData()));
        if (StringUtils.isNotBlank(UserContextUtils.getCurrentTenantId())) {
            deploymentBuilder.tenantId(UserContextUtils.getCurrentTenantId());
        }
        if (Objects.nonNull(vo.getActivateProcessDate())) {
            deploymentBuilder.activateProcessDefinitionsOn(CoreCommonDateUtils.toDateTime(vo.getActivateProcessDate()));
        }
        Deployment deploy = deploymentBuilder.deploy();
        // 更新模型管理表状态
        DesignModelEntity entity = new DesignModelEntity();
        entity.setModelId(vo.getModelId());
        entity.setDeploymentTime(CoreCommonDateUtils.toLocalDateTime(deploy.getDeploymentTime()));
        entity.setDeploymentId(deploy.getId());
        entity.setModelState(ModelStateType.DEPLOYMENT.getType());
        entity.setDeploymentName(deploy.getName());
        // 获取部署资源信息
        List<Resource> deploymentResources = repositoryService.getDeploymentResources(deploy.getId());
        List<String> resourceIds = new ArrayList<>(deploymentResources.size());
        List<String> resourceNames = new ArrayList<>(deploymentResources.size());
        deploymentResources.forEach(v -> {
            resourceIds.add(v.getId());
            resourceNames.add(v.getName());
        });
        entity.setResourceNames(StringUtils.join(resourceNames, ","));
        entity.setResourceIds(StringUtils.join(resourceIds, ","));
        // 部署流程定义信息
        List<ProcessDefinition> processDefinitions = repositoryService.createProcessDefinitionQuery().deploymentId(deploy.getId()).list();
        List<String> diagramNames = new ArrayList<>(processDefinitions.size());
        List<String> processDefinitionKeys = new ArrayList<>(processDefinitions.size());
        List<String> processDefinitionIds = new ArrayList<>(processDefinitions.size());
        ProcessDefinition processDefinition = processDefinitions.get(0);
        entity.setVersion(processDefinition.getVersion());
        processDefinitions.forEach(v -> {
            diagramNames.add(v.getName());
            processDefinitionKeys.add(v.getKey());
            processDefinitionIds.add(v.getId());
        });
        entity.setDiagramNames(StringUtils.join(diagramNames, ","));
        entity.setDiagramData(designModelEntity.getDiagramData());
        entity.setProcessDefinitionIds(StringUtils.join(processDefinitionIds, ","));
        entity.setProcessDefinitionKeys(StringUtils.join(processDefinitionKeys, ","));
        int i = modelMapper.updateById(entity);
        if (i <= 0) {
            throw new ResponseException(Status.DATABASE_BASE_ERROR, "更新模型状态失败");
        }
        // 创建历史部署历史信息
        DesignModelHistoryVo designModelHistoryVo = toHistoryCopyMap.eToD(entity);
        designModelHistoryVo.setCategory(designModelEntity.getCategory());
        int insert = modelHistoryMapper.insert(toHistoryCopyMap.dToV(designModelHistoryVo));
        if (insert <= 0) {
            throw new ResponseException(Status.DATABASE_BASE_ERROR, "保存历史部署失败");
        }
    }


    @Override
    @Transactional(rollbackFor = {Exception.class, Error.class})
    public void historyDeployment(DeploymentHistoryVo vo) {
        DesignModelHistoryEntity designModelHistoryEntity = modelHistoryMapper.selectById(vo.getHistoryModelId());
        DeploymentBuilder deploymentBuilder = repositoryService.createDeployment();
        deploymentBuilder.addDeploymentResources(vo.getDeploymentId());
        if (StringUtils.isNotBlank(UserContextUtils.getCurrentTenantId())) {
            deploymentBuilder.tenantId(UserContextUtils.getCurrentTenantId());
        }
        if (Objects.nonNull(vo.getActivateProcessDate())) {
            deploymentBuilder.activateProcessDefinitionsOn(CoreCommonDateUtils.toDateTime(vo.getActivateProcessDate()));
        }
        deploymentBuilder.name(vo.getDeploymentName());
        Deployment deploy = deploymentBuilder.deploy();
        // 更新模型管理表状态
        DesignModelEntity entity = new DesignModelEntity();
        entity.setModelId(designModelHistoryEntity.getModelId());
        entity.setDeploymentTime(CoreCommonDateUtils.toLocalDateTime(deploy.getDeploymentTime()));
        entity.setDeploymentId(deploy.getId());
        entity.setDeploymentName(deploy.getName());
        List<ProcessDefinition> processDefinitions = repositoryService.createProcessDefinitionQuery().deploymentId(deploy.getId()).list();
        List<String> diagramNames = new ArrayList<>(processDefinitions.size());
        List<String> processDefinitionKeys = new ArrayList<>(processDefinitions.size());
        List<String> processDefinitionIds = new ArrayList<>(processDefinitions.size());
        ProcessDefinition processDefinition = processDefinitions.get(0);
        entity.setVersion(processDefinition.getVersion());
        processDefinitions.forEach(v -> {
            diagramNames.add(v.getName());
            processDefinitionKeys.add(v.getKey());
            processDefinitionIds.add(v.getId());
        });
        entity.setProcessDefinitionKeys(StringUtils.join(processDefinitionKeys, ","));
        entity.setProcessDefinitionIds(StringUtils.join(processDefinitionIds, ","));
        List<Resource> deploymentResources = repositoryService.getDeploymentResources(deploy.getId());
        List<String> resourceIds = new ArrayList<>(deploymentResources.size());
        List<String> resourceNames = new ArrayList<>(deploymentResources.size());
        deploymentResources.forEach(v -> {
            resourceIds.add(v.getId());
            resourceNames.add(v.getName());
        });
        entity.setResourceNames(StringUtils.join(resourceNames, ","));
        entity.setResourceIds(StringUtils.join(resourceIds, ","));
        int i = modelMapper.updateById(entity);
        if (i <= 0) {
            throw new ResponseException(Status.DATABASE_BASE_ERROR, "更新模型状态失败");
        }
        // 创建历史部署历史信息
        DesignModelHistoryVo designModelHistoryVo = toHistoryCopyMap.eToD(entity);
        designModelHistoryVo.setDiagramNames(StringUtils.join(diagramNames, ","));
        designModelHistoryVo.setDiagramData(designModelHistoryEntity.getDiagramData());
        designModelHistoryVo.setDeploymentId(deploy.getId());
        designModelHistoryVo.setDeploymentName(deploy.getName());
        designModelHistoryVo.setCategory(designModelHistoryEntity.getCategory());
        designModelHistoryVo.setProcessDefinitionKeys(StringUtils.join(processDefinitionKeys, ","));
        designModelHistoryVo.setProcessDefinitionIds(StringUtils.join(processDefinitionIds, ","));
        int insert = modelHistoryMapper.insert(toHistoryCopyMap.dToV(designModelHistoryVo));
        if (insert <= 0) {
            throw new ResponseException(Status.DATABASE_BASE_ERROR, "保存历史部署失败");
        }
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
        // 查询流程定义是否为最后一个，如果是最后一个修改模型管理为未发布
        long count = repositoryService.createProcessDefinitionQuery().processDefinitionKey(vo.getProcessDefinitionKey()).count();
        // if (count == 0) {
        // LambdaQueryWrapper<DesignModelEntity> lambdaQueryWrapper = new
        // LambdaQueryWrapper<>();
        // lambdaQueryWrapper.eq(DesignModelEntity::getProcessId,
        // vo.getProcessDefinitionKey());
        // DesignModelEntity entity = new DesignModelEntity();
        // entity.setModelState(0);
        // int update = modelMapper.update(entity, lambdaQueryWrapper);
        // if (update <= 0) {
        // throw new ResponseException(Status.DATABASE_BASE_ERROR, "修改模型管理状态失败");
        // }
        // }
    }


    @Override
    public DeploymentDetailDto getDeploymentDetail(String processKeywordId) {
        ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery().processDefinitionKey(processKeywordId).latestVersion().singleResult();
        if (Objects.isNull(processDefinition)) {
            processDefinition = repositoryService.createProcessDefinitionQuery().processDefinitionId(processKeywordId).singleResult();
        }
        if (Objects.isNull(processDefinition)) {
            throw new ResponseException(Status.DATABASE_BASE_ERROR, "当前流程部署信息不存在");
        }
        ProcessDefinitionEntity processDefinitionEntity = (ProcessDefinitionEntity) processDefinition;
        DeploymentEntity deploymentEntity = queryDeploymentById(processDefinition.getDeploymentId());
        DeploymentDetailDto deploymentDetailDto = new DeploymentDetailDto();
        deploymentDetailDto.setNew(deploymentEntity.isNew());
        deploymentDetailDto.setProcessDefinitionKey(processDefinitionEntity.getKey());
        // 获取流程模型信息
        LambdaQueryWrapper<DesignModelEntity> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(DesignModelEntity::getDeploymentId, processDefinition.getDeploymentId());
        DesignModelEntity designModelEntity = modelMapper.selectOne(lambdaQueryWrapper);
        if (Objects.nonNull(designModelEntity)) {
            deploymentDetailDto.setModelId(designModelEntity.getModelId());
            deploymentDetailDto.setResourceNames(deploymentEntity.getName());
        }
        deploymentDetailDto.setSuspensionState(processDefinitionEntity.getSuspensionState());
        deploymentDetailDto.setCategory(processDefinitionEntity.getCategory());
        deploymentDetailDto.setHasStartFormKey(processDefinitionEntity.getHasStartFormKey());
        deploymentDetailDto.setDeploymentId(processDefinition.getDeploymentId());
        deploymentDetailDto.setProcessDefinitionId(processDefinitionEntity.getId());
        Integer historyTimeToLive = processDefinition.getHistoryTimeToLive();
        InputStream processModel = repositoryService.getProcessModel(processDefinitionEntity.getId());
        deploymentDetailDto.setDiagramData(Base64FileUtils.inputStreamToBase64(processModel));
        deploymentDetailDto.setResourceNames(processDefinitionEntity.getDiagramResourceName());
        deploymentDetailDto.setDeploymentName(deploymentEntity.getName());
        deploymentDetailDto.setVersion(processDefinitionEntity.getVersion());
        deploymentDetailDto.setVersionTag(processDefinitionEntity.getVersionTag());
        // 如果有表单key则去获取
        if (deploymentDetailDto.isHasStartFormKey()) {
            String startFormKey = formService.getStartFormKey(deploymentDetailDto.getProcessDefinitionId());
            deploymentDetailDto.setStartFormKey(startFormKey);
        }
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
        if (StringUtils.isNotBlank(UserContextUtils.getCurrentTenantId())) {
            deploymentQuery = deploymentQuery.tenantIdIn(UserContextUtils.getCurrentTenantId());
        }
        Deployment deployment = deploymentQuery.singleResult();
        if (Objects.isNull(deployment) || !(deployment instanceof DeploymentEntity)) {
            throw new ResponseException(Status.DATABASE_BASE_ERROR, "流程部署信息不存在");
        }
        return (DeploymentEntity) deployment;
    }
}
