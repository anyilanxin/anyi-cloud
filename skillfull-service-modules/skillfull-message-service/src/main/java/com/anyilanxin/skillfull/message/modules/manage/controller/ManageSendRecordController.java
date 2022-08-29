// +----------------------------------------------------------------------
// | SkillFull快速开发平台 [ SkillFull ]
// +----------------------------------------------------------------------
// | 版权所有 2020~2021 zxiaozhou
// +----------------------------------------------------------------------
// | 官方网站: https://skillfull.divisu.com
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
import com.anyilanxin.skillfull.message.modules.manage.controller.vo.ManageSendRecordPageVo;
import com.anyilanxin.skillfull.message.modules.manage.service.IManageSendRecordService;
import com.anyilanxin.skillfull.message.modules.manage.service.dto.ManageSendRecordDto;
import com.anyilanxin.skillfull.message.modules.manage.service.dto.ManageSendRecordPageDto;
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

import java.util.List;

/**
 * 消息发送记录表(ManageSendRecord)控制层
 *
 * @author zxiaozhou
 * @copyright zxiaozhou（https://skillfull.divisu.com）
 * @date 2022-03-29 05:23:40
 * @since JDK1.8
 */
@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@Hidden
@Tag(name = "ManageSendRecord", description = "消息发送记录表相关")
@RequestMapping(value = "/manageSendRecord", produces = MediaType.APPLICATION_JSON_VALUE)
public class ManageSendRecordController extends BaseController {
    private final IManageSendRecordService service;


    @Operation(summary = "消息发送记录表逻辑删除", tags = {"v1.0.0"}, description = "删除消息发送记录表")
    @Parameter(in = ParameterIn.PATH, description = "消息id", name = "sendRecordId", required = true)
    @DeleteMapping(value = "/delete-one/{sendRecordId}")
    public Result<String> deleteById(@PathVariable(required = false) @PathNotBlankOrNull(message = "消息id不能为空") String sendRecordId) {
        service.deleteById(sendRecordId);
        return ok(I18nUtil.get("Controller.DeleteSuccess"));
    }


    @Operation(summary = "消息发送记录表逻辑批量删除", tags = {"v1.0.0"}, description = "批量删除消息发送记录表")
    @PostMapping(value = "/delete-batch")
    public Result<String> deleteBatchByIds(@RequestBody @NotNullSize(message = "待删除消息id不能为空") List<String> sendRecordIds) {
        service.deleteBatch(sendRecordIds);
        return ok(I18nUtil.get("Controller.BatchDeleteSuccess"));
    }


    @Operation(summary = "通过消息id查询详情", tags = {"v1.0.0"}, description = "查询消息发送记录表详情")
    @Parameter(in = ParameterIn.PATH, description = "消息id", name = "sendRecordId", required = true)
    @GetMapping(value = "/select/one/{sendRecordId}")
    public Result<ManageSendRecordDto> getById(@PathVariable(required = false) @PathNotBlankOrNull(message = "消息id不能为空") String sendRecordId) {
        return ok(service.getById(sendRecordId));
    }


    @Operation(summary = "消息发送记录表分页查询", tags = {"v1.0.0"}, description = "分页查询消息发送记录表")
    @PostMapping(value = "/select/page")
    public Result<PageDto<ManageSendRecordPageDto>> selectPage(@RequestBody ManageSendRecordPageVo vo) {
        return ok(service.pageByModel(vo));
    }
}
