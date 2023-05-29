

package com.anyilanxin.anyicloud.process.modules.rbac.service;

import com.anyilanxin.anyicloud.processrpc.model.ProcessRoleModel;
import com.anyilanxin.anyicloud.processrpc.model.ProcessUserModel;

import java.util.Map;
import java.util.Set;

/**
 * 用户组相关
 *
 * @author zxh
 * @date 2021-11-05 17:30
 * @since 1.0.0
 */
public interface IProcessIdentityService {

    /**
     * 通过用户ids获取用户信息
     *
     * @param userIds
     * @return Map<String, ProcessUserModel>
     * @author zxh
     * @date 2022-07-15 01:01
     */
    Map<String, ProcessUserModel> getUserByIds(Set<String> userIds);


    /**
     * 通过用户id获取用户信息
     *
     * @param userId
     * @return ProcessUserModel
     * @author zxh
     * @date 2022-07-15 01:01
     */
    ProcessUserModel getUserById(String userId);


    /**
     * 通过角色ids获取角色信息
     *
     * @param roleIds
     * @return Map<String, ProcessRoleModel>
     * @author zxh
     * @date 2022-07-15 01:02
     */
    Map<String, ProcessRoleModel> getRoleByIds(Set<String> roleIds);


    /**
     * 通过角色id获取角色信息
     *
     * @param roleId
     * @return ProcessRoleModel
     * @author zxh
     * @date 2022-07-15 01:02
     */
    ProcessRoleModel getRoleById(String roleId);
}
