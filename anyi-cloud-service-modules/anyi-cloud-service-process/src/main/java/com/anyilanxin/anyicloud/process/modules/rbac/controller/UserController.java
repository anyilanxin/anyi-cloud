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

package com.anyilanxin.anyicloud.process.modules.rbac.controller;

import com.anyilanxin.anyicloud.corecommon.base.AnYiResult;
import com.anyilanxin.anyicloud.corecommon.model.common.AnYiPageResult;
import com.anyilanxin.anyicloud.corecommon.utils.AnYiI18nUtil;
import com.anyilanxin.anyicloud.corecommon.validation.annotation.PathNotBlankOrNull;
import com.anyilanxin.anyicloud.coremvc.base.controller.AnYiBaseController;
import com.anyilanxin.anyicloud.process.modules.rbac.controller.vo.*;
import com.anyilanxin.anyicloud.process.modules.rbac.service.IUserService;
import com.anyilanxin.anyicloud.process.modules.rbac.service.dto.UserDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

/**
 * 用户相关
 *
 * @author zxh
 * @date 2021-11-05 17:30
 * @since 1.0.0
 */
@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@Tag(name = "RbacUser", description = "用户相关")
@RequestMapping(value = "/rbac-user", produces = MediaType.APPLICATION_JSON_VALUE)
public class UserController extends AnYiBaseController {
    private final IUserService service;

    @Operation(summary = "添加或更新用户", tags = {"v1.0.0"}, description = "添加或更新用户")
    @PostMapping(value = "/insert-or-update")
    public AnYiResult<String> saveOrUpdate(@RequestBody @Valid UserVo vo) {
        service.saveOrUpdate(vo);
        return ok("用户操作成功");
    }


    @Operation(summary = "删除或添加组关联关系", tags = {"v1.0.0"}, description = "删除或添加组关联关系")
    @PostMapping(value = "/delete-or-group")
    public AnYiResult<String> deleteOrAddGroup(@RequestBody @Valid UserGroupVo vo) {
        service.deleteOrAddGroup(vo);
        return ok("关联关系操作成功");
    }


    @Operation(summary = "删除或添加租户关联关系", tags = {"v1.0.0"}, description = "删除或添加租户关联关系")
    @PostMapping(value = "/delete-or-tenant")
    public AnYiResult<String> deleteOrAddTenant(@RequestBody @Valid UserTenantVo vo) {
        service.deleteOrAddTenant(vo);
        return ok("关联关系操作成功");
    }


    @Operation(summary = "删除用户", tags = {"v1.0.0"}, description = "删除用户")
    @Parameter(in = ParameterIn.PATH, description = "用户id", name = "userId", required = true)
    @DeleteMapping(value = "/delete-one/{userId}")
    public AnYiResult<String> deleteById(@PathVariable(required = false) @PathNotBlankOrNull(message = "用户id不能为空") String userId) {
        service.deleteUser(userId);
        return ok(AnYiI18nUtil.get("Controller.DeleteSuccess"));
    }


    @Operation(summary = "全量同步用户信息", tags = {"v1.0.0"}, description = "全量同步用户信息")
    @PostMapping(value = "/all")
    public AnYiResult<String> syncUser(@RequestBody @Valid Set<SyncUserVo> voSet) {
        service.syncUser(voSet);
        return ok("用户信息操作成功");
    }


    @Operation(summary = "查询用户详情", tags = {"v1.0.0"}, description = "查询用户详情")
    @Parameter(in = ParameterIn.PATH, description = "用户id", name = "userId", required = true)
    @GetMapping(value = "/select/one/{userId}")
    public AnYiResult<UserDto> getById(@PathVariable(required = false) @PathNotBlankOrNull(message = "用户id不能为空") String userId) {
        return ok(service.getUser(userId));
    }


    @Operation(summary = "查询用户列表", tags = {"v1.0.0"}, description = "查询用户列表")
    @PostMapping(value = "/select/list")
    public AnYiResult<List<UserDto>> getList(@RequestBody UserQueryVo vo) {
        return ok(service.getUserList(vo));
    }


    @Operation(summary = "分页查询用户", tags = {"v1.0.0"}, description = "分页查询用户")
    @PostMapping(value = "/select/page")
    public AnYiResult<AnYiPageResult<UserDto>> selectPage(@RequestBody UserQueryPageVoCamunda vo) {
        return ok(service.getUserPage(vo));
    }
}
