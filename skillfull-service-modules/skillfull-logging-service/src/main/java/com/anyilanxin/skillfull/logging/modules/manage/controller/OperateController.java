// +----------------------------------------------------------------------
// | SkillFull快速开发平台 [ SkillFull ]
// +----------------------------------------------------------------------
// | 版权所有 2020~2021 zxiaozhou
// +----------------------------------------------------------------------
// | 官方网站: https://www.divisu.com
// +----------------------------------------------------------------------
// | 作者: zxiaozhou <z7630853@163.com>
// +----------------------------------------------------------------------
package com.anyilanxin.skillfull.logging.modules.manage.controller;

import com.anyilanxin.skillfull.corecommon.base.Result;
import com.anyilanxin.skillfull.corecommon.utils.I18nUtil;
import com.anyilanxin.skillfull.corecommon.validation.annotation.NotNullSize;
import com.anyilanxin.skillfull.corecommon.validation.annotation.PathNotBlankOrNull;
import com.anyilanxin.skillfull.coremvc.base.controller.BaseController;
import com.anyilanxin.skillfull.database.datasource.base.service.dto.PageDto;
import com.anyilanxin.skillfull.logging.modules.manage.controller.vo.OperatePageVo;
import com.anyilanxin.skillfull.logging.modules.manage.service.IOperateService;
import com.anyilanxin.skillfull.logging.modules.manage.service.dto.OperateDto;
import com.anyilanxin.skillfull.logging.modules.manage.service.dto.OperatePageDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 操作日志(Operate)控制层
 *
 * @author zxiaozhou
 * @copyright zxiaozhou（https://divisu.com）
 * @date 2022-01-26 19:51:06
 * @since JDK1.8
 */
@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@Tag(name = "Operate", description = "操作日志相关")
@RequestMapping(value = "/operate", produces = MediaType.APPLICATION_JSON_VALUE)
public class OperateController extends BaseController {
    private final IOperateService service;

    @Operation(summary = "操作日志删除", tags = {"v1.0.0"}, description = "操作日志删除")
    @Parameter(in = ParameterIn.PATH, description = "操作日志id", name = "operateId", required = true)
    @DeleteMapping(value = "/delete-one/{operateId}")
    public Result<String> deleteById(@PathVariable(required = false) @PathNotBlankOrNull(message = "操作日志id不能为空") String operateId) {
        service.deleteById(operateId);
        return ok(I18nUtil.get("Controller.DeleteSuccess"));
    }


    @Operation(summary = "操作日志批量删除", tags = {"v1.0.0"}, description = "操作日志批量删除")
    @PostMapping(value = "/delete-batch")
    public Result<String> deleteBatchByIds(@RequestBody @NotNullSize(message = "待删除操作日志id不能为空") List<String> operateIds) {
        service.deleteBatch(operateIds);
        return ok(I18nUtil.get("Controller.BatchDeleteSuccess"));
    }


    @Operation(summary = "通过操作日志id查询详情", tags = {"v1.0.0"}, description = "查询操作日志详情")
    @Parameter(in = ParameterIn.PATH, description = "操作日志id", name = "operateId", required = true)
    @GetMapping(value = "/select/one/{operateId}")
    public Result<OperateDto> getById(@PathVariable(required = false) @PathNotBlankOrNull(message = "操作日志id不能为空") String operateId) {
        return ok(service.getById(operateId));
    }


    @Operation(summary = "操作日志分页查询", tags = {"v1.0.0"}, description = "分页查询操作日志")
    @PostMapping(value = "/select/page")
    public Result<PageDto<OperatePageDto>> selectPage(@RequestBody OperatePageVo vo) {
        return ok(service.pageByModel(vo));
    }
}
