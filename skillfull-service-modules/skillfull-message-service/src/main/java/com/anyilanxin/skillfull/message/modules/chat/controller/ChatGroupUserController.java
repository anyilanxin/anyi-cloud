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
import com.anyilanxin.skillfull.message.modules.chat.controller.vo.ChatGroupUserPageVo;
import com.anyilanxin.skillfull.message.modules.chat.controller.vo.ChatGroupUserQueryVo;
import com.anyilanxin.skillfull.message.modules.chat.controller.vo.ChatGroupUserVo;
import com.anyilanxin.skillfull.message.modules.chat.service.IChatGroupUserService;
import com.anyilanxin.skillfull.message.modules.chat.service.dto.ChatGroupUserDto;
import com.anyilanxin.skillfull.message.modules.chat.service.dto.ChatGroupUserPageDto;
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
 * 群成员信息(ChatGroupUser)控制层
 *
 * @author zxiaozhou
 * @copyright zxiaozhou（https://divisu.com）
 * @date 2022-03-29 23:38:26
 * @since JDK1.8
 */
@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@Hidden
@Tag(name = "ChatGroupUser", description = "群成员信息Api接口相关")
@RequestMapping(value = "/chatGroupUser", produces = MediaType.APPLICATION_JSON_VALUE)
public class ChatGroupUserController extends BaseController {
    private final IChatGroupUserService service;

    @Operation(summary = "群成员信息添加", tags = {"v1.0.0"}, description = "添加群成员信息", hidden = true)
    @PostMapping(value = "/insert")
    public Result<String> insert(@RequestBody @Valid ChatGroupUserVo vo) {
        service.save(vo);
        return ok(I18nUtil.get("Controller.InsertSuccess"));
    }


    @Operation(summary = "通过群成员id修改", tags = {"v1.0.0"}, description = "修改群成员信息", hidden = true)
    @Parameter(in = ParameterIn.PATH, description = "群成员id", name = "groupUserId", required = true)
    @PutMapping(value = "/update/{groupUserId}")
    public Result<String> update(@PathVariable(required = false) @PathNotBlankOrNull(message = "群成员id不能为空") String groupUserId,
                                 @RequestBody @Valid ChatGroupUserVo vo) {
        service.updateById(groupUserId, vo);
        return ok(I18nUtil.get("Controller.UpdateSuccess"));
    }


    @Operation(summary = "群成员信息逻辑删除", tags = {"v1.0.0"}, description = "删除群成员信息", hidden = true)
    @Parameter(in = ParameterIn.PATH, description = "群成员id", name = "groupUserId", required = true)
    @DeleteMapping(value = "/delete-one/{groupUserId}")
    public Result<String> deleteById(@PathVariable(required = false) @PathNotBlankOrNull(message = "群成员id不能为空") String groupUserId) {
        service.deleteById(groupUserId);
        return ok(I18nUtil.get("Controller.DeleteSuccess"));
    }


    @Operation(summary = "群成员信息逻辑批量删除", tags = {"v1.0.0"}, description = "批量删除群成员信息", hidden = true)
    @PostMapping(value = "/delete-batch")
    public Result<String> deleteBatchByIds(@RequestBody @NotNullSize(message = "待删除群成员id不能为空") List<String> groupUserIds) {
        service.deleteBatch(groupUserIds);
        return ok(I18nUtil.get("Controller.BatchDeleteSuccess"));
    }


    @Operation(summary = "通过群成员id查询详情", tags = {"v1.0.0"}, description = "查询群成员信息详情", hidden = true)
    @Parameter(in = ParameterIn.PATH, description = "群成员id", name = "groupUserId", required = true)
    @GetMapping(value = "/select/one/{groupUserId}")
    public Result<ChatGroupUserDto> getById(@PathVariable(required = false) @PathNotBlankOrNull(message = "群成员id不能为空") String groupUserId) {
        return ok(service.getById(groupUserId));
    }


    @Operation(summary = "通过条件查询群成员信息多条数据", tags = {"v1.0.0"}, description = "通过条件查询群成员信息", hidden = true)
    @PostMapping(value = "/select/list/by-model")
    public Result<List<ChatGroupUserDto>> selectListByModel(@RequestBody ChatGroupUserQueryVo vo) {
        return ok(service.selectListByModel(vo));
    }


    @Operation(summary = "群成员信息分页查询", tags = {"v1.0.0"}, description = "分页查询群成员信息", hidden = true)
    @PostMapping(value = "/select/page")
    public Result<PageDto<ChatGroupUserPageDto>> selectPage(@RequestBody ChatGroupUserPageVo vo) {
        return ok(service.pageByModel(vo));
    }
}
