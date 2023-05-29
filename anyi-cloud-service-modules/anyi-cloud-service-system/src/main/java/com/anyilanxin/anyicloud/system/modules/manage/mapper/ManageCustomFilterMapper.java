

package com.anyilanxin.anyicloud.system.modules.manage.mapper;

import com.anyilanxin.anyicloud.database.datasource.base.mapper.BaseMapper;
import com.anyilanxin.anyicloud.system.modules.manage.entity.ManageCustomFilterEntity;

import java.util.Collection;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * 自定义过滤器(ManageCustomFilter)持久层
 *
 * @author zxh
 * @copyright zhouxuanhong（https://anyilanxin.com）
 * @date 2021-12-19 00:22:14
 * @since 1.0.0
 */
@Repository
public interface ManageCustomFilterMapper extends BaseMapper<ManageCustomFilterEntity> {

    /**
     * 通过自定义过滤器id物理删除
     *
     * @param customFilterId ${@link String} 自定义过滤器id
     * @return int ${@link Integer} 成功状态:0-失败,1-成功
     * @author zxh
     * @date 2021-12-19 00:22:14
     */
    int physicalDeleteById(@Param("id") String customFilterId);


    /**
     * 通过自定义过滤器id物理批量删除
     *
     * @param idList ${@link Collection} 待删除id
     * @return int ${@link Integer} 成功状态:0-失败,大于1-成功
     * @author zxh
     * @date 2021-12-19 00:22:14
     */
    int physicalDeleteBatchIds(@Param("coll") Collection<String> idList);
}
