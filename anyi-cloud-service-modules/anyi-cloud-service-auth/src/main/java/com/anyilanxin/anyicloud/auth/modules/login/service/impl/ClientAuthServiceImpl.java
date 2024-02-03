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

import com.alibaba.fastjson2.JSONObject;
import com.alibaba.fastjson2.TypeReference;
import com.anyilanxin.anyicloud.auth.modules.login.mapper.ClientAuthMapper;
import com.anyilanxin.anyicloud.auth.modules.login.service.IClientAuthService;
import com.anyilanxin.anyicloud.auth.modules.login.service.dto.ClientAndResourceAuthDto;
import com.anyilanxin.anyicloud.auth.modules.login.service.mapstruct.ClientAuthCopyMap;
import com.anyilanxin.anyicloud.corecommon.model.auth.client.ClientBaseModel;
import com.anyilanxin.anyicloud.corecommon.model.auth.client.ClientSettingModel;
import com.anyilanxin.anyicloud.corecommon.model.auth.client.TokenSettingModel;
import com.anyilanxin.anyicloud.corecommon.model.system.ClientAndResourceAuthModel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.dromara.hutool.core.collection.CollUtil;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
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
public class ClientAuthServiceImpl implements IClientAuthService {
    private final ClientAuthMapper clientAuthMapper;
    private final ClientAuthCopyMap copyMap;


    @Override
    public ClientAndResourceAuthModel getByClientId(String clientId) {
        var clientDetailsModel = clientAuthMapper.selectClientIdByClientId(clientId);
        if (Objects.isNull(clientDetailsModel)) {
            throw new OAuth2AuthenticationException("客户端信息不存在:" + clientId);
        }
        ClientAndResourceAuthModel clientAndResourceAuthModel = copyMap.bToA(clientDetailsModel);
        handleSetBase(clientDetailsModel, clientAndResourceAuthModel);
        return handleData(clientAndResourceAuthModel);

    }


    /**
     * entity数据更新到model中
     */
    private void handleSetBase(ClientAndResourceAuthDto entity, ClientBaseModel model) {
        if (StringUtils.isNotBlank(entity.getAuthorizationGrantTypes())) {
            Set<String> authorizationGrantTypeInfos = JSONObject.parseObject(entity.getAuthorizationGrantTypes(), new TypeReference<Set<String>>() {
            });
            model.setAuthorizationGrantTypeInfos(authorizationGrantTypeInfos);
        }
        if (StringUtils.isNotBlank(entity.getClientAuthenticationMethods())) {
            Set<String> clientAuthenticationMethodInfos = JSONObject.parseObject(entity.getClientAuthenticationMethods(), new TypeReference<Set<String>>() {
            });
            model.setClientAuthenticationMethodInfos(clientAuthenticationMethodInfos);
        }
        if (StringUtils.isNotBlank(entity.getClientSettings())) {
            model.setClientSettingInfo(JSONObject.parseObject(entity.getClientSettings(), ClientSettingModel.class));
        }
        if (StringUtils.isNotBlank(entity.getPostLogoutRedirectUris())) {
            Set<String> postLogoutRedirectUriInfos = JSONObject.parseObject(entity.getPostLogoutRedirectUris(), new TypeReference<Set<String>>() {
            });
            model.setPostLogoutRedirectUriInfos(postLogoutRedirectUriInfos);
        }
        if (StringUtils.isNotBlank(entity.getRedirectUris())) {
            Set<String> redirectUriInfos = JSONObject.parseObject(entity.getRedirectUris(), new TypeReference<Set<String>>() {
            });
            model.setRedirectUriInfos(redirectUriInfos);
        }
        if (StringUtils.isNotBlank(entity.getScopes())) {
            Set<String> scopeInfos = JSONObject.parseObject(entity.getScopes(), new TypeReference<Set<String>>() {
            });
            model.setScopeInfos(scopeInfos);
        }
        if (StringUtils.isNotBlank(entity.getTokenSettings())) {
            model.setTokenSettingInfo(JSONObject.parseObject(entity.getTokenSettings(), TokenSettingModel.class));
        }
    }

    private ClientAndResourceAuthModel handleData(ClientAndResourceAuthModel clientDetailsModel) {
        var id = clientDetailsModel.getId();
        // 获取授权角色
        var clientAuthRole = clientAuthMapper.getClientAuthRole(id);
        var roleIds = new HashSet<String>(64);
        var roleCodes = new HashSet<String>(64);
        clientAuthRole.forEach(v -> {
            roleIds.add(v.getRoleId());
            roleCodes.add(v.getRoleCode());
        });
        clientDetailsModel.setRoleInfos(clientAuthRole);
        clientDetailsModel.setRoleCodes(roleCodes);
        clientDetailsModel.setRoleIds(roleIds);
        // 获取授权角色资源权限
        var resourceApiSimpleDtos = clientAuthMapper.selectResourceRoleApiByClientDetailId(id);
        if (CollUtil.isEmpty(resourceApiSimpleDtos)) {
            resourceApiSimpleDtos = new HashSet<>();
        }
        // 获取关联资源权限
        var clientResourceInfos = clientAuthMapper.selectResourceApiByClientDetailId(id);
        if (CollUtil.isNotEmpty(clientResourceInfos)) {
            resourceApiSimpleDtos.addAll(clientResourceInfos);
        }
        if (CollUtil.isNotEmpty(resourceApiSimpleDtos)) {
            var actionMap = new HashMap<String, Set<String>>(resourceApiSimpleDtos.size());
            resourceApiSimpleDtos.forEach(v -> {
                Set<String> actions = actionMap.get(v.getResourceCode());
                if (CollUtil.isEmpty(actions)) {
                    actions = new HashSet<>(64);
                }
                if (StringUtils.isNotBlank(v.getPermissionAction())) {
                    actions.addAll(Set.of(v.getPermissionAction().split("[,，]")));
                }
                actionMap.put(v.getResourceCode(), actions);
            });
            clientDetailsModel.setActions(actionMap);
        }
        return clientDetailsModel;
    }


    @Override
    public ClientAndResourceAuthModel getById(String id) {
        var clientDetailsModel = clientAuthMapper.selectClientIdById(id);
        if (Objects.isNull(clientDetailsModel)) {
            throw new OAuth2AuthenticationException("客户端信息不存在:" + id);
        }
        ClientAndResourceAuthModel clientAndResourceAuthModel = copyMap.bToA(clientDetailsModel);
        handleSetBase(clientDetailsModel, clientAndResourceAuthModel);
        return handleData(clientAndResourceAuthModel);
    }
}
