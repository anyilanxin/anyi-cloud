package com.anyilanxin.skillfull.auth.oauth2;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.CollectionUtil;
import com.anyilanxin.skillfull.auth.core.properties.AuthProperty;
import com.anyilanxin.skillfull.auth.utils.Oauth2LogUtils;
import com.anyilanxin.skillfull.corecommon.constant.impl.CommonNotHaveType;
import com.anyilanxin.skillfull.oauth2common.authinfo.SkillFullAccessToken;
import com.anyilanxin.skillfull.oauth2common.authinfo.SkillFullClientDetails;
import com.anyilanxin.skillfull.oauth2common.constant.OAuth2RequestExtendConstant;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.exceptions.InvalidGrantException;
import org.springframework.security.oauth2.common.exceptions.InvalidTokenException;
import org.springframework.security.oauth2.provider.*;
import org.springframework.security.oauth2.provider.approval.Approval;
import org.springframework.security.oauth2.provider.approval.ApprovalStore;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author zxiaozhou
 * @date 2022-02-14 06:07
 * @since JDK1.8
 */
public class CustomDefaultTokenServices extends DefaultTokenServices {

    private ApprovalStore approvalStore;
    private AuthProperty authProperty;
    private ClientDetailsService clientDetailsService;


    @Override
    @Transactional
    public OAuth2AccessToken createAccessToken(OAuth2Authentication authentication) throws AuthenticationException {
        String clientId = authentication.getOAuth2Request().getClientId();
        ClientDetails clientDetails = clientDetailsService.loadClientByClientId(clientId);
        authentication = addStoredRequestExtension(authentication, clientDetails);
        // 如果非客户端模式这进行单设备校验
        if (!authentication.isClientOnly()) {
            Set<String> scope = authentication.getOAuth2Request().getScope();
            if (Objects.nonNull(clientDetails) && clientDetails instanceof SkillFullClientDetails) {
                Authentication userAuthentication = authentication.getUserAuthentication();
                Object principal = userAuthentication.getPrincipal();
                if (principal instanceof UserDetails) {
                    UserDetails userDetails = (UserDetails) principal;
                    SkillFullClientDetails customClientDetails = (SkillFullClientDetails) clientDetails;
                    if (customClientDetails.getSingleLogin() == CommonNotHaveType.HAVE.getType()) {
                        Collection<Approval> approvals = this.approvalStore.getApprovals(userDetails.getUsername(), clientId);
                        // 相同scope不能同时登录
                        if (customClientDetails.getSingleLoginType() == 2 && CollUtil.isNotEmpty(scope) && CollUtil.isNotEmpty(approvals)) {
                            approvals = approvals.stream().filter(v -> scope.contains(v.getScope())).collect(Collectors.toList());
                        }
                        if (CollUtil.isNotEmpty(approvals)) {
                            approvalStore.revokeApprovals(approvals);
                        }
                    }
                }
            }
        }
        OAuth2AccessToken oAuth2AccessToken = oAuth2AccessTokenToCustomToken(super.createAccessToken(authentication));
        Oauth2LogUtils.setPostAuthLog(true, "授权成功", "", (SkillFullAccessToken) oAuth2AccessToken);
        return oAuth2AccessToken;
    }


    @Override
    @Transactional(noRollbackFor = {InvalidTokenException.class, InvalidGrantException.class})
    public OAuth2AccessToken refreshAccessToken(String refreshTokenValue, TokenRequest tokenRequest)
            throws AuthenticationException {
        return oAuth2AccessTokenToCustomToken(super.refreshAccessToken(refreshTokenValue, tokenRequest));
    }


    private OAuth2AccessToken oAuth2AccessTokenToCustomToken(OAuth2AccessToken oAuth2AccessToken) {
        SkillFullAccessToken token = new SkillFullAccessToken(oAuth2AccessToken.getValue());
        token.setRefreshToken(oAuth2AccessToken.getRefreshToken());
        token.setScope(oAuth2AccessToken.getScope());
        token.setExpiration(oAuth2AccessToken.getExpiration());
        token.setAdditionalInformation(oAuth2AccessToken.getAdditionalInformation());
        return token;
    }


    /**
     * 添加StoredRequestExtension扩展信息
     *
     * @param authentication ${@link OAuth2Authentication}
     * @param clientDetails  ${@link ClientDetails}
     * @return OAuth2Authentication ${@link OAuth2Authentication}
     * @author zxiaozhou
     * @date 2022-03-06 23:51
     */
    private OAuth2Authentication addStoredRequestExtension(OAuth2Authentication authentication, ClientDetails clientDetails) {
        if (Objects.nonNull(clientDetails) && clientDetails instanceof SkillFullClientDetails) {
            SkillFullClientDetails customClientDetails = (SkillFullClientDetails) clientDetails;
            OAuth2Request oAuth2Request = authentication.getOAuth2Request();
            Map<String, Serializable> extensions = oAuth2Request.getExtensions();
            if (CollectionUtil.isEmpty(extensions)) {
                extensions = new HashMap<>();
            }
            Integer limitResource = customClientDetails.getLimitResource();
            if (Objects.isNull(limitResource)) {
                limitResource = 1;
            }
            extensions.put(OAuth2RequestExtendConstant.LIMIT_RESOURCE, limitResource);
            extensions.put(OAuth2RequestExtendConstant.LOGIN_ENDPOINT, "default");
            extensions.put(OAuth2RequestExtendConstant.LOGIN_UNIQUE, "default");
            if (authProperty.getTokenGeneratorType() == 1) {
                extensions.put(OAuth2RequestExtendConstant.LOGIN_UNIQUE, UUID.randomUUID().toString());
            }
            OAuth2Request newOAuth2Request = new OAuth2Request(oAuth2Request.getRequestParameters(),
                    oAuth2Request.getClientId(),
                    oAuth2Request.getAuthorities(),
                    oAuth2Request.isApproved(),
                    oAuth2Request.getScope(),
                    oAuth2Request.getResourceIds(),
                    oAuth2Request.getRedirectUri(),
                    oAuth2Request.getResponseTypes(),
                    extensions);
            return new OAuth2Authentication(newOAuth2Request, authentication.getUserAuthentication());
        }
        return authentication;
    }


    @Override
    public void setTokenStore(TokenStore tokenStore) {
        super.setTokenStore(tokenStore);
    }


    public void setApprovalStore(ApprovalStore approvalStore) {
        this.approvalStore = approvalStore;
    }

    public void setAuthProperty(AuthProperty authProperty) {
        this.authProperty = authProperty;
    }


    @Override
    public void setClientDetailsService(ClientDetailsService clientDetailsService) {
        super.setClientDetailsService(clientDetailsService);
        this.clientDetailsService = clientDetailsService;
    }
}
