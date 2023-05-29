

package com.anyilanxin.anyicloud.auth.oauth2.provider;

import com.anyilanxin.anyicloud.auth.oauth2.provider.token.ClientCredentialsAuthenticationToken;
import com.anyilanxin.anyicloud.corecommon.utils.I18nUtil;
import com.anyilanxin.anyicloud.oauth2common.utils.PasswordCheck;

import java.util.Objects;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;

/**
 * 开放id DaoAuthenticationProvider
 *
 * @author zxh
 * @date 2022-02-12 23:47
 * @since 1.0.0
 */
@Slf4j
public class ClientCredentialsAuthenticationProvider extends AbstractUserDetailsAuthenticationProvider {
    private final ClientDetailsService clientDetailsService;
    private final PasswordEncoder passwordEncoder;

    public ClientCredentialsAuthenticationProvider(final ClientDetailsService clientDetailsService, final PasswordEncoder passwordEncoder) {
        this.clientDetailsService = clientDetailsService;
        this.passwordEncoder = passwordEncoder;
    }


    @Override
    protected void additionalAuthenticationChecks(UserDetails userDetails, UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {
        ClientCredentialsAuthenticationToken token = (ClientCredentialsAuthenticationToken) authentication;
        String credentials = token.getCredentials().toString();
        PasswordCheck passwordCheck = PasswordCheck.getSingleton(passwordEncoder);
        if (!passwordCheck.matches(credentials, userDetails.getPassword())) {
            throw new BadCredentialsException(I18nUtil.get("ClientCredentialsAuthenticationProvider.clientBadCredentials"));
        }
    }


    @Override
    protected UserDetails retrieveUser(String username, UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {
        ClientDetails clientDetails = clientDetailsService.loadClientByClientId(username);
        if (Objects.isNull(clientDetails)) {
            throw new InternalAuthenticationServiceException(I18nUtil.get("ClientCredentialsAuthenticationProvider.clientNotFound"));
        }
        String clientSecret = clientDetails.getClientSecret();
        if (StringUtils.isBlank(clientSecret)) {
            clientSecret = "";
        }
        return new User(username, clientSecret, clientDetails.getAuthorities());
    }


    @Override
    public boolean supports(Class<?> authentication) {
        return ClientCredentialsAuthenticationToken.class.isAssignableFrom(authentication);
    }


    @Override
    public void setHideUserNotFoundExceptions(boolean hideUserNotFoundExceptions) {
        super.setHideUserNotFoundExceptions(hideUserNotFoundExceptions);
    }
}
