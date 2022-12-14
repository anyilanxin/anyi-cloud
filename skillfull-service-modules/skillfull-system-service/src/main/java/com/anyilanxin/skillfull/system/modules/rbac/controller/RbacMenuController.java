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
import com.anyilanxin.skillfull.system.modules.rbac.controller.vo.RbacMenuPageVo;
import com.anyilanxin.skillfull.system.modules.rbac.controller.vo.RbacMenuVo;
import com.anyilanxin.skillfull.system.modules.rbac.service.IRbacMenuService;
import com.anyilanxin.skillfull.system.modules.rbac.service.dto.RbacMenuDto;
import com.anyilanxin.skillfull.system.modules.rbac.service.dto.RbacMenuPageDto;
import com.anyilanxin.skillfull.system.modules.rbac.service.dto.RbacMenuTreeDto;
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

/**
 * 菜单表(RbacMenu)控制层
 *
 * @author zxiaozhou
 * @copyright zxiaozhou（https://skillfull.divisu.com）
 * @date 2022-05-02 16:12:21
 * @since JDK1.8
 */
@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@Tag(name = "RbacMenu", description = "菜单相关")
@RequestMapping(value = "/rbac-menu", produces = MediaType.APPLICATION_JSON_VALUE)
public class RbacMenuController extends BaseController {
    private final IRbacMenuService service;

    @Operation(summary = "菜单表添加", tags = {"v1.0.0"}, description = "添加菜单表")
    @PostMapping(value = "/insert")
    public Result<String> insert(@RequestBody @Valid RbacMenuVo vo) {
        service.save(vo);
        return ok(I18nUtil.get("Controller.InsertSuccess"));
    }


    @Operation(summary = "通过权限id修改", tags = {"v1.0.0"}, description = "修改菜单表")
    @Parameter(in = ParameterIn.PATH, description = "权限id", name = "menuId", required = true)
    @PutMapping(value = "/update/{menuId}")
    public Result<String> update(@PathVariable(required = false) @PathNotBlankOrNull(message = "权限id不能为空") String menuId,
                                 @RequestBody @Valid RbacMenuVo vo) {
        service.updateById(menuId, vo);
        return ok(I18nUtil.get("Controller.UpdateSuccess"));
    }


    @Operation(summary = "菜单表逻辑删除", tags = {"v1.0.0"}, description = "删除菜单表")
    @Parameter(in = ParameterIn.PATH, description = "权限id", name = "menuId", required = true)
    @DeleteMapping(value = "/delete-one/{menuId}")
    public Result<String> deleteById(@PathVariable(required = false) @PathNotBlankOrNull(message = "权限id不能为空") String menuId) {
        service.deleteById(menuId);
        return ok(I18nUtil.get("Controller.DeleteSuccess"));
    }


    @Operation(summary = "菜单表逻辑批量删除", tags = {"v1.0.0"}, description = "批量删除菜单表")
    @PostMapping(value = "/delete-batch")
    public Result<String> deleteBatchByIds(@RequestBody @NotNullSize(message = "待删除权限id不能为空") List<String> menuIds) {
        service.deleteBatch(menuIds);
        return ok(I18nUtil.get("Controller.BatchDeleteSuccess"));
    }


    @Operation(summary = "通过权限id查询详情", tags = {"v1.0.0"}, description = "查询菜单表详情")
    @Parameter(in = ParameterIn.PATH, description = "权限id", name = "menuId", required = true)
    @GetMapping(value = "/select/one/{menuId}")
    public Result<RbacMenuDto> getById(@PathVariable(required = false) @PathNotBlankOrNull(message = "权限id不能为空") String menuId) {
        return ok(service.getById(menuId));
    }


    @Operation(summary = "获取菜单权限树", tags = {"v1.0.0"}, description = "获取菜单权限树")
    @Parameters({
            @Parameter(description = "类型:0-目录,1-菜单,2-按钮，多个英文逗号隔开", name = "type"),
            @Parameter(description = "系统id", name = "systemId"),
            @Parameter(description = "状态:1-有效、2-所有,默认2", name = "status")
    })
    @GetMapping(value = "/select/tree")
    public Result<List<RbacMenuTreeDto>> getMenuTree(@RequestParam(defaultValue = "0", required = false) String type,
                                                     @RequestParam(required = false) String systemId,
                                                     @RequestParam(defaultValue = "2", required = false) Integer status) {
        return ok(service.getMenuTree(type, systemId, status));
    }

    @Operation(summary = "菜单表分页查询", tags = {"v1.0.0"}, description = "分页查询菜单表")
    @PostMapping(value = "/select/page")
    public Result<PageDto<RbacMenuPageDto>> selectPage(@RequestBody RbacMenuPageVo vo) {
        return ok(service.pageByModel(vo));
    }
}
