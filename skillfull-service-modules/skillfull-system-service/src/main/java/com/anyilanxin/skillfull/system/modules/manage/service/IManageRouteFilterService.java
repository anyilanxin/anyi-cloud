package com.anyilanxin.skillfull.system.modules.manage.service;

import com.anyilanxin.skillfull.database.datasource.base.service.BaseService;
import com.anyilanxin.skillfull.system.modules.manage.controller.vo.ManageRouteFilterVo;
import com.anyilanxin.skillfull.system.modules.manage.entity.ManageRouteFilterEntity;
import com.anyilanxin.skillfull.system.modules.manage.service.dto.ManageRouteFilterDto;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 路由过滤器(ManageRouteFilter)业务层接口
 *
 * @author zxiaozhou
 * @copyright zxiaozhou（https://skillfull.divisu.com）
 * @date 2021-12-19 10:37:42
 * @since JDK1.8
 */
public interface IManageRouteFilterService extends BaseService<ManageRouteFilterEntity> {
    /**
     * 保存
     *
     * @param vos       ${@link List< ManageRouteFilterVo >} 待保存数据
     * @param serviceId ${@link String} 服务id
     * @param override  ${@link Boolean} 是否覆盖:true-覆盖,false-不覆盖
     * @param routerId  ${@link String} 路由id
     * @throws RuntimeException ${@link RuntimeException}
     * @author zxiaozhou
     * @date 2021-12-19 10:37:42
     */
    void save(List<ManageRouteFilterVo> vos, String routerId, String serviceId, boolean override) throws RuntimeException;

    /**
     * 通过id查询详情
     *
     * @param filterId ${@link String} 过滤器id
     * @return ManageRouteFilterDto ${@link ManageRouteFilterDto} 查询结果
     * @throws RuntimeException ${@link RuntimeException}
     * @author zxiaozhou
     * @date 2021-12-19 10:37:42
     */
    ManageRouteFilterDto getById(String filterId) throws RuntimeException;


    /**
     * 通过routeId查询详情
     *
     * @param routeId ${@link String} 路由id
     * @return ManageRouteFilterDto ${@link ManageRouteFilterDto} 查询结果
     * @throws RuntimeException ${@link RuntimeException}
     * @author zxiaozhou
     * @date 2021-12-19 10:37:42
     */
    List<ManageRouteFilterDto> getByRouteId(String routeId) throws RuntimeException;


    /**
     * 通过routeIds查询详情
     *
     * @param routeIds ${@link Set<String>} 路由ids
     * @return ManageRouteFilterDto ${@link ManageRouteFilterDto} 查询结果
     * @throws RuntimeException ${@link RuntimeException}
     * @author zxiaozhou
     * @date 2021-12-19 10:37:42
     */
    Map<String, List<ManageRouteFilterDto>> getByRouteId(Set<String> routeIds) throws RuntimeException;


    /**
     * 通过routerId删除
     *
     * @param routerId ${@link String} 路由id
     * @throws RuntimeException ${@link RuntimeException}
     * @author zxiaozhou
     * @date 2021-12-19 10:37:42
     */
    void deleteByRouterId(String routerId) throws RuntimeException;

    /**
     * 通过routerIds删除
     *
     * @param routerIds ${@link Set<String>} 路由ids
     * @throws RuntimeException ${@link RuntimeException}
     * @author zxiaozhou
     * @date 2021-12-19 10:37:42
     */
    void deleteByRouterIds(Set<String> routerIds) throws RuntimeException;
}
