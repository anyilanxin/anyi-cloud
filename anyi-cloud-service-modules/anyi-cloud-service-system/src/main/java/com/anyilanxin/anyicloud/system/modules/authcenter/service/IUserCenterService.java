/*
 * Copyright (c) 2023-present ZHOUXUANHONG(安一老厨)<anyilanxin@aliyun.com>
 *
 * AnYi Cloud Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * AnYi Cloud 采用APACHE LICENSE 2.0开源协议，您在使用过程中，需要注意以下几点：
 *   1.请不要删除和修改根目录下的LICENSE.txt文件；
 *   2.请不要删除和修改 AnYi Cloud 源码头部的版权声明；
 *   3.请保留源码和相关描述文件的项目出处，作者声明等；
 *   4.分发源码时候，请注明软件出处 https://github.com/anyilanxin/anyi-cloud；
 *   5.在修改包名，模块名称，项目代码等时，请注明软件出处 https://github.com/anyilanxin/anyi-cloud；
 *   6.本软件不允许在国家法律规定范围外使用，如出现违法行为原作者本人不承担任何法律风险；
 *   7.进行商用时，不得基于AnYi Cloud的基础，修改包装而成一个与AnYi Cloud EE、AnYi Zeebe EE、AnYi Standalone EE功能类似的程序，进行销售或发布，参与同类软件产品市场的竞争；
 *   8.本软件使用的第三方依赖皆为开源软件，如需要修改第三方源码请遵循第三方源码附带开源协议；
 *   9.本软件流程部分请遵循camunda开源协议：
 *     https://docs.camunda.org/manual/latest/introduction/third-party-libraries
 *     https://github.com/camunda/camunda-bpm-platform/blob/master/LICENSE
 *   10.若您的项目无法满足以上几点，可申请商业授权。
 */
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
