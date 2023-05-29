

package com.anyilanxin.anyicloud.system.modules.manage.service;

import com.anyilanxin.anyicloud.database.datasource.base.service.BaseService;
import com.anyilanxin.anyicloud.system.modules.manage.controller.vo.ManageRouteVo;
import com.anyilanxin.anyicloud.system.modules.manage.entity.ManageRouteEntity;
import com.anyilanxin.anyicloud.system.modules.manage.service.dto.ManageRouteDto;

import java.util.List;

/**
 * 路由(ManageRoute)业务层接口
 *
 * @author zxh
 * @copyright zhouxuanhong（https://anyilanxin.com）
 * @date 2021-12-19 00:22:16
 * @since 1.0.0
 */
public interface IManageRouteService extends BaseService<ManageRouteEntity> {
    /**
     * 保存
     *
     * @param vo ${@link ManageRouteVo} 路由保存
     * @throws RuntimeException ${@link RuntimeException}
     * @author zxh
     * @date 2021-12-19 00:22:16
     */
    void save(ManageRouteVo vo) throws RuntimeException;


    /**
     * 通过id更新
     *
     * @param vo      ${@link ManageRouteVo} 路由更新
     * @param routeId ${@link String} 路由id
     * @param vo      ${@link ManageRouteVo} 路由更新
     * @throws RuntimeException ${@link RuntimeException}
     * @author zxh
     * @date 2021-12-19 00:22:16
     */
    void updateById(String routeId, ManageRouteVo vo) throws RuntimeException;


    /**
     * 条件查询多条
     *
     * @param serviceId ${@link String} 服务id
     * @return List<ManageRouteDto> ${@link List< ManageRouteDto >} 查询结果
     * @throws RuntimeException ${@link RuntimeException}
     * @author zxh
     * @date 2021-12-19 00:22:16
     */
    List<ManageRouteDto> selectList(String serviceId) throws RuntimeException;


    /**
     * 通过id查询详情
     *
     * @param routeId ${@link String} 路由id
     * @return ManageRouteDto ${@link ManageRouteDto} 查询结果
     * @throws RuntimeException ${@link RuntimeException}
     * @author zxh
     * @date 2021-12-19 00:22:16
     */
    ManageRouteDto getById(String routeId) throws RuntimeException;


    /**
     * 通过routeId删除
     *
     * @param routeId ${@link String} 路由id
     * @throws RuntimeException ${@link RuntimeException}
     * @author zxh
     * @date 2021-12-19 00:22:16
     */
    void deleteById(String routeId) throws RuntimeException;


    /**
     * 通过serviceId删除
     *
     * @param serviceId ${@link String} 服务id
     * @throws RuntimeException ${@link RuntimeException}
     * @author zxh
     * @date 2021-12-19 00:22:16
     */
    void deleteByServiceId(String serviceId) throws RuntimeException;


    /**
     * 修改路由状态
     *
     * @param routeId ${@link String} 路由id
     * @param state   ${@link Integer} 操作类型:0-禁止,1-启用
     * @author zxh
     * @date 2021-12-19 17:41
     */
    void updateStatus(String routeId, Integer state);
}
