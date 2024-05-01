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

package com.anyilanxin.cloud.auth.utils;

import com.anyilanxin.cloud.corecommon.model.system.AnYiUserAndResourceAuthModel;
import com.anyilanxin.cloud.oauth2common.authinfo.AnYiUserDetails;
import com.anyilanxin.cloud.oauth2common.mapstruct.ClientDetailsCopyMap;
import com.anyilanxin.cloud.oauth2common.mapstruct.UserDetailsCopyMap;
import com.anyilanxin.cloud.oauth2common.store.AnYiAuthentication;
import com.anyilanxin.cloud.oauth2common.store.IAuthStore;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import static com.anyilanxin.cloud.oauth2common.utils.Oauth2CommonUtils.toUserDetails;

/**
 * @author zxh
 * @date 2022-03-03 14:19
 * @since 1.0.0
 */
@Component
@RequiredArgsConstructor
public class AuthServiceOauth2CommonUtils {
    private final UserDetailsCopyMap userDetailsCopyMap;
    private final ClientDetailsCopyMap clientDetailsCopyMap;
    private static AuthServiceOauth2CommonUtils utils;
    private final IAuthStore store;


    @PostConstruct
    private void init() {
        utils = this;
    }


    /**
     * 刷新当前用户信息
     *
     * @param model ${@link AnYiUserAndResourceAuthModel}
     * @author zxh
     * @date 2022-03-11 01:15
     */
    public static void refreshUserOauth(AnYiUserAndResourceAuthModel model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        refreshUserOauth(authentication, model);
    }


    /**
     * 刷新用户信息
     *
     * @param authentication ${@link Authentication}权限信息
     * @param model          ${@link AnYiUserAndResourceAuthModel} 新的用户信息
     * @author zxh
     * @date 2022-04-05 23:23
     */
    private static void refreshUserOauth(Authentication authentication, AnYiUserAndResourceAuthModel model) {
        // 在获取用户信息
        if (authentication instanceof AnYiAuthentication auth2Authentication) {
            Object principal = auth2Authentication.getPrincipal();
            if (principal instanceof AnYiUserDetails) {
                AnYiUserDetails customUserDetails = toUserDetails(model);
                auth2Authentication.setPrincipal(customUserDetails);
                utils.store.updateAuthentication(auth2Authentication.getTokenValue(), auth2Authentication);
                SecurityContextHolder.getContext().setAuthentication(auth2Authentication);
            }
        }
    }

}
