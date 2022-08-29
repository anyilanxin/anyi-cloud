// +----------------------------------------------------------------------
// | SkillFull快速开发平台 [ SkillFull ]
// +----------------------------------------------------------------------
// | 版权所有 2020~2021 zxiaozhou
// +----------------------------------------------------------------------
// | 官方网站: https://skillfull.divisu.com
// +----------------------------------------------------------------------
// | 作者: zxiaozhou <z7630853@163.com>
// +----------------------------------------------------------------------
package com.anyilanxin.skillfull.system.modules.rbac.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.anyilanxin.skillfull.corecommon.constant.Status;
import com.anyilanxin.skillfull.corecommon.exception.ResponseException;
import com.anyilanxin.skillfull.corecommon.utils.I18nUtil;
import com.anyilanxin.skillfull.system.modules.rbac.entity.RbacOrgRoleUserEntity;
import com.anyilanxin.skillfull.system.modules.rbac.mapper.RbacOrgRoleUserMapper;
import com.anyilanxin.skillfull.system.modules.rbac.service.IRbacOrgRoleUserService;
import com.anyilanxin.skillfull.system.modules.rbac.service.mapstruct.RbacOrgRoleUserCopyMap;
import com.anyilanxin.skillfull.system.modules.rbac.service.mapstruct.RbacOrgRoleUserPageCopyMap;
import com.anyilanxin.skillfull.system.modules.rbac.service.mapstruct.RbacOrgRoleUserQueryCopyMap;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 机构角色-用户(RbacOrgRoleUser)业务层实现
 *
 * @author zxiaozhou
 * @copyright zxiaozhou（https://skillfull.divisu.com）
 * @date 2022-07-05 00:22:57
 * @since JDK1.8
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
                RbacOrgRoleUserEntity entity = RbacOrgRoleUserEntity.builder()
                        .userId(userId)
                        .orgId(orgId)
                        .orgRoleId(v)
                        .build();
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
        LambdaQueryWrapper<RbacOrgRoleUserEntity> lambdaQueryWrapper = Wrappers.<RbacOrgRoleUserEntity>lambdaQuery()
                .eq(RbacOrgRoleUserEntity::getUserId, userId)
                .eq(RbacOrgRoleUserEntity::getOrgId, orgId);
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
