/**
* Copyright (c) 2021-2022 ZHOUXUANHONG(安一老厨)<anyilanxin@aliyun.com>
*
* <p>AnYi Cloud Licensed under the Apache License, Version 2.0 (the "License"); you may not use
* this file except in compliance with the License. You may obtain a copy of the License at
*
* <p>http://www.apache.org/licenses/LICENSE-2.0
*
* <p>Unless required by applicable law or agreed to in writing, software distributed under the
* License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
* express or implied. See the License for the specific language governing permissions and
* limitations under the License.
*
* <p>AnYi Cloud 采用APACHE LICENSE 2.0开源协议，您在使用过程中，需要注意以下几点：
*
* <p>1.请不要删除和修改根目录下的LICENSE文件。 2.请不要删除和修改 AnYi Cloud 源码头部的版权声明。 3.请保留源码和相关描述文件的项目出处，作者声明等。
* 4.分发源码时候，请注明软件出处 https://github.com/anyilanxin/anyi-cloud 5.在修改包名，模块名称，项目代码等时，请注明软件出处
* https://github.com/anyilanxin/anyi-cloud 6.若您的项目无法满足以上几点，可申请商业授权
*/
package com.anyilanxin.skillfull.oauth2webflux.config;

import com.anyilanxin.skillfull.corecommon.constant.AuthConstant;
import com.anyilanxin.skillfull.oauth2common.mapstruct.OauthUserAndUserDetailsCopyMap;
import com.anyilanxin.skillfull.oauth2webflux.user.IGetLoginUserInfo;
import com.anyilanxin.skillfull.oauth2webflux.user.impl.GetLoginUserInfoImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.web.reactive.function.client.ClientRequest;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.WebClient;

/**
* @author zxiaozhou
* @date 2021-06-16 08:05
* @since JDK1.8
*/
@AutoConfiguration
@RequiredArgsConstructor
public class Oauth2WebFluxCommonConfig {
    private final TokenStore tokenStore;
    private final OauthUserAndUserDetailsCopyMap detailsCopyMap;

    /** webclient 配置 */
    @Bean
    @LoadBalanced
    @ConditionalOnMissingBean
    public WebClient webClientBuilder() {
        return WebClient.builder()
                .filter(
                        ExchangeFilterFunction.ofRequestProcessor(
                                request ->
                                        ReactiveSecurityContextHolder.getContext()
                                                .map(
                                                        context -> {
                                                            Authentication authentication = context.getAuthentication();
                                                            if (authentication.getDetails()
                                                                    instanceof OAuth2AuthenticationDetails) {
                                                                OAuth2AuthenticationDetails token =
                                                                        (OAuth2AuthenticationDetails) authentication.getDetails();
                                                                return ClientRequest.from(request)
                                                                        .header(
                                                                                AuthConstant.BEARER_TOKEN_HEADER_NAME,
                                                                                "Bearer " + token.getTokenValue())
                                                                        .build();
                                                            } else if (authentication.getDetails()
                                                                    instanceof DefaultOAuth2AccessToken) {
                                                                DefaultOAuth2AccessToken token =
                                                                        (DefaultOAuth2AccessToken) authentication.getDetails();
                                                                return ClientRequest.from(request)
                                                                        .header(
                                                                                AuthConstant.BEARER_TOKEN_HEADER_NAME,
                                                                                "Bearer " + token.getValue())
                                                                        .build();
                                                            }
                                                            return ClientRequest.from(request).build();
                                                        })))
                .build();
    }

    @Bean
    @ConditionalOnMissingBean
    public IGetLoginUserInfo getLoginUserInfo() {
        return new GetLoginUserInfoImpl(tokenStore, detailsCopyMap);
    }
}
