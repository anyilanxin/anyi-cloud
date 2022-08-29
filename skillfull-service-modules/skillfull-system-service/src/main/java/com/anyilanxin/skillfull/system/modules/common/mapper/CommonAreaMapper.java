// +----------------------------------------------------------------------
// | SkillFull快速开发平台 [ SkillFull ]
// +----------------------------------------------------------------------
// | 版权所有 2020~2021 zxiaozhou
// +----------------------------------------------------------------------
// | 官方网站: https://skillfull.divisu.com
// +----------------------------------------------------------------------
// | 作者: zxiaozhou <z7630853@163.com>
// +----------------------------------------------------------------------
package com.anyilanxin.skillfull.system.modules.common.mapper;

import com.anyilanxin.skillfull.database.datasource.base.mapper.BaseMapper;
import com.anyilanxin.skillfull.system.modules.common.controller.vo.CommonAreaPageVo;
import com.anyilanxin.skillfull.system.modules.common.entity.CommonAreaEntity;
import com.anyilanxin.skillfull.system.modules.common.service.dto.CommonAreaPageDto;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * 区域表(CommonArea)持久层
 *
 * @author zxiaozhou
 * @date 2020-11-02 09:25:03
 * @since JDK11
 */
@Repository
public interface CommonAreaMapper extends BaseMapper<CommonAreaEntity> {
    /**
     * 分页查询
     *
     * @param vo   ${@link CommonAreaPageVo} 查询条件
     * @param page ${@link Page< CommonAreaPageDto >} 分页信息
     * @return IPage<CommonAreaPageDto> ${@link IPage<CommonAreaPageDto>} 结果
     * @author zxiaozhou
     * @date 2020-11-02 09:25:03
     */
    IPage<CommonAreaPageDto> pageByModel(Page<CommonAreaPageDto> page, @Param("query") CommonAreaPageVo vo);


    /**
     * 通过区域id物理删除
     *
     * @param areaId ${@link String} 区域id
     * @return int ${@link Integer} 成功状态:0-失败,1-成功
     * @author zxiaozhou
     * @date 2020-08-28 11:36
     */
    int physicalDeleteById(@Param("id") String areaId);
}
