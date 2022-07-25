// +----------------------------------------------------------------------
// | SkillFull快速开发平台 [ SkillFull ]
// +----------------------------------------------------------------------
// | 版权所有 2020~2021 zxiaozhou
// +----------------------------------------------------------------------
// | 官方网站: https://www.divisu.com
// +----------------------------------------------------------------------
// | 作者: zxiaozhou <z7630853@163.com>
// +----------------------------------------------------------------------
package com.anyilanxin.skillfull.auth.modules.login.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.anyilanxin.skillfull.auth.modules.login.mapper.ClientAuthMapper;
import com.anyilanxin.skillfull.auth.modules.login.service.IClientAuthService;
import com.anyilanxin.skillfull.corecommon.auth.model.RoleInfo;
import com.anyilanxin.skillfull.corecommon.base.model.system.ClientAndResourceAuthModel;
import com.anyilanxin.skillfull.corecommon.exception.ResponseException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * 用户中心
 *
 * @author zxiaozhou
 * @date 2022-05-02 09:18
 * @since JDK1.8
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ClientAuthServiceImpl implements IClientAuthService {
    private final ClientAuthMapper clientAuthMapper;


    @Override
    public ClientAndResourceAuthModel getByClientId(String clientId) {
        ClientAndResourceAuthModel clientDetailsModel = clientAuthMapper.selectClientIdByClientId(clientId);
        if (Objects.isNull(clientDetailsModel)) {
            throw new ResponseException("客户端信息不存在:" + clientId);
        }
        String clientDetailId = clientDetailsModel.getClientDetailId();
        // 获取授权角色
        Set<RoleInfo> clientAuthRole = clientAuthMapper.getClientAuthRole(clientDetailId);
        Set<String> roleIds = new HashSet<>(64);
        Set<String> roleCodes = new HashSet<>(64);
        clientAuthRole.forEach(v -> {
            roleIds.add(v.getRoleId());
            roleCodes.add(v.getRoleCode());
        });
        clientDetailsModel.setRoleInfos(clientAuthRole);
        clientDetailsModel.setRoleCodes(roleCodes);
        clientDetailsModel.setRoleIds(roleIds);
        // 获取授权角色资源权限
        Set<RbacResourceApiSimpleDto> resourceApiSimpleDtos = clientAuthMapper.selectResourceRoleApiByClientDetailId(clientDetailId);
        if (CollUtil.isEmpty(resourceApiSimpleDtos)) {
            resourceApiSimpleDtos = new HashSet<>();
        }
        // 获取关联资源权限
        Set<RbacResourceApiSimpleDto> clientResourceInfos = clientAuthMapper.selectResourceApiByClientDetailId(clientDetailId);
        if (CollUtil.isNotEmpty(clientResourceInfos)) {
            resourceApiSimpleDtos.addAll(clientResourceInfos);
        }
        if (CollUtil.isNotEmpty(resourceApiSimpleDtos)) {
            Map<String, Set<String>> actionMap = new HashMap<>(resourceApiSimpleDtos.size());
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
}
