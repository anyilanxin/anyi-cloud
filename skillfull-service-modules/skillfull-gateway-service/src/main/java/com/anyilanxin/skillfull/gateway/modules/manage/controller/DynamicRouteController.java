// +----------------------------------------------------------------------
// | SkillFull快速开发平台 [ SkillFull ]
// +----------------------------------------------------------------------
// | 版权所有 2020~2021 zxiaozhou
// +----------------------------------------------------------------------
// | 官方网站: https://www.divisu.com
// +----------------------------------------------------------------------
// | 作者: zxiaozhou <z7630853@163.com>
// +----------------------------------------------------------------------
package com.anyilanxin.skillfull.gateway.modules.manage.controller;

import com.anyilanxin.skillfull.corecommon.base.Result;
import com.anyilanxin.skillfull.corecommon.model.stream.router.SystemRouterModel;
import com.anyilanxin.skillfull.corecommon.utils.I18nUtil;
import com.anyilanxin.skillfull.corecommon.validation.annotation.PathNotBlankOrNull;
import com.anyilanxin.skillfull.corewebflux.base.controller.BaseController;
import com.anyilanxin.skillfull.corewebflux.utils.ServletUtils;
import com.anyilanxin.skillfull.gateway.modules.manage.service.IDynamicRouteService;
import com.anyilanxin.skillfull.gatewayapi.model.RouteResponseModel;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.cloud.gateway.route.RouteDefinitionLocator;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import java.util.List;

/**
 * 动态路由controller
 *
 * @author zxiaozhou
 * @date 2020-09-10 21:14
 * @since JDK11
 */
@Tag(name = "DynamicRoute", description = "动态路由")
@RestController
@RequestMapping(value = "/route", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
@Validated
public class DynamicRouteController extends BaseController {
    private final IDynamicRouteService service;
    private final RouteDefinitionLocator routeDefinitionLocator;


    @Operation(summary = "添加路由", tags = {"v1.0.0"}, description = "添加路由")
    @PostMapping("/add")
    Mono<Result<String>> addRoute(@RequestBody @Valid SystemRouterModel vo,
                                  final ServerHttpRequest request) {
        ServletUtils.setServerHttpRequest(request);
        service.addRoute(vo);
        return ok(I18nUtil.get("Controller.BatchDeleteSuccess"));
    }


    @Operation(summary = "更新路由", tags = {"v1.0.0"}, description = "更新路由")
    @PutMapping("/update")
    Mono<Result<String>> updateRoute(@RequestBody @Valid SystemRouterModel vo,
                                     final ServerHttpRequest request) {
        ServletUtils.setServerHttpRequest(request);
        service.updateRoute(vo);
        return ok("更新成功");
    }


    @Operation(summary = "删除路由", tags = {"v1.0.0"}, description = "删除路由")
    @Parameter(in = ParameterIn.PATH, description = "路由编码", name = "routeCode", required = true)
    @DeleteMapping("/delete/{routeCode}")
    Mono<Result<String>> deleteRoute(@PathVariable @PathNotBlankOrNull(message = "路由编码不能为空") String routeCode,
                                     final ServerHttpRequest request) {
        ServletUtils.setServerHttpRequest(request);
        service.deleteRoute(routeCode);
        return ok(I18nUtil.get("Controller.DeleteSuccess"));
    }


    @Operation(summary = "查询路由", tags = {"v1.0.0"}, description = "查询路由")
    @GetMapping("/select/list")
    Mono<Result<List<RouteResponseModel>>> getRoutes() {
        return service.getRoutes().collectList().map(BaseController::getResult);
    }


    @Operation(summary = "查询原始路由", tags = {"v1.0.0"}, description = "查询原始路由")
    @GetMapping("/select/list-original")
    Mono<Result<List<RouteDefinition>>> getOriginalRoutes() {
        return routeDefinitionLocator.getRouteDefinitions().collectList().map(BaseController::getResult);
    }

}
