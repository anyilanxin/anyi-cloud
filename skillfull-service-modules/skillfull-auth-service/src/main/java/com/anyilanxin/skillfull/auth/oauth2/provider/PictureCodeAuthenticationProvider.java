/*
 * Copyright (c) 2021-2022 ZHOUXUANHONG(安一老厨)<anyilanxin@aliyun.com>
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
 *   1.请不要删除和修改根目录下的LICENSE文件；
 *   2.请不要删除和修改 AnYi Cloud 源码头部的版权声明；
 *   3.请保留源码和相关描述文件的项目出处，作者声明等；
 *   4.分发源码时候，请注明软件出处 https://github.com/anyilanxin/anyi-cloud；
 *   5.在修改包名，模块名称，项目代码等时，请注明软件出处 https://github.com/anyilanxin/anyi-cloud；
 *   6.本软件不允许在国家法律规定范围外使用，如出现违法行为原作者本人不承担任何法律风险；
 *   7.本软件使用的第三方依赖皆为开源软件，如需要修改第三方源码请遵循第三方源码附带开源协议；
 *   8.本软件流程部分请遵循camunda开源协议：
 *     https://docs.camunda.org/manual/latest/introduction/third-party-libraries
 *     https://github.com/camunda/camunda-bpm-platform/blob/master/LICENSE
 *   9.若您的项目无法满足以上几点，可申请商业授权。
 */

package com.anyilanxin.skillfull.auth.oauth2.provider;

import com.anyilanxin.skillfull.auth.oauth2.provider.token.PictureCodeAuthenticationToken;
import com.anyilanxin.skillfull.auth.oauth2.validate.CheckDto;
import com.anyilanxin.skillfull.auth.oauth2.validate.CheckModel;
import com.anyilanxin.skillfull.auth.oauth2.validate.IValidate;
import com.anyilanxin.skillfull.corecommon.utils.I18nUtil;
import com.anyilanxin.skillfull.oauth2common.authinfo.SkillFullUserDetails;
import com.anyilanxin.skillfull.oauth2common.utils.PasswordCheck;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * 密码DaoAuthenticationProvider
 *
 * @author zxiaozhou
 * @date 2022-02-12 23:47
 * @since JDK1.8
 */
@Slf4j
public class PictureCodeAuthenticationProvider extends AbstractUserDetailsAuthenticationProvider {
    private final PasswordEncoder passwordEncoder;
    private final UserDetailsService userDetailsService;
    private final IValidate validate;

    public PictureCodeAuthenticationProvider(
            final UserDetailsService userDetailsService,
            final PasswordEncoder passwordEncoder,
            final IValidate validate) {
        this.userDetailsService = userDetailsService;
        this.passwordEncoder = passwordEncoder;
        this.validate = validate;
    }

    @Override
    protected void additionalAuthenticationChecks(
            UserDetails userDetails, UsernamePasswordAuthenticationToken authentication)
            throws AuthenticationException {
        PictureCodeAuthenticationToken pictureCodeAuthenticationToken =
                (PictureCodeAuthenticationToken) authentication;
        String pictureCode = pictureCodeAuthenticationToken.getPictureCode();
        String pictureCodeId = pictureCodeAuthenticationToken.getPictureCodeId();
        CheckDto checkDto = validate.checkVerification(new CheckModel(pictureCodeId, pictureCode));
        if (!checkDto.isResult()) {
            throw new BadCredentialsException(checkDto.getMsg());
        }
        SkillFullUserDetails customUserDetails = (SkillFullUserDetails) userDetails;
        PasswordCheck passwordCheck = PasswordCheck.getSingleton(passwordEncoder);
        if (!passwordCheck.matches(
                pictureCodeAuthenticationToken.getCredentials().toString(),
                customUserDetails.getSalt(),
                userDetails.getPassword())) {
            throw new BadCredentialsException(
                    I18nUtil.get("PictureCodeAuthenticationProvider.badCredentials"));
        }
    }

    @Override
    protected UserDetails retrieveUser(
            String username, UsernamePasswordAuthenticationToken authentication)
            throws AuthenticationException {
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        if (userDetails == null) {
            throw new UsernameNotFoundException(
                    I18nUtil.get("PictureCodeAuthenticationProvider.userNotFound"));
        }
        return userDetails;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return PictureCodeAuthenticationToken.class.isAssignableFrom(authentication);
    }

    @Override
    public void setHideUserNotFoundExceptions(boolean hideUserNotFoundExceptions) {
        super.setHideUserNotFoundExceptions(hideUserNotFoundExceptions);
    }
}
