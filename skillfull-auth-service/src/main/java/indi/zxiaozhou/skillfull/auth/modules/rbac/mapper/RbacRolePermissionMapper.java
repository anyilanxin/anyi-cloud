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

import indi.zxiaozhou.skillfull.auth.modules.rbac.entity.RbacRolePermissionEntity;
import indi.zxiaozhou.skillfull.auth.modules.rbac.service.dto.RbacRolePermissionDto;
import indi.zxiaozhou.skillfull.auth.modules.rbac.service.dto.RbacUserRouterDto;
import indi.zxiaozhou.skillfull.coredatabase.base.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

/**
 * 角色-权限表(RbacRolePermission)持久层
 *
 * @author zxiaozhou
 * @date 2020-10-08 13:29:15
 * @since JDK11
 */
@Repository
public interface RbacRolePermissionMapper extends BaseMapper<RbacRolePermissionEntity> {
    
    /**
     * 获取有效的菜单按钮权限
     *
     * @param type ${@link Integer} 1-查询菜单按钮,其他查询所有
     * @return List<RbacRolePermissionDto> ${@link List<RbacRolePermissionDto>} 结果
     * @author zxiaozhou
     * @date 2020-10-08 13:29:15
     */
    List<RbacRolePermissionDto> selectMenuAntButton(String roleIds, Integer type);


    /**
     * 获取用户路由信息
     *
     * @param userId    ${@link String} 用户id
     * @param systemIds ${@link Set<String>} 查询系统id
     * @param type      ${@link Integer} 类型,不传查询所有
     * @return List<RbacUserRouterDto> ${@link List<RbacUserRouterDto>} 结果
     * @author zxiaozhou
     * @date 2020-10-08 13:29:15
     */
    List<RbacUserRouterDto> getUserRouters(String userId, Set<String> systemIds, Integer type);


    /**
     * 通过权限角色id物理删除
     *
     * @param roleId ${@link String} 角色id
     * @return int ${@link Integer} 成功状态:0-失败,1-成功
     * @author zxiaozhou
     * @date 2020-08-28 11:36
     */
    int physicalDeleteById(@Param("id") String roleId);
}