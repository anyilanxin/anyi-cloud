

package com.anyilanxin.anyicloud.system.modules.rbac.service;

import com.anyilanxin.anyicloud.database.datasource.base.service.BaseService;
import com.anyilanxin.anyicloud.database.datasource.base.service.dto.PageDto;
import com.anyilanxin.anyicloud.system.modules.rbac.controller.vo.RbacOrgPageVo;
import com.anyilanxin.anyicloud.system.modules.rbac.controller.vo.RbacOrgVo;
import com.anyilanxin.anyicloud.system.modules.rbac.entity.RbacOrgEntity;
import com.anyilanxin.anyicloud.system.modules.rbac.service.dto.RbacOrgDto;
import com.anyilanxin.anyicloud.system.modules.rbac.service.dto.RbacOrgHasChildrenDto;
import com.anyilanxin.anyicloud.system.modules.rbac.service.dto.RbacOrgTreeDto;
import com.anyilanxin.anyicloud.system.modules.rbac.service.dto.RbacOrgTreePageDto;

import java.util.List;

/**
 * 组织表(RbacOrg)业务层接口
 *
 * @author zxh
 * @copyright zhouxuanhong（https://anyilanxin.com）
 * @date 2022-05-02 16:39:45
 * @since 1.0.0
 */
public interface IRbacOrgService extends BaseService<RbacOrgEntity> {
    /**
     * 保存
     *
     * @param vo 组织表保存数据
     * @throws RuntimeException
     * @author zxh
     * @date 2022-05-02 16:39:45
     */
    void save(RbacOrgVo vo) throws RuntimeException;


    /**
     * 通过id更新
     *
     * @param orgId 组织id
     * @param vo    组织表更新数据
     * @throws RuntimeException
     * @author zxh
     * @date 2022-05-02 16:39:45
     */
    void updateById(String orgId, RbacOrgVo vo) throws RuntimeException;


    /**
     * 分页查询
     *
     * @param vo 分页查询条件
     * @return PageDto<RbacOrgPageDto> 分页查询结果
     * @throws RuntimeException
     * @author zxh
     * @date 2022-05-02 16:39:45
     */
    PageDto<RbacOrgTreePageDto> pageByModel(RbacOrgPageVo vo) throws RuntimeException;


    /**
     * 通过id查询详情
     *
     * @param orgId 组织id
     * @return RbacOrgDto 查询结果
     * @throws RuntimeException
     * @author zxh
     * @date 2022-05-02 16:39:45
     */
    RbacOrgDto getById(String orgId) throws RuntimeException;


    /**
     * 通过orgId删除
     *
     * @param orgId 组织id
     * @throws RuntimeException
     * @author zxh
     * @date 2022-05-02 16:39:45
     */
    void deleteById(String orgId) throws RuntimeException;


    /**
     * 查询机构树(异步)
     *
     * @param parentId      上级组织id
     * @param activateOrgId 需要激活的组织id
     * @return List<RbacOrgTreeDto>
     * @author zxh
     * @date 2022-05-02 17:11
     */
    List<RbacOrgTreeDto> selectOrgTreeAsync(String parentId, String activateOrgId);


    /**
     * 通过机构id修改机构状态
     *
     * @param orgId ${@link String} 机构id
     * @param type  ${@link Integer} 类型:0-禁用,1-启用
     * @throws RuntimeException ${@link RuntimeException}
     * @author zxh
     * @date 2021-01-31 00:55
     */
    void updateOrgState(String orgId, Integer type) throws RuntimeException;


    /**
     * 获取组织机构信息
     *
     * @param type     ${@link Integer} 类型:0-所有,1-有效,默认1
     * @param parentId ${@link String} 父级id,为空时查顶级
     * @return List<RbacOrgHasChildrenDto> ${@link List< RbacOrgHasChildrenDto >}
     * @throws RuntimeException ${@link RuntimeException}
     * @author zxh
     * @date 2021-01-30 23:34
     */
    List<RbacOrgHasChildrenDto> selectOrgList(Integer type, String parentId) throws RuntimeException;


    /**
     * 获取组织机构树
     *
     * @param type ${@link Integer} 类型:0-所有,1-有效,默认1
     * @return List<RbacOrgTreeDto> ${@link List< RbacOrgTreeDto >}
     * @throws RuntimeException ${@link RuntimeException}
     * @author zxh
     * @date 2021-02-02 16:02
     */
    List<RbacOrgTreeDto> selectOrgTreeList(Integer type) throws RuntimeException;
}
