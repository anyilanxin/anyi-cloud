

package com.anyilanxin.anyicloud.process.modules.auxiliary.mapper;

import com.anyilanxin.anyicloudee.database.datasource.base.mapper.BaseMapper;
import com.anyilanxin.anyicloudee.process.modules.auxiliary.entity.SequenceFlowEntity;

import java.util.Collection;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * 流程活动实例连线信息(SequenceFlow)持久层
 *
 * @author zxh
 * @copyright zhouxuanhong（https://anyilanxin.com）
 * @date 2022-10-06 09:36:47
 * @since 1.0.0
 */
@Repository
public interface SequenceFlowMapper extends BaseMapper<SequenceFlowEntity> {
    /**
     * 通过连线实例信息id(实例id+连线bpmnId)物理删除
     *
     * @param sequenceFlowId 连线实例信息id(实例id+连线bpmnId)
     * @return int 成功状态:0-失败,1-成功
     * @author zxh
     * @date 2022-10-06 09:36:47
     */
    int physicalDeleteById(@Param("id") String sequenceFlowId);


    /**
     * 通过连线实例信息id(实例id+连线bpmnId)物理批量删除
     *
     * @param idList 连线实例信息id(实例id+连线bpmnId)列表
     * @return int 成功状态:0-失败,大于1-成功
     * @author zxh
     * @date 2022-10-06 09:36:47
     */
    int physicalDeleteBatchIds(@Param("coll") Collection<String> idList);
}
