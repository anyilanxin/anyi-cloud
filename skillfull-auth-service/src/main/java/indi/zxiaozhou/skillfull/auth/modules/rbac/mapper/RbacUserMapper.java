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
import indi.zxiaozhou.skillfull.auth.modules.rbac.controller.vo.RbacUserPageVo;
import indi.zxiaozhou.skillfull.auth.modules.rbac.entity.RbacUserEntity;
import indi.zxiaozhou.skillfull.auth.modules.rbac.service.dto.RbacUserPageDto;
import indi.zxiaozhou.skillfull.coredatabase.base.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;

/**
 * 用户表(RbacUser)持久层
 *
 * @author zxiaozhou
 * @date 2021-01-19 15:37:38
 * @since JDK11
 */
@Repository
public interface RbacUserMapper extends BaseMapper<RbacUserEntity> {
    /**
     * 分页查询
     *
     * @param vo   ${@link RbacUserPageVo} 查询条件
     * @param page ${@link Page<RbacUserPageDto>} 分页信息
     * @return IPage<RbacUserPageDto> ${@link IPage<RbacUserPageDto>} 结果
     * @author zxiaozhou
     * @date 2021-01-19 15:37:38
     */
    IPage<RbacUserPageDto> pageByModel(Page<RbacUserPageDto> page, @Param("query") RbacUserPageVo vo);
    

    /**
     * 通过用户id物理删除
     *
     * @param userId ${@link String} 用户id
     * @return int ${@link Integer} 成功状态:0-失败,1-成功
     * @author zxiaozhou
     * @date 2020-08-28 11:36
     */
    int physicalDeleteById(@Param("id") String userId);


    /**
     * 通过用户id物理批量删除
     *
     * @param idList ${@link Collection} 待删除id
     * @return int ${@link Integer} 成功状态:0-失败,大于1-成功
     * @author zxiaozhou
     * @date 2020-08-28 11:36
     */
    int physicalDeleteBatchIds(@Param("coll") Collection<String> idList);
}