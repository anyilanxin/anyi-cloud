// +----------------------------------------------------------------------
// | SkillFull快速开发平台 [ SkillFull ]
// +----------------------------------------------------------------------
// | 版权所有 2020~2021 zxiaozhou
// +----------------------------------------------------------------------
// | 官方网站: https://skillfull.divisu.com
// +----------------------------------------------------------------------
// | 作者: zxiaozhou <z7630853@163.com>
// +----------------------------------------------------------------------
package com.anyilanxin.skillfull.oauth2webflux.oauth2;

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
 * @author zxiaozhou
 * @date 2022-02-19 20:53
 * @since JDK1.8
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
        return Mono.justOrEmpty(authentication)
                .filter(a -> a instanceof BearerTokenAuthenticationToken)
                .cast(BearerTokenAuthenticationToken.class)
                .map(BearerTokenAuthenticationToken::getToken)
                .flatMap((accessToken -> {
                    OAuth2AccessToken oAuth2AccessToken = this.tokenStore.readAccessToken(accessToken);
                    //根据access_token从数据库获取不到OAuth2AccessToken
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
                }))
                .onErrorStop()
                .cast(Authentication.class);
    }
}
