// +----------------------------------------------------------------------
// | SkillFull快速开发平台 [ SkillFull ]
// +----------------------------------------------------------------------
// | 版权所有 2020~2021 zxiaozhou
// +----------------------------------------------------------------------
// | 官方网站: https://skillfull.divisu.com
// +----------------------------------------------------------------------
// | 作者: zxiaozhou <z7630853@163.com>
// +----------------------------------------------------------------------
package com.anyilanxin.skillfull.database.datasource.base.mapper;

import java.util.Collection;

/**
 * mapper基类
 *
 * @author zxiaozhou
 * @date 2020-06-22 15:24
 * @since JDK11
 */
public interface MysqlBaseMapper<T> extends com.baomidou.mybatisplus.core.mapper.BaseMapper<T> {

    /**
     * 批量插入
     * <p>
     * 该批量插入只支持mysql,同时需要创建MysqlBatchInjector bean
     * </p>
     *
     * @param entityList 实体列表
     * @return {@link Integer }
     * @author zxiaozhou
     * @date 2022-10-07 19:00:46
     */
    Integer insertBatchSomeColumn(Collection<T> entityList);
}
