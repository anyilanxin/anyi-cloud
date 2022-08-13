// +----------------------------------------------------------------------
// | SkillFull快速开发平台 [ SkillFull ]
// +----------------------------------------------------------------------
// | 版权所有 2020~2021 zxiaozhou
// +----------------------------------------------------------------------
// | 官方网站: https://www.divisu.com
// +----------------------------------------------------------------------
// | 作者: zxiaozhou <z7630853@163.com>
// +----------------------------------------------------------------------
package com.anyilanxin.skillfull.system.modules.rbac.mapper;

import com.anyilanxin.skillfull.corecommon.model.auth.RoleInfo;
import com.anyilanxin.skillfull.database.datasource.base.mapper.BaseMapper;
import com.anyilanxin.skillfull.system.modules.rbac.entity.RbacRoleClientEntity;
import com.anyilanxin.skillfull.system.modules.rbac.service.dto.RbacRoleSimpleDto;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Set;

/**
 * 角色-客户端(RbacRoleClient)持久层
 *
 * @author zxiaozhou
 * @copyright zxiaozhou（https://divisu.com）
 * @date 2022-05-02 16:12:20
 * @since JDK1.8
 */
@Repository
public interface RbacRoleClientMapper extends BaseMapper<RbacRoleClientEntity> {

    /**
     * 通过客户端角色id物理删除
     *
     * @param roleClient 客户端角色id
     * @return int 成功状态:0-失败,1-成功
     * @author zxiaozhou
     * @date 2022-05-02 16:12:20
     */
    int physicalDeleteById(@Param("id") String roleClient);


    /**
     * 通过客户端角色id物理批量删除
     *
     * @param idList 客户端角色id列表
     * @return int 成功状态:0-失败,大于1-成功
     * @author zxiaozhou
     * @date 2022-05-02 16:12:20
     */
    int physicalDeleteBatchIds(@Param("coll") Collection<String> idList);


    /**
     * 获取客户端角色权限
     *
     * @param clientDetailId
     * @return List<String>
     * @author zxiaozhou
     * @date 2022-07-04 01:18
     */
    Set<String> selectRoleListById(@Param("id") String clientDetailId);


    /**
     * 获取客户端角色权限(完整数据)
     *
     * @param clientDetailId
     * @return List<RbacResourceApiPageDto>
     * @author zxiaozhou
     * @date 2022-07-04 01:18
     */
    Set<RbacRoleSimpleDto> selectRoleAllInfoListById(@Param("id") String clientDetailId);


    /**
     * 获取客户端角色
     *
     * @param clientDetailId 客户端明细id
     * @return Set<ClientRoleModel> 查询结果
     * @author zxiaozhou
     * @date 2022-04-06 00:08
     */
    Set<RoleInfo> getClientAuthRole(@Param("clientDetailId") String clientDetailId);
}
