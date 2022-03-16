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

import indi.zxiaozhou.skillfull.auth.modules.rbac.entity.RbacCorrelateUserEntity;
import indi.zxiaozhou.skillfull.auth.modules.rbac.service.dto.RbacCorrelateUserOrgDto;
import indi.zxiaozhou.skillfull.auth.modules.rbac.service.dto.RbacCorrelateUserPositionDto;
import indi.zxiaozhou.skillfull.corecommon.base.model.auth.RoleModel;
import indi.zxiaozhou.skillfull.coredatabase.base.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 * 用户关联关系表(RbacCorrelateUser)持久层
 *
 * @author zxiaozhou
 * @date 2021-01-19 15:23:09
 * @since JDK11
 */
@Repository
public interface RbacCorrelateUserMapper extends BaseMapper<RbacCorrelateUserEntity> {
    /**
     * 获取用户角色信息
     *
     * @param userId ${@link String} 用户id
     * @return Set<RoleModel> ${@link Set<RoleModel>} 角色信息
     * @author zxiaozhou
     * @date 2021-07-26 14:57
     */
    Set<RoleModel> getUserRoleById(String userId);

    /**
     * 通过用户关联关系id物理删除
     *
     * @param correlateUserId ${@link String} 用户关联关系id
     * @return int ${@link Integer} 成功状态:0-失败,1-成功
     * @author zxiaozhou
     * @date 2020-08-28 11:36
     */
    int physicalDeleteById(@Param("id") String correlateUserId);


    /**
     * 通过用户关联关系id物理批量删除
     *
     * @param idList ${@link Collection} 待删除id
     * @return int ${@link Integer} 成功状态:0-失败,大于1-成功
     * @author zxiaozhou
     * @date 2020-08-28 11:36
     */
    int physicalDeleteBatchIds(@Param("coll") Collection<String> idList);

    /**
     * 获取关联机构信息
     *
     * @param userId ${@link String} 用户id
     * @return RbacCorrelateUserOrgDto ${@link RbacCorrelateUserOrgDto}
     * @author zxiaozhou
     * @date 2021-01-19 17:38
     */
    RbacCorrelateUserOrgDto getCorrelateOrg(String userId);


    /**
     * 获取关联职位信息
     *
     * @param userId ${@link String} 用户id
     * @return List<RbacCorrelateUserPositionDto> ${@link List<RbacCorrelateUserPositionDto>}
     * @author zxiaozhou
     * @date 2021-01-19 17:38
     */
    List<RbacCorrelateUserPositionDto> getCorrelatePosition(String userId);
}