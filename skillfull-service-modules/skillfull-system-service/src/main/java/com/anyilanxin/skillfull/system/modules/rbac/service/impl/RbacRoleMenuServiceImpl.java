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
import com.anyilanxin.skillfull.system.modules.rbac.entity.RbacRoleMenuEntity;
import com.anyilanxin.skillfull.system.modules.rbac.mapper.RbacRoleMenuMapper;
import com.anyilanxin.skillfull.system.modules.rbac.service.IRbacRoleMenuService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 角色-菜单表(RbacRoleMenu)业务层实现
 *
 * @author zxiaozhou
 * @copyright zxiaozhou（https://skillfull.divisu.com）
 * @date 2022-05-02 16:12:21
 * @since JDK1.8
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
                RbacRoleMenuEntity entity = RbacRoleMenuEntity.builder()
                        .roleId(roleId)
                        .menuId(v)
                        .build();
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
            LambdaQueryWrapper<RbacRoleMenuEntity> lambdaQueryWrapper = Wrappers.<RbacRoleMenuEntity>lambdaQuery()
                    .in(RbacRoleMenuEntity::getRoleId, roleIds);
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
