package com.anyilanxin.skillfull.system.modules.manage.controller;

import com.anyilanxin.skillfull.corecommon.base.Result;
import com.anyilanxin.skillfull.corecommon.model.system.ManageSwaggerInfoModel;
import com.anyilanxin.skillfull.corecommon.model.web.WebSecurityModel;
import com.anyilanxin.skillfull.corecommon.utils.I18nUtil;
import com.anyilanxin.skillfull.corecommon.validation.annotation.PathNotBlankOrNull;
import com.anyilanxin.skillfull.coremvc.base.controller.BaseController;
import com.anyilanxin.skillfull.database.datasource.base.service.dto.PageDto;
import com.anyilanxin.skillfull.gatewayrpc.feign.GatewayRemoteService;
import com.anyilanxin.skillfull.gatewayrpc.model.RouteResponseModel;
import com.anyilanxin.skillfull.system.modules.manage.controller.vo.InstancePageVo;
import com.anyilanxin.skillfull.system.modules.manage.controller.vo.ManageServicePageVo;
import com.anyilanxin.skillfull.system.modules.manage.controller.vo.ManageServiceVo;
import com.anyilanxin.skillfull.system.modules.manage.controller.vo.NacosUpdateInstanceVo;
import com.anyilanxin.skillfull.system.modules.manage.service.ICustomNacosNamingService;
import com.anyilanxin.skillfull.system.modules.manage.service.IManageServiceService;
import com.anyilanxin.skillfull.system.modules.manage.service.IManageSyncService;
import com.anyilanxin.skillfull.system.modules.manage.service.INacosService;
import com.anyilanxin.skillfull.system.modules.manage.service.dto.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

/**
 * 服务管理(ManageService)控制层
 *
 * @author zxiaozhou
 * @copyright zxiaozhou（https://skillfull.divisu.com）
 * @date 2021-12-19 00:22:19
 * @since JDK1.8
 */
@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@Tag(name = "GatewayManage", description = "网关管理")
@RequestMapping(value = "/manage-service", produces = MediaType.APPLICATION_JSON_VALUE)
public class ManageServiceController extends BaseController {
    private final IManageServiceService service;
    private final GatewayRemoteService remoteService;
    private final INacosService nacosService;
    private final IManageSyncService syncService;
    private final ICustomNacosNamingService nacosNamingService;

    @Operation(summary = "服务管理添加", tags = {"v1.0.0"}, description = "添加服务管理")
    @PostMapping(value = "/insert")
    public Result<String> insert(@RequestBody @Valid ManageServiceVo vo) {
        service.save(vo);
        return ok(I18nUtil.get("Controller.InsertSuccess"));
    }

    @Operation(summary = "获取有效的服务列表", tags = {"v1.0.0"}, description = "获取有效的服务列表")
    @GetMapping(value = "/select/list-valid-info")
    public Result<List<ValidServiceInfoDto>> getValidServiceInfo() {
        return ok(service.getValidServiceInfo());
    }

    @Operation(summary = "通过服务id修改", tags = {"v1.0.0"}, description = "修改服务管理")
    @Parameter(in = ParameterIn.PATH, description = "服务id", name = "serviceId", required = true)
    @PutMapping(value = "/update/{serviceId}")
    public Result<String> update(@PathVariable(required = false) @PathNotBlankOrNull(message = "服务id不能为空") String serviceId,
                                 @RequestBody @Valid ManageServiceVo vo) {
        service.updateById(serviceId, vo);
        return ok(I18nUtil.get("Controller.UpdateSuccess"));
    }


    @Operation(summary = "获取需要聚合swagger的路由信息", tags = {"v1.0.0"}, description = "获取需要聚合swagger的路由信息")
    @GetMapping(value = "/select/swagger-info")
    @PreAuthorize("permitAll()")
    public Result<Map<String, ManageSwaggerInfoModel>> selectSwaggerInfo() {
        return ok(service.selectSwaggerInfo());
    }


    @Operation(summary = "服务管理逻辑删除", tags = {"v1.0.0"}, description = "删除服务管理")
    @Parameter(in = ParameterIn.PATH, description = "服务id", name = "serviceId", required = true)
    @DeleteMapping(value = "/delete-one/{serviceId}")
    public Result<String> deleteById(@PathVariable(required = false) @PathNotBlankOrNull(message = "服务id不能为空") String serviceId) {
        service.deleteById(serviceId);
        return ok(I18nUtil.get("Controller.DeleteSuccess"));
    }


    @Operation(summary = "通过服务id查询详情", tags = {"v1.0.0"}, description = "查询服务管理详情")
    @Parameter(in = ParameterIn.PATH, description = "服务id", name = "serviceId", required = true)
    @GetMapping(value = "/select/one/{serviceId}")
    public Result<ManageServiceDto> getById(@PathVariable(required = false) @PathNotBlankOrNull(message = "服务id不能为空") String serviceId) {
        return ok(service.getById(serviceId));
    }


    @Operation(summary = "服务管理分页查询", tags = {"v1.0.0"}, description = "分页查询服务管理")
    @PostMapping(value = "/select/page")
    public Result<PageDto<ManageServicePageDto>> selectPage(@RequestBody ManageServicePageVo vo) {
        return ok(service.pageByModel(vo));
    }


    @Operation(summary = "服务实例分页查询", tags = {"v1.0.0"}, description = "服务实例分页查询")
    @PostMapping(value = "/select-instance/page")
    public Result<PageDto<ServiceInstancePageDto>> selectInstancePage(@RequestBody InstancePageVo vo) {
        return ok(nacosNamingService.selectInstancePage(vo));
    }

    @Operation(summary = "服务实例上下线", tags = {"v1.0.0"}, description = "删除服务实例")
    @PostMapping(value = "/update/service-instance")
    Result<String> updateInstance(@RequestBody @Valid NacosUpdateInstanceVo vo) {
        nacosService.updateInstance(vo);
        return ok("服务实例上下线成功,稍后请刷新");
    }


    @Operation(summary = "获取系统统计", tags = {"v1.0.0"}, description = "获取系统统计")
    @GetMapping(value = "/stat")
    public Result<SystemStatDto> systemStat() {
        return ok(service.systemStat());
    }


    @Operation(summary = "手动刷新网关", tags = {"v1.0.0"}, description = "手动刷新网关")
    @GetMapping(value = "/refresh-gateway")
    public Result<String> refreshGateway() {
        syncService.reloadRoute(true);
        return ok("刷新网关成功");
    }


    @Operation(summary = "获取请求安全信息", tags = {"v1.0.0"}, description = "获取请求安全信息")
    @GetMapping(value = "/select/base-security")
    public Result<WebSecurityModel> getBaseSecurity() {
        return remoteService.getBaseSecurity();
    }


    @Operation(summary = "查询网关路由", tags = {"v1.0.0"}, description = "查询网关路由")
    @GetMapping(value = "/select/gateway-router")
    public Result<List<RouteResponseModel>> getGatewayRouter() {
        return remoteService.getRoutes();
    }


    @Operation(summary = "请求安全基础信息手动刷新", tags = {"v1.0.0"}, description = "请求安全基础信息手动刷新")
    @Parameter(in = ParameterIn.PATH, description = "请求序列", name = "serialNumber", required = true)
    @GetMapping("/select/base-security/refresh/{serialNumber}")
    Result<WebSecurityModel> getRefreshBaseSecurity(@PathVariable @PathNotBlankOrNull(message = "请求序列不能为空") String serialNumber) {
        return remoteService.getRefreshBaseSecurity(serialNumber);
    }
}
