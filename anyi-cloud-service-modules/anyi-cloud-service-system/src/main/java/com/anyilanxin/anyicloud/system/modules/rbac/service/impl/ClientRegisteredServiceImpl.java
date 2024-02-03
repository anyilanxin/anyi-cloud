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

import com.alibaba.fastjson2.JSONObject;
import com.alibaba.fastjson2.TypeReference;
import com.anyilanxin.anyicloud.corecommon.constant.AnYiResultStatus;
import com.anyilanxin.anyicloud.corecommon.constant.impl.CommonNotHaveType;
import com.anyilanxin.anyicloud.corecommon.exception.AnYiResponseException;
import com.anyilanxin.anyicloud.corecommon.model.auth.client.ClientBaseModel;
import com.anyilanxin.anyicloud.corecommon.model.auth.client.ClientSettingModel;
import com.anyilanxin.anyicloud.corecommon.model.auth.client.TokenSettingModel;
import com.anyilanxin.anyicloud.corecommon.model.common.AnYiPageResult;
import com.anyilanxin.anyicloud.corecommon.utils.AnYiI18nUtil;
import com.anyilanxin.anyicloud.database.utils.PageUtils;
import com.anyilanxin.anyicloud.oauth2common.utils.PasswordCheck;
import com.anyilanxin.anyicloud.system.modules.rbac.controller.vo.ClientRegisteredPageQuery;
import com.anyilanxin.anyicloud.system.modules.rbac.controller.vo.ClientRegisteredVo;
import com.anyilanxin.anyicloud.system.modules.rbac.controller.vo.RbacClientAuthVo;
import com.anyilanxin.anyicloud.system.modules.rbac.entity.ClientRegisteredEntity;
import com.anyilanxin.anyicloud.system.modules.rbac.mapper.ClientRegisteredMapper;
import com.anyilanxin.anyicloud.system.modules.rbac.mapper.RbacRoleClientMapper;
import com.anyilanxin.anyicloud.system.modules.rbac.service.IClientRegisteredService;
import com.anyilanxin.anyicloud.system.modules.rbac.service.IRbacRoleClientService;
import com.anyilanxin.anyicloud.system.modules.rbac.service.dto.ClientRegisteredDto;
import com.anyilanxin.anyicloud.system.modules.rbac.service.dto.ClientRegisteredPageDto;
import com.anyilanxin.anyicloud.system.modules.rbac.service.dto.RbacRoleSimpleDto;
import com.anyilanxin.anyicloud.system.modules.rbac.service.mapstruct.ClientRegisteredCopyMap;
import com.anyilanxin.anyicloud.system.modules.rbac.service.mapstruct.ClientRegisteredPageCopyMap;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.dromara.hutool.core.collection.CollUtil;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 授权客户端信息(ClientRegistered)业务层实现
 *
 * @author zxh
 * @copyright zhouxuanhong（https://anyilanxin.com）
 * @date 2023-09-24 14:48:16
 * @since JDK11
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ClientRegisteredServiceImpl extends ServiceImpl<ClientRegisteredMapper, ClientRegisteredEntity> implements IClientRegisteredService {
    private final ClientRegisteredCopyMap map;
    private final ClientRegisteredPageCopyMap pageCopyMap;
    private final ClientRegisteredMapper mapper;
    private final IRbacClientResourceApiService resourceApiService;
    private final IRbacRoleClientService roleClientService;
    private final RbacRoleClientMapper rbacRoleClientMapper;
    private final PasswordEncoder passwordEncoder;
    private final RbacClientResourceApiMapper resourceApiMapper;


    @Override
    @Transactional(rollbackFor = {Exception.class, Error.class})
    public void save(ClientRegisteredVo vo) throws RuntimeException {
        var entity = map.vToE(vo);
        handleSetEntity(vo, entity);
        check(entity);
        PasswordCheck passwordCheck = PasswordCheck.getSingleton(passwordEncoder);
        String encodePassword = passwordCheck.getEncodePassword(vo.getClientSecurity());
        entity.setClientSecurity(encodePassword);
        var result = super.save(entity);
        if (!result) {
            throw new AnYiResponseException(AnYiResultStatus.DATABASE_BASE_ERROR, AnYiI18nUtil.get("ServiceImpl.SaveDataFail"));
        }
    }


    /**
     * 数据校验
     *
     * @param detailsEntity 客户端信息
     * @author zxh
     * @date 2022-02-10 16:19
     */
    @Transactional(rollbackFor = {Exception.class, Error.class}, readOnly = true)
    public void check(ClientRegisteredEntity detailsEntity) {
        if (StringUtils.isBlank(detailsEntity.getId())) {
            if (StringUtils.isBlank(detailsEntity.getClientId())) {
                throw new AnYiResponseException(AnYiResultStatus.VERIFICATION_FAILED, "客户端id不能为空");
            }
            if (StringUtils.isBlank(detailsEntity.getClientSecurity())) {
                throw new AnYiResponseException(AnYiResultStatus.VERIFICATION_FAILED, "客户端密码不能为空");
            }
            LambdaQueryWrapper<ClientRegisteredEntity> lambdaQueryWrapper = new LambdaQueryWrapper<>();
            lambdaQueryWrapper.eq(ClientRegisteredEntity::getClientId, detailsEntity.getClientId());
            ClientRegisteredEntity one = this.getOne(lambdaQueryWrapper);
            if (Objects.nonNull(one)) {
                throw new AnYiResponseException(AnYiResultStatus.VERIFICATION_FAILED, "客户端编码已经存在，请换一个");
            }
        } else {
            detailsEntity.setClientId(null);
            detailsEntity.setClientSecurity(null);
        }
        if (detailsEntity.getLimitError() == CommonNotHaveType.HAVE.getType() && (Objects.isNull(detailsEntity.getMaxErrorNum()) || detailsEntity.getMaxErrorNum() <= 0)) {
            throw new AnYiResponseException(AnYiResultStatus.VERIFICATION_FAILED, "当前设置了需要限制验证错误次数，请填写最大允许错误次数");
        }
    }


    @Override
    @Transactional(rollbackFor = {Exception.class, Error.class})
    public void updateById(String id, ClientRegisteredVo vo) throws RuntimeException {
        // 查询数据是否存在
        this.getById(id);
        // 更新数据
        var entity = map.vToE(vo);
        entity.setId(id);
        check(entity);
        handleSetEntity(vo, entity);
        var result = super.updateById(entity);
        if (!result) {
            throw new AnYiResponseException(AnYiResultStatus.DATABASE_BASE_ERROR, AnYiI18nUtil.get("ServiceImpl.UpdateDataFail"));
        }
    }


    /**
     * model数据更新到entity中
     */
    private void handleSetEntity(ClientBaseModel model, ClientRegisteredEntity entity) {
        if (CollUtil.isNotEmpty(model.getAuthorizationGrantTypeInfos())) {
            entity.setAuthorizationGrantTypes(JSONObject.toJSONString(model.getAuthorizationGrantTypeInfos()));
        }
        if (CollUtil.isNotEmpty(model.getClientAuthenticationMethodInfos())) {
            entity.setClientAuthenticationMethods(JSONObject.toJSONString(model.getClientAuthenticationMethodInfos()));
        }
        if (Objects.nonNull(model.getClientSettingInfo())) {
            entity.setClientSettings(JSONObject.toJSONString(model.getClientSettingInfo()));
        }
        if (CollUtil.isNotEmpty(model.getPostLogoutRedirectUriInfos())) {
            entity.setPostLogoutRedirectUris(JSONObject.toJSONString(model.getPostLogoutRedirectUriInfos()));
        }
        if (CollUtil.isNotEmpty(model.getRedirectUriInfos())) {
            entity.setRedirectUris(JSONObject.toJSONString(model.getRedirectUriInfos()));
        }
        if (CollUtil.isNotEmpty(model.getScopeInfos())) {
            entity.setScopes(JSONObject.toJSONString(model.getScopeInfos()));
        }
        if (Objects.nonNull(model.getTokenSettingInfo())) {
            entity.setTokenSettings(JSONObject.toJSONString(model.getTokenSettingInfo()));
        }
    }


    /**
     * entity数据更新到model中
     */
    private void handleSetBase(ClientRegisteredEntity entity, ClientBaseModel model) {
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


    @Override
    @Transactional(rollbackFor = {Exception.class, Error.class}, readOnly = true)
    public AnYiPageResult<ClientRegisteredPageDto> pageByModel(ClientRegisteredPageQuery vo) throws RuntimeException {
        LambdaQueryWrapper<ClientRegisteredEntity> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(StringUtils.isNotBlank(vo.getClientId()), ClientRegisteredEntity::getClientId, vo.getClientId())
                .like(StringUtils.isNotBlank(vo.getClientName()), ClientRegisteredEntity::getClientName, vo.getClientName())
                .eq(Objects.nonNull(vo.getClientStatus()), ClientRegisteredEntity::getClientStatus, vo.getClientStatus())
                .le(Objects.nonNull(vo.getEndTime()), ClientRegisteredEntity::getCreateTime, vo.getEndTime())
                .ge(Objects.nonNull(vo.getStartTime()), ClientRegisteredEntity::getCreateTime, vo.getStartTime());
        Page<ClientRegisteredEntity> page = this.page(PageUtils.getPage(vo), lambdaQueryWrapper);
        List<ClientRegisteredPageDto> results = page.getRecords().stream().map(v -> {
            ClientRegisteredPageDto clientRegisteredPageDto = pageCopyMap.eToD(v);
            handleSetBase(v, clientRegisteredPageDto);
            return clientRegisteredPageDto;
        }).collect(Collectors.toList());
        return PageUtils.toPageData(page, results);
    }


    @Override
    @Transactional(rollbackFor = {Exception.class, Error.class}, readOnly = true)
    public ClientRegisteredDto getById(String id) throws RuntimeException {
        var byId = super.getById(id);
        if (Objects.isNull(byId)) {
            throw new AnYiResponseException(AnYiResultStatus.DATABASE_BASE_ERROR, AnYiI18nUtil.get("ServiceImpl.QueryDataFail"));
        }
        ClientRegisteredDto clientRegisteredDto = map.eToD(byId);
        handleSetBase(byId, clientRegisteredDto);
        // 获取资源权限
        Set<String> resourceIds = resourceApiMapper.selectResourceApiListById(id);
        if (CollUtil.isEmpty(resourceIds)) {
            resourceIds = Collections.emptySet();
        }
        Set<RbacResourceApiPageDto> resrouceInfos = resourceApiMapper.selectResourceApiAllInfoListById(id);
        if (CollUtil.isEmpty(resrouceInfos)) {
            resrouceInfos = Collections.emptySet();
        }
        clientRegisteredDto.setClientResourceApiIds(resourceIds);
        clientRegisteredDto.setClientResourceApiInfos(resrouceInfos);
        // 获取管关联角色
        Set<String> roleIds = rbacRoleClientMapper.selectRoleListById(id);
        if (CollUtil.isEmpty(roleIds)) {
            roleIds = Collections.emptySet();
        }
        Set<RbacRoleSimpleDto> roleInfos = rbacRoleClientMapper.selectRoleAllInfoListById(id);
        if (CollUtil.isEmpty(roleInfos)) {
            roleInfos = Collections.emptySet();
        }
        clientRegisteredDto.setRoleIds(roleIds);
        clientRegisteredDto.setRoleInfos(roleInfos);
        return clientRegisteredDto;
    }


    @Override
    @Transactional(rollbackFor = {Exception.class, Error.class})
    public void deleteById(String id) throws RuntimeException {
        // 查询数据是否存在
        this.getById(id);
        // 删除数据
        var b = this.removeById(id);
        if (!b) {
            throw new AnYiResponseException(AnYiResultStatus.DATABASE_BASE_ERROR, AnYiI18nUtil.get("ServiceImpl.DeleteDataFail"));
        }
    }


    @Override
    @Transactional(rollbackFor = {Exception.class, Error.class})
    public void deleteBatch(List<String> ids) throws RuntimeException {
        var entities = this.listByIds(ids);
        if (CollUtil.isEmpty(entities)) {
            throw new AnYiResponseException(AnYiResultStatus.DATABASE_BASE_ERROR, AnYiI18nUtil.get("ServiceImpl.QueryDataFailOrDelete"));
        }
        var waitDeleteList = new ArrayList<String>();
        entities.forEach(v -> waitDeleteList.add(v.getId()));
        var i = mapper.deleteBatchIds(waitDeleteList);
        if (i <= 0) {
            throw new AnYiResponseException(AnYiResultStatus.DATABASE_BASE_ERROR, AnYiI18nUtil.get("ServiceImpl.BatchDeleteDataFail"));
        }
    }


    @Override
    @Transactional(rollbackFor = {Exception.class, Error.class})
    public void updateState(String id, Integer type) {
        // 查询数据是否存在
        this.getById(id);
        ClientRegisteredEntity detailsEntity = ClientRegisteredEntity.builder()
                .id(id)
                .clientStatus(type)
                .build();
        boolean result = super.updateById(detailsEntity);
        if (!result) {
            throw new AnYiResponseException("修改状态失败");
        }
    }


    @Override
    @Transactional(rollbackFor = {Exception.class, Error.class})
    public void updateAuth(String id, RbacClientAuthVo vo) {
        // 查询客户端是否存在
        this.getById(id);
        // 更新角色关联
        roleClientService.deleteBatch(List.of(id));
        roleClientService.saveBatch(id, vo.getRoleIds());
        // 更新资源关联
        resourceApiService.deleteBatch(List.of(id));
        resourceApiService.saveBatch(id, vo.getClientResourceApiIds());
    }


//    public static void main(String[] args) {
//        PasswordCheck passwordCheck = PasswordCheck.getSingleton(new BCryptPasswordEncoder());
//        String encodePassword = passwordCheck.getEncodePassword("connectorworker_service_client");
//        System.out.println(encodePassword);
//    }
}
