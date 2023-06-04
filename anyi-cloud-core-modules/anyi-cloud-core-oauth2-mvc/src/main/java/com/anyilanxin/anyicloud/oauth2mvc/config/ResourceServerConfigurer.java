/*
 * Copyright (c) 2021-present ZHOUXUANHONG(安一老厨)<anyilanxin@aliyun.com>
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
