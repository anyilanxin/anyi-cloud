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

package com.anyilanxin.anyicloud.system.modules.manage.controller;

import com.anyilanxin.anyicloud.corecommon.base.AnYiResult;
import com.anyilanxin.anyicloud.corecommon.model.common.AnYiPageResult;
import com.anyilanxin.anyicloud.corecommon.model.system.ManageSwaggerInfoModel;
import com.anyilanxin.anyicloud.corecommon.model.web.WebSecurityModel;
import com.anyilanxin.anyicloud.corecommon.utils.AnYiI18nUtil;
import com.anyilanxin.anyicloud.corecommon.validation.annotation.PathNotBlankOrNull;
import com.anyilanxin.anyicloud.coremvc.base.controller.AnYiBaseController;
import com.anyilanxin.anyicloud.gatewayadapter.model.RouteResponseModel;
import com.anyilanxin.anyicloud.gatewayrpcadapter.rpc.GatewayRemoteRpc;
import com.anyilanxin.anyicloud.system.modules.manage.controller.vo.ManageServicePageQuery;
import com.anyilanxin.anyicloud.system.modules.manage.controller.vo.ManageServiceVo;
import com.anyilanxin.anyicloud.system.modules.manage.service.IManageServiceService;
import com.anyilanxin.anyicloud.system.modules.manage.service.IManageSyncService;
import com.anyilanxin.anyicloud.system.modules.manage.service.dto.ManageServiceDto;
import com.anyilanxin.anyicloud.system.modules.manage.service.dto.ManageServicePageDto;
import com.anyilanxin.anyicloud.system.modules.manage.service.dto.SystemStatDto;
import com.anyilanxin.anyicloud.system.modules.manage.service.dto.ValidServiceInfoDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 服务管理(ManageService)控制层
 *
 * @author zxh
 * @copyright zhouxuanhong（https://anyilanxin.com）
 * @date 2021-12-19 00:22:19
 * @since 1.0.0
 */
@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@Tag(name = "GatewayManage", description = "网关管理")
@RequestMapping(value = "/manage-service", produces = MediaType.APPLICATION_JSON_VALUE)
public class ManageServiceController extends AnYiBaseController {
    private final IManageServiceService service;
    private final GatewayRemoteRpc remoteService;
    private final IManageSyncService syncService;

    @Operation(summary = "服务管理添加", tags = {"v1.0.0"}, description = "添加服务管理")
    @PostMapping(value = "/insert")
    public AnYiResult<String> insert(@RequestBody @Valid ManageServiceVo vo) {
        service.save(vo);
        return ok(AnYiI18nUtil.get("Controller.InsertSuccess"));
    }


    @Operation(summary = "获取有效的服务列表", tags = {"v1.0.0"}, description = "获取有效的服务列表")
    @GetMapping(value = "/select/list-valid-info")
    public AnYiResult<List<ValidServiceInfoDto>> getValidServiceInfo() {
        return ok(service.getValidServiceInfo());
    }


    @Operation(summary = "通过服务id修改", tags = {"v1.0.0"}, description = "修改服务管理")
    @Parameter(in = ParameterIn.PATH, description = "服务id", name = "serviceId", required = true)
    @PutMapping(value = "/update/{serviceId}")
    public AnYiResult<String> update(@PathVariable(required = false) @PathNotBlankOrNull(message = "服务id不能为空") String serviceId, @RequestBody @Valid ManageServiceVo vo) {
        service.updateById(serviceId, vo);
        return ok(AnYiI18nUtil.get("Controller.UpdateSuccess"));
    }


    @Operation(summary = "获取需要聚合swagger的路由信息", tags = {"v1.0.0"}, description = "获取需要聚合swagger的路由信息")
    @GetMapping(value = "/select/swagger-info")
    @PreAuthorize("permitAll()")
    public AnYiResult<Map<String, ManageSwaggerInfoModel>> selectSwaggerInfo() {
        return ok(service.selectSwaggerInfo());
    }


    @Operation(summary = "服务管理逻辑删除", tags = {"v1.0.0"}, description = "删除服务管理")
    @Parameter(in = ParameterIn.PATH, description = "服务id", name = "serviceId", required = true)
    @DeleteMapping(value = "/delete-one/{serviceId}")
    public AnYiResult<String> deleteById(@PathVariable(required = false) @PathNotBlankOrNull(message = "服务id不能为空") String serviceId) {
        service.deleteById(serviceId);
        return ok(AnYiI18nUtil.get("Controller.DeleteSuccess"));
    }


    @Operation(summary = "通过服务id查询详情", tags = {"v1.0.0"}, description = "查询服务管理详情")
    @Parameter(in = ParameterIn.PATH, description = "服务id", name = "serviceId", required = true)
    @GetMapping(value = "/select/one/{serviceId}")
    public AnYiResult<ManageServiceDto> getById(@PathVariable(required = false) @PathNotBlankOrNull(message = "服务id不能为空") String serviceId) {
        return ok(service.getById(serviceId));
    }


    @Operation(summary = "服务管理分页查询", tags = {"v1.0.0"}, description = "分页查询服务管理")
    @PostMapping(value = "/select/page")
    public AnYiResult<AnYiPageResult<ManageServicePageDto>> selectPage(@RequestBody ManageServicePageQuery vo) {
        return ok(service.pageByModel(vo));
    }


    @Operation(summary = "获取系统统计", tags = {"v1.0.0"}, description = "获取系统统计")
    @GetMapping(value = "/stat")
    public AnYiResult<SystemStatDto> systemStat() {
        return ok(service.systemStat());
    }


    @Operation(summary = "手动刷新网关", tags = {"v1.0.0"}, description = "手动刷新网关")
    @GetMapping(value = "/refresh-gateway")
    public AnYiResult<String> refreshGateway() {
        syncService.reloadRoute(true);
        return ok("刷新网关成功");
    }


    @Operation(summary = "获取请求安全信息", tags = {"v1.0.0"}, description = "获取请求安全信息")
    @GetMapping(value = "/select/base-security")
    public AnYiResult<WebSecurityModel> getBaseSecurity() {
        return remoteService.getBaseSecurity();
    }


    @Operation(summary = "查询网关路由", tags = {"v1.0.0"}, description = "查询网关路由")
    @GetMapping(value = "/select/gateway-router")
    public AnYiResult<List<RouteResponseModel>> getGatewayRouter() {
        return remoteService.getRoutes();
    }


    @Operation(summary = "请求安全基础信息手动刷新", tags = {"v1.0.0"}, description = "请求安全基础信息手动刷新")
    @Parameter(in = ParameterIn.PATH, description = "请求序列", name = "serialNumber", required = true)
    @GetMapping("/select/base-security/refresh/{serialNumber}")
    AnYiResult<WebSecurityModel> getRefreshBaseSecurity(@PathVariable @PathNotBlankOrNull(message = "请求序列不能为空") String serialNumber) {
        return remoteService.getRefreshBaseSecurity(serialNumber);
    }
}
