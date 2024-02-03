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

package com.anyilanxin.anyicloud.auth.modules.login.service.impl;

import com.anyilanxin.anyicloud.auth.modules.login.mapper.UserAuthMapper;
import com.anyilanxin.anyicloud.auth.modules.login.service.IUserAuthService;
import com.anyilanxin.anyicloud.auth.modules.login.service.dto.RbacResourceApiSimpleDto;
import com.anyilanxin.anyicloud.auth.modules.login.service.dto.RbacUserDto;
import com.anyilanxin.anyicloud.auth.modules.login.service.mapstruct.UserAuthCopyMap;
import com.anyilanxin.anyicloud.corecommon.constant.AuthConstant;
import com.anyilanxin.anyicloud.corecommon.model.auth.RoleInfo;
import com.anyilanxin.anyicloud.corecommon.model.system.AnYiUserAndResourceAuthModel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.dromara.hutool.core.collection.CollUtil;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * 用户中心
 *
 * @author zxh
 * @date 2022-05-02 09:18
 * @since 1.0.0
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class UserAuthServiceImpl implements IUserAuthService {
    private final UserAuthCopyMap authCopyMap;
    private final UserAuthMapper userAuthMapper;

    @Override
    public AnYiUserAndResourceAuthModel getUserByOpenId(String openId) {
        var entity = userAuthMapper.selectByOpenId(openId);
        return getUserInfo(entity, null, true);
    }


    @Override
    public AnYiUserAndResourceAuthModel getUserByAccountPhone(String userName) {
        var entity = userAuthMapper.selectByPhoneOrAccount(userName);
        return getUserInfo(entity, null, true);
    }


    @Override
    public AnYiUserAndResourceAuthModel getUserByPhone(String phone) {
        var entity = userAuthMapper.selectByPhone(phone);
        return getUserInfo(entity, null, true);
    }


    /**
     * 处理机构相关
     *
     * @param userAndResourceAuthModel
     * @param orgId
     * @param roleInfos
     * @param resourceApiSimpleAll
     * @author zxh
     * @date 2022-07-12 18:39
     */
    void handleOrgInfo(AnYiUserAndResourceAuthModel userAndResourceAuthModel, String orgId, Set<RoleInfo> roleInfos, Set<RbacResourceApiSimpleDto> resourceApiSimpleAll) {
        // 如果机构未空，则选择最近一个，并且设置用户最近登录机构信息
        if (StringUtils.isBlank(orgId)) {
            var rbacOrgUserDtos = userAuthMapper.selectUserOrgListByUserId(userAndResourceAuthModel.getUserId());
            if (CollUtil.isNotEmpty(rbacOrgUserDtos)) {
                orgId = rbacOrgUserDtos.get(0).getOrgId();
                userAuthMapper.updateLoginOrgId(userAndResourceAuthModel.getUserId(), orgId);
            }
        }
        if (StringUtils.isNotBlank(orgId)) {
            var orgDto = userAuthMapper.selectOrgInfoById(orgId);
            if (Objects.nonNull(orgDto)) {
                userAndResourceAuthModel.setCurrentOrgCode(orgDto.getOrgCode());
                userAndResourceAuthModel.setCurrentOrgId(orgDto.getOrgId());
                userAndResourceAuthModel.setCurrentOrgName(orgDto.getOrgName());
                userAndResourceAuthModel.setCurrentAreaCode(orgDto.getAreaCode());
                userAndResourceAuthModel.setCurrentAreaName(orgDto.getAreaCodeName());
                userAndResourceAuthModel.setOrgInfo(orgDto);
                // 获取机构授权角色
                var orgRoleInfos = userAuthMapper.selectByUserIdAndOrgId(userAndResourceAuthModel.getUserId(), orgId);
                if (CollUtil.isNotEmpty(orgRoleInfos)) {
                    roleInfos.addAll(orgRoleInfos);
                }
                // 获取机构角色关联资源权限
                if (!userAndResourceAuthModel.isSuperAdmin()) {
                    Set<RbacResourceApiSimpleDto> rbacResourceApiSimpleDtos = userAuthMapper.selectOrgResourceApiByUserId(userAndResourceAuthModel.getUserId(), orgId);
                    if (CollUtil.isNotEmpty(rbacResourceApiSimpleDtos)) {
                        resourceApiSimpleAll.addAll(rbacResourceApiSimpleDtos);
                    }
                }
            }
        }
    }


    @Override
    public AnYiUserAndResourceAuthModel getUserInfo(RbacUserDto entity, String orgId, boolean havePassword) {
        if (Objects.isNull(entity)) {
            return null;
        }
        if (StringUtils.isBlank(orgId)) {
            orgId = entity.getCurrentOrgId();
        }
        var userAndResourceAuthModel = authCopyMap.bToA(entity);
        // 获取用户角色信息
        var roleInfos = userAuthMapper.selectByUserId(entity.getUserId(), AuthConstant.SUPER_ROLE);
        if (CollUtil.isEmpty(roleInfos)) {
            roleInfos = new HashSet<>(64);
        } else {
            for (RoleInfo roleInfo : roleInfos) {
                if (roleInfo.isSuperRole()) {
                    userAndResourceAuthModel.setSuperAdmin(true);
                    break;
                }
            }
        }
        var resourceApiSimpleAll = new HashSet<RbacResourceApiSimpleDto>(128);
        if (userAndResourceAuthModel.isSuperAdmin()) {
            Set<RbacResourceApiSimpleDto> resourceApiSimpleDtos = userAuthMapper.selectResourceApiAll();
            if (CollUtil.isNotEmpty(resourceApiSimpleDtos)) {
                resourceApiSimpleAll.addAll(resourceApiSimpleDtos);
            }
        } else {
            // 获取用户资源授权
            var resourceApiSimpleDtos = userAuthMapper.selectResourceApiByUserId(entity.getUserId());
            if (CollUtil.isNotEmpty(resourceApiSimpleDtos)) {
                resourceApiSimpleAll.addAll(resourceApiSimpleDtos);
            }
        }
        // 处理机构相关
        handleOrgInfo(userAndResourceAuthModel, orgId, roleInfos, resourceApiSimpleAll);
        // 如果不需要保留密码则去掉
        if (!havePassword) {
            userAndResourceAuthModel.setPassword(null);
            userAndResourceAuthModel.setSalt(null);
        }
        // 处理角色
        var roleCodes = new HashSet<String>(64);
        var roleIds = new HashSet<String>(64);
        roleInfos.forEach(v -> {
            roleCodes.add(v.getRoleCode());
            roleIds.add(v.getRoleId());
        });
        userAndResourceAuthModel.setRoleInfos(roleInfos);
        userAndResourceAuthModel.setRoleCodes(roleCodes);
        userAndResourceAuthModel.setRoleIds(roleIds);
        // 处理授权资源
        if (CollUtil.isNotEmpty(resourceApiSimpleAll)) {
            var actionMap = new HashMap<String, Set<String>>(resourceApiSimpleAll.size());
            resourceApiSimpleAll.forEach(v -> {
                Set<String> actionSet = actionMap.get(v.getResourceCode());
                if (CollUtil.isEmpty(actionSet)) {
                    actionSet = new HashSet<>();
                }
                if (StringUtils.isNotBlank(v.getPermissionAction())) {
                    actionSet.addAll(Set.of(v.getPermissionAction().split("[,，]")));
                }
                actionMap.put(v.getResourceCode(), actionSet);
            });
            userAndResourceAuthModel.setActions(actionMap);
        }
        return userAndResourceAuthModel;
    }
}
