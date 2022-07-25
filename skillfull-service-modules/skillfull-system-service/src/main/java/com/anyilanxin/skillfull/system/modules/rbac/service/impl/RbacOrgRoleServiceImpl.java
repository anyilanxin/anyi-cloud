// +----------------------------------------------------------------------
// | SkillFull快速开发平台 [ SkillFull ]
// +----------------------------------------------------------------------
// | 版权所有 2020~2021 zxiaozhou
// +----------------------------------------------------------------------
// | 官方网站: https://www.divisu.com
// +----------------------------------------------------------------------
// | 作者: zxiaozhou <z7630853@163.com>
// +----------------------------------------------------------------------
package com.anyilanxin.skillfull.system.modules.rbac.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.anyilanxin.skillfull.corecommon.constant.Status;
import com.anyilanxin.skillfull.corecommon.exception.ResponseException;
import com.anyilanxin.skillfull.corecommon.utils.I18nUtil;
import com.anyilanxin.skillfull.database.datasource.base.service.dto.PageDto;
import com.anyilanxin.skillfull.system.core.constant.impl.MenuType;
import com.anyilanxin.skillfull.system.modules.rbac.controller.vo.RbacOrgRoleAuthVo;
import com.anyilanxin.skillfull.system.modules.rbac.controller.vo.RbacOrgRolePageVo;
import com.anyilanxin.skillfull.system.modules.rbac.controller.vo.RbacOrgRoleVo;
import com.anyilanxin.skillfull.system.modules.rbac.entity.RbacOrgRoleEntity;
import com.anyilanxin.skillfull.system.modules.rbac.mapper.RbacOrgRoleMapper;
import com.anyilanxin.skillfull.system.modules.rbac.mapper.RbacOrgRoleMenuMapper;
import com.anyilanxin.skillfull.system.modules.rbac.mapper.RbacOrgRoleUserMapper;
import com.anyilanxin.skillfull.system.modules.rbac.service.IRbacOrgRoleMenuService;
import com.anyilanxin.skillfull.system.modules.rbac.service.IRbacOrgRoleService;
import com.anyilanxin.skillfull.system.modules.rbac.service.dto.RbacOrgRoleDto;
import com.anyilanxin.skillfull.system.modules.rbac.service.dto.RbacOrgRoleMenuButtonDto;
import com.anyilanxin.skillfull.system.modules.rbac.service.dto.RbacOrgRoleMenuDto;
import com.anyilanxin.skillfull.system.modules.rbac.service.dto.RbacOrgRolePageDto;
import com.anyilanxin.skillfull.system.modules.rbac.service.mapstruct.PermissionOrgMenuActionMap;
import com.anyilanxin.skillfull.system.modules.rbac.service.mapstruct.RbacOrgRoleCopyMap;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * 机构角色表(RbacOrgRole)业务层实现
 *
 * @author zxiaozhou
 * @copyright zxiaozhou（https://divisu.com）
 * @date 2022-07-05 00:22:57
 * @since JDK1.8
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class RbacOrgRoleServiceImpl extends ServiceImpl<RbacOrgRoleMapper, RbacOrgRoleEntity> implements IRbacOrgRoleService {
    private final RbacOrgRoleCopyMap map;
    private final IRbacOrgRoleMenuService menuService;
    private final RbacOrgRoleUserMapper rbacOrgRoleUserMapper;
    private final PermissionOrgMenuActionMap menuActionMap;
    private final RbacOrgRoleMenuMapper roleMenuMapper;
    private final IRbacOrgRoleResourceApiService apiService;
    private final RbacOrgRoleMapper mapper;


    @Override
    @Transactional(rollbackFor = {Exception.class, Error.class})
    public void save(RbacOrgRoleVo vo) throws RuntimeException {
        RbacOrgRoleEntity entity = map.vToE(vo);
        boolean result = super.save(entity);
        if (!result) {
            throw new ResponseException(Status.DATABASE_BASE_ERROR, I18nUtil.get("ServiceImpl.SaveDataFail"));
        }
    }


    @Override
    @Transactional(rollbackFor = {Exception.class, Error.class})
    public void updateById(String orgRoleId, RbacOrgRoleVo vo) throws RuntimeException {
        // 查询数据是否存在
        this.getById(orgRoleId);
        // 更新数据
        RbacOrgRoleEntity entity = map.vToE(vo);
        entity.setOrgRoleId(orgRoleId);
        boolean result = super.updateById(entity);
        if (!result) {
            throw new ResponseException(Status.DATABASE_BASE_ERROR, I18nUtil.get("ServiceImpl.UpdateDataFail"));
        }
    }


    @Override
    @Transactional(rollbackFor = {Exception.class, Error.class}, readOnly = true)
    public PageDto<RbacOrgRolePageDto> pageByModel(RbacOrgRolePageVo vo) throws RuntimeException {
        return new PageDto<>(mapper.pageByModel(vo.getPage(), vo));
    }


    @Override
    @Transactional(rollbackFor = {Exception.class, Error.class}, readOnly = true)
    public RbacOrgRoleDto getById(String orgRoleId) throws RuntimeException {
        RbacOrgRoleEntity byId = super.getById(orgRoleId);
        if (Objects.isNull(byId)) {
            throw new ResponseException(Status.DATABASE_BASE_ERROR, I18nUtil.get("ServiceImpl.QueryDataFail"));
        }
        return map.eToD(byId);
    }


    @Override
    @Transactional(rollbackFor = {Exception.class, Error.class})
    public void deleteById(String orgRoleId) throws RuntimeException {
        // 查询数据是否存在
        this.getById(orgRoleId);
        // 删除数据
        boolean b = this.removeById(orgRoleId);
        if (!b) {
            throw new ResponseException(Status.DATABASE_BASE_ERROR, I18nUtil.get("ServiceImpl.DeleteDataFail"));
        }
        List<String> roleIds = new ArrayList<>(1);
        roleIds.add(orgRoleId);
        // 删除角色菜单关联
        menuService.deleteBatch(roleIds);
        // 删除角色资源关联
        menuService.deleteBatch(roleIds);
        // 删除用户关联
        rbacOrgRoleUserMapper.physicalDeleteBatchIds(roleIds);
    }


    @Override
    @Transactional(rollbackFor = {Exception.class, Error.class})
    public void deleteBatch(List<String> orgRoleIds) throws RuntimeException {
        List<RbacOrgRoleEntity> entities = this.listByIds(orgRoleIds);
        if (CollUtil.isEmpty(entities)) {
            throw new ResponseException(Status.DATABASE_BASE_ERROR, I18nUtil.get("ServiceImpl.QueryDataFailOrDelete"));
        }
        entities.forEach(v -> deleteById(v.getOrgRoleId()));
    }


    @Override
    public void updateAuth(String orgRoleId, RbacOrgRoleAuthVo vo) {
        // 查询数据是否存在
        this.getById(orgRoleId);
        // 更新数据
        RbacOrgRoleEntity rbacRoleEntity = RbacOrgRoleEntity.builder()
                .customDataAuthData(vo.getCustomDataAuthData())
                .dataAuthType(vo.getDataAuthType())
                .orgRoleId(orgRoleId)
                .build();
        boolean b = this.updateById(rbacRoleEntity);
        if (!b) {
            throw new ResponseException(Status.DATABASE_BASE_ERROR, "更新权限失败");
        }
        // 更新菜单关联
        menuService.deleteBatch(List.of(rbacRoleEntity.getOrgRoleId()));
        menuService.saveBatch(rbacRoleEntity.getOrgRoleId(), vo.getMenuIds());
        // 更新资源关联
        apiService.deleteBatch(List.of(rbacRoleEntity.getOrgRoleId()));
        apiService.saveBatch(rbacRoleEntity.getOrgRoleId(), vo.getApiIds());
    }


    @Override
    public Set<RbacOrgRoleMenuButtonDto> getMenuActions(String orgRoleId) {
        // 查询角色是否存在
        getById(orgRoleId);
        // 处理业务数据
        List<RbacOrgRoleMenuDto> rbacRolePermission = roleMenuMapper.selectMenuAntButton(orgRoleId);
        // 组装菜单角色信息
        if (CollUtil.isNotEmpty(rbacRolePermission)) {
            List<RbacOrgRoleMenuButtonDto> permissionInfos = new ArrayList<>();
            List<RbacOrgRoleMenuButtonDto.Action> actions = new ArrayList<>();
            rbacRolePermission.forEach(v -> {
                if (v.getMenuType() == MenuType.MENU.getType()) {
                    permissionInfos.add(menuActionMap.vToD(v));
                } else if (v.getMenuType() == MenuType.BUTTON.getType()) {
                    actions.add(menuActionMap.vToE(v));
                }
            });
            if (CollUtil.isNotEmpty(actions) && CollUtil.isNotEmpty(permissionInfos)) {
                permissionInfos.forEach(v -> {
                    Set<RbacOrgRoleMenuButtonDto.Action> finalActions = new HashSet<>();
                    String menuId = v.getMenuId();
                    actions.forEach(sv -> {
                        if (menuId.equals(sv.getParentId()) && sv.getRoleId().equals(v.getRoleId())) {
                            finalActions.add(sv);
                        }
                    });
                    if (CollUtil.isNotEmpty(finalActions)) {
                        v.setActionSet(finalActions);
                    }
                });
            }
            if (CollUtil.isNotEmpty(permissionInfos)) {
                return new HashSet<>(permissionInfos);
            }
        }
        return Collections.emptySet();
    }


    @Override
    public void updateStatus(String orgRoleId, Integer status) {
        // 查询数据是否存在
        this.getById(orgRoleId);
        RbacOrgRoleEntity entity = new RbacOrgRoleEntity();
        entity.setOrgRoleId(orgRoleId);
        entity.setRoleStatus(status);
        boolean b = this.updateById(entity);
        if (!b) {
            throw new ResponseException(Status.DATABASE_BASE_ERROR, "角色" + (status == 0 ? "禁用" : "启用") + "失败");
        }
    }
}
