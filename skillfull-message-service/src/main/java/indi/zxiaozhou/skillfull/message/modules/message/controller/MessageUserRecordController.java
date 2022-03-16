// +----------------------------------------------------------------------
// | SkillFull快速开发平台 [ SkillFull ]
// +----------------------------------------------------------------------
// | 版权所有 2020~2021 zxiaozhou
// +----------------------------------------------------------------------
// | 官方网站: https://www.divisu.com
// +----------------------------------------------------------------------
// | 作者: zxiaozhou <z7630853@163.com>
// +----------------------------------------------------------------------
package indi.zxiaozhou.skillfull.message.modules.message.controller;

import indi.zxiaozhou.skillfull.corecommon.base.Result;
import indi.zxiaozhou.skillfull.corecommon.validation.annotation.NotNullSize;
import indi.zxiaozhou.skillfull.corecommon.validation.annotation.PathNotBlankOrNull;
import indi.zxiaozhou.skillfull.coredatabase.base.service.dto.PageDto;
import indi.zxiaozhou.skillfull.corewebflux.base.controller.BaseController;
import indi.zxiaozhou.skillfull.corewebflux.utils.ServletUtils;
import indi.zxiaozhou.skillfull.message.modules.message.controller.vo.MessageUserRecordPageVo;
import indi.zxiaozhou.skillfull.message.modules.message.controller.vo.MessageUserRecordVo;
import indi.zxiaozhou.skillfull.message.modules.message.service.IMessageUserRecordService;
import indi.zxiaozhou.skillfull.message.modules.message.service.dto.MessageUserNoReadRecordDto;
import indi.zxiaozhou.skillfull.message.modules.message.service.dto.MessageUserRecordDto;
import indi.zxiaozhou.skillfull.message.modules.message.service.dto.MessageUserRecordPageDto;
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

import javax.validation.Valid;
import java.util.List;

/**
 * 用户消息记录(MessageUserRecord)控制层
 *
 * @author zxiaozhou
 * @date 2021-01-26 16:47:59
 * @since JDK11
 */
@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@Tag(name = "MessageUserRecord", description = "用户消息记录")
@RequestMapping(value = "/msg/record", produces = MediaType.APPLICATION_JSON_VALUE)
public class MessageUserRecordController extends BaseController {
    private final IMessageUserRecordService service;


    @Operation(summary = "用户消息记录添加", tags = {"v1.0.0"}, description = "添加用户消息记录", hidden = true)
    @PostMapping(value = "/insert")
    public Mono<Result<String>> insert(@RequestBody @Valid MessageUserRecordVo vo,
                                       final ServerHttpRequest request) {
        ServletUtils.setServerHttpRequest(request);
        service.save(vo);
        return ok("添加成功");
    }


    @Operation(summary = "通过消息记录id修改", tags = {"v1.0.0"}, description = "修改用户消息记录", hidden = true)
    @Parameter(in = ParameterIn.PATH, description = "消息记录id", name = "msgId", required = true)
    @PutMapping(value = "/update/{msgId}")
    public Mono<Result<String>> update(@PathVariable(required = false) @PathNotBlankOrNull(message = "消息记录id不能为空") String msgId,
                                       @RequestBody @Valid MessageUserRecordVo vo,
                                       final ServerHttpRequest request) {
        ServletUtils.setServerHttpRequest(request);
        service.updateById(msgId, vo);
        return ok("修改成功");
    }


    @Operation(summary = "用户消息记录逻辑删除", tags = {"v1.0.0"}, description = "删除用户消息记录", hidden = true)
    @Parameter(in = ParameterIn.PATH, description = "消息记录id", name = "msgId", required = true)
    @DeleteMapping(value = "/delete-one/{msgId}")
    public Mono<Result<String>> deleteById(@PathVariable(required = false) @PathNotBlankOrNull(message = "消息记录id不能为空") String msgId,
                                           final ServerHttpRequest request) {
        ServletUtils.setServerHttpRequest(request);
        service.deleteById(msgId);
        return ok("删除成功");
    }


    @Operation(summary = "用户消息记录逻辑批量删除", tags = {"v1.0.0"}, description = "批量删除用户消息记录", hidden = true)
    @PostMapping(value = "/delete-batch")
    public Mono<Result<String>> deleteBatch(@RequestBody @NotNullSize(message = "待删除消息记录id不能为空") List<String> msgIds,
                                            final ServerHttpRequest request) {
        ServletUtils.setServerHttpRequest(request);
        service.deleteBatch(msgIds);
        return ok("批量删除成功");
    }


    @Operation(summary = "通过消息记录id查询详情", tags = {"v1.0.0"}, description = "查询用户消息记录详情", hidden = true)
    @Parameter(in = ParameterIn.PATH, description = "消息记录id", name = "msgId", required = true)
    @GetMapping(value = "/select/one/{msgId}")
    public Mono<Result<MessageUserRecordDto>> getById(@PathVariable(required = false) @PathNotBlankOrNull(message = "消息记录id不能为空") String msgId,
                                                      final ServerHttpRequest request) {
        ServletUtils.setServerHttpRequest(request);
        return ok(service.getById(msgId));
    }


    @Operation(summary = "获取未读消息", tags = {"v1.0.0"}, description = "获取未读消息")
    @GetMapping(value = "/select/no-read")
    public Mono<Result<MessageUserNoReadRecordDto>> getListNoRead(final ServerHttpRequest request) {
        ServletUtils.setServerHttpRequest(request);
        return ok(service.getListNoRead());
    }


    @Operation(summary = "清空消息", tags = {"v1.0.0"}, description = "清空消息")
    @GetMapping(value = "/clear/msg/{type}")
    public Mono<Result<String>> clearMsg(@PathVariable(required = false) Integer type,
                                         final ServerHttpRequest request) {
        ServletUtils.setServerHttpRequest(request);
        service.clearMsg(type);
        return ok("清空成功");
    }


    @Operation(summary = "用户消息记录分页查询", tags = {"v1.0.0"}, description = "分页查询用户消息记录")
    @PostMapping(value = "/select/page")
    public Mono<Result<PageDto<MessageUserRecordPageDto>>> selectPage(@RequestBody MessageUserRecordPageVo vo,
                                                                      final ServerHttpRequest request) {
        ServletUtils.setServerHttpRequest(request);
        return ok(service.pageByModel(vo));
    }
}