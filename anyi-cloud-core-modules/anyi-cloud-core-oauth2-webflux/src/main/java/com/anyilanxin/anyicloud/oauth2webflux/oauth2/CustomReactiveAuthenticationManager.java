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

package com.anyilanxin.anyicloud.oauth2webflux.oauth2;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.exceptions.InvalidTokenException;
import org.springframework.security.oauth2.common.exceptions.UnauthorizedClientException;
import org.springframework.security.oauth2.common.exceptions.UnauthorizedUserException;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.server.resource.BearerTokenAuthenticationToken;
import reactor.core.publisher.Mono;

/**
 * @author zxh
 * @date 2022-02-19 20:53
 * @since 1.0.0
 */
@Slf4j
public class CustomReactiveAuthenticationManager implements ReactiveAuthenticationManager {
    private final TokenStore tokenStore;

    public CustomReactiveAuthenticationManager(TokenStore tokenStore) {
        super();
        this.tokenStore = tokenStore;
    }


    @Override
    public Mono<Authentication> authenticate(Authentication authentication) {
        return Mono.justOrEmpty(authentication).filter(a -> a instanceof BearerTokenAuthenticationToken).cast(BearerTokenAuthenticationToken.class).map(BearerTokenAuthenticationToken::getToken).flatMap((accessToken -> {
            OAuth2AccessToken oAuth2AccessToken = this.tokenStore.readAccessToken(accessToken);
            // 根据access_token从数据库获取不到OAuth2AccessToken
            if (oAuth2AccessToken == null) {
                throw new InvalidTokenException("无效的token");
            } else if (oAuth2AccessToken.isExpired()) {
                throw new InvalidTokenException("token已经过期，请刷新");
            }
            OAuth2Authentication oAuth2Authentication = this.tokenStore.readAuthentication(accessToken);
            if (oAuth2Authentication == null) {
                throw new InvalidTokenException("Access Token 无效!");
            } else if (oAuth2Authentication.isClientOnly() && !oAuth2Authentication.isAuthenticated()) {
                throw new UnauthorizedClientException("当前客户端未授权");
            } else if (!oAuth2Authentication.isClientOnly() && !oAuth2Authentication.getUserAuthentication().isAuthenticated()) {
                throw new UnauthorizedUserException("当前用户未授权");
            } else {
                oAuth2Authentication.setDetails(oAuth2AccessToken);
                return Mono.just(oAuth2Authentication);
            }
        })).onErrorStop().cast(Authentication.class);
    }
}
