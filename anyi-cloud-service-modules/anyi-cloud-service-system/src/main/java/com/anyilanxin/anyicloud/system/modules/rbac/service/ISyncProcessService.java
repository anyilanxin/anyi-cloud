

package com.anyilanxin.anyicloud.system.modules.rbac.service;

/**
 * 同步流程引擎
 *
 * @author zxh
 * @date 2021-11-08 16:29
 * @since 1.0.0
 */
public interface ISyncProcessService {
    /**
     * 添加或更新用户
     *
     * @param userId ${@link String}
     * @author zxh
     * @date 2021-11-08 16:32
     */
    void addOrUpdateUser(String userId);


    /**
     * 删除用户
     *
     * @param userId ${@link String}
     * @author zxh
     * @date 2021-11-08 16:32
     */
    void deleteUser(String userId);


    /**
     * 全量同步用户
     *
     * @author zxh
     * @date 2021-11-08 16:32
     */
    void syncUserAll();


    /**
     * 添加或更新角色
     *
     * @param roleId ${@link String}
     * @author zxh
     * @date 2021-11-08 16:32
     */
    void addOrUpdateRole(String roleId);


    /**
     * 删除角色
     *
     * @param roleId ${@link String}
     * @author zxh
     * @date 2021-11-08 16:32
     */
    void deleteRole(String roleId);


    /**
     * 全量同步角色
     *
     * @author zxh
     * @date 2021-11-08 16:32
     */
    void syncRoleAll();
}
