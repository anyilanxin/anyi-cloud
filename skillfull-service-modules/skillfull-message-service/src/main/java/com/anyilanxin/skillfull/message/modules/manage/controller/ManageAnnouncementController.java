// +----------------------------------------------------------------------
// | SkillFull快速开发平台 [ SkillFull ]
// +----------------------------------------------------------------------
// | 版权所有 2020~2021 zxiaozhou
// +----------------------------------------------------------------------
// | 官方网站: https://www.divisu.com
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
import com.anyilanxin.skillfull.message.modules.manage.controller.vo.ManageAnnouncementPageVo;
import com.anyilanxin.skillfull.message.modules.manage.controller.vo.ManageAnnouncementQueryVo;
import com.anyilanxin.skillfull.message.modules.manage.controller.vo.ManageAnnouncementVo;
import com.anyilanxin.skillfull.message.modules.manage.service.IManageAnnouncementService;
import com.anyilanxin.skillfull.message.modules.manage.service.dto.ManageAnnouncementDto;
import com.anyilanxin.skillfull.message.modules.manage.service.dto.ManageAnnouncementPageDto;
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
 * 系统通告公告管理(ManageAnnouncement)控制层
 *
 * @author zxiaozhou
 * @copyright zxiaozhou（https://divisu.com）
 * @date 2022-03-29 08:34:21
 * @since JDK1.8
 */
@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@Hidden
@Tag(name = "ManageAnnouncement", description = "系统通告公告管理Api接口相关")
@RequestMapping(value = "/manageAnnouncement", produces = MediaType.APPLICATION_JSON_VALUE)
public class ManageAnnouncementController extends BaseController {
    private final IManageAnnouncementService service;

    @Operation(summary = "系统通告公告管理添加", tags = {"v1.0.0"}, description = "添加系统通告公告管理", hidden = true)
    @PostMapping(value = "/insert")
    public Result<String> insert(@RequestBody @Valid ManageAnnouncementVo vo) {
        service.save(vo);
        return ok(I18nUtil.get("Controller.InsertSuccess"));
    }


    @Operation(summary = "通过通知公告id修改", tags = {"v1.0.0"}, description = "修改系统通告公告管理", hidden = true)
    @Parameter(in = ParameterIn.PATH, description = "通知公告id", name = "anntId", required = true)
    @PutMapping(value = "/update/{anntId}")
    public Result<String> update(@PathVariable(required = false) @PathNotBlankOrNull(message = "通知公告id不能为空") String anntId,
                                 @RequestBody @Valid ManageAnnouncementVo vo) {
        service.updateById(anntId, vo);
        return ok(I18nUtil.get("Controller.UpdateSuccess"));
    }


    @Operation(summary = "系统通告公告管理逻辑删除", tags = {"v1.0.0"}, description = "删除系统通告公告管理", hidden = true)
    @Parameter(in = ParameterIn.PATH, description = "通知公告id", name = "anntId", required = true)
    @DeleteMapping(value = "/delete-one/{anntId}")
    public Result<String> deleteById(@PathVariable(required = false) @PathNotBlankOrNull(message = "通知公告id不能为空") String anntId) {
        service.deleteById(anntId);
        return ok(I18nUtil.get("Controller.DeleteSuccess"));
    }


    @Operation(summary = "系统通告公告管理逻辑批量删除", tags = {"v1.0.0"}, description = "批量删除系统通告公告管理", hidden = true)
    @PostMapping(value = "/delete-batch")
    public Result<String> deleteBatchByIds(@RequestBody @NotNullSize(message = "待删除通知公告id不能为空") List<String> anntIds) {
        service.deleteBatch(anntIds);
        return ok(I18nUtil.get("Controller.BatchDeleteSuccess"));
    }


    @Operation(summary = "通过通知公告id查询详情", tags = {"v1.0.0"}, description = "查询系统通告公告管理详情", hidden = true)
    @Parameter(in = ParameterIn.PATH, description = "通知公告id", name = "anntId", required = true)
    @GetMapping(value = "/select/one/{anntId}")
    public Result<ManageAnnouncementDto> getById(@PathVariable(required = false) @PathNotBlankOrNull(message = "通知公告id不能为空") String anntId) {
        return ok(service.getById(anntId));
    }


    @Operation(summary = "通过条件查询系统通告公告管理多条数据", tags = {"v1.0.0"}, description = "通过条件查询系统通告公告管理", hidden = true)
    @PostMapping(value = "/select/list/by-model")
    public Result<List<ManageAnnouncementDto>> selectListByModel(@RequestBody ManageAnnouncementQueryVo vo) {
        return ok(service.selectListByModel(vo));
    }


    @Operation(summary = "系统通告公告管理分页查询", tags = {"v1.0.0"}, description = "分页查询系统通告公告管理", hidden = true)
    @PostMapping(value = "/select/page")
    public Result<PageDto<ManageAnnouncementPageDto>> selectPage(@RequestBody ManageAnnouncementPageVo vo) {
        return ok(service.pageByModel(vo));
    }
}
