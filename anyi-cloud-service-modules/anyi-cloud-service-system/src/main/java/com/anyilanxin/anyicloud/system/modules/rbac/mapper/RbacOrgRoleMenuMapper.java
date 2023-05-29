

package com.anyilanxin.anyicloud.system.modules.rbac.mapper;

import com.anyilanxin.anyicloud.database.datasource.base.mapper.BaseMapper;
import com.anyilanxin.anyicloud.system.modules.rbac.controller.vo.RbacOrgRoleMenuPageVo;
import com.anyilanxin.anyicloud.system.modules.rbac.controller.vo.RbacOrgRoleMenuQueryVo;
import com.anyilanxin.anyicloud.system.modules.rbac.entity.RbacOrgRoleMenuEntity;
import com.anyilanxin.anyicloud.system.modules.rbac.service.dto.RbacOrgRoleMenuDto;
import com.anyilanxin.anyicloud.system.modules.rbac.service.dto.RbacOrgRoleMenuPageDto;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import java.util.Collection;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * 机构角色-菜单表(RbacOrgRoleMenu)持久层
 *
 * @author zxh
 * @copyright zhouxuanhong（https://anyilanxin.com）
 * @date 2022-07-05 00:22:57
 * @since 1.0.0
 */
@Repository
public interface RbacOrgRoleMenuMapper extends BaseMapper<RbacOrgRoleMenuEntity> {
    /**
     * 分页查询
     *
     * @param vo   查询条件
     * @param page 分页信息
     * @return IPage<RbacOrgRoleMenuPageDto> 查询结果
     * @author zxh
     * @date 2022-07-05 00:22:57
     */
    IPage<RbacOrgRoleMenuPageDto> pageByModel(Page<RbacOrgRoleMenuPageDto> page, @Param("query") RbacOrgRoleMenuPageVo vo);


    /**
     * 条件查询多条
     *
     * @param vo 查询条件
     * @return List<RbacOrgRoleMenuDto> 查询结果
     * @author zxh
     * @date 2022-07-05 00:22:57
     */
    List<RbacOrgRoleMenuDto> selectListByModel(RbacOrgRoleMenuQueryVo vo);


    /**
     * 通过机构权限角色id物理删除
     *
     * @param orgRoleMenuId 机构权限角色id
     * @return int 成功状态:0-失败,1-成功
     * @author zxh
     * @date 2022-07-05 00:22:57
     */
    int physicalDeleteById(@Param("id") String orgRoleMenuId);


    /**
     * 通过机构权限角色id物理批量删除
     *
     * @param idList 机构权限角色id列表
     * @return int 成功状态:0-失败,大于1-成功
     * @author zxh
     * @date 2022-07-05 00:22:57
     */
    int physicalDeleteBatchIds(@Param("coll") Collection<String> idList);


    /**
     * 获取有效的菜单按钮权限
     *
     * @return List<RbacOrgRoleMenuDto> 结果
     * @author zxh
     * @date 2020-10-08 13:29:15
     */
    List<RbacOrgRoleMenuDto> selectMenuAntButton(@Param("orgRoleId") String orgRoleId);
}
