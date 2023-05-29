

package com.anyilanxin.anyicloud.oauth2mvc.config;

import static com.anyilanxin.anyicloud.corecommon.constant.SysBaseConstant.DEFAULT_RESOURCE_ID;

import cn.hutool.core.collection.CollUtil;
import com.anyilanxin.anyicloud.corecommon.constant.AuthConstant;
import com.anyilanxin.anyicloud.oauth2common.CustomOAuth2AuthenticationManager;
import com.anyilanxin.anyicloud.oauth2mvc.CustomAccessDeniedHandler;
import com.anyilanxin.anyicloud.oauth2mvc.CustomBearerTokenExtractor;
import com.anyilanxin.anyicloud.oauth2mvc.CustomLogoutSuccessHandler;
import com.anyilanxin.anyicloud.oauth2mvc.CustomOAuthEntryPoint;
import com.anyilanxin.anyicloud.oauth2mvc.config.properties.CustomSecurityProperties;
import com.anyilanxin.anyicloud.oauth2mvc.utils.Oauth2Utils;

import java.util.Set;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationManager;
import org.springframework.security.oauth2.provider.authentication.TokenExtractor;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

/**
 * 资源服务器配置
 *
 * @author zhouxuanhong
 * @date 2019-05-20 19:42
 * @since 1.0.0
 */
@AutoConfiguration
@EnableResourceServer
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
@RequiredArgsConstructor
@Slf4j
public class ResourceServerConfigurer extends ResourceServerConfigurerAdapter {
    private final TokenStore tokenStore;
    private final ApplicationContext applicationContext;
    private final CustomSecurityProperties properties;

    @Value("${spring.application.name:" + DEFAULT_RESOURCE_ID + "}")
    private String applicationName;

    @Override
    public void configure(HttpSecurity http) throws Exception {
        // 禁止csrf攻击
        http.csrf().disable();
        // 允许iframe内嵌
        http.headers().frameOptions().disable();
        http.logout().logoutSuccessHandler(logoutSuccessHandler());
        if (!properties.isEnabled()) {
            log.info("------------CustomResourceServerConfigurerAdapter------------>当前设置了不需要鉴权");
            http.authorizeRequests().anyRequest().permitAll();
        } else {
            // 接管所有请求
            Set<Oauth2Utils.WhiteListInfo> whiteList = Oauth2Utils.getWhiteList(applicationContext, properties);
            log.info("------------CustomResourceServerConfigurer------当前鉴权白名单------>configure:\n{}", whiteList);
            if (CollUtil.isNotEmpty(whiteList)) {
                for (Oauth2Utils.WhiteListInfo whiteListInfo : whiteList) {
                    Set<HttpMethod> methods = whiteListInfo.getMethods();
                    Set<String> urls = whiteListInfo.getUrls();
                    if (CollUtil.isNotEmpty(methods)) {
                        for (HttpMethod method : methods) {
                            http.authorizeRequests().antMatchers(method, urls.toArray(new String[]{})).permitAll();
                        }
                    } else {
                        http.authorizeRequests().antMatchers(urls.toArray(new String[]{})).permitAll();
                    }
                }
            }
            http.authorizeRequests().anyRequest().authenticated();
        }
    }


    @Override
    public void configure(ResourceServerSecurityConfigurer resources) {
        resources.tokenStore(tokenStore).resourceId(applicationName).tokenExtractor(tokenExtractor()).authenticationManager(authenticationManager()).authenticationEntryPoint(oAuthEntryPoint()).accessDeniedHandler(accessDeniedHandler());
    }


    @Bean
    public OAuth2AuthenticationManager authenticationManager() {
        CustomOAuth2AuthenticationManager customOAuth2AuthenticationManager = new CustomOAuth2AuthenticationManager();
        DefaultTokenServices defaultTokenServices = new DefaultTokenServices();
        defaultTokenServices.setTokenStore(tokenStore);
        customOAuth2AuthenticationManager.setTokenServices(defaultTokenServices);
        return customOAuth2AuthenticationManager;
    }


    @Bean
    public CustomOAuthEntryPoint oAuthEntryPoint() {
        return new CustomOAuthEntryPoint();
    }


    @Bean
    public CustomAccessDeniedHandler accessDeniedHandler() {
        return new CustomAccessDeniedHandler();
    }


    @Bean
    public LogoutSuccessHandler logoutSuccessHandler() {
        return new CustomLogoutSuccessHandler(tokenExtractor(), tokenStore);
    }


    @Bean
    public TokenExtractor tokenExtractor() {
        CustomBearerTokenExtractor tokenExtractor = new CustomBearerTokenExtractor();
        tokenExtractor.setAllowUriQueryParameter(true);
        tokenExtractor.setAccessTokenQueryName(AuthConstant.ACCESS_TOKEN_QUERY_NAME);
        tokenExtractor.setBearerTokenHeaderName(AuthConstant.BEARER_TOKEN_HEADER_NAME);
        return tokenExtractor;
    }
}
