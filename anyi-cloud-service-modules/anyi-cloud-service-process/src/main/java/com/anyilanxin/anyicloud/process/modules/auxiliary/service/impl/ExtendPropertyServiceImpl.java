/*
 * Copyright (c) 2021-2022 ZHOUXUANHONG(安一老厨)<anyilanxin@aliyun.com>
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
 *   1.请不要删除和修改根目录下的LICENSE文件；
 *   2.请不要删除和修改 AnYi Cloud 源码头部的版权声明；
 *   3.请保留源码和相关描述文件的项目出处，作者声明等；
 *   4.分发源码时候，请注明软件出处 https://github.com/anyilanxin/anyi-cloud；
 *   5.在修改包名，模块名称，项目代码等时，请注明软件出处 https://github.com/anyilanxin/anyi-cloud；
 *   6.本软件不允许在国家法律规定范围外使用，如出现违法行为原作者本人不承担任何法律风险；
 *   7.本软件使用的第三方依赖皆为开源软件，如需要修改第三方源码请遵循第三方源码附带开源协议；
 *   8.本软件流程部分请遵循camunda开源协议：
 *     https://docs.camunda.org/manual/latest/introduction/third-party-libraries
 *     https://github.com/camunda/camunda-bpm-platform/blob/master/LICENSE
 *   9.若您的项目无法满足以上几点，可申请商业授权。
 */

package com.anyilanxin.anyicloud.process.modules.auxiliary.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.alibaba.fastjson2.JSONObject;
import com.anyilanxin.anyicloudee.process.modules.auxiliary.entity.ExtendPropertyEntity;
import com.anyilanxin.anyicloudee.process.modules.auxiliary.mapper.ExtendPropertyMapper;
import com.anyilanxin.anyicloudee.process.modules.auxiliary.service.IExtendPropertyService;
import com.anyilanxin.anyicloudee.process.modules.auxiliary.service.mapstruct.ExtendPropertyCopyMap;
import com.anyilanxin.anyicloudee.processrpc.model.ExtendPropertyModel;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import java.util.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 流程定义扩展属性信息(ExtendProperty)业务层实现
 *
 * @author zxh
 * @copyright zhouxuanhong（https://anyilanxin.com）
 * @date 2022-11-18 10:38:07
 * @since 1.0.0
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ExtendPropertyServiceImpl extends ServiceImpl<ExtendPropertyMapper, ExtendPropertyEntity> implements IExtendPropertyService {
    private final ExtendPropertyCopyMap map;
    private final ExtendPropertyMapper mapper;

    @Override
    public Map<String, ExtendPropertyModel> getExtendProperty(String processDefinitionId) throws RuntimeException {
        var lambdaQueryWrapper = Wrappers.<ExtendPropertyEntity>lambdaQuery().eq(ExtendPropertyEntity::getProcessDefinitionId, processDefinitionId);
        List<ExtendPropertyEntity> list = this.list(lambdaQueryWrapper);
        if (CollUtil.isEmpty(list)) {
            return Collections.emptyMap();
        }
        var resultMap = new HashMap<String, ExtendPropertyModel>(list.size());
        list.forEach(v -> resultMap.put(v.getBpmnActivityDefinitionKey(), map.aToB(v)));
        return resultMap;
    }


    @Override
    public ExtendPropertyModel getExtendProperty(String processDefinitionId, String bpmnActivityDefinitionKey) throws RuntimeException {
        var lambdaQueryWrapper = Wrappers.<ExtendPropertyEntity>lambdaQuery().eq(ExtendPropertyEntity::getProcessDefinitionId, processDefinitionId).eq(ExtendPropertyEntity::getBpmnActivityDefinitionKey, bpmnActivityDefinitionKey);
        ExtendPropertyEntity one = this.getOne(lambdaQueryWrapper);
        if (Objects.isNull(one)) {
            return null;
        }
        return map.aToB(one);
    }


    @Override
    public <T> T getExtendProperty(String processDefinitionId, String bpmnActivityDefinitionKey, Class<T> tClass) throws RuntimeException {
        var lambdaQueryWrapper = Wrappers.<ExtendPropertyEntity>lambdaQuery().eq(ExtendPropertyEntity::getProcessDefinitionId, processDefinitionId).eq(ExtendPropertyEntity::getBpmnActivityDefinitionKey, bpmnActivityDefinitionKey);
        ExtendPropertyEntity one = this.getOne(lambdaQueryWrapper);
        if (Objects.isNull(one)) {
            return null;
        }
        JSONObject extendPropertyInfo = one.getExtendPropertyInfo();
        return JSONObject.parseObject(extendPropertyInfo.toJSONString(), tClass);
    }


    @Override
    public Map<String, Map<String, ExtendPropertyModel>> getExtendProperties(Set<String> processDefinitionIds) throws RuntimeException {
        var lambdaQueryWrapper = Wrappers.<ExtendPropertyEntity>lambdaQuery().in(ExtendPropertyEntity::getProcessDefinitionId, processDefinitionIds);
        List<ExtendPropertyEntity> list = this.list(lambdaQueryWrapper);
        if (CollUtil.isEmpty(list)) {
            return Collections.emptyMap();
        }
        var resultMap = new HashMap<String, Map<String, ExtendPropertyModel>>(list.size());
        list.forEach(v -> {
            var modelMap = resultMap.get(v.getProcessDefinitionId());
            if (CollUtil.isEmpty(modelMap)) {
                modelMap = new HashMap<>(32);
            }
            modelMap.put(v.getBpmnActivityDefinitionKey(), map.aToB(v));
            resultMap.put(v.getProcessDefinitionId(), modelMap);
        });
        return resultMap;
    }
}
