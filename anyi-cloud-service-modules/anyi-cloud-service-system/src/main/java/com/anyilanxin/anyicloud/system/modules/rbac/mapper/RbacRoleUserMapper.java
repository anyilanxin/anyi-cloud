

package com.anyilanxin.anyicloud.system.modules.rbac.mapper;

import com.anyilanxin.anyicloud.corecommon.model.auth.RoleInfo;
import com.anyilanxin.anyicloud.database.datasource.base.mapper.BaseMapper;
import com.anyilanxin.anyicloud.system.modules.rbac.entity.RbacRoleUserEntity;
import com.anyilanxin.anyicloud.system.modules.rbac.service.dto.RbacMenuDto;
import com.anyilanxin.anyicloud.system.modules.rbac.service.dto.RbacRoleSimpleDto;

import java.util.Collection;
import java.util.Set;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * 角色-客户端(RbacRoleUser)持久层
 *
 * @author zxh
 * @copyright zhouxuanhong（https://anyilanxin.com）
 * @date 2022-07-02 23:01:21
 * @since 1.0.0
 */
@Repository
public interface RbacRoleUserMapper extends BaseMapper<RbacRoleUserEntity> {
    /**
     * 获取用户角色
     *
     * @param userId 用户id
     * @return List<String>
     * @author zxh
     * @date 2022-07-04 01:18
     */
    Set<String> selectUserRoleListById(@Param("userId") String userId);


    /**
     * 获取用户角色(完整数据)
     *
     * @param userId 用户id
     * @return List<RbacRoleSimpleDto>
     * @author zxh
     * @date 2022-07-04 01:18
     */
    Set<RbacRoleSimpleDto> selectUserRoleAllInfoListById(@Param("userId") String userId);


    /**
     * 查询用户角色
     *
     * @param userId
     * @return RoleInfo>
     * @author zxh
     * @date 2022-07-05 00:42
     */
    Set<RoleInfo> selectByUserId(@Param("userId") String userId);


    /**
     * 查询用户角色授权菜单信息
     *
     * @param userId        用户id
     * @param systemCodeSet 系统编码
     * @return Set<RbacMenuDto>
     * @author zxh
     * @date 2022-07-05 00:42
     */
    Set<RbacMenuDto> selectMenuByUserId(@Param("userId") String userId, @Param("systemCodes") Set<String> systemCodeSet);


    /**
     * 通过用户id物理删除
     *
     * @param userId 用户id
     * @return int 成功状态:0-失败,1-成功
     * @author zxh
     * @date 2022-07-02 23:01:20
     */
    int physicalDeleteByUserId(@Param("id") String userId);


    /**
     * 通过角色用户id物理删除
     *
     * @param roleUserId 角色用户id
     * @return int 成功状态:0-失败,1-成功
     * @author zxh
     * @date 2022-07-02 23:01:21
     */
    int physicalDeleteById(@Param("id") String roleUserId);


    /**
     * 通过角色id物理批量删除
     *
     * @param idList 角色用户id列表
     * @return int 成功状态:0-失败,大于1-成功
     * @author zxh
     * @date 2022-07-02 23:01:21
     */
    int physicalDeleteBatchRoleIds(@Param("coll") Collection<String> idList);


    /**
     * 通过角色关联id物理批量删除
     *
     * @param idList 角色用户id列表
     * @return int 成功状态:0-失败,大于1-成功
     * @author zxh
     * @date 2022-07-02 23:01:21
     */
    int physicalDeleteBatchIds(@Param("coll") Collection<String> idList);
}
