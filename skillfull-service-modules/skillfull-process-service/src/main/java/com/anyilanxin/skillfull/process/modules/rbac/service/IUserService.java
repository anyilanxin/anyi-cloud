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
import com.anyilanxin.skillfull.process.modules.rbac.service.dto.UserDto;

import java.util.List;
import java.util.Set;

/**
 * 用户相关
 *
 * @author zxiaozhou
 * @date 2021-11-05 17:30
 * @since JDK1.8
 */
public interface IUserService {
    /**
     * 添加或更新用户
     *
     * @param vo ${@link UserVo}
     * @author zxiaozhou
     * @date 2021-11-05 17:51
     */
    void saveOrUpdate(UserVo vo) throws RuntimeException;


    /**
     * 用户与用户组关联
     *
     * @param vo ${@link UserGroupVo}
     * @author zxiaozhou
     * @date 2021-11-07 21:09
     */
    void deleteOrAddGroup(UserGroupVo vo) throws RuntimeException;


    /**
     * 用户与租户关联
     *
     * @param vo ${@link UserTenantVo}
     * @author zxiaozhou
     * @date 2021-11-07 21:10
     */
    void deleteOrAddTenant(UserTenantVo vo) throws RuntimeException;


    /**
     * 获取用户
     *
     * @param userId ${@link String}
     * @return UserModel ${@link UserDto}
     * @author zxiaozhou
     * @date 2021-11-05 17:51
     */
    UserDto getUser(String userId) throws RuntimeException;


    /**
     * 获取用户信息
     *
     * @param vo ${@link UserQueryVo}
     * @return List<UserDto> ${@link List<UserDto>}
     * @author zxiaozhou
     * @date 2021-11-05 17:51
     */
    List<UserDto> getUserList(UserQueryVo vo) throws RuntimeException;


    /**
     * 分页获取用户信息
     *
     * @param vo ${@link UserQueryPageVoCamunda}
     * @return PageDto<UserDto>${@link PageDto<UserDto>}
     * @author zxiaozhou
     * @date 2021-11-05 17:51
     */
    PageDto<UserDto> getUserPage(UserQueryPageVoCamunda vo) throws RuntimeException;


    /**
     * 删除用户
     *
     * @param userId ${@link String}
     * @author zxiaozhou
     * @date 2021-11-05 17:51
     */
    void deleteUser(String userId) throws RuntimeException;


    /**
     * 全量同步用户信息
     *
     * @param voSet ${@link List<SyncUserVo>}
     * @author zxiaozhou
     * @date 2021-11-07 21:37
     */
    void syncUser(Set<SyncUserVo> voSet) throws RuntimeException;
}
