// +----------------------------------------------------------------------
// | SkillFull快速开发平台 [ SkillFull ]
// +----------------------------------------------------------------------
// | 版权所有 2020~2021 zxiaozhou
// +----------------------------------------------------------------------
// | 官方网站: https://skillfull.divisu.com
// +----------------------------------------------------------------------
// | 作者: zxiaozhou <z7630853@163.com>
// +----------------------------------------------------------------------
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

    /**
     * webclient 配置
     */
    @Bean
    @LoadBalanced
    @ConditionalOnMissingBean
    public WebClient webClientBuilder() {
        return WebClient.builder()
                .filter(ExchangeFilterFunction.ofRequestProcessor(
                        request -> ReactiveSecurityContextHolder.getContext().map(context -> {
                            Authentication authentication = context.getAuthentication();
                            if (authentication.getDetails() instanceof OAuth2AuthenticationDetails) {
                                OAuth2AuthenticationDetails token = (OAuth2AuthenticationDetails) authentication.getDetails();
                                return ClientRequest.from(request)
                                        .header(AuthConstant.BEARER_TOKEN_HEADER_NAME, "Bearer " + token.getTokenValue())
                                        .build();
                            } else if (authentication.getDetails() instanceof DefaultOAuth2AccessToken) {
                                DefaultOAuth2AccessToken token = (DefaultOAuth2AccessToken) authentication.getDetails();
                                return ClientRequest.from(request)
                                        .header(AuthConstant.BEARER_TOKEN_HEADER_NAME, "Bearer " + token.getValue())
                                        .build();
                            }
                            return ClientRequest.from(request)
                                    .build();
                        }))
                )
                .build();
    }


    @Bean
    @ConditionalOnMissingBean
    public IGetLoginUserInfo getLoginUserInfo() {
        return new GetLoginUserInfoImpl(tokenStore, detailsCopyMap);
    }
}
