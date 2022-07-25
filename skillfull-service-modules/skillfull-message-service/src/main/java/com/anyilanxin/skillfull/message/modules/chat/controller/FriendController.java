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
import com.anyilanxin.skillfull.message.modules.chat.controller.vo.FriendPageVo;
import com.anyilanxin.skillfull.message.modules.chat.controller.vo.FriendQueryVo;
import com.anyilanxin.skillfull.message.modules.chat.controller.vo.FriendVo;
import com.anyilanxin.skillfull.message.modules.chat.service.IFriendService;
import com.anyilanxin.skillfull.message.modules.chat.service.dto.FriendDto;
import com.anyilanxin.skillfull.message.modules.chat.service.dto.FriendPageDto;
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
 * 好友列表(Friend)控制层
 *
 * @author zxiaozhou
 * @copyright zxiaozhou（https://divisu.com）
 * @date 2022-03-29 23:38:24
 * @since JDK1.8
 */
@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@Hidden
@Tag(name = "Friend", description = "好友列表Api接口相关")
@RequestMapping(value = "/friend", produces = MediaType.APPLICATION_JSON_VALUE)
public class FriendController extends BaseController {
    private final IFriendService service;

    @Operation(summary = "好友列表添加", tags = {"v1.0.0"}, description = "添加好友列表", hidden = true)
    @PostMapping(value = "/insert")
    public Result<String> insert(@RequestBody @Valid FriendVo vo) {
        service.save(vo);
        return ok(I18nUtil.get("Controller.InsertSuccess"));
    }


    @Operation(summary = "通过模板id修改", tags = {"v1.0.0"}, description = "修改好友列表", hidden = true)
    @Parameter(in = ParameterIn.PATH, description = "模板id", name = "friendId", required = true)
    @PutMapping(value = "/update/{friendId}")
    public Result<String> update(@PathVariable(required = false) @PathNotBlankOrNull(message = "模板id不能为空") String friendId,
                                 @RequestBody @Valid FriendVo vo) {
        service.updateById(friendId, vo);
        return ok(I18nUtil.get("Controller.UpdateSuccess"));
    }


    @Operation(summary = "好友列表逻辑删除", tags = {"v1.0.0"}, description = "删除好友列表", hidden = true)
    @Parameter(in = ParameterIn.PATH, description = "模板id", name = "friendId", required = true)
    @DeleteMapping(value = "/delete-one/{friendId}")
    public Result<String> deleteById(@PathVariable(required = false) @PathNotBlankOrNull(message = "模板id不能为空") String friendId) {
        service.deleteById(friendId);
        return ok(I18nUtil.get("Controller.DeleteSuccess"));
    }


    @Operation(summary = "好友列表逻辑批量删除", tags = {"v1.0.0"}, description = "批量删除好友列表", hidden = true)
    @PostMapping(value = "/delete-batch")
    public Result<String> deleteBatchByIds(@RequestBody @NotNullSize(message = "待删除模板id不能为空") List<String> friendIds) {
        service.deleteBatch(friendIds);
        return ok(I18nUtil.get("Controller.BatchDeleteSuccess"));
    }


    @Operation(summary = "通过模板id查询详情", tags = {"v1.0.0"}, description = "查询好友列表详情", hidden = true)
    @Parameter(in = ParameterIn.PATH, description = "模板id", name = "friendId", required = true)
    @GetMapping(value = "/select/one/{friendId}")
    public Result<FriendDto> getById(@PathVariable(required = false) @PathNotBlankOrNull(message = "模板id不能为空") String friendId) {
        return ok(service.getById(friendId));
    }


    @Operation(summary = "通过条件查询好友列表多条数据", tags = {"v1.0.0"}, description = "通过条件查询好友列表", hidden = true)
    @PostMapping(value = "/select/list/by-model")
    public Result<List<FriendDto>> selectListByModel(@RequestBody FriendQueryVo vo) {
        return ok(service.selectListByModel(vo));
    }


    @Operation(summary = "好友列表分页查询", tags = {"v1.0.0"}, description = "分页查询好友列表", hidden = true)
    @PostMapping(value = "/select/page")
    public Result<PageDto<FriendPageDto>> selectPage(@RequestBody FriendPageVo vo) {
        return ok(service.pageByModel(vo));
    }
}
