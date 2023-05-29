

package com.anyilanxin.anyicloud.auth.modules.login.service;

import com.anyilanxin.anyicloud.corecommon.model.auth.UserInfo;

/**
 * 授权相关
 *
 * @author zxh
 * @date 2022-02-19 09:26
 * @since 1.0.0
 */
public interface IAuthService {

    /**
     * 取消授权
     *
     * @author zxh
     * @date 2022-02-22 22:49
     */
    void logOut();


    /**
     * 获取当前用户信息
     *
     * @param orgId 机构id
     * @return CustomUserDetails
     * @author zxh
     * @date 2022-05-02 09:44
     */
    UserInfo getUserInfo(String orgId);
}
