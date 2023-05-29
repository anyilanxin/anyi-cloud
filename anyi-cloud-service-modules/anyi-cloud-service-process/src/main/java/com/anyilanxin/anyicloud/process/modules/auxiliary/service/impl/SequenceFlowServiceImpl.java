

package com.anyilanxin.anyicloud.process.modules.auxiliary.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.anyilanxin.anyicloudee.process.modules.auxiliary.entity.SequenceFlowEntity;
import com.anyilanxin.anyicloudee.process.modules.auxiliary.mapper.SequenceFlowMapper;
import com.anyilanxin.anyicloudee.process.modules.auxiliary.service.ISequenceFlowService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.util.*;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 流程活动实例连线信息(SequenceFlow)业务层实现
 *
 * @author zxh
 * @copyright zhouxuanhong（https://anyilanxin.com）
 * @date 2022-10-06 09:36:47
 * @since 1.0.0
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class SequenceFlowServiceImpl extends ServiceImpl<SequenceFlowMapper, SequenceFlowEntity> implements ISequenceFlowService {

    @Override
    @Transactional(rollbackFor = {Exception.class, Error.class}, readOnly = true)
    public Map<String, Set<String>> getSequenceFlowByInstanceId(String processInstanceId) throws RuntimeException {
        var lambdaQueryWrapper = new LambdaQueryWrapper<SequenceFlowEntity>();
        lambdaQueryWrapper.eq(SequenceFlowEntity::getProcessInstanceId, processInstanceId);
        var list = this.list(lambdaQueryWrapper);
        if (CollUtil.isEmpty(list)) {
            return Collections.emptyMap();
        }
        var result = new HashMap<String, Set<String>>(list.size());
        list.forEach(v -> {
            Set<String> sequenceFlowSet = result.get(v.getTargetActivityInstanceId());
            if (CollUtil.isEmpty(sequenceFlowSet)) {
                sequenceFlowSet = new HashSet<>();
            }
            sequenceFlowSet.add(v.getElementBpmnId());
            result.put(v.getTargetActivityInstanceId(), sequenceFlowSet);
        });
        return result;
    }


    @Override
    public Map<String, Map<String, Set<String>>> getSequenceFlowByDefinitionKey(String processDefinitionKey) throws RuntimeException {
        var lambdaQueryWrapper = new LambdaQueryWrapper<SequenceFlowEntity>();
        lambdaQueryWrapper.eq(SequenceFlowEntity::getProcessDefinitionKey, processDefinitionKey);
        var list = this.list(lambdaQueryWrapper);
        if (CollUtil.isEmpty(list)) {
            return Collections.emptyMap();
        }

        var result = new HashMap<String, Map<String, Set<String>>>(list.size());
        Map<String, List<SequenceFlowEntity>> collect = list.stream().collect(Collectors.groupingBy(SequenceFlowEntity::getProcessInstanceId));
        collect.forEach((k, v) -> {
            Map<String, Set<String>> stringSetMap = result.get(k);
            if (CollUtil.isEmpty(stringSetMap)) {
                stringSetMap = new HashMap<>(v.size());
            }
            Map<String, Set<String>> finalStringSetMap = stringSetMap;
            v.forEach(sv -> {
                Set<String> sequenceFlowSet = finalStringSetMap.get(sv.getTargetActivityInstanceId());
                if (CollUtil.isEmpty(sequenceFlowSet)) {
                    sequenceFlowSet = new HashSet<>();
                }
                sequenceFlowSet.add(sv.getElementBpmnId());
                finalStringSetMap.put(sv.getTargetActivityInstanceId(), sequenceFlowSet);
            });
            result.put(k, stringSetMap);
        });
        return result;
    }
}
