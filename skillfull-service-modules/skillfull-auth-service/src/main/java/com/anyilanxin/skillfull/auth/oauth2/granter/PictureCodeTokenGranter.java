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

package com.anyilanxin.skillfull.auth.oauth2.granter;

import com.anyilanxin.skillfull.auth.oauth2.provider.token.PictureCodeAuthenticationToken;
import com.anyilanxin.skillfull.auth.utils.Oauth2LogUtils;
import com.anyilanxin.skillfull.corecommon.constant.impl.AuthorizedGrantTypes;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.provider.*;
import org.springframework.security.oauth2.provider.token.AbstractTokenGranter;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;

/**
 * 图片验证码模式
 *
 * @author zxiaozhou
 * @date 2022-02-12 20:41
 * @since JDK1.8
 */
@Slf4j
public class PictureCodeTokenGranter extends AbstractTokenGranter {
  private final AuthenticationManager authenticationManager;

  public PictureCodeTokenGranter(
      AuthenticationManager authenticationManager,
      AuthorizationServerTokenServices tokenServices,
      ClientDetailsService clientDetailsService,
      OAuth2RequestFactory requestFactory) {
    super(
        tokenServices,
        clientDetailsService,
        requestFactory,
        AuthorizedGrantTypes.PICTURE_CODE.getType());
    this.authenticationManager = authenticationManager;
  }

  @Override
  protected OAuth2Authentication getOAuth2Authentication(
      ClientDetails client, TokenRequest tokenRequest) {
    Oauth2LogUtils.setPreAuthLog(client, tokenRequest);
    Map<String, String> requestParameters = tokenRequest.getRequestParameters();
    String accountOrPhone = requestParameters.get("userName");
    String password = requestParameters.get("password");
    String pictureCode = requestParameters.get("code");
    String pictureCodeId = requestParameters.get("codeId");
    if (StringUtils.isBlank(accountOrPhone)) {
      throw new InternalAuthenticationServiceException("手机号或账号不能为空");
    }
    if (StringUtils.isBlank(password)) {
      throw new InternalAuthenticationServiceException("密码不能为空");
    }
    if (StringUtils.isBlank(pictureCode)) {
      throw new InternalAuthenticationServiceException("验证码不能为空");
    }
    if (StringUtils.isBlank(pictureCodeId)) {
      throw new InternalAuthenticationServiceException("验证码不能为空");
    }
    PictureCodeAuthenticationToken pictureCodeAuthenticationToken =
        new PictureCodeAuthenticationToken(accountOrPhone, password, pictureCode, pictureCodeId);
    Authentication authenticate =
        authenticationManager.authenticate(pictureCodeAuthenticationToken);
    OAuth2Request oAuth2Request = getRequestFactory().createOAuth2Request(client, tokenRequest);
    return new OAuth2Authentication(oAuth2Request, authenticate);
  }
}
