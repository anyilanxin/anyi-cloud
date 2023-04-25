/**
 * Copyright (c) 2021-2022 ZHOUXUANHONG(安一老厨)<anyilanxin@aliyun.com>
 *
 * AnYi Cloud Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * AnYi Cloud 采用APACHE LICENSE 2.0开源协议，您在使用过程中，需要注意以下几点：
 *
 * 1.请不要删除和修改根目录下的LICENSE文件。
 * 2.请不要删除和修改 AnYi Cloud 源码头部的版权声明。
 * 3.请保留源码和相关描述文件的项目出处，作者声明等。
 * 4.分发源码时候，请注明软件出处 https://github.com/anyilanxin/anyi-cloud
 * 5.在修改包名，模块名称，项目代码等时，请注明软件出处 https://github.com/anyilanxin/anyi-cloud
 * 6.若您的项目无法满足以上几点，可申请商业授权
 */
package com.anyilanxin.skillfull.oauth2mvc.config;

import com.anyilanxin.skillfull.corecommon.constant.AuthConstant;
import com.anyilanxin.skillfull.corecommon.utils.ClientTokenUtils;
import com.anyilanxin.skillfull.oauth2common.config.AuthConfigAttributeModel;
import com.anyilanxin.skillfull.oauth2common.mapstruct.OauthUserAndUserDetailsCopyMap;
import com.anyilanxin.skillfull.oauth2mvc.CustomBearerTokenExtractor;
import com.anyilanxin.skillfull.oauth2mvc.user.IGetLoginUserInfo;
import com.anyilanxin.skillfull.oauth2mvc.user.impl.GetLoginUserInfoImpl;
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
