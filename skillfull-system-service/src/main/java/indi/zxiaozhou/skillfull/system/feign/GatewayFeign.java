// +----------------------------------------------------------------------
// | SkillFull快速开发平台 [ SkillFull ]
// +----------------------------------------------------------------------
// | 版权所有 2020~2021 zxiaozhou
// +----------------------------------------------------------------------
// | 官方网站: https://www.divisu.com
// +----------------------------------------------------------------------
// | 作者: zxiaozhou <z7630853@163.com>
// +----------------------------------------------------------------------
package indi.zxiaozhou.skillfull.system.feign;

import cn.hutool.json.JSONObject;
import indi.zxiaozhou.skillfull.corecommon.base.Result;
import indi.zxiaozhou.skillfull.corecommon.base.model.stream.router.SystemRouterModel;
import indi.zxiaozhou.skillfull.corecommon.base.model.web.WebSecurityModel;
import indi.zxiaozhou.skillfull.corecommon.feign.FeignFallback;
import indi.zxiaozhou.skillfull.corecommon.validation.annotation.PathNotBlankOrNull;
import indi.zxiaozhou.skillfull.system.feign.dto.GatewayServiceRouteDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * 网关feign
 *
 * @author zxiaozhou
 * @date 2020-09-12 16:54
 * @since JDK11
 */
@FeignClient(value = "gateway-service", fallbackFactory = FeignFallback.class)
public interface GatewayFeign {

    /**
     * 查询网关路由
     *
     * @return Result<List < GatewayServiceRouteDto>> ${@link Result<List<GatewayServiceRouteDto>>} 结果
     * @author zxiaozhou
     * @date 2020-09-15 17:03
     */
    @GetMapping("/route/select/list")
    Result<List<GatewayServiceRouteDto>> getRoutes();


    /**
     * 查询网关原始路由路由
     *
     * @return Result<List < JSONObject>> ${@link Result<List<JSONObject>>} 结果
     * @author zxiaozhou
     * @date 2020-09-15 17:03
     */
    @GetMapping("/route/select/list-original")
    Result<List<JSONObject>> getOriginalRoutes();


    /**
     * 添加路由
     *
     * @return Result<String> ${@link Result<String>} 结果
     * @author zxiaozhou
     * @date 2020-09-15 17:03
     */
    @PostMapping("/route/add")
    Result<String> addRoute(@RequestBody @Valid SystemRouterModel vo);


    /**
     * 更新路由
     *
     * @return Result<String> ${@link Result<String>} 结果
     * @author zxiaozhou
     * @date 2020-09-15 17:03
     */
    @PostMapping("/route/update")
    Result<String> updateRoute(@RequestBody @Valid SystemRouterModel vo);


    /**
     * 删除路由
     *
     * @param routeCode ${@link String} 路由编码
     * @return Result<String> ${@link Result<String>} 结果
     * @author zxiaozhou
     * @date 2020-09-15 17:03
     */
    @DeleteMapping("/route/delete/{routeCode}")
    Result<String> deleteRoute(@PathVariable @PathNotBlankOrNull(message = "路由编码不能为空") String routeCode);
}
