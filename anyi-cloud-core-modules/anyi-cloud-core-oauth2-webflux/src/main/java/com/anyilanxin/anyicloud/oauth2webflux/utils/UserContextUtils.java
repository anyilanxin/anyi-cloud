

package com.anyilanxin.anyicloud.oauth2webflux.utils;

import com.anyilanxin.anyicloud.corecommon.model.auth.RoleInfo;
import com.anyilanxin.anyicloud.corecommon.model.auth.UserInfo;
import com.anyilanxin.anyicloud.oauth2webflux.user.IGetLoginUserInfo;

import java.util.Set;
import javax.annotation.PostConstruct;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

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
    public static Mono<UserInfo> getUserInfo(String token) {
        return utils.loginUserInfo.getUserInfo(token);
    }


    /**
     * 获取登录用户信息
     */
    public static Mono<UserInfo> getUserInfo() {
        return utils.loginUserInfo.getUserInfo();
    }


    /**
     * 判断当前登录人是否为超管
     */
    public static Mono<Boolean> superRole() {
        return utils.loginUserInfo.superRole();
    }


    /**
     * 获取当前登录人角色信息
     */
    public static Mono<Set<RoleInfo>> getRoleInfos() {
        return utils.loginUserInfo.getRoleInfos();
    }


    /**
     * 获取当前登录人角色编码
     */
    public static Mono<Set<String>> getRoleCodes() {
        return utils.loginUserInfo.getRoleCodes();
    }


    /**
     * 获取当前登录人角色id
     */
    public static Mono<Set<String>> getRoleIds() {
        return utils.loginUserInfo.getRoleIds();
    }


    /**
     * 获取当前登录人用户id
     */
    public static Mono<String> getUserId() {
        return utils.loginUserInfo.getUserId();
    }


    /**
     * 获取当前登录人用户名
     */
    public static Mono<String> getUserName() {
        Mono<String> userName = utils.loginUserInfo.getUserName();
        return userName.switchIfEmpty(Mono.just(""));
    }


    /**
     * 获取当前登录人昵称
     */
    public static Mono<String> getNickName() {
        Mono<String> nickName = utils.loginUserInfo.getNickName();
        return nickName.switchIfEmpty(Mono.just(""));
    }


    /**
     * 获取当前登录人真实姓名
     */
    public static Mono<String> getRealName() {
        Mono<String> realName = utils.loginUserInfo.getRealName();
        return realName.switchIfEmpty(Mono.just(""));
    }


    /**
     * 获取当前登录机构
     */
    public static Mono<String> getCurrentOrgId() {
        Mono<String> currentOrgId = utils.loginUserInfo.getCurrentOrgId();
        return currentOrgId.switchIfEmpty(Mono.just(""));
    }


    /**
     * 获取当前登录机构编码
     */
    public static Mono<String> getCurrentOrgCode() {
        Mono<String> currentOrgCode = utils.loginUserInfo.getCurrentOrgCode();
        return currentOrgCode.switchIfEmpty(Mono.just(""));
    }


    /**
     * 获取当前登录区域编码
     */
    public static Mono<String> getCurrentAreaCode() {
        Mono<String> currentAreaCode = utils.loginUserInfo.getCurrentAreaCode();
        return currentAreaCode.switchIfEmpty(Mono.just(""));
    }


    /**
     * 获取当前登录租户id
     */
    public static Mono<String> getCurrentTenantId() {
        Mono<String> currentTenantId = utils.loginUserInfo.getCurrentTenantId();
        return currentTenantId.switchIfEmpty(Mono.just(""));
    }


    /**
     * 获取当前登录人电话号码
     */
    public static Mono<String> getPhone() {
        Mono<String> phone = utils.loginUserInfo.getPhone();
        return phone.switchIfEmpty(Mono.just(""));
    }
}
