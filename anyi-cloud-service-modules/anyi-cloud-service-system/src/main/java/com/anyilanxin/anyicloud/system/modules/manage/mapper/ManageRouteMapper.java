

package com.anyilanxin.anyicloud.system.modules.manage.mapper;

import com.anyilanxin.anyicloud.database.datasource.base.mapper.BaseMapper;
import com.anyilanxin.anyicloud.system.modules.manage.entity.ManageRouteEntity;

import java.util.Collection;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * 路由(ManageRoute)持久层
 *
 * @author zxh
 * @copyright zhouxuanhong（https://anyilanxin.com）
 * @date 2021-12-19 00:22:16
 * @since 1.0.0
 */
@Repository
public interface ManageRouteMapper extends BaseMapper<ManageRouteEntity> {
    /**
     * 通过路由id物理删除
     *
     * @param routeId ${@link String} 路由id
     * @return int ${@link Integer} 成功状态:0-失败,1-成功
     * @author zxh
     * @date 2021-12-19 00:22:16
     */
    int physicalDeleteById(@Param("id") String routeId);


    /**
     * 通过路由id物理批量删除
     *
     * @param idList ${@link Collection} 待删除id
     * @return int ${@link Integer} 成功状态:0-失败,大于1-成功
     * @author zxh
     * @date 2021-12-19 00:22:16
     */
    int physicalDeleteBatchIds(@Param("coll") Collection<String> idList);
}
