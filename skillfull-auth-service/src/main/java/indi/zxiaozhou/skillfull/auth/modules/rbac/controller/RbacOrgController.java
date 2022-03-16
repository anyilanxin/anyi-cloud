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

import indi.zxiaozhou.skillfull.auth.modules.rbac.controller.vo.RbacOrgPageVo;
import indi.zxiaozhou.skillfull.auth.modules.rbac.controller.vo.RbacOrgVo;
import indi.zxiaozhou.skillfull.auth.modules.rbac.service.IRbacOrgService;
import indi.zxiaozhou.skillfull.auth.modules.rbac.service.dto.RbacOrgHasChildrenDto;
import indi.zxiaozhou.skillfull.auth.modules.rbac.service.dto.RbacOrgTreeDto;
import indi.zxiaozhou.skillfull.auth.modules.rbac.service.dto.RbacOrgTreePageDto;
import indi.zxiaozhou.skillfull.corecommon.base.Result;
import indi.zxiaozhou.skillfull.corecommon.constant.CoreCommonCacheConstant;
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
 * @date 2021-01-19 13:02:50
 * @since JDK11
 */
@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@Tag(name = "RbacOrg", description = "组织机构")
@RequestMapping(value = "/rbac-org", produces = MediaType.APPLICATION_JSON_VALUE)
public class RbacOrgController extends BaseController {
    private final IRbacOrgService service;


    @Operation(summary = "组织表添加", tags = {"v1.0.0"}, description = "添加组织表")
    @PostMapping(value = "/insert")
    @CacheEvict(value = CoreCommonCacheConstant.ENGINE_ORG_CACHE, allEntries = true)
    public Result<String> insert(@RequestBody @Valid RbacOrgVo vo) {
        service.save(vo);
        return ok("添加成功");
    }


    @Operation(summary = "通过组织id修改", tags = {"v1.0.0"}, description = "修改组织表")
    @Parameter(in = ParameterIn.PATH, description = "组织id", name = "orgId", required = true)
    @PutMapping(value = "/update/{orgId}")
    @CacheEvict(value = CoreCommonCacheConstant.ENGINE_ORG_CACHE, allEntries = true)
    public Result<String> update(@PathVariable(required = false) @PathNotBlankOrNull(message = "组织id不能为空") String orgId,
                                 @RequestBody @Valid RbacOrgVo vo) {
        service.updateById(orgId, vo);
        return ok("修改成功");
    }


    @Operation(summary = "通过机构id修改机构状态", tags = {"v1.0.0"}, description = "通过机构id修改机构状态")
    @Parameters({
            @Parameter(in = ParameterIn.PATH, description = "机构id", name = "orgId", required = true),
            @Parameter(description = "类型:0-禁用,1-启用", name = "type", required = true)
    })
    @GetMapping(value = "/update/org/state")
    @CacheEvict(value = CoreCommonCacheConstant.ENGINE_ORG_CACHE, allEntries = true)
    public Result<String> updateOrgState(@RequestParam(required = false) @NotBlank(message = "机构id不能为空") String orgId,
                                         @RequestParam(required = false) @NotNull(message = "操作类型不能为空") Integer type) {
        service.updateOrgState(orgId, type);
        return ok(type == 0 ? "禁用成功" : "启用成功");
    }

    @Operation(summary = "组织表逻辑删除", tags = {"v1.0.0"}, description = "删除组织表")
    @Parameter(in = ParameterIn.PATH, description = "组织id", name = "orgId", required = true)
    @DeleteMapping(value = "/delete-one/{orgId}")
    @CacheEvict(value = CoreCommonCacheConstant.ENGINE_ORG_CACHE, allEntries = true)
    public Result<String> deleteById(@PathVariable(required = false) @PathNotBlankOrNull(message = "组织id不能为空") String orgId) {
        service.deleteById(orgId);
        return ok("删除成功");
    }


    @Operation(summary = "组织表分页查询", tags = {"v1.0.0"}, description = "分页查询组织父级")
    @GetMapping(value = "/select/org-list")
    @Parameters({
            @Parameter(description = "类型:0-所有,1-有效,默认1", name = "type"),
            @Parameter(description = "父级id", name = "parentId")
    })
    @Cacheable(value = CoreCommonCacheConstant.ENGINE_ORG_CACHE, key = "#parentId+#type")
    public Result<List<RbacOrgHasChildrenDto>> selectOrgList(@RequestParam(defaultValue = "1", required = false) Integer type,
                                                             @RequestParam(defaultValue = "", required = false) String parentId) {
        return ok(service.selectOrgList(type, parentId));
    }


    @Operation(summary = "获取组织机构树", tags = {"v1.0.0"}, description = "获取组织机构树")
    @GetMapping(value = "/select/org-tree-list")
    @Parameter(description = "类型:0-所有,1-有效,默认1", name = "type")
    @Cacheable(value = CoreCommonCacheConstant.ENGINE_ORG_CACHE, key = "#type")
    public Result<List<RbacOrgTreeDto>> selectOrgTreeList(@RequestParam(defaultValue = "1", required = false) Integer type) {
        return ok(service.selectOrgTreeList(type));
    }


    @Operation(summary = "组织表分页查询", tags = {"v1.0.0"}, description = "组织表分页查询")
    @PostMapping(value = "/select/page")
    public Result<PageDto<RbacOrgTreePageDto>> selectPage(@RequestBody RbacOrgPageVo vo) {
        return ok(service.pageByModel(vo));
    }
}