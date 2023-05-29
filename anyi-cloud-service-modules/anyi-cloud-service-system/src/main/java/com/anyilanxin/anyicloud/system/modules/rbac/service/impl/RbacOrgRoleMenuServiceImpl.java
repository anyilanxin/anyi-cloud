

package com.anyilanxin.anyicloud.system.modules.rbac.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.anyilanxin.anyicloud.corecommon.constant.Status;
import com.anyilanxin.anyicloud.corecommon.exception.ResponseException;
import com.anyilanxin.anyicloud.corecommon.utils.I18nUtil;
import com.anyilanxin.anyicloud.system.modules.rbac.entity.RbacOrgRoleMenuEntity;
import com.anyilanxin.anyicloud.system.modules.rbac.mapper.RbacOrgRoleMenuMapper;
import com.anyilanxin.anyicloud.system.modules.rbac.service.IRbacOrgRoleMenuService;
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
 * 机构角色-菜单表(RbacOrgRoleMenu)业务层实现
 *
 * @author zxh
 * @copyright zhouxuanhong（https://anyilanxin.com）
 * @date 2022-07-05 00:22:57
 * @since 1.0.0
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class RbacOrgRoleMenuServiceImpl extends ServiceImpl<RbacOrgRoleMenuMapper, RbacOrgRoleMenuEntity> implements IRbacOrgRoleMenuService {
    private final RbacOrgRoleMenuMapper mapper;

    @Override
    public void saveBatch(String orgRoleId, List<String> menuIds) throws RuntimeException {
        if (CollUtil.isNotEmpty(menuIds)) {
            List<RbacOrgRoleMenuEntity> roleMenuEntities = new ArrayList<>(menuIds.size());
            menuIds.forEach(v -> {
                RbacOrgRoleMenuEntity entity = RbacOrgRoleMenuEntity.builder().orgRoleId(orgRoleId).menuId(v).build();
                roleMenuEntities.add(entity);
            });
            boolean b = this.saveBatch(roleMenuEntities);
            if (!b) {
                throw new ResponseException(Status.DATABASE_BASE_ERROR, "保存角色菜单关联失败");
            }
        }
    }


    @Override
    public void deleteBatch(List<String> orgRoleId) throws RuntimeException {
        if (CollUtil.isNotEmpty(orgRoleId)) {
            LambdaQueryWrapper<RbacOrgRoleMenuEntity> lambdaQueryWrapper = Wrappers.<RbacOrgRoleMenuEntity>lambdaQuery().in(RbacOrgRoleMenuEntity::getOrgRoleId, orgRoleId);
            List<RbacOrgRoleMenuEntity> list = this.list(lambdaQueryWrapper);
            if (CollUtil.isNotEmpty(list)) {
                Set<String> roleMenuIds = new HashSet<>(list.size());
                list.forEach(v -> roleMenuIds.add(v.getOrgRoleMenuId()));
                int i = mapper.physicalDeleteBatchIds(roleMenuIds);
                if (i <= 0) {
                    throw new ResponseException(Status.DATABASE_BASE_ERROR, I18nUtil.get("ServiceImpl.QueryDataFailOrDelete"));
                }
            }
        }
    }
}
