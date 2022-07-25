// +----------------------------------------------------------------------
// | SkillFull快速开发平台 [ SkillFull ]
// +----------------------------------------------------------------------
// | 版权所有 2020~2021 zxiaozhou
// +----------------------------------------------------------------------
// | 官方网站: https://www.divisu.com
// +----------------------------------------------------------------------
// | 作者: zxiaozhou <z7630853@163.com>
// +----------------------------------------------------------------------
package com.anyilanxin.skillfull.system.modules.manage.mapper;

import com.anyilanxin.skillfull.database.datasource.base.mapper.BaseMapper;
import com.anyilanxin.skillfull.system.modules.manage.entity.ManageRoutePredicateEntity;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;

/**
 * 路由断言(ManageRoutePredicate)持久层
 *
 * @author zxiaozhou
 * @copyright zxiaozhou（https://divisu.com）
 * @date 2021-12-19 10:37:42
 * @since JDK1.8
 */
@Repository
public interface ManageRoutePredicateMapper extends BaseMapper<ManageRoutePredicateEntity> {
    /**
     * 通过断言id物理删除
     *
     * @param predicateId ${@link String} 断言id
     * @return int ${@link Integer} 成功状态:0-失败,1-成功
     * @author zxiaozhou
     * @date 2021-12-19 10:37:42
     */
    int physicalDeleteById(@Param("id") String predicateId);


    /**
     * 通过断言id物理批量删除
     *
     * @param idList ${@link Collection} 待删除id
     * @return int ${@link Integer} 成功状态:0-失败,大于1-成功
     * @author zxiaozhou
     * @date 2021-12-19 10:37:42
     */
    int physicalDeleteBatchIds(@Param("coll") Collection<String> idList);
}
