// +----------------------------------------------------------------------
// | SkillFull快速开发平台 [ SkillFull ]
// +----------------------------------------------------------------------
// | 版权所有 2020~2021 zxiaozhou
// +----------------------------------------------------------------------
// | 官方网站: https://www.divisu.com
// +----------------------------------------------------------------------
// | 作者: zxiaozhou <z7630853@163.com>
// +----------------------------------------------------------------------
package indi.zxiaozhou.skillfull.auth.modules.rbac.controller;

import indi.zxiaozhou.skillfull.auth.modules.rbac.controller.vo.RbacRolePageVo;
import indi.zxiaozhou.skillfull.auth.modules.rbac.controller.vo.RbacRolePermissionVo;
import indi.zxiaozhou.skillfull.auth.modules.rbac.controller.vo.RbacRoleVo;
import indi.zxiaozhou.skillfull.auth.modules.rbac.service.IRbacCorrelateRoleService;
import indi.zxiaozhou.skillfull.auth.modules.rbac.service.IRbacRolePermissionService;
import indi.zxiaozhou.skillfull.auth.modules.rbac.service.IRbacRoleService;
import indi.zxiaozhou.skillfull.auth.modules.rbac.service.dto.*;
import indi.zxiaozhou.skillfull.corecommon.annotation.Anonymous;
import indi.zxiaozhou.skillfull.corecommon.base.Result;
import indi.zxiaozhou.skillfull.corecommon.validation.annotation.NotNullSize;
import indi.zxiaozhou.skillfull.corecommon.validation.annotation.PathNotBlankOrNull;
import indi.zxiaozhou.skillfull.coredatabase.base.service.dto.PageDto;
import indi.zxiaozhou.skillfull.coremvc.base.controller.BaseController;
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
 * 角色表(RbacRole)控制层
 *
 * @author zxiaozhou
 * @date 2020-10-08 13:44:02
 * @since JDK11
 */
@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@Tag(name = "RbacRole", description = "角色管理")
@RequestMapping(value = "/rbac-role", produces = MediaType.APPLICATION_JSON_VALUE)
public class RbacRoleController extends BaseController {
    private final IRbacRoleService service;
    private final IRbacRolePermissionService permissionService;
    private final IRbacCorrelateRoleService rbacCorrelateRoleService;


    @Operation(summary = "角色表添加", tags = {"v1.0.0"}, description = "添加角色表")
    @PostMapping(value = "/insert")
    public Result<String> insert(@RequestBody @Valid RbacRoleVo vo) {
        service.save(vo);
        return ok("添加成功");
    }


    @Operation(summary = "关联角色权限信息", tags = {"v1.0.0"}, description = "关联角色权限信息")
    @PostMapping(value = "/insert-permission")
    public Result<String> editPermission(@RequestBody @Valid RbacRolePermissionVo vo) {
        permissionService.updatePermission(vo);
        return ok("添加成功");
    }


    @Operation(summary = "获取有效的角色", tags = {"v1.0.0"}, description = "获取有效的角色")
    @GetMapping(value = "/select/role-info")
    public Result<List<RbacRoleEffectiveDto>> getEffectiveRoles() {
        return ok(service.getEffectiveRoles());
    }

    @Operation(summary = "通过角色id修改", tags = {"v1.0.0"}, description = "修改角色表")
    @Parameter(in = ParameterIn.PATH, description = "角色id", name = "roleId", required = true)
    @PutMapping(value = "/update/{roleId}")
    public Result<String> update(@PathVariable(required = false) @PathNotBlankOrNull(message = "角色id不能为空") String roleId, @RequestBody @Valid RbacRoleVo vo) {
        service.updateById(roleId, vo);
        return ok("修改成功");
    }

    @Operation(summary = "角色表逻辑删除", tags = {"v1.0.0"}, description = "删除角色表")
    @Parameter(in = ParameterIn.PATH, description = "角色id", name = "roleId", required = true)
    @DeleteMapping(value = "/delete-one/{roleId}")
    public Result<String> deleteById(@PathVariable(required = false) @PathNotBlankOrNull(message = "角色id不能为空") String roleId) {
        service.deleteById(roleId);
        return ok("删除成功");
    }


    @Operation(summary = "通过角色id查询详情", tags = {"v1.0.0"}, description = "查询角色表详情")
    @Parameter(in = ParameterIn.PATH, description = "角色id", name = "roleId", required = true)
    @GetMapping(value = "/select/one/{roleId}")
    public Result<RbacRoleDto> getById(@PathVariable(required = false) @PathNotBlankOrNull(message = "角色id不能为空") String roleId) {
        return ok(service.getById(roleId));
    }


    @Operation(summary = "通过角色id查询菜单按钮权限", tags = {"v1.0.0"}, description = "通过角色id查询菜单按钮权限")
    @Parameter(in = ParameterIn.PATH, description = "角色id", name = "roleId", required = true)
    @GetMapping(value = "/select/menu-action/{roleId}")
    public Result<Set<RbacRolePermissionInfoDto>> getMenuActionById(@PathVariable(required = false) @PathNotBlankOrNull(message = "角色id不能为空") String roleId) {
        return ok(service.getMenuActions(null, roleId));
    }


    @Operation(summary = "通过角色编码查询信息", tags = {"v1.0.0"}, description = "通过角色编码查询信息")
    @PostMapping(value = "/select/list")
    public Result<List<RbacRoleBasicDto>> getListByCodes(@RequestBody @NotNullSize(message = "角色roleCodes不能为空") List<String> roleCodes) {
        return ok(service.getListByCodes(roleCodes));
    }


    @Operation(summary = "通过角色id查询信息", tags = {"v1.0.0"}, description = "通过角色id查询信息")
    @PostMapping(value = "/select/list/role-id")
    @Anonymous
    public Result<List<RbacRoleBasicDto>> getRoleListByIds(@RequestBody @NotNullSize(message = "角色roleIds不能为空") List<String> roleIds) {
        return ok(service.getRoleListByIds(roleIds));
    }


    @Operation(summary = "角色启用或禁用", tags = {"v1.0.0"}, description = "角色启用或禁用")
    @Parameters({
            @Parameter(description = "角色id", name = "roleId"),
            @Parameter(description = "状态:0-禁用、1-启用", name = "status")
    })
    @GetMapping(value = "/update/status")
    public Result<String> updateStatus(@RequestParam(required = false) String roleId,
                                       @RequestParam(required = false) Integer status) {
        service.updateStatus(roleId, status);
        return ok(status == 0 ? "禁用成功" : "启用成功");
    }


    @Operation(summary = "角色表分页查询", tags = {"v1.0.0"}, description = "分页查询角色表")
    @PostMapping(value = "/select/page")
    public Result<PageDto<RbacRolePageDto>> selectPage(@RequestBody RbacRolePageVo vo) {
        return ok(service.pageByModel(vo));
    }
}