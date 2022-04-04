// +----------------------------------------------------------------------
// | SkillFull快速开发平台 [ SkillFull ]
// +----------------------------------------------------------------------
// | 版权所有 2020~2021 zxiaozhou
// +----------------------------------------------------------------------
// | 官方网站: https://www.divisu.com
// +----------------------------------------------------------------------
// | 作者: zxiaozhou <z7630853@163.com>
// +----------------------------------------------------------------------
package indi.zxiaozhou.skillfull.system.modules.manage.controller;

import indi.zxiaozhou.skillfull.corecommon.base.Result;
import indi.zxiaozhou.skillfull.corecommon.validation.annotation.PathNotBlankOrNull;
import indi.zxiaozhou.skillfull.coremvc.base.controller.BaseController;
import indi.zxiaozhou.skillfull.system.modules.manage.controller.vo.ManageRouteVo;
import indi.zxiaozhou.skillfull.system.modules.manage.service.IManageRouteService;
import indi.zxiaozhou.skillfull.system.modules.manage.service.dto.ManageRouteDto;
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
 * 路由(ManageRoute)控制层
 *
 * @author zxiaozhou
 * @copyright zxiaozhou（https://divisu.com）
 * @date 2021-12-19 00:22:15
 * @since JDK1.8
 */
@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@Tag(name = "ManageRoute", description = "路由Api接口相关")
@RequestMapping(value = "/manage-route", produces = MediaType.APPLICATION_JSON_VALUE)
public class ManageRouteController extends BaseController {
    private final IManageRouteService service;

    @Operation(summary = "路由添加", tags = {"v1.0.0"}, description = "添加路由")
    @PostMapping(value = "/insert")
    public Result<String> insert(@RequestBody @Valid ManageRouteVo vo) {
        service.save(vo);
        return ok("添加成功");
    }


    @Operation(summary = "通过路由id修改", tags = {"v1.0.0"}, description = "修改路由")
    @Parameter(in = ParameterIn.PATH, description = "路由id", name = "routeId", required = true)
    @PutMapping(value = "/update/{routeId}")
    public Result<String> update(@PathVariable(required = false) @PathNotBlankOrNull(message = "路由id不能为空") String routeId,
                                 @RequestBody @Valid ManageRouteVo vo) {
        service.updateById(routeId, vo);
        return ok("修改成功");
    }


    @Operation(summary = "路由逻辑删除", tags = {"v1.0.0"}, description = "删除路由")
    @Parameter(in = ParameterIn.PATH, description = "路由id", name = "routeId", required = true)
    @DeleteMapping(value = "/delete-one/{routeId}")
    public Result<String> deleteById(@PathVariable(required = false) @PathNotBlankOrNull(message = "路由id不能为空") String routeId) {
        service.deleteById(routeId);
        return ok("删除成功");
    }


    @Operation(summary = "通过路由id查询详情", tags = {"v1.0.0"}, description = "查询路由详情")
    @Parameter(in = ParameterIn.PATH, description = "路由id", name = "routeId", required = true)
    @GetMapping(value = "/select/one/{routeId}")
    public Result<ManageRouteDto> getById(@PathVariable(required = false) @PathNotBlankOrNull(message = "路由id不能为空") String routeId) {
        return ok(service.getById(routeId));
    }


    @Operation(summary = "查询服务路由", tags = {"v1.0.0"}, description = "查询所有服务自定义过滤器")
    @Parameter(in = ParameterIn.PATH, description = "服务id", name = "serviceId", required = true)
    @GetMapping(value = "/select/list/{serviceId}")
    public Result<List<ManageRouteDto>> selectList(@PathVariable(required = false) @PathNotBlankOrNull(message = "服务id不能为空") String serviceId) {
        return ok(service.selectList(serviceId));
    }


    @Operation(summary = "修改路由状态", tags = {"v1.0.0"}, description = "修改路由状态")
    @Parameters({
            @Parameter(in = ParameterIn.QUERY, description = "路由id", name = "routeId", required = true),
            @Parameter(in = ParameterIn.QUERY, description = "操作类型:0-禁止,1-启用", name = "state", required = true)
    })
    @GetMapping(value = "/update-status")
    public Result<String> updateStatus(@RequestParam(required = false) @NotBlank(message = "路由id不能为空") String routeId,
                                       @RequestParam(required = false) @NotNull(message = "操作类型不能为空") @Range(min = 0, max = 1, message = "操作类型只能为0、1") Integer state) {
        service.updateStatus(routeId, state);
        return ok("修改成功");
    }
}
