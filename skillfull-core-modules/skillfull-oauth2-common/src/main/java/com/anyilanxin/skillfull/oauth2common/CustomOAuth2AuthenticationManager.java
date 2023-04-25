package com.anyilanxin.skillfull.oauth2common;

import com.anyilanxin.skillfull.oauth2common.constant.OAuth2RequestExtendConstant;
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

import java.io.Serializable;
import java.util.Collection;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

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

    public CustomOAuth2AuthenticationManager() {
    }

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
                if (limitResource != 0 && this.resourceId != null && resourceIds != null && !resourceIds.isEmpty() && !resourceIds.contains(this.resourceId)) {
                    throw new OAuth2AccessDeniedException("Invalid token does not contain resource id (" + this.resourceId + ")");
                } else {
                    this.checkClientDetails(auth);
                    if (authentication.getDetails() instanceof OAuth2AuthenticationDetails) {
                        OAuth2AuthenticationDetails details = (OAuth2AuthenticationDetails) authentication.getDetails();
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
                client = this.clientDetailsService.loadClientByClientId(auth.getOAuth2Request().getClientId());
            } catch (ClientRegistrationException var6) {
                throw new OAuth2AccessDeniedException("Invalid token contains invalid client id");
            }

            Set<String> allowed = client.getScope();
            for (String scope : auth.getOAuth2Request().getScope()) {
                if (!allowed.contains(scope)) {
                    throw new OAuth2AccessDeniedException("Invalid token contains disallowed scope (" + scope + ") for this client");
                }
            }
        }

    }
}
