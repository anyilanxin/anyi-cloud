

package com.anyilanxin.anyicloud.system.modules.common.mapper;

import com.anyilanxin.anyicloud.database.datasource.base.mapper.BaseMapper;
import com.anyilanxin.anyicloud.system.modules.common.controller.vo.CommonDictItemPageVo;
import com.anyilanxin.anyicloud.system.modules.common.entity.CommonDictItemEntity;
import com.anyilanxin.anyicloud.system.modules.common.service.dto.CommonDictItemDto;
import com.anyilanxin.anyicloud.system.modules.common.service.dto.CommonDictItemPageDto;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * 数据字典配置项表(CommonDictItem)持久层
 *
 * @author zxh
 * @date 2020-11-02 09:25:25
 * @since 1.0.0
 */
@Repository
public interface CommonDictItemMapper extends BaseMapper<CommonDictItemEntity> {
    /**
     * 分页查询
     *
     * @param vo   ${@link CommonDictItemPageVo} 查询条件
     * @param page ${@link Page< CommonDictItemPageDto >} 分页信息
     * @return IPage<CommonDictItemPageDto> ${@link IPage<CommonDictItemPageDto>} 结果
     * @author zxh
     * @date 2020-11-02 09:25:25
     */
    IPage<CommonDictItemPageDto> pageByModel(Page<CommonDictItemPageDto> page, @Param("query") CommonDictItemPageVo vo);


    /**
     * 条件查询多条
     *
     * @param dictCode ${@link String} 字典编码
     * @return List<CommonDictItemDto> ${@link List< CommonDictItemDto >} 结果
     * @author zxh
     * @date 2020-11-02 09:25:25
     */
    List<CommonDictItemDto> selectListByCode(@Param("dictCode") String dictCode);


    /**
     * 通过字典项id物理删除
     *
     * @param itemId ${@link String} 字典项id
     * @return int ${@link Integer} 成功状态:0-失败,1-成功
     * @author zxh
     * @date 2020-08-28 11:36
     */
    int physicalDeleteById(@Param("id") String itemId);
}
