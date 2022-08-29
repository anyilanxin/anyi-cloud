// +----------------------------------------------------------------------
// | SkillFull快速开发平台 [ SkillFull ]
// +----------------------------------------------------------------------
// | 版权所有 2020~2021 zxiaozhou
// +----------------------------------------------------------------------
// | 官方网站: https://skillfull.divisu.com
// +----------------------------------------------------------------------
// | 作者: zxiaozhou <z7630853@163.com>
// +----------------------------------------------------------------------
package com.anyilanxin.skillfull.auth.modules.login.service;

import com.anyilanxin.skillfull.auth.modules.login.service.dto.RbacUserDto;
import com.anyilanxin.skillfull.corecommon.model.system.UserAndResourceAuthModel;

/**
 * 用户中心
 *
 * @author zxiaozhou
 * @date 2022-05-02 09:17
 * @since JDK1.8
 */
public interface IUserAuthService {

    /**
     * 通过openId获取用户信息
     *
     * @param openId
     * @return UserAndResourceAuthModel
     * @author zxiaozhou
     * @date 2022-07-12 13:39
     */
    UserAndResourceAuthModel getUserByOpenId(String openId);


    /**
     * 通过用户名获取用户信息
     *
     * @param userName
     * @return UserAndResourceAuthModel
     * @author zxiaozhou
     * @date 2022-07-12 13:40
     */
    UserAndResourceAuthModel getUserByAccountPhone(String userName);


    /**
     * 通过电话号码获取用户信息
     *
     * @param phone
     * @return UserAndResourceAuthModel
     * @author zxiaozhou
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
     * @author zxiaozhou
     * @date 2022-07-23 20:07
     */
    UserAndResourceAuthModel getUserInfo(RbacUserDto entity, String orgId, boolean havePassword);
}
