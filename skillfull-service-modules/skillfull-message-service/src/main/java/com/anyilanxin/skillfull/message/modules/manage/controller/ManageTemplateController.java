// +----------------------------------------------------------------------
// | SkillFull快速开发平台 [ SkillFull ]
// +----------------------------------------------------------------------
// | 版权所有 2020~2021 zxiaozhou
// +----------------------------------------------------------------------
// | 官方网站: https://www.divisu.com
// +----------------------------------------------------------------------
// | 作者: zxiaozhou <z7630853@163.com>
// +----------------------------------------------------------------------
package com.anyilanxin.skillfull.message.modules.manage.controller;

import com.anyilanxin.skillfull.corecommon.base.Result;
import com.anyilanxin.skillfull.corecommon.utils.I18nUtil;
import com.anyilanxin.skillfull.corecommon.validation.annotation.NotNullSize;
import com.anyilanxin.skillfull.corecommon.validation.annotation.PathNotBlankOrNull;
import com.anyilanxin.skillfull.coremvc.base.controller.BaseController;
import com.anyilanxin.skillfull.database.datasource.base.service.dto.PageDto;
import com.anyilanxin.skillfull.message.modules.manage.controller.vo.ManageTemplatePageVo;
import com.anyilanxin.skillfull.message.modules.manage.controller.vo.ManageTemplateVo;
import com.anyilanxin.skillfull.message.modules.manage.service.IManageTemplateService;
import com.anyilanxin.skillfull.message.modules.manage.service.dto.ManageTemplateDto;
import com.anyilanxin.skillfull.message.modules.manage.service.dto.ManageTemplatePageDto;
import io.swagger.v3.oas.annotations.Hidden;
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
 * 消息模板(ManageTemplate)控制层
 *
 * @author zxiaozhou
 * @copyright zxiaozhou（https://divisu.com）
 * @date 2022-03-29 05:23:42
 * @since JDK1.8
 */
@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@Hidden
@Tag(name = "ManageTemplate", description = "消息模板相关")
@RequestMapping(value = "/manageTemplate", produces = MediaType.APPLICATION_JSON_VALUE)
public class ManageTemplateController extends BaseController {
    private final IManageTemplateService service;

    @Operation(summary = "消息模板添加", tags = {"v1.0.0"}, description = "添加消息模板")
    @PostMapping(value = "/insert")
    public Result<String> insert(@RequestBody @Valid ManageTemplateVo vo) {
        service.save(vo);
        return ok(I18nUtil.get("Controller.InsertSuccess"));
    }


    @Operation(summary = "通过模板id修改", tags = {"v1.0.0"}, description = "修改消息模板")
    @Parameter(in = ParameterIn.PATH, description = "模板id", name = "templateId", required = true)
    @PutMapping(value = "/update/{templateId}")
    public Result<String> update(@PathVariable(required = false) @PathNotBlankOrNull(message = "模板id不能为空") String templateId,
                                 @RequestBody @Valid ManageTemplateVo vo) {
        service.updateById(templateId, vo);
        return ok(I18nUtil.get("Controller.UpdateSuccess"));
    }


    @Operation(summary = "消息模板逻辑删除", tags = {"v1.0.0"}, description = "删除消息模板")
    @Parameter(in = ParameterIn.PATH, description = "模板id", name = "templateId", required = true)
    @DeleteMapping(value = "/delete-one/{templateId}")
    public Result<String> deleteById(@PathVariable(required = false) @PathNotBlankOrNull(message = "模板id不能为空") String templateId) {
        service.deleteById(templateId);
        return ok(I18nUtil.get("Controller.DeleteSuccess"));
    }


    @Operation(summary = "消息模板逻辑批量删除", tags = {"v1.0.0"}, description = "批量删除消息模板")
    @PostMapping(value = "/delete-batch")
    public Result<String> deleteBatchByIds(@RequestBody @NotNullSize(message = "待删除模板id不能为空") List<String> templateIds) {
        service.deleteBatch(templateIds);
        return ok(I18nUtil.get("Controller.BatchDeleteSuccess"));
    }


    @Operation(summary = "通过模板id查询详情", tags = {"v1.0.0"}, description = "查询消息模板详情")
    @Parameter(in = ParameterIn.PATH, description = "模板id", name = "templateId", required = true)
    @GetMapping(value = "/select/one/{templateId}")
    public Result<ManageTemplateDto> getById(@PathVariable(required = false) @PathNotBlankOrNull(message = "模板id不能为空") String templateId) {
        return ok(service.getById(templateId));
    }


    @Operation(summary = "消息模板分页查询", tags = {"v1.0.0"}, description = "分页查询消息模板")
    @PostMapping(value = "/select/page")
    public Result<PageDto<ManageTemplatePageDto>> selectPage(@RequestBody ManageTemplatePageVo vo) {
        return ok(service.pageByModel(vo));
    }
}
