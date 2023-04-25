package com.anyilanxin.skillfull.system.modules.rbac.service;

import com.anyilanxin.skillfull.database.datasource.base.service.BaseService;
import com.anyilanxin.skillfull.system.modules.rbac.entity.RbacOrgRoleUserEntity;

import java.util.Set;

/**
 * 机构角色-用户(RbacOrgRoleUser)业务层接口
 *
 * @author zxiaozhou
 * @copyright zxiaozhou（https://skillfull.divisu.com）
 * @date 2022-07-05 00:22:57
 * @since JDK1.8
 */
public interface IRbacOrgRoleUserService extends BaseService<RbacOrgRoleUserEntity> {
    /**
     * 保存
     *
     * @param userId     用户id
     * @param orgId      机构id
     * @param orgRoleIds 机构角色 id
     * @throws RuntimeException
     * @author zxiaozhou
     * @date 2022-07-02 23:01:20
     */
    void saveBatch(String userId, String orgId, Set<String> orgRoleIds) throws RuntimeException;


    /**
     * 通过指定用户以及机构删除
     *
     * @param userId 用户id
     * @param orgId  机构id
     * @throws RuntimeException
     * @author zxiaozhou
     * @date 2022-05-02 16:12:20
     */
    void deleteByUserId(String userId, String orgId) throws RuntimeException;
}
