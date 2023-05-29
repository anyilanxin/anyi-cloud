

package com.anyilanxin.anyicloud.system.modules.rbac.service;

import com.anyilanxin.anyicloud.database.datasource.base.service.BaseService;
import com.anyilanxin.anyicloud.system.modules.rbac.entity.RbacOrgMenuEntity;
import com.anyilanxin.anyicloud.system.modules.rbac.service.dto.RbacMenuTreeDto;

import java.util.List;
import java.util.Set;

/**
 * 机构-菜单表(RbacOrgMenu)业务层接口
 *
 * @author zxh
 * @copyright zhouxuanhong（https://anyilanxin.com）
 * @date 2022-07-02 23:01:20
 * @since 1.0.0
 */
public interface IRbacOrgMenuService extends BaseService<RbacOrgMenuEntity> {
    /**
     * 保存
     *
     * @param orgId   机构id
     * @param menuIds 机构菜单id
     * @throws RuntimeException
     * @author zxh
     * @date 2022-07-02 23:01:20
     */
    void save(String orgId, Set<String> menuIds) throws RuntimeException;


    /**
     * 通过orgId删除
     *
     * @param orgId 机构id
     * @throws RuntimeException
     * @author zxh
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
     * @author zxh
     * @date 2020-10-07 20:23
     */
    List<RbacMenuTreeDto> getMenuTree(String orgId, String systemId, Integer status);
}
