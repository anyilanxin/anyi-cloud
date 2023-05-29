

package com.anyilanxin.anyicloud.oauth2mvc.utils;

import com.anyilanxin.anyicloud.corecommon.model.auth.RoleInfo;
import com.anyilanxin.anyicloud.corecommon.model.auth.UserInfo;
import com.anyilanxin.anyicloud.oauth2mvc.user.IGetLoginUserInfo;

import java.util.Set;
import javax.annotation.PostConstruct;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

/**
 * 上下文持有者
 *
 * @author zxh
 * @date 2020-10-07 09:07
 * @since 1.0.0
 */
@Component
@RequiredArgsConstructor
public class UserContextUtils {
    private static UserContextUtils utils;
    private final IGetLoginUserInfo loginUserInfo;

    @PostConstruct
    private void init() {
        utils = this;
    }


    /**
     * 根据token获取登录用户信息
     */
    public static UserInfo getUserInfo(String token) {
        return utils.loginUserInfo.getUserInfo(token);
    }


    /**
     * 获取登录用户信息
     */
    public static UserInfo getUserInfo() {
        return utils.loginUserInfo.getUserInfo();
    }


    /**
     * 判断当前登录人是否为超管
     */
    public static boolean superRole() {
        return utils.loginUserInfo.superRole();
    }


    /**
     * 获取当前登录人角色信息
     */
    public static Set<RoleInfo> getRoleInfos() {
        return utils.loginUserInfo.getRoleInfos();
    }


    /**
     * 获取当前登录人角色编码
     */
    public static Set<String> getRoleCodes() {
        return utils.loginUserInfo.getRoleCodes();
    }


    /**
     * 获取当前登录人角色id
     */
    public static Set<String> getRoleIds() {
        return utils.loginUserInfo.getRoleIds();
    }


    /**
     * 获取当前登录人用户id
     */
    public static String getUserId() {
        return utils.loginUserInfo.getUserId();
    }


    /**
     * 获取当前登录人用户名
     */
    public static String getUserName() {
        String userName = utils.loginUserInfo.getUserName();
        return StringUtils.isNotBlank(userName) ? userName : "";
    }


    /**
     * 获取当前登录人昵称
     */
    public static String getNickName() {
        String nickName = utils.loginUserInfo.getNickName();
        return StringUtils.isNotBlank(nickName) ? nickName : "";
    }


    /**
     * 获取当前登录人真实姓名
     */
    public static String getRealName() {
        String realName = utils.loginUserInfo.getRealName();
        return StringUtils.isNotBlank(realName) ? realName : "";
    }


    /**
     * 获取当前登录机构
     */
    public static String getCurrentOrgId() {
        String currentOrgId = utils.loginUserInfo.getCurrentOrgId();
        return StringUtils.isNotBlank(currentOrgId) ? currentOrgId : "";
    }


    /**
     * 获取当前登录机构编码
     */
    public static String getCurrentOrgCode() {
        String currentOrgCode = utils.loginUserInfo.getCurrentOrgCode();
        return StringUtils.isNotBlank(currentOrgCode) ? currentOrgCode : "";
    }


    /**
     * 获取当前登录区域编码
     */
    public static String getCurrentAreaCode() {
        String currentAreaCode = utils.loginUserInfo.getCurrentAreaCode();
        return StringUtils.isNotBlank(currentAreaCode) ? currentAreaCode : "";
    }


    /**
     * 获取当前登录租户id
     */
    public static String getCurrentTenantId() {
        String currentTenantId = utils.loginUserInfo.getCurrentTenantId();
        return StringUtils.isNotBlank(currentTenantId) ? currentTenantId : "";
    }


    /**
     * 获取当前登录人电话号码
     */
    public static String getPhone() {
        String phone = utils.loginUserInfo.getPhone();
        return StringUtils.isNotBlank(phone) ? phone : "";
    }
}
