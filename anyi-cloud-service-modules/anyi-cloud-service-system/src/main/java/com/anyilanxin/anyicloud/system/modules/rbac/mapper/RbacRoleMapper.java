

package com.anyilanxin.anyicloud.system.modules.rbac.mapper;

import com.anyilanxin.anyicloud.database.datasource.base.mapper.BaseMapper;
import com.anyilanxin.anyicloud.system.modules.rbac.controller.vo.RbacRolePageVo;
import com.anyilanxin.anyicloud.system.modules.rbac.controller.vo.RbacRoleQueryVo;
import com.anyilanxin.anyicloud.system.modules.rbac.entity.RbacRoleEntity;
import com.anyilanxin.anyicloud.system.modules.rbac.service.dto.RbacRoleDto;
import com.anyilanxin.anyicloud.system.modules.rbac.service.dto.RbacRolePageDto;
import com.anyilanxin.anyicloud.systemrpc.model.UserRoleModel;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * 角色表(RbacRole)持久层
 *
 * @author zxh
 * @copyright zhouxuanhong（https://anyilanxin.com）
 * @date 2022-05-02 19:29:58
 * @since 1.0.0
 */
@Repository
public interface RbacRoleMapper extends BaseMapper<RbacRoleEntity> {
    /**
     * 分页查询
     *
     * @param vo   查询条件
     * @param page 分页信息
     * @return IPage<RbacRolePageDto> 查询结果
     * @author zxh
     * @date 2022-05-02 19:29:58
     */
    IPage<RbacRolePageDto> pageByModel(Page<RbacRolePageDto> page, @Param("query") RbacRolePageVo vo, @Param("superRoleCode") String superRoleCode);


    /**
     * 条件查询多条
     *
     * @param vo 查询条件
     * @return List<RbacRoleDto> 查询结果
     * @author zxh
     * @date 2022-05-02 19:29:58
     */
    List<RbacRoleDto> selectListByModel(RbacRoleQueryVo vo, @Param("superRoleCode") String superRoleCode);


    /**
     * 获取用户授权的角色
     *
     * @param userId ${@link String}
     * @param orgId  ${@link String}
     * @return Set<UserRoleModel> ${@link Set< UserRoleModel >}
     * @author zxh
     * @date 2022-04-06 00:08
     */
    Set<UserRoleModel> getUserAuthRole(String userId, String orgId, @Param("superRoleCode") String superRoleCode);


    /**
     * 通过角色id物理删除
     *
     * @param roleId 角色id
     * @return int 成功状态:0-失败,1-成功
     * @author zxh
     * @date 2022-05-02 19:29:58
     */
    int physicalDeleteById(@Param("id") String roleId);


    /**
     * 通过角色id物理批量删除
     *
     * @param idList 角色id列表
     * @return int 成功状态:0-失败,大于1-成功
     * @author zxh
     * @date 2022-05-02 19:29:58
     */
    int physicalDeleteBatchIds(@Param("coll") Collection<String> idList);
}
