

package com.anyilanxin.anyicloud.auth.modules.login.service.impl;

import com.anyilanxin.anyicloud.auth.modules.login.mapper.ClientAuthMapper;
import com.anyilanxin.anyicloud.auth.modules.login.service.IClientAuthService;
import com.anyilanxin.anyicloud.corecommon.exception.ResponseException;
import com.anyilanxin.anyicloud.corecommon.model.auth.RoleInfo;
import com.anyilanxin.anyicloud.corecommon.model.system.ClientAndResourceAuthModel;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
        return clientDetailsModel;
    }
}
