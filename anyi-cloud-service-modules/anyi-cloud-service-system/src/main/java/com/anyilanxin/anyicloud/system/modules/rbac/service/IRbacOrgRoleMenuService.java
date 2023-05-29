

package com.anyilanxin.anyicloud.system.modules.rbac.service;

import com.anyilanxin.anyicloud.database.datasource.base.service.BaseService;
import com.anyilanxin.anyicloud.system.modules.rbac.entity.RbacOrgRoleMenuEntity;

import java.util.List;

/**
 * 机构角色-菜单表(RbacOrgRoleMenu)业务层接口
 *
 * @author zxh
 * @copyright zhouxuanhong（https://anyilanxin.com）
 * @date 2022-07-05 00:22:57
 * @since 1.0.0
 */
public interface IRbacOrgRoleMenuService extends BaseService<RbacOrgRoleMenuEntity> {
    /**
     * 保存关联关系(需要先调用删除接口，否则就是追加)
     *
     * @param orgRoleId 机构角色id
     * @param menuIds   菜单列表
     * @throws RuntimeException
     * @author zxh
     * @date 2022-05-02 16:12:21
     */
    void saveBatch(String orgRoleId, List<String> menuIds) throws RuntimeException;


    /**
     * 角色-菜单表批量删除
     *
     * @param orgRoleIds 机构角色id列表
     * @throws RuntimeException
     * @author zxh
     * @date 2022-05-02 16:12:21
     */
    void deleteBatch(List<String> orgRoleIds) throws RuntimeException;
}
