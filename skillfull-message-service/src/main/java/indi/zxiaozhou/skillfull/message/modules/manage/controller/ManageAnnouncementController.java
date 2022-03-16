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
import indi.zxiaozhou.skillfull.message.modules.manage.controller.vo.ManageAnnouncementPageVo;
import indi.zxiaozhou.skillfull.message.modules.manage.controller.vo.ManageAnnouncementQueryVo;
import indi.zxiaozhou.skillfull.message.modules.manage.controller.vo.ManageAnnouncementVo;
import indi.zxiaozhou.skillfull.message.modules.manage.service.IManageAnnouncementService;
import indi.zxiaozhou.skillfull.message.modules.manage.service.dto.ManageAnnouncementDto;
import indi.zxiaozhou.skillfull.message.modules.manage.service.dto.ManageAnnouncementPageDto;
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
 * 系统通告管理(ManageAnnouncement)控制层
 *
 * @author zxiaozhou
 * @date 2021-04-19 17:44:21
 * @since JDK1.8
 */
@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@Tag(name = "ManageAnnouncement", description = "系统通告管理")
@RequestMapping(value = "/manage/announcement", produces = MediaType.APPLICATION_JSON_VALUE)
public class ManageAnnouncementController extends BaseController {
    private final IManageAnnouncementService service;


    @Operation(summary = "系统通告管理添加", tags = {"v1.0.0"}, description = "添加系统通告管理")
    @PostMapping(value = "/insert")
    public Mono<Result<String>> insert(@RequestBody @Valid ManageAnnouncementVo vo, final ServerHttpRequest request) {
        ServletUtils.setServerHttpRequest(request);
        service.save(vo);
        return ok("添加成功");
    }


    @Operation(summary = "通过公告id修改", tags = {"v1.0.0"}, description = "修改系统通告管理", hidden = true)
    @Parameter(in = ParameterIn.PATH, description = "公告id", name = "anntId", required = true)
    @PutMapping(value = "/update/{anntId}")
    public Mono<Result<String>> update(@PathVariable(required = false) @PathNotBlankOrNull(message = "公告id不能为空") String anntId,
                                       @RequestBody @Valid ManageAnnouncementVo vo,
                                       final ServerHttpRequest request) {
        ServletUtils.setServerHttpRequest(request);
        service.updateById(anntId, vo);
        return ok("修改成功");
    }


    @Operation(summary = "系统通告管理逻辑删除", tags = {"v1.0.0"}, description = "删除系统通告管理", hidden = true)
    @Parameter(in = ParameterIn.PATH, description = "公告id", name = "anntId", required = true)
    @DeleteMapping(value = "/delete-one/{anntId}")
    public Mono<Result<String>> deleteById(@PathVariable(required = false) @PathNotBlankOrNull(message = "公告id不能为空") String anntId,
                                           final ServerHttpRequest request) {
        ServletUtils.setServerHttpRequest(request);
        service.deleteById(anntId);
        return ok("删除成功");
    }


    @Operation(summary = "系统通告管理逻辑批量删除", tags = {"v1.0.0"}, description = "批量删除系统通告管理", hidden = true)
    @PostMapping(value = "/delete-batch")
    public Mono<Result<String>> deleteBatch(@RequestBody @NotNullSize(message = "待删除公告id不能为空") List<String> anntIds,
                                            final ServerHttpRequest request) {
        ServletUtils.setServerHttpRequest(request);
        service.deleteBatch(anntIds);
        return ok("批量删除成功");
    }


    @Operation(summary = "通过公告id查询详情", tags = {"v1.0.0"}, description = "查询系统通告管理详情", hidden = true)
    @Parameter(in = ParameterIn.PATH, description = "公告id", name = "anntId", required = true)
    @GetMapping(value = "/select/one/{anntId}")
    public Mono<Result<ManageAnnouncementDto>> getById(@PathVariable(required = false) @PathNotBlankOrNull(message = "公告id不能为空") String anntId,
                                                       final ServerHttpRequest request) {
        ServletUtils.setServerHttpRequest(request);
        return ok(service.getById(anntId));
    }


    @Operation(summary = "通过条件查询系统通告管理多条数据", tags = {"v1.0.0"}, description = "通过条件查询系统通告管理", hidden = true)
    @PostMapping(value = "/select/list")
    public Mono<Result<List<ManageAnnouncementDto>>> getList(@RequestBody ManageAnnouncementQueryVo vo,
                                                             final ServerHttpRequest request) {
        ServletUtils.setServerHttpRequest(request);
        return ok(service.selectListByModel(vo));
    }


    @Operation(summary = "系统通告管理分页查询", tags = {"v1.0.0"}, description = "分页查询系统通告管理", hidden = true)
    @PostMapping(value = "/select/page")
    public Mono<Result<PageDto<ManageAnnouncementPageDto>>> selectPage(@RequestBody ManageAnnouncementPageVo vo,
                                                                       final ServerHttpRequest request) {
        ServletUtils.setServerHttpRequest(request);
        return ok(service.pageByModel(vo));
    }
}