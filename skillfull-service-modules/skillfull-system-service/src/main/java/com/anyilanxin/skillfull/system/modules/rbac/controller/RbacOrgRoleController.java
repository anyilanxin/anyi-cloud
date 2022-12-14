// +----------------------------------------------------------------------
// | SkillFull快速开发平台 [ SkillFull ]
// +----------------------------------------------------------------------
// | 版权所有 2020~2021 zxiaozhou
// +----------------------------------------------------------------------
// | 官方网站: https://skillfull.divisu.com
// +----------------------------------------------------------------------
// | 作者: zxiaozhou <z7630853@163.com>
// +----------------------------------------------------------------------
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
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Set;

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

    @Operation(summary = "机构角色表添加", tags = {"v1.0.0"}, description = "添加机构角色表")
    @PostMapping(value = "/insert")
    public Result<String> insert(@RequestBody @Valid RbacOrgRoleVo vo) {
        service.save(vo);
        return ok(I18nUtil.get("Controller.InsertSuccess"));
    }


    @Operation(summary = "通过机构角色id修改", tags = {"v1.0.0"}, description = "修改机构角色表")
    @Parameter(in = ParameterIn.PATH, description = "机构角色id", name = "orgRoleId", required = true)
    @PutMapping(value = "/update/{orgRoleId}")
    public Result<String> update(@PathVariable(required = false) @PathNotBlankOrNull(message = "机构角色id不能为空") String orgRoleId,
                                 @RequestBody @Valid RbacOrgRoleVo vo) {
        service.updateById(orgRoleId, vo);
        return ok(I18nUtil.get("Controller.UpdateSuccess"));
    }


    @Operation(summary = "更新或添加角色权限", tags = {"v1.0.0"}, description = "更新或添加角色权限")
    @Parameter(in = ParameterIn.PATH, description = "角色id", name = "orgRoleId", required = true)
    @PutMapping(value = "/update-auth/{orgRoleId}")
    public Result<String> updateAuth(@PathVariable(required = false) @PathNotBlankOrNull(message = "角色id不能为空") String orgRoleId,
                                     @RequestBody @Valid RbacOrgRoleAuthVo vo) {
        service.updateAuth(orgRoleId, vo);
        return ok("设置权限成功");
    }


    @Operation(summary = "机构角色表逻辑删除", tags = {"v1.0.0"}, description = "删除机构角色表")
    @Parameter(in = ParameterIn.PATH, description = "机构角色id", name = "orgRoleId", required = true)
    @DeleteMapping(value = "/delete-one/{orgRoleId}")
    public Result<String> deleteById(@PathVariable(required = false) @PathNotBlankOrNull(message = "机构角色id不能为空") String orgRoleId) {
        service.deleteById(orgRoleId);
        return ok(I18nUtil.get("Controller.DeleteSuccess"));
    }


    @Operation(summary = "机构角色表逻辑批量删除", tags = {"v1.0.0"}, description = "批量删除机构角色表")
    @PostMapping(value = "/delete-batch")
    public Result<String> deleteBatchByIds(@RequestBody @NotNullSize(message = "待删除机构角色id不能为空") List<String> orgRoleIds) {
        service.deleteBatch(orgRoleIds);
        return ok(I18nUtil.get("Controller.BatchDeleteSuccess"));
    }


    @Operation(summary = "通过机构角色id查询详情", tags = {"v1.0.0"}, description = "查询机构角色表详情")
    @Parameter(in = ParameterIn.PATH, description = "机构角色id", name = "orgRoleId", required = true)
    @GetMapping(value = "/select/one/{orgRoleId}")
    public Result<RbacOrgRoleDto> getById(@PathVariable(required = false) @PathNotBlankOrNull(message = "机构角色id不能为空") String orgRoleId) {
        return ok(service.getById(orgRoleId));
    }


    @Operation(summary = "角色启用或禁用", tags = {"v1.0.0"}, description = "角色启用或禁用")
    @Parameters({
            @Parameter(description = "角色id", name = "orgRoleId"),
            @Parameter(description = "状态:0-禁用、1-启用", name = "status")
    })
    @GetMapping(value = "/update/status")
    public Result<String> updateStatus(@RequestParam(required = false) String orgRoleId,
                                       @RequestParam(required = false) Integer status) {
        service.updateStatus(orgRoleId, status);
        return ok(status == 0 ? "禁用成功" : "启用成功");
    }


    @Operation(summary = "机构角色表分页查询", tags = {"v1.0.0"}, description = "分页查询机构角色表")
    @PostMapping(value = "/select/page")
    public Result<PageDto<RbacOrgRolePageDto>> selectPage(@RequestBody RbacOrgRolePageVo vo) {
        return ok(service.pageByModel(vo));
    }


    @Operation(summary = "通过角色id查询菜单按钮权限", tags = {"v1.0.0"}, description = "通过角色id查询菜单按钮权限")
    @Parameter(in = ParameterIn.PATH, description = "角色id", name = "orgRoleId", required = true)
    @GetMapping(value = "/select/menu-action/{orgRoleId}")
    public Result<Set<RbacOrgRoleMenuButtonDto>> getMenuActionById(@PathVariable(required = false) @PathNotBlankOrNull(message = "角色id不能为空") String orgRoleId) {
        return ok(service.getMenuActions(orgRoleId));
    }
}
