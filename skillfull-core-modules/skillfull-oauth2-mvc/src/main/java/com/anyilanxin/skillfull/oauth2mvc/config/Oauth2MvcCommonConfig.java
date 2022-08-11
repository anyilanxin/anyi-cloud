// +----------------------------------------------------------------------
// | SkillFull快速开发平台 [ SkillFull ]
// +----------------------------------------------------------------------
// | 版权所有 2020~2021 zxiaozhou
// +----------------------------------------------------------------------
// | 官方网站: https://www.divisu.com
// +----------------------------------------------------------------------
// | 作者: zxiaozhou <z7630853@163.com>
// +----------------------------------------------------------------------
package com.anyilanxin.skillfull.oauth2mvc.config;

import com.anyilanxin.skillfull.oauth2common.config.AuthConfigAttributeModel;
import com.anyilanxin.skillfull.oauth2common.mapstruct.OauthUserAndUserDetailsCopyMap;
import com.anyilanxin.skillfull.oauth2mvc.CustomBearerTokenExtractor;
import com.anyilanxin.skillfull.oauth2mvc.user.IGetLoginUserInfo;
import com.anyilanxin.skillfull.oauth2mvc.user.impl.GetLoginUserInfoImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.authentication.BearerTokenExtractor;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.web.client.RestTemplate;

/**
 * oauth2 webflux公共配置
 *
 * @author zxiaozhou
 * @date 2022-03-01 22:30
 * @since JDK1.8
 */
@AutoConfiguration
@RequiredArgsConstructor
public class Oauth2MvcCommonConfig {
    private final TokenStore tokenStore;
    private final OauthUserAndUserDetailsCopyMap detailsCopyMap;

    /**
     * url权限配置存储
     */
    @Bean
    public AuthConfigAttributeModel authConfigAttribute() {
        return new AuthConfigAttributeModel();
    }

    /**
     * bearer读取配置
     */
    @Bean
    public BearerTokenExtractor bearerTokenExtractor() {
        return new CustomBearerTokenExtractor();
    }


    /**
     * rest template
     *
     * @return RestTemplate ${@link RestTemplate}
     * @author zxiaozhou
     * @date 2021-12-03 21:34
     */
    @Bean
    @LoadBalanced
    @ConditionalOnMissingBean
    public RestTemplate restTemplate() {
        RestTemplate rest = new RestTemplate();
        rest.getInterceptors().add((request, body, execution) -> {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication == null) {
                return execution.execute(request, body);
            }
            if (!(authentication.getDetails() instanceof OAuth2AuthenticationDetails)) {
                return execution.execute(request, body);
            }
            OAuth2AuthenticationDetails token = (OAuth2AuthenticationDetails) authentication.getDetails();
            request.getHeaders().setBearerAuth(token.getTokenValue());
            return execution.execute(request, body);
        });
        return rest;
    }


    @Bean
    @ConditionalOnMissingBean
    public IGetLoginUserInfo getLoginUserInfo() {
        return new GetLoginUserInfoImpl(tokenStore, detailsCopyMap);
    }
}
