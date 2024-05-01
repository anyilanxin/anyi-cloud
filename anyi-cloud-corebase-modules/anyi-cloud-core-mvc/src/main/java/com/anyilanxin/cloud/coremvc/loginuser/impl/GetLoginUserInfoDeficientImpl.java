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

package com.anyilanxin.cloud.coremvc.loginuser.impl;

import com.anyilanxin.cloud.corecommon.model.auth.AnYiUserInfo;
import com.anyilanxin.cloud.corecommon.model.auth.RoleInfo;
import com.anyilanxin.cloud.corecommon.model.auth.UserIdentity;
import com.anyilanxin.cloud.coremvc.loginuser.IGetLoginUserInfo;

import java.util.Collections;
import java.util.Set;

/**
 * 获取用户信息缺损实现
 *
 * @author zxh
 * @date 2023-06-28 10:43
 * @since 1.0.0
 */
public class GetLoginUserInfoDeficientImpl implements IGetLoginUserInfo {
    @Override
    public AnYiUserInfo getUserInfo(String token) {
        AnYiUserInfo userInfo = new AnYiUserInfo();
        userInfo.setUserId("1547044236679495680");
        userInfo.setCurrentOrgId("anyilanxin");
        userInfo.setCurrentAreaCode("anyilanxin");
        userInfo.setCurrentTenantId("anyilanxin");
        userInfo.setCurrentOrgCode("anyilanxin");
        userInfo.setSuperAdmin(false);
        userInfo.setRoleInfos(Collections.emptySet());
        userInfo.setRoleCodes(Collections.emptySet());
        userInfo.setRoleIds(Collections.emptySet());
        userInfo.setUserName("anyilanxin");
        userInfo.setNickName("anyilanxin");
        userInfo.setRealName("anyilanxin");
        userInfo.setPhone("18275226138");
        return userInfo;
    }


    @Override
    public AnYiUserInfo getUserInfo() {
        return getUserInfo("");
    }


    @Override
    public int getIdentityStatus() {
        return 0;
    }


    @Override
    public UserIdentity getIdentity() {
        return getUserInfo().getIdentity();
    }


    @Override
    public boolean superRole() {
        return getUserInfo().isSuperAdmin();
    }


    @Override
    public Set<RoleInfo> getRoleInfos() {
        return getUserInfo().getRoleInfos();
    }


    @Override
    public Set<String> getRoleCodes() {
        return getUserInfo().getRoleCodes();
    }


    @Override
    public Set<String> getRoleIds() {
        return getUserInfo().getRoleIds();
    }


    @Override
    public String getUserId() {
        return getUserInfo().getUserId();
    }


    @Override
    public String getUserName() {
        return getUserInfo().getUserName();
    }


    @Override
    public String getNickName() {
        return getUserInfo().getNickName();
    }


    @Override
    public String getRealName() {
        return getUserInfo().getRealName();
    }


    @Override
    public String getCurrentOrgId() {
        return getUserInfo().getCurrentOrgId();
    }


    @Override
    public String getCurrentOrgCode() {
        return getUserInfo().getCurrentOrgCode();
    }


    @Override
    public String getCurrentAreaCode() {
        return getUserInfo().getCurrentAreaCode();
    }


    @Override
    public String getCurrentTenantId() {
        return getUserInfo().getCurrentTenantId();
    }


    @Override
    public String getPhone() {
        return getUserInfo().getPhone();
    }
}
