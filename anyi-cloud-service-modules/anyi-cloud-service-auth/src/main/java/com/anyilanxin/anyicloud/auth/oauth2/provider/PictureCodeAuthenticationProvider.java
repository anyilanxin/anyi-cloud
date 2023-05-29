

package com.anyilanxin.anyicloud.auth.oauth2.provider;

import com.anyilanxin.anyicloud.auth.oauth2.provider.token.PictureCodeAuthenticationToken;
import com.anyilanxin.anyicloud.auth.oauth2.validate.CheckDto;
import com.anyilanxin.anyicloud.auth.oauth2.validate.CheckModel;
import com.anyilanxin.anyicloud.auth.oauth2.validate.IValidate;
import com.anyilanxin.anyicloud.corecommon.utils.I18nUtil;
import com.anyilanxin.anyicloud.oauth2common.authinfo.SkillFullUserDetails;
import com.anyilanxin.anyicloud.oauth2common.utils.PasswordCheck;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * 密码DaoAuthenticationProvider
 *
 * @author zxh
 * @date 2022-02-12 23:47
 * @since 1.0.0
 */
@Slf4j
public class PictureCodeAuthenticationProvider extends AbstractUserDetailsAuthenticationProvider {
    private final PasswordEncoder passwordEncoder;
    private final UserDetailsService userDetailsService;
    private final IValidate validate;

    public PictureCodeAuthenticationProvider(final UserDetailsService userDetailsService, final PasswordEncoder passwordEncoder, final IValidate validate) {
        this.userDetailsService = userDetailsService;
        this.passwordEncoder = passwordEncoder;
        this.validate = validate;
    }


    @Override
    protected void additionalAuthenticationChecks(UserDetails userDetails, UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {
        PictureCodeAuthenticationToken pictureCodeAuthenticationToken = (PictureCodeAuthenticationToken) authentication;
        String pictureCode = pictureCodeAuthenticationToken.getPictureCode();
        String pictureCodeId = pictureCodeAuthenticationToken.getPictureCodeId();
        CheckDto checkDto = validate.checkVerification(new CheckModel(pictureCodeId, pictureCode));
        if (!checkDto.isResult()) {
            throw new BadCredentialsException(checkDto.getMsg());
        }
        SkillFullUserDetails customUserDetails = (SkillFullUserDetails) userDetails;
        PasswordCheck passwordCheck = PasswordCheck.getSingleton(passwordEncoder);
        if (!passwordCheck.matches(pictureCodeAuthenticationToken.getCredentials().toString(), customUserDetails.getSalt(), userDetails.getPassword())) {
            throw new BadCredentialsException(I18nUtil.get("PictureCodeAuthenticationProvider.badCredentials"));
        }
    }


    @Override
    protected UserDetails retrieveUser(String username, UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        if (userDetails == null) {
            throw new UsernameNotFoundException(I18nUtil.get("PictureCodeAuthenticationProvider.userNotFound"));
        }
        return userDetails;
    }


    @Override
    public boolean supports(Class<?> authentication) {
        return PictureCodeAuthenticationToken.class.isAssignableFrom(authentication);
    }


    @Override
    public void setHideUserNotFoundExceptions(boolean hideUserNotFoundExceptions) {
        super.setHideUserNotFoundExceptions(hideUserNotFoundExceptions);
    }
}
