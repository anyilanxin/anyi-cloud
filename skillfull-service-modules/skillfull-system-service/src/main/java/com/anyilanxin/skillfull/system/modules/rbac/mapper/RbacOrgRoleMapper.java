package com.anyilanxin.skillfull.system.modules.rbac.mapper;

import com.anyilanxin.skillfull.database.datasource.base.mapper.BaseMapper;
import com.anyilanxin.skillfull.system.modules.rbac.controller.vo.RbacOrgRolePageVo;
import com.anyilanxin.skillfull.system.modules.rbac.entity.RbacOrgRoleEntity;
import com.anyilanxin.skillfull.system.modules.rbac.service.dto.RbacOrgRolePageDto;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;

/**
 * 机构角色表(RbacOrgRole)持久层
 *
 * @author zxiaozhou
 * @copyright zxiaozhou（https://skillfull.divisu.com）
 * @date 2022-07-05 00:22:57
 * @since JDK1.8
 */
@Repository
public interface RbacOrgRoleMapper extends BaseMapper<RbacOrgRoleEntity> {
    /**
     * 分页查询
     *
     * @param vo   查询条件
     * @param page 分页信息
     * @return IPage<RbacOrgRolePageDto> 查询结果
     * @author zxiaozhou
     * @date 2022-07-05 00:22:57
     */
    IPage<RbacOrgRolePageDto> pageByModel(Page<RbacOrgRolePageDto> page, @Param("query") RbacOrgRolePageVo vo);


    /**
     * 通过机构角色id物理删除
     *
     * @param orgRoleId 机构角色id
     * @return int 成功状态:0-失败,1-成功
     * @author zxiaozhou
     * @date 2022-07-05 00:22:57
     */
    int physicalDeleteById(@Param("id") String orgRoleId);


    /**
     * 通过机构角色id物理批量删除
     *
     * @param idList 机构角色id列表
     * @return int 成功状态:0-失败,大于1-成功
     * @author zxiaozhou
     * @date 2022-07-05 00:22:57
     */
    int physicalDeleteBatchIds(@Param("coll") Collection<String> idList);
}
