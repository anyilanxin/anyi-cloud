// +----------------------------------------------------------------------
// | SkillFull快速开发平台 [ SkillFull ]
// +----------------------------------------------------------------------
// | 版权所有 2020~2021 zxiaozhou
// +----------------------------------------------------------------------
// | 官方网站: https://www.divisu.com
// +----------------------------------------------------------------------
// | 作者: zxiaozhou <z7630853@163.com>
// +----------------------------------------------------------------------
package indi.zxiaozhou.skillfull.system.modules.common.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import indi.zxiaozhou.skillfull.coredatabase.base.mapper.BaseMapper;
import indi.zxiaozhou.skillfull.system.modules.common.controller.vo.CommonDictPageVo;
import indi.zxiaozhou.skillfull.system.modules.common.entity.CommonDictEntity;
import indi.zxiaozhou.skillfull.system.modules.common.service.dto.CommonDictPageDto;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;

/**
 * 数据字典表(CommonDict)持久层
 *
 * @author zxiaozhou
 * @date 2020-11-02 09:25:17
 * @since JDK11
 */
@Repository
public interface CommonDictMapper extends BaseMapper<CommonDictEntity> {
    /**
     * 分页查询
     *
     * @param vo   ${@link CommonDictPageVo} 查询条件
     * @param page ${@link Page<CommonDictPageDto>} 分页信息
     * @return IPage<CommonDictPageDto> ${@link IPage<CommonDictPageDto>} 结果
     * @author zxiaozhou
     * @date 2020-11-02 09:25:17
     */
    IPage<CommonDictPageDto> pageByModel(Page<CommonDictPageDto> page, @Param("query") CommonDictPageVo vo);


    /**
     * 通过字典id物理删除
     *
     * @param dictId ${@link String} 字典id
     * @return int ${@link Integer} 成功状态:0-失败,1-成功
     * @author zxiaozhou
     * @date 2020-08-28 11:36
     */
    int physicalDeleteById(@Param("id") String dictId);


    /**
     * 通过字典id物理批量删除
     *
     * @param idList ${@link Collection} 待删除id
     * @return int ${@link Integer} 成功状态:0-失败,大于1-成功
     * @author zxiaozhou
     * @date 2020-08-28 11:36
     */
    int physicalDeleteBatchIds(@Param("coll") Collection<String> idList);
}