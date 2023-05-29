

package com.anyilanxin.anyicloud.system.modules.rbac.service;

import com.anyilanxin.anyicloud.database.datasource.base.service.BaseService;
import com.anyilanxin.anyicloud.system.modules.rbac.entity.RbacRoleClientEntity;

import java.util.List;
import java.util.Set;

/**
 * 角色-客户端(RbacRoleClient)业务层接口
 *
 * @author zxh
 * @copyright zhouxuanhong（https://anyilanxin.com）
 * @date 2022-05-02 16:12:20
 * @since 1.0.0
 */
public interface IRbacRoleClientService extends BaseService<RbacRoleClientEntity> {
    /**
     * 保存
     *
     * @param clientDetailId 客户端id
     * @param roleIds        角色id
     * @throws RuntimeException
     * @author zxh
     * @date 2022-07-02 23:01:20
     */
    void saveBatch(String clientDetailId, Set<String> roleIds) throws RuntimeException;


    /**
     * 通过clientDetailIds删除
     *
     * @param clientDetailIds 客户端ids
     * @throws RuntimeException
     * @author zxh
     * @date 2022-05-02 16:12:20
     */
    void deleteBatch(List<String> clientDetailIds) throws RuntimeException;
}
