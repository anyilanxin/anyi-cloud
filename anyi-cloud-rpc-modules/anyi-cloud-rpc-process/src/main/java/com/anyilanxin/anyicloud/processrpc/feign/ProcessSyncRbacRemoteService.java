

package com.anyilanxin.anyicloud.processrpc.feign;

import com.anyilanxin.anyicloud.corecommon.base.Result;
import com.anyilanxin.anyicloud.corecommon.constant.ServiceConstant;
import com.anyilanxin.anyicloud.corecommon.feign.FeignFallback;
import com.anyilanxin.anyicloud.corecommon.validation.annotation.PathNotBlankOrNull;
import com.anyilanxin.anyicloud.processrpc.model.*;
import com.anyilanxin.skillfull.processrpc.model.*;

import java.util.Set;
import javax.validation.Valid;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * 流程服务feign
 *
 * @author zxh
 * @date 2021-05-21 01:55
 * @since 1.0.0
 */
@FeignClient(value = ServiceConstant.PROCESS_SERVICE, path = ServiceConstant.PROCESS_SERVICE_PATH, fallbackFactory = FeignFallback.class)
public interface ProcessSyncRbacRemoteService {

    /**
     * 删除租户
     *
     * @param tenantId ${@link String}
     * @return Result<String> ${@link Result<String>}
     * @author zxh
     * @date 2021-11-08 16:13
     */
    @DeleteMapping(value = "/rbac-tenant/delete-one/{tenantId}")
    Result<String> deleteTenantById(@PathVariable(required = false) @PathNotBlankOrNull(message = "租户id不能为空") String tenantId);


    /**
     * 添加或更新用户组
     *
     * @param vo ${@link GroupRequestModel}
     * @return Result<String> ${@link Result<String>}
     * @author zxh
     * @date 2021-11-08 16:13
     */
    @PostMapping(value = "/rbac-group/insert-or-update")
    Result<String> saveOrUpdateGroup(@RequestBody @Valid GroupRequestModel vo);


    /**
     * 删除用户组
     *
     * @param groupId ${@link String}
     * @return Result<String> ${@link Result<String>}
     * @author zxh
     * @date 2021-11-08 16:13
     */
    @DeleteMapping(value = "/rbac-group/delete-one/{groupId}")
    Result<String> deleteGroupById(@PathVariable(required = false) @PathNotBlankOrNull(message = "用户组id不能为空") String groupId);


    /**
     * 全量同步用户组信息
     *
     * @param voSet ${@link Set<  SyncGroupRequestModel  >}
     * @return Result<String> ${@link Result<String>}
     * @author zxh
     * @date 2021-11-08 16:14
     */
    @PostMapping(value = "/rbac-group/all")
    Result<String> syncGroup(@RequestBody @Valid Set<SyncGroupRequestModel> voSet);


    /**
     * 添加或更新用户
     *
     * @param vo ${@link UserRequestModel}
     * @return Result<String> ${@link Result<String>}
     * @author zxh
     * @date 2021-11-08 16:13
     */
    @PostMapping(value = "/rbac-user/insert-or-update")
    Result<String> saveOrUpdateUser(@RequestBody @Valid UserRequestModel vo);


    /**
     * 删除用户
     *
     * @param userId ${@link String}
     * @return Result<String> ${@link Result<String>}
     * @author zxh
     * @date 2021-11-08 16:14
     */
    @DeleteMapping(value = "/rbac-user/delete-one/{userId}")
    Result<String> deleteUserById(@PathVariable(required = false) @PathNotBlankOrNull(message = "用户id不能为空") String userId);


    /**
     * 删除或添加组关联关系
     *
     * @param vo ${@link UserGroupRequestModel}
     * @return Result<String> ${@link Result<String>}
     * @author zxh
     * @date 2021-11-08 17:25
     */
    @PostMapping(value = "/rbac-user/delete-or-group")
    Result<String> deleteOrAddGroup(@RequestBody @Valid UserGroupRequestModel vo);


    /**
     * 全量同步用户信息
     *
     * @param voSet ${@link Set<  SyncUserRequestModel  >}
     * @return Result<String> ${@link Result<String>}
     * @author zxh
     * @date 2021-11-08 16:14
     */
    @PostMapping(value = "/rbac-user/all")
    Result<String> syncUser(@RequestBody @Valid Set<SyncUserRequestModel> voSet);
}
