

package com.anyilanxin.anyicloud.process.modules.auxiliary.service;

import com.anyilanxin.anyicloudee.database.datasource.base.service.BaseService;
import com.anyilanxin.anyicloudee.process.modules.auxiliary.entity.SequenceFlowEntity;
import com.anyilanxin.anyicloudee.processrpc.model.SequenceFlowModel;

import java.util.Map;
import java.util.Set;

/**
 * 流程活动实例连线信息(SequenceFlow)业务层接口
 *
 * @author zxh
 * @copyright zhouxuanhong（https://anyilanxin.com）
 * @date 2022-10-06 09:36:47
 * @since 1.0.0
 */
public interface ISequenceFlowService extends BaseService<SequenceFlowEntity> {
    /**
     * 通过流程实例获取连线
     *
     * @param processInstanceId 流程实例id
     * @return {@link Map }<{@link String }, {@link SequenceFlowModel }>
     * @throws RuntimeException 运行时异常
     * @author zxh
     * @date 2022-10-06 11:15:31
     */
    Map<String, Set<String>> getSequenceFlowByInstanceId(String processInstanceId) throws RuntimeException;


    /**
     * 通过流程定义获取连线
     *
     * @param processInstanceId 流程实例id
     * @return {@link Map }<{@link String }, {@link Map }<{@link String }, {@link Set }<{@link String }>>>
     * @throws RuntimeException 运行时异常
     * @author zxh
     * @date 2023-05-06 13:16:30
     */
    Map<String, Map<String, Set<String>>> getSequenceFlowByDefinitionKey(String processInstanceId) throws RuntimeException;
}
