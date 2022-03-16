// +----------------------------------------------------------------------
// | SkillFull快速开发平台 [ SkillFull ]
// +----------------------------------------------------------------------
// | 版权所有 2020~2021 zxiaozhou
// +----------------------------------------------------------------------
// | 官方网站: https://www.divisu.com
// +----------------------------------------------------------------------
// | 作者: zxiaozhou <z7630853@163.com>
// +----------------------------------------------------------------------
package indi.zxiaozhou.skillfull.message.modules.manage.controller;

import indi.zxiaozhou.skillfull.corecommon.base.Result;
import indi.zxiaozhou.skillfull.corecommon.validation.annotation.NotNullSize;
import indi.zxiaozhou.skillfull.corecommon.validation.annotation.PathNotBlankOrNull;
import indi.zxiaozhou.skillfull.coredatabase.base.service.dto.PageDto;
import indi.zxiaozhou.skillfull.corewebflux.base.controller.BaseController;
import indi.zxiaozhou.skillfull.corewebflux.utils.ServletUtils;
import indi.zxiaozhou.skillfull.message.modules.manage.controller.vo.ManageSendRecordPageVo;
import indi.zxiaozhou.skillfull.message.modules.manage.service.IManageSendRecordService;
import indi.zxiaozhou.skillfull.message.modules.manage.service.dto.ManageSendRecordDto;
import indi.zxiaozhou.skillfull.message.modules.manage.service.dto.ManageSendRecordPageDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.List;

/**
 * 消息发送记录表(ManageSendRecord)控制层
 *
 * @author zxiaozhou
 * @date 2021-04-19 17:45:45
 * @since JDK1.8
 */
@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@Tag(name = "ManageSendRecord", description = "消息发送记录")
@RequestMapping(value = "/manage/send-record", produces = MediaType.APPLICATION_JSON_VALUE)
public class ManageSendRecordController extends BaseController {
    private final IManageSendRecordService service;


    @Operation(summary = "消息发送记录表逻辑删除", tags = {"v1.0.0"}, description = "删除消息发送记录表")
    @Parameter(in = ParameterIn.PATH, description = "消息id", name = "esId", required = true)
    @DeleteMapping(value = "/delete-one/{esId}")
    public Mono<Result<String>> deleteById(@PathVariable(required = false) @PathNotBlankOrNull(message = "消息id不能为空") String esId,
                                           final ServerHttpRequest request) {
        ServletUtils.setServerHttpRequest(request);
        service.deleteById(esId);
        return ok("删除成功");
    }


    @Operation(summary = "消息发送记录表逻辑批量删除", tags = {"v1.0.0"}, description = "批量删除消息发送记录表")
    @PostMapping(value = "/delete-batch")
    public Mono<Result<String>> deleteBatch(@RequestBody @NotNullSize(message = "待删除消息id不能为空") List<String> esIds,
                                            final ServerHttpRequest request) {
        ServletUtils.setServerHttpRequest(request);
        service.deleteBatch(esIds);
        return ok("批量删除成功");
    }


    @Operation(summary = "通过消息id查询详情", tags = {"v1.0.0"}, description = "查询消息发送记录表详情")
    @Parameter(in = ParameterIn.PATH, description = "消息id", name = "esId", required = true)
    @GetMapping(value = "/select/one/{esId}")
    public Mono<Result<ManageSendRecordDto>> getById(@PathVariable(required = false) @PathNotBlankOrNull(message = "消息id不能为空") String esId,
                                                     final ServerHttpRequest request) {
        ServletUtils.setServerHttpRequest(request);
        return ok(service.getById(esId));
    }


    @Operation(summary = "消息发送记录表分页查询", tags = {"v1.0.0"}, description = "分页查询消息发送记录表")
    @PostMapping(value = "/select/page")
    public Mono<Result<PageDto<ManageSendRecordPageDto>>> selectPage(@RequestBody ManageSendRecordPageVo vo,
                                                                     final ServerHttpRequest request) {
        ServletUtils.setServerHttpRequest(request);
        return ok(service.pageByModel(vo));
    }
}