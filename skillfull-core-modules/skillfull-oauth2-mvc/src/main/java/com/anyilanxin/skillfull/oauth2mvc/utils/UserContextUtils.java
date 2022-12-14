// +----------------------------------------------------------------------
// | SkillFull快速开发平台 [ SkillFull ]
// +----------------------------------------------------------------------
// | 版权所有 2020~2021 zxiaozhou
// +----------------------------------------------------------------------
// | 官方网站: https://skillfull.divisu.com
// +----------------------------------------------------------------------
// | 作者: zxiaozhou <z7630853@163.com>
// +----------------------------------------------------------------------
package com.anyilanxin.skillfull.oauth2mvc.utils;

import com.anyilanxin.skillfull.corecommon.model.auth.RoleInfo;
import com.anyilanxin.skillfull.corecommon.model.auth.UserInfo;
import com.anyilanxin.skillfull.oauth2mvc.user.IGetLoginUserInfo;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Set;

/**
 * 上下文持有者
 *
 * @author zxiaozhou
 * @date 2020-10-07 09:07
 * @since JDK11
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
