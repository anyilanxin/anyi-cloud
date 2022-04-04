// +----------------------------------------------------------------------
// | SkillFull快速开发平台 [ SkillFull ]
// +----------------------------------------------------------------------
// | 版权所有 2020~2021 zxiaozhou
// +----------------------------------------------------------------------
// | 官方网站: https://www.divisu.com
// +----------------------------------------------------------------------
// | 作者: zxiaozhou <z7630853@163.com>
// +----------------------------------------------------------------------
package indi.zxiaozhou.skillfull.auth.modules.rbac.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import indi.zxiaozhou.skillfull.auth.modules.rbac.controller.vo.RbacPositionPageVo;
import indi.zxiaozhou.skillfull.auth.modules.rbac.entity.RbacPositionEntity;
import indi.zxiaozhou.skillfull.auth.modules.rbac.service.dto.RbacPositionPageDto;
import indi.zxiaozhou.skillfull.coredatabase.base.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;

/**
 * 职位表(RbacPosition)持久层
 *
 * @author zxiaozhou
 * @date 2021-01-19 18:17:48
 * @since JDK11
 */
@Repository
public interface RbacPositionMapper extends BaseMapper<RbacPositionEntity> {
    /**
     * 分页查询
     *
     * @param vo   ${@link RbacPositionPageVo} 查询条件
     * @param page ${@link Page<RbacPositionPageDto>} 分页信息
     * @return IPage<RbacPositionPageDto> ${@link IPage<RbacPositionPageDto>} 结果
     * @author zxiaozhou
     * @date 2021-01-19 18:17:48
     */
    IPage<RbacPositionPageDto> pageByModel(Page<RbacPositionPageDto> page, @Param("query") RbacPositionPageVo vo);
    

    /**
     * 通过职位id物理删除
     *
     * @param positionId ${@link String} 职位id
     * @return int ${@link Integer} 成功状态:0-失败,1-成功
     * @author zxiaozhou
     * @date 2020-08-28 11:36
     */
    int physicalDeleteById(@Param("id") String positionId);


    /**
     * 通过职位id物理批量删除
     *
     * @param idList ${@link Collection} 待删除id
     * @return int ${@link Integer} 成功状态:0-失败,大于1-成功
     * @author zxiaozhou
     * @date 2020-08-28 11:36
     */
    int physicalDeleteBatchIds(@Param("coll") Collection<String> idList);
}