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

package com.anyilanxin.anyicloud.auth.oauth2.authentication.tokenendpoint.password;


import com.anyilanxin.anyicloud.auth.oauth2.authentication.IAnYiAbstractUserDetailsAuthenticationProvider;
import com.anyilanxin.anyicloud.auth.oauth2.validate.CheckModel;
import com.anyilanxin.anyicloud.auth.oauth2.validate.IValidate;
import com.anyilanxin.anyicloud.auth.utils.PasswordCheck;
import com.anyilanxin.anyicloud.oauth2common.authinfo.AnYiUserDetails;
import com.anyilanxin.anyicloud.oauth2common.exception.AnYiBadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.OAuth2Token;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationService;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenGenerator;

public class AnYiOAuth2PasswordAuthenticationProvider extends IAnYiAbstractUserDetailsAuthenticationProvider {
    private final IValidate validate;


    public AnYiOAuth2PasswordAuthenticationProvider(IValidate validate,
                                                    OAuth2AuthorizationService authorizationService,
                                                    UserDetailsService userDetailsService,
                                                    OAuth2TokenGenerator<? extends OAuth2Token> tokenGenerator,
                                                    PasswordEncoder passwordEncoder) {
        super(authorizationService, userDetailsService, tokenGenerator);
        this.validate = validate;
        super.setPasswordEncoder(passwordEncoder);
    }


    @Override
    protected void additionalAuthenticationChecks(UserDetails userDetails, Authentication authentication) throws AuthenticationException {
        AnYiPasswordAuthenticationToken passwordAuthenticationToken = (AnYiPasswordAuthenticationToken) authentication;
        CheckModel model = new CheckModel();
        model.setCodeId(passwordAuthenticationToken.getPictureId());
        model.setCodeValue(passwordAuthenticationToken.getPictureCode());
//        CheckDto checkDto = validate.checkVerification(model);
//        if (!checkDto.isResult()) {
//            throw new AnYiBadCredentialsException(checkDto.getMsg());
//        }
        AnYiUserDetails anYiUserDetails = (AnYiUserDetails) userDetails;
        PasswordCheck passwordCheck = PasswordCheck.getSingleton(getPasswordEncoder());
        if (!passwordCheck.matches(passwordAuthenticationToken.getPassword(), anYiUserDetails.getSalt(), userDetails.getPassword())) {
            throw new AnYiBadCredentialsException("密码错误");
        }
    }


    @Override
    protected String determineUsername(Authentication authentication) {
        AnYiPasswordAuthenticationToken passwordAuthenticationToken = (AnYiPasswordAuthenticationToken) authentication;
        return passwordAuthenticationToken.getUsername();
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return AnYiPasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
