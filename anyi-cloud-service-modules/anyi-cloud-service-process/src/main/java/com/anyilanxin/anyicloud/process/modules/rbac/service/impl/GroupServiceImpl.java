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
import com.anyilanxin.anyicloud.process.modules.rbac.controller.vo.*;
import com.anyilanxin.anyicloud.process.modules.rbac.service.IGroupService;
import com.anyilanxin.anyicloud.process.modules.rbac.service.dto.GroupDto;
import com.anyilanxin.skillfull.process.modules.rbac.controller.vo.*;
import io.seata.spring.annotation.GlobalTransactional;
import java.util.*;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.camunda.bpm.engine.IdentityService;
import org.camunda.bpm.engine.identity.Group;
import org.camunda.bpm.engine.identity.GroupQuery;
import org.camunda.bpm.engine.identity.Tenant;
import org.camunda.bpm.engine.identity.User;
import org.springframework.stereotype.Service;

/**
 * 用户组相关
 *
 * @author 安一老厨
 * @date 2021-11-05 17:30
 * @since 1.0.0
 */
@Service
@RequiredArgsConstructor
public class GroupServiceImpl implements IGroupService {
    private final IdentityService identityService;
    private static final String DEFAULT_GROUP_ID = "camunda-admin";
    private static final String DEFAULT_GROUP_TYPE = "SYSTEM";

    @Override
    @GlobalTransactional
    public void saveOrUpdate(GroupVo vo) throws RuntimeException {
        Group group = identityService.createGroupQuery().groupId(vo.getGroupId()).singleResult();
        if (Objects.isNull(group)) {
            group = vo.getCamundaGroup();
        } else {
            group.setType(vo.getCode());
            group.setName(vo.getName());
            group.setId(vo.getGroupId());
        }
        identityService.saveGroup(group);
    }


    @Override
    @GlobalTransactional
    public void deleteOrAddTenant(GroupTenantVo vo) throws RuntimeException {
        String groupId = vo.getGroupId();
        List<Tenant> list = identityService.createTenantQuery().list();
        if (CollUtil.isNotEmpty(list)) {
            // 删除历史信息
            list.forEach(v -> {
                Group group = identityService.createGroupQuery().groupId(groupId).memberOfTenant(v.getId()).singleResult();
                if (Objects.nonNull(group)) {
                    identityService.deleteTenantGroupMembership(v.getId(), groupId);
                }
            });
            // 添加新信息
            if (CollUtil.isNotEmpty(vo.getTenantIds())) {
                vo.getTenantIds().forEach(v -> identityService.createTenantGroupMembership(v, groupId));
            }
        }
    }


    @Override
    public GroupDto getGroup(String groupId) throws RuntimeException {
        Group group = getCamundaGroup(groupId);
        return new GroupDto().getGroup(group);
    }


    @Override
    @GlobalTransactional
    public void deleteGroup(String groupId) throws RuntimeException {
        getCamundaGroup(groupId);
        identityService.deleteGroup(groupId);
        // 删除关联关系
        List<User> list = identityService.createUserQuery().memberOfGroup(groupId).list();
        if (CollUtil.isNotEmpty(list)) {
            list.forEach(v -> identityService.deleteMembership(v.getId(), groupId));
        }
    }


    @Override
    public List<GroupDto> getGroupList(GroupQueryVo vo) throws RuntimeException {
        GroupQuery groupQuery = identityService.createGroupQuery();
        if (StringUtils.isNotBlank(vo.getName())) {
            groupQuery.groupNameLike("%" + vo.getName() + "%");
        }
        if (StringUtils.isNotBlank(vo.getTenantId())) {
            groupQuery.memberOfTenant(vo.getTenantId());
        }
        List<Group> list = groupQuery.list();
        if (CollectionUtil.isEmpty(list)) {
            return Collections.emptyList();
        }
        List<GroupDto> groupList = new ArrayList<>(list.size());
        list.forEach(v -> groupList.add(new GroupDto().getGroup(v)));
        return groupList;
    }


    @Override
    public PageDto<GroupDto> getGroupPage(GroupQueryPageVoCamunda vo) throws RuntimeException {
        GroupQuery groupQuery = identityService.createGroupQuery();
        if (StringUtils.isNotBlank(vo.getName())) {
            groupQuery.groupNameLike("%" + vo.getName() + "%");
        }
        if (StringUtils.isNotBlank(vo.getTenantId())) {
            groupQuery.memberOfTenant(vo.getTenantId());
        }
        if (CollUtil.isNotEmpty(vo.getAscs())) {
            vo.getAscs().forEach(v -> {
                if (v.equals("name")) {
                    groupQuery.orderByGroupName().asc();
                }
            });
        }
        if (CollUtil.isNotEmpty(vo.getDescs())) {
            vo.getAscs().forEach(v -> {
                if (v.equals("name")) {
                    groupQuery.orderByGroupName().desc();
                }
            });
        }
        long count = groupQuery.count();
        if (count == 0L) {
            return new PageDto<>(0, Collections.emptyList());
        }
        List<Group> list = groupQuery.listPage(vo.getCurrent(), vo.getSize());
        List<GroupDto> groupList = new ArrayList<>(list.size());
        list.forEach(v -> {
            GroupDto groupModel = new GroupDto().getGroup(v);
            groupList.add(groupModel);
        });
        return new PageDto<>(count, groupList);
    }


    /**
     * 获取用户组信息
     *
     * @param groupId ${@link String} 用户组id
     * @return User ${@link User}
     * @author 安一老厨
     * @date 2021-11-07 00:42
     */
    private Group getCamundaGroup(String groupId) {
        // 查询用户组信息
        Group group = identityService.createGroupQuery().groupId(groupId).singleResult();
        if (Objects.isNull(group)) {
            throw new ResponseException(Status.DATABASE_BASE_ERROR, "用户组不存在");
        }
        return group;
    }


    @Override
    @GlobalTransactional
    public void syncGroup(Set<SyncGroupVo> voSet) throws RuntimeException {
        // 删除所有用户组，但排除系统默认的
        List<Group> list = identityService.createGroupQuery().list();
        List<Tenant> tenants = identityService.createTenantQuery().list();
        if (CollUtil.isNotEmpty(list)) {
            // 删除历史数据
            list.forEach(v -> {
                if (!DEFAULT_GROUP_ID.equals(v.getId()) && !DEFAULT_GROUP_TYPE.equals(v.getType())) {
                    // 删除关联关系
                    if (CollUtil.isNotEmpty(tenants)) {
                        tenants.forEach(sv -> {
                            Group group = identityService.createGroupQuery().groupId(v.getId()).memberOfTenant(sv.getId()).singleResult();
                            if (Objects.nonNull(group)) {
                                identityService.deleteTenantUserMembership(sv.getId(), v.getId());
                            }
                        });
                    }
                    // 删除角色
                    identityService.deleteGroup(v.getId());
                }
            });
        }
        // 添加新的
        if (CollUtil.isNotEmpty(voSet)) {
            voSet.forEach(v -> {
                identityService.saveGroup(v.getCamundaGroup());
                Set<String> tenantIds = v.getTenantIds();
                if (CollUtil.isNotEmpty(tenantIds)) {
                    tenantIds.forEach(sv -> identityService.createTenantGroupMembership(sv, v.getGroupId()));
                }
            });
        }
    }
}
