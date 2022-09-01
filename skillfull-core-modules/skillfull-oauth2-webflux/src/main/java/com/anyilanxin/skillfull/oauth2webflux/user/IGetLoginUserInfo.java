// +----------------------------------------------------------------------
// | SkillFull快速开发平台 [ SkillFull ]
// +----------------------------------------------------------------------
// | 版权所有 2020~2021 zxiaozhou
// +----------------------------------------------------------------------
// | 官方网站: https://skillfull.divisu.com
// +----------------------------------------------------------------------
// | 作者: zxiaozhou <z7630853@163.com>
// +----------------------------------------------------------------------
package com.anyilanxin.skillfull.oauth2webflux.user;

import com.anyilanxin.skillfull.corecommon.model.auth.RoleInfo;
import com.anyilanxin.skillfull.corecommon.model.auth.UserIdentity;
import com.anyilanxin.skillfull.corecommon.model.auth.UserInfo;
import reactor.core.publisher.Mono;

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
    Mono<UserInfo> getUserInfo(String token);

    /**
     * 获取登录用户信息
     */
    Mono<UserInfo> getUserInfo();


    /**
     * 实名状态:0-待提交,1-审核中,2-未通过(审核失败),3-通过(审核成功),默认0
     */
    Mono<Integer> getIdentityStatus();


    /**
     * 实名认证信息
     */
    Mono<UserIdentity> getIdentity();

    /**
     * 判断是否为超级管理员
     */
    Mono<Boolean> superRole();

    /**
     * 获取角色信息
     */
    Mono<Set<RoleInfo>> getRoleInfos();

    /**
     * 获取角色编码
     */
    Mono<Set<String>> getRoleCodes();


    /**
     * 获取角色Id
     */
    Mono<Set<String>> getRoleIds();

    /**
     * 获取用户id
     */
    Mono<String> getUserId();


    /**
     * 获取用户名
     */
    Mono<String> getUserName();

    /**
     * 获取昵称
     */
    Mono<String> getNickName();

    /**
     * 获取用户真实姓名
     */
    Mono<String> getRealName();

    /**
     * 获取当前机构id
     */
    Mono<String> getCurrentOrgId();

    /**
     * 获取当前机构编码
     */
    Mono<String> getCurrentOrgCode();

    /**
     * 获取当前区域编码
     */
    Mono<String> getCurrentAreaCode();

    /**
     * 获取当前租户id
     */
    Mono<String> getCurrentTenantId();

    /**
     * 获取当前用户手机号
     */
    Mono<String> getPhone();
}
