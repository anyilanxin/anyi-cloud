package com.anyilanxin.skillfull.system.modules.rbac.service;

import com.anyilanxin.skillfull.database.datasource.base.service.BaseService;
import com.anyilanxin.skillfull.system.modules.rbac.entity.RbacOrgMenuEntity;
import com.anyilanxin.skillfull.system.modules.rbac.service.dto.RbacMenuTreeDto;

import java.util.List;
import java.util.Set;

/**
 * 机构-菜单表(RbacOrgMenu)业务层接口
 *
 * @author zxiaozhou
 * @copyright zxiaozhou（https://skillfull.divisu.com）
 * @date 2022-07-02 23:01:20
 * @since JDK1.8
 */
public interface IRbacOrgMenuService extends BaseService<RbacOrgMenuEntity> {
    /**
     * 保存
     *
     * @param orgId   机构id
     * @param menuIds 机构菜单id
     * @throws RuntimeException
     * @author zxiaozhou
     * @date 2022-07-02 23:01:20
     */
    void save(String orgId, Set<String> menuIds) throws RuntimeException;


    /**
     * 通过orgId删除
     *
     * @param orgId 机构id
     * @throws RuntimeException
     * @author zxiaozhou
     * @date 2022-07-02 23:01:20
     */
    void deleteById(String orgId) throws RuntimeException;


    /**
     * 获取机构菜单权限树
     *
     * @param orgId    机构id
     * @param systemId 系统id
     * @param status   菜单状态:0-禁用,1-启用,不传所有
     * @return List<RbacMenuTreeDto>
     * @author zxiaozhou
     * @date 2020-10-07 20:23
     */
    List<RbacMenuTreeDto> getMenuTree(String orgId, String systemId, Integer status);
}
