

package com.anyilanxin.anyicloud.system.modules.common.controller;

import com.anyilanxin.anyicloud.corecommon.base.Result;
import com.anyilanxin.anyicloud.corecommon.constant.CoreCommonCacheConstant;
import com.anyilanxin.anyicloud.corecommon.utils.I18nUtil;
import com.anyilanxin.anyicloud.corecommon.validation.annotation.NotNullSize;
import com.anyilanxin.anyicloud.corecommon.validation.annotation.PathNotBlankOrNull;
import com.anyilanxin.anyicloud.coremvc.base.controller.BaseController;
import com.anyilanxin.anyicloud.database.datasource.base.service.dto.PageDto;
import com.anyilanxin.anyicloud.system.modules.common.controller.vo.CommonCategoryPageVo;
import com.anyilanxin.anyicloud.system.modules.common.controller.vo.CommonCategoryVo;
import com.anyilanxin.anyicloud.system.modules.common.service.ICommonCategoryService;
import com.anyilanxin.anyicloud.system.modules.common.service.dto.CommonCategoryDto;
import com.anyilanxin.anyicloud.system.modules.common.service.dto.CommonCategoryPageDto;
import com.anyilanxin.anyicloud.system.modules.common.service.dto.CommonCategoryTreeDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;
import javax.validation.Valid;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * 分类字典表(CommonCategory)控制层
 *
 * @author zxh
 * @date 2021-01-07 23:39:52
 * @since 1.0.0
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
        return ok(I18nUtil.get("Controller.InsertSuccess"));
    }


    @Operation(summary = "通过分类id修改", tags = {"v1.0.0"}, description = "修改分类字典表")
    @Parameter(in = ParameterIn.PATH, description = "分类id", name = "categoryId", required = true)
    @PutMapping(value = "/update/{categoryId}")
    @CacheEvict(value = CoreCommonCacheConstant.ENGINE_DICT_CATEGORY_CACHE, allEntries = true)
    public Result<String> update(@PathVariable(required = false) @PathNotBlankOrNull(message = "分类id不能为空") String categoryId, @RequestBody @Valid CommonCategoryVo vo) {
        service.updateById(categoryId, vo);
        return ok(I18nUtil.get("Controller.UpdateSuccess"));
    }


    @Operation(summary = "分类字典表逻辑删除", tags = {"v1.0.0"}, description = "删除分类字典表")
    @Parameter(in = ParameterIn.PATH, description = "分类id", name = "categoryId", required = true)
    @DeleteMapping(value = "/delete-one/{categoryId}")
    @CacheEvict(value = CoreCommonCacheConstant.ENGINE_DICT_CATEGORY_CACHE, allEntries = true)
    public Result<String> deleteById(@PathVariable(required = false) @PathNotBlankOrNull(message = "分类id不能为空") String categoryId) {
        service.deleteById(categoryId);
        return ok(I18nUtil.get("Controller.DeleteSuccess"));
    }


    @Operation(summary = "分类字典表逻辑批量删除", tags = {"v1.0.0"}, description = "批量删除分类字典表")
    @PostMapping(value = "/delete-batch")
    @CacheEvict(value = CoreCommonCacheConstant.ENGINE_DICT_CATEGORY_CACHE, allEntries = true)
    public Result<String> deleteBatch(@RequestBody @NotNullSize(message = "待删除分类id不能为空") List<String> categoryIds) {
        service.deleteBatch(categoryIds);
        return ok(I18nUtil.get("Controller.BatchDeleteSuccess"));
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
