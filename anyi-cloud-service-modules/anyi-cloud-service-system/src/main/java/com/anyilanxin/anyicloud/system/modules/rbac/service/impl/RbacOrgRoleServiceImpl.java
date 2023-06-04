/*
 * Copyright (c) 2023-present ZHOUXUANHONG(安一老厨)<anyilanxin@aliyun.com>
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
 *   1.请不要删除和修改根目录下的LICENSE.txt文件；
 *   2.请不要删除和修改 AnYi Cloud 源码头部的版权声明；
 *   3.请保留源码和相关描述文件的项目出处，作者声明等；
 *   4.分发源码时候，请注明软件出处 https://github.com/anyilanxin/anyi-cloud；
 *   5.在修改包名，模块名称，项目代码等时，请注明软件出处 https://github.com/anyilanxin/anyi-cloud；
 *   6.本软件不允许在国家法律规定范围外使用，如出现违法行为原作者本人不承担任何法律风险；
 *   7.进行商用时，不得基于AnYi Cloud的基础，修改包装而成一个与AnYi Cloud EE、AnYi Zeebe EE、AnYi Standalone EE功能类似的程序，进行销售或发布，参与同类软件产品市场的竞争；
 *   8.本软件使用的第三方依赖皆为开源软件，如需要修改第三方源码请遵循第三方源码附带开源协议；
 *   9.本软件流程部分请遵循camunda开源协议：
 *     https://docs.camunda.org/manual/latest/introduction/third-party-libraries
 *     https://github.com/camunda/camunda-bpm-platform/blob/master/LICENSE
 *   10.若您的项目无法满足以上几点，可申请商业授权。
 */
package com.anyilanxin.anyicloud.system.modules.rbac.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.anyilanxin.anyicloud.corecommon.constant.Status;
import com.anyilanxin.anyicloud.corecommon.exception.ResponseException;
import com.anyilanxin.anyicloud.corecommon.utils.I18nUtil;
import com.anyilanxin.anyicloud.database.datasource.base.service.dto.PageDto;
import com.anyilanxin.anyicloud.system.core.constant.impl.MenuType;
import com.anyilanxin.anyicloud.system.modules.rbac.controller.vo.RbacOrgRoleAuthVo;
import com.anyilanxin.anyicloud.system.modules.rbac.controller.vo.RbacOrgRolePageVo;
import com.anyilanxin.anyicloud.system.modules.rbac.controller.vo.RbacOrgRoleVo;
import com.anyilanxin.anyicloud.system.modules.rbac.entity.RbacOrgRoleEntity;
import com.anyilanxin.anyicloud.system.modules.rbac.mapper.RbacOrgRoleMapper;
import com.anyilanxin.anyicloud.system.modules.rbac.mapper.RbacOrgRoleMenuMapper;
import com.anyilanxin.anyicloud.system.modules.rbac.mapper.RbacOrgRoleUserMapper;
import com.anyilanxin.anyicloud.system.modules.rbac.service.IRbacOrgRoleMenuService;
import com.anyilanxin.anyicloud.system.modules.rbac.service.IRbacOrgRoleService;
import com.anyilanxin.anyicloud.system.modules.rbac.service.dto.RbacOrgRoleDto;
import com.anyilanxin.anyicloud.system.modules.rbac.service.dto.RbacOrgRoleMenuButtonDto;
import com.anyilanxin.anyicloud.system.modules.rbac.service.dto.RbacOrgRoleMenuDto;
import com.anyilanxin.anyicloud.system.modules.rbac.service.dto.RbacOrgRolePageDto;
import com.anyilanxin.anyicloud.system.modules.rbac.service.mapstruct.PermissionOrgMenuActionMap;
import com.anyilanxin.anyicloud.system.modules.rbac.service.mapstruct.RbacOrgRoleCopyMap;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import java.util.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 机构角色表(RbacOrgRole)业务层实现
 *
 * @author zxh
 * @copyright zhouxuanhong（https://anyilanxin.com）
 * @date 2022-07-05 00:22:57
 * @since 1.0.0
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
        RbacOrgRoleEntity rbacRoleEntity = RbacOrgRoleEntity.builder().customDataAuthData(vo.getCustomDataAuthData()).dataAuthType(vo.getDataAuthType()).orgRoleId(orgRoleId).build();
        boolean b = this.updateById(rbacRoleEntity);
        if (!b) {
            throw new ResponseException(Status.DATABASE_BASE_ERROR, "更新权限失败");
        }
        // 更新菜单关联
        menuService.deleteBatch(List.of(rbacRoleEntity.getOrgRoleId()));
        menuService.saveBatch(rbacRoleEntity.getOrgRoleId(), vo.getMenuIds());
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
