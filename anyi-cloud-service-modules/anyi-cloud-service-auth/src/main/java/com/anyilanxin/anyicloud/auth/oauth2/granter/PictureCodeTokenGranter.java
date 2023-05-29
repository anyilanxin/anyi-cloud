

package com.anyilanxin.anyicloud.auth.oauth2.granter;

import com.anyilanxin.anyicloud.auth.oauth2.provider.token.PictureCodeAuthenticationToken;
import com.anyilanxin.anyicloud.auth.utils.Oauth2LogUtils;
import com.anyilanxin.anyicloud.corecommon.constant.impl.AuthorizedGrantTypes;

import java.util.Map;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.provider.*;
import org.springframework.security.oauth2.provider.token.AbstractTokenGranter;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;

/**
 * 图片验证码模式
 *
 * @author zxh
 * @date 2022-02-12 20:41
 * @since 1.0.0
 */
@Slf4j
public class PictureCodeTokenGranter extends AbstractTokenGranter {
    private final AuthenticationManager authenticationManager;

    public PictureCodeTokenGranter(AuthenticationManager authenticationManager, AuthorizationServerTokenServices tokenServices, ClientDetailsService clientDetailsService, OAuth2RequestFactory requestFactory) {
        super(tokenServices, clientDetailsService, requestFactory, AuthorizedGrantTypes.PICTURE_CODE.getType());
        this.authenticationManager = authenticationManager;
    }


    @Override
    protected OAuth2Authentication getOAuth2Authentication(ClientDetails client, TokenRequest tokenRequest) {
        Oauth2LogUtils.setPreAuthLog(client, tokenRequest);
        Map<String, String> requestParameters = tokenRequest.getRequestParameters();
        String accountOrPhone = requestParameters.get("userName");
        String password = requestParameters.get("password");
        String pictureCode = requestParameters.get("code");
        String pictureCodeId = requestParameters.get("codeId");
        if (StringUtils.isBlank(accountOrPhone)) {
            throw new InternalAuthenticationServiceException("手机号或账号不能为空");
        }
        if (StringUtils.isBlank(password)) {
            throw new InternalAuthenticationServiceException("密码不能为空");
        }
        if (StringUtils.isBlank(pictureCode)) {
            throw new InternalAuthenticationServiceException("验证码不能为空");
        }
        if (StringUtils.isBlank(pictureCodeId)) {
            throw new InternalAuthenticationServiceException("验证码不能为空");
        }
        PictureCodeAuthenticationToken pictureCodeAuthenticationToken = new PictureCodeAuthenticationToken(accountOrPhone, password, pictureCode, pictureCodeId);
        Authentication authenticate = authenticationManager.authenticate(pictureCodeAuthenticationToken);
        OAuth2Request oAuth2Request = getRequestFactory().createOAuth2Request(client, tokenRequest);
        return new OAuth2Authentication(oAuth2Request, authenticate);
    }
}
