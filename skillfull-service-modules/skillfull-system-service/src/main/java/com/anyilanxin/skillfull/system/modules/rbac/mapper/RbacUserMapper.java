// +----------------------------------------------------------------------
// | SkillFull快速开发平台 [ SkillFull ]
// +----------------------------------------------------------------------
// | 版权所有 2020~2021 zxiaozhou
// +----------------------------------------------------------------------
// | 官方网站: https://skillfull.divisu.com
// +----------------------------------------------------------------------
// | 作者: zxiaozhou <z7630853@163.com>
// +----------------------------------------------------------------------
package com.anyilanxin.skillfull.system.modules.rbac.mapper;

import com.anyilanxin.skillfull.database.datasource.base.mapper.BaseMapper;
import com.anyilanxin.skillfull.system.modules.rbac.controller.vo.RbacEnalbeUserPageVo;
import com.anyilanxin.skillfull.system.modules.rbac.controller.vo.RbacUserPageVo;
import com.anyilanxin.skillfull.system.modules.rbac.entity.RbacUserEntity;
import com.anyilanxin.skillfull.system.modules.rbac.service.dto.RbacUserPageDto;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;

/**
 * 用户表(RbacUser)持久层
 *
 * @author zxiaozhou
 * @copyright zxiaozhou（https://skillfull.divisu.com）
 * @date 2022-05-02 16:12:21
 * @since JDK1.8
 */
@Repository
public interface RbacUserMapper extends BaseMapper<RbacUserEntity> {
    /**
     * 分页查询
     *
     * @param vo   查询条件
     * @param page 分页信息
     * @return IPage<RbacUserPageDto> 查询结果
     * @author zxiaozhou
     * @date 2022-05-02 16:12:21
     */
    IPage<RbacUserPageDto> pageByModel(Page<RbacUserPageDto> page, @Param("query") RbacUserPageVo vo);


    /**
     * 分页查询可关联的用户信息
     *
     * @param vo   查询条件
     * @param page 分页信息
     * @return IPage<RbacUserPageDto> 查询结果
     * @author zxiaozhou
     * @date 2022-05-02 16:12:21
     */
    IPage<RbacUserPageDto> selectEnableUserPage(Page<RbacUserPageDto> page, @Param("query") RbacEnalbeUserPageVo vo);


    /**
     * 通过用户id物理删除
     *
     * @param userId 用户id
     * @return int 成功状态:0-失败,1-成功
     * @author zxiaozhou
     * @date 2022-05-02 16:12:21
     */
    int physicalDeleteById(@Param("id") String userId);


    /**
     * 通过用户id物理批量删除
     *
     * @param idList 用户id列表
     * @return int 成功状态:0-失败,大于1-成功
     * @author zxiaozhou
     * @date 2022-05-02 16:12:21
     */
    int physicalDeleteBatchIds(@Param("coll") Collection<String> idList);
}
