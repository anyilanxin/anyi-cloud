// +----------------------------------------------------------------------
// | SkillFull快速开发平台 [ SkillFull ]
// +----------------------------------------------------------------------
// | 版权所有 2020~2021 zxiaozhou
// +----------------------------------------------------------------------
// | 官方网站: https://skillfull.divisu.com
// +----------------------------------------------------------------------
// | 作者: zxiaozhou <z7630853@163.com>
// +----------------------------------------------------------------------
package com.anyilanxin.skillfull.process.modules.rbac.service.impl;

import com.anyilanxin.skillfull.process.modules.rbac.service.IProcessIdentityService;
import com.anyilanxin.skillfull.processrpc.model.ProcessRoleModel;
import com.anyilanxin.skillfull.processrpc.model.ProcessUserModel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Map;
import java.util.Set;

/**
 * 用户组相关
 *
 * @author zxiaozhou
 * @date 2021-11-05 17:30
 * @since JDK1.8
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
