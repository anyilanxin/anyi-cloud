/*
 * Copyright (c) 2021-2022 ZHOUXUANHONG(安一老厨)<anyilanxin@aliyun.com>
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
 *   1.请不要删除和修改根目录下的LICENSE文件；
 *   2.请不要删除和修改 AnYi Cloud 源码头部的版权声明；
 *   3.请保留源码和相关描述文件的项目出处，作者声明等；
 *   4.分发源码时候，请注明软件出处 https://github.com/anyilanxin/anyi-cloud；
 *   5.在修改包名，模块名称，项目代码等时，请注明软件出处 https://github.com/anyilanxin/anyi-cloud；
 *   6.本软件不允许在国家法律规定范围外使用，如出现违法行为原作者本人不承担任何法律风险；
 *   7.本软件使用的第三方依赖皆为开源软件，如需要修改第三方源码请遵循第三方源码附带开源协议；
 *   8.本软件流程部分请遵循camunda开源协议：
 *     https://docs.camunda.org/manual/latest/introduction/third-party-libraries
 *     https://github.com/camunda/camunda-bpm-platform/blob/master/LICENSE
 *   9.若您的项目无法满足以上几点，可申请商业授权。
 */


package com.anyilanxin.skillfull.system.modules.common.controller;

import com.anyilanxin.skillfull.corecommon.base.Result;
import com.anyilanxin.skillfull.corecommon.constant.CoreCommonCacheConstant;
import com.anyilanxin.skillfull.corecommon.utils.I18nUtil;
import com.anyilanxin.skillfull.corecommon.validation.annotation.NotNullSize;
import com.anyilanxin.skillfull.corecommon.validation.annotation.PathNotBlankOrNull;
import com.anyilanxin.skillfull.coremvc.base.controller.BaseController;
import com.anyilanxin.skillfull.database.datasource.base.service.dto.PageDto;
import com.anyilanxin.skillfull.system.modules.common.controller.vo.CommonDictItemPageVo;
import com.anyilanxin.skillfull.system.modules.common.controller.vo.CommonDictItemVo;
import com.anyilanxin.skillfull.system.modules.common.controller.vo.CommonDictPageVo;
import com.anyilanxin.skillfull.system.modules.common.controller.vo.CommonDictVo;
import com.anyilanxin.skillfull.system.modules.common.service.ICommonDictItemService;
import com.anyilanxin.skillfull.system.modules.common.service.ICommonDictService;
import com.anyilanxin.skillfull.system.modules.common.service.dto.CommonDictItemDto;
import com.anyilanxin.skillfull.system.modules.common.service.dto.CommonDictItemPageDto;
import com.anyilanxin.skillfull.system.modules.common.service.dto.CommonDictPageDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * 数据字典表(CommonDict)控制层
 *
 * @author zxiaozhou
 * @date 2020-11-02 09:25:14
 * @since JDK11
 */
@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@Tag(name = "CommonDict", description = "数据字典表Api接口相关")
@RequestMapping(value = "/common-dict", produces = MediaType.APPLICATION_JSON_VALUE)
public class CommonDictController extends BaseController {
    private final ICommonDictService service;
    private final ICommonDictItemService itemService;

    @Operation(
            summary = "数据字典表添加",
            tags = {"v1.0.0"},
            description = "添加数据字典表")
    @PostMapping(value = "/insert")
    public Result<String> insert(@RequestBody @Valid CommonDictVo vo) {
        service.save(vo);
        return ok(I18nUtil.get("Controller.InsertSuccess"));
    }

    @Operation(
            summary = "通过字典id修改",
            tags = {"v1.0.0"},
            description = "修改数据字典表")
    @Parameter(in = ParameterIn.PATH, description = "字典id", name = "dictId", required = true)
    @PutMapping(value = "/update/{dictId}")
    @CacheEvict(value = CoreCommonCacheConstant.ENGINE_DICT_CACHE, allEntries = true)
    public Result<String> update(
            @PathVariable(required = false) @PathNotBlankOrNull(message = "字典id不能为空") String dictId,
            @RequestBody @Valid CommonDictVo vo) {
        service.updateById(dictId, vo);
        return ok(I18nUtil.get("Controller.UpdateSuccess"));
    }

    @Operation(
            summary = "修改字段状态",
            tags = {"v1.0.0"},
            description = "修改字段状态")
    @Parameters({
            @Parameter(description = "字典id", name = "dictId", required = true),
            @Parameter(description = "操作类型:0-禁用,1-启用", name = "type", required = true)
    })
    @GetMapping(value = "/update/state/dict")
    @CacheEvict(value = CoreCommonCacheConstant.ENGINE_DICT_CACHE, allEntries = true)
    public Result<String> updateDictState(
            @RequestParam(required = false) @NotBlank(message = "字典id不能为空") String dictId,
            @RequestParam(required = false) @NotNull(message = "操作类型不能为空") Integer type) {
        service.updateDictState(dictId, type);
        return ok(type == 0 ? "禁用成功" : "启用成功");
    }

    @Operation(
            summary = "数据字典表逻辑删除",
            tags = {"v1.0.0"},
            description = "删除数据字典表")
    @Parameter(in = ParameterIn.PATH, description = "字典id", name = "dictId", required = true)
    @DeleteMapping(value = "/delete-one/{dictId}")
    @CacheEvict(value = CoreCommonCacheConstant.ENGINE_DICT_CACHE, allEntries = true)
    public Result<String> deleteById(
            @PathVariable(required = false) @PathNotBlankOrNull(message = "字典id不能为空") String dictId) {
        service.deleteById(dictId);
        return ok(I18nUtil.get("Controller.DeleteSuccess"));
    }

    @Operation(
            summary = "数据字典表逻辑批量删除",
            tags = {"v1.0.0"},
            description = "批量删除数据字典表")
    @PostMapping(value = "/delete-batch")
    @CacheEvict(value = CoreCommonCacheConstant.ENGINE_DICT_CACHE, allEntries = true)
    public Result<String> deleteBatch(
            @RequestBody @NotNullSize(message = "待删除字典id不能为空") List<String> dictIds) {
        service.deleteBatch(dictIds);
        return ok(I18nUtil.get("Controller.BatchDeleteSuccess"));
    }

    @Operation(
            summary = "通过编码获取数据字典",
            tags = {"v1.0.0"},
            description = "通过编码获取数据字典")
    @Parameter(in = ParameterIn.PATH, description = "字典编码", name = "dictCode", required = true)
    @GetMapping(value = "/select/by-code/{dictCode}")
    @Cacheable(value = CoreCommonCacheConstant.ENGINE_DICT_CACHE, key = "#dictCode")
    public Result<List<CommonDictItemDto>> getListByCode(
            @PathVariable(required = false) @PathNotBlankOrNull(message = "字典编码不能为空") String dictCode) {
        return ok(itemService.selectListByCode(dictCode));
    }

    @Operation(
            summary = "数据字典表分页查询",
            tags = {"v1.0.0"},
            description = "分页查询数据字典表")
    @PostMapping(value = "/select/page")
    public Result<PageDto<CommonDictPageDto>> selectPage(@RequestBody CommonDictPageVo vo) {
        return ok(service.pageByModel(vo));
    }

    @Operation(
            summary = "数据字典配置项表添加",
            tags = {"v1.0.0"},
            description = "添加数据字典配置项表")
    @PostMapping(value = "/insert-item")
    @CacheEvict(value = CoreCommonCacheConstant.ENGINE_DICT_CACHE, allEntries = true)
    public Result<String> insertItem(@RequestBody @Valid CommonDictItemVo vo) {
        itemService.save(vo);
        return ok("添加子项成功");
    }

    @Operation(
            summary = "通过字典项id修改",
            tags = {"v1.0.0"},
            description = "修改数据字典配置项表")
    @Parameter(in = ParameterIn.PATH, description = "字典项id", name = "itemId", required = true)
    @PutMapping(value = "/update-item/{itemId}")
    @CacheEvict(value = CoreCommonCacheConstant.ENGINE_DICT_CACHE, allEntries = true)
    public Result<String> updateItem(
            @PathVariable(required = false) @PathNotBlankOrNull(message = "字典项id不能为空") String itemId,
            @RequestBody @Valid CommonDictItemVo vo) {
        itemService.updateById(itemId, vo);
        return ok("修改子项成功");
    }

    @Operation(
            summary = "通过字典id修改",
            tags = {"v1.0.0"},
            description = "修改数据字典表")
    @Parameters({
            @Parameter(description = "字典项id", name = "itemId", required = true),
            @Parameter(description = "操作类型:0-禁用,1-启用", name = "type", required = true)
    })
    @GetMapping(value = "/update/state/item")
    @CacheEvict(value = CoreCommonCacheConstant.ENGINE_DICT_CACHE, allEntries = true)
    public Result<String> updateDictItemState(
            @RequestParam(required = false) @NotBlank(message = "字典项id不能为空") String itemId,
            @RequestParam(required = false) @NotNull(message = "操作类型不能为空") Integer type) {
        itemService.updateDictItemState(itemId, type);
        return ok(type == 0 ? "禁用成功" : "启用成功");
    }

    @Operation(
            summary = "数据字典配置项表逻辑删除",
            tags = {"v1.0.0"},
            description = "删除数据字典配置项表")
    @Parameter(in = ParameterIn.PATH, description = "字典项id", name = "itemId", required = true)
    @DeleteMapping(value = "/delete-item-one/{itemId}")
    @CacheEvict(value = CoreCommonCacheConstant.ENGINE_DICT_CACHE, allEntries = true)
    public Result<String> deleteItem(
            @PathVariable(required = false) @PathNotBlankOrNull(message = "字典项id不能为空") String itemId) {
        itemService.deleteById(itemId);
        return ok("删除子项成功");
    }

    @Operation(
            summary = "数据字典配置项表逻辑批量删除",
            tags = {"v1.0.0"},
            description = "批量删除数据字典配置项表")
    @PostMapping(value = "/delete-item-batch")
    @CacheEvict(value = CoreCommonCacheConstant.ENGINE_DICT_CACHE, allEntries = true)
    public Result<String> deleteItemBatch(
            @RequestBody @NotNullSize(message = "待删除字典项id不能为空") List<String> itemIds) {
        itemService.deleteBatch(itemIds);
        return ok("批量删除子项成功");
    }

    @Operation(
            summary = "数据字典项表分页查询",
            tags = {"v1.0.0"},
            description = "数据字典项表分页查询")
    @PostMapping(value = "/select/item-page")
    public Result<PageDto<CommonDictItemPageDto>> selectItemPage(
            @RequestBody CommonDictItemPageVo vo) {
        return ok(itemService.pageByModel(vo));
    }
}
