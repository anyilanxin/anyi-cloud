

package com.anyilanxin.anyicloud.system.modules.manage.service;

import com.anyilanxin.anyicloud.database.datasource.base.service.BaseService;
import com.anyilanxin.anyicloud.system.modules.manage.controller.vo.ManageSpecialUrlVo;
import com.anyilanxin.anyicloud.system.modules.manage.entity.ManageSpecialUrlEntity;
import com.anyilanxin.anyicloud.system.modules.manage.service.dto.ManageSpecialUrlDto;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 路由特殊地址(ManageSpecialUrl)业务层接口
 *
 * @author zxh
 * @copyright zhouxuanhong（https://anyilanxin.com）
 * @date 2021-12-19 09:34:50
 * @since 1.0.0
 */
public interface IManageSpecialUrlService extends BaseService<ManageSpecialUrlEntity> {
    /**
     * 先删除后保存新的
     *
     * @param vo             ${@link List< ManageSpecialUrlVo >} 待保存数据
     * @param customFilterId ${@link String} 自定义过滤器id
     * @author zxh zxiaozhou
     * @date 2021-12-19 09:44
     */
    void deleteAndSave(List<ManageSpecialUrlVo> vo, String customFilterId) throws RuntimeException;


    /**
     * 条件查询多条
     *
     * @param customFilterId ${@link String} 自定义过滤器id
     * @return List<ManageSpecialUrlDto> ${@link List< ManageSpecialUrlDto >} 查询结果
     * @throws RuntimeException ${@link RuntimeException}
     * @author zxh
     * @date 2021-12-19 09:34:50
     */
    List<ManageSpecialUrlDto> selectByCustomFilterId(String customFilterId) throws RuntimeException;


    /**
     * 条件查询多条
     *
     * @param customFilterIds ${@link Set<String>} 自定义过滤器ids
     * @return Map<String, List < ManageSpecialUrlDto>> ${@link Map<String,
     * List<ManageSpecialUrlDto>>} 查询结果
     * @throws RuntimeException ${@link RuntimeException}
     * @author zxh
     * @date 2021-12-19 09:34:50
     */
    Map<String, List<ManageSpecialUrlDto>> selectByCustomFilterIds(Set<String> customFilterIds) throws RuntimeException;


    /**
     * 删除特殊url
     *
     * @param customFilterId ${@link String} 自定义过滤器id
     * @author zxh zxiaozhou
     * @date 2021-12-19 09:59
     */
    void deleteByCustomFilterId(String customFilterId) throws RuntimeException;


    /**
     * 删除特殊url
     *
     * @param customFilterIds ${@link Set<String>} 自定义过滤器ids
     * @author zxh zxiaozhou
     * @date 2021-12-19 09:59
     */
    void deleteByCustomFilterIds(Set<String> customFilterIds) throws RuntimeException;
}
