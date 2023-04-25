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
 *   1.请不要删除和修改根目录下的LICENSE文件。
 *   2.请不要删除和修改 AnYi Cloud 源码头部的版权声明。
 *   3.请保留源码和相关描述文件的项目出处，作者声明等。
 *   4.分发源码时候，请注明软件出处 https://github.com/anyilanxin/anyi-cloud
 *   5.在修改包名，模块名称，项目代码等时，请注明软件出处 https://github.com/anyilanxin/anyi-cloud
 *   6.若您的项目无法满足以上几点，可申请商业授权
 */

package com.anyilanxin.skillfull.auth.oauth2.provider;

import com.anyilanxin.skillfull.auth.oauth2.provider.token.ClientCredentialsAuthenticationToken;
import com.anyilanxin.skillfull.corecommon.utils.I18nUtil;
import com.anyilanxin.skillfull.oauth2common.utils.PasswordCheck;
import java.util.Objects;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;

/**
 * 开放id DaoAuthenticationProvider
 *
 * @author zxiaozhou
 * @date 2022-02-12 23:47
 * @since JDK1.8
 */
@Slf4j
public class ClientCredentialsAuthenticationProvider
    extends AbstractUserDetailsAuthenticationProvider {
  private final ClientDetailsService clientDetailsService;
  private final PasswordEncoder passwordEncoder;

  public ClientCredentialsAuthenticationProvider(
      final ClientDetailsService clientDetailsService, final PasswordEncoder passwordEncoder) {
    this.clientDetailsService = clientDetailsService;
    this.passwordEncoder = passwordEncoder;
  }

  @Override
  protected void additionalAuthenticationChecks(
      UserDetails userDetails, UsernamePasswordAuthenticationToken authentication)
      throws AuthenticationException {
    ClientCredentialsAuthenticationToken token =
        (ClientCredentialsAuthenticationToken) authentication;
    String credentials = token.getCredentials().toString();
    PasswordCheck passwordCheck = PasswordCheck.getSingleton(passwordEncoder);
    if (!passwordCheck.matches(credentials, userDetails.getPassword())) {
      throw new BadCredentialsException(
          I18nUtil.get("ClientCredentialsAuthenticationProvider.clientBadCredentials"));
    }
  }

  @Override
  protected UserDetails retrieveUser(
      String username, UsernamePasswordAuthenticationToken authentication)
      throws AuthenticationException {
    ClientDetails clientDetails = clientDetailsService.loadClientByClientId(username);
    if (Objects.isNull(clientDetails)) {
      throw new InternalAuthenticationServiceException(
          I18nUtil.get("ClientCredentialsAuthenticationProvider.clientNotFound"));
    }
    String clientSecret = clientDetails.getClientSecret();
    if (StringUtils.isBlank(clientSecret)) {
      clientSecret = "";
    }
    return new User(username, clientSecret, clientDetails.getAuthorities());
  }

  @Override
  public boolean supports(Class<?> authentication) {
    return ClientCredentialsAuthenticationToken.class.isAssignableFrom(authentication);
  }

  @Override
  public void setHideUserNotFoundExceptions(boolean hideUserNotFoundExceptions) {
    super.setHideUserNotFoundExceptions(hideUserNotFoundExceptions);
  }
}
