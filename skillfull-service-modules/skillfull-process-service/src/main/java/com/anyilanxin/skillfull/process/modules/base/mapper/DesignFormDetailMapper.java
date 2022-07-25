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
import com.anyilanxin.skillfull.process.modules.base.controller.vo.DesignFormDetailPageVo;
import com.anyilanxin.skillfull.process.modules.base.controller.vo.DesignFormDetailQueryVo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

/**
 * 流程表单详细(DesignFormDetail)持久层
 *
 * @author zxiaozhou
 * @date 2021-02-12 20:17:16
 * @since JDK1.8
 */
@Repository
public interface DesignFormDetailMapper extends BaseMapper<DesignFormDetailEntity> {
    /**
     * 分页查询
     *
     * @param vo   ${@link DesignFormDetailPageVo} 查询条件
     * @param page ${@link Page< DesignFormDetailPageDto >} 分页信息
     * @return IPage<DesignFormDetailPageDto> ${@link IPage<DesignFormDetailPageDto>} 结果
     * @author zxiaozhou
     * @date 2021-02-12 20:17:16
     */
    IPage<DesignFormDetailPageDto> pageByModel(Page<DesignFormDetailPageDto> page, @Param("query") DesignFormDetailPageVo vo);


    /**
     * 条件查询多条
     *
     * @param vo ${@link DesignFormDetailQueryVo} 查询条件
     * @return List<DesignFormDetailDto> ${@link List< DesignFormDetailDto >} 结果
     * @author zxiaozhou
     * @date 2021-02-12 20:17:16
     */
    List<DesignFormDetailDto> selectListByModel(DesignFormDetailQueryVo vo);


    /**
     * 通过表单详细id物理删除
     *
     * @param formDetailId ${@link String} 表单详细id
     * @return int ${@link Integer} 成功状态:0-失败,1-成功
     * @author zxiaozhou
     * @date 2020-08-28 11:36
     */
    int physicalDeleteById(@Param("id") String formDetailId);


    /**
     * 通过表单详细id物理批量删除
     *
     * @param idList ${@link Collection} 待删除id
     * @return int ${@link Integer} 成功状态:0-失败,大于1-成功
     * @author zxiaozhou
     * @date 2020-08-28 11:36
     */
    int physicalDeleteBatchIds(@Param("coll") Collection<String> idList);
}
