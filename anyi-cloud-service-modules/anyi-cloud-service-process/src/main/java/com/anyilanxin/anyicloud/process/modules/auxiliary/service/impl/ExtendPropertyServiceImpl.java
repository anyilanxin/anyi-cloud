

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
