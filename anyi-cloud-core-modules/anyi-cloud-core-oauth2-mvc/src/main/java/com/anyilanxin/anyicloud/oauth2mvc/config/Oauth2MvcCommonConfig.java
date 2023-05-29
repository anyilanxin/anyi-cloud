

package com.anyilanxin.anyicloud.oauth2mvc.config;

import com.anyilanxin.anyicloud.corecommon.constant.AuthConstant;
import com.anyilanxin.anyicloud.corecommon.utils.ClientTokenUtils;
import com.anyilanxin.anyicloud.oauth2common.config.AuthConfigAttributeModel;
import com.anyilanxin.anyicloud.oauth2common.mapstruct.OauthUserAndUserDetailsCopyMap;
import com.anyilanxin.anyicloud.oauth2mvc.CustomBearerTokenExtractor;
import com.anyilanxin.anyicloud.oauth2mvc.user.IGetLoginUserInfo;
import com.anyilanxin.anyicloud.oauth2mvc.user.impl.GetLoginUserInfoImpl;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
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
 * @author zxh
 * @date 2022-03-01 22:30
 * @since 1.0.0
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
     * @author zxh
     * @date 2021-12-03 21:34
     */
    @Bean
    @LoadBalanced
    @ConditionalOnMissingBean
    public RestTemplate restTemplate() {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getInterceptors().add((request, body, execution) -> {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication == null) {
                return execution.execute(request, body);
            }
            if (!(authentication.getDetails() instanceof OAuth2AuthenticationDetails)) {
                return execution.execute(request, body);
            }
            OAuth2AuthenticationDetails token = (OAuth2AuthenticationDetails) authentication.getDetails();
            request.getHeaders().add(AuthConstant.BEARER_TOKEN_HEADER_NAME, token.getTokenValue());
            // 不存在则启动客户端模式获取token
            if (StringUtils.isBlank(token.getTokenValue())) {
                String tokenToAuthService = ClientTokenUtils.getTokenToAuthService();
                if (StringUtils.isNotBlank(tokenToAuthService)) {
                    request.getHeaders().add(AuthConstant.BEARER_TOKEN_HEADER_NAME, token.getTokenValue());
                }
            }
            return execution.execute(request, body);
        });
        return restTemplate;
    }


    @Bean
    @ConditionalOnMissingBean
    public IGetLoginUserInfo getLoginUserInfo() {
        return new GetLoginUserInfoImpl(tokenStore, detailsCopyMap);
    }
}
