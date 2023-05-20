/*
 * Copyright (c) 2021-2022 ZHOUXUANHONG(安一老厨)<anyilanxin@aliyun.com>
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
 *   7.本软件使用的第三方依赖皆为开源软件，如需要修改第三方源码请遵循第三方源码附带开源协议；
 *   8.本软件流程部分请遵循camunda开源协议：
 *     https://docs.camunda.org/manual/latest/introduction/third-party-libraries
 *     https://github.com/camunda/camunda-bpm-platform/blob/master/LICENSE
 *   9.若您的项目无法满足以上几点，可申请商业授权。
 */

package com.anyilanxin.anyicloud.auth.modules.login.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.anyilanxin.anyicloud.auth.modules.login.mapper.UserAuthMapper;
import com.anyilanxin.anyicloud.auth.modules.login.service.IUserAuthService;
import com.anyilanxin.anyicloud.auth.modules.login.service.dto.RbacOrgUserDto;
import com.anyilanxin.anyicloud.auth.modules.login.service.dto.RbacUserDto;
import com.anyilanxin.anyicloud.auth.modules.login.service.mapstruct.UserAuthCopyMap;
import com.anyilanxin.anyicloud.corecommon.constant.SysBaseConstant;
import com.anyilanxin.anyicloud.corecommon.exception.ResponseException;
import com.anyilanxin.anyicloud.corecommon.model.auth.OrgSimpleInfo;
import com.anyilanxin.anyicloud.corecommon.model.auth.RoleInfo;
import com.anyilanxin.anyicloud.corecommon.model.system.UserAndResourceAuthModel;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

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
    public UserAndResourceAuthModel getUserByOpenId(String openId) {
        RbacUserDto entity = userAuthMapper.selectByOpenId(openId);
        return getUserInfo(entity, null, true);
    }


    @Override
    public UserAndResourceAuthModel getUserByAccountPhone(String userName) {
        RbacUserDto entity = userAuthMapper.selectByPhoneOrAccount(userName);
        return getUserInfo(entity, null, true);
    }


    @Override
    public UserAndResourceAuthModel getUserByPhone(String phone) {
        RbacUserDto entity = userAuthMapper.selectByPhone(phone);
        return getUserInfo(entity, null, true);
    }


    /**
     * 处理机构相关
     *
     * @param userAndResourceAuthModel
     * @param orgId
     * @param roleInfos
     * @author zxh
     * @date 2022-07-12 18:39
     */
    void handleOrgInfo(UserAndResourceAuthModel userAndResourceAuthModel, String orgId, Set<RoleInfo> roleInfos) {
        // 如果机构未空，则选择最近一个，并且设置用户最近登录机构信息
        if (StringUtils.isBlank(orgId)) {
            List<RbacOrgUserDto> rbacOrgUserDtos = userAuthMapper.selectUserOrgListByUserId(userAndResourceAuthModel.getUserId());
            if (CollUtil.isNotEmpty(rbacOrgUserDtos)) {
                orgId = rbacOrgUserDtos.get(0).getOrgId();
                userAuthMapper.updateLoginOrgId(userAndResourceAuthModel.getUserId(), orgId);
            }
        }
        if (StringUtils.isNotBlank(orgId)) {
            OrgSimpleInfo orgDto = userAuthMapper.selectOrgInfoById(orgId);
            if (Objects.nonNull(orgDto)) {
                userAndResourceAuthModel.setCurrentOrgCode(orgDto.getOrgCode());
                userAndResourceAuthModel.setCurrentOrgId(orgDto.getOrgId());
                userAndResourceAuthModel.setCurrentOrgName(orgDto.getOrgName());
                userAndResourceAuthModel.setCurrentAreaCode(orgDto.getAreaCode());
                userAndResourceAuthModel.setCurrentAreaName(orgDto.getAreaCodeName());
                userAndResourceAuthModel.setOrgInfo(orgDto);
                // 获取机构授权角色
                Set<RoleInfo> orgRoleInfos = userAuthMapper.selectByUserIdAndOrgId(userAndResourceAuthModel.getUserId(), orgId);
                if (CollUtil.isNotEmpty(orgRoleInfos)) {
                    roleInfos.addAll(orgRoleInfos);
                }
            }
        }
    }


    @Override
    public UserAndResourceAuthModel getUserInfo(RbacUserDto entity, String orgId, boolean havePassword) {
        if (Objects.isNull(entity)) {
            throw new ResponseException("用户信息不存在");
        }
        if (StringUtils.isBlank(orgId)) {
            orgId = entity.getCurrentOrgId();
        }
        UserAndResourceAuthModel userAndResourceAuthModel = authCopyMap.bToA(entity);
        // 获取用户角色信息
        Set<RoleInfo> roleInfos = userAuthMapper.selectByUserId(entity.getUserId(), SysBaseConstant.SUPER_ROLE);
        if (CollUtil.isEmpty(roleInfos)) {
            roleInfos = new HashSet<>(64);
        } else {
            for (RoleInfo roleInfo : roleInfos) {
                if (SysBaseConstant.SUPER_ROLE.equals(roleInfo.getRoleCode())) {
                    userAndResourceAuthModel.setSuperAdmin(true);
                    break;
                }
            }
        }
        // 处理机构相关
        handleOrgInfo(userAndResourceAuthModel, orgId, roleInfos);
        // 如果不需要保留密码则去掉
        if (!havePassword) {
            userAndResourceAuthModel.setPassword(null);
            userAndResourceAuthModel.setSalt(null);
        }
        // 处理角色
        Set<String> roleCodes = new HashSet<>(64);
        Set<String> roleIds = new HashSet<>(64);
        roleInfos.forEach(v -> {
            roleCodes.add(v.getRoleCode());
            roleIds.add(v.getRoleId());
        });
        userAndResourceAuthModel.setRoleInfos(roleInfos);
        userAndResourceAuthModel.setRoleCodes(roleCodes);
        userAndResourceAuthModel.setRoleIds(roleIds);
        return userAndResourceAuthModel;
    }
}
