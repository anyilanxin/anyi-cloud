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
