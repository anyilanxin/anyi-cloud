package com.anyilanxin.skillfull.auth.modules.login.service;

import com.anyilanxin.skillfull.corecommon.model.auth.UserInfo;

/**
 * 授权相关
 *
 * @author zxiaozhou
 * @date 2022-02-19 09:26
 * @since JDK1.8
 */
public interface IAuthService {

    /**
     * 取消授权
     *
     * @author zxiaozhou
     * @date 2022-02-22 22:49
     */
    void logOut();


    /**
     * 获取当前用户信息
     *
     * @param orgId 机构id
     * @return CustomUserDetails
     * @author zxiaozhou
     * @date 2022-05-02 09:44
     */
    UserInfo getUserInfo(String orgId);

}
