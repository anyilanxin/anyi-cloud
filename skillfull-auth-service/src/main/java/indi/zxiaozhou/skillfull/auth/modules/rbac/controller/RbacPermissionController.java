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

import indi.zxiaozhou.skillfull.auth.modules.rbac.controller.vo.RbacPermissionPageVo;
import indi.zxiaozhou.skillfull.auth.modules.rbac.controller.vo.RbacPermissionVo;
import indi.zxiaozhou.skillfull.auth.modules.rbac.service.IRbacPermissionService;
import indi.zxiaozhou.skillfull.auth.modules.rbac.service.dto.RbacPermissionDto;
import indi.zxiaozhou.skillfull.auth.modules.rbac.service.dto.RbacPermissionPageDto;
import indi.zxiaozhou.skillfull.auth.modules.rbac.service.dto.RbacPermissionTreeDto;
import indi.zxiaozhou.skillfull.corecommon.base.Result;
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
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 权限表(Permission)控制层
 *
 * @author zxiaozhou
 * @date 2020-09-26 17:16:14
 * @since JDK11
 */
@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@Tag(name = "Permission", description = "权限管理")
@RequestMapping(value = "/rbac-permission", produces = MediaType.APPLICATION_JSON_VALUE)
public class RbacPermissionController extends BaseController {
    private final IRbacPermissionService service;


    @Operation(summary = "权限表添加", tags = {"v1.0.0"}, description = "添加权限表")
    @PostMapping(value = "/insert")
    public Result<String> insert(@RequestBody @Valid RbacPermissionVo vo) {
        service.save(vo);
        return ok("添加成功");
    }


    @Operation(summary = "通过权限id修改", tags = {"v1.0.0"}, description = "修改权限表")
    @Parameter(in = ParameterIn.PATH, description = "权限id", name = "permissionId", required = true)
    @PutMapping(value = "/update/{permissionId}")
    public Result<String> update(@PathVariable(required = false) @PathNotBlankOrNull(message = "权限id不能为空") String permissionId, @RequestBody @Valid RbacPermissionVo vo) {
        service.updateById(permissionId, vo);
        return ok("修改成功");
    }


    @Operation(summary = "权限表逻辑删除", tags = {"v1.0.0"}, description = "删除权限表")
    @Parameter(in = ParameterIn.PATH, description = "权限id", name = "permissionId", required = true)
    @DeleteMapping(value = "/delete-one/{permissionId}")
    public Result<String> deleteById(@PathVariable(required = false) @PathNotBlankOrNull(message = "权限id不能为空") String permissionId) {
        service.deleteById(permissionId);
        return ok("删除成功");
    }


    @Operation(summary = "通过权限id查询详情", tags = {"v1.0.0"}, description = "查询权限表详情")
    @Parameter(in = ParameterIn.PATH, description = "权限id", name = "permissionId", required = true)
    @GetMapping(value = "/select/one/{permissionId}")
    public Result<RbacPermissionDto> getById(@PathVariable(required = false) @PathNotBlankOrNull(message = "权限id不能为空") String permissionId) {
        return ok(service.getById(permissionId));
    }


    @Operation(summary = "通过权限id修改权限菜单状态", tags = {"v1.0.0"}, description = "通过权限id修改权限菜单状态")
    @Parameters({
            @Parameter(description = "权限id", name = "permissionId", required = true),
            @Parameter(description = "类型:0-禁用,1-启用", name = "type", required = true)
    })
    @GetMapping(value = "/update/permission/state")
    public Result<String> updatePermissionState(@RequestParam(required = false) @NotBlank(message = "权限id不能为空") String permissionId,
                                                @RequestParam(required = false) @NotNull(message = "操作类型不能为空") Integer type) {
        service.updatePermissionState(permissionId, type);
        return ok(type == 0 ? "禁用成功" : "启用成功");
    }


    @Operation(summary = "获取菜单权限树", tags = {"v1.0.0"}, description = "获取菜单权限树")
    @Parameters({
            @Parameter(description = "类型:0-目录,1-菜单,2-按钮，多个英文逗号隔开", name = "type"),
            @Parameter(description = "系统id", name = "systemId"),
            @Parameter(description = "状态:1-有效、2-所有,默认2", name = "status")
    })
    @GetMapping(value = "/select/tree")
    public Result<List<RbacPermissionTreeDto>> getPermissionTree(@RequestParam(defaultValue = "0", required = false) String type,
                                                                 @RequestParam(required = false) String systemId,
                                                                 @RequestParam(defaultValue = "2", required = false) Integer status) {
        return ok(service.getPermissionTree(type, systemId, status));
    }


    @Operation(summary = "权限表分页查询", tags = {"v1.0.0"}, description = "分页查询权限表")
    @PostMapping(value = "/select/page")
    public Result<PageDto<RbacPermissionPageDto>> selectPage(@RequestBody RbacPermissionPageVo vo) {
        return ok(service.pageByModel(vo));
    }
}