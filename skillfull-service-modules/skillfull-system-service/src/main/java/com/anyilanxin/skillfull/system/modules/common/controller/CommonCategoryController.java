/**
 * Copyright (c) 2021-2022 ZHOUXUANHONG(安一老厨)<anyilanxin@aliyun.com>
 *
 * AnYi Cloud Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * AnYi Cloud 采用APACHE LICENSE 2.0开源协议，您在使用过程中，需要注意以下几点：
 *
 * 1.请不要删除和修改根目录下的LICENSE文件。
 * 2.请不要删除和修改 AnYi Cloud 源码头部的版权声明。
 * 3.请保留源码和相关描述文件的项目出处，作者声明等。
 * 4.分发源码时候，请注明软件出处 https://github.com/anyilanxin/anyi-cloud
 * 5.在修改包名，模块名称，项目代码等时，请注明软件出处 https://github.com/anyilanxin/anyi-cloud
 * 6.若您的项目无法满足以上几点，可申请商业授权
 */
package com.anyilanxin.skillfull.system.modules.common.controller;

import com.anyilanxin.skillfull.corecommon.base.Result;
import com.anyilanxin.skillfull.corecommon.constant.CoreCommonCacheConstant;
import com.anyilanxin.skillfull.corecommon.utils.I18nUtil;
import com.anyilanxin.skillfull.corecommon.validation.annotation.NotNullSize;
import com.anyilanxin.skillfull.corecommon.validation.annotation.PathNotBlankOrNull;
import com.anyilanxin.skillfull.coremvc.base.controller.BaseController;
import com.anyilanxin.skillfull.database.datasource.base.service.dto.PageDto;
import com.anyilanxin.skillfull.system.modules.common.controller.vo.CommonCategoryPageVo;
import com.anyilanxin.skillfull.system.modules.common.controller.vo.CommonCategoryVo;
import com.anyilanxin.skillfull.system.modules.common.service.ICommonCategoryService;
import com.anyilanxin.skillfull.system.modules.common.service.dto.CommonCategoryDto;
import com.anyilanxin.skillfull.system.modules.common.service.dto.CommonCategoryPageDto;
import com.anyilanxin.skillfull.system.modules.common.service.dto.CommonCategoryTreeDto;
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
        return ok(I18nUtil.get("Controller.InsertSuccess"));
    }


    @Operation(summary = "通过分类id修改", tags = {"v1.0.0"}, description = "修改分类字典表")
    @Parameter(in = ParameterIn.PATH, description = "分类id", name = "categoryId", required = true)
    @PutMapping(value = "/update/{categoryId}")
    @CacheEvict(value = CoreCommonCacheConstant.ENGINE_DICT_CATEGORY_CACHE, allEntries = true)
    public Result<String> update(@PathVariable(required = false) @PathNotBlankOrNull(message = "分类id不能为空") String categoryId,
                                 @RequestBody @Valid CommonCategoryVo vo) {
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
