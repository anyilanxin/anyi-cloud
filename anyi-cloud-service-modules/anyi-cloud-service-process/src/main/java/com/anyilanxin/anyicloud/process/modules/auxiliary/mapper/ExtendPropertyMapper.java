

package com.anyilanxin.anyicloud.process.modules.auxiliary.mapper;

import com.anyilanxin.anyicloudee.database.datasource.base.mapper.BaseMapper;
import com.anyilanxin.anyicloudee.process.modules.auxiliary.entity.ExtendPropertyEntity;

import java.util.Collection;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * 流程定义扩展属性信息(ExtendProperty)持久层
 *
 * @author zxh
 * @copyright zhouxuanhong（https://anyilanxin.com）
 * @date 2022-11-18 10:38:07
 * @since 1.0.0
 */
@Repository
public interface ExtendPropertyMapper extends BaseMapper<ExtendPropertyEntity> {

    /**
     * 通过扩展属性id物理删除
     *
     * @param propertyId 扩展属性id
     * @return int 成功状态:0-失败,1-成功
     * @author zxh
     * @date 2022-11-18 10:38:07
     */
    int physicalDeleteById(@Param("id") String propertyId);


    /**
     * 通过扩展属性id物理批量删除
     *
     * @param idList 扩展属性id列表
     * @return int 成功状态:0-失败,大于1-成功
     * @author zxh
     * @date 2022-11-18 10:38:07
     */
    int physicalDeleteBatchIds(@Param("coll") Collection<String> idList);
}
