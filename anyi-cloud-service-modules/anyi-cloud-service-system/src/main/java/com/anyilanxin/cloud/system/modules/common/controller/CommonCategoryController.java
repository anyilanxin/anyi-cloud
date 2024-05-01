/*
 * Copyright (c) 2023-present ZHOUXUANHONG(安一老厨)<anyilanxin@aliyun.com>
 *
 * AnYi Cloud Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * AnYi Cloud 采用APACHE LICENSE 2.0开源协议，您在使用过程中，需要注意以下几点：
 *   1.请不要删除和修改根目录下的LICENSE.txt文件；
 *   2.请不要删除和修改 AnYi Cloud 源码头部的版权声明；
 *   3.请保留源码和相关描述文件的项目出处，作者声明等；
 *   4.分发源码时候，请注明软件出处 https://github.com/anyilanxin/anyi-cloud；
 *   5.在修改包名，模块名称，项目代码等时，请注明软件出处 https://github.com/anyilanxin/anyi-cloud；
 *   6.本软件不允许在国家法律规定范围外使用，如出现违法行为原作者本人不承担任何法律风险；
 *   7.进行商用时，不得基于AnYi Cloud的基础，修改包装而成一个与AnYi Cloud EE、AnYi Zeebe EE、AnYi Standalone EE功能类似的程序，进行销售或发布，参与同类软件产品市场的竞争；
 *   8.本软件使用的第三方依赖皆为开源软件，如需要修改第三方源码请遵循第三方源码附带开源协议；
 *   9.本软件流程部分请遵循camunda开源协议：
 *     https://docs.camunda.org/manual/latest/introduction/third-party-libraries
 *     https://github.com/camunda/camunda-bpm-platform/blob/master/LICENSE
 *   10.若您的项目无法满足以上几点，可申请商业授权。
 */

package com.anyilanxin.cloud.system.modules.common.controller;

import com.anyilanxin.cloud.corecommon.base.AnYiResult;
import com.anyilanxin.cloud.corecommon.constant.CoreCommonCacheConstant;
import com.anyilanxin.cloud.corecommon.model.common.AnYiPageResult;
import com.anyilanxin.cloud.corecommon.utils.AnYiI18nUtil;
import com.anyilanxin.cloud.corecommon.validation.annotation.NotNullSize;
import com.anyilanxin.cloud.corecommon.validation.annotation.PathNotBlankOrNull;
import com.anyilanxin.cloud.coremvc.base.controller.AnYiBaseController;
import com.anyilanxin.cloud.system.modules.common.controller.vo.CommonCategoryPageQuery;
import com.anyilanxin.cloud.system.modules.common.controller.vo.CommonCategoryVo;
import com.anyilanxin.cloud.system.modules.common.service.ICommonCategoryService;
import com.anyilanxin.cloud.system.modules.common.service.dto.CommonCategoryDto;
import com.anyilanxin.cloud.system.modules.common.service.dto.CommonCategoryPageDto;
import com.anyilanxin.cloud.system.modules.common.service.dto.CommonCategoryTreeDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
public class CommonCategoryController extends AnYiBaseController {
    private final ICommonCategoryService service;

    @Operation(summary = "分类字典表添加", tags = {"v1.0.0"}, description = "添加分类字典表")
    @PostMapping(value = "/insert")
    public AnYiResult<String> insert(@RequestBody @Valid CommonCategoryVo vo) {
        service.save(vo);
        return ok(AnYiI18nUtil.get("Controller.InsertSuccess"));
    }


    @Operation(summary = "通过分类id修改", tags = {"v1.0.0"}, description = "修改分类字典表")
    @Parameter(in = ParameterIn.PATH, description = "分类id", name = "categoryId", required = true)
    @PutMapping(value = "/update/{categoryId}")
    @CacheEvict(value = CoreCommonCacheConstant.ENGINE_DICT_CATEGORY_CACHE, allEntries = true)
    public AnYiResult<String> update(@PathVariable(required = false) @PathNotBlankOrNull(message = "分类id不能为空") String categoryId, @RequestBody @Valid CommonCategoryVo vo) {
        service.updateById(categoryId, vo);
        return ok(AnYiI18nUtil.get("Controller.UpdateSuccess"));
    }


    @Operation(summary = "分类字典表逻辑删除", tags = {"v1.0.0"}, description = "删除分类字典表")
    @Parameter(in = ParameterIn.PATH, description = "分类id", name = "categoryId", required = true)
    @DeleteMapping(value = "/delete-one/{categoryId}")
    @CacheEvict(value = CoreCommonCacheConstant.ENGINE_DICT_CATEGORY_CACHE, allEntries = true)
    public AnYiResult<String> deleteById(@PathVariable(required = false) @PathNotBlankOrNull(message = "分类id不能为空") String categoryId) {
        service.deleteById(categoryId);
        return ok(AnYiI18nUtil.get("Controller.DeleteSuccess"));
    }


    @Operation(summary = "分类字典表逻辑批量删除", tags = {"v1.0.0"}, description = "批量删除分类字典表")
    @PostMapping(value = "/delete-batch")
    @CacheEvict(value = CoreCommonCacheConstant.ENGINE_DICT_CATEGORY_CACHE, allEntries = true)
    public AnYiResult<String> deleteBatch(@RequestBody @NotNullSize(message = "待删除分类id不能为空") List<String> categoryIds) {
        service.deleteBatch(categoryIds);
        return ok(AnYiI18nUtil.get("Controller.BatchDeleteSuccess"));
    }


    @Operation(summary = "根据统一分类id查询分类", tags = {"v1.0.0"}, description = "根据统一分类id查询分类")
    @GetMapping(value = "/select/list/{categoryCommonCode}")
    @Cacheable(value = CoreCommonCacheConstant.ENGINE_DICT_CATEGORY_CACHE, key = "#categoryCommonCode")
    @Parameter(in = ParameterIn.PATH, description = "统一分类编码", name = "categoryCommonCode", required = true)
    public AnYiResult<List<CommonCategoryDto>> getList(@PathVariable(required = false) @PathNotBlankOrNull(message = "统一分类编码不能为空") String categoryCommonCode) {
        return ok(service.selectListByCommonCode(categoryCommonCode));
    }


    @Operation(summary = "通过统一分类id查询详情", tags = {"v1.0.0"}, description = "查询路由详情")
    @Parameter(in = ParameterIn.PATH, description = "分类id", name = "categoryId", required = true)
    @GetMapping(value = "/select/one/{categoryId}")
    public AnYiResult<CommonCategoryDto> getById(@PathVariable(required = false) @PathNotBlankOrNull(message = "路由id不能为空") String categoryId) {
        return ok(service.getById(categoryId));
    }


    @Operation(summary = " 根据统一分类id查询分类(树形)", tags = {"v1.0.0"}, description = " 根据统一分类id查询分类(树形)")
    @GetMapping(value = "/select/list-tree/{categoryCommonCode}")
    @Cacheable(value = CoreCommonCacheConstant.ENGINE_DICT_CATEGORY_CACHE, key = "#categoryCommonCode")
    @Parameter(in = ParameterIn.PATH, description = "统一分类编码", name = "categoryCommonCode", required = true)
    public AnYiResult<List<CommonCategoryTreeDto>> getTreeList(@PathVariable(required = false) @PathNotBlankOrNull(message = "统一分类编码不能为空") String categoryCommonCode) {
        return ok(service.selectTreeListByCommonCode(categoryCommonCode));
    }


    @Operation(summary = " 查询所有分类编码(树形)", tags = {"v1.0.0"}, description = " 查询所有分类编码(树形)")
    @GetMapping(value = "/select/list-tree-all")
    public AnYiResult<List<CommonCategoryTreeDto>> getAllTreeList() {
        return ok(service.selectAllTree());
    }


    @Operation(summary = "分类字典表分页查询", tags = {"v1.0.0"}, description = "分页查询分类字典表")
    @PostMapping(value = "/select/page")
    public AnYiResult<AnYiPageResult<CommonCategoryPageDto>> selectPage(@RequestBody CommonCategoryPageQuery vo) {
        return ok(service.pageByModel(vo));
    }
}
