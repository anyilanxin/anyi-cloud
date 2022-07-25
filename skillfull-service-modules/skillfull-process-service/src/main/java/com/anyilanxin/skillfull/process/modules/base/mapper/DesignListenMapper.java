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
import com.anyilanxin.skillfull.process.modules.base.controller.vo.DesignListenPageVo;
import com.anyilanxin.skillfull.process.modules.base.controller.vo.DesignListenQueryVo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

/**
 * 流程监听(DesignListen)持久层
 *
 * @author zxiaozhou
 * @date 2021-02-12 20:18:02
 * @since JDK1.8
 */
@Repository
public interface DesignListenMapper extends BaseMapper<DesignListenEntity> {
    /**
     * 分页查询
     *
     * @param vo   ${@link DesignListenPageVo} 查询条件
     * @param page ${@link Page< DesignListenPageDto >} 分页信息
     * @return IPage<DesignListenPageDto> ${@link IPage<DesignListenPageDto>} 结果
     * @author zxiaozhou
     * @date 2021-02-12 20:18:02
     */
    IPage<DesignListenPageDto> pageByModel(Page<DesignListenPageDto> page, @Param("query") DesignListenPageVo vo);


    /**
     * 条件查询多条
     *
     * @param vo ${@link DesignListenQueryVo} 查询条件
     * @return List<DesignListenDto> ${@link List< DesignListenDto >} 结果
     * @author zxiaozhou
     * @date 2021-02-12 20:18:02
     */
    List<DesignListenDto> selectListByModel(DesignListenQueryVo vo);


    /**
     * 通过监听id物理删除
     *
     * @param listenId ${@link String} 监听id
     * @return int ${@link Integer} 成功状态:0-失败,1-成功
     * @author zxiaozhou
     * @date 2020-08-28 11:36
     */
    int physicalDeleteById(@Param("id") String listenId);


    /**
     * 通过监听id物理批量删除
     *
     * @param idList ${@link Collection} 待删除id
     * @return int ${@link Integer} 成功状态:0-失败,大于1-成功
     * @author zxiaozhou
     * @date 2020-08-28 11:36
     */
    int physicalDeleteBatchIds(@Param("coll") Collection<String> idList);
}
