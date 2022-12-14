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
import com.anyilanxin.skillfull.process.modules.base.controller.vo.DesignModelPageVo;
import com.anyilanxin.skillfull.process.modules.base.entity.DesignModelEntity;
import com.anyilanxin.skillfull.process.modules.base.service.dto.DesignModelDeploymentStatiDto;
import com.anyilanxin.skillfull.process.modules.base.service.dto.DesignModelPageDto;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;

/**
 * 流程模型管理(DesignModel)持久层
 *
 * @author zxiaozhou
 * @date 2021-11-25 05:22:56
 * @since JDK1.8
 */
@Repository
public interface DesignModelMapper extends BaseMapper<DesignModelEntity> {
    /**
     * 分页查询
     *
     * @param vo   ${@link DesignModelPageVo} 查询条件
     * @param page ${@link Page< DesignModelPageDto >} 分页信息
     * @return IPage<DesignModelPageDto> ${@link IPage<DesignModelPageDto>} 结果
     * @author zxiaozhou
     * @date 2021-11-25 05:22:56
     */
    IPage<DesignModelPageDto> pageByModel(Page<DesignModelPageDto> page, @Param("query") DesignModelPageVo vo);


    /**
     * 模型状态统计
     *
     * @return DesignModelDeploymentStatiDto ${@link DesignModelDeploymentStatiDto}
     * @author zxiaozhou
     * @date 2021-03-02 18:01
     */
    DesignModelDeploymentStatiDto statistics();


    /**
     * 通过模型id物理删除
     *
     * @param modelId ${@link String} 模型id
     * @return int ${@link Integer} 成功状态:0-失败,1-成功
     * @author zxiaozhou
     * @date 2021-11-25 05:22:56
     */
    int physicalDeleteById(@Param("id") String modelId);

    /**
     * 获取某个模型数量，排除指定模型id的数量
     *
     * @param modelId              需要排除的模型id
     * @param processDefinitionKey 流程定义key
     * @author zxiaozhou
     * @date 2022-06-05 14:49
     */
    int getModelNum(@Param("id") String modelId, @Param("key") String processDefinitionKey);


    /**
     * 通过模型id物理批量删除
     *
     * @param idList ${@link Collection} 待删除id
     * @return int ${@link Integer} 成功状态:0-失败,大于1-成功
     * @author zxiaozhou
     * @date 2021-11-25 05:22:56
     */
    int physicalDeleteBatchIds(@Param("coll") Collection<String> idList);
}
