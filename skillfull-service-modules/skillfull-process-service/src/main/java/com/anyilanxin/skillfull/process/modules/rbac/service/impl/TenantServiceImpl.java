// +----------------------------------------------------------------------
// | SkillFull快速开发平台 [ SkillFull ]
// +----------------------------------------------------------------------
// | 版权所有 2020~2021 zxiaozhou
// +----------------------------------------------------------------------
// | 官方网站: https://skillfull.divisu.com
// +----------------------------------------------------------------------
// | 作者: zxiaozhou <z7630853@163.com>
// +----------------------------------------------------------------------
package com.anyilanxin.skillfull.process.modules.rbac.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.CollectionUtil;
import com.anyilanxin.skillfull.corecommon.constant.Status;
import com.anyilanxin.skillfull.corecommon.exception.ResponseException;
import com.anyilanxin.skillfull.database.datasource.base.service.dto.PageDto;
import com.anyilanxin.skillfull.process.modules.rbac.controller.vo.TenantQueryPageVoCamunda;
import com.anyilanxin.skillfull.process.modules.rbac.controller.vo.TenantQueryVo;
import com.anyilanxin.skillfull.process.modules.rbac.controller.vo.TenantVo;
import com.anyilanxin.skillfull.process.modules.rbac.service.ITenantService;
import com.anyilanxin.skillfull.process.modules.rbac.service.dto.TenantDto;
import io.seata.spring.annotation.GlobalTransactional;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.camunda.bpm.engine.IdentityService;
import org.camunda.bpm.engine.identity.Group;
import org.camunda.bpm.engine.identity.Tenant;
import org.camunda.bpm.engine.identity.TenantQuery;
import org.camunda.bpm.engine.identity.User;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * 租户相关
 *
 * @author zxiaozhou
 * @date 2021-11-05 17:30
 * @since JDK1.8
 */
@Service
@RequiredArgsConstructor
public class TenantServiceImpl implements ITenantService {
    private final IdentityService identityService;

    @Override
    @GlobalTransactional
    public void saveOrUpdate(TenantVo vo) throws RuntimeException {
        Tenant tenant = identityService.createTenantQuery().tenantId(vo.getTenantId()).singleResult();
        if (Objects.isNull(tenant)) {
            tenant = vo.getCamundaTenant();
        } else {
            tenant.setName(vo.getName());
            tenant.setId(vo.getTenantId());
        }
        identityService.saveTenant(tenant);
    }


    @Override
    public TenantDto getTenant(String tenantId) throws RuntimeException {
        Tenant tenant = getCamundaTenant(tenantId);
        return new TenantDto().getTenant(tenant);
    }


    @Override
    @GlobalTransactional
    public void deleteTenant(String tenantId) throws RuntimeException {
        getCamundaTenant(tenantId);
        identityService.deleteTenant(tenantId);
        // 删除关联关系
        List<Group> groups = identityService.createGroupQuery().memberOfTenant(tenantId).list();
        if (CollUtil.isNotEmpty(groups)) {
            groups.forEach(v -> identityService.deleteTenantGroupMembership(tenantId, v.getId()));
        }
        List<User> users = identityService.createUserQuery().memberOfTenant(tenantId).list();
        if (CollUtil.isNotEmpty(users)) {
            users.forEach(v -> identityService.deleteTenantUserMembership(tenantId, v.getId()));
        }
    }


    @Override
    public List<TenantDto> getTenantList(TenantQueryVo vo) throws RuntimeException {
        TenantQuery tenantQuery = identityService.createTenantQuery();
        if (StringUtils.isNotBlank(vo.getName())) {
            tenantQuery.tenantNameLike("%" + vo.getName() + "%");
        }
        List<Tenant> list = tenantQuery.list();
        if (CollectionUtil.isEmpty(list)) {
            return Collections.emptyList();
        }
        List<TenantDto> tenantList = new ArrayList<>(list.size());
        list.forEach(v -> tenantList.add(new TenantDto().getTenant(v)));
        return tenantList;
    }


    @Override
    public PageDto<TenantDto> getTenantPage(TenantQueryPageVoCamunda vo) throws RuntimeException {
        TenantQuery tenantQuery = identityService.createTenantQuery();
        if (StringUtils.isNotBlank(vo.getName())) {
            tenantQuery.tenantNameLike("%" + vo.getName() + "%");
        }
        if (CollUtil.isNotEmpty(vo.getAscs())) {
            vo.getAscs().forEach(v -> {
                if (v.equals("name")) {
                    tenantQuery.orderByTenantName().asc();
                }
            });
        }
        if (CollUtil.isNotEmpty(vo.getDescs())) {
            vo.getAscs().forEach(v -> {
                if (v.equals("name")) {
                    tenantQuery.orderByTenantName().desc();
                }
            });
        }
        long count = tenantQuery.count();
        if (count == 0L) {
            return new PageDto<>(0, Collections.emptyList());
        }
        List<Tenant> list = tenantQuery.listPage(vo.getCurrent(), vo.getSize());
        List<TenantDto> tenantList = new ArrayList<>(list.size());
        list.forEach(v -> {
            TenantDto tenantDto = new TenantDto().getTenant(v);
            tenantList.add(tenantDto);
        });
        return new PageDto<>(count, tenantList);
    }


    /**
     * 获取租户信息
     *
     * @param tenantId ${@link String} 租户id
     * @return User ${@link User}
     * @author zxiaozhou
     * @date 2021-11-07 00:42
     */
    private Tenant getCamundaTenant(String tenantId) {
        // 查询租户信息
        Tenant tenant = identityService.createTenantQuery().tenantId(tenantId).singleResult();
        if (Objects.isNull(tenant)) {
            throw new ResponseException(Status.DATABASE_BASE_ERROR, "租户信息不存在");
        }
        return tenant;
    }


    @Override
    @GlobalTransactional
    public void syncTenant(Set<TenantVo> voSet) throws RuntimeException {
        // 删除所有
        List<Tenant> list = identityService.createTenantQuery().list();
        if (CollUtil.isNotEmpty(list)) {
            list.forEach(v -> identityService.deleteTenant(v.getId()));
        }
        // 添加新的
        if (CollUtil.isNotEmpty(voSet)) {
            voSet.forEach(v -> identityService.saveTenant(v.getCamundaTenant()));
        }
    }
}
