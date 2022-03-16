// +----------------------------------------------------------------------
// | SkillFull快速开发平台 [ SkillFull ]
// +----------------------------------------------------------------------
// | 版权所有 2020~2021 zxiaozhou
// +----------------------------------------------------------------------
// | 官方网站: https://www.divisu.com
// +----------------------------------------------------------------------
// | 作者: zxiaozhou <z7630853@163.com>
// +----------------------------------------------------------------------
package indi.zxiaozhou.skillfull.auth.security.login.service;


import indi.zxiaozhou.skillfull.auth.modules.rbac.entity.RbacUserEntity;
import indi.zxiaozhou.skillfull.auth.security.login.controller.vo.*;
import indi.zxiaozhou.skillfull.auth.security.login.service.dto.UserInfoDto;
import indi.zxiaozhou.skillfull.auth.security.login.service.model.LoginPermissionInfo;
import indi.zxiaozhou.skillfull.corecommon.base.model.auth.LoginRouteModel;
import indi.zxiaozhou.skillfull.corecommon.base.model.auth.LoginRouteTreeModel;
import indi.zxiaozhou.skillfull.corecommon.base.model.login.LoginUserInfoModel;
import indi.zxiaozhou.skillfull.coredatabase.base.service.BaseService;

import java.util.List;

/**
 * 用户信息相关
 *
 * @author zxiaozhou
 * @date 2020-07-17 20:21
 * @since JDK11
 */
public interface IWebLoginUserCenterService extends BaseService<RbacUserEntity> {
    /**
     * 修改密码
     *
     * @param vo ${@link ChangePasswordVo} 密码信息
     * @throws RuntimeException ${@link RuntimeException}
     * @author zxiaozhou
     * @date 2021-01-10 16:39
     */
    void changePassword(ChangePasswordVo vo) throws RuntimeException;


    /**
     * 修改用户信息
     *
     * @param vo ${@link ChangeUserInfoVo} 待修改数据
     * @throws RuntimeException ${@link RuntimeException}
     * @author zxiaozhou
     * @date 2021-01-13 10:04
     */
    void changeUserInfo(ChangeUserInfoVo vo) throws RuntimeException;


    /**
     * 找回密码
     *
     * @param vo ${@link FindPasswordVo} 待验证数据
     * @throws RuntimeException ${@link RuntimeException}
     * @author zxiaozhou
     * @date 2021-01-13 10:08
     */
    void findPassword(FindPasswordVo vo) throws RuntimeException;


    /**
     * 修改手机号前验证旧手机验证码
     *
     * @param vo ${@link CheckOldUserPhoneVo} 待验证信息
     * @throws RuntimeException ${@link RuntimeException}
     * @author zxiaozhou
     * @date 2021-01-13 10:45
     */
    void checkOldUserPhoneSms(CheckOldUserPhoneVo vo) throws RuntimeException;


    /**
     * 修改手机号
     *
     * @param vo ${@link ChangeUserPhoneVo} 待修改手机号信息
     * @throws RuntimeException ${@link RuntimeException}
     * @author zxiaozhou
     * @date 2021-01-13 10:32
     */
    void changeUserPhone(ChangeUserPhoneVo vo) throws RuntimeException;


    /**
     * 绑定手机号码
     *
     * @param vo ${@link BindUserPhoneVo} 待绑定手机号信息
     * @throws RuntimeException ${@link RuntimeException}
     * @author zxiaozhou
     * @date 2021-01-13 10:31
     */
    void bindUserPhone(BindUserPhoneVo vo) throws RuntimeException;


    /**
     * 获取菜单路由信息(树形)
     *
     * @return List<RouterInfoDto> ${@link List <RouterInfoDto>}
     * @throws RuntimeException ${@link RuntimeException}
     * @author zxiaozhou
     * @date 2020-09-27 01:29
     */
    List<LoginRouteTreeModel> getRouterTreeInfo(String userId, String systemCodes, String orgId) throws RuntimeException;


    /**
     * 获取菜单路由信息
     *
     * @return List<RouterInfoDto> ${@link List <RouterInfoDto>}
     * @throws RuntimeException ${@link RuntimeException}
     * @author zxiaozhou
     * @date 2020-09-27 01:29
     */
    List<LoginRouteModel> getRouterInfo(String userId, String systemCodes, String orgId) throws RuntimeException;


    /**
     * 获取用户权限信息
     *
     * @return UserInfoDto ${@link UserInfoDto}
     * @throws RuntimeException ${@link RuntimeException}
     * @author zxiaozhou
     * @date 2020-09-27 01:30
     */
    UserInfoDto getUserInfo() throws RuntimeException;


    /**
     * 获取登录用户信息
     *
     * @param rbacUserEntity ${@link RbacUserEntity} 用户信息
     * @return LoginPermissionInfo ${@link LoginPermissionInfo}
     * @throws RuntimeException ${@link RuntimeException}
     * @author zxiaozhou
     * @date 2020-07-01 03:27
     */
    LoginUserInfoModel getLoinUserInfo(RbacUserEntity rbacUserEntity) throws RuntimeException;
}
