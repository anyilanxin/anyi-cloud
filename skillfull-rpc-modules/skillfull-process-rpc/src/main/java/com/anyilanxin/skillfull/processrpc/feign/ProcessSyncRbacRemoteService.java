/**
 * Copyright (c) 2021-2022 ZHOUXUANHONG(安一老厨)<anyilanxin@aliyun.com>
 *
 * AnYi Cloud Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * AnYi Cloud 采用APACHE LICENSE 2.0开源协议，您在使用过程中，需要注意以下几点：
 *
 * 1.请不要删除和修改根目录下的LICENSE文件。
 * 2.请不要删除和修改 AnYi Cloud 源码头部的版权声明。
 * 3.请保留源码和相关描述文件的项目出处，作者声明等。
 * 4.分发源码时候，请注明软件出处 https://github.com/anyilanxin/anyi-cloud
 * 5.在修改包名，模块名称，项目代码等时，请注明软件出处 https://github.com/anyilanxin/anyi-cloud
 * 6.若您的项目无法满足以上几点，可申请商业授权
 */
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
     * @param voSet ${@link Set< SyncGroupRequestModel >}
     * @return Result<String> ${@link Result<String>}
     * @author zxiaozhou
     * @date 2021-11-08 16:14
     */
    @PostMapping(value = "/rbac-group/all")
    Result<String> syncGroup(@RequestBody @Valid Set<SyncGroupRequestModel> voSet);


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
