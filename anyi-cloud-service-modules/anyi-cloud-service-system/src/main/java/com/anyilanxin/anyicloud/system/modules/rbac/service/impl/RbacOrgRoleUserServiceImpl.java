

package com.anyilanxin.anyicloud.system.modules.rbac.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.anyilanxin.anyicloud.corecommon.constant.Status;
import com.anyilanxin.anyicloud.corecommon.exception.ResponseException;
import com.anyilanxin.anyicloud.corecommon.utils.I18nUtil;
import com.anyilanxin.anyicloud.system.modules.rbac.entity.RbacOrgRoleUserEntity;
import com.anyilanxin.anyicloud.system.modules.rbac.mapper.RbacOrgRoleUserMapper;
import com.anyilanxin.anyicloud.system.modules.rbac.service.IRbacOrgRoleUserService;
import com.anyilanxin.anyicloud.system.modules.rbac.service.mapstruct.RbacOrgRoleUserCopyMap;
import com.anyilanxin.anyicloud.system.modules.rbac.service.mapstruct.RbacOrgRoleUserPageCopyMap;
import com.anyilanxin.anyicloud.system.modules.rbac.service.mapstruct.RbacOrgRoleUserQueryCopyMap;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 机构角色-用户(RbacOrgRoleUser)业务层实现
 *
 * @author zxh
 * @copyright zhouxuanhong（https://anyilanxin.com）
 * @date 2022-07-05 00:22:57
 * @since 1.0.0
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class RbacOrgRoleUserServiceImpl extends ServiceImpl<RbacOrgRoleUserMapper, RbacOrgRoleUserEntity> implements IRbacOrgRoleUserService {
    private final RbacOrgRoleUserCopyMap map;
    private final RbacOrgRoleUserPageCopyMap pageMap;
    private final RbacOrgRoleUserQueryCopyMap queryMap;
    private final RbacOrgRoleUserMapper mapper;

    @Override
    public void saveBatch(String userId, String orgId, Set<String> orgRoleIds) throws RuntimeException {
        if (CollUtil.isNotEmpty(orgRoleIds)) {
            List<RbacOrgRoleUserEntity> orgRoleUserEntities = new ArrayList<>(orgRoleIds.size());
            orgRoleIds.forEach(v -> {
                RbacOrgRoleUserEntity entity = RbacOrgRoleUserEntity.builder().userId(userId).orgId(orgId).orgRoleId(v).build();
                orgRoleUserEntities.add(entity);
            });
            boolean b = this.saveBatch(orgRoleUserEntities);
            if (!b) {
                throw new ResponseException(Status.DATABASE_BASE_ERROR, "保存机构角色关联失败");
            }
        }
    }


    @Override
    public void deleteByUserId(String userId, String orgId) throws RuntimeException {
        LambdaQueryWrapper<RbacOrgRoleUserEntity> lambdaQueryWrapper = Wrappers.<RbacOrgRoleUserEntity>lambdaQuery().eq(RbacOrgRoleUserEntity::getUserId, userId).eq(RbacOrgRoleUserEntity::getOrgId, orgId);
        List<RbacOrgRoleUserEntity> list = this.list(lambdaQueryWrapper);
        if (CollUtil.isNotEmpty(list)) {
            Set<String> roleUserIds = new HashSet<>(list.size());
            list.forEach(v -> roleUserIds.add(v.getRoleUserId()));
            int i = mapper.physicalDeleteBatchIds(roleUserIds);
            if (i <= 0) {
                throw new ResponseException(Status.DATABASE_BASE_ERROR, I18nUtil.get("ServiceImpl.QueryDataFailOrDelete"));
            }
        }
    }
}
