package com.anyilanxin.skillfull.system.modules.manage.mapper;

import com.anyilanxin.skillfull.database.datasource.base.mapper.BaseMapper;
import com.anyilanxin.skillfull.system.modules.manage.entity.ManageSpecialUrlEntity;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;

/**
 * 路由特殊地址(ManageSpecialUrl)持久层
 *
 * @author zxiaozhou
 * @copyright zxiaozhou（https://skillfull.divisu.com）
 * @date 2021-12-19 09:34:50
 * @since JDK1.8
 */
@Repository
public interface ManageSpecialUrlMapper extends BaseMapper<ManageSpecialUrlEntity> {
    /**
     * 通过特殊过滤器id物理删除
     *
     * @param specialUrlId ${@link String} 特殊过滤器id
     * @return int ${@link Integer} 成功状态:0-失败,1-成功
     * @author zxiaozhou
     * @date 2021-12-19 09:34:50
     */
    int physicalDeleteById(@Param("id") String specialUrlId);


    /**
     * 通过特殊过滤器id物理批量删除
     *
     * @param idList ${@link Collection} 待删除id
     * @return int ${@link Integer} 成功状态:0-失败,大于1-成功
     * @author zxiaozhou
     * @date 2021-12-19 09:34:50
     */
    int physicalDeleteBatchIds(@Param("coll") Collection<String> idList);
}
