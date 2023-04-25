package com.anyilanxin.skillfull.system.modules.common.mapper;

import com.anyilanxin.skillfull.database.datasource.base.mapper.BaseMapper;
import com.anyilanxin.skillfull.system.modules.common.controller.vo.CommonCategoryPageVo;
import com.anyilanxin.skillfull.system.modules.common.entity.CommonCategoryEntity;
import com.anyilanxin.skillfull.system.modules.common.service.dto.CommonCategoryPageDto;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;

/**
 * 分类字典表(CommonCategory)持久层
 *
 * @author zxiaozhou
 * @date 2021-01-07 23:40:04
 * @since JDK11
 */
@Repository
public interface CommonCategoryMapper extends BaseMapper<CommonCategoryEntity> {
    /**
     * 分页查询
     *
     * @param vo   ${@link CommonCategoryPageVo} 查询条件
     * @param page ${@link Page< CommonCategoryPageDto >} 分页信息
     * @return IPage<CommonCategoryPageDto> ${@link IPage<CommonCategoryPageDto>} 结果
     * @author zxiaozhou
     * @date 2021-01-07 23:40:04
     */
    IPage<CommonCategoryPageDto> pageByModel(Page<CommonCategoryPageDto> page, @Param("query") CommonCategoryPageVo vo);

    /**
     * 通过分类id物理删除
     *
     * @param categoryId ${@link String} 分类id
     * @return int ${@link Integer} 成功状态:0-失败,1-成功
     * @author zxiaozhou
     * @date 2020-08-28 11:36
     */
    int physicalDeleteById(@Param("id") String categoryId);


    /**
     * 通过分类id物理批量删除
     *
     * @param idList ${@link Collection} 待删除id
     * @return int ${@link Integer} 成功状态:0-失败,大于1-成功
     * @author zxiaozhou
     * @date 2020-08-28 11:36
     */
    int physicalDeleteBatchIds(@Param("coll") Collection<String> idList);
}
