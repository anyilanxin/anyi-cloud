

package com.anyilanxin.anyicloud.system.modules.rbac.mapper;

import com.anyilanxin.anyicloud.database.datasource.base.mapper.BaseMapper;
import com.anyilanxin.anyicloud.system.modules.rbac.controller.vo.RbacSystemPageVo;
import com.anyilanxin.anyicloud.system.modules.rbac.entity.RbacSystemEntity;
import com.anyilanxin.anyicloud.system.modules.rbac.service.dto.RbacSystemPageDto;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import java.util.Collection;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * 系统(RbacSystem)持久层
 *
 * @author zxh
 * @copyright zhouxuanhong（https://anyilanxin.com）
 * @date 2022-05-02 11:46:37
 * @since 1.0.0
 */
@Repository
public interface RbacSystemMapper extends BaseMapper<RbacSystemEntity> {
    /**
     * 分页查询
     *
     * @param vo   查询条件
     * @param page 分页信息
     * @return IPage<RbacSystemPageDto> 查询结果
     * @author zxh
     * @date 2022-05-02 11:46:37
     */
    IPage<RbacSystemPageDto> pageByModel(Page<RbacSystemPageDto> page, @Param("query") RbacSystemPageVo vo);


    /**
     * 通过系统id物理删除
     *
     * @param systemId 系统id
     * @return int 成功状态:0-失败,1-成功
     * @author zxh
     * @date 2022-05-02 11:46:37
     */
    int physicalDeleteById(@Param("id") String systemId);


    /**
     * 通过系统id物理批量删除
     *
     * @param idList 系统id列表
     * @return int 成功状态:0-失败,大于1-成功
     * @author zxh
     * @date 2022-05-02 11:46:37
     */
    int physicalDeleteBatchIds(@Param("coll") Collection<String> idList);
}
