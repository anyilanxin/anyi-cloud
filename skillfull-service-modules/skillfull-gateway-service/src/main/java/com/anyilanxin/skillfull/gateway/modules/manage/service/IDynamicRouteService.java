// +----------------------------------------------------------------------
// | SkillFull快速开发平台 [ SkillFull ]
// +----------------------------------------------------------------------
// | 版权所有 2020~2021 zxiaozhou
// +----------------------------------------------------------------------
// | 官方网站: https://www.divisu.com
// +----------------------------------------------------------------------
// | 作者: zxiaozhou <z7630853@163.com>
// +----------------------------------------------------------------------
package com.anyilanxin.skillfull.gateway.modules.manage.service;

import com.anyilanxin.skillfull.corecommon.model.stream.router.SystemRouterModel;
import com.anyilanxin.skillfull.gateway.modules.manage.controller.vo.GatewayRouteVo;
import com.anyilanxin.skillfull.gatewayapi.model.RouteResponseModel;
import reactor.core.publisher.Flux;

/**
 * 动态路由服务接口
 *
 * @author zxiaozhou
 * @date 2020-09-10 22:41
 * @since JDK11
 */
public interface IDynamicRouteService {
    /**
     * 添加路由
     *
     * @param vo ${@link GatewayRouteVo} 保存参数
     * @throws RuntimeException ${@link RuntimeException}
     * @author zxiaozhou
     * @date 2020-09-10 22:45
     */
    void addRoute(SystemRouterModel vo) throws RuntimeException;


    /**
     * 更新路由
     *
     * @param vo ${@link GatewayRouteVo} 跟新参数
     * @throws RuntimeException ${@link RuntimeException}
     * @author zxiaozhou
     * @date 2020-09-10 22:45
     */
    void updateRoute(SystemRouterModel vo) throws RuntimeException;


    /**
     * 删除路由
     *
     * @param routeId ${@link String} 路由id
     * @throws RuntimeException ${@link RuntimeException}
     * @author zxiaozhou
     * @date 2020-09-11 11:35
     */
    void deleteRoute(String routeId) throws RuntimeException;


    /**
     * 查询路由
     *
     * @return Flux<RouteResponseModel>${@link  Flux<RouteResponseModel>}
     * @throws RuntimeException ${@link RuntimeException}
     * @author zxiaozhou
     * @date 2020-09-10 22:49
     */
    Flux<RouteResponseModel> getRoutes() throws RuntimeException;


    /**
     * 加载或刷新路由
     *
     * @throws RuntimeException ${@link RuntimeException}
     * @author zxiaozhou
     * @date 2020-09-13 00:39
     */
    void loadRoute() throws RuntimeException;

}
