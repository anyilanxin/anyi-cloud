package com.anyilanxin.skillfull.system.modules.manage.service;

import com.anyilanxin.skillfull.database.datasource.base.service.BaseService;
import com.anyilanxin.skillfull.system.modules.manage.controller.vo.ManageRouteCustomFilterVo;
import com.anyilanxin.skillfull.system.modules.manage.entity.ManageRouteCustomFilterEntity;
import com.anyilanxin.skillfull.system.modules.manage.service.dto.ManageCustomFilterSimpleDto;
import com.anyilanxin.skillfull.system.modules.manage.service.dto.ManageRouteCustomFilterDto;
import com.anyilanxin.skillfull.system.modules.manage.service.dto.RouterCustomFilterDto;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 路由-自定义过滤器表(ManageRouteCustomFilter)业务层接口
 *
 * @author zxiaozhou
 * @copyright zxiaozhou（https://skillfull.divisu.com）
 * @date 2021-12-19 00:22:17
 * @since JDK1.8
 */
public interface IManageRouteCustomFilterService extends BaseService<ManageRouteCustomFilterEntity> {
    /**
     * 保存
     *
     * @param customFilters ${@link List< ManageRouteCustomFilterVo >} 自定义过滤器
     * @param routerId      ${@link String} 路由id
     * @param override      ${@link Boolean} 是否覆盖:true-覆盖,false-不覆盖
     * @throws RuntimeException ${@link RuntimeException}
     * @author zxiaozhou
     * @date 2021-12-19 00:22:17
     */
    void save(List<ManageRouteCustomFilterVo> customFilters, String routerId, boolean override) throws RuntimeException;

    /**
     * 通过id查询详情
     *
     * @param routeCustomFilterId ${@link String} 路由自定义过滤器id
     * @return ManageRouteCustomFilterDto ${@link ManageRouteCustomFilterDto} 查询结果
     * @throws RuntimeException ${@link RuntimeException}
     * @author zxiaozhou
     * @date 2021-12-19 00:22:17
     */
    ManageRouteCustomFilterDto getById(String routeCustomFilterId) throws RuntimeException;


    /**
     * 通过routerIds获取过滤器
     *
     * @param routerIds ${@link Set<String>} 路由ids
     * @return Map<String, List < ManageCustomFilterSimpleDto>> ${@link Map<String,List< ManageCustomFilterSimpleDto >>}
     * @author zxiaozhou
     * @date 2021-12-22 04:36
     */
    Map<String, List<ManageCustomFilterSimpleDto>> getByRouterIds(Set<String> routerIds) throws RuntimeException;


    /**
     * 通过routerIds获取网关过滤器
     *
     * @param routerIds ${@link Set<String>} 路由ids
     * @return Map<String, List < ManageCustomFilterSimpleDto>> ${@link Map<String,List<ManageCustomFilterSimpleDto>>} routerId:过滤器信息
     * @author zxiaozhou
     * @date 2021-12-22 04:36
     */
    Map<String, RouterCustomFilterDto> getGatewayByRouterIds(Set<String> routerIds) throws RuntimeException;


    /**
     * 通过过routerId获取
     *
     * @param routerId ${@link String} 路由id
     * @return List<ManageCustomFilterSimpleDto> ${@link List<ManageCustomFilterSimpleDto>}
     * @author zxiaozhou
     * @date 2021-12-22 04:37
     */
    List<ManageCustomFilterSimpleDto> getByRouterId(String routerId) throws RuntimeException;


    /**
     * 通过routerId删除
     *
     * @param routerId ${@link String} 路由id
     * @throws RuntimeException ${@link RuntimeException}
     * @author zxiaozhou
     * @date 2021-12-19 00:22:17
     */
    void deleteByRouterId(String routerId) throws RuntimeException;


    /**
     * 通过routerIds删除
     *
     * @param routerIds ${@link Set <String>} 路由ids
     * @throws RuntimeException ${@link RuntimeException}
     * @author zxiaozhou
     * @date 2021-12-19 00:22:17
     */
    void deleteByRouterIds(Set<String> routerIds) throws RuntimeException;
}
