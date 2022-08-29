// +----------------------------------------------------------------------
// | SkillFull快速开发平台 [ SkillFull ]
// +----------------------------------------------------------------------
// | 版权所有 2020~2021 zxiaozhou
// +----------------------------------------------------------------------
// | 官方网站: https://skillfull.divisu.com
// +----------------------------------------------------------------------
// | 作者: zxiaozhou <z7630853@163.com>
// +----------------------------------------------------------------------
package com.anyilanxin.skillfull.system.modules.rbac.mapper;

import com.anyilanxin.skillfull.corecommon.model.auth.RoleInfo;
import com.anyilanxin.skillfull.database.datasource.base.mapper.BaseMapper;
import com.anyilanxin.skillfull.system.modules.rbac.entity.RbacOrgRoleUserEntity;
import com.anyilanxin.skillfull.system.modules.rbac.service.dto.RbacMenuDto;
import com.anyilanxin.skillfull.system.modules.rbac.service.dto.RbacRoleSimpleDto;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Set;

/**
 * 机构角色-用户(RbacOrgRoleUser)持久层
 *
 * @author zxiaozhou
 * @copyright zxiaozhou（https://skillfull.divisu.com）
 * @date 2022-07-05 00:22:57
 * @since JDK1.8
 */
@Repository
public interface RbacOrgRoleUserMapper extends BaseMapper<RbacOrgRoleUserEntity> {
    /**
     * 获取用户机构角色
     *
     * @param userId 用户id
     * @param orgId  机构id
     * @return List<String>
     * @author zxiaozhou
     * @date 2022-07-04 01:18
     */
    Set<String> selectUserOrgRoleListById(@Param("userId") String userId,
                                          @Param("orgId") String orgId);


    /**
     * 获取用户机构角色(完整数据)
     *
     * @param userId 用户id
     * @param orgId  机构id
     * @return List<RbacRoleSimpleDto>
     * @author zxiaozhou
     * @date 2022-07-04 01:18
     */
    Set<RbacRoleSimpleDto> selectUserOrgRoleAllInfoListById(@Param("userId") String userId,
                                                            @Param("orgId") String orgId);


    /**
     * 查询用户在某个机构下的角色信息
     *
     * @param userId 用户id
     * @param orgId  机构id
     * @return RoleInfo>
     * @author zxiaozhou
     * @date 2022-07-05 00:36
     */
    Set<RoleInfo> selectByUserIdAndOrgId(@Param("userId") String userId, @Param("orgId") String orgId);


    /**
     * 查询用户在某个机构下的角色关联菜单信息
     *
     * @param userId        用户id
     * @param orgId         机构id
     * @param systemCodeSet 系统编码
     * @return Set<RbacMenuDto>
     * @author zxiaozhou
     * @date 2022-07-05 00:36
     */
    Set<RbacMenuDto> selectMenuByUserIdAndOrgId(@Param("userId") String userId,
                                                @Param("orgId") String orgId,
                                                @Param("systemCodes") Set<String> systemCodeSet);


    /**
     * 通过角色用户id物理删除
     *
     * @param roleUserId 角色用户id
     * @return int 成功状态:0-失败,1-成功
     * @author zxiaozhou
     * @date 2022-07-05 00:22:57
     */
    int physicalDeleteById(@Param("id") String roleUserId);

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
     * 通过角色用户id物理批量删除
     *
     * @param idList 角色用户id列表
     * @return int 成功状态:0-失败,大于1-成功
     * @author zxiaozhou
     * @date 2022-07-05 00:22:57
     */
    int physicalDeleteBatchIds(@Param("coll") Collection<String> idList);
}
