// +----------------------------------------------------------------------
// | SkillFull快速开发平台 [ SkillFull ]
// +----------------------------------------------------------------------
// | 版权所有 2020~2021 zxiaozhou
// +----------------------------------------------------------------------
// | 官方网站: https://skillfull.divisu.com
// +----------------------------------------------------------------------
// | 作者: zxiaozhou <z7630853@163.com>
// +----------------------------------------------------------------------
package com.anyilanxin.skillfull.system.modules.manage.controller;

import com.anyilanxin.skillfull.corecommon.base.Result;
import com.anyilanxin.skillfull.corecommon.utils.I18nUtil;
import com.anyilanxin.skillfull.corecommon.validation.annotation.PathNotBlankOrNull;
import com.anyilanxin.skillfull.coremvc.base.controller.BaseController;
import com.anyilanxin.skillfull.system.modules.manage.controller.vo.ManageCustomFilterVo;
import com.anyilanxin.skillfull.system.modules.manage.service.IManageCustomFilterService;
import com.anyilanxin.skillfull.system.modules.manage.service.dto.ManageCustomFilterDetailDto;
import com.anyilanxin.skillfull.system.modules.manage.service.dto.ManageCustomFilterListDto;
import com.anyilanxin.skillfull.system.modules.manage.service.dto.ManageCustomFilterSimpleDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.constraints.Range;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 自定义过滤器(ManageCustomFilter)控制层
 *
 * @author zxiaozhou
 * @copyright zxiaozhou（https://skillfull.divisu.com）
 * @date 2021-12-19 00:22:14
 * @since JDK1.8
 */
@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@Tag(name = "GatewayManage", description = "网关管理")
@RequestMapping(value = "/manage-custom-filter", produces = MediaType.APPLICATION_JSON_VALUE)
public class ManageCustomFilterController extends BaseController {
    private final IManageCustomFilterService service;

    @Operation(summary = "自定义过滤器添加", tags = {"v1.0.0"}, description = "添加自定义过滤器")
    @PostMapping(value = "/insert")
    public Result<String> insert(@RequestBody @Valid ManageCustomFilterVo vo) {
        service.save(vo);
        return ok(I18nUtil.get("Controller.InsertSuccess"));
    }


    @Operation(summary = "通过自定义过滤器id修改", tags = {"v1.0.0"}, description = "修改自定义过滤器")
    @Parameter(in = ParameterIn.PATH, description = "自定义过滤器id", name = "customFilterId", required = true)
    @PutMapping(value = "/update/{customFilterId}")
    public Result<String> update(@PathVariable(required = false) @PathNotBlankOrNull(message = "自定义过滤器id不能为空") String customFilterId,
                                 @RequestBody @Valid ManageCustomFilterVo vo) {
        service.updateById(customFilterId, vo);
        return ok(I18nUtil.get("Controller.UpdateSuccess"));
    }


    @Operation(summary = "自定义过滤器逻辑删除", tags = {"v1.0.0"}, description = "删除自定义过滤器")
    @Parameter(in = ParameterIn.PATH, description = "自定义过滤器id", name = "customFilterId", required = true)
    @DeleteMapping(value = "/delete-one/{customFilterId}")
    public Result<String> deleteById(@PathVariable(required = false) @PathNotBlankOrNull(message = "自定义过滤器id不能为空") String customFilterId) {
        service.deleteById(customFilterId);
        return ok(I18nUtil.get("Controller.DeleteSuccess"));
    }


    @Operation(summary = "通过自定义过滤器id查询详情", tags = {"v1.0.0"}, description = "查询自定义过滤器详情")
    @Parameter(in = ParameterIn.PATH, description = "自定义过滤器id", name = "customFilterId", required = true)
    @GetMapping(value = "/select/one/{customFilterId}")
    public Result<ManageCustomFilterDetailDto> getById(@PathVariable(required = false) @PathNotBlankOrNull(message = "自定义过滤器id不能为空") String customFilterId) {
        return ok(service.getById(customFilterId));
    }


    @Operation(summary = "查询服务自定义过滤器", tags = {"v1.0.0"}, description = "查询服务自定义过滤器")
    @Parameter(in = ParameterIn.PATH, description = "服务id", name = "serviceId", required = true)
    @GetMapping(value = "/select/list/{serviceId}")
    public Result<List<ManageCustomFilterListDto>> selectList(@PathVariable(required = false) @PathNotBlankOrNull(message = "服务id不能为空") String serviceId) {
        return ok(service.selectList(serviceId));
    }


    @Operation(summary = "查询服务自定义过滤器(无特殊url信息，并且是有效的)", tags = {"v1.0.0"}, description = "查询服务自定义过滤器(无特殊url信息，并且是有效的)")
    @Parameter(in = ParameterIn.PATH, description = "服务id", name = "serviceId", required = true)
    @GetMapping(value = "/select/list-simple/{serviceId}")
    public Result<List<ManageCustomFilterSimpleDto>> selectSimpleList(@PathVariable(required = false) @PathNotBlankOrNull(message = "服务id不能为空") String serviceId) {
        return ok(service.selectSimpleList(serviceId));
    }


    @Operation(summary = "修改过滤器状态", tags = {"v1.0.0"}, description = "修改过滤器状态")
    @Parameters({
            @Parameter(in = ParameterIn.QUERY, description = "过滤器id", name = "customFilterId", required = true),
            @Parameter(in = ParameterIn.QUERY, description = "操作类型:0-禁止,1-启用", name = "state", required = true)
    })
    @GetMapping(value = "/update-status")
    public Result<String> updateStatus(@RequestParam(required = false) @NotBlank(message = "过滤器id不能为空") String customFilterId,
                                       @RequestParam(required = false) @NotNull(message = "操作类型不能为空") @Range(min = 0, max = 1, message = "操作类型只能为0、1") Integer state) {
        service.updateStatus(customFilterId, state);
        return ok(I18nUtil.get("Controller.UpdateSuccess"));
    }
}
