// +----------------------------------------------------------------------
// | SkillFull快速开发平台 [ SkillFull ]
// +----------------------------------------------------------------------
// | 版权所有 2020~2021 zxiaozhou
// +----------------------------------------------------------------------
// | 官方网站: https://www.divisu.com
// +----------------------------------------------------------------------
// | 作者: zxiaozhou <z7630853@163.com>
// +----------------------------------------------------------------------
package com.anyilanxin.skillfull.system.modules.rbac.controller;

import com.anyilanxin.skillfull.corecommon.base.Result;
import com.anyilanxin.skillfull.corecommon.constant.CoreCommonCacheConstant;
import com.anyilanxin.skillfull.corecommon.utils.I18nUtil;
import com.anyilanxin.skillfull.corecommon.validation.annotation.PathNotBlankOrNull;
import com.anyilanxin.skillfull.coremvc.base.controller.BaseController;
import com.anyilanxin.skillfull.database.datasource.base.service.dto.PageDto;
import com.anyilanxin.skillfull.system.modules.rbac.controller.vo.RbacOrgPageVo;
import com.anyilanxin.skillfull.system.modules.rbac.controller.vo.RbacOrgResourceApiPageVo;
import com.anyilanxin.skillfull.system.modules.rbac.controller.vo.RbacOrgVo;
import com.anyilanxin.skillfull.system.modules.rbac.service.IRbacOrgMenuService;
import com.anyilanxin.skillfull.system.modules.rbac.service.IRbacOrgService;
import com.anyilanxin.skillfull.system.modules.rbac.service.dto.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 组织表(RbacOrg)控制层
 *
 * @author zxiaozhou
 * @copyright zxiaozhou（https://divisu.com）
 * @date 2022-05-02 16:39:44
 * @since JDK1.8
 */
@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@Tag(name = "RbacOrg", description = "组织相关")
@RequestMapping(value = "/rbac-org", produces = MediaType.APPLICATION_JSON_VALUE)
public class RbacOrgController extends BaseController {
    private final IRbacOrgService service;
    private final IRbacResourceService resourceService;
    private final IRbacOrgResourceApiService apiService;
    private final IRbacOrgMenuService menuService;

    @Operation(summary = "组织表添加", tags = {"v1.0.0"}, description = "添加组织表")
    @PostMapping(value = "/insert")
    @CacheEvict(value = CoreCommonCacheConstant.ENGINE_ORG_CACHE, allEntries = true)
    public Result<String> insert(@RequestBody @Valid RbacOrgVo vo) {
        service.save(vo);
        return ok(I18nUtil.get("Controller.InsertSuccess"));
    }


//    @Operation(summary = "更新机构默认菜单权限", tags = {"v1.0.0"}, description = "更新机构默认菜单权限")
//    @PostMapping(value = "/update/org-auth")
//    public Result<String> updateOrgAuth(@RequestBody @Valid RbacDefaultAuthVo vo) {
//        service.updateOrgAuth(vo);
//        return ok("修改成功");
//    }


    @Operation(summary = "通过机构id修改机构状态", tags = {"v1.0.0"}, description = "通过机构id修改机构状态")
    @Parameters({
            @Parameter(in = ParameterIn.QUERY, description = "机构id", name = "orgId", required = true),
            @Parameter(in = ParameterIn.QUERY, description = "类型:0-禁用,1-启用", name = "type", required = true)
    })
    @GetMapping(value = "/update/org/state")
    @CacheEvict(value = CoreCommonCacheConstant.ENGINE_ORG_CACHE, allEntries = true)
    public Result<String> updateOrgState(@RequestParam(required = false) @NotBlank(message = "机构id不能为空") String orgId,
                                         @RequestParam(required = false) @NotNull(message = "操作类型不能为空") Integer type) {
        service.updateOrgState(orgId, type);
        return ok(type == 0 ? "禁用成功" : "启用成功");
    }


    @Operation(summary = "通过组织id修改", tags = {"v1.0.0"}, description = "修改组织表")
    @Parameter(in = ParameterIn.PATH, description = "组织id", name = "orgId", required = true)
    @PutMapping(value = "/update/{orgId}")
    @CacheEvict(value = CoreCommonCacheConstant.ENGINE_ORG_CACHE, allEntries = true)
    public Result<String> update(@PathVariable(required = false) @PathNotBlankOrNull(message = "组织id不能为空") String orgId,
                                 @RequestBody @Valid RbacOrgVo vo) {
        service.updateById(orgId, vo);
        return ok(I18nUtil.get("Controller.UpdateSuccess"));
    }


    @Operation(summary = "组织表逻辑删除", tags = {"v1.0.0"}, description = "删除组织表")
    @Parameter(in = ParameterIn.PATH, description = "组织id", name = "orgId", required = true)
    @DeleteMapping(value = "/delete-one/{orgId}")
    @CacheEvict(value = CoreCommonCacheConstant.ENGINE_ORG_CACHE, allEntries = true)
    public Result<String> deleteById(@PathVariable(required = false) @PathNotBlankOrNull(message = "组织id不能为空") String orgId) {
        service.deleteById(orgId);
        return ok(I18nUtil.get("Controller.DeleteSuccess"));
    }


    @Operation(summary = "查询机构树(异步)", tags = {"v1.0.0"}, description = "查询机构树(异步)")
    @PostMapping(value = "/select/async/org-tree")
    @Parameters({
            @Parameter(in = ParameterIn.QUERY, description = "上级组织id", name = "parentId"),
            @Parameter(in = ParameterIn.QUERY, description = "需要激活的组织id", name = "activateOrgId")
    })
    @Cacheable(value = CoreCommonCacheConstant.ENGINE_ORG_CACHE, key = "'asyncdeparttree:'+#parentId+#activateOrgId")
    public Result<List<RbacOrgTreeDto>> selectOrgTreeAsync(@RequestParam(required = false, defaultValue = "") String parentId,
                                                           @RequestParam(required = false, defaultValue = "") String activateOrgId) {
        return ok(service.selectOrgTreeAsync(parentId, activateOrgId));
    }


    @Operation(summary = "组织列表查询", tags = {"v1.0.0"}, description = "组织列表查询")
    @GetMapping(value = "/select/org-list")
    @Parameters({
            @Parameter(description = "类型:0-所有,1-有效,默认1", name = "type"),
            @Parameter(description = "父级id", name = "parentId")
    })
    @Cacheable(value = CoreCommonCacheConstant.ENGINE_ORG_CACHE, key = "'list:'+#parentId+#type")
    public Result<List<RbacOrgHasChildrenDto>> selectOrgList(@RequestParam(defaultValue = "1", required = false) Integer type,
                                                             @RequestParam(defaultValue = "", required = false) String parentId) {
        return ok(service.selectOrgList(type, parentId));
    }


    @Operation(summary = "获取组织机构树", tags = {"v1.0.0"}, description = "获取组织机构树")
    @GetMapping(value = "/select/org-tree-list")
    @Parameter(description = "类型:0-所有,1-有效,默认1", name = "type")
    @Cacheable(value = CoreCommonCacheConstant.ENGINE_ORG_CACHE, key = "'listtree:'+#type")
    public Result<List<RbacOrgTreeDto>> selectOrgTreeList(@RequestParam(defaultValue = "1", required = false) Integer type) {
        return ok(service.selectOrgTreeList(type));
    }


    @Operation(summary = "通过组织id查询详情", tags = {"v1.0.0"}, description = "查询组织表详情")
    @Parameter(in = ParameterIn.PATH, description = "组织id", name = "orgId", required = true)
    @GetMapping(value = "/select/one/{orgId}")
    public Result<RbacOrgDto> getById(@PathVariable(required = false) @PathNotBlankOrNull(message = "组织id不能为空") String orgId) {
        return ok(service.getById(orgId));
    }


    @Operation(summary = "组织表分页查询", tags = {"v1.0.0"}, description = "分页查询组织表")
    @PostMapping(value = "/select/page")
    public Result<PageDto<RbacOrgTreePageDto>> selectPage(@RequestBody RbacOrgPageVo vo) {
        return ok(service.pageByModel(vo));
    }


    @Operation(summary = "获取机构菜单权限树", tags = {"v1.0.0"}, description = "获取机构菜单权限树")
    @Parameters({
            @Parameter(description = "机构id", name = "orgId", required = true),
            @Parameter(description = "系统id", name = "systemId"),
            @Parameter(description = "菜单状态:0-禁用,1-启用,不传所有", name = "status")
    })
    @GetMapping(value = "/select/tree")
    public Result<List<RbacMenuTreeDto>> getMenuTree(@RequestParam(required = false) @NotBlank(message = "机构id不能为空") String orgId,
                                                     @RequestParam(required = false) String systemId,
                                                     @RequestParam(required = false) Integer status) {
        return ok(menuService.getMenuTree(orgId, systemId, status));
    }


    @Operation(summary = "通过条件查询资源表多条数据", tags = {"v1.0.0"}, description = "通过条件查询资源表")
    @GetMapping(value = "/select-resource/list")
    @Parameter(in = ParameterIn.QUERY, description = "类型:0-禁用的,1-有效的，不传所有", name = "type", required = true)
    public Result<List<RbacResourceDto>> selectResourceList(@RequestParam(required = false) Integer type) {
        return ok(resourceService.selectList(type));
    }


    @Operation(summary = "资源api表分页查询", tags = {"v1.0.0"}, description = "分页查询资源api表")
    @PostMapping(value = "/select-resource-api/page")
    public Result<PageDto<RbacOrgResourceApiPageDto>> selectResourcePage(@RequestBody RbacOrgResourceApiPageVo vo) {
        return ok(apiService.selectResourcePage(vo));
    }
}
