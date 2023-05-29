/*
 * Copyright (c) 2021-present ZHOUXUANHONG(安一老厨)<anyilanxin@aliyun.com>
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
 *   7.进行商用时，不得基于AnYi Cloud的基础，修改包装而成一个与AnYi Zeebe EE、AnYi Cloud EE功能类似的程序，进行销售或发布，参与同类软件产品市场的竞争；
 *   8.本软件使用的第三方依赖皆为开源软件，如需要修改第三方源码请遵循第三方源码附带开源协议；
 *   9.本软件流程部分请遵循camunda开源协议：
 *     https://docs.camunda.org/manual/latest/introduction/third-party-libraries
 *     https://github.com/camunda/camunda-bpm-platform/blob/master/LICENSE
 *   10.若您的项目无法满足以上几点，可申请商业授权。
 */

package com.anyilanxin.anyicloud.system.modules.rbac.service.impl;

import static com.anyilanxin.anyicloud.corecommon.constant.SysBaseConstant.SUPER_ROLE;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.CollectionUtil;
import com.anyilanxin.anyicloud.corecommon.constant.AuthConstant;
import com.anyilanxin.anyicloud.corecommon.constant.Status;
import com.anyilanxin.anyicloud.corecommon.exception.ResponseException;
import com.anyilanxin.anyicloud.corecommon.utils.CodeUtil;
import com.anyilanxin.anyicloud.corecommon.utils.I18nUtil;
import com.anyilanxin.anyicloud.database.datasource.base.service.dto.PageDto;
import com.anyilanxin.anyicloud.system.core.constant.impl.MenuType;
import com.anyilanxin.anyicloud.system.modules.rbac.controller.vo.RbacRoleAuthVo;
import com.anyilanxin.anyicloud.system.modules.rbac.controller.vo.RbacRolePageVo;
import com.anyilanxin.anyicloud.system.modules.rbac.controller.vo.RbacRoleQueryVo;
import com.anyilanxin.anyicloud.system.modules.rbac.controller.vo.RbacRoleVo;
import com.anyilanxin.anyicloud.system.modules.rbac.entity.RbacMenuEntity;
import com.anyilanxin.anyicloud.system.modules.rbac.entity.RbacRoleEntity;
import com.anyilanxin.anyicloud.system.modules.rbac.mapper.RbacMenuMapper;
import com.anyilanxin.anyicloud.system.modules.rbac.mapper.RbacRoleMapper;
import com.anyilanxin.anyicloud.system.modules.rbac.mapper.RbacRoleMenuMapper;
import com.anyilanxin.anyicloud.system.modules.rbac.mapper.RbacRoleUserMapper;
import com.anyilanxin.anyicloud.system.modules.rbac.service.IRbacRoleMenuService;
import com.anyilanxin.anyicloud.system.modules.rbac.service.IRbacRoleService;
import com.anyilanxin.anyicloud.system.modules.rbac.service.ISyncProcessService;
import com.anyilanxin.anyicloud.system.modules.rbac.service.dto.*;
import com.anyilanxin.anyicloud.system.modules.rbac.service.mapstruct.PermissionMenuActionMap;
import com.anyilanxin.anyicloud.system.modules.rbac.service.mapstruct.RbacRoleAndMenuDtoMap;
import com.anyilanxin.anyicloud.system.modules.rbac.service.mapstruct.RbacRoleBasicDtoMap;
import com.anyilanxin.anyicloud.system.modules.rbac.service.mapstruct.RbacRoleCopyMap;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.util.*;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 角色表(RbacRole)业务层实现
 *
 * @author zxh
 * @copyright zhouxuanhong（https://anyilanxin.com）
 * @date 2022-05-02 16:12:20
 * @since 1.0.0
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class RbacRoleServiceImpl extends ServiceImpl<RbacRoleMapper, RbacRoleEntity> implements IRbacRoleService {
    private final RbacRoleCopyMap map;
    private final RbacRoleMenuMapper roleMenuMapper;
    private final RbacRoleUserMapper rbacRoleUserMapper;
    private final PermissionMenuActionMap menuActionMap;
    private final RbacRoleAndMenuDtoMap rbacRoleAndMenuDtoMap;
    private final RbacRoleBasicDtoMap basicDtoMap;
    private final IRbacRoleMenuService roleMenuService;
    private final RbacMenuMapper menuMapper;
    private final RbacRoleMapper mapper;
    private final ISyncProcessService syncService;

    @Override
    @Transactional(rollbackFor = {Exception.class, Error.class})
    // @GlobalTransactional
    public void save(RbacRoleVo vo) throws RuntimeException {
        RbacRoleEntity entity = map.vToE(vo);
        entity.setRoleSysCode(generateCode(null, vo.getParentRoleId(), false));
        boolean result = super.save(entity);
        if (!result) {
            throw new ResponseException(Status.DATABASE_BASE_ERROR, I18nUtil.get("ServiceImpl.SaveDataFail"));
        }
        // 同步流程引擎
        // syncService.addOrUpdateRole(entity.getRoleId());
    }


    /**
     * 生成系统code
     *
     * @param oldParentId ${@link String} 历史上级id
     * @param newParentId ${@link String} 现在上级id
     * @param isUpdate    ${@link Boolean} false-新建,true-更新
     * @return String ${@link String}
     * @author zxh
     * @date 2021-03-08 12:00
     */
    @Transactional(rollbackFor = {Exception.class, Error.class}, readOnly = true)
    synchronized String generateCode(String oldParentId, String newParentId, boolean isUpdate) {
        oldParentId = StringUtils.isBlank(oldParentId) ? "" : oldParentId;
        newParentId = StringUtils.isBlank(newParentId) ? "" : newParentId;
        if (isUpdate && oldParentId.equals(newParentId)) {
            return null;
        }
        String code;
        if (StringUtils.isBlank(newParentId)) {
            LambdaQueryWrapper<RbacRoleEntity> lambdaQueryWrapper = new LambdaQueryWrapper<>();
            lambdaQueryWrapper.and(v -> v.isNull(RbacRoleEntity::getParentRoleId).or().eq(RbacRoleEntity::getParentRoleId, "")).orderByDesc(RbacRoleEntity::getRoleSysCode).last("LIMIT 1");
            RbacRoleEntity one = this.getOne(lambdaQueryWrapper);
            code = CodeUtil.getSubYouBianCode(null, Objects.isNull(one) ? null : one.getRoleSysCode());
        } else {
            // 获取上级code
            RbacRoleDto byId = this.getById(newParentId);
            // 获取本级最大code
            LambdaQueryWrapper<RbacRoleEntity> lambdaQueryWrapper = new LambdaQueryWrapper<>();
            lambdaQueryWrapper.like(RbacRoleEntity::getRoleSysCode, byId.getRoleSysCode() + "____").orderByDesc(RbacRoleEntity::getRoleSysCode).last("LIMIT 1");
            RbacRoleEntity one = this.getOne(lambdaQueryWrapper);
            code = CodeUtil.getSubYouBianCode(byId.getRoleSysCode(), Objects.isNull(one) ? null : one.getRoleSysCode());
        }
        return code;
    }


    @Override
    @Transactional(rollbackFor = {Exception.class, Error.class})
    // @GlobalTransactional
    public void updateById(String roleId, RbacRoleVo vo) throws RuntimeException {
        // 查询数据是否存在
        this.getById(roleId);
        // 更新数据
        RbacRoleEntity entity = map.vToE(vo);
        entity.setRoleId(roleId);
        boolean result = super.updateById(entity);
        if (!result) {
            throw new ResponseException(Status.DATABASE_BASE_ERROR, I18nUtil.get("ServiceImpl.UpdateDataFail"));
        }
        // 同步流程引擎
        // syncService.addOrUpdateRole(entity.getRoleId());
    }


    @Override
    @Transactional(rollbackFor = {Exception.class, Error.class}, readOnly = true)
    public List<RbacRoleDto> selectListByModel(RbacRoleQueryVo vo) throws RuntimeException {
        List<RbacRoleDto> list = mapper.selectListByModel(vo, AuthConstant.SUPER_ROLE);
        if (CollUtil.isEmpty(list)) {
            throw new ResponseException(Status.DATABASE_BASE_ERROR, I18nUtil.get("ServiceImpl.QueryDataFail"));
        }
        list.forEach(v -> {
            // 判断是否为超级管理员角色
            if (AuthConstant.SUPER_ROLE.equals(v.getRoleCode())) {
                v.setSuperRole(true);
            }
        });
        return list;
    }


    @Override
    @Transactional(rollbackFor = {Exception.class, Error.class}, readOnly = true)
    public PageDto<RbacRolePageDto> pageByModel(RbacRolePageVo vo) throws RuntimeException {
        return new PageDto<>(mapper.pageByModel(vo.getPage(), vo, AuthConstant.SUPER_ROLE));
    }


    @Override
    @Transactional(rollbackFor = {Exception.class, Error.class}, readOnly = true)
    public RbacRoleDto getById(String roleId) throws RuntimeException {
        RbacRoleEntity byId = super.getById(roleId);
        if (Objects.isNull(byId)) {
            throw new ResponseException(Status.DATABASE_BASE_ERROR, I18nUtil.get("ServiceImpl.QueryDataFail"));
        }
        RbacRoleDto rbacRoleDto = map.eToD(byId);
        // 查询功能权限
        List<String> menus;
        if (AuthConstant.SUPER_ROLE.equals(byId.getRoleCode())) {
            menus = roleMenuMapper.selectAllMenu();
        } else {
            menus = roleMenuMapper.selectMenuListById(roleId);
        }
        if (CollUtil.isEmpty(menus)) {
            menus = Collections.emptyList();
        }
        rbacRoleDto.setMenuIds(menus);
        // 判断是否为超级管理员角色
        if (AuthConstant.SUPER_ROLE.equals(rbacRoleDto.getRoleCode())) {
            rbacRoleDto.setSuperRole(true);
        }
        return rbacRoleDto;
    }


    @Override
    @Transactional(rollbackFor = {Exception.class, Error.class})
    // @GlobalTransactional
    public void deleteById(String roleId) throws RuntimeException {
        // 查询数据是否存在
        this.getById(roleId);
        // 删除数据
        boolean b = this.removeById(roleId);
        if (!b) {
            throw new ResponseException(Status.DATABASE_BASE_ERROR, I18nUtil.get("ServiceImpl.DeleteDataFail"));
        }
        List<String> roleIds = new ArrayList<>(1);
        roleIds.add(roleId);
        // 删除角色菜单关联
        roleMenuService.deleteBatch(roleIds);
        // 删除用户关联
        rbacRoleUserMapper.physicalDeleteBatchRoleIds(roleIds);
        // 同步流程引擎
        // syncService.deleteRole(roleId);
    }


    @Override
    @Transactional(rollbackFor = {Exception.class, Error.class})
    // @GlobalTransactional
    public void deleteBatch(List<String> roleIds) throws RuntimeException {
        List<RbacRoleEntity> entities = this.listByIds(roleIds);
        if (CollUtil.isEmpty(entities)) {
            throw new ResponseException(Status.DATABASE_BASE_ERROR, I18nUtil.get("ServiceImpl.QueryDataFailOrDelete"));
        }
        entities.forEach(v -> deleteById(v.getRoleId()));
    }


    @Override
    public void updateAuth(String roleId, RbacRoleAuthVo vo) {
        // 查询数据是否存在
        this.getById(roleId);
        // 更新数据
        RbacRoleEntity rbacRoleEntity = RbacRoleEntity.builder().customDataAuthData(vo.getCustomDataAuthData()).dataAuthType(vo.getDataAuthType()).roleId(roleId).build();
        boolean b = this.updateById(rbacRoleEntity);
        if (!b) {
            throw new ResponseException(Status.DATABASE_BASE_ERROR, "更新权限失败");
        }
        // 更新菜单关联
        roleMenuService.deleteBatch(List.of(rbacRoleEntity.getRoleId()));
        roleMenuService.saveBatch(rbacRoleEntity.getRoleId(), vo.getMenuIds());
    }


    @Override
    public void updateStatus(String roleId, Integer status) throws RuntimeException {
        // 查询数据是否存在
        RbacRoleDto byId = this.getById(roleId);
        // 如果是超级管理员永远启用
        if (SUPER_ROLE.equals(byId.getRoleCode())) {
            throw new ResponseException(Status.VERIFICATION_FAILED, "超级管理员角色状态不可操作，永远启用");
        }
        RbacRoleEntity entity = new RbacRoleEntity();
        entity.setRoleId(roleId);
        entity.setRoleStatus(status);
        boolean b = this.updateById(entity);
        if (!b) {
            throw new ResponseException(Status.DATABASE_BASE_ERROR, "角色" + (status == 0 ? "禁用" : "启用") + "失败");
        }
    }


    @Override
    public List<RbacRoleBasicDto> getEffectiveRoles() throws RuntimeException {
        LambdaQueryWrapper<RbacRoleEntity> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(RbacRoleEntity::getRoleStatus, 1);
        List<RbacRoleEntity> list = this.list(lambdaQueryWrapper);
        if (CollectionUtil.isEmpty(list)) {
            return Collections.emptyList();
        }
        List<RbacRoleBasicDto> result = new ArrayList<>(list.size());
        list.forEach(v -> {
            // 判断是否为超级管理员角色
            RbacRoleBasicDto rbacRoleBasicDto = basicDtoMap.bToA(v);
            if (AuthConstant.SUPER_ROLE.equals(rbacRoleBasicDto.getRoleCode())) {
                rbacRoleBasicDto.setSuperRole(true);
            }
            result.add(rbacRoleBasicDto);
        });
        return result;
    }


    @Override
    public Set<RbacRoleMenuButtonDto> getMenuActions(String roleId) throws RuntimeException {
        RbacRoleEntity role = super.getById(roleId);
        // 处理普通角色
        List<RbacRoleMenuDto> rbacRolePermissionDtos = new ArrayList<>(16);
        // 如果超级管理员角色
        if (SUPER_ROLE.equals(role.getRoleCode())) {
            LambdaQueryWrapper<RbacMenuEntity> lambdaQueryWrapper = new LambdaQueryWrapper<>();
            lambdaQueryWrapper.ne(RbacMenuEntity::getMenuType, MenuType.CATALOGUE.getType());
            List<RbacMenuEntity> rbacPermissionEntities = menuMapper.selectList(lambdaQueryWrapper);
            if (CollUtil.isNotEmpty(rbacPermissionEntities)) {
                List<RbacRoleMenuDto> superRbacRoleMenuDtos = rbacRoleAndMenuDtoMap.bToA(rbacPermissionEntities);
                for (RbacRoleMenuDto permissionDto : superRbacRoleMenuDtos) {
                    permissionDto.setRoleCode(role.getRoleCode());
                    permissionDto.setRoleId(role.getRoleId());
                    permissionDto.setRoleName(role.getRoleName());
                }
                rbacRolePermissionDtos.addAll(superRbacRoleMenuDtos);
            }
        } else {
            List<RbacRoleMenuDto> rbacRolePermission = roleMenuMapper.selectMenuAntButton(role.getRoleId());
            if (CollUtil.isNotEmpty(rbacRolePermission)) {
                rbacRolePermissionDtos.addAll(rbacRolePermission);
            }
        }
        // 组装菜单角色信息
        if (CollUtil.isNotEmpty(rbacRolePermissionDtos)) {
            List<RbacRoleMenuButtonDto> permissionInfos = new ArrayList<>();
            List<RbacRoleMenuButtonDto.Action> actions = new ArrayList<>();
            rbacRolePermissionDtos.forEach(v -> {
                if (v.getMenuType() == MenuType.MENU.getType()) {
                    permissionInfos.add(menuActionMap.vToD(v));
                } else if (v.getMenuType() == MenuType.BUTTON.getType()) {
                    actions.add(menuActionMap.vToE(v));
                }
            });
            if (CollUtil.isNotEmpty(actions) && CollUtil.isNotEmpty(permissionInfos)) {
                permissionInfos.forEach(v -> {
                    Set<RbacRoleMenuButtonDto.Action> finalActions = new HashSet<>();
                    String menuId = v.getMenuId();
                    actions.forEach(sv -> {
                        if (menuId.equals(sv.getParentId()) && sv.getRoleId().equals(v.getRoleId())) {
                            finalActions.add(sv);
                        }
                    });
                    if (CollUtil.isNotEmpty(finalActions)) {
                        v.setActionSet(finalActions);
                    }
                    // 判断是否为超级管理员角色
                    if (AuthConstant.SUPER_ROLE.equals(v.getRoleCode())) {
                        v.setSuperRole(true);
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
    public List<RbacRoleBasicDto> getListByCodes(List<String> roleCodes) {
        LambdaQueryWrapper<RbacRoleEntity> lambdaQueryWrapper = Wrappers.<RbacRoleEntity>lambdaQuery().in(RbacRoleEntity::getRoleCode, roleCodes);
        List<RbacRoleEntity> rbacRoleEntities = this.list(lambdaQueryWrapper);
        if (CollUtil.isEmpty(rbacRoleEntities)) {
            return Collections.emptyList();
        }
        List<RbacRoleBasicDto> result = new ArrayList<>(rbacRoleEntities.size());
        rbacRoleEntities.forEach(v -> {
            RbacRoleBasicDto rbacRoleBasicDto = basicDtoMap.bToA(v);
            // 判断是否为超级管理员角色
            if (AuthConstant.SUPER_ROLE.equals(rbacRoleBasicDto.getRoleCode())) {
                rbacRoleBasicDto.setSuperRole(true);
            }
            result.add(rbacRoleBasicDto);
        });
        return result;
    }


    @Override
    public List<RbacRoleBasicDto> getRoleListByIds(List<String> roleIds) {
        LambdaQueryWrapper<RbacRoleEntity> lambdaQueryWrapper = Wrappers.<RbacRoleEntity>lambdaQuery().in(RbacRoleEntity::getRoleId, roleIds);
        List<RbacRoleEntity> rbacRoleEntities = this.list(lambdaQueryWrapper);

        if (CollUtil.isEmpty(rbacRoleEntities)) {
            return Collections.emptyList();
        }
        List<RbacRoleBasicDto> result = new ArrayList<>(rbacRoleEntities.size());
        rbacRoleEntities.forEach(v -> {
            RbacRoleBasicDto rbacRoleBasicDto = basicDtoMap.bToA(v);
            // 判断是否为超级管理员角色
            if (AuthConstant.SUPER_ROLE.equals(rbacRoleBasicDto.getRoleCode())) {
                rbacRoleBasicDto.setSuperRole(true);
            }
            result.add(rbacRoleBasicDto);
        });
        return result;
    }
}
