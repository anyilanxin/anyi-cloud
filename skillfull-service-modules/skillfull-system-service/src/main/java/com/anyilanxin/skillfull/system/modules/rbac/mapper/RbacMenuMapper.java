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
import com.anyilanxin.skillfull.system.modules.rbac.controller.vo.RbacMenuPageVo;
import com.anyilanxin.skillfull.system.modules.rbac.entity.RbacMenuEntity;
import com.anyilanxin.skillfull.system.modules.rbac.service.dto.RbacMenuDto;
import com.anyilanxin.skillfull.system.modules.rbac.service.dto.RbacMenuPageDto;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Set;

/**
 * 菜单表(RbacMenu)持久层
 *
 * @author zxiaozhou
 * @copyright zxiaozhou（https://divisu.com）
 * @date 2022-05-02 22:49:09
 * @since JDK1.8
 */
@Repository
public interface RbacMenuMapper extends BaseMapper<RbacMenuEntity> {
    /**
     * 分页查询
     *
     * @param vo   查询条件
     * @param page 分页信息
     * @return IPage<RbacMenuPageDto> 查询结果
     * @author zxiaozhou
     * @date 2022-05-02 22:49:09
     */
    IPage<RbacMenuPageDto> pageByModel(Page<RbacMenuPageDto> page, @Param("query") RbacMenuPageVo vo);


    /**
     * 通过权限id物理删除
     *
     * @param menuId 权限id
     * @return int 成功状态:0-失败,1-成功
     * @author zxiaozhou
     * @date 2022-05-02 22:49:09
     */
    int physicalDeleteById(@Param("id") String menuId);


    /**
     * 通过权限id物理批量删除
     *
     * @param idList 权限id列表
     * @return int 成功状态:0-失败,大于1-成功
     * @author zxiaozhou
     * @date 2022-05-02 22:49:09
     */
    int physicalDeleteBatchIds(@Param("coll") Collection<String> idList);


    /**
     * 获取所有有效的按钮权限信息
     *
     * @param systemCodeSet 系统编码
     * @return Set<UserRouteModel>
     * @author zxiaozhou
     * @date 2022-07-12 21:46
     */
    Set<RbacMenuDto> getAllButton(@Param("systemCodes") Set<String> systemCodeSet);


    /**
     * 获取所有有效的菜单信息
     *
     * @param systemCodeSet 系统编码
     * @return Set<UserRouteModel>
     * @author zxiaozhou
     * @date 2022-07-12 21:46
     */
    Set<RbacMenuDto> getAllMenu(@Param("systemCodes") Set<String> systemCodeSet);
}
