

package com.anyilanxin.anyicloud.system.modules.rbac.mapper;

import com.anyilanxin.anyicloud.database.datasource.base.mapper.BaseMapper;
import com.anyilanxin.anyicloud.system.modules.rbac.controller.vo.RbacOrgPageVo;
import com.anyilanxin.anyicloud.system.modules.rbac.entity.RbacOrgEntity;
import com.anyilanxin.anyicloud.system.modules.rbac.service.dto.RbacOrgTreePageDto;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import java.util.Collection;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * 组织表(RbacOrg)持久层
 *
 * @author zxh
 * @copyright zhouxuanhong（https://anyilanxin.com）
 * @date 2022-05-02 16:39:45
 * @since 1.0.0
 */
@Repository
public interface RbacOrgMapper extends BaseMapper<RbacOrgEntity> {
    /**
     * 分页查询
     *
     * @param vo   查询条件
     * @param page 分页信息
     * @return IPage<RbacOrgPageDto> 查询结果
     * @author zxh
     * @date 2022-05-02 16:39:45
     */
    IPage<RbacOrgTreePageDto> pageByModel(Page<RbacOrgTreePageDto> page, @Param("query") RbacOrgPageVo vo);


    /**
     * 通过组织id物理删除
     *
     * @param orgId 组织id
     * @return int 成功状态:0-失败,1-成功
     * @author zxh
     * @date 2022-05-02 16:39:45
     */
    int physicalDeleteById(@Param("id") String orgId);


    /**
     * 通过组织id物理批量删除
     *
     * @param idList 组织id列表
     * @return int 成功状态:0-失败,大于1-成功
     * @author zxh
     * @date 2022-05-02 16:39:45
     */
    int physicalDeleteBatchIds(@Param("coll") Collection<String> idList);
}
