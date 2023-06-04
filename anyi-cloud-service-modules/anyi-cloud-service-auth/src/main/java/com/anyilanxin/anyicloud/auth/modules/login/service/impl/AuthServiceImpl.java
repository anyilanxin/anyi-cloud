/*
 * Copyright (c) 2021-present ZHOUXUANHONG(安一老厨)<anyilanxin@aliyun.com>
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
package com.anyilanxin.anyicloud.auth.modules.login.service.impl;

import com.anyilanxin.anyicloud.auth.modules.login.mapper.UserAuthMapper;
import com.anyilanxin.anyicloud.auth.modules.login.service.IAuthService;
import com.anyilanxin.anyicloud.auth.modules.login.service.IUserAuthService;
import com.anyilanxin.anyicloud.auth.modules.login.service.dto.RbacUserDto;
import com.anyilanxin.anyicloud.corecommon.model.auth.UserInfo;
import com.anyilanxin.anyicloud.corecommon.model.system.UserAndResourceAuthModel;
import com.anyilanxin.anyicloud.oauth2common.utils.Oauth2CommonUtils;
import com.anyilanxin.anyicloud.oauth2mvc.utils.UserContextUtils;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.stereotype.Service;

/**
 * 授权相关
 *
 * @author zxh
 * @date 2022-02-19 10:12
 * @since 1.0.0
 */
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements IAuthService {
    private final TokenStore tokenStore;
    private final UserAuthMapper userAuthMapper;
    private final IUserAuthService authService;

    @Override
    public void logOut() {
        SecurityContext context = SecurityContextHolder.getContext();
        if (Objects.nonNull(context.getAuthentication())) {
            Authentication principal = context.getAuthentication();
            if (principal instanceof OAuth2Authentication) {
                OAuth2Authentication authentication = (OAuth2Authentication) principal;
                OAuth2AccessToken accessToken = tokenStore.getAccessToken(authentication);
                if (Objects.nonNull(accessToken)) {
                    tokenStore.removeAccessToken(accessToken);
                }
            }
        }
    }


    @Override
    public UserInfo getUserInfo(String orgId) {
        RbacUserDto userDto = userAuthMapper.selectUserInfoByUserId(UserContextUtils.getUserId());
        String currentOrgId = orgId;
        if (StringUtils.isBlank(currentOrgId)) {
            currentOrgId = UserContextUtils.getCurrentOrgId();
        }
        if (StringUtils.isNotBlank(currentOrgId) && !currentOrgId.equals(UserContextUtils.getCurrentOrgId())) {
            // 更新用户当前登录机构信息
            userAuthMapper.updateLoginOrgId(userDto.getUserId(), orgId);
        }
        UserAndResourceAuthModel userInfo = authService.getUserInfo(userDto, currentOrgId, false);
        Oauth2CommonUtils.refreshUserOauth(userInfo);
        return userInfo;
    }
}
