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
import com.anyilanxin.skillfull.system.modules.rbac.entity.RbacRoleUserEntity;

import java.util.List;
import java.util.Set;

/**
 * 角色-客户端(RbacRoleUser)业务层接口
 *
 * @author zxiaozhou
 * @copyright zxiaozhou（https://divisu.com）
 * @date 2022-07-02 23:01:21
 * @since JDK1.8
 */
public interface IRbacRoleUserService extends BaseService<RbacRoleUserEntity> {
    /**
     * 保存
     *
     * @param userId  用户id
     * @param roleIds 角色 id
     * @throws RuntimeException
     * @author zxiaozhou
     * @date 2022-07-02 23:01:20
     */
    void saveBatch(String userId, Set<String> roleIds) throws RuntimeException;


    /**
     * 通过指定用户删除
     *
     * @param userIds 用户ids
     * @throws RuntimeException
     * @author zxiaozhou
     * @date 2022-05-02 16:12:20
     */
    void deleteBatch(List<String> userIds) throws RuntimeException;
}
