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
import com.anyilanxin.skillfull.message.modules.chat.controller.vo.ChatSessionInfoPageVo;
import com.anyilanxin.skillfull.message.modules.chat.controller.vo.ChatSessionInfoQueryVo;
import com.anyilanxin.skillfull.message.modules.chat.controller.vo.ChatSessionInfoVo;
import com.anyilanxin.skillfull.message.modules.chat.service.IChatSessionInfoService;
import com.anyilanxin.skillfull.message.modules.chat.service.dto.ChatSessionInfoDto;
import com.anyilanxin.skillfull.message.modules.chat.service.dto.ChatSessionInfoPageDto;
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
 * 聊天会话信息(ChatSessionInfo)控制层
 *
 * @author zxiaozhou
 * @copyright zxiaozhou（https://divisu.com）
 * @date 2022-04-08 05:39:29
 * @since JDK1.8
 */
@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@Hidden
@Tag(name = "ChatSessionInfo", description = "聊天会话信息Api接口相关")
@RequestMapping(value = "/chatSessionInfo", produces = MediaType.APPLICATION_JSON_VALUE)
public class ChatSessionInfoController extends BaseController {
    private final IChatSessionInfoService service;

    @Operation(summary = "聊天会话信息添加", tags = {"v1.0.0"}, description = "添加聊天会话信息", hidden = true)
    @PostMapping(value = "/insert")
    public Result<String> insert(@RequestBody @Valid ChatSessionInfoVo vo) {
        service.save(vo);
        return ok(I18nUtil.get("Controller.InsertSuccess"));
    }


    @Operation(summary = "通过会话id修改", tags = {"v1.0.0"}, description = "修改聊天会话信息", hidden = true)
    @Parameter(in = ParameterIn.PATH, description = "会话id", name = "sessionInfoId", required = true)
    @PutMapping(value = "/update/{sessionInfoId}")
    public Result<String> update(@PathVariable(required = false) @PathNotBlankOrNull(message = "会话id不能为空") String sessionInfoId,
                                 @RequestBody @Valid ChatSessionInfoVo vo) {
        service.updateById(sessionInfoId, vo);
        return ok(I18nUtil.get("Controller.UpdateSuccess"));
    }


    @Operation(summary = "聊天会话信息逻辑删除", tags = {"v1.0.0"}, description = "删除聊天会话信息", hidden = true)
    @Parameter(in = ParameterIn.PATH, description = "会话id", name = "sessionInfoId", required = true)
    @DeleteMapping(value = "/delete-one/{sessionInfoId}")
    public Result<String> deleteById(@PathVariable(required = false) @PathNotBlankOrNull(message = "会话id不能为空") String sessionInfoId) {
        service.deleteById(sessionInfoId);
        return ok(I18nUtil.get("Controller.DeleteSuccess"));
    }


    @Operation(summary = "聊天会话信息逻辑批量删除", tags = {"v1.0.0"}, description = "批量删除聊天会话信息", hidden = true)
    @PostMapping(value = "/delete-batch")
    public Result<String> deleteBatchByIds(@RequestBody @NotNullSize(message = "待删除会话id不能为空") List<String> sessionInfoIds) {
        service.deleteBatch(sessionInfoIds);
        return ok(I18nUtil.get("Controller.BatchDeleteSuccess"));
    }


    @Operation(summary = "通过会话id查询详情", tags = {"v1.0.0"}, description = "查询聊天会话信息详情", hidden = true)
    @Parameter(in = ParameterIn.PATH, description = "会话id", name = "sessionInfoId", required = true)
    @GetMapping(value = "/select/one/{sessionInfoId}")
    public Result<ChatSessionInfoDto> getById(@PathVariable(required = false) @PathNotBlankOrNull(message = "会话id不能为空") String sessionInfoId) {
        return ok(service.getById(sessionInfoId));
    }


    @Operation(summary = "通过条件查询聊天会话信息多条数据", tags = {"v1.0.0"}, description = "通过条件查询聊天会话信息", hidden = true)
    @PostMapping(value = "/select/list/by-model")
    public Result<List<ChatSessionInfoDto>> selectListByModel(@RequestBody ChatSessionInfoQueryVo vo) {
        return ok(service.selectListByModel(vo));
    }


    @Operation(summary = "聊天会话信息分页查询", tags = {"v1.0.0"}, description = "分页查询聊天会话信息", hidden = true)
    @PostMapping(value = "/select/page")
    public Result<PageDto<ChatSessionInfoPageDto>> selectPage(@RequestBody ChatSessionInfoPageVo vo) {
        return ok(service.pageByModel(vo));
    }
}
