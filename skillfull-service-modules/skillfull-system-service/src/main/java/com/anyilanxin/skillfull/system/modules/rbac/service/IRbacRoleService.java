// +----------------------------------------------------------------------
// | SkillFull快速开发平台 [ SkillFull ]
// +----------------------------------------------------------------------
// | 版权所有 2020~2021 zxiaozhou
// +----------------------------------------------------------------------
// | 官方网站: https://www.divisu.com
// +----------------------------------------------------------------------
// | 作者: zxiaozhou <z7630853@163.com>
// +----------------------------------------------------------------------
package com.anyilanxin.skillfull.system.modules.rbac.service;

import com.anyilanxin.skillfull.database.datasource.base.service.BaseService;
import com.anyilanxin.skillfull.database.datasource.base.service.dto.PageDto;
import com.anyilanxin.skillfull.system.modules.rbac.controller.vo.RbacRoleAuthVo;
import com.anyilanxin.skillfull.system.modules.rbac.controller.vo.RbacRolePageVo;
import com.anyilanxin.skillfull.system.modules.rbac.controller.vo.RbacRoleQueryVo;
import com.anyilanxin.skillfull.system.modules.rbac.controller.vo.RbacRoleVo;
import com.anyilanxin.skillfull.system.modules.rbac.entity.RbacRoleEntity;
import com.anyilanxin.skillfull.system.modules.rbac.service.dto.RbacRoleBasicDto;
import com.anyilanxin.skillfull.system.modules.rbac.service.dto.RbacRoleDto;
import com.anyilanxin.skillfull.system.modules.rbac.service.dto.RbacRoleMenuButtonDto;
import com.anyilanxin.skillfull.system.modules.rbac.service.dto.RbacRolePageDto;

import java.util.List;
import java.util.Set;

/**
 * 角色表(RbacRole)业务层接口
 *
 * @author zxiaozhou
 * @copyright zxiaozhou（https://divisu.com）
 * @date 2022-05-02 16:12:20
 * @since JDK1.8
 */
public interface IRbacRoleService extends BaseService<RbacRoleEntity> {
    /**
     * 保存
     *
     * @param vo 角色表保存数据
     * @throws RuntimeException
     * @author zxiaozhou
     * @date 2022-05-02 16:12:20
     */
    void save(RbacRoleVo vo) throws RuntimeException;


    /**
     * 通过id更新
     *
     * @param roleId 角色id
     * @param vo     角色表更新数据
     * @throws RuntimeException
     * @author zxiaozhou
     * @date 2022-05-02 16:12:20
     */
    void updateById(String roleId, RbacRoleVo vo) throws RuntimeException;


    /**
     * 分页查询
     *
     * @param vo 分页查询条件
     * @return PageDto<RbacRolePageDto> 分页查询结果
     * @throws RuntimeException
     * @author zxiaozhou
     * @date 2022-05-02 16:12:20
     */
    PageDto<RbacRolePageDto> pageByModel(RbacRolePageVo vo) throws RuntimeException;

    /**
     * 条件查询多条
     *
     * @param vo 角色表查询条件
     * @return List<RbacRoleDto> 查询结果
     * @throws RuntimeException
     * @author zxiaozhou
     * @date 2022-05-02 16:12:20
     */
    List<RbacRoleDto> selectListByModel(RbacRoleQueryVo vo) throws RuntimeException;


    /**
     * 通过id查询详情
     *
     * @param roleId 角色id
     * @return RbacRoleDto 查询结果
     * @throws RuntimeException
     * @author zxiaozhou
     * @date 2022-05-02 16:12:20
     */
    RbacRoleDto getById(String roleId) throws RuntimeException;


    /**
     * 通过roleId删除
     *
     * @param roleId 角色id
     * @throws RuntimeException
     * @author zxiaozhou
     * @date 2022-05-02 16:12:20
     */
    void deleteById(String roleId) throws RuntimeException;


    /**
     * 角色表批量删除
     *
     * @param roleIds 角色id列表
     * @throws RuntimeException
     * @author zxiaozhou
     * @date 2022-05-02 16:12:20
     */
    void deleteBatch(List<String> roleIds) throws RuntimeException;


    /**
     * 更新或添加角色权限
     *
     * @param roleId 角色id
     * @param vo     待插入数据
     * @author zxiaozhou
     * @date 2022-05-03 18:43
     */
    void updateAuth(String roleId, RbacRoleAuthVo vo);


    /**
     * 角色启用或禁用
     *
     * @param roleId 角色id
     * @param status 状态:0-禁用、1-启用
     * @throws RuntimeException
     * @author zxiaozhou
     * @date 2020-10-09 07:32
     */
    void updateStatus(String roleId, Integer status) throws RuntimeException;


    /**
     * 获取有效的角色
     *
     * @return List<RbacRoleBasicDto>
     * @throws RuntimeException
     * @author zxiaozhou
     * @date 2021-03-09 15:45
     */
    List<RbacRoleBasicDto> getEffectiveRoles() throws RuntimeException;


    /**
     * 通过角色编码查询信息
     *
     * @param roleCodes 角色编码
     * @return List<RbacRoleBasicDto> 角色信息
     * @author zxiaozhou
     * @date 2021-05-23 00:09
     */
    List<RbacRoleBasicDto> getListByCodes(List<String> roleCodes);


    /**
     * 获取某个角色菜单按钮权限信息
     *
     * @param roleId 角色信息
     * @return Set<RbacRoleNoRoleButtonActionDto>
     * @author zxiaozhou
     * @date 2022-01-28 09:50
     */
    Set<RbacRoleMenuButtonDto> getMenuActions(String roleId) throws RuntimeException;


    /**
     * 通过角色id查询信息
     *
     * @param roleIds 角色roleIds不能为空
     * @return List<RbacRoleBasicDto>  角色信息
     * @author zxiaozhou
     * @date 2021-05-23 00:09
     */
    List<RbacRoleBasicDto> getRoleListByIds(List<String> roleIds);
}
