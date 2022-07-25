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
import com.anyilanxin.skillfull.message.modules.chat.controller.vo.ChatMessageInfoPageVo;
import com.anyilanxin.skillfull.message.modules.chat.controller.vo.ChatMessageInfoQueryVo;
import com.anyilanxin.skillfull.message.modules.chat.controller.vo.ChatMessageInfoVo;
import com.anyilanxin.skillfull.message.modules.chat.service.IChatMessageInfoService;
import com.anyilanxin.skillfull.message.modules.chat.service.dto.ChatMessageInfoDto;
import com.anyilanxin.skillfull.message.modules.chat.service.dto.ChatMessageInfoPageDto;
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
 * 聊天消息(ChatMessageInfo)控制层
 *
 * @author zxiaozhou
 * @copyright zxiaozhou（https://divisu.com）
 * @date 2022-03-29 23:38:27
 * @since JDK1.8
 */
@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@Hidden
@Tag(name = "ChatMessageInfo", description = "聊天消息Api接口相关")
@RequestMapping(value = "/chatMessageInfo", produces = MediaType.APPLICATION_JSON_VALUE)
public class ChatMessageInfoController extends BaseController {
    private final IChatMessageInfoService service;

    @Operation(summary = "聊天消息添加", tags = {"v1.0.0"}, description = "添加聊天消息", hidden = true)
    @PostMapping(value = "/insert")
    public Result<String> insert(@RequestBody @Valid ChatMessageInfoVo vo) {
        service.save(vo);
        return ok(I18nUtil.get("Controller.InsertSuccess"));
    }


    @Operation(summary = "通过聊天消息id修改", tags = {"v1.0.0"}, description = "修改聊天消息", hidden = true)
    @Parameter(in = ParameterIn.PATH, description = "聊天消息id", name = "chatMessageId", required = true)
    @PutMapping(value = "/update/{chatMessageId}")
    public Result<String> update(@PathVariable(required = false) @PathNotBlankOrNull(message = "聊天消息id不能为空") String chatMessageId,
                                 @RequestBody @Valid ChatMessageInfoVo vo) {
        service.updateById(chatMessageId, vo);
        return ok(I18nUtil.get("Controller.UpdateSuccess"));
    }


    @Operation(summary = "聊天消息逻辑删除", tags = {"v1.0.0"}, description = "删除聊天消息", hidden = true)
    @Parameter(in = ParameterIn.PATH, description = "聊天消息id", name = "chatMessageId", required = true)
    @DeleteMapping(value = "/delete-one/{chatMessageId}")
    public Result<String> deleteById(@PathVariable(required = false) @PathNotBlankOrNull(message = "聊天消息id不能为空") String chatMessageId) {
        service.deleteById(chatMessageId);
        return ok(I18nUtil.get("Controller.DeleteSuccess"));
    }


    @Operation(summary = "聊天消息逻辑批量删除", tags = {"v1.0.0"}, description = "批量删除聊天消息", hidden = true)
    @PostMapping(value = "/delete-batch")
    public Result<String> deleteBatchByIds(@RequestBody @NotNullSize(message = "待删除聊天消息id不能为空") List<String> chatMessageIds) {
        service.deleteBatch(chatMessageIds);
        return ok(I18nUtil.get("Controller.BatchDeleteSuccess"));
    }


    @Operation(summary = "通过聊天消息id查询详情", tags = {"v1.0.0"}, description = "查询聊天消息详情", hidden = true)
    @Parameter(in = ParameterIn.PATH, description = "聊天消息id", name = "chatMessageId", required = true)
    @GetMapping(value = "/select/one/{chatMessageId}")
    public Result<ChatMessageInfoDto> getById(@PathVariable(required = false) @PathNotBlankOrNull(message = "聊天消息id不能为空") String chatMessageId) {
        return ok(service.getById(chatMessageId));
    }


    @Operation(summary = "通过条件查询聊天消息多条数据", tags = {"v1.0.0"}, description = "通过条件查询聊天消息", hidden = true)
    @PostMapping(value = "/select/list/by-model")
    public Result<List<ChatMessageInfoDto>> selectListByModel(@RequestBody ChatMessageInfoQueryVo vo) {
        return ok(service.selectListByModel(vo));
    }


    @Operation(summary = "聊天消息分页查询", tags = {"v1.0.0"}, description = "分页查询聊天消息", hidden = true)
    @PostMapping(value = "/select/page")
    public Result<PageDto<ChatMessageInfoPageDto>> selectPage(@RequestBody ChatMessageInfoPageVo vo) {
        return ok(service.pageByModel(vo));
    }
}
