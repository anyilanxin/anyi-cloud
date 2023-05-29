

package com.anyilanxin.anyicloud.system.modules.rbac.mapper;

import com.anyilanxin.anyicloud.database.datasource.base.mapper.BaseMapper;
import com.anyilanxin.anyicloud.system.modules.rbac.controller.vo.RbacMenuPageVo;
import com.anyilanxin.anyicloud.system.modules.rbac.entity.RbacMenuEntity;
import com.anyilanxin.anyicloud.system.modules.rbac.service.dto.RbacMenuDto;
import com.anyilanxin.anyicloud.system.modules.rbac.service.dto.RbacMenuPageDto;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import java.util.Collection;
import java.util.Set;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * 菜单表(RbacMenu)持久层
 *
 * @author zxh
 * @copyright zhouxuanhong（https://anyilanxin.com）
 * @date 2022-05-02 22:49:09
 * @since 1.0.0
 */
@Repository
public interface RbacMenuMapper extends BaseMapper<RbacMenuEntity> {
    /**
     * 分页查询
     *
     * @param vo   查询条件
     * @param page 分页信息
     * @return IPage<RbacMenuPageDto> 查询结果
     * @author zxh
     * @date 2022-05-02 22:49:09
     */
    IPage<RbacMenuPageDto> pageByModel(Page<RbacMenuPageDto> page, @Param("query") RbacMenuPageVo vo);


    /**
     * 通过权限id物理删除
     *
     * @param menuId 权限id
     * @return int 成功状态:0-失败,1-成功
     * @author zxh
     * @date 2022-05-02 22:49:09
     */
    int physicalDeleteById(@Param("id") String menuId);


    /**
     * 通过权限id物理批量删除
     *
     * @param idList 权限id列表
     * @return int 成功状态:0-失败,大于1-成功
     * @author zxh
     * @date 2022-05-02 22:49:09
     */
    int physicalDeleteBatchIds(@Param("coll") Collection<String> idList);


    /**
     * 获取所有有效的菜单信息
     *
     * @param systemCodeSet 系统编码
     * @return Set<UserRouteModel>
     * @author zxh
     * @date 2022-07-12 21:46
     */
    Set<RbacMenuDto> getAllMenu(@Param("systemCodes") Set<String> systemCodeSet);
}
