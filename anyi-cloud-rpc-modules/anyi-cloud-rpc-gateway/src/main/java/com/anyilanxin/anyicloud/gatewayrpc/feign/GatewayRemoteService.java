

package com.anyilanxin.anyicloud.gatewayrpc.feign;

import cn.hutool.json.JSONObject;
import com.anyilanxin.anyicloud.corecommon.base.Result;
import com.anyilanxin.anyicloud.corecommon.constant.ServiceConstant;
import com.anyilanxin.anyicloud.corecommon.feign.FeignFallback;
import com.anyilanxin.anyicloud.corecommon.model.stream.router.SystemRouterModel;
import com.anyilanxin.anyicloud.corecommon.model.web.WebSecurityModel;
import com.anyilanxin.anyicloud.corecommon.validation.annotation.PathNotBlankOrNull;
import com.anyilanxin.anyicloud.gatewayrpc.model.RouteResponseModel;

import java.util.List;
import javax.validation.Valid;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

/**
 * 网关服务feign
 *
 * @author zxh
 * @date 2020-09-12 16:54
 * @since 1.0.0
 */
@FeignClient(value = ServiceConstant.GATEWAY_SERVICE, path = ServiceConstant.GATEWAY_SERVICE_PATH, fallbackFactory = FeignFallback.class)
public interface GatewayRemoteService {

    /**
     * 查询网关路由
     *
     * @return Result<List < GatewayServiceRouteDto>> ${@link Result<List<  RouteResponseModel  >>} 结果
     * @author zxh
     * @date 2020-09-15 17:03
     */
    @GetMapping("/route/select/list")
    Result<List<RouteResponseModel>> getRoutes();


    /**
     * 查询网关原始路由路由
     *
     * @return Result<List < JSONObject>> ${@link Result<List<JSONObject>>} 结果
     * @author zxh
     * @date 2020-09-15 17:03
     */
    @GetMapping("/route/select/list-original")
    Result<List<JSONObject>> getOriginalRoutes();


    /**
     * 添加路由
     *
     * @return Result<String> ${@link Result<String>} 结果
     * @author zxh
     * @date 2020-09-15 17:03
     */
    @PostMapping("/route/add")
    Result<String> addRoute(@RequestBody @Valid SystemRouterModel vo);


    /**
     * 更新路由
     *
     * @return Result<String> ${@link Result<String>} 结果
     * @author zxh
     * @date 2020-09-15 17:03
     */
    @PostMapping("/route/update")
    Result<String> updateRoute(@RequestBody @Valid SystemRouterModel vo);


    /**
     * 删除路由
     *
     * @param routeCode ${@link String} 路由编码
     * @return Result<String> ${@link Result<String>} 结果
     * @author zxh
     * @date 2020-09-15 17:03
     */
    @DeleteMapping("/route/delete/{routeCode}")
    Result<String> deleteRoute(@PathVariable @PathNotBlankOrNull(message = "路由编码不能为空") String routeCode);


    /**
     * 获取请求安全基础信息(需要路由设置使用加密传输)
     *
     * @return Result<WebSecurityModel> ${@link Result< WebSecurityModel >} 结果
     * @author zxh
     * @date 2020-09-15 17:03
     */
    @GetMapping("/tools/select/base-security")
    Result<WebSecurityModel> getBaseSecurity();


    /**
     * 获取请求安全基础信息手动刷新(需要路由设置使用加密传输)
     *
     * @return Result<WebSecurityModel> ${@link Result< WebSecurityModel >} 结果
     * @author zxh
     * @date 2020-09-15 17:03
     */
    @GetMapping("/tools/select/base-security/refresh/{serialNumber}")
    Result<WebSecurityModel> getRefreshBaseSecurity(@PathVariable @PathNotBlankOrNull(message = "请求序列不能为空") String serialNumber);
}
