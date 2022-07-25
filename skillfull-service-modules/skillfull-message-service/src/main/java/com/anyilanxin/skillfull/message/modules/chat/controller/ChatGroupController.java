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
import com.anyilanxin.skillfull.message.modules.chat.controller.vo.ChatGroupPageVo;
import com.anyilanxin.skillfull.message.modules.chat.controller.vo.ChatGroupQueryVo;
import com.anyilanxin.skillfull.message.modules.chat.controller.vo.ChatGroupVo;
import com.anyilanxin.skillfull.message.modules.chat.service.IChatGroupService;
import com.anyilanxin.skillfull.message.modules.chat.service.dto.ChatGroupDto;
import com.anyilanxin.skillfull.message.modules.chat.service.dto.ChatGroupPageDto;
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
 * 消息群(ChatGroup)控制层
 *
 * @author zxiaozhou
 * @copyright zxiaozhou（https://divisu.com）
 * @date 2022-03-29 23:38:22
 * @since JDK1.8
 */
@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@Hidden
@Tag(name = "ChatGroup", description = "消息群Api接口相关")
@RequestMapping(value = "/chatGroup", produces = MediaType.APPLICATION_JSON_VALUE)
public class ChatGroupController extends BaseController {
    private final IChatGroupService service;

    @Operation(summary = "消息群添加", tags = {"v1.0.0"}, description = "添加消息群", hidden = true)
    @PostMapping(value = "/insert")
    public Result<String> insert(@RequestBody @Valid ChatGroupVo vo) {
        service.save(vo);
        return ok(I18nUtil.get("Controller.InsertSuccess"));
    }


    @Operation(summary = "通过群id修改", tags = {"v1.0.0"}, description = "修改消息群", hidden = true)
    @Parameter(in = ParameterIn.PATH, description = "群id", name = "chatGroupId", required = true)
    @PutMapping(value = "/update/{chatGroupId}")
    public Result<String> update(@PathVariable(required = false) @PathNotBlankOrNull(message = "群id不能为空") String chatGroupId,
                                 @RequestBody @Valid ChatGroupVo vo) {
        service.updateById(chatGroupId, vo);
        return ok(I18nUtil.get("Controller.UpdateSuccess"));
    }


    @Operation(summary = "消息群逻辑删除", tags = {"v1.0.0"}, description = "删除消息群", hidden = true)
    @Parameter(in = ParameterIn.PATH, description = "群id", name = "chatGroupId", required = true)
    @DeleteMapping(value = "/delete-one/{chatGroupId}")
    public Result<String> deleteById(@PathVariable(required = false) @PathNotBlankOrNull(message = "群id不能为空") String chatGroupId) {
        service.deleteById(chatGroupId);
        return ok(I18nUtil.get("Controller.DeleteSuccess"));
    }


    @Operation(summary = "消息群逻辑批量删除", tags = {"v1.0.0"}, description = "批量删除消息群", hidden = true)
    @PostMapping(value = "/delete-batch")
    public Result<String> deleteBatchByIds(@RequestBody @NotNullSize(message = "待删除群id不能为空") List<String> chatGroupIds) {
        service.deleteBatch(chatGroupIds);
        return ok(I18nUtil.get("Controller.BatchDeleteSuccess"));
    }


    @Operation(summary = "通过群id查询详情", tags = {"v1.0.0"}, description = "查询消息群详情", hidden = true)
    @Parameter(in = ParameterIn.PATH, description = "群id", name = "chatGroupId", required = true)
    @GetMapping(value = "/select/one/{chatGroupId}")
    public Result<ChatGroupDto> getById(@PathVariable(required = false) @PathNotBlankOrNull(message = "群id不能为空") String chatGroupId) {
        return ok(service.getById(chatGroupId));
    }


    @Operation(summary = "通过条件查询消息群多条数据", tags = {"v1.0.0"}, description = "通过条件查询消息群", hidden = true)
    @PostMapping(value = "/select/list/by-model")
    public Result<List<ChatGroupDto>> selectListByModel(@RequestBody ChatGroupQueryVo vo) {
        return ok(service.selectListByModel(vo));
    }


    @Operation(summary = "消息群分页查询", tags = {"v1.0.0"}, description = "分页查询消息群", hidden = true)
    @PostMapping(value = "/select/page")
    public Result<PageDto<ChatGroupPageDto>> selectPage(@RequestBody ChatGroupPageVo vo) {
        return ok(service.pageByModel(vo));
    }
}
