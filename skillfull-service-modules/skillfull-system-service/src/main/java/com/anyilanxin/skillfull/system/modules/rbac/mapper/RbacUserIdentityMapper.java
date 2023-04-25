package com.anyilanxin.skillfull.system.modules.rbac.mapper;

import com.anyilanxin.skillfull.database.datasource.base.mapper.BaseMapper;
import com.anyilanxin.skillfull.system.modules.rbac.controller.vo.RbacUserIdentityPageVo;
import com.anyilanxin.skillfull.system.modules.rbac.entity.RbacUserIdentityEntity;
import com.anyilanxin.skillfull.system.modules.rbac.service.dto.RbacUserIdentityPageDto;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;

/**
 * 实名信息表(RbacUserIdentity)持久层
 *
 * @author zxiaozhou
 * @copyright zxiaozhou（https://skillfull.divisu.com）
 * @date 2022-05-02 16:12:21
 * @since JDK1.8
 */
@Repository
public interface RbacUserIdentityMapper extends BaseMapper<RbacUserIdentityEntity> {
    /**
     * 分页查询
     *
     * @param vo   查询条件
     * @param page 分页信息
     * @return IPage<RbacUserIdentityPageDto> 查询结果
     * @author zxiaozhou
     * @date 2022-05-02 16:12:21
     */
    IPage<RbacUserIdentityPageDto> pageByModel(Page<RbacUserIdentityPageDto> page, @Param("query") RbacUserIdentityPageVo vo);


    /**
     * 通过实名信息id物理删除
     *
     * @param identityId 实名信息id
     * @return int 成功状态:0-失败,1-成功
     * @author zxiaozhou
     * @date 2022-05-02 16:12:21
     */
    int physicalDeleteById(@Param("id") String identityId);


    /**
     * 通过实名信息id物理批量删除
     *
     * @param idList 实名信息id列表
     * @return int 成功状态:0-失败,大于1-成功
     * @author zxiaozhou
     * @date 2022-05-02 16:12:21
     */
    int physicalDeleteBatchIds(@Param("coll") Collection<String> idList);
}
