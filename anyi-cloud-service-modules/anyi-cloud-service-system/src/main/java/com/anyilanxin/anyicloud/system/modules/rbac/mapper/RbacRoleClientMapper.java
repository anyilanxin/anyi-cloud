

package com.anyilanxin.anyicloud.system.modules.rbac.mapper;

import com.anyilanxin.anyicloud.corecommon.model.auth.RoleInfo;
import com.anyilanxin.anyicloud.database.datasource.base.mapper.BaseMapper;
import com.anyilanxin.anyicloud.system.modules.rbac.entity.RbacRoleClientEntity;
import com.anyilanxin.anyicloud.system.modules.rbac.service.dto.RbacRoleSimpleDto;

import java.util.Collection;
import java.util.Set;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * 角色-客户端(RbacRoleClient)持久层
 *
 * @author zxh
 * @copyright zhouxuanhong（https://anyilanxin.com）
 * @date 2022-05-02 16:12:20
 * @since 1.0.0
 */
@Repository
public interface RbacRoleClientMapper extends BaseMapper<RbacRoleClientEntity> {

    /**
     * 通过客户端角色id物理删除
     *
     * @param roleClient 客户端角色id
     * @return int 成功状态:0-失败,1-成功
     * @author zxh
     * @date 2022-05-02 16:12:20
     */
    int physicalDeleteById(@Param("id") String roleClient);


    /**
     * 通过客户端角色id物理批量删除
     *
     * @param idList 客户端角色id列表
     * @return int 成功状态:0-失败,大于1-成功
     * @author zxh
     * @date 2022-05-02 16:12:20
     */
    int physicalDeleteBatchIds(@Param("coll") Collection<String> idList);


    /**
     * 获取客户端角色权限
     *
     * @param clientDetailId
     * @return List<String>
     * @author zxh
     * @date 2022-07-04 01:18
     */
    Set<String> selectRoleListById(@Param("id") String clientDetailId);


    /**
     * 获取客户端角色权限(完整数据)
     *
     * @param clientDetailId
     * @return List<RbacResourceApiPageDto>
     * @author zxh
     * @date 2022-07-04 01:18
     */
    Set<RbacRoleSimpleDto> selectRoleAllInfoListById(@Param("id") String clientDetailId);


    /**
     * 获取客户端角色
     *
     * @param clientDetailId 客户端明细id
     * @return Set<ClientRoleModel> 查询结果
     * @author zxh
     * @date 2022-04-06 00:08
     */
    Set<RoleInfo> getClientAuthRole(@Param("clientDetailId") String clientDetailId);
}
