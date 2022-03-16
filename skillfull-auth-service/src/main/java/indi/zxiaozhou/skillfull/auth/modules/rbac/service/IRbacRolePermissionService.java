// +----------------------------------------------------------------------
// | SkillFull快速开发平台 [ SkillFull ]
// +----------------------------------------------------------------------
// | 版权所有 2020~2021 zxiaozhou
// +----------------------------------------------------------------------
// | 官方网站: https://www.divisu.com
// +----------------------------------------------------------------------
// | 作者: zxiaozhou <z7630853@163.com>
// +----------------------------------------------------------------------
package indi.zxiaozhou.skillfull.auth.modules.rbac.service;

import indi.zxiaozhou.skillfull.auth.modules.rbac.controller.vo.RbacRolePermissionVo;
import indi.zxiaozhou.skillfull.auth.modules.rbac.entity.RbacRolePermissionEntity;
import indi.zxiaozhou.skillfull.coredatabase.base.service.BaseService;

/**
 * 角色-权限表(RbacRolePermission)业务层接口
 *
 * @author zxiaozhou
 * @date 2020-10-08 13:29:16
 * @since JDK11
 */
public interface IRbacRolePermissionService extends BaseService<RbacRolePermissionEntity> {
    /**
     * 保存
     *
     * @param vo ${@link RbacRolePermissionVo} 角色-权限表保存
     * @throws RuntimeException ${@link RuntimeException}
     * @author zxiaozhou
     * @date 2020-10-08 13:29:16
     */
    void save(RbacRolePermissionVo vo) throws RuntimeException;


    /**
     * 通过角色权限
     *
     * @param vo ${@link RbacRolePermissionVo} 角色-权限表更新
     * @param vo ${@link RbacRolePermissionVo} 角色-权限表更新
     * @throws RuntimeException ${@link RuntimeException}
     * @author zxiaozhou
     * @date 2020-10-08 13:29:16
     */
    void updatePermission(RbacRolePermissionVo vo) throws RuntimeException;


    /**
     * 删除角色权限关联
     *
     * @param roleId ${@link String} 角色id
     * @throws RuntimeException ${@link RuntimeException}
     * @author zxiaozhou
     * @date 2021-03-09 09:47
     */
    void deleteRolePermission(String roleId) throws RuntimeException;

}