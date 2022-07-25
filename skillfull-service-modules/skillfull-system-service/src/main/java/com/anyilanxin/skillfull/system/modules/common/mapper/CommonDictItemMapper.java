// +----------------------------------------------------------------------
// | SkillFull快速开发平台 [ SkillFull ]
// +----------------------------------------------------------------------
// | 版权所有 2020~2021 zxiaozhou
// +----------------------------------------------------------------------
// | 官方网站: https://www.divisu.com
// +----------------------------------------------------------------------
// | 作者: zxiaozhou <z7630853@163.com>
// +----------------------------------------------------------------------
package com.anyilanxin.skillfull.system.modules.common.mapper;

import com.anyilanxin.skillfull.database.datasource.base.mapper.BaseMapper;
import com.anyilanxin.skillfull.system.modules.common.controller.vo.CommonDictItemPageVo;
import com.anyilanxin.skillfull.system.modules.common.entity.CommonDictItemEntity;
import com.anyilanxin.skillfull.system.modules.common.service.dto.CommonDictItemDto;
import com.anyilanxin.skillfull.system.modules.common.service.dto.CommonDictItemPageDto;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 数据字典配置项表(CommonDictItem)持久层
 *
 * @author zxiaozhou
 * @date 2020-11-02 09:25:25
 * @since JDK11
 */
@Repository
public interface CommonDictItemMapper extends BaseMapper<CommonDictItemEntity> {
    /**
     * 分页查询
     *
     * @param vo   ${@link CommonDictItemPageVo} 查询条件
     * @param page ${@link Page< CommonDictItemPageDto >} 分页信息
     * @return IPage<CommonDictItemPageDto> ${@link IPage<CommonDictItemPageDto>} 结果
     * @author zxiaozhou
     * @date 2020-11-02 09:25:25
     */
    IPage<CommonDictItemPageDto> pageByModel(Page<CommonDictItemPageDto> page, @Param("query") CommonDictItemPageVo vo);


    /**
     * 条件查询多条
     *
     * @param dictCode ${@link String} 字典编码
     * @return List<CommonDictItemDto> ${@link List< CommonDictItemDto >} 结果
     * @author zxiaozhou
     * @date 2020-11-02 09:25:25
     */
    List<CommonDictItemDto> selectListByCode(@Param("dictCode") String dictCode);


    /**
     * 通过字典项id物理删除
     *
     * @param itemId ${@link String} 字典项id
     * @return int ${@link Integer} 成功状态:0-失败,1-成功
     * @author zxiaozhou
     * @date 2020-08-28 11:36
     */
    int physicalDeleteById(@Param("id") String itemId);
}