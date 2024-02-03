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

package com.anyilanxin.anyicloud.process.modules.monitorstatistics.service.impl;

import com.anyilanxin.anyicloud.corecommon.constant.AnYiResultStatus;
import com.anyilanxin.anyicloud.corecommon.exception.AnYiResponseException;
import com.anyilanxin.anyicloud.coreprocess.model.AnYiProcessDefinitionAnalystInfoModel;
import com.anyilanxin.anyicloud.process.modules.auxiliary.entity.SequenceFlowEntity;
import com.anyilanxin.anyicloud.process.modules.auxiliary.mapper.SequenceFlowMapper;
import com.anyilanxin.anyicloud.process.modules.monitorstatistics.service.IProcessDefinitionStatisticService;
import com.anyilanxin.anyicloud.process.utils.Base64FileUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.RequiredArgsConstructor;
import org.camunda.bpm.engine.HistoryService;
import org.camunda.bpm.engine.RepositoryService;
import org.camunda.bpm.engine.history.HistoricActivityStatistics;
import org.camunda.bpm.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.camunda.bpm.engine.repository.ProcessDefinition;
import org.dromara.hutool.core.collection.CollUtil;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.*;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ProcessDefinitionStatisticServiceImpl implements IProcessDefinitionStatisticService {
    private final SequenceFlowMapper sequenceFlowMapper;
    private final RepositoryService repositoryService;
    private final HistoryService historyService;

    @Override
    public AnYiProcessDefinitionAnalystInfoModel getDefinitionStatistic(String processDefinitionId) {
        ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery().processDefinitionId(processDefinitionId).singleResult();
        if (Objects.isNull(processDefinition)) {
            throw new AnYiResponseException(AnYiResultStatus.DATABASE_BASE_ERROR, "流程定义不存在");
        }
        InputStream processModel = repositoryService.getProcessModel(processDefinition.getId());
        if (Objects.isNull(processModel)) {
            throw new AnYiResponseException(AnYiResultStatus.DATABASE_BASE_ERROR, "流程模型不存在");
        }
        ProcessDefinitionEntity definitionEntity = (ProcessDefinitionEntity) processDefinition;
        // @formatter:off
        AnYiProcessDefinitionAnalystInfoModel definitionAnalystInfoModel = AnYiProcessDefinitionAnalystInfoModel
                .builder()
                .bpmnBase64(Base64FileUtils.inputStreamToBase64(processModel))
                .bpmnProcessDefinitionKey(definitionEntity.getId())
                .bpmnProcessDescription(definitionEntity.getDescription())
                .bpmnProcessName(definitionEntity.getName())
                .processVersion(definitionEntity.getVersion())
                .bpmnProcessId(definitionEntity.getKey())
                .build();
        List<HistoricActivityStatistics> activityStatistics = historyService.createHistoricActivityStatisticsQuery(processDefinitionId).includeFinished().includeCompleteScope().list();
        if (CollUtil.isNotEmpty(activityStatistics)) {
            LambdaQueryWrapper<SequenceFlowEntity> lambdaQueryWrapper = new LambdaQueryWrapper<>();
            lambdaQueryWrapper.eq(SequenceFlowEntity::getProcessDefinitionKey, processDefinitionId);
            List<SequenceFlowEntity> sequenceFlowEntities = sequenceFlowMapper.selectList(lambdaQueryWrapper);
            Map<String, Set<String>> stringSetMap = new HashMap<>();
            if (CollUtil.isNotEmpty(sequenceFlowEntities)) {
                stringSetMap = sequenceFlowEntities
                        .stream()
                        .collect(Collectors.groupingBy(SequenceFlowEntity::getTargetBpmnActivityId, Collectors.mapping(SequenceFlowEntity::getElementBpmnId, Collectors.toSet())));
            }
            Map<String, Set<String>> finalStringSetMap = stringSetMap;
            Map<String, AnYiProcessDefinitionAnalystInfoModel.AnYiDefinitionStatisticInfo> statisticInfo = activityStatistics.stream()
                    .map(v -> AnYiProcessDefinitionAnalystInfoModel.AnYiDefinitionStatisticInfo
                            .builder()
                            .bpmnElementId(v.getId())
                            .value(v.getInstances() + v.getFinished())
                            .build()
                    )
                    .peek(v -> v.setIncomingBpmnIds(finalStringSetMap.get(v.getBpmnElementId())))
                    .collect(Collectors.toMap(AnYiProcessDefinitionAnalystInfoModel.AnYiDefinitionStatisticInfo::getBpmnElementId, v -> v));
            definitionAnalystInfoModel.setStatisticInfo(statisticInfo);
        }
        // @formatter:off
        return definitionAnalystInfoModel;
    }
}
