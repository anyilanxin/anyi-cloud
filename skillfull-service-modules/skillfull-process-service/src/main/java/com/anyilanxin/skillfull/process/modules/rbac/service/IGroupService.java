// +----------------------------------------------------------------------
// | SkillFull快速开发平台 [ SkillFull ]
// +----------------------------------------------------------------------
// | 版权所有 2020~2021 zxiaozhou
// +----------------------------------------------------------------------
// | 官方网站: https://www.divisu.com
// +----------------------------------------------------------------------
// | 作者: zxiaozhou <z7630853@163.com>
// +----------------------------------------------------------------------
package com.anyilanxin.skillfull.process.modules.rbac.service;

import com.anyilanxin.skillfull.database.datasource.base.service.dto.PageDto;
import com.anyilanxin.skillfull.process.modules.rbac.controller.vo.*;
import com.anyilanxin.skillfull.process.modules.rbac.service.dto.GroupDto;

import java.util.List;
import java.util.Set;

/**
 * 用户组相关
 *
 * @author zxiaozhou
 * @date 2021-11-05 17:30
 * @since JDK1.8
 */
public interface IGroupService {
    /**
     * 保存或更新用户组
     *
     * @param vo ${@link GroupVo}
     * @author zxiaozhou
     * @date 2021-11-05 17:51
     */
    void saveOrUpdate(GroupVo vo) throws RuntimeException;


    /**
     * 用户组与租户关联
     *
     * @param vo ${@link GroupTenantVo}
     * @author zxiaozhou
     * @date 2021-11-07 21:10
     */
    void deleteOrAddTenant(GroupTenantVo vo) throws RuntimeException;


    /**
     * 获取用户组
     *
     * @param groupId ${@link String}
     * @return GroupDto ${@link GroupDto}
     * @author zxiaozhou
     * @date 2021-11-05 17:51
     */
    GroupDto getGroup(String groupId) throws RuntimeException;


    /**
     * 获取用户组信息
     *
     * @param model ${@link GroupQueryVo}
     * @return List<GroupDto> ${@link List <GroupDto>}
     * @author zxiaozhou
     * @date 2021-11-05 17:51
     */
    List<GroupDto> getGroupList(GroupQueryVo model) throws RuntimeException;


    /**
     * 分页获取用户组信息
     *
     * @param vo ${@link GroupQueryPageVoCamunda}
     * @return PageDto<GroupDto>${@link PageDto <GroupDto>}
     * @author zxiaozhou
     * @date 2021-11-05 17:51
     */
    PageDto<GroupDto> getGroupPage(GroupQueryPageVoCamunda vo) throws RuntimeException;


    /**
     * 删除用户组
     *
     * @param groupId ${@link String}
     * @author zxiaozhou
     * @date 2021-11-05 17:51
     */
    void deleteGroup(String groupId) throws RuntimeException;


    /**
     * 全量同步用户组信息
     *
     * @param voSet ${@link List< SyncGroupVo >}
     * @author zxiaozhou
     * @date 2021-11-07 21:37
     */
    void syncGroup(Set<SyncGroupVo> voSet) throws RuntimeException;
}
