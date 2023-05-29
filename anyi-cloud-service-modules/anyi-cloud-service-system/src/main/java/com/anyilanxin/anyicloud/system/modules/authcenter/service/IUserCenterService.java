

package com.anyilanxin.anyicloud.system.modules.authcenter.service;

import com.anyilanxin.anyicloud.corecommon.model.auth.UserOrgTreeInfo;
import com.anyilanxin.anyicloud.corecommon.model.auth.UserRouteModel;
import com.anyilanxin.anyicloud.corecommon.model.auth.UserRouteTreeModel;
import com.anyilanxin.anyicloud.system.modules.authcenter.controller.vo.FindPasswordVo;
import com.anyilanxin.anyicloud.system.modules.authcenter.controller.vo.UpdateInfoVo;
import com.anyilanxin.anyicloud.system.modules.authcenter.controller.vo.UpdatePasswordVo;
import com.anyilanxin.anyicloud.system.modules.authcenter.controller.vo.UpdatePhoneVo;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

/**
 * 用户中心
 *
 * @author zxh
 * @date 2022-05-02 09:17
 * @since 1.0.0
 */
public interface IUserCenterService {

    /**
     * 获取当前用户菜单信息
     *
     * @param systemCodes 系统编码
     * @return List<LoginRouteModel>
     * @author zxh
     * @date 2022-05-02 09:44
     */
    List<UserRouteModel> getRouterInfo(String systemCodes);


    /**
     * 获取用户路由菜单信息(树形)
     *
     * @param systemCodes 系统编码
     * @return List<LoginRouteTreeModel>
     * @author zxh
     * @date 2022-05-02 09:44
     */
    List<UserRouteTreeModel> getRouterInfoTree(String systemCodes);


    /**
     * 修改用户资料
     *
     * @param vo 待修改信息
     * @author zxh
     * @date 2022-05-02 09:44
     */
    void updateUserInfo(UpdateInfoVo vo);


    /**
     * 修改用户头像
     *
     * @param file 头像文件
     * @author zxh
     * @date 2022-05-02 09:44
     */
    void updateUserAvatar(MultipartFile file);


    /**
     * 修改用户手机号
     *
     * @param vo 修改手机号信息
     * @author zxh
     * @date 2022-05-02 09:45
     */
    void updateUserPhone(UpdatePhoneVo vo);


    /**
     * 修改密码
     *
     * @param vo 修改密码信息
     * @author zxh
     * @date 2022-05-02 09:45
     */
    void updatePassword(UpdatePasswordVo vo);


    /**
     * 找回密码
     *
     * @param vo 找回密码信息
     * @author zxh
     * @date 2022-05-02 09:45
     */
    void findPassword(FindPasswordVo vo);


    /**
     * 修改手机号或者找回密码发送短信验证码
     *
     * @param phone 手机号
     * @author zxh
     * @date 2022-05-02 09:47
     */
    void sendSmsCode(String phone);


    /**
     * 获取用户机构列表
     *
     * @return List<UserOrgInfo>
     * @author zxh
     * @date 2022-07-16 11:12
     */
    List<UserOrgTreeInfo> getUserOrgInfo();
}
