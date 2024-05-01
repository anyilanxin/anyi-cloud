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

package com.anyilanxin.cloud.process.modules.rbac.service.impl;

import com.anyilanxin.cloud.corecommon.base.AnYiResult;
import com.anyilanxin.cloud.corecommon.constant.AnYiResultStatus;
import com.anyilanxin.cloud.corecommon.exception.AnYiResponseException;
import com.anyilanxin.cloud.corecommon.model.auth.RoleInfo;
import com.anyilanxin.cloud.process.modules.rbac.service.IProcessIdentityService;
import com.anyilanxin.cloud.process.modules.rbac.service.mapstruct.RoleCopyMap;
import com.anyilanxin.cloud.process.modules.rbac.service.mapstruct.UserCopyMap;
import com.anyilanxin.cloud.processadapter.model.AnYiProcessUserModel;
import com.anyilanxin.cloud.processadapter.model.CustomIdentityLink;
import com.anyilanxin.cloud.processadapter.model.ProcessRoleModel;
import com.anyilanxin.cloud.systemadapter.model.SimpleUserModel;
import com.anyilanxin.cloud.systemrpcadapter.rpc.SystemRoleRemoteRpc;
import com.anyilanxin.cloud.systemrpcadapter.rpc.SystemUserRemoteRpc;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.dromara.hutool.core.collection.CollUtil;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * 用户组相关
 *
 * @author zxh
 * @date 2021-11-05 17:30
 * @since 1.0.0
 */
@RequiredArgsConstructor
@Service
public class ProcessIdentityServiceImpl implements IProcessIdentityService {
    private final SystemUserRemoteRpc remoteUserService;
    private final SystemRoleRemoteRpc remoteRoleService;
    private final UserCopyMap userCopyMap;
    private final RoleCopyMap roleCopyMap;

    @Override
    public Map<String, AnYiProcessUserModel> getUserByIds(Set<String> userIds) {
        if (CollUtil.isNotEmpty(userIds)) {
            AnYiResult<List<SimpleUserModel>> userListByIds = remoteUserService.getUserListByIds(new ArrayList<>(userIds));
            if (!userListByIds.isSuccess() || CollUtil.isEmpty(userListByIds.getData())) {
                throw new AnYiResponseException(AnYiResultStatus.API_ERROR, "获取用户信息失败:" + userListByIds.getMessage());
            }
            List<SimpleUserModel> data = userListByIds.getData();
            Map<String, AnYiProcessUserModel> userModelMap = new HashMap<>(data.size());
            data.forEach(v -> userModelMap.put(v.getUserId(), userCopyMap.bToA(v)));
            return userModelMap;
        }
        return Collections.emptyMap();
    }


    @Override
    public AnYiProcessUserModel getUserById(String userId) {
        return null;
    }


    @Override
    public Map<String, ProcessRoleModel> getRoleByIds(Set<String> roleIds) {
        if (CollUtil.isNotEmpty(roleIds)) {
            AnYiResult<List<RoleInfo>> roleListByIds = remoteRoleService.getRoleListByIds(new ArrayList<>(roleIds));
            if (!roleListByIds.isSuccess() || CollUtil.isEmpty(roleListByIds.getData())) {
                throw new AnYiResponseException(AnYiResultStatus.API_ERROR, "获取角色信息失败:" + roleListByIds.getMessage());
            }
            List<RoleInfo> data = roleListByIds.getData();
            Map<String, ProcessRoleModel> roleModelMap = new HashMap<>(data.size());
            data.forEach(v -> roleModelMap.put(v.getRoleId(), roleCopyMap.bToA(v)));
            return roleModelMap;
        }
        return Collections.emptyMap();
    }


    @Override
    public ProcessRoleModel getRoleById(String roleId) {
        return null;
    }


    @Override
    public void completionUserOrRole(List<CustomIdentityLink> customIdentityLinkList) {
        if (CollUtil.isNotEmpty(customIdentityLinkList)) {
            // 分离出用户与角色
            Set<String> userIdSet = new HashSet<>(customIdentityLinkList.size());
            Set<String> roleIdSet = new HashSet<>(customIdentityLinkList.size());
            customIdentityLinkList.forEach(v -> {
                if (StringUtils.isNotBlank(v.getUserId())) {
                    userIdSet.add(v.getUserId());
                } else {
                    roleIdSet.add(v.getGroupId());
                }
            });
            Map<String, AnYiProcessUserModel> userByIds = getUserByIds(userIdSet);
            Map<String, ProcessRoleModel> roleByIds = getRoleByIds(roleIdSet);
            customIdentityLinkList.forEach(v -> {
                if (StringUtils.isNotBlank(v.getUserId())) {
                    AnYiProcessUserModel processUserModel = userByIds.get(v.getUserId());
                    if (Objects.nonNull(processUserModel)) {
                        v.setUserRealName(processUserModel.getRealName());
                    }
                } else {
                    ProcessRoleModel processRoleModel = roleByIds.get(v.getGroupId());
                    if (Objects.nonNull(processRoleModel)) {
                        v.setGroupName(processRoleModel.getRoleName());
                    }
                }
            });
        }

    }


    @Override
    public void completionUserOrRole(CustomIdentityLink customIdentityLink) {
        if (Objects.nonNull(customIdentityLink)) {
            System.out.println(customIdentityLink);
        }
    }
}
