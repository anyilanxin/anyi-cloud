

package com.anyilanxin.anyicloud.system.modules.rbac.mapper;

import com.anyilanxin.anyicloud.database.datasource.base.mapper.BaseMapper;
import com.anyilanxin.anyicloud.system.modules.rbac.controller.vo.RbacUserAgentPageVo;
import com.anyilanxin.anyicloud.system.modules.rbac.controller.vo.RbacUserAgentQueryVo;
import com.anyilanxin.anyicloud.system.modules.rbac.entity.RbacUserAgentEntity;
import com.anyilanxin.anyicloud.system.modules.rbac.service.dto.RbacUserAgentDto;
import com.anyilanxin.anyicloud.system.modules.rbac.service.dto.RbacUserAgentPageDto;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import java.util.Collection;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * 用户-代理人表(RbacUserAgent)持久层
 *
 * @author zxh
 * @copyright zhouxuanhong（https://anyilanxin.com）
 * @date 2022-05-02 16:12:20
 * @since 1.0.0
 */
@Repository
public interface RbacUserAgentMapper extends BaseMapper<RbacUserAgentEntity> {
    /**
     * 分页查询
     *
     * @param vo   查询条件
     * @param page 分页信息
     * @return IPage<RbacUserAgentPageDto> 查询结果
     * @author zxh
     * @date 2022-05-02 16:12:20
     */
    IPage<RbacUserAgentPageDto> pageByModel(Page<RbacUserAgentPageDto> page, @Param("query") RbacUserAgentPageVo vo);


    /**
     * 条件查询多条
     *
     * @param vo 查询条件
     * @return List<RbacUserAgentDto> 查询结果
     * @author zxh
     * @date 2022-05-02 16:12:20
     */
    List<RbacUserAgentDto> selectListByModel(RbacUserAgentQueryVo vo);


    /**
     * 通过代理id物理删除
     *
     * @param agentId 代理id
     * @return int 成功状态:0-失败,1-成功
     * @author zxh
     * @date 2022-05-02 16:12:20
     */
    int physicalDeleteById(@Param("id") String agentId);


    /**
     * 通过代理id物理批量删除
     *
     * @param idList 代理id列表
     * @return int 成功状态:0-失败,大于1-成功
     * @author zxh
     * @date 2022-05-02 16:12:20
     */
    int physicalDeleteBatchIds(@Param("coll") Collection<String> idList);
}
