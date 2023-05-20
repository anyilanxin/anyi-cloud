/*
 * Copyright (c) 2021-2022 ZHOUXUANHONG(安一老厨)<anyilanxin@aliyun.com>
 *
 * AnYi Cloud Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * AnYi Cloud 采用APACHE LICENSE 2.0开源协议，您在使用过程中，需要注意以下几点：
 *   1.请不要删除和修改根目录下的LICENSE文件；
 *   2.请不要删除和修改 AnYi Cloud 源码头部的版权声明；
 *   3.请保留源码和相关描述文件的项目出处，作者声明等；
 *   4.分发源码时候，请注明软件出处 https://github.com/anyilanxin/anyi-cloud；
 *   5.在修改包名，模块名称，项目代码等时，请注明软件出处 https://github.com/anyilanxin/anyi-cloud；
 *   6.本软件不允许在国家法律规定范围外使用，如出现违法行为原作者本人不承担任何法律风险；
 *   7.本软件使用的第三方依赖皆为开源软件，如需要修改第三方源码请遵循第三方源码附带开源协议；
 *   8.本软件流程部分请遵循camunda开源协议：
 *     https://docs.camunda.org/manual/latest/introduction/third-party-libraries
 *     https://github.com/camunda/camunda-bpm-platform/blob/master/LICENSE
 *   9.若您的项目无法满足以上几点，可申请商业授权。
 */

package com.anyilanxin.anyicloud.process.modules.rbac.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.CollectionUtil;
import com.anyilanxin.anyicloud.corecommon.constant.Status;
import com.anyilanxin.anyicloud.corecommon.exception.ResponseException;
import com.anyilanxin.anyicloud.database.datasource.base.service.dto.PageDto;
import com.anyilanxin.anyicloud.process.modules.rbac.controller.vo.TenantQueryPageVoCamunda;
import com.anyilanxin.anyicloud.process.modules.rbac.controller.vo.TenantQueryVo;
import com.anyilanxin.anyicloud.process.modules.rbac.controller.vo.TenantVo;
import com.anyilanxin.anyicloud.process.modules.rbac.service.ITenantService;
import com.anyilanxin.anyicloud.process.modules.rbac.service.dto.TenantDto;
import io.seata.spring.annotation.GlobalTransactional;
import java.util.*;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.camunda.bpm.engine.IdentityService;
import org.camunda.bpm.engine.identity.Group;
import org.camunda.bpm.engine.identity.Tenant;
import org.camunda.bpm.engine.identity.TenantQuery;
import org.camunda.bpm.engine.identity.User;
import org.springframework.stereotype.Service;

/**
 * 租户相关
 *
 * @author zxh
 * @date 2021-11-05 17:30
 * @since 1.0.0
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
     * @author zxh
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
