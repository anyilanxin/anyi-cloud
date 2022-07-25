// +----------------------------------------------------------------------
// | SkillFull快速开发平台 [ SkillFull ]
// +----------------------------------------------------------------------
// | 版权所有 2020~2021 zxiaozhou
// +----------------------------------------------------------------------
// | 官方网站: https://www.divisu.com
// +----------------------------------------------------------------------
// | 作者: zxiaozhou <z7630853@163.com>
// +----------------------------------------------------------------------
package com.anyilanxin.skillfull.message.modules.chat.controller;

import com.anyilanxin.skillfull.corecommon.base.Result;
import com.anyilanxin.skillfull.corecommon.utils.I18nUtil;
import com.anyilanxin.skillfull.corecommon.validation.annotation.NotNullSize;
import com.anyilanxin.skillfull.corecommon.validation.annotation.PathNotBlankOrNull;
import com.anyilanxin.skillfull.coremvc.base.controller.BaseController;
import com.anyilanxin.skillfull.database.datasource.base.service.dto.PageDto;
import com.anyilanxin.skillfull.message.modules.chat.controller.vo.ChatMsgSessionAssociatedPageVo;
import com.anyilanxin.skillfull.message.modules.chat.controller.vo.ChatMsgSessionAssociatedQueryVo;
import com.anyilanxin.skillfull.message.modules.chat.controller.vo.ChatMsgSessionAssociatedVo;
import com.anyilanxin.skillfull.message.modules.chat.service.IChatMsgSessionAssociatedService;
import com.anyilanxin.skillfull.message.modules.chat.service.dto.ChatMsgSessionAssociatedDto;
import com.anyilanxin.skillfull.message.modules.chat.service.dto.ChatMsgSessionAssociatedPageDto;
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
 * 聊天会话关系表(ChatMsgSessionAssociated)控制层
 *
 * @author zxiaozhou
 * @copyright zxiaozhou（https://divisu.com）
 * @date 2022-03-29 23:38:29
 * @since JDK1.8
 */
@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@Hidden
@Tag(name = "ChatMsgSessionAssociated", description = "聊天会话关系表Api接口相关")
@RequestMapping(value = "/chatMsgSessionAssociated", produces = MediaType.APPLICATION_JSON_VALUE)
public class ChatMsgSessionAssociatedController extends BaseController {
    private final IChatMsgSessionAssociatedService service;

    @Operation(summary = "聊天会话关系表添加", tags = {"v1.0.0"}, description = "添加聊天会话关系表", hidden = true)
    @PostMapping(value = "/insert")
    public Result<String> insert(@RequestBody @Valid ChatMsgSessionAssociatedVo vo) {
        service.save(vo);
        return ok(I18nUtil.get("Controller.InsertSuccess"));
    }


    @Operation(summary = "通过消息会话关联id修改", tags = {"v1.0.0"}, description = "修改聊天会话关系表", hidden = true)
    @Parameter(in = ParameterIn.PATH, description = "消息会话关联id", name = "correlationMsgSessionId", required = true)
    @PutMapping(value = "/update/{correlationMsgSessionId}")
    public Result<String> update(@PathVariable(required = false) @PathNotBlankOrNull(message = "消息会话关联id不能为空") String correlationMsgSessionId,
                                 @RequestBody @Valid ChatMsgSessionAssociatedVo vo) {
        service.updateById(correlationMsgSessionId, vo);
        return ok(I18nUtil.get("Controller.UpdateSuccess"));
    }


    @Operation(summary = "聊天会话关系表逻辑删除", tags = {"v1.0.0"}, description = "删除聊天会话关系表", hidden = true)
    @Parameter(in = ParameterIn.PATH, description = "消息会话关联id", name = "correlationMsgSessionId", required = true)
    @DeleteMapping(value = "/delete-one/{correlationMsgSessionId}")
    public Result<String> deleteById(@PathVariable(required = false) @PathNotBlankOrNull(message = "消息会话关联id不能为空") String correlationMsgSessionId) {
        service.deleteById(correlationMsgSessionId);
        return ok(I18nUtil.get("Controller.DeleteSuccess"));
    }


    @Operation(summary = "聊天会话关系表逻辑批量删除", tags = {"v1.0.0"}, description = "批量删除聊天会话关系表", hidden = true)
    @PostMapping(value = "/delete-batch")
    public Result<String> deleteBatchByIds(@RequestBody @NotNullSize(message = "待删除消息会话关联id不能为空") List<String> correlationMsgSessionIds) {
        service.deleteBatch(correlationMsgSessionIds);
        return ok(I18nUtil.get("Controller.BatchDeleteSuccess"));
    }


    @Operation(summary = "通过消息会话关联id查询详情", tags = {"v1.0.0"}, description = "查询聊天会话关系表详情", hidden = true)
    @Parameter(in = ParameterIn.PATH, description = "消息会话关联id", name = "correlationMsgSessionId", required = true)
    @GetMapping(value = "/select/one/{correlationMsgSessionId}")
    public Result<ChatMsgSessionAssociatedDto> getById(@PathVariable(required = false) @PathNotBlankOrNull(message = "消息会话关联id不能为空") String correlationMsgSessionId) {
        return ok(service.getById(correlationMsgSessionId));
    }


    @Operation(summary = "通过条件查询聊天会话关系表多条数据", tags = {"v1.0.0"}, description = "通过条件查询聊天会话关系表", hidden = true)
    @PostMapping(value = "/select/list/by-model")
    public Result<List<ChatMsgSessionAssociatedDto>> selectListByModel(@RequestBody ChatMsgSessionAssociatedQueryVo vo) {
        return ok(service.selectListByModel(vo));
    }


    @Operation(summary = "聊天会话关系表分页查询", tags = {"v1.0.0"}, description = "分页查询聊天会话关系表", hidden = true)
    @PostMapping(value = "/select/page")
    public Result<PageDto<ChatMsgSessionAssociatedPageDto>> selectPage(@RequestBody ChatMsgSessionAssociatedPageVo vo) {
        return ok(service.pageByModel(vo));
    }
}
