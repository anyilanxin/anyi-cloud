// +----------------------------------------------------------------------
// | SkillFull快速开发平台 [ SkillFull ]
// +----------------------------------------------------------------------
// | 版权所有 2020~2021 zxiaozhou
// +----------------------------------------------------------------------
// | 官方网站: https://www.divisu.com
// +----------------------------------------------------------------------
// | 作者: zxiaozhou <z7630853@163.com>
// +----------------------------------------------------------------------
package com.anyilanxin.skillfull.corecommon.auth;

import com.anyilanxin.skillfull.corecommon.auth.model.RoleInfo;
import com.anyilanxin.skillfull.corecommon.auth.model.UserIdentity;
import com.anyilanxin.skillfull.corecommon.auth.model.UserInfo;

import java.util.HashSet;
import java.util.Set;

/**
 * 获取登录用户信息接口
 *
 * @author zxiaozhou
 * @date 2022-04-09 09:33
 * @since JDK1.8
 */
public class GetLoginUserInfoDefault implements IGetLoginUserInfo {
    @Override
    public UserInfo getUserInfo(String token) {
        return new UserInfo();
    }

    @Override
    public UserInfo getUserInfo() {
        return new UserInfo();
    }

    @Override
    public boolean superRole() {
        return false;
    }

    @Override
    public Set<RoleInfo> getRoleInfos() {
        return new HashSet<>();
    }

    @Override
    public Set<String> getRoleCodes() {
        return new HashSet<>();
    }

    @Override
    public Set<String> getRoleIds() {
        return new HashSet<>();
    }

    @Override
    public String getUserId() {
        return "";
    }

    @Override
    public String getUserName() {
        return "";
    }

    @Override
    public String getNickName() {
        return "";
    }

    @Override
    public String getRealName() {
        return "";
    }

    @Override
    public String getCurrentOrgId() {
        return "";
    }

    @Override
    public String getCurrentOrgCode() {
        return "";
    }

    @Override
    public String getCurrentAreaCode() {
        return "";
    }

    @Override
    public String getCurrentTenantId() {
        return "";
    }

    @Override
    public String getPhone() {
        return "";
    }

    @Override
    public int getIdentityStatus() {
        return 0;
    }

    @Override
    public UserIdentity getIdentity() {
        return null;
    }
}
