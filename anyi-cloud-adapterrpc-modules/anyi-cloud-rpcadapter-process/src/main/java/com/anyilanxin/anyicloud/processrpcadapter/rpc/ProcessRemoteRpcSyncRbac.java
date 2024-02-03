/*
 * Copyright (c) 2023-present ZHOUXUANHONG(安一老厨)<anyilanxin@aliyun.com>
 *
 * AnYi Cloud Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * AnYi Cloud 采用APACHE LICENSE 2.0开源协议，您在使用过程中，需要注意以下几点：
 *   1.请不要删除和修改根目录下的LICENSE.txt文件；
 *   2.请不要删除和修改 AnYi Cloud 源码头部的版权声明；
 *   3.请保留源码和相关描述文件的项目出处，作者声明等；
 *   4.分发源码时候，请注明软件出处 https://github.com/anyilanxin/anyi-cloud；
 *   5.在修改包名，模块名称，项目代码等时，请注明软件出处 https://github.com/anyilanxin/anyi-cloud；
 *   6.本软件不允许在国家法律规定范围外使用，如出现违法行为原作者本人不承担任何法律风险；
 *   7.进行商用时，不得基于AnYi Cloud的基础，修改包装而成一个与AnYi Cloud EE、AnYi Zeebe EE、AnYi Standalone EE功能类似的程序，进行销售或发布，参与同类软件产品市场的竞争；
 *   8.本软件使用的第三方依赖皆为开源软件，如需要修改第三方源码请遵循第三方源码附带开源协议；
 *   9.本软件流程部分请遵循camunda开源协议：
 *     https://docs.camunda.org/manual/latest/introduction/third-party-libraries
 *     https://github.com/camunda/camunda-bpm-platform/blob/master/LICENSE
 *   10.若您的项目无法满足以上几点，可申请商业授权。
 */

package com.anyilanxin.anyicloud.processrpcadapter.rpc;

import com.anyilanxin.anyicloud.corecommon.base.AnYiResult;
import com.anyilanxin.anyicloud.corecommon.constant.ServiceConstant;
import com.anyilanxin.anyicloud.corecommon.feign.FeignFallback;
import com.anyilanxin.anyicloud.corecommon.validation.annotation.PathNotBlankOrNull;
import com.anyilanxin.anyicloud.processadapter.model.*;
import jakarta.validation.Valid;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Set;

/**
 * 流程服务feign
 *
 * @author zxh
 * @date 2021-05-21 01:55
 * @since 1.0.0
 */
@FeignClient(value = ServiceConstant.PROCESS_SERVICE, path = ServiceConstant.PROCESS_SERVICE_PATH, fallbackFactory = FeignFallback.class)
public interface ProcessRemoteRpcSyncRbac {

    /**
     * 添加或更新租户
     *
     * @param vo ${@link TenantRequestModel}
     * @return AnYiResult<String> ${@link AnYiResult <String>}
     * @author zxh
     * @date 2021-11-08 16:12
     */
    @PostMapping(value = "/rbac-tenant/insert-or-update")
    AnYiResult<String> saveOrUpdateTenant(@RequestBody @Valid TenantRequestModel vo);


    /**
     * 删除租户
     *
     * @param tenantId ${@link String}
     * @return AnYiResult<String> ${@link AnYiResult <String>}
     * @author zxh
     * @date 2021-11-08 16:13
     */
    @DeleteMapping(value = "/rbac-tenant/delete-one/{tenantId}")
    AnYiResult<String> deleteTenantById(@PathVariable(required = false, value = "tenantId") @PathNotBlankOrNull(message = "租户id不能为空") String tenantId);


    /**
     * 全量同步租户信息
     *
     * @param voSet ${@link Set< TenantRequestModel >}
     * @return AnYiResult<String> ${@link AnYiResult <String>}
     * @author zxh
     * @date 2021-11-08 16:14
     */
    @PostMapping(value = "/rbac-tenant/all")
    AnYiResult<String> syncTenant(@RequestBody @Valid Set<TenantRequestModel> voSet);


    /**
     * 添加或更新用户组
     *
     * @param vo ${@link GroupRequestModel}
     * @return AnYiResult<String> ${@link AnYiResult <String>}
     * @author zxh
     * @date 2021-11-08 16:13
     */
    @PostMapping(value = "/rbac-group/insert-or-update")
    AnYiResult<String> saveOrUpdateGroup(@RequestBody @Valid GroupRequestModel vo);


    /**
     * 删除用户组
     *
     * @param groupId ${@link String}
     * @return AnYiResult<String> ${@link AnYiResult <String>}
     * @author zxh
     * @date 2021-11-08 16:13
     */
    @DeleteMapping(value = "/rbac-group/delete-one/{groupId}")
    AnYiResult<String> deleteGroupById(@PathVariable(required = false, value = "groupId") @PathNotBlankOrNull(message = "用户组id不能为空") String groupId);


    /**
     * 全量同步用户组信息
     *
     * @param voSet ${@link Set< TenantRequestModel >}
     * @return AnYiResult<String> ${@link AnYiResult <String>}
     * @author zxh
     * @date 2021-11-08 16:14
     */
    @PostMapping(value = "/rbac-group/all")
    AnYiResult<String> syncGroup(@RequestBody @Valid Set<SyncGroupRequestModel> voSet);


    /**
     * 删除或添加租户关联关系
     *
     * @param vo ${@link GroupTenantRequestModel}
     * @return AnYiResult<String> ${@link AnYiResult <String>}
     * @author zxh
     * @date 2021-11-08 17:26
     */
    @PostMapping(value = "/rbac-group/delete-or-tenant")
    AnYiResult<String> deleteOrAddTenantGroup(@RequestBody @Valid GroupTenantRequestModel vo);


    /**
     * 添加或更新用户
     *
     * @param vo ${@link UserRequestModel}
     * @return AnYiResult<String> ${@link AnYiResult <String>}
     * @author zxh
     * @date 2021-11-08 16:13
     */
    @PostMapping(value = "/rbac-user/insert-or-update")
    AnYiResult<String> saveOrUpdateUser(@RequestBody @Valid UserRequestModel vo);


    /**
     * 删除用户
     *
     * @param userId ${@link String}
     * @return AnYiResult<String> ${@link AnYiResult <String>}
     * @author zxh
     * @date 2021-11-08 16:14
     */
    @DeleteMapping(value = "/rbac-user/delete-one/{userId}")
    AnYiResult<String> deleteUserById(@PathVariable(required = false, value = "userId") @PathNotBlankOrNull(message = "用户id不能为空") String userId);


    /**
     * 删除或添加组关联关系
     *
     * @param vo ${@link UserGroupRequestModel}
     * @return AnYiResult<String> ${@link AnYiResult <String>}
     * @author zxh
     * @date 2021-11-08 17:25
     */
    @PostMapping(value = "/rbac-user/delete-or-group")
    AnYiResult<String> deleteOrAddGroup(@RequestBody @Valid UserGroupRequestModel vo);


    /**
     * 删除或添加租户关联关系
     *
     * @param vo ${@link UserTenantRequestModel}
     * @return AnYiResult<String> ${@link AnYiResult <String>}
     * @author zxh
     * @date 2021-11-08 17:26
     */
    @PostMapping(value = "/rbac-user/delete-or-tenant")
    AnYiResult<String> deleteOrAddTenant(@RequestBody @Valid UserTenantRequestModel vo);


    /**
     * 全量同步用户信息
     *
     * @param voSet ${@link Set<    SyncUserRequestModel    >}
     * @return AnYiResult<String> ${@link AnYiResult <String>}
     * @author zxh
     * @date 2021-11-08 16:14
     */
    @PostMapping(value = "/rbac-user/all")
    AnYiResult<String> syncUser(@RequestBody @Valid Set<SyncUserRequestModel> voSet);

}
