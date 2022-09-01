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


import com.anyilanxin.skillfull.corecommon.constant.AuthConstant;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.authentication.AuthenticationWebFilter;

/**
 * @author zxiaozhou
 * @date 2022-02-19 20:42
 * @since JDK1.8
 */
@Configuration
@EnableWebFluxSecurity
@RequiredArgsConstructor
@Slf4j
public class ResourceServerConfig {
    private final TokenStore tokenStore;
    private final CustomServerAccessDeniedHandler customServerAccessDeniedHandler;
    private final CustomServerAuthenticationEntryPoint authenticationEntryPoint;

    @Bean
    public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
        //token管理器
        ReactiveAuthenticationManager authenticationManager = new CustomReactiveAuthenticationManager(tokenStore);
        //认证过滤器
        AuthenticationWebFilter authenticationWebFilter = new AuthenticationWebFilter(authenticationManager);
        CustomServerBearerTokenAuthenticationConverter serverBearerTokenAuthenticationConverter = new CustomServerBearerTokenAuthenticationConverter();
        serverBearerTokenAuthenticationConverter.setAllowUriQueryParameter(true);
        serverBearerTokenAuthenticationConverter.setAccessTokenQueryName(AuthConstant.ACCESS_TOKEN_QUERY_NAME);
        serverBearerTokenAuthenticationConverter.setBearerTokenHeaderName(AuthConstant.BEARER_TOKEN_HEADER_NAME);
        authenticationWebFilter.setServerAuthenticationConverter(serverBearerTokenAuthenticationConverter);
        http.httpBasic().disable()
                .addFilterBefore(authenticationWebFilter, SecurityWebFiltersOrder.AUTHENTICATION)
                .authorizeExchange().anyExchange().permitAll()
                .and().exceptionHandling()
                .accessDeniedHandler(customServerAccessDeniedHandler) // 处理未授权
                .authenticationEntryPoint(authenticationEntryPoint)
                .and().csrf().disable();
        return http.build();
    }
}
