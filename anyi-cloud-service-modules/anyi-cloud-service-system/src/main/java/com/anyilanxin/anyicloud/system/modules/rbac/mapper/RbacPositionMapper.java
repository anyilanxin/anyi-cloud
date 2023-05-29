

package com.anyilanxin.anyicloud.system.modules.rbac.mapper;

import com.anyilanxin.anyicloud.database.datasource.base.mapper.BaseMapper;
import com.anyilanxin.anyicloud.system.modules.rbac.controller.vo.RbacPositionPageVo;
import com.anyilanxin.anyicloud.system.modules.rbac.entity.RbacPositionEntity;
import com.anyilanxin.anyicloud.system.modules.rbac.service.dto.RbacPositionPageDto;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import java.util.Collection;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * 职位表(RbacPosition)持久层
 *
 * @author zxh
 * @copyright zhouxuanhong（https://anyilanxin.com）
 * @date 2022-05-02 16:12:20
 * @since 1.0.0
 */
@Repository
public interface RbacPositionMapper extends BaseMapper<RbacPositionEntity> {
    /**
     * 分页查询
     *
     * @param vo   查询条件
     * @param page 分页信息
     * @return IPage<RbacPositionPageDto> 查询结果
     * @author zxh
     * @date 2022-05-02 16:12:20
     */
    IPage<RbacPositionPageDto> pageByModel(Page<RbacPositionPageDto> page, @Param("query") RbacPositionPageVo vo);


    /**
     * 通过职位id物理删除
     *
     * @param positionId 职位id
     * @return int 成功状态:0-失败,1-成功
     * @author zxh
     * @date 2022-05-02 16:12:20
     */
    int physicalDeleteById(@Param("id") String positionId);


    /**
     * 通过职位id物理批量删除
     *
     * @param idList 职位id列表
     * @return int 成功状态:0-失败,大于1-成功
     * @author zxh
     * @date 2022-05-02 16:12:20
     */
    int physicalDeleteBatchIds(@Param("coll") Collection<String> idList);
}
