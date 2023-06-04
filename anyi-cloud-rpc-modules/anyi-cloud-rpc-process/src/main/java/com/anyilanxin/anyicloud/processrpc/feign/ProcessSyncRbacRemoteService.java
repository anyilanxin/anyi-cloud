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
