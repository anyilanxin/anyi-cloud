

package com.anyilanxin.anyicloud.auth.modules.login.service;

import com.anyilanxin.anyicloud.auth.modules.login.service.dto.RbacUserDto;
import com.anyilanxin.anyicloud.corecommon.model.system.UserAndResourceAuthModel;

/**
 * 用户中心
 *
 * @author zxh
 * @date 2022-05-02 09:17
 * @since 1.0.0
 */
public interface IUserAuthService {

    /**
     * 通过openId获取用户信息
     *
     * @param openId
     * @return UserAndResourceAuthModel
     * @author zxh
     * @date 2022-07-12 13:39
     */
    UserAndResourceAuthModel getUserByOpenId(String openId);


    /**
     * 通过用户名获取用户信息
     *
     * @param userName
     * @return UserAndResourceAuthModel
     * @author zxh
     * @date 2022-07-12 13:40
     */
    UserAndResourceAuthModel getUserByAccountPhone(String userName);


    /**
     * 通过电话号码获取用户信息
     *
     * @param phone
     * @return UserAndResourceAuthModel
     * @author zxh
     * @date 2022-07-12 13:40
     */
    UserAndResourceAuthModel getUserByPhone(String phone);


    /**
     * 组装用户授权信息
     *
     * @param entity
     * @param orgId
     * @param havePassword
     * @return UserAndResourceAuthModel
     * @author zxh
     * @date 2022-07-23 20:07
     */
    UserAndResourceAuthModel getUserInfo(RbacUserDto entity, String orgId, boolean havePassword);
}
