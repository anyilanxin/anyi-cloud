// +----------------------------------------------------------------------
// | SkillFull快速开发平台 [ SkillFull ]
// +----------------------------------------------------------------------
// | 版权所有 2020~2021 zxiaozhou
// +----------------------------------------------------------------------
// | 官方网站: https://www.divisu.com
// +----------------------------------------------------------------------
// | 作者: zxiaozhou <z7630853@163.com>
// +----------------------------------------------------------------------
package com.anyilanxin.skillfull.process.modules.base.mapper;

import com.anyilanxin.skillfull.database.datasource.base.mapper.BaseMapper;
import com.anyilanxin.skillfull.process.modules.base.controller.vo.DesignExpressionPageVo;
import com.anyilanxin.skillfull.process.modules.base.controller.vo.DesignExpressionQueryVo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

/**
 * 流程表达式(DesignExpression)持久层
 *
 * @author zxiaozhou
 * @copyright zxiaozhou（https://divisu.com）
 * @date 2022-03-27 00:20:29
 * @since JDK1.8
 */
@Repository
public interface DesignExpressionMapper extends BaseMapper<DesignExpressionEntity> {
    /**
     * 分页查询
     *
     * @param vo   ${@link DesignExpressionPageVo} 查询条件
     * @param page ${@link Page< DesignExpressionPageDto >} 分页信息
     * @return IPage<DesignExpressionPageDto> ${@link IPage<DesignExpressionPageDto>} 结果
     * @author zxiaozhou
     * @date 2022-03-27 00:20:29
     */
    IPage<DesignExpressionPageDto> pageByModel(Page<DesignExpressionPageDto> page, @Param("query") DesignExpressionPageVo vo);


    /**
     * 条件查询多条
     *
     * @param vo ${@link DesignExpressionQueryVo} 查询条件
     * @return List<DesignExpressionDto> ${@link List< DesignExpressionDto >} 结果
     * @author zxiaozhou
     * @date 2022-03-27 00:20:29
     */
    List<DesignExpressionDto> selectListByModel(DesignExpressionQueryVo vo);


    /**
     * 通过表达式id物理删除
     *
     * @param expressionId ${@link String} 表达式id
     * @return int ${@link Integer} 成功状态:0-失败,1-成功
     * @author zxiaozhou
     * @date 2022-03-27 00:20:29
     */
    int physicalDeleteById(@Param("id") String expressionId);


    /**
     * 通过表达式id物理批量删除
     *
     * @param idList ${@link Collection} 待删除id
     * @return int ${@link Integer} 成功状态:0-失败,大于1-成功
     * @author zxiaozhou
     * @date 2022-03-27 00:20:29
     */
    int physicalDeleteBatchIds(@Param("coll") Collection<String> idList);
}
