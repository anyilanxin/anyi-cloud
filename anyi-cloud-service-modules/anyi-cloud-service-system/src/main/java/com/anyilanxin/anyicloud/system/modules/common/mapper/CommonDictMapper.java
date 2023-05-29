

package com.anyilanxin.anyicloud.system.modules.common.mapper;

import com.anyilanxin.anyicloud.database.datasource.base.mapper.BaseMapper;
import com.anyilanxin.anyicloud.system.modules.common.controller.vo.CommonDictPageVo;
import com.anyilanxin.anyicloud.system.modules.common.entity.CommonDictEntity;
import com.anyilanxin.anyicloud.system.modules.common.service.dto.CommonDictPageDto;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import java.util.Collection;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * 数据字典表(CommonDict)持久层
 *
 * @author zxh
 * @date 2020-11-02 09:25:17
 * @since 1.0.0
 */
@Repository
public interface CommonDictMapper extends BaseMapper<CommonDictEntity> {
    /**
     * 分页查询
     *
     * @param vo   ${@link CommonDictPageVo} 查询条件
     * @param page ${@link Page< CommonDictPageDto >} 分页信息
     * @return IPage<CommonDictPageDto> ${@link IPage<CommonDictPageDto>} 结果
     * @author zxh
     * @date 2020-11-02 09:25:17
     */
    IPage<CommonDictPageDto> pageByModel(Page<CommonDictPageDto> page, @Param("query") CommonDictPageVo vo);


    /**
     * 通过字典id物理删除
     *
     * @param dictId ${@link String} 字典id
     * @return int ${@link Integer} 成功状态:0-失败,1-成功
     * @author zxh
     * @date 2020-08-28 11:36
     */
    int physicalDeleteById(@Param("id") String dictId);


    /**
     * 通过字典id物理批量删除
     *
     * @param idList ${@link Collection} 待删除id
     * @return int ${@link Integer} 成功状态:0-失败,大于1-成功
     * @author zxh
     * @date 2020-08-28 11:36
     */
    int physicalDeleteBatchIds(@Param("coll") Collection<String> idList);
}
