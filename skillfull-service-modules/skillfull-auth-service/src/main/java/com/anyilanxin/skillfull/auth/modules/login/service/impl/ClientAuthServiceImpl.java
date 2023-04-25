package com.anyilanxin.skillfull.auth.modules.login.service.impl;

import com.anyilanxin.skillfull.auth.modules.login.mapper.ClientAuthMapper;
import com.anyilanxin.skillfull.auth.modules.login.service.IClientAuthService;
import com.anyilanxin.skillfull.corecommon.exception.ResponseException;
import com.anyilanxin.skillfull.corecommon.model.auth.RoleInfo;
import com.anyilanxin.skillfull.corecommon.model.system.ClientAndResourceAuthModel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

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
        return clientDetailsModel;
    }
}
