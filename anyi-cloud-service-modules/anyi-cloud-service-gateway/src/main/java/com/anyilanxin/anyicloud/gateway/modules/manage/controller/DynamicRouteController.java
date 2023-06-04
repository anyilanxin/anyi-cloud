/*
 * Copyright (c) 2023-present ZHOUXUANHONG(安一老厨)<anyilanxin@aliyun.com>
 *
 * AnYi Cloud Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * AnYi Cloud 采用APACHE LICENSE 2.0开源协议，您在使用过程中，需要注意以下几点：
 *   1.请不要删除和修改根目录下的LICENSE.txt文件；
 *   2.请不要删除和修改 AnYi Cloud 源码头部的版权声明；
 *   3.请保留源码和相关描述文件的项目出处，作者声明等；
 *   4.分发源码时候，请注明软件出处 https://github.com/anyilanxin/anyi-cloud；
 *   5.在修改包名，模块名称，项目代码等时，请注明软件出处 https://github.com/anyilanxin/anyi-cloud；
 *   6.本软件不允许在国家法律规定范围外使用，如出现违法行为原作者本人不承担任何法律风险；
 *   7.进行商用时，不得基于AnYi Cloud的基础，修改包装而成一个与AnYi Cloud EE、AnYi Zeebe EE、AnYi Standalone EE功能类似的程序，进行销售或发布，参与同类软件产品市场的竞争；
 *   8.本软件使用的第三方依赖皆为开源软件，如需要修改第三方源码请遵循第三方源码附带开源协议；
 *   9.本软件流程部分请遵循camunda开源协议：
 *     https://docs.camunda.org/manual/latest/introduction/third-party-libraries
 *     https://github.com/camunda/camunda-bpm-platform/blob/master/LICENSE
 *   10.若您的项目无法满足以上几点，可申请商业授权。
 */
package com.anyilanxin.anyicloud.gateway.modules.manage.controller;

import com.anyilanxin.anyicloud.corecommon.base.Result;
import com.anyilanxin.anyicloud.corecommon.model.stream.router.SystemRouterModel;
import com.anyilanxin.anyicloud.corecommon.utils.I18nUtil;
import com.anyilanxin.anyicloud.corecommon.validation.annotation.PathNotBlankOrNull;
import com.anyilanxin.anyicloud.corewebflux.base.controller.BaseController;
import com.anyilanxin.anyicloud.corewebflux.utils.ServletUtils;
import com.anyilanxin.anyicloud.gateway.modules.manage.service.IDynamicRouteService;
import com.anyilanxin.anyicloud.gatewayrpc.model.RouteResponseModel;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.cloud.gateway.route.RouteDefinitionLocator;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

/**
 * 动态路由controller
 *
 * @author zxh
 * @date 2020-09-10 21:14
 * @since 1.0.0
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
    Mono<Result<String>> addRoute(@RequestBody @Valid SystemRouterModel vo, final ServerHttpRequest request) {
        ServletUtils.setServerHttpRequest(request);
        service.addRoute(vo);
        return ok(I18nUtil.get("Controller.BatchDeleteSuccess"));
    }


    @Operation(summary = "更新路由", tags = {"v1.0.0"}, description = "更新路由")
    @PutMapping("/update")
    Mono<Result<String>> updateRoute(@RequestBody @Valid SystemRouterModel vo, final ServerHttpRequest request) {
        ServletUtils.setServerHttpRequest(request);
        service.updateRoute(vo);
        return ok("更新成功");
    }


    @Operation(summary = "删除路由", tags = {"v1.0.0"}, description = "删除路由")
    @Parameter(in = ParameterIn.PATH, description = "路由编码", name = "routeCode", required = true)
    @DeleteMapping("/delete/{routeCode}")
    Mono<Result<String>> deleteRoute(@PathVariable @PathNotBlankOrNull(message = "路由编码不能为空") String routeCode, final ServerHttpRequest request) {
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
