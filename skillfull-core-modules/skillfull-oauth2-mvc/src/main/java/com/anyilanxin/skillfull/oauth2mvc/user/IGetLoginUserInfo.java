package com.anyilanxin.skillfull.oauth2mvc.user;

import com.anyilanxin.skillfull.corecommon.model.auth.RoleInfo;
import com.anyilanxin.skillfull.corecommon.model.auth.UserIdentity;
import com.anyilanxin.skillfull.corecommon.model.auth.UserInfo;

import java.util.Set;

/**
 * 获取登录用户信息接口
 *
 * @author zxiaozhou
 * @date 2022-04-09 09:33
 * @since JDK1.8
 */
public interface IGetLoginUserInfo {
    /**
     * 根据token获取登录用户信息
     */
    UserInfo getUserInfo(String token);

    /**
     * 获取登录用户信息
     */
    UserInfo getUserInfo();


    /**
     * 实名状态:0-待提交,1-审核中,2-未通过(审核失败),3-通过(审核成功),默认0
     */
    int getIdentityStatus();


    /**
     * 实名认证信息
     */
    UserIdentity getIdentity();

    /**
     * 判断是否为超级管理员
     */
    boolean superRole();

    /**
     * 获取角色信息
     */
    Set<RoleInfo> getRoleInfos();

    /**
     * 获取角色编码
     */
    Set<String> getRoleCodes();


    /**
     * 获取角色Id
     */
    Set<String> getRoleIds();

    /**
     * 获取用户id
     */
    String getUserId();


    /**
     * 获取用户名
     */
    String getUserName();

    /**
     * 获取昵称
     */
    String getNickName();

    /**
     * 获取用户真实姓名
     */
    String getRealName();

    /**
     * 获取当前机构id
     */
    String getCurrentOrgId();

    /**
     * 获取当前机构编码
     */
    String getCurrentOrgCode();

    /**
     * 获取当前区域编码
     */
    String getCurrentAreaCode();

    /**
     * 获取当前租户id
     */
    String getCurrentTenantId();

    /**
     * 获取当前用户手机号
     */
    String getPhone();
}
