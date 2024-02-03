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

import com.anyilanxin.anyicloud.corecommon.base.AnYiResult;
import com.anyilanxin.anyicloud.corecommon.constant.AnYiResultStatus;
import com.anyilanxin.anyicloud.corecommon.constant.AuthConstant;
import com.anyilanxin.anyicloud.corecommon.exception.AnYiResponseException;
import com.anyilanxin.anyicloud.processadapter.model.*;
import com.anyilanxin.anyicloud.processrpcadapter.rpc.ProcessRemoteRpcSyncRbac;
import com.anyilanxin.anyicloud.system.modules.rbac.entity.RbacRoleEntity;
import com.anyilanxin.anyicloud.system.modules.rbac.entity.RbacUserEntity;
import com.anyilanxin.anyicloud.system.modules.rbac.mapper.RbacRoleMapper;
import com.anyilanxin.anyicloud.system.modules.rbac.mapper.RbacUserMapper;
import com.anyilanxin.anyicloud.system.modules.rbac.service.ISyncProcessService;
import com.anyilanxin.anyicloud.system.modules.rbac.service.mapstruct.RbacUserProcessDtoMap;
import com.anyilanxin.anyicloud.systemadapter.model.UserRoleModel;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import io.seata.spring.annotation.GlobalTransactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dromara.hutool.core.collection.CollUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * 同步流程引擎
 *
 * @author zxh
 * @date 2021-11-08 16:30
 * @since 1.0.0
 */
@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = {Exception.class, Error.class})
public class SyncProcessServiceImpl implements ISyncProcessService {
    private final RbacUserMapper userMapper;
    private final RbacRoleMapper rbacRoleMapper;
    private final ProcessRemoteRpcSyncRbac remoteService;
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
        AnYiResult<String> stringResult = remoteService.saveOrUpdateUser(vo);
        if (!stringResult.isSuccess()) {
            throw new AnYiResponseException(AnYiResultStatus.API_ERROR, "数据同步流程引擎失败");
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
                throw new AnYiResponseException(AnYiResultStatus.API_ERROR, "数据同步流程引擎失败");
            }
        }
    }


    @Override
    @GlobalTransactional
    public void deleteUser(String userId) {
        // 删除用户信息
        AnYiResult<String> stringResult = remoteService.deleteUserById(userId);
        if (!stringResult.isSuccess()) {
            throw new AnYiResponseException(AnYiResultStatus.API_ERROR, "数据同步流程引擎失败");
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
                    if (CollUtil.isEmpty(roles)) {
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
            AnYiResult<String> stringResult = remoteService.syncUser(voSet);
            if (!stringResult.isSuccess()) {
                throw new AnYiResponseException(AnYiResultStatus.API_ERROR, "数据同步流程引擎失败");
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
        AnYiResult<String> stringResult = remoteService.saveOrUpdateGroup(vo);
        if (!stringResult.isSuccess()) {
            throw new AnYiResponseException(AnYiResultStatus.API_ERROR, "数据同步流程引擎失败");
        }
    }


    @Override
    @GlobalTransactional
    public void deleteRole(String roleId) {
        // 删除角色信息
        AnYiResult<String> stringResult = remoteService.deleteGroupById(roleId);
        if (!stringResult.isSuccess()) {
            throw new AnYiResponseException(AnYiResultStatus.API_ERROR, "数据同步流程引擎失败");
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
            AnYiResult<String> stringResult = remoteService.syncGroup(voSet);
            if (!stringResult.isSuccess()) {
                throw new AnYiResponseException(AnYiResultStatus.API_ERROR, "数据同步流程引擎失败");
            }
        }
    }
}
