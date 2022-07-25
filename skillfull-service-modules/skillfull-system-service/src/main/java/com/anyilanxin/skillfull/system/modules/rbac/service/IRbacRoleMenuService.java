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
import com.anyilanxin.skillfull.system.modules.rbac.entity.RbacRoleMenuEntity;

import java.util.List;

/**
 * 角色-菜单表(RbacRoleMenu)业务层接口
 *
 * @author zxiaozhou
 * @copyright zxiaozhou（https://divisu.com）
 * @date 2022-05-02 16:12:21
 * @since JDK1.8
 */
public interface IRbacRoleMenuService extends BaseService<RbacRoleMenuEntity> {

    /**
     * 保存关联关系(需要先调用删除接口，否则就是追加)
     *
     * @param roleId  角色id
     * @param menuIds 菜单列表
     * @throws RuntimeException
     * @author zxiaozhou
     * @date 2022-05-02 16:12:21
     */
    void saveBatch(String roleId, List<String> menuIds) throws RuntimeException;


    /**
     * 角色-菜单表批量删除
     *
     * @param roleIds 角色id列表
     * @throws RuntimeException
     * @author zxiaozhou
     * @date 2022-05-02 16:12:21
     */
    void deleteBatch(List<String> roleIds) throws RuntimeException;
}
