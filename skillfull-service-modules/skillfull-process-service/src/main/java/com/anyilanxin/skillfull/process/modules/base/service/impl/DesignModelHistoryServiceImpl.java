/**
 * Copyright (c) 2021-2022 ZHOUXUANHONG(安一老厨)<anyilanxin@aliyun.com>
 *
 * AnYi Cloud Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * AnYi Cloud 采用APACHE LICENSE 2.0开源协议，您在使用过程中，需要注意以下几点：
 *
 * 1.请不要删除和修改根目录下的LICENSE文件。
 * 2.请不要删除和修改 AnYi Cloud 源码头部的版权声明。
 * 3.请保留源码和相关描述文件的项目出处，作者声明等。
 * 4.分发源码时候，请注明软件出处 https://github.com/anyilanxin/anyi-cloud
 * 5.在修改包名，模块名称，项目代码等时，请注明软件出处 https://github.com/anyilanxin/anyi-cloud
 * 6.若您的项目无法满足以上几点，可申请商业授权
 */
package com.anyilanxin.skillfull.process.modules.base.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.anyilanxin.skillfull.corecommon.constant.Status;
import com.anyilanxin.skillfull.corecommon.exception.ResponseException;
import com.anyilanxin.skillfull.corecommon.utils.I18nUtil;
import com.anyilanxin.skillfull.database.datasource.base.service.dto.PageDto;
import com.anyilanxin.skillfull.process.modules.base.controller.vo.DeleteHistoryDesignModelVo;
import com.anyilanxin.skillfull.process.modules.base.controller.vo.DesignModelHistoryPageVo;
import com.anyilanxin.skillfull.process.modules.base.controller.vo.DesignModelHistoryVo;
import com.anyilanxin.skillfull.process.modules.base.entity.DesignModelHistoryEntity;
import com.anyilanxin.skillfull.process.modules.base.entity.ProcessCategoryEntity;
import com.anyilanxin.skillfull.process.modules.base.mapper.DesignModelHistoryMapper;
import com.anyilanxin.skillfull.process.modules.base.mapper.ProcessCategoryMapper;
import com.anyilanxin.skillfull.process.modules.base.service.IDesignModelHistoryService;
import com.anyilanxin.skillfull.process.modules.base.service.dto.DesignModelHistoryDto;
import com.anyilanxin.skillfull.process.modules.base.service.dto.DesignModelHistoryPageDto;
import com.anyilanxin.skillfull.process.modules.base.service.mapstruct.DesignModelHistoryCopyMap;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.HistoryService;
import org.camunda.bpm.engine.RepositoryService;
import org.camunda.bpm.engine.history.HistoricProcessInstance;
import org.camunda.bpm.engine.repository.Deployment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 流程模型历史(DesignModelHistory)业务层实现
 *
 * @author zxiaozhou
 * @date 2021-11-25 09:52:37
 * @since JDK1.8
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class DesignModelHistoryServiceImpl extends ServiceImpl<DesignModelHistoryMapper, DesignModelHistoryEntity> implements IDesignModelHistoryService {
    private final DesignModelHistoryCopyMap map;
    private final DesignModelHistoryMapper mapper;
    private final RepositoryService repositoryService;
    private final ProcessCategoryMapper categoryMapper;
    private final HistoryService historyService;


    @Override
    @Transactional(rollbackFor = {Exception.class, Error.class})
    public void save(DesignModelHistoryVo vo) throws RuntimeException {
        DesignModelHistoryEntity entity = map.vToE(vo);
        boolean result = super.save(entity);
        if (!result) {
            throw new ResponseException(Status.DATABASE_BASE_ERROR, I18nUtil.get("ServiceImpl.SaveDataFail"));
        }
    }


    @Override
    @Transactional(rollbackFor = {Exception.class, Error.class}, readOnly = true)
    public PageDto<DesignModelHistoryPageDto> pageByModel(DesignModelHistoryPageVo vo) throws RuntimeException {
        IPage<DesignModelHistoryPageDto> designModelHistoryPageDtoIPage = mapper.pageByModel(vo.getPage(), vo);
        List<DesignModelHistoryPageDto> records = designModelHistoryPageDtoIPage.getRecords();
        if (CollUtil.isNotEmpty(records)) {
            Set<String> processDefinitionKeysSet = new HashSet<>(records.size());
            records.forEach(v -> processDefinitionKeysSet.addAll(Set.of(v.getProcessDefinitionKeys().split("[,，]"))));
            // 获取所有数据进行统计分析
            List<HistoricProcessInstance> processInstances = historyService.createHistoricProcessInstanceQuery().processDefinitionKeyIn(processDefinitionKeysSet.toArray(new String[]{})).list();
            if (CollUtil.isNotEmpty(processInstances)) {
                // 数据分组求和
                Map<String, Map<String, Long>> statisticalResult = new HashMap<>();
                processInstances.stream()
                        .collect(Collectors.groupingBy(HistoricProcessInstance::getProcessDefinitionId))
                        .forEach((k, v) -> {
                            long active = v.stream().filter(sv -> HistoricProcessInstance.STATE_ACTIVE.equals(sv.getState())).count();
                            long suspended = v.stream().filter(sv -> HistoricProcessInstance.STATE_SUSPENDED.equals(sv.getState())).count();
                            long completed = v.stream().filter(sv -> HistoricProcessInstance.STATE_COMPLETED.equals(sv.getState())).count();
                            long externallyTerminated = v.stream().filter(sv -> HistoricProcessInstance.STATE_EXTERNALLY_TERMINATED.equals(sv.getState())).count();
                            long internallyTerminated = v.stream().filter(sv -> HistoricProcessInstance.STATE_INTERNALLY_TERMINATED.equals(sv.getState())).count();
                            Map<String, Long> statistical = new HashMap<>();
                            statistical.put(HistoricProcessInstance.STATE_ACTIVE, active);
                            statistical.put(HistoricProcessInstance.STATE_SUSPENDED, suspended);
                            statistical.put(HistoricProcessInstance.STATE_COMPLETED, completed);
                            statistical.put(HistoricProcessInstance.STATE_EXTERNALLY_TERMINATED, externallyTerminated);
                            statistical.put(HistoricProcessInstance.STATE_INTERNALLY_TERMINATED, internallyTerminated);
                            statisticalResult.put(k, statistical);
                        });
                // 分别赋值
                records.forEach(v -> {
                    String[] processDefinitionIdSet = v.getProcessDefinitionIds().split("[,，]");
                    long active = 0;
                    long suspended = 0;
                    long completed = 0;
                    long externallyTerminated = 0;
                    long internallyTerminated = 0;
                    for (String processDefinitionId : processDefinitionIdSet) {
                        Map<String, Long> stringLongMap = statisticalResult.get(processDefinitionId);
                        if (CollUtil.isNotEmpty(stringLongMap)) {
                            active += stringLongMap.get(HistoricProcessInstance.STATE_ACTIVE);
                            suspended += stringLongMap.get(HistoricProcessInstance.STATE_SUSPENDED);
                            completed += stringLongMap.get(HistoricProcessInstance.STATE_COMPLETED);
                            externallyTerminated += stringLongMap.get(HistoricProcessInstance.STATE_EXTERNALLY_TERMINATED);
                            internallyTerminated += stringLongMap.get(HistoricProcessInstance.STATE_INTERNALLY_TERMINATED);
                        }
                    }
                    v.setActive(active);
                    v.setSuspended(suspended);
                    v.setCompleted(completed);
                    v.setExternallyTerminated(externallyTerminated);
                    v.setInternallyTerminated(internallyTerminated);
                    v.setTotalNum(active + suspended + completed + externallyTerminated + internallyTerminated);
                });
            }
        }
        return new PageDto<>(designModelHistoryPageDtoIPage.getTotal(), records);
    }


    @Override
    @Transactional(rollbackFor = {Exception.class, Error.class}, readOnly = true)
    public DesignModelHistoryDto getById(String historyModelId) throws RuntimeException {
        DesignModelHistoryEntity byId = super.getById(historyModelId);
        if (Objects.isNull(byId)) {
            throw new ResponseException(Status.DATABASE_BASE_ERROR, I18nUtil.get("ServiceImpl.QueryDataFail"));
        }
        DesignModelHistoryDto designModelHistoryDto = map.eToD(byId);
        // 查询类别
        LambdaQueryWrapper<ProcessCategoryEntity> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(ProcessCategoryEntity::getCategoryCode, byId.getCategory());
        ProcessCategoryEntity processCategoryEntity = categoryMapper.selectOne(lambdaQueryWrapper);
        if (Objects.nonNull(processCategoryEntity)) {
            designModelHistoryDto.setCategoryName(processCategoryEntity.getCategoryName());
        }
        // 获取所有数据进行统计分析
        List<HistoricProcessInstance> processInstances = historyService.createHistoricProcessInstanceQuery().processDefinitionKeyIn(designModelHistoryDto.getProcessDefinitionKeys().split("[,，]")).list();
        if (CollUtil.isNotEmpty(processInstances)) {
            // 数据分组求和
            long active = processInstances.stream().filter(sv -> HistoricProcessInstance.STATE_ACTIVE.equals(sv.getState())).count();
            long suspended = processInstances.stream().filter(sv -> HistoricProcessInstance.STATE_SUSPENDED.equals(sv.getState())).count();
            long completed = processInstances.stream().filter(sv -> HistoricProcessInstance.STATE_COMPLETED.equals(sv.getState())).count();
            long externallyTerminated = processInstances.stream().filter(sv -> HistoricProcessInstance.STATE_EXTERNALLY_TERMINATED.equals(sv.getState())).count();
            long internallyTerminated = processInstances.stream().filter(sv -> HistoricProcessInstance.STATE_INTERNALLY_TERMINATED.equals(sv.getState())).count();
            designModelHistoryDto.setActive(active);
            designModelHistoryDto.setSuspended(suspended);
            designModelHistoryDto.setCompleted(completed);
            designModelHistoryDto.setExternallyTerminated(externallyTerminated);
            designModelHistoryDto.setInternallyTerminated(internallyTerminated);
            designModelHistoryDto.setTotalNum(active + suspended + completed + externallyTerminated + internallyTerminated);
        }
        return designModelHistoryDto;
    }


    @Override
    @Transactional(rollbackFor = {Exception.class, Error.class})
    public void deleteByModel(DeleteHistoryDesignModelVo vo) {
        // 查询数据是否存在
        DesignModelHistoryEntity byId = super.getById(vo.getHistoryModelId());
        if (Objects.isNull(byId)) {
            throw new ResponseException(Status.DATABASE_BASE_ERROR, I18nUtil.get("ServiceImpl.QueryDataFail"));
        }
        // 删除引擎流程部署信息
        Deployment deployment = repositoryService.createDeploymentQuery().deploymentId(byId.getDeploymentId()).singleResult();
        if (Objects.nonNull(deployment)) {
            repositoryService.deleteDeployment(deployment.getId(), vo.getCascade(), vo.getSkipCustomListeners(), vo.getSkipIoMappings());
        }
        // 删除自定义历史数据
        boolean b = this.removeById(vo.getHistoryModelId());
        if (!b) {
            throw new ResponseException(Status.DATABASE_BASE_ERROR, "删除历史部署信息失败");
        }

    }
}
