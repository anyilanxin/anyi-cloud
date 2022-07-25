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

import com.anyilanxin.skillfull.database.datasource.base.mapper.BaseMapper;
import com.anyilanxin.skillfull.system.modules.rbac.controller.vo.RbacOrgRoleMenuPageVo;
import com.anyilanxin.skillfull.system.modules.rbac.controller.vo.RbacOrgRoleMenuQueryVo;
import com.anyilanxin.skillfull.system.modules.rbac.entity.RbacOrgRoleMenuEntity;
import com.anyilanxin.skillfull.system.modules.rbac.service.dto.RbacOrgRoleMenuDto;
import com.anyilanxin.skillfull.system.modules.rbac.service.dto.RbacOrgRoleMenuPageDto;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

/**
 * 机构角色-菜单表(RbacOrgRoleMenu)持久层
 *
 * @author zxiaozhou
 * @copyright zxiaozhou（https://divisu.com）
 * @date 2022-07-05 00:22:57
 * @since JDK1.8
 */
@Repository
public interface RbacOrgRoleMenuMapper extends BaseMapper<RbacOrgRoleMenuEntity> {
    /**
     * 分页查询
     *
     * @param vo   查询条件
     * @param page 分页信息
     * @return IPage<RbacOrgRoleMenuPageDto> 查询结果
     * @author zxiaozhou
     * @date 2022-07-05 00:22:57
     */
    IPage<RbacOrgRoleMenuPageDto> pageByModel(Page<RbacOrgRoleMenuPageDto> page, @Param("query") RbacOrgRoleMenuPageVo vo);


    /**
     * 条件查询多条
     *
     * @param vo 查询条件
     * @return List<RbacOrgRoleMenuDto> 查询结果
     * @author zxiaozhou
     * @date 2022-07-05 00:22:57
     */
    List<RbacOrgRoleMenuDto> selectListByModel(RbacOrgRoleMenuQueryVo vo);


    /**
     * 通过机构权限角色id物理删除
     *
     * @param orgRoleMenuId 机构权限角色id
     * @return int 成功状态:0-失败,1-成功
     * @author zxiaozhou
     * @date 2022-07-05 00:22:57
     */
    int physicalDeleteById(@Param("id") String orgRoleMenuId);


    /**
     * 通过机构权限角色id物理批量删除
     *
     * @param idList 机构权限角色id列表
     * @return int 成功状态:0-失败,大于1-成功
     * @author zxiaozhou
     * @date 2022-07-05 00:22:57
     */
    int physicalDeleteBatchIds(@Param("coll") Collection<String> idList);


    /**
     * 获取有效的菜单按钮权限
     *
     * @return List<RbacOrgRoleMenuDto>  结果
     * @author zxiaozhou
     * @date 2020-10-08 13:29:15
     */
    List<RbacOrgRoleMenuDto> selectMenuAntButton(@Param("orgRoleId") String orgRoleId);
}
