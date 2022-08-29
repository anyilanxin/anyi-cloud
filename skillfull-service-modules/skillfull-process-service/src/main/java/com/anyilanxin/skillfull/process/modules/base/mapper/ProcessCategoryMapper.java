// +----------------------------------------------------------------------
// | SkillFull快速开发平台 [ SkillFull ]
// +----------------------------------------------------------------------
// | 版权所有 2020~2021 zxiaozhou
// +----------------------------------------------------------------------
// | 官方网站: https://skillfull.divisu.com
// +----------------------------------------------------------------------
// | 作者: zxiaozhou <z7630853@163.com>
// +----------------------------------------------------------------------
package com.anyilanxin.skillfull.process.modules.base.mapper;

import com.anyilanxin.skillfull.database.datasource.base.mapper.BaseMapper;
import com.anyilanxin.skillfull.process.modules.base.controller.vo.ProcessCategoryPageVo;
import com.anyilanxin.skillfull.process.modules.base.controller.vo.ProcessCategoryQueryVo;
import com.anyilanxin.skillfull.process.modules.base.entity.ProcessCategoryEntity;
import com.anyilanxin.skillfull.process.modules.base.service.dto.ProcessCategoryDto;
import com.anyilanxin.skillfull.process.modules.base.service.dto.ProcessCategoryPageDto;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

/**
 * 流程类别(ProcessCategory)持久层
 *
 * @author zxiaozhou
 * @date 2021-11-19 10:47:01
 * @since JDK1.8
 */
@Repository
public interface ProcessCategoryMapper extends BaseMapper<ProcessCategoryEntity> {
    /**
     * 分页查询
     *
     * @param vo   ${@link ProcessCategoryPageVo} 查询条件
     * @param page ${@link Page< ProcessCategoryPageDto >} 分页信息
     * @return IPage<ProcessCategoryPageDto> ${@link IPage<ProcessCategoryPageDto>} 结果
     * @author zxiaozhou
     * @date 2021-11-19 10:47:01
     */
    IPage<ProcessCategoryPageDto> pageByModel(Page<ProcessCategoryPageDto> page, @Param("query") ProcessCategoryPageVo vo);


    /**
     * 条件查询多条
     *
     * @param vo ${@link ProcessCategoryQueryVo} 查询条件
     * @return List<ProcessCategoryDto> ${@link List< ProcessCategoryDto >} 结果
     * @author zxiaozhou
     * @date 2021-11-19 10:47:01
     */
    List<ProcessCategoryDto> selectListByModel(ProcessCategoryQueryVo vo);


    /**
     * 通过类别id物理删除
     *
     * @param categoryId ${@link String} 类别id
     * @return int ${@link Integer} 成功状态:0-失败,1-成功
     * @author zxiaozhou
     * @date 2021-11-19 10:47:01
     */
    int physicalDeleteById(@Param("id") String categoryId);


    /**
     * 通过类别id物理批量删除
     *
     * @param idList ${@link Collection} 待删除id
     * @return int ${@link Integer} 成功状态:0-失败,大于1-成功
     * @author zxiaozhou
     * @date 2021-11-19 10:47:01
     */
    int physicalDeleteBatchIds(@Param("coll") Collection<String> idList);
}
