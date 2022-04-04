// +----------------------------------------------------------------------
// | SkillFull快速开发平台 [ SkillFull ]
// +----------------------------------------------------------------------
// | 版权所有 2020~2021 zxiaozhou
// +----------------------------------------------------------------------
// | 官方网站: https://www.divisu.com
// +----------------------------------------------------------------------
// | 作者: zxiaozhou <z7630853@163.com>
// +----------------------------------------------------------------------
package indi.zxiaozhou.skillfull.auth.modules.common.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import indi.zxiaozhou.skillfull.auth.modules.common.controller.vo.CommonDataMapPageVo;
import indi.zxiaozhou.skillfull.auth.modules.common.controller.vo.CommonDataMapQueryVo;
import indi.zxiaozhou.skillfull.auth.modules.common.entity.CommonDataMapEntity;
import indi.zxiaozhou.skillfull.auth.modules.common.service.dto.CommonDataMapDto;
import indi.zxiaozhou.skillfull.auth.modules.common.service.dto.CommonDataMapPageDto;
import indi.zxiaozhou.skillfull.coredatabase.base.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

/**
 * 数据映射表(CommonDataMap)持久层
 *
 * @author zxiaozhou
 * @date 2021-07-28 11:53:28
 * @since JDK1.8
 */
@Repository
public interface CommonDataMapMapper extends BaseMapper<CommonDataMapEntity> {
    /**
     * 分页查询
     *
     * @param vo   ${@link CommonDataMapPageVo} 查询条件
     * @param page ${@link Page<CommonDataMapPageDto>} 分页信息
     * @return IPage<CommonDataMapPageDto> ${@link IPage<CommonDataMapPageDto>} 结果
     * @author zxiaozhou
     * @date 2021-07-28 11:53:28
     */
    IPage<CommonDataMapPageDto> pageByModel(Page<CommonDataMapPageDto> page, @Param("query") CommonDataMapPageVo vo);


    /**
     * 条件查询多条
     *
     * @param vo ${@link CommonDataMapQueryVo} 查询条件
     * @return List<CommonDataMapDto> ${@link List<CommonDataMapDto>} 结果
     * @author zxiaozhou
     * @date 2021-07-28 11:53:28
     */
    List<CommonDataMapDto> selectListByModel(CommonDataMapQueryVo vo);


    /**
     * 通过数据映射id物理删除
     *
     * @param dataMapId ${@link String} 数据映射id
     * @return int ${@link Integer} 成功状态:0-失败,1-成功
     * @author zxiaozhou
     * @date 2021-07-28 11:53:28
     */
    int physicalDeleteById(@Param("id") String dataMapId);


    /**
     * 通过数据映射id物理批量删除
     *
     * @param idList ${@link Collection} 待删除id
     * @return int ${@link Integer} 成功状态:0-失败,大于1-成功
     * @author zxiaozhou
     * @date 2021-07-28 11:53:28
     */
    int physicalDeleteBatchIds(@Param("coll") Collection<String> idList);
}
