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
import indi.zxiaozhou.skillfull.message.modules.manage.controller.vo.ManageTemplatePageVo;
import indi.zxiaozhou.skillfull.message.modules.manage.controller.vo.ManageTemplateVo;
import indi.zxiaozhou.skillfull.message.modules.manage.service.IManageTemplateService;
import indi.zxiaozhou.skillfull.message.modules.manage.service.dto.ManageTemplateDto;
import indi.zxiaozhou.skillfull.message.modules.manage.service.dto.ManageTemplatePageDto;
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
 * 消息模板(ManageTemplate)控制层
 *
 * @author zxiaozhou
 * @date 2021-04-19 17:42:56
 * @since JDK1.8
 */
@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@Tag(name = "ManageTemplate", description = "消息模板")
@RequestMapping(value = "/manage/template", produces = MediaType.APPLICATION_JSON_VALUE)
public class ManageTemplateController extends BaseController {
    private final IManageTemplateService service;


    @Operation(summary = "消息模板添加", tags = {"v1.0.0"}, description = "添加消息模板")
    @PostMapping(value = "/insert")
    public Mono<Result<String>> insert(@RequestBody @Valid ManageTemplateVo vo,
                                       final ServerHttpRequest request) {
        ServletUtils.setServerHttpRequest(request);
        service.save(vo);
        return ok("添加成功");
    }


    @Operation(summary = "通过模板id修改", tags = {"v1.0.0"}, description = "修改消息模板")
    @Parameter(in = ParameterIn.PATH, description = "模板id", name = "templateId", required = true)
    @PutMapping(value = "/update/{templateId}")
    public Mono<Result<String>> update(@PathVariable(required = false) @PathNotBlankOrNull(message = "模板id不能为空") String templateId,
                                       @RequestBody @Valid ManageTemplateVo vo,
                                       final ServerHttpRequest request) {
        ServletUtils.setServerHttpRequest(request);
        service.updateById(templateId, vo);
        return ok("修改成功");
    }


    @Operation(summary = "消息模板逻辑删除", tags = {"v1.0.0"}, description = "删除消息模板")
    @Parameter(in = ParameterIn.PATH, description = "模板id", name = "templateId", required = true)
    @DeleteMapping(value = "/delete-one/{templateId}")
    public Mono<Result<String>> deleteById(@PathVariable(required = false) @PathNotBlankOrNull(message = "模板id不能为空") String templateId,
                                           final ServerHttpRequest request) {
        ServletUtils.setServerHttpRequest(request);
        service.deleteById(templateId);
        return ok("删除成功");
    }


    @Operation(summary = "消息模板逻辑批量删除", tags = {"v1.0.0"}, description = "批量删除消息模板")
    @PostMapping(value = "/delete-batch")
    public Mono<Result<String>> deleteBatch(@RequestBody @NotNullSize(message = "待删除模板id不能为空") List<String> templateIds,
                                            final ServerHttpRequest request) {
        ServletUtils.setServerHttpRequest(request);
        service.deleteBatch(templateIds);
        return ok("批量删除成功");
    }


    @Operation(summary = "通过模板id查询详情", tags = {"v1.0.0"}, description = "查询消息模板详情")
    @Parameter(in = ParameterIn.PATH, description = "模板id", name = "templateId", required = true)
    @GetMapping(value = "/select/one/{templateId}")
    public Mono<Result<ManageTemplateDto>> getById(@PathVariable(required = false) @PathNotBlankOrNull(message = "模板id不能为空") String templateId,
                                                   final ServerHttpRequest request) {
        ServletUtils.setServerHttpRequest(request);
        return ok(service.getById(templateId));
    }


    @Operation(summary = "通过模板编码获取有效的模板基本信息", tags = {"v1.0.0"}, description = "通过模板编码获取有效的模板基本信息")
    @PostMapping(value = "/select/effective/by-codes")
    public Mono<Result<List<ManageTemplateDto>>> getEffectiveBasicInfoByCode(@RequestBody @NotNullSize(message = "模板编码不能为空") List<String> codes,
                                                                             final ServerHttpRequest request) {
        ServletUtils.setServerHttpRequest(request);
        return ok(service.getEffectiveBasicInfoByCodes(codes));
    }


    @Operation(summary = "通过类型获取所有有效模板基本信息", tags = {"v1.0.0"}, description = "通过类型获取所有有效模板基本信息")
    @GetMapping(value = "/select/effective/by-type/{type}")
    @Parameter(in = ParameterIn.PATH, description = "模板类型:1-微信模板,2-短信,3-邮件,4-系统公告", name = "type", required = true)
    public Mono<Result<List<ManageTemplateDto>>> getEffectiveBasicInfoByType(@PathVariable(required = false) @PathNotBlankOrNull(message = "模板类型不能为空") Integer type,
                                                                             final ServerHttpRequest request) {
        ServletUtils.setServerHttpRequest(request);
        return ok(service.getEffectiveBasicInfoByType(type));
    }


    @Operation(summary = "消息模板分页查询", tags = {"v1.0.0"}, description = "分页查询消息模板")
    @PostMapping(value = "/select/page")
    public Mono<Result<PageDto<ManageTemplatePageDto>>> selectPage(@RequestBody ManageTemplatePageVo vo,
                                                                   final ServerHttpRequest request) {
        ServletUtils.setServerHttpRequest(request);
        return ok(service.pageByModel(vo));
    }
}