

package com.anyilanxin.anyicloud.process.modules.rbac.service;

import com.anyilanxin.anyicloud.database.datasource.base.service.dto.PageDto;
import com.anyilanxin.anyicloud.process.modules.rbac.controller.vo.TenantQueryPageVoCamunda;
import com.anyilanxin.anyicloud.process.modules.rbac.controller.vo.TenantQueryVo;
import com.anyilanxin.anyicloud.process.modules.rbac.controller.vo.TenantVo;
import com.anyilanxin.anyicloud.process.modules.rbac.service.dto.TenantDto;

import java.util.List;
import java.util.Set;

/**
 * 租户相关
 *
 * @author zxh
 * @date 2021-11-05 17:30
 * @since 1.0.0
 */
public interface ITenantService {
    /**
     * 保存租户
     *
     * @param vo ${@link TenantVo}
     * @author zxh
     * @date 2021-11-05 17:51
     */
    void saveOrUpdate(TenantVo vo) throws RuntimeException;


    /**
     * 获取租户
     *
     * @param tenantId ${@link String}
     * @return TenantDto ${@link TenantDto}
     * @author zxh
     * @date 2021-11-05 17:51
     */
    TenantDto getTenant(String tenantId) throws RuntimeException;


    /**
     * 获取租户信息
     *
     * @param vo ${@link TenantQueryVo}
     * @return List<TenantDto> ${@link List <TenantDto>}
     * @author zxh
     * @date 2021-11-05 17:51
     */
    List<TenantDto> getTenantList(TenantQueryVo vo) throws RuntimeException;


    /**
     * 分页获取租户信息
     *
     * @param vo ${@link TenantQueryPageVoCamunda}
     * @return PageDto<TenantDto>${@link PageDto <TenantDto>}
     * @author zxh
     * @date 2021-11-05 17:51
     */
    PageDto<TenantDto> getTenantPage(TenantQueryPageVoCamunda vo) throws RuntimeException;


    /**
     * 删除租户
     *
     * @param tenantId ${@link String}
     * @author zxh
     * @date 2021-11-05 17:51
     */
    void deleteTenant(String tenantId) throws RuntimeException;


    /**
     * 全量同步租户信息
     *
     * @param voSet ${@link Set <TenantVo>}
     * @author zxh
     * @date 2021-11-07 21:37
     */
    void syncTenant(Set<TenantVo> voSet) throws RuntimeException;
}
