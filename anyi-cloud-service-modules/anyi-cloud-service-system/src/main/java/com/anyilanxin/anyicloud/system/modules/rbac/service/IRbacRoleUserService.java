

package com.anyilanxin.anyicloud.system.modules.rbac.service;

import com.anyilanxin.anyicloud.database.datasource.base.service.BaseService;
import com.anyilanxin.anyicloud.system.modules.rbac.entity.RbacRoleUserEntity;

import java.util.List;
import java.util.Set;

/**
 * 角色-客户端(RbacRoleUser)业务层接口
 *
 * @author zxh
 * @copyright zhouxuanhong（https://anyilanxin.com）
 * @date 2022-07-02 23:01:21
 * @since 1.0.0
 */
public interface IRbacRoleUserService extends BaseService<RbacRoleUserEntity> {
    /**
     * 保存
     *
     * @param userId  用户id
     * @param roleIds 角色 id
     * @throws RuntimeException
     * @author zxh
     * @date 2022-07-02 23:01:20
     */
    void saveBatch(String userId, Set<String> roleIds) throws RuntimeException;


    /**
     * 通过指定用户删除
     *
     * @param userIds 用户ids
     * @throws RuntimeException
     * @author zxh
     * @date 2022-05-02 16:12:20
     */
    void deleteBatch(List<String> userIds) throws RuntimeException;
}
