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
import com.anyilanxin.skillfull.process.modules.rbac.controller.vo.*;
import com.anyilanxin.skillfull.process.modules.rbac.service.IGroupService;
import com.anyilanxin.skillfull.process.modules.rbac.service.dto.GroupDto;
import io.seata.spring.annotation.GlobalTransactional;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.camunda.bpm.engine.IdentityService;
import org.camunda.bpm.engine.identity.Group;
import org.camunda.bpm.engine.identity.GroupQuery;
import org.camunda.bpm.engine.identity.Tenant;
import org.camunda.bpm.engine.identity.User;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * 用户组相关
 *
 * @author zxiaozhou
 * @date 2021-11-05 17:30
 * @since JDK1.8
 */
@Service
@RequiredArgsConstructor
public class GroupServiceImpl implements IGroupService {
    private final IdentityService identityService;
    private final static String DEFAULT_GROUP_ID = "camunda-admin";
    private final static String DEFAULT_GROUP_TYPE = "SYSTEM";


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
                Group group = identityService.createGroupQuery()
                        .groupId(groupId)
                        .memberOfTenant(v.getId())
                        .singleResult();
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
     * @author zxiaozhou
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
