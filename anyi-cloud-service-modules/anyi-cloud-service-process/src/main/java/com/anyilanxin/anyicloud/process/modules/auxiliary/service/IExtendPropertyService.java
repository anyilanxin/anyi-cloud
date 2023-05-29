

package com.anyilanxin.anyicloud.process.modules.auxiliary.service;

import com.anyilanxin.anyicloudee.database.datasource.base.service.BaseService;
import com.anyilanxin.anyicloudee.process.modules.auxiliary.entity.ExtendPropertyEntity;
import com.anyilanxin.anyicloudee.processrpc.model.ExtendPropertyModel;

import java.util.Map;
import java.util.Set;

/**
 * 流程定义扩展属性信息(ExtendProperty)业务层接口
 *
 * @author zxh
 * @copyright zhouxuanhong（https://anyilanxin.com）
 * @date 2022-11-18 10:38:07
 * @since 1.0.0
 */
public interface IExtendPropertyService extends BaseService<ExtendPropertyEntity> {

    /**
     * 获取扩展属性
     *
     * @param processDefinitionId 流程定义id
     * @return {@link Map }<{@link String }, {@link ExtendPropertyModel }>
     * @throws RuntimeException 运行时异常
     * @author zxh
     * @date 2022-11-18 10:48:13
     */
    Map<String, ExtendPropertyModel> getExtendProperty(String processDefinitionId) throws RuntimeException;


    /**
     * 获取扩展属性
     *
     * @param processDefinitionId       流程定义id
     * @param bpmnActivityDefinitionKey bpmn活动key
     * @return {@link ExtendPropertyModel }
     * @throws RuntimeException 运行时异常
     * @author zxh
     * @date 2022-11-18 10:48:15
     */
    ExtendPropertyModel getExtendProperty(String processDefinitionId, String bpmnActivityDefinitionKey) throws RuntimeException;


    /**
     * 获取扩展属性
     *
     * @param processDefinitionId       流程定义id
     * @param bpmnActivityDefinitionKey bpmn活动key
     * @return {@link ExtendPropertyModel }
     * @throws RuntimeException 运行时异常
     * @author zxh
     * @date 2022-11-18 10:48:15
     */
    <T> T getExtendProperty(String processDefinitionId, String bpmnActivityDefinitionKey, Class<T> tClass) throws RuntimeException;


    /**
     * 获取扩展属性
     *
     * @param processDefinitionIds 流程定义ids
     * @return {@link Map }<{@link String }, {@link Map }<{@link String }, {@link ExtendPropertyModel
     * }>>
     * @throws RuntimeException 运行时异常
     * @author zxh
     * @date 2022-11-18 10:48:17
     */
    Map<String, Map<String, ExtendPropertyModel>> getExtendProperties(Set<String> processDefinitionIds) throws RuntimeException;
}
