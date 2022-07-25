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
import com.anyilanxin.skillfull.system.modules.rbac.controller.vo.RbacOrgPageVo;
import com.anyilanxin.skillfull.system.modules.rbac.entity.RbacOrgEntity;
import com.anyilanxin.skillfull.system.modules.rbac.service.dto.RbacOrgTreePageDto;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;

/**
 * 组织表(RbacOrg)持久层
 *
 * @author zxiaozhou
 * @copyright zxiaozhou（https://divisu.com）
 * @date 2022-05-02 16:39:45
 * @since JDK1.8
 */
@Repository
public interface RbacOrgMapper extends BaseMapper<RbacOrgEntity> {
    /**
     * 分页查询
     *
     * @param vo   查询条件
     * @param page 分页信息
     * @return IPage<RbacOrgPageDto> 查询结果
     * @author zxiaozhou
     * @date 2022-05-02 16:39:45
     */
    IPage<RbacOrgTreePageDto> pageByModel(Page<RbacOrgTreePageDto> page, @Param("query") RbacOrgPageVo vo);


    /**
     * 通过组织id物理删除
     *
     * @param orgId 组织id
     * @return int 成功状态:0-失败,1-成功
     * @author zxiaozhou
     * @date 2022-05-02 16:39:45
     */
    int physicalDeleteById(@Param("id") String orgId);


    /**
     * 通过组织id物理批量删除
     *
     * @param idList 组织id列表
     * @return int 成功状态:0-失败,大于1-成功
     * @author zxiaozhou
     * @date 2022-05-02 16:39:45
     */
    int physicalDeleteBatchIds(@Param("coll") Collection<String> idList);
}
