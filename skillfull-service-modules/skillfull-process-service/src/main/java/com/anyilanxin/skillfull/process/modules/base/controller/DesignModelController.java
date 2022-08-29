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
import com.anyilanxin.skillfull.corecommon.utils.I18nUtil;
import com.anyilanxin.skillfull.corecommon.validation.annotation.PathNotBlankOrNull;
import com.anyilanxin.skillfull.coremvc.base.controller.BaseController;
import com.anyilanxin.skillfull.database.datasource.base.service.dto.PageDto;
import com.anyilanxin.skillfull.process.modules.base.controller.vo.DeleteDesignModelVo;
import com.anyilanxin.skillfull.process.modules.base.controller.vo.DesignModelPageVo;
import com.anyilanxin.skillfull.process.modules.base.controller.vo.DesignModelVo;
import com.anyilanxin.skillfull.process.modules.base.service.IDesignModelService;
import com.anyilanxin.skillfull.process.modules.base.service.dto.DesignModelDeploymentStatiDto;
import com.anyilanxin.skillfull.process.modules.base.service.dto.DesignModelDto;
import com.anyilanxin.skillfull.process.modules.base.service.dto.DesignModelPageDto;
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

/**
 * 流程模型管理(DesignModel)控制层
 *
 * @author zxiaozhou
 * @date 2021-11-25 05:22:56
 * @since JDK1.8
 */
@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@Tag(name = "DesignModel", description = "流程模型管理")
@RequestMapping(value = "/design-model", produces = MediaType.APPLICATION_JSON_VALUE)
public class DesignModelController extends BaseController {
    private final IDesignModelService service;


    @Operation(summary = "流程模型管理添加", tags = {"v1.0.0"}, description = "添加流程模型管理")
    @PostMapping(value = "/insert")
    public Result<String> insert(@RequestBody @Valid DesignModelVo vo) {
        service.save(vo);
        return ok(I18nUtil.get("Controller.InsertSuccess"));
    }


    @Operation(summary = "通过模型id修改", tags = {"v1.0.0"}, description = "修改流程模型管理")
    @Parameter(in = ParameterIn.PATH, description = "模型id", name = "modelId", required = true)
    @PutMapping(value = "/update/{modelId}")
    public Result<String> update(@PathVariable(required = false) @PathNotBlankOrNull(message = "模型id不能为空") String modelId,
                                 @RequestBody @Valid DesignModelVo vo) {
        service.updateById(modelId, vo);
        return ok(I18nUtil.get("Controller.UpdateSuccess"));
    }


    @Operation(summary = "流程模型管理逻辑删除", tags = {"v1.0.0"}, description = "删除流程模型管理")
    @PostMapping(value = "/delete/by-model")
    public Result<String> deleteByModel(@RequestBody @Valid DeleteDesignModelVo vo) {
        service.deleteByModel(vo);
        return ok("删除模型成功");
    }


    @Operation(summary = "通过模型id查询详情", tags = {"v1.0.0"}, description = "查询流程模型管理详情")
    @Parameter(in = ParameterIn.PATH, description = "模型id", name = "modelId", required = true)
    @GetMapping(value = "/select/one/{modelId}")
    public Result<DesignModelDto> getById(@PathVariable(required = false) @PathNotBlankOrNull(message = "模型id不能为空") String modelId) {
        return ok(service.getById(modelId));
    }


    @Operation(summary = "模型部署情况统计", tags = {"v1.0.0"}, description = "模型部署情况统计")
    @GetMapping(value = "/stati")
    public Result<DesignModelDeploymentStatiDto> statistics() {
        return ok(service.statistics());
    }


    @Operation(summary = "流程模型管理分页查询", tags = {"v1.0.0"}, description = "分页查询流程模型管理")
    @PostMapping(value = "/select/page")
    public Result<PageDto<DesignModelPageDto>> selectPage(@RequestBody DesignModelPageVo vo) {
        return ok(service.pageByModel(vo));
    }
}
