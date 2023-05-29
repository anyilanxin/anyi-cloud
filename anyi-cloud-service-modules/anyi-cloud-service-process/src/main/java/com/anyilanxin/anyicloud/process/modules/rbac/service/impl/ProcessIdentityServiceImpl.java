

package com.anyilanxin.anyicloud.process.modules.rbac.service.impl;

import com.anyilanxin.anyicloud.process.modules.rbac.service.IProcessIdentityService;
import com.anyilanxin.anyicloud.processrpc.model.ProcessRoleModel;
import com.anyilanxin.anyicloud.processrpc.model.ProcessUserModel;

import java.util.Collections;
import java.util.Map;
import java.util.Set;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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

    @Override
    public Map<String, ProcessUserModel> getUserByIds(Set<String> userIds) {
        return Collections.emptyMap();
    }


    @Override
    public ProcessUserModel getUserById(String userId) {
        return null;
    }


    @Override
    public Map<String, ProcessRoleModel> getRoleByIds(Set<String> roleIds) {
        return Collections.emptyMap();
    }


    @Override
    public ProcessRoleModel getRoleById(String roleId) {
        return null;
    }
}
