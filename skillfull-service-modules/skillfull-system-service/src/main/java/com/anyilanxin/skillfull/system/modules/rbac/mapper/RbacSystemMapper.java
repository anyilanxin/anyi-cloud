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
import com.anyilanxin.skillfull.system.modules.rbac.controller.vo.RbacSystemPageVo;
import com.anyilanxin.skillfull.system.modules.rbac.entity.RbacSystemEntity;
import com.anyilanxin.skillfull.system.modules.rbac.service.dto.RbacSystemPageDto;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;

/**
 * 系统(RbacSystem)持久层
 *
 * @author zxiaozhou
 * @copyright zxiaozhou（https://divisu.com）
 * @date 2022-05-02 11:46:37
 * @since JDK1.8
 */
@Repository
public interface RbacSystemMapper extends BaseMapper<RbacSystemEntity> {
    /**
     * 分页查询
     *
     * @param vo   查询条件
     * @param page 分页信息
     * @return IPage<RbacSystemPageDto> 查询结果
     * @author zxiaozhou
     * @date 2022-05-02 11:46:37
     */
    IPage<RbacSystemPageDto> pageByModel(Page<RbacSystemPageDto> page, @Param("query") RbacSystemPageVo vo);


    /**
     * 通过系统id物理删除
     *
     * @param systemId 系统id
     * @return int 成功状态:0-失败,1-成功
     * @author zxiaozhou
     * @date 2022-05-02 11:46:37
     */
    int physicalDeleteById(@Param("id") String systemId);


    /**
     * 通过系统id物理批量删除
     *
     * @param idList 系统id列表
     * @return int 成功状态:0-失败,大于1-成功
     * @author zxiaozhou
     * @date 2022-05-02 11:46:37
     */
    int physicalDeleteBatchIds(@Param("coll") Collection<String> idList);
}
