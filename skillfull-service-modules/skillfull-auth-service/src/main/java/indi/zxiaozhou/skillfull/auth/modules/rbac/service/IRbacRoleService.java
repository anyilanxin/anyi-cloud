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

import indi.zxiaozhou.skillfull.auth.modules.rbac.controller.vo.RbacRolePageVo;
import indi.zxiaozhou.skillfull.auth.modules.rbac.controller.vo.RbacRoleVo;
import indi.zxiaozhou.skillfull.auth.modules.rbac.entity.RbacRoleEntity;
import indi.zxiaozhou.skillfull.auth.modules.rbac.service.dto.*;
import indi.zxiaozhou.skillfull.coredatabase.base.service.BaseService;
import indi.zxiaozhou.skillfull.coredatabase.base.service.dto.PageDto;

import java.util.List;
import java.util.Set;

/**
 * 角色表(RbacRole)业务层接口
 *
 * @author zxiaozhou
 * @date 2020-10-08 13:44:03
 * @since JDK11
 */
public interface IRbacRoleService extends BaseService<RbacRoleEntity> {
    /**
     * 保存
     *
     * @param vo ${@link RbacRoleVo} 角色表保存
     * @throws RuntimeException ${@link RuntimeException}
     * @author zxiaozhou
     * @date 2020-10-08 13:44:03
     */
    void save(RbacRoleVo vo) throws RuntimeException;


    /**
     * 通过id更新
     *
     * @param vo     ${@link RbacRoleVo} 角色表更新
     * @param roleId ${@link String} 角色id
     * @param vo     ${@link RbacRoleVo} 角色表更新
     * @throws RuntimeException ${@link RuntimeException}
     * @author zxiaozhou
     * @date 2020-10-08 13:44:03
     */
    void updateById(String roleId, RbacRoleVo vo) throws RuntimeException;


    /**
     * 分页查询
     *
     * @param vo ${@link RbacRolePageVo} 角色表分页查询Vo
     * @return PageDto<RbacRolePageDto> ${@link PageDto<RbacRolePageDto>} 分页查询结果
     * @throws RuntimeException ${@link RuntimeException}
     * @author zxiaozhou
     * @date 2020-10-08 13:44:03
     */
    PageDto<RbacRolePageDto> pageByModel(RbacRolePageVo vo) throws RuntimeException;

    /**
     * 获取某个角色菜单按钮权限信息
     *
     * @param role   ${@link String} 角色id
     * @param roleId ${@link RbacRoleEntity} 角色信息
     * @return RbacRolePermissionInfoDto> ${@link RbacRolePermissionInfoDto>}
     * @author zxiaozhou
     * @date 2022-01-28 09:50
     */
    Set<RbacRolePermissionInfoDto> getMenuActions(RbacRoleEntity role, String roleId) throws RuntimeException;

    /**
     * 通过id查询详情
     *
     * @param roleId ${@link String} 角色id
     * @return ProcessRoleModel ${@link RbacRoleDto} 查询结果
     * @throws RuntimeException ${@link RuntimeException}
     * @author zxiaozhou
     * @date 2020-10-08 13:44:03
     */
    RbacRoleDto getById(String roleId) throws RuntimeException;


    /**
     * 通过roleId删除
     *
     * @param roleId ${@link String} 角色id
     * @throws RuntimeException ${@link RuntimeException}
     * @author zxiaozhou
     * @date 2020-08-28 12:20
     */
    void deleteById(String roleId) throws RuntimeException;


    /**
     * 角色启用或禁用
     *
     * @param roleId ${@link String} 角色id
     * @param status ${@link Integer}状态:0-禁用、1-启用
     * @throws RuntimeException ${@link RuntimeException}
     * @author zxiaozhou
     * @date 2020-10-09 07:32
     */
    void updateStatus(String roleId, Integer status) throws RuntimeException;


    /**
     * 获取有效的角色
     *
     * @return List<RbacRoleEffectiveDto> ${@link List<RbacRoleEffectiveDto>}
     * @throws RuntimeException ${@link RuntimeException}
     * @author zxiaozhou
     * @date 2021-03-09 15:45
     */
    List<RbacRoleEffectiveDto> getEffectiveRoles() throws RuntimeException;


    /**
     * 通过角色编码查询信息
     *
     * @param roleCodes ${@link List<String>} 角色编码
     * @return List<RbacRoleBasicDto> ${@link List<RbacRoleBasicDto>} 角色信息
     * @author zxiaozhou
     * @date 2021-05-23 00:09
     */
    List<RbacRoleBasicDto> getListByCodes(List<String> roleCodes);


    /**
     * 通过角色id查询信息
     *
     * @param roleIds ${@link List<String>} 角色roleIds不能为空
     * @return List<RbacRoleBasicDto> ${@link List<RbacRoleBasicDto>} 角色信息
     * @author zxiaozhou
     * @date 2021-05-23 00:09
     */
    List<RbacRoleBasicDto> getRoleListByIds(List<String> roleIds);
}