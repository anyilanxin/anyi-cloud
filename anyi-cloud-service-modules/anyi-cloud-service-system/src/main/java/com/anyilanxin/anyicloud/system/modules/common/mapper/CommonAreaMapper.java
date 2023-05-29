

package com.anyilanxin.anyicloud.system.modules.common.mapper;

import com.anyilanxin.anyicloud.database.datasource.base.mapper.BaseMapper;
import com.anyilanxin.anyicloud.system.modules.common.controller.vo.CommonAreaPageVo;
import com.anyilanxin.anyicloud.system.modules.common.entity.CommonAreaEntity;
import com.anyilanxin.anyicloud.system.modules.common.service.dto.CommonAreaPageDto;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * 区域表(CommonArea)持久层
 *
 * @author zxh
 * @date 2020-11-02 09:25:03
 * @since 1.0.0
 */
@Repository
public interface CommonAreaMapper extends BaseMapper<CommonAreaEntity> {
    /**
     * 分页查询
     *
     * @param vo   ${@link CommonAreaPageVo} 查询条件
     * @param page ${@link Page< CommonAreaPageDto >} 分页信息
     * @return IPage<CommonAreaPageDto> ${@link IPage<CommonAreaPageDto>} 结果
     * @author zxh
     * @date 2020-11-02 09:25:03
     */
    IPage<CommonAreaPageDto> pageByModel(Page<CommonAreaPageDto> page, @Param("query") CommonAreaPageVo vo);


    /**
     * 通过区域id物理删除
     *
     * @param areaId ${@link String} 区域id
     * @return int ${@link Integer} 成功状态:0-失败,1-成功
     * @author zxh
     * @date 2020-08-28 11:36
     */
    int physicalDeleteById(@Param("id") String areaId);
}
