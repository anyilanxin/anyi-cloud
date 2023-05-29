

package com.anyilanxin.anyicloud.system.modules.manage.service;

import com.anyilanxin.anyicloud.database.datasource.base.service.BaseService;
import com.anyilanxin.anyicloud.system.modules.manage.controller.vo.ManageCustomFilterVo;
import com.anyilanxin.anyicloud.system.modules.manage.entity.ManageCustomFilterEntity;
import com.anyilanxin.anyicloud.system.modules.manage.service.dto.ManageCustomFilterDetailDto;
import com.anyilanxin.anyicloud.system.modules.manage.service.dto.ManageCustomFilterListDto;
import com.anyilanxin.anyicloud.system.modules.manage.service.dto.ManageCustomFilterSimpleDto;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 自定义过滤器(ManageCustomFilter)业务层接口
 *
 * @author zxh
 * @copyright zhouxuanhong（https://anyilanxin.com）
 * @date 2021-12-19 00:22:15
 * @since 1.0.0
 */
public interface IManageCustomFilterService extends BaseService<ManageCustomFilterEntity> {
    /**
     * 保存
     *
     * @param vo ${@link ManageCustomFilterVo} 自定义过滤器保存
     * @throws RuntimeException ${@link RuntimeException}
     * @author zxh
     * @date 2021-12-19 00:22:15
     */
    void save(ManageCustomFilterVo vo) throws RuntimeException;


    /**
     * 通过id更新
     *
     * @param vo             ${@link ManageCustomFilterVo} 自定义过滤器更新
     * @param customFilterId ${@link String} 自定义过滤器id
     * @param vo             ${@link ManageCustomFilterVo} 自定义过滤器更新
     * @throws RuntimeException ${@link RuntimeException}
     * @author zxh
     * @date 2021-12-19 00:22:15
     */
    void updateById(String customFilterId, ManageCustomFilterVo vo) throws RuntimeException;


    /**
     * 通过id查询详情
     *
     * @param customFilterId ${@link String} 自定义过滤器id
     * @return ManageCustomFilterDetailDto ${@link ManageCustomFilterDetailDto} 查询结果
     * @throws RuntimeException ${@link RuntimeException}
     * @author zxh
     * @date 2021-12-19 00:22:15
     */
    ManageCustomFilterDetailDto getById(String customFilterId) throws RuntimeException;


    /**
     * 通过customFilterId删除
     *
     * @param customFilterId ${@link String} 自定义过滤器id
     * @throws RuntimeException ${@link RuntimeException}
     * @author zxh
     * @date 2021-12-19 00:22:15
     */
    void deleteById(String customFilterId) throws RuntimeException;


    /**
     * 通过serviceId删除
     *
     * @param serviceId ${@link String} 服务id
     * @throws RuntimeException ${@link RuntimeException}
     * @author zxh
     * @date 2021-12-19 00:22:15
     */
    void deleteByServiceId(String serviceId) throws RuntimeException;


    /**
     * 查询所有服务自定义过滤器
     *
     * @param serviceId ${@link String} 服务id
     * @return List<ManageCustomFilterDto> ${@link List< ManageCustomFilterListDto >}
     * @author zxh zxiaozhou
     * @date 2021-12-19 09:21
     */
    List<ManageCustomFilterListDto> selectList(String serviceId);


    /**
     * 查询所有服务自定义过滤器(有效的)，并组装为routerId为键的map
     *
     * @param serviceId ${@link String} 服务id
     * @param routerIds ${@link Set<String>} 路由id
     * @return List<ManageCustomFilterDto> ${@link List< ManageCustomFilterListDto >}
     * @author zxh zxiaozhou
     * @date 2021-12-19 09:21
     */
    Map<String, List<ManageCustomFilterSimpleDto>> selectListRouterIds(Set<String> routerIds, String serviceId);


    /**
     * 修改过滤器状态
     *
     * @param customFilterId ${@link String} 过滤器id
     * @param state          ${@link Integer} 操作类型:0-禁止,1-启用
     * @author zxh
     * @date 2021-12-19 15:23
     */
    void updateStatus(String customFilterId, Integer state);


    /**
     * 查询服务自定义过滤器(无特殊url信息)
     *
     * @param serviceId ${@link String} 服务id
     * @return List<ManageCustomFilterSimpleDto> ${@link List<ManageCustomFilterSimpleDto>} 返回结果
     * @author zxh
     * @date 2021-12-19 17:28
     */
    List<ManageCustomFilterSimpleDto> selectSimpleList(String serviceId);
}
