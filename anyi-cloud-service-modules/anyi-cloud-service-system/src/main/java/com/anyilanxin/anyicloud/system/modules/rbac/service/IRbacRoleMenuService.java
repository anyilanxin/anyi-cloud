

package com.anyilanxin.anyicloud.system.modules.rbac.service;

import com.anyilanxin.anyicloud.database.datasource.base.service.BaseService;
import com.anyilanxin.anyicloud.system.modules.rbac.entity.RbacRoleMenuEntity;

import java.util.List;

/**
 * 角色-菜单表(RbacRoleMenu)业务层接口
 *
 * @author zxh
 * @copyright zhouxuanhong（https://anyilanxin.com）
 * @date 2022-05-02 16:12:21
 * @since 1.0.0
 */
public interface IRbacRoleMenuService extends BaseService<RbacRoleMenuEntity> {

    /**
     * 保存关联关系(需要先调用删除接口，否则就是追加)
     *
     * @param roleId  角色id
     * @param menuIds 菜单列表
     * @throws RuntimeException
     * @author zxh
     * @date 2022-05-02 16:12:21
     */
    void saveBatch(String roleId, List<String> menuIds) throws RuntimeException;


    /**
     * 角色-菜单表批量删除
     *
     * @param roleIds 角色id列表
     * @throws RuntimeException
     * @author zxh
     * @date 2022-05-02 16:12:21
     */
    void deleteBatch(List<String> roleIds) throws RuntimeException;
}
