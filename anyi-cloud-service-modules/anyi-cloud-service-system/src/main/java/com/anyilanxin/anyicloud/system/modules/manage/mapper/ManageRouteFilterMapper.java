

package com.anyilanxin.anyicloud.system.modules.manage.mapper;

import com.anyilanxin.anyicloud.database.datasource.base.mapper.BaseMapper;
import com.anyilanxin.anyicloud.system.modules.manage.entity.ManageRouteFilterEntity;

import java.util.Collection;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * 路由过滤器(ManageRouteFilter)持久层
 *
 * @author zxh
 * @copyright zhouxuanhong（https://anyilanxin.com）
 * @date 2021-12-19 10:37:41
 * @since 1.0.0
 */
@Repository
public interface ManageRouteFilterMapper extends BaseMapper<ManageRouteFilterEntity> {

    /**
     * 通过过滤器id物理删除
     *
     * @param filterId ${@link String} 过滤器id
     * @return int ${@link Integer} 成功状态:0-失败,1-成功
     * @author zxh
     * @date 2021-12-19 10:37:41
     */
    int physicalDeleteById(@Param("id") String filterId);


    /**
     * 通过过滤器id物理批量删除
     *
     * @param idList ${@link Collection} 待删除id
     * @return int ${@link Integer} 成功状态:0-失败,大于1-成功
     * @author zxh
     * @date 2021-12-19 10:37:41
     */
    int physicalDeleteBatchIds(@Param("coll") Collection<String> idList);
}
