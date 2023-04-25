package com.anyilanxin.skillfull.system.modules.rbac.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.anyilanxin.skillfull.corecommon.constant.Status;
import com.anyilanxin.skillfull.corecommon.exception.ResponseException;
import com.anyilanxin.skillfull.corecommon.utils.I18nUtil;
import com.anyilanxin.skillfull.system.modules.rbac.entity.RbacRoleUserEntity;
import com.anyilanxin.skillfull.system.modules.rbac.mapper.RbacRoleUserMapper;
import com.anyilanxin.skillfull.system.modules.rbac.service.IRbacRoleUserService;
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
 * 角色-客户端(RbacRoleUser)业务层实现
 *
 * @author zxiaozhou
 * @copyright zxiaozhou（https://skillfull.divisu.com）
 * @date 2022-07-02 23:01:21
 * @since JDK1.8
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class RbacRoleUserServiceImpl extends ServiceImpl<RbacRoleUserMapper, RbacRoleUserEntity> implements IRbacRoleUserService {
    private final RbacRoleUserMapper mapper;


    @Override
    public void saveBatch(String userId, Set<String> roleIds) throws RuntimeException {
        if (CollUtil.isNotEmpty(roleIds)) {
            List<RbacRoleUserEntity> roleUserEntities = new ArrayList<>(roleIds.size());
            roleIds.forEach(v -> {
                RbacRoleUserEntity entity = RbacRoleUserEntity.builder()
                        .userId(userId)
                        .roleId(v)
                        .build();
                roleUserEntities.add(entity);
            });
            boolean b = this.saveBatch(roleUserEntities);
            if (!b) {
                throw new ResponseException(Status.DATABASE_BASE_ERROR, "保存角色关联失败");
            }
        }
    }


    @Override
    public void deleteBatch(List<String> userIds) throws RuntimeException {
        if (CollUtil.isNotEmpty(userIds)) {
            LambdaQueryWrapper<RbacRoleUserEntity> lambdaQueryWrapper = Wrappers.<RbacRoleUserEntity>lambdaQuery()
                    .in(RbacRoleUserEntity::getUserId, userIds);
            List<RbacRoleUserEntity> list = this.list(lambdaQueryWrapper);
            if (CollUtil.isNotEmpty(list)) {
                Set<String> userRoleIds = new HashSet<>(list.size());
                list.forEach(v -> userRoleIds.add(v.getRoleUserId()));
                int i = mapper.physicalDeleteBatchIds(userRoleIds);
                if (i <= 0) {
                    throw new ResponseException(Status.DATABASE_BASE_ERROR, I18nUtil.get("ServiceImpl.QueryDataFailOrDelete"));
                }
            }
        }
    }
}
