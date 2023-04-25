/**
* Copyright (c) 2021-2022 ZHOUXUANHONG(安一老厨)<anyilanxin@aliyun.com>
*
* <p>AnYi Cloud Licensed under the Apache License, Version 2.0 (the "License"); you may not use
* this file except in compliance with the License. You may obtain a copy of the License at
*
* <p>http://www.apache.org/licenses/LICENSE-2.0
*
* <p>Unless required by applicable law or agreed to in writing, software distributed under the
* License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
* express or implied. See the License for the specific language governing permissions and
* limitations under the License.
*
* <p>AnYi Cloud 采用APACHE LICENSE 2.0开源协议，您在使用过程中，需要注意以下几点：
*
* <p>1.请不要删除和修改根目录下的LICENSE文件。 2.请不要删除和修改 AnYi Cloud 源码头部的版权声明。 3.请保留源码和相关描述文件的项目出处，作者声明等。
* 4.分发源码时候，请注明软件出处 https://github.com/anyilanxin/anyi-cloud 5.在修改包名，模块名称，项目代码等时，请注明软件出处
* https://github.com/anyilanxin/anyi-cloud 6.若您的项目无法满足以上几点，可申请商业授权
*/
package com.anyilanxin.skillfull.system.modules.rbac.controller;

import com.anyilanxin.skillfull.corecommon.base.Result;
import com.anyilanxin.skillfull.corecommon.utils.I18nUtil;
import com.anyilanxin.skillfull.corecommon.validation.annotation.NotNullSize;
import com.anyilanxin.skillfull.corecommon.validation.annotation.PathNotBlankOrNull;
import com.anyilanxin.skillfull.coremvc.base.controller.BaseController;
import com.anyilanxin.skillfull.database.datasource.base.service.dto.PageDto;
import com.anyilanxin.skillfull.system.modules.rbac.controller.vo.RbacOrgRoleAuthVo;
import com.anyilanxin.skillfull.system.modules.rbac.controller.vo.RbacOrgRolePageVo;
import com.anyilanxin.skillfull.system.modules.rbac.controller.vo.RbacOrgRoleVo;
import com.anyilanxin.skillfull.system.modules.rbac.service.IRbacOrgRoleService;
import com.anyilanxin.skillfull.system.modules.rbac.service.dto.RbacOrgRoleDto;
import com.anyilanxin.skillfull.system.modules.rbac.service.dto.RbacOrgRoleMenuButtonDto;
import com.anyilanxin.skillfull.system.modules.rbac.service.dto.RbacOrgRolePageDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import java.util.Set;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
* 机构角色表(RbacOrgRole)控制层
*
* @author zxiaozhou
* @copyright zxiaozhou（https://skillfull.divisu.com）
* @date 2022-07-05 00:22:57
* @since JDK1.8
*/
@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@Tag(name = "RbacOrgRole", description = "机构角色表相关")
@RequestMapping(value = "/rbac-org-role", produces = MediaType.APPLICATION_JSON_VALUE)
public class RbacOrgRoleController extends BaseController {
    private final IRbacOrgRoleService service;

    @Operation(
            summary = "机构角色表添加",
            tags = {"v1.0.0"},
            description = "添加机构角色表")
    @PostMapping(value = "/insert")
    public Result<String> insert(@RequestBody @Valid RbacOrgRoleVo vo) {
        service.save(vo);
        return ok(I18nUtil.get("Controller.InsertSuccess"));
    }

    @Operation(
            summary = "通过机构角色id修改",
            tags = {"v1.0.0"},
            description = "修改机构角色表")
    @Parameter(in = ParameterIn.PATH, description = "机构角色id", name = "orgRoleId", required = true)
    @PutMapping(value = "/update/{orgRoleId}")
    public Result<String> update(
            @PathVariable(required = false) @PathNotBlankOrNull(message = "机构角色id不能为空") String orgRoleId,
            @RequestBody @Valid RbacOrgRoleVo vo) {
        service.updateById(orgRoleId, vo);
        return ok(I18nUtil.get("Controller.UpdateSuccess"));
    }

    @Operation(
            summary = "更新或添加角色权限",
            tags = {"v1.0.0"},
            description = "更新或添加角色权限")
    @Parameter(in = ParameterIn.PATH, description = "角色id", name = "orgRoleId", required = true)
    @PutMapping(value = "/update-auth/{orgRoleId}")
    public Result<String> updateAuth(
            @PathVariable(required = false) @PathNotBlankOrNull(message = "角色id不能为空") String orgRoleId,
            @RequestBody @Valid RbacOrgRoleAuthVo vo) {
        service.updateAuth(orgRoleId, vo);
        return ok("设置权限成功");
    }

    @Operation(
            summary = "机构角色表逻辑删除",
            tags = {"v1.0.0"},
            description = "删除机构角色表")
    @Parameter(in = ParameterIn.PATH, description = "机构角色id", name = "orgRoleId", required = true)
    @DeleteMapping(value = "/delete-one/{orgRoleId}")
    public Result<String> deleteById(
            @PathVariable(required = false) @PathNotBlankOrNull(message = "机构角色id不能为空")
                    String orgRoleId) {
        service.deleteById(orgRoleId);
        return ok(I18nUtil.get("Controller.DeleteSuccess"));
    }

    @Operation(
            summary = "机构角色表逻辑批量删除",
            tags = {"v1.0.0"},
            description = "批量删除机构角色表")
    @PostMapping(value = "/delete-batch")
    public Result<String> deleteBatchByIds(
            @RequestBody @NotNullSize(message = "待删除机构角色id不能为空") List<String> orgRoleIds) {
        service.deleteBatch(orgRoleIds);
        return ok(I18nUtil.get("Controller.BatchDeleteSuccess"));
    }

    @Operation(
            summary = "通过机构角色id查询详情",
            tags = {"v1.0.0"},
            description = "查询机构角色表详情")
    @Parameter(in = ParameterIn.PATH, description = "机构角色id", name = "orgRoleId", required = true)
    @GetMapping(value = "/select/one/{orgRoleId}")
    public Result<RbacOrgRoleDto> getById(
            @PathVariable(required = false) @PathNotBlankOrNull(message = "机构角色id不能为空")
                    String orgRoleId) {
        return ok(service.getById(orgRoleId));
    }

    @Operation(
            summary = "角色启用或禁用",
            tags = {"v1.0.0"},
            description = "角色启用或禁用")
    @Parameters({
        @Parameter(description = "角色id", name = "orgRoleId"),
        @Parameter(description = "状态:0-禁用、1-启用", name = "status")
    })
    @GetMapping(value = "/update/status")
    public Result<String> updateStatus(
            @RequestParam(required = false) String orgRoleId,
            @RequestParam(required = false) Integer status) {
        service.updateStatus(orgRoleId, status);
        return ok(status == 0 ? "禁用成功" : "启用成功");
    }

    @Operation(
            summary = "机构角色表分页查询",
            tags = {"v1.0.0"},
            description = "分页查询机构角色表")
    @PostMapping(value = "/select/page")
    public Result<PageDto<RbacOrgRolePageDto>> selectPage(@RequestBody RbacOrgRolePageVo vo) {
        return ok(service.pageByModel(vo));
    }

    @Operation(
            summary = "通过角色id查询菜单按钮权限",
            tags = {"v1.0.0"},
            description = "通过角色id查询菜单按钮权限")
    @Parameter(in = ParameterIn.PATH, description = "角色id", name = "orgRoleId", required = true)
    @GetMapping(value = "/select/menu-action/{orgRoleId}")
    public Result<Set<RbacOrgRoleMenuButtonDto>> getMenuActionById(
            @PathVariable(required = false) @PathNotBlankOrNull(message = "角色id不能为空") String orgRoleId) {
        return ok(service.getMenuActions(orgRoleId));
    }
}
