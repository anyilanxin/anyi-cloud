

package com.anyilanxin.anyicloud.system.modules.manage.service;

import com.anyilanxin.anyicloud.database.datasource.base.service.BaseService;
import com.anyilanxin.anyicloud.system.modules.manage.controller.vo.ManageRoutePredicateVo;
import com.anyilanxin.anyicloud.system.modules.manage.entity.ManageRoutePredicateEntity;
import com.anyilanxin.anyicloud.system.modules.manage.service.dto.ManageRoutePredicateDto;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 路由断言(ManageRoutePredicate)业务层接口
 *
 * @author zxh
 * @copyright zhouxuanhong（https://anyilanxin.com）
 * @date 2021-12-19 10:37:42
 * @since 1.0.0
 */
public interface IManageRoutePredicateService extends BaseService<ManageRoutePredicateEntity> {
    /**
     * 保存
     *
     * @param vos       ${@link List< ManageRoutePredicateVo >} 待保存数据
     * @param serviceId ${@link String} 服务id
     * @param override  ${@link Boolean} 是否覆盖:true-覆盖,false-不覆盖
     * @param routerId  ${@link String} 路由id
     * @throws RuntimeException ${@link RuntimeException}
     * @author zxh
     * @date 2021-12-19 10:37:42
     */
    void save(List<ManageRoutePredicateVo> vos, String routerId, String serviceId, boolean override) throws RuntimeException;


    /**
     * 通过id查询详情
     *
     * @param predicateId ${@link String} 断言id
     * @return ManageRoutePredicateDto ${@link ManageRoutePredicateDto} 查询结果
     * @throws RuntimeException ${@link RuntimeException}
     * @author zxh
     * @date 2021-12-19 10:37:42
     */
    ManageRoutePredicateDto getById(String predicateId) throws RuntimeException;


    /**
     * 通过routeId查询详情
     *
     * @param routeId ${@link String} 路由id
     * @return List<ManageRoutePredicateDto> ${@link List<ManageRoutePredicateDto>} 查询结果
     * @throws RuntimeException ${@link RuntimeException}
     * @author zxh
     * @date 2021-12-19 10:37:42
     */
    List<ManageRoutePredicateDto> getByRouteId(String routeId) throws RuntimeException;


    /**
     * 通过routeIds查询详情
     *
     * @param routeIds ${@link Set<String>} 路由ids
     * @return Map<String, List < ManageRoutePredicateDto>> ${@link Map<String,
     * List<ManageRoutePredicateDto>>} 查询结果
     * @throws RuntimeException ${@link RuntimeException}
     * @author zxh
     * @date 2021-12-19 10:37:42
     */
    Map<String, List<ManageRoutePredicateDto>> getByRouteId(Set<String> routeIds) throws RuntimeException;


    /**
     * 通过routerId删除
     *
     * @param routerId ${@link String} 路由id
     * @throws RuntimeException ${@link RuntimeException}
     * @author zxh
     * @date 2021-12-19 10:37:42
     */
    void deleteByRouterId(String routerId) throws RuntimeException;


    /**
     * 通过routerIds删除
     *
     * @param routerIds ${@link Set<String>} 路由ids
     * @throws RuntimeException ${@link RuntimeException}
     * @author zxh
     * @date 2021-12-19 10:37:42
     */
    void deleteByRouterIds(Set<String> routerIds) throws RuntimeException;
}
