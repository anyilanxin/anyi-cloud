

package com.anyilanxin.anyicloud.process.modules.base.controller;

import com.anyilanxin.anyicloud.corecommon.base.Result;
import com.anyilanxin.anyicloud.corecommon.utils.I18nUtil;
import com.anyilanxin.anyicloud.corecommon.validation.annotation.PathNotBlankOrNull;
import com.anyilanxin.anyicloud.coremvc.base.controller.BaseController;
import com.anyilanxin.anyicloud.database.datasource.base.service.dto.PageDto;
import com.anyilanxin.anyicloud.process.modules.base.controller.vo.DeleteHistoryDesignModelVo;
import com.anyilanxin.anyicloud.process.modules.base.controller.vo.DesignModelHistoryPageVo;
import com.anyilanxin.anyicloud.process.modules.base.service.IDesignModelHistoryService;
import com.anyilanxin.anyicloud.process.modules.base.service.dto.DesignModelHistoryDto;
import com.anyilanxin.anyicloud.process.modules.base.service.dto.DesignModelHistoryPageDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;

import javax.validation.Valid;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * 流程模型历史(DesignModelHistory)控制层
 *
 * @author zxh
 * @date 2021-11-25 09:52:36
 * @since 1.0.0
 */
@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@Tag(name = "DesignModelHistory", description = "流程模型历史相关")
@RequestMapping(value = "/design-model/history", produces = MediaType.APPLICATION_JSON_VALUE)
public class DesignModelHistoryController extends BaseController {
    private final IDesignModelHistoryService service;

    @Operation(summary = "流程模型历史逻辑删除", tags = {"v1.0.0"}, description = "删除流程模型历史")
    @PostMapping(value = "/delete/by-model")
    public Result<String> deleteByModel(@RequestBody @Valid DeleteHistoryDesignModelVo vo) {
        service.deleteByModel(vo);
        return ok(I18nUtil.get("Controller.DeleteSuccess"));
    }


    @Operation(summary = "通过历史模型id查询详情", tags = {"v1.0.0"}, description = "查询流程模型历史详情")
    @Parameter(in = ParameterIn.PATH, description = "历史模型id", name = "historyModelId", required = true)
    @GetMapping(value = "/select/one/{historyModelId}")
    public Result<DesignModelHistoryDto> getById(@PathVariable(required = false) @PathNotBlankOrNull(message = "历史模型id不能为空") String historyModelId) {
        return ok(service.getById(historyModelId));
    }


    @Operation(summary = "流程模型历史分页查询", tags = {"v1.0.0"}, description = "分页查询流程模型历史")
    @PostMapping(value = "/select/page")
    public Result<PageDto<DesignModelHistoryPageDto>> selectPage(@RequestBody DesignModelHistoryPageVo vo) {
        return ok(service.pageByModel(vo));
    }
}
