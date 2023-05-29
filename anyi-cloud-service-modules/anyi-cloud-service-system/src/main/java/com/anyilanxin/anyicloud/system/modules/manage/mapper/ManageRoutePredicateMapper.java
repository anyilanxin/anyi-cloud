

package com.anyilanxin.anyicloud.system.modules.manage.mapper;

import com.anyilanxin.anyicloud.database.datasource.base.mapper.BaseMapper;
import com.anyilanxin.anyicloud.system.modules.manage.entity.ManageRoutePredicateEntity;

import java.util.Collection;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * 路由断言(ManageRoutePredicate)持久层
 *
 * @author zxh
 * @copyright zhouxuanhong（https://anyilanxin.com）
 * @date 2021-12-19 10:37:42
 * @since 1.0.0
 */
@Repository
public interface ManageRoutePredicateMapper extends BaseMapper<ManageRoutePredicateEntity> {
    /**
     * 通过断言id物理删除
     *
     * @param predicateId ${@link String} 断言id
     * @return int ${@link Integer} 成功状态:0-失败,1-成功
     * @author zxh
     * @date 2021-12-19 10:37:42
     */
    int physicalDeleteById(@Param("id") String predicateId);


    /**
     * 通过断言id物理批量删除
     *
     * @param idList ${@link Collection} 待删除id
     * @return int ${@link Integer} 成功状态:0-失败,大于1-成功
     * @author zxh
     * @date 2021-12-19 10:37:42
     */
    int physicalDeleteBatchIds(@Param("coll") Collection<String> idList);
}
