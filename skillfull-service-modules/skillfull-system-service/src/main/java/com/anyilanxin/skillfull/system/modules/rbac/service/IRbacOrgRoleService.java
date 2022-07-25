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
import com.anyilanxin.skillfull.system.modules.rbac.controller.vo.RbacOrgRoleAuthVo;
import com.anyilanxin.skillfull.system.modules.rbac.controller.vo.RbacOrgRolePageVo;
import com.anyilanxin.skillfull.system.modules.rbac.controller.vo.RbacOrgRoleVo;
import com.anyilanxin.skillfull.system.modules.rbac.entity.RbacOrgRoleEntity;
import com.anyilanxin.skillfull.system.modules.rbac.service.dto.RbacOrgRoleDto;
import com.anyilanxin.skillfull.system.modules.rbac.service.dto.RbacOrgRoleMenuButtonDto;
import com.anyilanxin.skillfull.system.modules.rbac.service.dto.RbacOrgRolePageDto;

import java.util.List;
import java.util.Set;

/**
 * 机构角色表(RbacOrgRole)业务层接口
 *
 * @author zxiaozhou
 * @copyright zxiaozhou（https://divisu.com）
 * @date 2022-07-05 00:22:57
 * @since JDK1.8
 */
public interface IRbacOrgRoleService extends BaseService<RbacOrgRoleEntity> {
    /**
     * 保存
     *
     * @param vo 机构角色表保存数据
     * @throws RuntimeException
     * @author zxiaozhou
     * @date 2022-07-05 00:22:57
     */
    void save(RbacOrgRoleVo vo) throws RuntimeException;


    /**
     * 通过id更新
     *
     * @param orgRoleId 机构角色id
     * @param vo        机构角色表更新数据
     * @throws RuntimeException
     * @author zxiaozhou
     * @date 2022-07-05 00:22:57
     */
    void updateById(String orgRoleId, RbacOrgRoleVo vo) throws RuntimeException;


    /**
     * 分页查询
     *
     * @param vo 分页查询条件
     * @return PageDto<RbacOrgRolePageDto> 分页查询结果
     * @throws RuntimeException
     * @author zxiaozhou
     * @date 2022-07-05 00:22:57
     */
    PageDto<RbacOrgRolePageDto> pageByModel(RbacOrgRolePageVo vo) throws RuntimeException;


    /**
     * 通过id查询详情
     *
     * @param orgRoleId 机构角色id
     * @return RbacOrgRoleDto 查询结果
     * @throws RuntimeException
     * @author zxiaozhou
     * @date 2022-07-05 00:22:57
     */
    RbacOrgRoleDto getById(String orgRoleId) throws RuntimeException;


    /**
     * 通过orgRoleId删除
     *
     * @param orgRoleId 机构角色id
     * @throws RuntimeException
     * @author zxiaozhou
     * @date 2022-07-05 00:22:57
     */
    void deleteById(String orgRoleId) throws RuntimeException;


    /**
     * 机构角色表批量删除
     *
     * @param orgRoleIds 机构角色id列表
     * @throws RuntimeException
     * @author zxiaozhou
     * @date 2022-07-05 00:22:57
     */
    void deleteBatch(List<String> orgRoleIds) throws RuntimeException;


    /**
     * 更新或添加角色权限
     *
     * @param orgRoleId
     * @param vo
     * @author zxiaozhou
     * @date 2022-07-07 09:48
     */
    void updateAuth(String orgRoleId, RbacOrgRoleAuthVo vo);


    /**
     * 通过角色id查询菜单按钮权限
     *
     * @param orgRoleId
     * @return Set<RbacOrgRoleMenuButtonDto>
     * @author zxiaozhou
     * @date 2022-07-07 09:55
     */
    Set<RbacOrgRoleMenuButtonDto> getMenuActions(String orgRoleId);


    /**
     * 角色启用或禁用
     *
     * @param orgRoleId
     * @param status
     * @author zxiaozhou
     * @date 2022-07-07 09:56
     */
    void updateStatus(String orgRoleId, Integer status);
}
