// +----------------------------------------------------------------------
// | SkillFull快速开发平台 [ SkillFull ]
// +----------------------------------------------------------------------
// | 版权所有 2020~2021 zxiaozhou
// +----------------------------------------------------------------------
// | 官方网站: https://www.divisu.com
// +----------------------------------------------------------------------
// | 作者: zxiaozhou <z7630853@163.com>
// +----------------------------------------------------------------------
package com.anyilanxin.skillfull.process.modules.rbac.service;

import com.anyilanxin.skillfull.database.datasource.base.service.dto.PageDto;
import com.anyilanxin.skillfull.process.modules.rbac.controller.vo.TenantQueryPageVoCamunda;
import com.anyilanxin.skillfull.process.modules.rbac.controller.vo.TenantQueryVo;
import com.anyilanxin.skillfull.process.modules.rbac.controller.vo.TenantVo;
import com.anyilanxin.skillfull.process.modules.rbac.service.dto.TenantDto;

import java.util.List;
import java.util.Set;

/**
 * 租户相关
 *
 * @author zxiaozhou
 * @date 2021-11-05 17:30
 * @since JDK1.8
 */
public interface ITenantService {
    /**
     * 保存租户
     *
     * @param vo ${@link TenantVo}
     * @author zxiaozhou
     * @date 2021-11-05 17:51
     */
    void saveOrUpdate(TenantVo vo) throws RuntimeException;


    /**
     * 获取租户
     *
     * @param tenantId ${@link String}
     * @return TenantDto ${@link TenantDto}
     * @author zxiaozhou
     * @date 2021-11-05 17:51
     */
    TenantDto getTenant(String tenantId) throws RuntimeException;


    /**
     * 获取租户信息
     *
     * @param vo ${@link TenantQueryVo}
     * @return List<TenantDto> ${@link List <TenantDto>}
     * @author zxiaozhou
     * @date 2021-11-05 17:51
     */
    List<TenantDto> getTenantList(TenantQueryVo vo) throws RuntimeException;


    /**
     * 分页获取租户信息
     *
     * @param vo ${@link TenantQueryPageVoCamunda}
     * @return PageDto<TenantDto>${@link PageDto <TenantDto>}
     * @author zxiaozhou
     * @date 2021-11-05 17:51
     */
    PageDto<TenantDto> getTenantPage(TenantQueryPageVoCamunda vo) throws RuntimeException;


    /**
     * 删除租户
     *
     * @param tenantId ${@link String}
     * @author zxiaozhou
     * @date 2021-11-05 17:51
     */
    void deleteTenant(String tenantId) throws RuntimeException;


    /**
     * 全量同步租户信息
     *
     * @param voSet ${@link Set <TenantVo>}
     * @author zxiaozhou
     * @date 2021-11-07 21:37
     */
    void syncTenant(Set<TenantVo> voSet) throws RuntimeException;
}
