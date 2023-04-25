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
package com.anyilanxin.skillfull.oauth2common;

import com.anyilanxin.skillfull.oauth2common.constant.OAuth2RequestExtendConstant;
import java.io.Serializable;
import java.util.Collection;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.client.resource.OAuth2AccessDeniedException;
import org.springframework.security.oauth2.common.exceptions.InvalidTokenException;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.ClientRegistrationException;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationManager;
import org.springframework.security.oauth2.provider.token.ResourceServerTokenServices;
import org.springframework.util.Assert;

/**
* @author zxiaozhou
* @date 2022-03-06 21:42
* @since JDK1.8
*/
@Slf4j
public class CustomOAuth2AuthenticationManager extends OAuth2AuthenticationManager {
    private ResourceServerTokenServices tokenServices;
    private ClientDetailsService clientDetailsService;
    private String resourceId;

    public CustomOAuth2AuthenticationManager() {}

    @Override
    public void setResourceId(String resourceId) {
        super.setResourceId(resourceId);
        this.resourceId = resourceId;
    }

    @Override
    public void setClientDetailsService(ClientDetailsService clientDetailsService) {
        super.setClientDetailsService(clientDetailsService);
        this.clientDetailsService = clientDetailsService;
    }

    @Override
    public void setTokenServices(ResourceServerTokenServices tokenServices) {
        super.setTokenServices(tokenServices);
        this.tokenServices = tokenServices;
    }

    @Override
    public void afterPropertiesSet() {
        Assert.state(this.tokenServices != null, "TokenServices are required");
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        if (authentication == null) {
            throw new InvalidTokenException("Invalid token (token not found)");
        } else {
            String token = (String) authentication.getPrincipal();
            OAuth2Authentication auth = this.tokenServices.loadAuthentication(token);
            if (auth == null) {
                throw new InvalidTokenException("Invalid token: " + token);
            } else {
                Map<String, Serializable> extensions = auth.getOAuth2Request().getExtensions();
                Collection<String> resourceIds = auth.getOAuth2Request().getResourceIds();
                Integer limitResource = 1;
                if (Objects.nonNull(extensions.get(OAuth2RequestExtendConstant.LIMIT_RESOURCE))) {
                    limitResource = (Integer) extensions.get(OAuth2RequestExtendConstant.LIMIT_RESOURCE);
                }
                if (limitResource != 0
                        && this.resourceId != null
                        && resourceIds != null
                        && !resourceIds.isEmpty()
                        && !resourceIds.contains(this.resourceId)) {
                    throw new OAuth2AccessDeniedException(
                            "Invalid token does not contain resource id (" + this.resourceId + ")");
                } else {
                    this.checkClientDetails(auth);
                    if (authentication.getDetails() instanceof OAuth2AuthenticationDetails) {
                        OAuth2AuthenticationDetails details =
                                (OAuth2AuthenticationDetails) authentication.getDetails();
                        if (!details.equals(auth.getDetails())) {
                            details.setDecodedDetails(auth.getDetails());
                        }
                    }

                    auth.setDetails(authentication.getDetails());
                    auth.setAuthenticated(true);
                    return auth;
                }
            }
        }
    }

    private void checkClientDetails(OAuth2Authentication auth) {
        if (this.clientDetailsService != null) {
            ClientDetails client;
            try {
                client =
                        this.clientDetailsService.loadClientByClientId(auth.getOAuth2Request().getClientId());
            } catch (ClientRegistrationException var6) {
                throw new OAuth2AccessDeniedException("Invalid token contains invalid client id");
            }

            Set<String> allowed = client.getScope();
            for (String scope : auth.getOAuth2Request().getScope()) {
                if (!allowed.contains(scope)) {
                    throw new OAuth2AccessDeniedException(
                            "Invalid token contains disallowed scope (" + scope + ") for this client");
                }
            }
        }
    }
}
