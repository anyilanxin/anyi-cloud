package com.anyilanxin.skillfull.message.modules.announcement.controller;

import com.anyilanxin.skillfull.corecommon.base.Result;
import com.anyilanxin.skillfull.corecommon.utils.I18nUtil;
import com.anyilanxin.skillfull.corecommon.validation.annotation.NotNullSize;
import com.anyilanxin.skillfull.corecommon.validation.annotation.PathNotBlankOrNull;
import com.anyilanxin.skillfull.coremvc.base.controller.BaseController;
import com.anyilanxin.skillfull.database.datasource.base.service.dto.PageDto;
import com.anyilanxin.skillfull.message.modules.announcement.controller.vo.AnnouncementRecordPageVo;
import com.anyilanxin.skillfull.message.modules.announcement.controller.vo.AnnouncementRecordQueryVo;
import com.anyilanxin.skillfull.message.modules.announcement.controller.vo.AnnouncementRecordVo;
import com.anyilanxin.skillfull.message.modules.announcement.service.IAnnouncementRecordService;
import com.anyilanxin.skillfull.message.modules.announcement.service.dto.AnnouncementRecordDto;
import com.anyilanxin.skillfull.message.modules.announcement.service.dto.AnnouncementRecordPageDto;
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
 * 系统通知公告阅读记录(AnnouncementRecord)控制层
 *
 * @author zxiaozhou
 * @copyright zxiaozhou（https://skillfull.divisu.com）
 * @date 2022-03-29 08:35:33
 * @since JDK1.8
 */
@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@Hidden
@Tag(name = "AnnouncementRecord", description = "系统通知公告阅读记录Api接口相关")
@RequestMapping(value = "/announcementRecord", produces = MediaType.APPLICATION_JSON_VALUE)
public class AnnouncementRecordController extends BaseController {
    private final IAnnouncementRecordService service;

    @Operation(summary = "系统通知公告阅读记录添加", tags = {"v1.0.0"}, description = "添加系统通知公告阅读记录", hidden = true)
    @PostMapping(value = "/insert")
    public Result<String> insert(@RequestBody @Valid AnnouncementRecordVo vo) {
        service.save(vo);
        return ok(I18nUtil.get("Controller.InsertSuccess"));
    }


    @Operation(summary = "通过通知公告阅读记录id修改", tags = {"v1.0.0"}, description = "修改系统通知公告阅读记录", hidden = true)
    @Parameter(in = ParameterIn.PATH, description = "通知公告阅读记录id", name = "anntReadId", required = true)
    @PutMapping(value = "/update/{anntReadId}")
    public Result<String> update(@PathVariable(required = false) @PathNotBlankOrNull(message = "通知公告阅读记录id不能为空") String anntReadId,
                                 @RequestBody @Valid AnnouncementRecordVo vo) {
        service.updateById(anntReadId, vo);
        return ok(I18nUtil.get("Controller.UpdateSuccess"));
    }


    @Operation(summary = "系统通知公告阅读记录逻辑删除", tags = {"v1.0.0"}, description = "删除系统通知公告阅读记录", hidden = true)
    @Parameter(in = ParameterIn.PATH, description = "通知公告阅读记录id", name = "anntReadId", required = true)
    @DeleteMapping(value = "/delete-one/{anntReadId}")
    public Result<String> deleteById(@PathVariable(required = false) @PathNotBlankOrNull(message = "通知公告阅读记录id不能为空") String anntReadId) {
        service.deleteById(anntReadId);
        return ok(I18nUtil.get("Controller.DeleteSuccess"));
    }


    @Operation(summary = "系统通知公告阅读记录逻辑批量删除", tags = {"v1.0.0"}, description = "批量删除系统通知公告阅读记录", hidden = true)
    @PostMapping(value = "/delete-batch")
    public Result<String> deleteBatchByIds(@RequestBody @NotNullSize(message = "待删除通知公告阅读记录id不能为空") List<String> anntReadIds) {
        service.deleteBatch(anntReadIds);
        return ok(I18nUtil.get("Controller.BatchDeleteSuccess"));
    }


    @Operation(summary = "通过通知公告阅读记录id查询详情", tags = {"v1.0.0"}, description = "查询系统通知公告阅读记录详情", hidden = true)
    @Parameter(in = ParameterIn.PATH, description = "通知公告阅读记录id", name = "anntReadId", required = true)
    @GetMapping(value = "/select/one/{anntReadId}")
    public Result<AnnouncementRecordDto> getById(@PathVariable(required = false) @PathNotBlankOrNull(message = "通知公告阅读记录id不能为空") String anntReadId) {
        return ok(service.getById(anntReadId));
    }


    @Operation(summary = "通过条件查询系统通知公告阅读记录多条数据", tags = {"v1.0.0"}, description = "通过条件查询系统通知公告阅读记录", hidden = true)
    @PostMapping(value = "/select/list/by-model")
    public Result<List<AnnouncementRecordDto>> selectListByModel(@RequestBody AnnouncementRecordQueryVo vo) {
        return ok(service.selectListByModel(vo));
    }


    @Operation(summary = "系统通知公告阅读记录分页查询", tags = {"v1.0.0"}, description = "分页查询系统通知公告阅读记录", hidden = true)
    @PostMapping(value = "/select/page")
    public Result<PageDto<AnnouncementRecordPageDto>> selectPage(@RequestBody AnnouncementRecordPageVo vo) {
        return ok(service.pageByModel(vo));
    }
}
