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
import indi.zxiaozhou.skillfull.auth.modules.rbac.controller.vo.RbacUserGroupPageVo;
import indi.zxiaozhou.skillfull.auth.modules.rbac.controller.vo.RbacUserGroupQueryVo;
import indi.zxiaozhou.skillfull.auth.modules.rbac.entity.RbacUserGroupEntity;
import indi.zxiaozhou.skillfull.auth.modules.rbac.service.dto.RbacUserGroupDto;
import indi.zxiaozhou.skillfull.auth.modules.rbac.service.dto.RbacUserGroupPageDto;
import indi.zxiaozhou.skillfull.coredatabase.base.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

/**
 * 用户组(RbacUserGroup)持久层
 *
 * @author zxiaozhou
 * @date 2021-05-17 23:13:08
 * @since JDK1.8
 */
@Repository
public interface RbacUserGroupMapper extends BaseMapper<RbacUserGroupEntity> {
    /**
     * 分页查询
     *
     * @param vo   ${@link RbacUserGroupPageVo} 查询条件
     * @param page ${@link Page<RbacUserGroupPageDto>} 分页信息
     * @return IPage<RbacUserGroupPageDto> ${@link IPage<RbacUserGroupPageDto>} 结果
     * @author zxiaozhou
     * @date 2021-05-17 23:13:08
     */
    IPage<RbacUserGroupPageDto> pageByModel(Page<RbacUserGroupPageDto> page, @Param("query") RbacUserGroupPageVo vo);


    /**
     * 条件查询多条
     *
     * @param vo ${@link RbacUserGroupQueryVo} 查询条件
     * @return List<RbacUserGroupDto> ${@link List<RbacUserGroupDto>} 结果
     * @author zxiaozhou
     * @date 2021-05-17 23:13:08
     */
    List<RbacUserGroupDto> selectListByModel(RbacUserGroupQueryVo vo);


    /**
     * 通过用户组id物理删除
     *
     * @param userGroupId ${@link String} 用户组id
     * @return int ${@link Integer} 成功状态:0-失败,1-成功
     * @author zxiaozhou
     * @date 2021-05-17 23:13:08
     */
    int physicalDeleteById(@Param("id") String userGroupId);


    /**
     * 通过用户组id物理批量删除
     *
     * @param idList ${@link Collection} 待删除id
     * @return int ${@link Integer} 成功状态:0-失败,大于1-成功
     * @author zxiaozhou
     * @date 2021-05-17 23:13:08
     */
    int physicalDeleteBatchIds(@Param("coll") Collection<String> idList);
}