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
import com.anyilanxin.skillfull.system.modules.rbac.controller.vo.RbacClientDetailsPageVo;
import com.anyilanxin.skillfull.system.modules.rbac.entity.RbacClientDetailsEntity;
import com.anyilanxin.skillfull.system.modules.rbac.service.dto.RbacClientDetailsPageDto;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;

/**
 * 授权客户端信息(RbacClientDetails)持久层
 *
 * @author zxiaozhou
 * @copyright zxiaozhou（https://divisu.com）
 * @date 2022-05-02 16:12:21
 * @since JDK1.8
 */
@Repository
public interface RbacClientDetailsMapper extends BaseMapper<RbacClientDetailsEntity> {
    /**
     * 分页查询
     *
     * @param vo   查询条件
     * @param page 分页信息
     * @return IPage<RbacClientDetailsPageDto> 查询结果
     * @author zxiaozhou
     * @date 2022-05-02 16:12:21
     */
    IPage<RbacClientDetailsPageDto> pageByModel(Page<RbacClientDetailsPageDto> page, @Param("query") RbacClientDetailsPageVo vo);


    /**
     * 通过客户端信息id物理删除
     *
     * @param clientDetailId 客户端信息id
     * @return int 成功状态:0-失败,1-成功
     * @author zxiaozhou
     * @date 2022-05-02 16:12:21
     */
    int physicalDeleteById(@Param("id") String clientDetailId);


    /**
     * 通过客户端信息id物理批量删除
     *
     * @param idList 客户端信息id列表
     * @return int 成功状态:0-失败,大于1-成功
     * @author zxiaozhou
     * @date 2022-05-02 16:12:21
     */
    int physicalDeleteBatchIds(@Param("coll") Collection<String> idList);
}
