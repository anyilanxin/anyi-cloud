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
import indi.zxiaozhou.skillfull.auth.modules.common.controller.vo.CommonUserIdentityPageVo;
import indi.zxiaozhou.skillfull.auth.modules.common.controller.vo.CommonUserIdentityQueryVo;
import indi.zxiaozhou.skillfull.auth.modules.common.entity.CommonUserIdentityEntity;
import indi.zxiaozhou.skillfull.auth.modules.common.service.dto.CommonUserIdentityDto;
import indi.zxiaozhou.skillfull.auth.modules.common.service.dto.CommonUserIdentityPageDto;
import indi.zxiaozhou.skillfull.coredatabase.base.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

/**
 * 实名信息表(CommonUserIdentity)持久层
 *
 * @author zxiaozhou
 * @date 2021-02-12 19:41:13
 * @since JDK1.8
 */
@Repository
public interface CommonUserIdentityMapper extends BaseMapper<CommonUserIdentityEntity> {
    /**
     * 分页查询
     *
     * @param vo   ${@link CommonUserIdentityPageVo} 查询条件
     * @param page ${@link Page<CommonUserIdentityPageDto>} 分页信息
     * @return IPage<CommonUserIdentityPageDto> ${@link IPage<CommonUserIdentityPageDto>} 结果
     * @author zxiaozhou
     * @date 2021-02-12 19:41:13
     */
    IPage<CommonUserIdentityPageDto> pageByModel(Page<CommonUserIdentityPageDto> page, @Param("query") CommonUserIdentityPageVo vo);


    /**
     * 条件查询多条
     *
     * @param vo ${@link CommonUserIdentityQueryVo} 查询条件
     * @return List<CommonUserIdentityDto> ${@link List<CommonUserIdentityDto>} 结果
     * @author zxiaozhou
     * @date 2021-02-12 19:41:13
     */
    List<CommonUserIdentityDto> selectListByModel(CommonUserIdentityQueryVo vo);


    /**
     * 通过实名信息id物理删除
     *
     * @param identityId ${@link String} 实名信息id
     * @return int ${@link Integer} 成功状态:0-失败,1-成功
     * @author zxiaozhou
     * @date 2020-08-28 11:36
     */
    int physicalDeleteById(@Param("id") String identityId);


    /**
     * 通过实名信息id物理批量删除
     *
     * @param idList ${@link Collection} 待删除id
     * @return int ${@link Integer} 成功状态:0-失败,大于1-成功
     * @author zxiaozhou
     * @date 2020-08-28 11:36
     */
    int physicalDeleteBatchIds(@Param("coll") Collection<String> idList);
}