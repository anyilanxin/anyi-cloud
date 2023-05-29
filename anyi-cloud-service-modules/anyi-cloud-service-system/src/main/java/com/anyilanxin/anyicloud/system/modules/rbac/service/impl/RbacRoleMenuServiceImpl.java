

package com.anyilanxin.anyicloud.system.modules.rbac.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.anyilanxin.anyicloud.corecommon.constant.Status;
import com.anyilanxin.anyicloud.corecommon.exception.ResponseException;
import com.anyilanxin.anyicloud.corecommon.utils.I18nUtil;
import com.anyilanxin.anyicloud.system.modules.rbac.entity.RbacRoleMenuEntity;
import com.anyilanxin.anyicloud.system.modules.rbac.mapper.RbacRoleMenuMapper;
import com.anyilanxin.anyicloud.system.modules.rbac.service.IRbacRoleMenuService;
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
import org.springframework.transaction.annotation.Transactional;

/**
 * 角色-菜单表(RbacRoleMenu)业务层实现
 *
 * @author zxh
 * @copyright zhouxuanhong（https://anyilanxin.com）
 * @date 2022-05-02 16:12:21
 * @since 1.0.0
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class RbacRoleMenuServiceImpl extends ServiceImpl<RbacRoleMenuMapper, RbacRoleMenuEntity> implements IRbacRoleMenuService {
    private final RbacRoleMenuMapper mapper;

    @Override
    public void saveBatch(String roleId, List<String> menuIds) throws RuntimeException {
        if (CollUtil.isNotEmpty(menuIds)) {
            List<RbacRoleMenuEntity> roleMenuEntities = new ArrayList<>(menuIds.size());
            menuIds.forEach(v -> {
                RbacRoleMenuEntity entity = RbacRoleMenuEntity.builder().roleId(roleId).menuId(v).build();
                roleMenuEntities.add(entity);
            });
            boolean b = this.saveBatch(roleMenuEntities);
            if (!b) {
                throw new ResponseException(Status.DATABASE_BASE_ERROR, "保存角色菜单关联失败");
            }
        }
    }


    @Override
    @Transactional(rollbackFor = {Exception.class, Error.class})
    public void deleteBatch(List<String> roleIds) throws RuntimeException {
        if (CollUtil.isNotEmpty(roleIds)) {
            LambdaQueryWrapper<RbacRoleMenuEntity> lambdaQueryWrapper = Wrappers.<RbacRoleMenuEntity>lambdaQuery().in(RbacRoleMenuEntity::getRoleId, roleIds);
            List<RbacRoleMenuEntity> list = this.list(lambdaQueryWrapper);
            if (CollUtil.isNotEmpty(list)) {
                Set<String> roleMenuIds = new HashSet<>(list.size());
                list.forEach(v -> roleMenuIds.add(v.getRoleMenuId()));
                int i = mapper.physicalDeleteBatchIds(roleMenuIds);
                if (i <= 0) {
                    throw new ResponseException(Status.DATABASE_BASE_ERROR, I18nUtil.get("ServiceImpl.QueryDataFailOrDelete"));
                }
            }
        }
    }
}
