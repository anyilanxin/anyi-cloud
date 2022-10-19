// +----------------------------------------------------------------------
// | SkillFull快速开发平台 [ SkillFull ]
// +----------------------------------------------------------------------
// | 版权所有 2020~2021 zxiaozhou
// +----------------------------------------------------------------------
// | 官方网站: https://skillfull.divisu.com
// +----------------------------------------------------------------------
// | 作者: zxiaozhou <z7630853@163.com>
// +----------------------------------------------------------------------
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
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

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


    @Operation(summary = "流程类别添加", tags = {"v1.0.0"}, description = "添加流程类别")
    @PostMapping(value = "/insert")
    public Result<String> insert(@RequestBody @Valid ProcessCategoryVo vo) {
        service.save(vo);
        return ok(I18nUtil.get("Controller.InsertSuccess"));
    }


    @Operation(summary = "通过类别id修改", tags = {"v1.0.0"}, description = "修改流程类别")
    @Parameter(in = ParameterIn.PATH, description = "类别id", name = "categoryId", required = true)
    @PutMapping(value = "/update/{categoryId}")
    public Result<String> update(@PathVariable(required = false) @PathNotBlankOrNull(message = "类别id不能为空") String categoryId,
                                 @RequestBody @Valid ProcessCategoryVo vo) {
        service.updateById(categoryId, vo);
        return ok(I18nUtil.get("Controller.UpdateSuccess"));
    }


    @Operation(summary = "流程类别逻辑删除", tags = {"v1.0.0"}, description = "删除流程类别")
    @Parameter(in = ParameterIn.PATH, description = "类别id", name = "categoryId", required = true)
    @DeleteMapping(value = "/delete-one/{categoryId}")
    public Result<String> deleteById(@PathVariable(required = false) @PathNotBlankOrNull(message = "类别id不能为空") String categoryId) {
        service.deleteById(categoryId);
        return ok(I18nUtil.get("Controller.DeleteSuccess"));
    }


    @Operation(summary = "通过条件查询流程类别多条数据", tags = {"v1.0.0"}, description = "查询有效的类表")
    @PostMapping(value = "/select/list")
    public Result<List<ProcessCategoryDto>> getList(@RequestBody ProcessCategoryQueryVo vo) {
        return ok(service.selectListByModel(vo));
    }


    @Operation(summary = "流程类别分页查询", tags = {"v1.0.0"}, description = "分页查询流程类别")
    @PostMapping(value = "/select/page")
    public Result<PageDto<ProcessCategoryPageDto>> selectPage(@RequestBody ProcessCategoryPageVo vo) {
        return ok(service.pageByModel(vo));
    }


    @Operation(summary = "获取建模流程类别下拉列表", tags = {"v1.0.0"}, description = "获取建模流程类别下拉列表")
    @GetMapping(value = "/select/category-list")
    public Result<List<SelectModel>> getModelDesignList() {
        return ok(service.getModelDesignList());
    }
}
