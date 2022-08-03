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
import cn.hutool.core.collection.CollectionUtil;
import com.anyilanxin.skillfull.corecommon.base.Result;
import com.anyilanxin.skillfull.corecommon.constant.AuthConstant;
import com.anyilanxin.skillfull.corecommon.constant.Status;
import com.anyilanxin.skillfull.corecommon.exception.ResponseException;
import com.anyilanxin.skillfull.processapi.feign.ProcessSyncRbacRemoteService;
import com.anyilanxin.skillfull.processapi.model.*;
import com.anyilanxin.skillfull.system.modules.rbac.entity.RbacRoleEntity;
import com.anyilanxin.skillfull.system.modules.rbac.entity.RbacUserEntity;
import com.anyilanxin.skillfull.system.modules.rbac.mapper.RbacRoleMapper;
import com.anyilanxin.skillfull.system.modules.rbac.mapper.RbacUserMapper;
import com.anyilanxin.skillfull.system.modules.rbac.service.ISyncProcessService;
import com.anyilanxin.skillfull.system.modules.rbac.service.mapstruct.RbacUserProcessDtoMap;
import com.anyilanxin.skillfull.systemapi.model.UserRoleModel;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import io.seata.spring.annotation.GlobalTransactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * 同步流程引擎
 *
 * @author zxiaozhou
 * @date 2021-11-08 16:30
 * @since JDK1.8
 */
@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = {Exception.class, Error.class})
public class SyncProcessServiceImpl implements ISyncProcessService {
    private final RbacUserMapper userMapper;
    private final RbacRoleMapper rbacRoleMapper;
    private final ProcessSyncRbacRemoteService remoteService;
    private final RbacUserProcessDtoMap userProcessDtoMap;


    @Override
    @GlobalTransactional
    public void addOrUpdateUser(String userId) {
        // 同步用户信息
        RbacUserEntity rbacUserEntity = userMapper.selectById(userId);
        UserRequestModel vo = new UserRequestModel();
        vo.setUserId(rbacUserEntity.getUserId());
        vo.setEmail(rbacUserEntity.getEmail());
        vo.setUserName(rbacUserEntity.getUserName());
        vo.setRealName(rbacUserEntity.getRealName());
        UserDetailRequestModel userDetailVo = userProcessDtoMap.bToA(rbacUserEntity);
        vo.setDetailInfo(userDetailVo);
        Result<String> stringResult = remoteService.saveOrUpdateUser(vo);
        if (!stringResult.isSuccess()) {
            throw new ResponseException(Status.API_ERROR, "数据同步流程引擎失败");
        }
        // 同步角色信息
        Set<UserRoleModel> userRoleById = rbacRoleMapper.getUserAuthRole(userId, "", AuthConstant.SUPER_ROLE);
        if (CollUtil.isNotEmpty(userRoleById)) {
            UserGroupRequestModel userGroupVo = new UserGroupRequestModel();
            userGroupVo.setUserId(userId);
            Set<String> groupIds = new HashSet<>(userRoleById.size());
            userRoleById.forEach(v -> groupIds.add(v.getRoleId()));
            userGroupVo.setGroupIds(groupIds);
            stringResult = remoteService.deleteOrAddGroup(userGroupVo);
            if (!stringResult.isSuccess()) {
                throw new ResponseException(Status.API_ERROR, "数据同步流程引擎失败");
            }
        }
    }

    @Override
    @GlobalTransactional
    public void deleteUser(String userId) {
        // 删除用户信息
        Result<String> stringResult = remoteService.deleteUserById(userId);
        if (!stringResult.isSuccess()) {
            throw new ResponseException(Status.API_ERROR, "数据同步流程引擎失败");
        }
    }


    @Override
    @GlobalTransactional
    public void syncUserAll() {
        // 数据组装
        List<RbacUserEntity> rbacUserEntities = userMapper.selectList(Wrappers.emptyWrapper());
        if (CollUtil.isNotEmpty(rbacUserEntities)) {
            Set<UserRoleModel> userRoleById = rbacRoleMapper.getUserAuthRole("", "", AuthConstant.SUPER_ROLE);
            Map<String, Set<String>> userAndRoles = new HashMap<>(rbacUserEntities.size());
            if (CollUtil.isNotEmpty(userRoleById)) {
                userRoleById.forEach(v -> {
                    Set<String> roles = userAndRoles.get(v.getUserId());
                    if (CollectionUtil.isEmpty(roles)) {
                        roles = new HashSet<>();
                    }
                    roles.add(v.getRoleId());
                    if (CollUtil.isNotEmpty(roles)) {
                        userAndRoles.put(v.getUserId(), roles);
                    }
                });
            }
            Set<SyncUserRequestModel> voSet = new HashSet<>(rbacUserEntities.size());
            rbacUserEntities.forEach(v -> {
                SyncUserRequestModel vo = new SyncUserRequestModel();
                vo.setUserId(v.getUserId());
                vo.setEmail(v.getEmail());
                vo.setUserName(v.getUserName());
                vo.setRealName(v.getRealName());
                UserDetailRequestModel userDetailVo = userProcessDtoMap.bToA(v);
                vo.setDetailInfo(userDetailVo);
                vo.setGroupIds(userAndRoles.get(v.getUserId()));
                voSet.add(vo);
            });
            // 同步用户信息
            Result<String> stringResult = remoteService.syncUser(voSet);
            if (!stringResult.isSuccess()) {
                throw new ResponseException(Status.API_ERROR, "数据同步流程引擎失败");
            }
        }
    }


    @Override
    @GlobalTransactional
    public void addOrUpdateRole(String roleId) {
        // 同步角色信息
        RbacRoleEntity rbacRoleEntity = rbacRoleMapper.selectById(roleId);
        GroupRequestModel vo = new GroupRequestModel();
        vo.setGroupId(rbacRoleEntity.getRoleId());
        vo.setCode(rbacRoleEntity.getRoleCode());
        vo.setName(rbacRoleEntity.getRoleName());
        Result<String> stringResult = remoteService.saveOrUpdateGroup(vo);
        if (!stringResult.isSuccess()) {
            throw new ResponseException(Status.API_ERROR, "数据同步流程引擎失败");
        }
    }


    @Override
    @GlobalTransactional
    public void deleteRole(String roleId) {
        // 删除角色信息
        Result<String> stringResult = remoteService.deleteGroupById(roleId);
        if (!stringResult.isSuccess()) {
            throw new ResponseException(Status.API_ERROR, "数据同步流程引擎失败");
        }
    }


    @Override
    @GlobalTransactional
    public void syncRoleAll() {
        // 同步角色信息
        List<RbacRoleEntity> rbacRoleEntities = rbacRoleMapper.selectList(Wrappers.emptyWrapper());
        if (CollUtil.isNotEmpty(rbacRoleEntities)) {
            Set<SyncGroupRequestModel> voSet = new HashSet<>(rbacRoleEntities.size());
            rbacRoleEntities.forEach(v -> {
                SyncGroupRequestModel vo = new SyncGroupRequestModel();
                vo.setGroupId(v.getRoleId());
                vo.setCode(v.getRoleCode());
                vo.setName(v.getRoleName());
                voSet.add(vo);
            });
            Result<String> stringResult = remoteService.syncGroup(voSet);
            if (!stringResult.isSuccess()) {
                throw new ResponseException(Status.API_ERROR, "数据同步流程引擎失败");
            }
        }
    }
}
