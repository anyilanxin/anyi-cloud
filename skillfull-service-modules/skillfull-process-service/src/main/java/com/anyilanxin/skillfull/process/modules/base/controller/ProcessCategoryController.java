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

package com.anyilanxin.skillfull.process.modules.base.controller;

import com.anyilanxin.skillfull.corecommon.base.Result;
import com.anyilanxin.skillfull.corecommon.model.common.SelectModel;
import com.anyilanxin.skillfull.corecommon.utils.I18nUtil;
import com.anyilanxin.skillfull.corecommon.validation.annotation.PathNotBlankOrNull;
import com.anyilanxin.skillfull.coremvc.base.controller.BaseController;
import com.anyilanxin.skillfull.database.datasource.base.service.dto.PageDto;
import com.anyilanxin.skillfull.process.modules.base.controller.vo.ProcessCategoryPageVo;
import com.anyilanxin.skillfull.process.modules.base.controller.vo.ProcessCategoryQueryVo;
import com.anyilanxin.skillfull.process.modules.base.controller.vo.ProcessCategoryVo;
import com.anyilanxin.skillfull.process.modules.base.service.IProcessCategoryService;
import com.anyilanxin.skillfull.process.modules.base.service.dto.ProcessCategoryDto;
import com.anyilanxin.skillfull.process.modules.base.service.dto.ProcessCategoryPageDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * 流程类别(ProcessCategory)控制层
 *
 * @author zxiaozhou
 * @date 2021-11-19 10:47:01
 * @since JDK1.8
 */
@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@Tag(name = "ProcessCategory", description = "流程类别相关")
@RequestMapping(value = "/process-category", produces = MediaType.APPLICATION_JSON_VALUE)
public class ProcessCategoryController extends BaseController {
    private final IProcessCategoryService service;

    @Operation(
            summary = "流程类别添加",
            tags = {"v1.0.0"},
            description = "添加流程类别")
    @PostMapping(value = "/insert")
    public Result<String> insert(@RequestBody @Valid ProcessCategoryVo vo) {
        service.save(vo);
        return ok(I18nUtil.get("Controller.InsertSuccess"));
    }

    @Operation(
            summary = "通过类别id修改",
            tags = {"v1.0.0"},
            description = "修改流程类别")
    @Parameter(in = ParameterIn.PATH, description = "类别id", name = "categoryId", required = true)
    @PutMapping(value = "/update/{categoryId}")
    public Result<String> update(
            @PathVariable(required = false) @PathNotBlankOrNull(message = "类别id不能为空")
                    String categoryId,
            @RequestBody @Valid ProcessCategoryVo vo) {
        service.updateById(categoryId, vo);
        return ok(I18nUtil.get("Controller.UpdateSuccess"));
    }

    @Operation(
            summary = "流程类别逻辑删除",
            tags = {"v1.0.0"},
            description = "删除流程类别")
    @Parameter(in = ParameterIn.PATH, description = "类别id", name = "categoryId", required = true)
    @DeleteMapping(value = "/delete-one/{categoryId}")
    public Result<String> deleteById(
            @PathVariable(required = false) @PathNotBlankOrNull(message = "类别id不能为空")
                    String categoryId) {
        service.deleteById(categoryId);
        return ok(I18nUtil.get("Controller.DeleteSuccess"));
    }

    @Operation(
            summary = "通过条件查询流程类别多条数据",
            tags = {"v1.0.0"},
            description = "查询有效的类表")
    @PostMapping(value = "/select/list")
    public Result<List<ProcessCategoryDto>> getList(@RequestBody ProcessCategoryQueryVo vo) {
        return ok(service.selectListByModel(vo));
    }

    @Operation(
            summary = "流程类别分页查询",
            tags = {"v1.0.0"},
            description = "分页查询流程类别")
    @PostMapping(value = "/select/page")
    public Result<PageDto<ProcessCategoryPageDto>> selectPage(
            @RequestBody ProcessCategoryPageVo vo) {
        return ok(service.pageByModel(vo));
    }

    @Operation(
            summary = "获取建模流程类别下拉列表",
            tags = {"v1.0.0"},
            description = "获取建模流程类别下拉列表")
    @GetMapping(value = "/select/category-list")
    public Result<List<SelectModel>> getModelDesignList() {
        return ok(service.getModelDesignList());
    }
}
