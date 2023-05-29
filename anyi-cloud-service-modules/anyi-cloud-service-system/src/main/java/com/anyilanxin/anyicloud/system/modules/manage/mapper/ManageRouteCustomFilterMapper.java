

package com.anyilanxin.anyicloud.system.modules.manage.mapper;

import com.anyilanxin.anyicloud.database.datasource.base.mapper.BaseMapper;
import com.anyilanxin.anyicloud.system.modules.manage.entity.ManageRouteCustomFilterEntity;
import com.anyilanxin.anyicloud.system.modules.manage.service.dto.ManageRouteCustomFilterDto;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * 路由-自定义过滤器表(ManageRouteCustomFilter)持久层
 *
 * @author zxh
 * @copyright zhouxuanhong（https://anyilanxin.com）
 * @date 2021-12-19 00:22:17
 * @since 1.0.0
 */
@Repository
public interface ManageRouteCustomFilterMapper extends BaseMapper<ManageRouteCustomFilterEntity> {
    /**
     * 查询当前路由的所有特殊过滤器
     *
     * @param routerIds ${@link Set<String>} 过滤器ids
     * @return List<ManageRouteCustomFilterDto> ${@link List< ManageRouteCustomFilterDto >} 结果
     * @author zxh
     * @date 2021-12-19 00:22:17
     */
    List<ManageRouteCustomFilterDto> selectListByRouterIds(Set<String> routerIds);


    /**
     * 通过路由自定义过滤器id物理删除
     *
     * @param routeCustomFilterId ${@link String} 路由自定义过滤器id
     * @return int ${@link Integer} 成功状态:0-失败,1-成功
     * @author zxh
     * @date 2021-12-19 00:22:17
     */
    int physicalDeleteById(@Param("id") String routeCustomFilterId);


    /**
     * 通过路由自定义过滤器id物理批量删除
     *
     * @param idList ${@link Collection} 待删除id
     * @return int ${@link Integer} 成功状态:0-失败,大于1-成功
     * @author zxh
     * @date 2021-12-19 00:22:17
     */
    int physicalDeleteBatchIds(@Param("coll") Collection<String> idList);
}
