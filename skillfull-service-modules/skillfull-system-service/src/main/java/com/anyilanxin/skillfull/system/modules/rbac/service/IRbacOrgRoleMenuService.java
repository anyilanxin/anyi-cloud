package com.anyilanxin.skillfull.system.modules.rbac.service;

import com.anyilanxin.skillfull.database.datasource.base.service.BaseService;
import com.anyilanxin.skillfull.system.modules.rbac.entity.RbacOrgRoleMenuEntity;

import java.util.List;

/**
 * 机构角色-菜单表(RbacOrgRoleMenu)业务层接口
 *
 * @author zxiaozhou
 * @copyright zxiaozhou（https://skillfull.divisu.com）
 * @date 2022-07-05 00:22:57
 * @since JDK1.8
 */
public interface IRbacOrgRoleMenuService extends BaseService<RbacOrgRoleMenuEntity> {
    /**
     * 保存关联关系(需要先调用删除接口，否则就是追加)
     *
     * @param orgRoleId 机构角色id
     * @param menuIds   菜单列表
     * @throws RuntimeException
     * @author zxiaozhou
     * @date 2022-05-02 16:12:21
     */
    void saveBatch(String orgRoleId, List<String> menuIds) throws RuntimeException;


    /**
     * 角色-菜单表批量删除
     *
     * @param orgRoleIds 机构角色id列表
     * @throws RuntimeException
     * @author zxiaozhou
     * @date 2022-05-02 16:12:21
     */
    void deleteBatch(List<String> orgRoleIds) throws RuntimeException;
}
