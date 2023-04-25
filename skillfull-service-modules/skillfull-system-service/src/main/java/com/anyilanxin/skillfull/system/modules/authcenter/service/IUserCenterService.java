package com.anyilanxin.skillfull.system.modules.authcenter.service;

import com.anyilanxin.skillfull.corecommon.model.auth.UserOrgTreeInfo;
import com.anyilanxin.skillfull.corecommon.model.auth.UserRouteModel;
import com.anyilanxin.skillfull.corecommon.model.auth.UserRouteTreeModel;
import com.anyilanxin.skillfull.system.modules.authcenter.controller.vo.FindPasswordVo;
import com.anyilanxin.skillfull.system.modules.authcenter.controller.vo.UpdateInfoVo;
import com.anyilanxin.skillfull.system.modules.authcenter.controller.vo.UpdatePasswordVo;
import com.anyilanxin.skillfull.system.modules.authcenter.controller.vo.UpdatePhoneVo;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * 用户中心
 *
 * @author zxiaozhou
 * @date 2022-05-02 09:17
 * @since JDK1.8
 */
public interface IUserCenterService {

    /**
     * 获取当前用户菜单信息
     *
     * @param systemCodes 系统编码
     * @return List<LoginRouteModel>
     * @author zxiaozhou
     * @date 2022-05-02 09:44
     */
    List<UserRouteModel> getRouterInfo(String systemCodes);


    /**
     * 获取用户路由菜单信息(树形)
     *
     * @param systemCodes 系统编码
     * @return List<LoginRouteTreeModel>
     * @author zxiaozhou
     * @date 2022-05-02 09:44
     */
    List<UserRouteTreeModel> getRouterInfoTree(String systemCodes);


    /**
     * 修改用户资料
     *
     * @param vo 待修改信息
     * @author zxiaozhou
     * @date 2022-05-02 09:44
     */
    void updateUserInfo(UpdateInfoVo vo);


    /**
     * 修改用户头像
     *
     * @param file 头像文件
     * @author zxiaozhou
     * @date 2022-05-02 09:44
     */
    void updateUserAvatar(MultipartFile file);


    /**
     * 修改用户手机号
     *
     * @param vo 修改手机号信息
     * @author zxiaozhou
     * @date 2022-05-02 09:45
     */
    void updateUserPhone(UpdatePhoneVo vo);


    /**
     * 修改密码
     *
     * @param vo 修改密码信息
     * @author zxiaozhou
     * @date 2022-05-02 09:45
     */
    void updatePassword(UpdatePasswordVo vo);


    /**
     * 找回密码
     *
     * @param vo 找回密码信息
     * @author zxiaozhou
     * @date 2022-05-02 09:45
     */
    void findPassword(FindPasswordVo vo);


    /**
     * 修改手机号或者找回密码发送短信验证码
     *
     * @param phone 手机号
     * @author zxiaozhou
     * @date 2022-05-02 09:47
     */
    void sendSmsCode(String phone);


    /**
     * 获取用户机构列表
     *
     * @return List<UserOrgInfo>
     * @author zxiaozhou
     * @date 2022-07-16 11:12
     */
    List<UserOrgTreeInfo> getUserOrgInfo();

}
