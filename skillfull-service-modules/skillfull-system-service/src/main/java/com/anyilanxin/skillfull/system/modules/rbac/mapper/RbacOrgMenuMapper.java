package com.anyilanxin.skillfull.system.modules.rbac.mapper;

import com.anyilanxin.skillfull.database.datasource.base.mapper.BaseMapper;
import com.anyilanxin.skillfull.system.modules.rbac.entity.RbacMenuEntity;
import com.anyilanxin.skillfull.system.modules.rbac.entity.RbacOrgMenuEntity;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 * 机构-菜单表(RbacOrgMenu)持久层
 *
 * @author zxiaozhou
 * @copyright zxiaozhou（https://skillfull.divisu.com）
 * @date 2022-07-02 23:01:20
 * @since JDK1.8
 */
@Repository
public interface RbacOrgMenuMapper extends BaseMapper<RbacOrgMenuEntity> {
    /**
     * 通过资源orgId物理删除
     *
     * @param orgId 机构id
     * @return int 成功状态:0-失败,1-成功
     * @author zxiaozhou
     * @date 2022-07-02 23:01:20
     */
    int physicalDeleteById(@Param("orgId") String orgId);


    /**
     * 删除不在不存在当前列表的资源
     *
     * @param orgId  机构id
     * @param idList 资源api角色id列表
     * @return int 成功状态:0-失败,大于1-成功
     * @author zxiaozhou
     * @date 2022-07-02 23:01:20
     */
    int physicalDeleteNotInIds(@Param("orgId") String orgId, @Param("coll") Collection<String> idList);


    /**
     * 查询机构功能权限
     *
     * @param orgId
     * @return Set<String>
     * @author zxiaozhou
     * @date 2022-07-06 23:59
     */
    Set<String> selectMenuListById(@Param("orgId") String orgId);


    /**
     * 查询机构菜单树
     *
     * @param orgId    机构id
     * @param systemId 系统id
     * @param status   菜单状态:0-禁用,1-启用,不传所有
     * @return List<RbacMenuEntity>
     * @author zxiaozhou
     * @date 2022-07-08 08:16
     */
    List<RbacMenuEntity> selectByParams(@Param("orgId") String orgId,
                                        @Param("systemId") String systemId,
                                        @Param("status") Integer status);
}
