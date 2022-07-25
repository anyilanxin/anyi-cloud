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
import com.anyilanxin.skillfull.process.modules.base.controller.vo.DesignFormPageVo;
import com.anyilanxin.skillfull.process.modules.base.controller.vo.DesignFormQueryVo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

/**
 * 流程表单(DesignForm)持久层
 *
 * @author zxiaozhou
 * @date 2021-02-12 20:15:50
 * @since JDK1.8
 */
@Repository
public interface DesignFormMapper extends BaseMapper<DesignFormEntity> {
    /**
     * 分页查询
     *
     * @param vo   ${@link DesignFormPageVo} 查询条件
     * @param page ${@link Page< DesignFormPageDto >} 分页信息
     * @return IPage<DesignFormPageDto> ${@link IPage<DesignFormPageDto>} 结果
     * @author zxiaozhou
     * @date 2021-02-12 20:15:50
     */
    IPage<DesignFormPageDto> pageByModel(Page<DesignFormPageDto> page, @Param("query") DesignFormPageVo vo);


    /**
     * 条件查询多条
     *
     * @param vo ${@link DesignFormQueryVo} 查询条件
     * @return List<DesignFormDto> ${@link List< DesignFormDto >} 结果
     * @author zxiaozhou
     * @date 2021-02-12 20:15:50
     */
    List<DesignFormDto> selectListByModel(DesignFormQueryVo vo);


    /**
     * 通过表单id物理删除
     *
     * @param formId ${@link String} 表单id
     * @return int ${@link Integer} 成功状态:0-失败,1-成功
     * @author zxiaozhou
     * @date 2020-08-28 11:36
     */
    int physicalDeleteById(@Param("id") String formId);


    /**
     * 通过表单id物理批量删除
     *
     * @param idList ${@link Collection} 待删除id
     * @return int ${@link Integer} 成功状态:0-失败,大于1-成功
     * @author zxiaozhou
     * @date 2020-08-28 11:36
     */
    int physicalDeleteBatchIds(@Param("coll") Collection<String> idList);
}
