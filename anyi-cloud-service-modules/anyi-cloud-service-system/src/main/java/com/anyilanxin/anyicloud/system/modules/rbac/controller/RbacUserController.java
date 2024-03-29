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

package com.anyilanxin.anyicloud.system.modules.rbac.controller;

import com.anyilanxin.anyicloud.corecommon.base.AnYiResult;
import com.anyilanxin.anyicloud.corecommon.model.common.AnYiPageResult;
import com.anyilanxin.anyicloud.corecommon.utils.AnYiI18nUtil;
import com.anyilanxin.anyicloud.corecommon.validation.annotation.NotNullSize;
import com.anyilanxin.anyicloud.corecommon.validation.annotation.PathNotBlankOrNull;
import com.anyilanxin.anyicloud.coremvc.base.controller.AnYiBaseController;
import com.anyilanxin.anyicloud.system.modules.rbac.controller.vo.RbacEnalbeUserPageQuery;
import com.anyilanxin.anyicloud.system.modules.rbac.controller.vo.RbacJoinOrgVo;
import com.anyilanxin.anyicloud.system.modules.rbac.controller.vo.RbacUserPageQuery;
import com.anyilanxin.anyicloud.system.modules.rbac.controller.vo.RbacUserVo;
import com.anyilanxin.anyicloud.system.modules.rbac.service.IRbacOrgUserService;
import com.anyilanxin.anyicloud.system.modules.rbac.service.IRbacUserService;
import com.anyilanxin.anyicloud.system.modules.rbac.service.ISyncProcessService;
import com.anyilanxin.anyicloud.system.modules.rbac.service.dto.RbacProcessCommonDto;
import com.anyilanxin.anyicloud.system.modules.rbac.service.dto.RbacUserDto;
import com.anyilanxin.anyicloud.system.modules.rbac.service.dto.RbacUserPageDto;
import com.anyilanxin.anyicloud.systemadapter.model.SimpleUserModel;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 用户表(RbacUser)控制层
 *
 * @author zxh
 * @copyright zhouxuanhong（https://anyilanxin.com）
 * @date 2022-05-02 16:12:21
 * @since 1.0.0
 */
@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@Tag(name = "RbacUser", description = "用户相关")
@RequestMapping(value = "/rbac-user", produces = MediaType.APPLICATION_JSON_VALUE)
public class RbacUserController extends AnYiBaseController {
    private final IRbacUserService service;
    private final ISyncProcessService syncService;
    private final IRbacOrgUserService orgUserService;

    @Operation(summary = "用户表添加", tags = {"v1.0.0"}, description = "添加用户表")
    @PostMapping(value = "/insert")
    public AnYiResult<String> insert(@RequestBody @Valid RbacUserVo vo) {
        service.save(vo);
        return ok(AnYiI18nUtil.get("Controller.InsertSuccess"));
    }


    @Operation(summary = "通过用户id修改", tags = {"v1.0.0"}, description = "修改用户表")
    @Parameter(in = ParameterIn.PATH, description = "用户id", name = "userId", required = true)
    @PutMapping(value = "/update/{userId}")
    public AnYiResult<String> update(@PathVariable(required = false) @PathNotBlankOrNull(message = "用户id不能为空") String userId, @RequestBody @Valid RbacUserVo vo) {
        service.updateById(userId, vo);
        return ok(AnYiI18nUtil.get("Controller.UpdateSuccess"));
    }


    @Operation(summary = "通过用户d修改状态", tags = {"v1.0.0"}, description = "通过用户d修改状态")
    @Parameters({@Parameter(in = ParameterIn.QUERY, description = "用户id", name = "userId", required = true), @Parameter(in = ParameterIn.QUERY, description = "类型:1-激活,2-冻结", name = "type", required = true)})
    @GetMapping(value = "/update-user/state")
    public AnYiResult<String> updateState(@RequestParam(required = false) @NotBlank(message = "用户id不能为空") String userId, @RequestParam(required = false) @NotNull(message = "操作类型不能为空") Integer type) {
        service.updateState(userId, type);
        return ok(type == 1 ? "激活成功" : "冻结成功");
    }


    @Operation(summary = "重置密码", tags = {"v1.0.0"}, description = "重置密码")
    @Parameter(in = ParameterIn.PATH, description = "用户id", name = "userId", required = true)
    @GetMapping(value = "/reset/password/{userId}")
    public AnYiResult<String> resetPassword(@PathVariable(required = false) @PathNotBlankOrNull(message = "用户id不能为空") String userId) {
        return ok(service.resetPassword(userId), "重置密码成功");
    }


    @Operation(summary = "用户表逻辑删除", tags = {"v1.0.0"}, description = "删除用户表")
    @Parameter(in = ParameterIn.PATH, description = "用户id", name = "userId", required = true)
    @DeleteMapping(value = "/delete-one/{userId}")
    public AnYiResult<String> deleteById(@PathVariable(required = false) @PathNotBlankOrNull(message = "用户id不能为空") String userId) {
        service.deleteById(userId);
        return ok(AnYiI18nUtil.get("Controller.DeleteSuccess"));
    }


    @Operation(summary = "加入机构", tags = {"v1.0.0"}, description = "加入机构")
    @PostMapping(value = "/join-org")
    public AnYiResult<String> joinOrg(@RequestBody @Valid RbacJoinOrgVo vo) {
        orgUserService.joinOrg(vo);
        return ok("加入机构成功");
    }


    @Operation(summary = "移除机构", tags = {"v1.0.0"}, description = "移除机构")
    @Parameters({@Parameter(in = ParameterIn.QUERY, description = "用户id", name = "userId", required = true), @Parameter(in = ParameterIn.QUERY, description = "机构id", name = "orgId", required = true)})

    @GetMapping(value = "/remove-org")
    public AnYiResult<String> removeOrg(@RequestParam(required = false) @NotBlank(message = "用户id不能为空") String userId, @RequestParam(required = false) @NotBlank(message = "机构id不能为空") String orgId) {
        orgUserService.removeOrg(userId, orgId);
        return ok("移除机构成功");
    }


    @Operation(summary = "用户表逻辑批量删除", tags = {"v1.0.0"}, description = "批量删除用户表")
    @PostMapping(value = "/delete-batch")
    public AnYiResult<String> deleteBatchByIds(@RequestBody @NotNullSize(message = "待删除用户id不能为空") List<String> userIds) {
        service.deleteBatch(userIds);
        return ok(AnYiI18nUtil.get("Controller.BatchDeleteSuccess"));
    }


    @Operation(summary = "通过用户id查询详情", tags = {"v1.0.0"}, description = "查询用户表详情")
    @Parameters({@Parameter(in = ParameterIn.QUERY, description = "用户id", name = "userId", required = true), @Parameter(in = ParameterIn.QUERY, description = "机构id", name = "orgId")})
    @GetMapping(value = "/select/one")
    public AnYiResult<RbacUserDto> getById(@RequestParam(required = false) @NotBlank(message = "用户id不能为空") String userId, @RequestParam(required = false) String orgId) {
        return ok(service.getById(userId, orgId));
    }


    @Operation(summary = "用户表分页查询", tags = {"v1.0.0"}, description = "分页查询用户表")
    @PostMapping(value = "/select/page")
    public AnYiResult<AnYiPageResult<RbacUserPageDto>> selectPage(@RequestBody RbacUserPageQuery vo) {
        return ok(service.pageByModel(vo));
    }


    @Operation(summary = "通过用户ids查询用户信息", tags = {"v1.0.0"}, description = "通过用户ids查询用户信息")
    @PostMapping(value = "/select/list-by-ids")
    public AnYiResult<List<SimpleUserModel>> selectUserByIds(@RequestBody List<String> userIds) {
        return ok(service.selectUserByIds(userIds));
    }


    @Operation(summary = "通过ids查询详细信息(流程引擎建模使用)", tags = {"v1.0.0"}, description = "通过ids查询详细信息(流程引擎建模使用)")
    @PostMapping(value = "/select-process/list-by-ids")
    public AnYiResult<List<RbacProcessCommonDto>> selectProcessDesignerByIds(@RequestBody List<String> ids) {
        return ok(service.selectProcessDesignerByIds(ids));
    }


    @Operation(summary = "获取所有用户信息", tags = {"v1.0.0"}, description = "获取所有用户信息")
    @GetMapping(value = "/select/list")
    @PreAuthorize("isAnonymous()||permitAll()")
    public AnYiResult<List<SimpleUserModel>> getUserList() {
        return ok(service.getUserList());
    }


    @Operation(summary = "分页查询可关联的用户信息", tags = {"v1.0.0"}, description = "分页查询可关联的用户信息")
    @PostMapping(value = "/select/enable-user-page")
    public AnYiResult<AnYiPageResult<RbacUserPageDto>> selectEnableUserPage(@RequestBody @Valid RbacEnalbeUserPageQuery vo) {
        return ok(service.selectEnableUserPage(vo));
    }


    @Operation(summary = "通过用户id查询用户信息", tags = {"v1.0.0"}, description = "通过用户id查询用户信息")
    @Parameter(in = ParameterIn.PATH, description = "用户id", name = "userId", required = true)
    @GetMapping("/select/one/{userId}")
    public AnYiResult<SimpleUserModel> getUserById(@PathVariable(value = "userId", required = false) @PathNotBlankOrNull(message = "用户id不能为空") String userId) {
        return ok(service.getUserById(userId));
    }

    // @Operation(summary = "全量同步流程引擎", tags = {"v1.0.0"}, description = "全量同步流程引擎")
    // @GetMapping(value = "/sync/process")
    // public AnYiResult<String> syncProcess() {
    // syncService.syncRoleAll();
    // syncService.syncUserAll();
    // return ok("同步成功");
    // }
}
