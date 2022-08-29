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
import com.anyilanxin.skillfull.process.modules.base.controller.vo.DesignModelHistoryPageVo;
import com.anyilanxin.skillfull.process.modules.base.entity.DesignModelHistoryEntity;
import com.anyilanxin.skillfull.process.modules.base.service.dto.DesignModelHistoryPageDto;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;

/**
 * 流程模型历史(DesignModelHistory)持久层
 *
 * @author zxiaozhou
 * @date 2021-11-25 09:52:36
 * @since JDK1.8
 */
@Repository
public interface DesignModelHistoryMapper extends BaseMapper<DesignModelHistoryEntity> {
    /**
     * 分页查询
     *
     * @param vo   ${@link DesignModelHistoryPageVo} 查询条件
     * @param page ${@link Page< DesignModelHistoryPageDto >} 分页信息
     * @return IPage<DesignModelHistoryPageDto> ${@link IPage<DesignModelHistoryPageDto>} 结果
     * @author zxiaozhou
     * @date 2021-11-25 09:52:36
     */
    IPage<DesignModelHistoryPageDto> pageByModel(Page<DesignModelHistoryPageDto> page, @Param("query") DesignModelHistoryPageVo vo);


    /**
     * 通过历史模型id物理删除
     *
     * @param historyModelId ${@link String} 历史模型id
     * @return int ${@link Integer} 成功状态:0-失败,1-成功
     * @author zxiaozhou
     * @date 2021-11-25 09:52:36
     */
    int physicalDeleteById(@Param("id") String historyModelId);


    /**
     * 通过历史模型id物理批量删除
     *
     * @param idList ${@link Collection} 待删除id
     * @return int ${@link Integer} 成功状态:0-失败,大于1-成功
     * @author zxiaozhou
     * @date 2021-11-25 09:52:36
     */
    int physicalDeleteBatchIds(@Param("coll") Collection<String> idList);
}
