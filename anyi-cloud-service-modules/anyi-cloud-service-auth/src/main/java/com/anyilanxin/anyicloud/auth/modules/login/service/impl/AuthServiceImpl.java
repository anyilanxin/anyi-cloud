

package com.anyilanxin.anyicloud.auth.modules.login.service.impl;

import com.anyilanxin.anyicloud.auth.modules.login.mapper.UserAuthMapper;
import com.anyilanxin.anyicloud.auth.modules.login.service.IAuthService;
import com.anyilanxin.anyicloud.auth.modules.login.service.IUserAuthService;
import com.anyilanxin.anyicloud.auth.modules.login.service.dto.RbacUserDto;
import com.anyilanxin.anyicloud.corecommon.model.auth.UserInfo;
import com.anyilanxin.anyicloud.corecommon.model.system.UserAndResourceAuthModel;
import com.anyilanxin.anyicloud.oauth2common.utils.Oauth2CommonUtils;
import com.anyilanxin.anyicloud.oauth2mvc.utils.UserContextUtils;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;

import java.util.Objects;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.stereotype.Service;

/**
 * 授权相关
 *
 * @author zxh
 * @date 2022-02-19 10:12
 * @since 1.0.0
 */
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements IAuthService {
    private final TokenStore tokenStore;
    private final UserAuthMapper userAuthMapper;
    private final IUserAuthService authService;

    @Override
    public void logOut() {
        SecurityContext context = SecurityContextHolder.getContext();
        if (Objects.nonNull(context.getAuthentication())) {
            Authentication principal = context.getAuthentication();
            if (principal instanceof OAuth2Authentication) {
                OAuth2Authentication authentication = (OAuth2Authentication) principal;
                OAuth2AccessToken accessToken = tokenStore.getAccessToken(authentication);
                if (Objects.nonNull(accessToken)) {
                    tokenStore.removeAccessToken(accessToken);
                }
            }
        }
    }


    @Override
    public UserInfo getUserInfo(String orgId) {
        RbacUserDto userDto = userAuthMapper.selectUserInfoByUserId(UserContextUtils.getUserId());
        String currentOrgId = orgId;
        if (StringUtils.isBlank(currentOrgId)) {
            currentOrgId = UserContextUtils.getCurrentOrgId();
        }
        if (StringUtils.isNotBlank(currentOrgId) && !currentOrgId.equals(UserContextUtils.getCurrentOrgId())) {
            // 更新用户当前登录机构信息
            userAuthMapper.updateLoginOrgId(userDto.getUserId(), orgId);
        }
        UserAndResourceAuthModel userInfo = authService.getUserInfo(userDto, currentOrgId, false);
        Oauth2CommonUtils.refreshUserOauth(userInfo);
        return userInfo;
    }
}
