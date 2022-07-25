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

import com.anyilanxin.skillfull.corecommon.auth.model.RoleInfo;
import com.anyilanxin.skillfull.database.datasource.base.mapper.BaseMapper;
import com.anyilanxin.skillfull.system.modules.rbac.entity.RbacRoleUserEntity;
import com.anyilanxin.skillfull.system.modules.rbac.service.dto.RbacMenuDto;
import com.anyilanxin.skillfull.system.modules.rbac.service.dto.RbacRoleSimpleDto;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Set;

/**
 * 角色-客户端(RbacRoleUser)持久层
 *
 * @author zxiaozhou
 * @copyright zxiaozhou（https://divisu.com）
 * @date 2022-07-02 23:01:21
 * @since JDK1.8
 */
@Repository
public interface RbacRoleUserMapper extends BaseMapper<RbacRoleUserEntity> {
    /**
     * 获取用户角色
     *
     * @param userId 用户id
     * @return List<String>
     * @author zxiaozhou
     * @date 2022-07-04 01:18
     */
    Set<String> selectUserRoleListById(@Param("userId") String userId);


    /**
     * 获取用户角色(完整数据)
     *
     * @param userId 用户id
     * @return List<RbacRoleSimpleDto>
     * @author zxiaozhou
     * @date 2022-07-04 01:18
     */
    Set<RbacRoleSimpleDto> selectUserRoleAllInfoListById(@Param("userId") String userId);

    /**
     * 查询用户角色
     *
     * @param userId
     * @return RoleInfo>
     * @author zxiaozhou
     * @date 2022-07-05 00:42
     */
    Set<RoleInfo> selectByUserId(@Param("userId") String userId);


    /**
     * 查询用户角色授权菜单信息
     *
     * @param userId        用户id
     * @param systemCodeSet 系统编码
     * @return Set<RbacMenuDto>
     * @author zxiaozhou
     * @date 2022-07-05 00:42
     */
    Set<RbacMenuDto> selectMenuByUserId(@Param("userId") String userId,
                                        @Param("systemCodes") Set<String> systemCodeSet);


    /**
     * 通过用户id物理删除
     *
     * @param userId 用户id
     * @return int 成功状态:0-失败,1-成功
     * @author zxiaozhou
     * @date 2022-07-02 23:01:20
     */
    int physicalDeleteByUserId(@Param("id") String userId);


    /**
     * 通过角色用户id物理删除
     *
     * @param roleUserId 角色用户id
     * @return int 成功状态:0-失败,1-成功
     * @author zxiaozhou
     * @date 2022-07-02 23:01:21
     */
    int physicalDeleteById(@Param("id") String roleUserId);


    /**
     * 通过角色id物理批量删除
     *
     * @param idList 角色用户id列表
     * @return int 成功状态:0-失败,大于1-成功
     * @author zxiaozhou
     * @date 2022-07-02 23:01:21
     */
    int physicalDeleteBatchRoleIds(@Param("coll") Collection<String> idList);


    /**
     * 通过角色关联id物理批量删除
     *
     * @param idList 角色用户id列表
     * @return int 成功状态:0-失败,大于1-成功
     * @author zxiaozhou
     * @date 2022-07-02 23:01:21
     */
    int physicalDeleteBatchIds(@Param("coll") Collection<String> idList);
}
