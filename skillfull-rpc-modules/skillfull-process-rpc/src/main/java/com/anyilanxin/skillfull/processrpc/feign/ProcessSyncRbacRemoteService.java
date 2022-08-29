// +----------------------------------------------------------------------
// | SkillFull快速开发平台 [ SkillFull ]
// +----------------------------------------------------------------------
// | 版权所有 2020~2021 zxiaozhou
// +----------------------------------------------------------------------
// | 官方网站: https://skillfull.divisu.com
// +----------------------------------------------------------------------
// | 作者: zxiaozhou <z7630853@163.com>
// +----------------------------------------------------------------------
package com.anyilanxin.skillfull.processrpc.feign;

import com.anyilanxin.skillfull.corecommon.base.Result;
import com.anyilanxin.skillfull.corecommon.constant.ServiceConstant;
import com.anyilanxin.skillfull.corecommon.feign.FeignFallback;
import com.anyilanxin.skillfull.corecommon.validation.annotation.PathNotBlankOrNull;
import com.anyilanxin.skillfull.processrpc.model.*;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import java.util.Set;

/**
 * 流程服务feign
 *
 * @author zxiaozhou
 * @date 2021-05-21 01:55
 * @since JDK1.8
 */
@FeignClient(value = ServiceConstant.PROCESS_SERVICE, path = ServiceConstant.PROCESS_SERVICE_PATH, fallbackFactory = FeignFallback.class)
public interface ProcessSyncRbacRemoteService {

    /**
     * 添加或更新租户
     *
     * @param vo ${@link TenantRequestModel}
     * @return Result<String> ${@link Result<String>}
     * @author zxiaozhou
     * @date 2021-11-08 16:12
     */
    @PostMapping(value = "/rbac-tenant/insert-or-update")
    Result<String> saveOrUpdateTenant(@RequestBody @Valid TenantRequestModel vo);


    /**
     * 删除租户
     *
     * @param tenantId ${@link String}
     * @return Result<String> ${@link Result<String>}
     * @author zxiaozhou
     * @date 2021-11-08 16:13
     */
    @DeleteMapping(value = "/rbac-tenant/delete-one/{tenantId}")
    Result<String> deleteTenantById(@PathVariable(required = false) @PathNotBlankOrNull(message = "租户id不能为空") String tenantId);


    /**
     * 全量同步租户信息
     *
     * @param voSet ${@link Set< TenantRequestModel >}
     * @return Result<String> ${@link Result<String>}
     * @author zxiaozhou
     * @date 2021-11-08 16:14
     */
    @PostMapping(value = "/rbac-tenant/all")
    Result<String> syncTenant(@RequestBody @Valid Set<TenantRequestModel> voSet);


    /**
     * 添加或更新用户组
     *
     * @param vo ${@link GroupRequestModel}
     * @return Result<String> ${@link Result<String>}
     * @author zxiaozhou
     * @date 2021-11-08 16:13
     */
    @PostMapping(value = "/rbac-group/insert-or-update")
    Result<String> saveOrUpdateGroup(@RequestBody @Valid GroupRequestModel vo);


    /**
     * 删除用户组
     *
     * @param groupId ${@link String}
     * @return Result<String> ${@link Result<String>}
     * @author zxiaozhou
     * @date 2021-11-08 16:13
     */
    @DeleteMapping(value = "/rbac-group/delete-one/{groupId}")
    Result<String> deleteGroupById(@PathVariable(required = false) @PathNotBlankOrNull(message = "用户组id不能为空") String groupId);


    /**
     * 全量同步用户组信息
     *
     * @param voSet ${@link Set< TenantRequestModel >}
     * @return Result<String> ${@link Result<String>}
     * @author zxiaozhou
     * @date 2021-11-08 16:14
     */
    @PostMapping(value = "/rbac-group/all")
    Result<String> syncGroup(@RequestBody @Valid Set<SyncGroupRequestModel> voSet);


    /**
     * 删除或添加租户关联关系
     *
     * @param vo ${@link GroupTenantRequestModel}
     * @return Result<String> ${@link Result<String>}
     * @author zxiaozhou
     * @date 2021-11-08 17:26
     */
    @PostMapping(value = "/rbac-group/delete-or-tenant")
    Result<String> deleteOrAddTenantGroup(@RequestBody @Valid GroupTenantRequestModel vo);


    /**
     * 添加或更新用户
     *
     * @param vo ${@link UserRequestModel}
     * @return Result<String> ${@link Result<String>}
     * @author zxiaozhou
     * @date 2021-11-08 16:13
     */
    @PostMapping(value = "/rbac-user/insert-or-update")
    Result<String> saveOrUpdateUser(@RequestBody @Valid UserRequestModel vo);


    /**
     * 删除用户
     *
     * @param userId ${@link String}
     * @return Result<String> ${@link Result<String>}
     * @author zxiaozhou
     * @date 2021-11-08 16:14
     */
    @DeleteMapping(value = "/rbac-user/delete-one/{userId}")
    Result<String> deleteUserById(@PathVariable(required = false) @PathNotBlankOrNull(message = "用户id不能为空") String userId);


    /**
     * 删除或添加组关联关系
     *
     * @param vo ${@link UserGroupRequestModel}
     * @return Result<String> ${@link Result<String>}
     * @author zxiaozhou
     * @date 2021-11-08 17:25
     */
    @PostMapping(value = "/rbac-user/delete-or-group")
    Result<String> deleteOrAddGroup(@RequestBody @Valid UserGroupRequestModel vo);


    /**
     * 删除或添加租户关联关系
     *
     * @param vo ${@link UserTenantRequestModel}
     * @return Result<String> ${@link Result<String>}
     * @author zxiaozhou
     * @date 2021-11-08 17:26
     */
    @PostMapping(value = "/rbac-user/delete-or-tenant")
    Result<String> deleteOrAddTenant(@RequestBody @Valid UserTenantRequestModel vo);


    /**
     * 全量同步用户信息
     *
     * @param voSet ${@link Set<   SyncUserRequestModel   >}
     * @return Result<String> ${@link Result<String>}
     * @author zxiaozhou
     * @date 2021-11-08 16:14
     */
    @PostMapping(value = "/rbac-user/all")
    Result<String> syncUser(@RequestBody @Valid Set<SyncUserRequestModel> voSet);

}
