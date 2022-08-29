// +----------------------------------------------------------------------
// | SkillFull快速开发平台 [ SkillFull ]
// +----------------------------------------------------------------------
// | 版权所有 2020~2021 zxiaozhou
// +----------------------------------------------------------------------
// | 官方网站: https://skillfull.divisu.com
// +----------------------------------------------------------------------
// | 作者: zxiaozhou <z7630853@163.com>
// +----------------------------------------------------------------------
package com.anyilanxin.skillfull.system.modules.rbac.service;

import com.anyilanxin.skillfull.database.datasource.base.service.BaseService;
import com.anyilanxin.skillfull.system.modules.rbac.entity.RbacRoleClientEntity;

import java.util.List;
import java.util.Set;

/**
 * 角色-客户端(RbacRoleClient)业务层接口
 *
 * @author zxiaozhou
 * @copyright zxiaozhou（https://skillfull.divisu.com）
 * @date 2022-05-02 16:12:20
 * @since JDK1.8
 */
public interface IRbacRoleClientService extends BaseService<RbacRoleClientEntity> {
    /**
     * 保存
     *
     * @param clientDetailId 客户端id
     * @param roleIds        角色id
     * @throws RuntimeException
     * @author zxiaozhou
     * @date 2022-07-02 23:01:20
     */
    void saveBatch(String clientDetailId, Set<String> roleIds) throws RuntimeException;


    /**
     * 通过clientDetailIds删除
     *
     * @param clientDetailIds 客户端ids
     * @throws RuntimeException
     * @author zxiaozhou
     * @date 2022-05-02 16:12:20
     */
    void deleteBatch(List<String> clientDetailIds) throws RuntimeException;
}
