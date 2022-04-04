// +----------------------------------------------------------------------
// | SkillFull快速开发平台 [ SkillFull ]
// +----------------------------------------------------------------------
// | 版权所有 2020~2021 zxiaozhou
// +----------------------------------------------------------------------
// | 官方网站: https://www.divisu.com
// +----------------------------------------------------------------------
// | 作者: zxiaozhou <z7630853@163.com>
// +----------------------------------------------------------------------
package indi.zxiaozhou.skillfull.system.modules.common.controller;

import indi.zxiaozhou.skillfull.corecommon.base.Result;
import indi.zxiaozhou.skillfull.corecommon.constant.CoreCommonCacheConstant;
import indi.zxiaozhou.skillfull.corecommon.validation.annotation.NotNullSize;
import indi.zxiaozhou.skillfull.corecommon.validation.annotation.PathNotBlankOrNull;
import indi.zxiaozhou.skillfull.coredatabase.base.service.dto.PageDto;
import indi.zxiaozhou.skillfull.coremvc.base.controller.BaseController;
import indi.zxiaozhou.skillfull.system.modules.common.controller.vo.CommonCategoryPageVo;
import indi.zxiaozhou.skillfull.system.modules.common.controller.vo.CommonCategoryVo;
import indi.zxiaozhou.skillfull.system.modules.common.service.ICommonCategoryService;
import indi.zxiaozhou.skillfull.system.modules.common.service.dto.CommonCategoryDto;
import indi.zxiaozhou.skillfull.system.modules.common.service.dto.CommonCategoryPageDto;
import indi.zxiaozhou.skillfull.system.modules.common.service.dto.CommonCategoryTreeDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
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
import java.util.List;

/**
 * 分类字典表(CommonCategory)控制层
 *
 * @author zxiaozhou
 * @date 2021-01-07 23:39:52
 * @since JDK11
 */
@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@Tag(name = "CommonCategory", description = "分类字典")
@RequestMapping(value = "/common-category", produces = MediaType.APPLICATION_JSON_VALUE)
public class CommonCategoryController extends BaseController {
    private final ICommonCategoryService service;


    @Operation(summary = "分类字典表添加", tags = {"v1.0.0"}, description = "添加分类字典表")
    @PostMapping(value = "/insert")
    public Result<String> insert(@RequestBody @Valid CommonCategoryVo vo) {
        service.save(vo);
        return ok("添加成功");
    }


    @Operation(summary = "通过分类id修改", tags = {"v1.0.0"}, description = "修改分类字典表")
    @Parameter(in = ParameterIn.PATH, description = "分类id", name = "categoryId", required = true)
    @PutMapping(value = "/update/{categoryId}")
    @CacheEvict(value = CoreCommonCacheConstant.ENGINE_DICT_CATEGORY_CACHE, allEntries = true)
    public Result<String> update(@PathVariable(required = false) @PathNotBlankOrNull(message = "分类id不能为空") String categoryId,
                                 @RequestBody @Valid CommonCategoryVo vo) {
        service.updateById(categoryId, vo);
        return ok("修改成功");
    }


    @Operation(summary = "分类字典表逻辑删除", tags = {"v1.0.0"}, description = "删除分类字典表")
    @Parameter(in = ParameterIn.PATH, description = "分类id", name = "categoryId", required = true)
    @DeleteMapping(value = "/delete-one/{categoryId}")
    @CacheEvict(value = CoreCommonCacheConstant.ENGINE_DICT_CATEGORY_CACHE, allEntries = true)
    public Result<String> deleteById(@PathVariable(required = false) @PathNotBlankOrNull(message = "分类id不能为空") String categoryId) {
        service.deleteById(categoryId);
        return ok("删除成功");
    }


    @Operation(summary = "分类字典表逻辑批量删除", tags = {"v1.0.0"}, description = "批量删除分类字典表")
    @PostMapping(value = "/delete-batch")
    @CacheEvict(value = CoreCommonCacheConstant.ENGINE_DICT_CATEGORY_CACHE, allEntries = true)
    public Result<String> deleteBatch(@RequestBody @NotNullSize(message = "待删除分类id不能为空") List<String> categoryIds) {
        service.deleteBatch(categoryIds);
        return ok("批量删除成功");
    }


    @Operation(summary = "根据统一分类id查询分类", tags = {"v1.0.0"}, description = "根据统一分类id查询分类")
    @GetMapping(value = "/select/list/{categoryCommonCode}")
    @Cacheable(value = CoreCommonCacheConstant.ENGINE_DICT_CATEGORY_CACHE, key = "#categoryCommonCode")
    @Parameter(in = ParameterIn.PATH, description = "统一分类编码", name = "categoryCommonCode", required = true)
    public Result<List<CommonCategoryDto>> getList(@PathVariable(required = false) @PathNotBlankOrNull(message = "统一分类编码不能为空") String categoryCommonCode) {
        return ok(service.selectListByCommonCode(categoryCommonCode));
    }


    @Operation(summary = "通过统一分类id查询详情", tags = {"v1.0.0"}, description = "查询路由详情")
    @Parameter(in = ParameterIn.PATH, description = "分类id", name = "categoryId", required = true)
    @GetMapping(value = "/select/one/{categoryId}")
    public Result<CommonCategoryDto> getById(@PathVariable(required = false) @PathNotBlankOrNull(message = "路由id不能为空") String categoryId) {
        return ok(service.getById(categoryId));
    }


    @Operation(summary = " 根据统一分类id查询分类(树形)", tags = {"v1.0.0"}, description = " 根据统一分类id查询分类(树形)")
    @GetMapping(value = "/select/list-tree/{categoryCommonCode}")
    @Cacheable(value = CoreCommonCacheConstant.ENGINE_DICT_CATEGORY_CACHE, key = "#categoryCommonCode")
    @Parameter(in = ParameterIn.PATH, description = "统一分类编码", name = "categoryCommonCode", required = true)
    public Result<List<CommonCategoryTreeDto>> getTreeList(@PathVariable(required = false) @PathNotBlankOrNull(message = "统一分类编码不能为空") String categoryCommonCode) {
        return ok(service.selectTreeListByCommonCode(categoryCommonCode));
    }


    @Operation(summary = " 查询所有分类编码(树形)", tags = {"v1.0.0"}, description = " 查询所有分类编码(树形)")
    @GetMapping(value = "/select/list-tree-all")
    public Result<List<CommonCategoryTreeDto>> getAllTreeList() {
        return ok(service.selectAllTree());
    }

    @Operation(summary = "分类字典表分页查询", tags = {"v1.0.0"}, description = "分页查询分类字典表")
    @PostMapping(value = "/select/page")
    public Result<PageDto<CommonCategoryPageDto>> selectPage(@RequestBody CommonCategoryPageVo vo) {
        return ok(service.pageByModel(vo));
    }
}