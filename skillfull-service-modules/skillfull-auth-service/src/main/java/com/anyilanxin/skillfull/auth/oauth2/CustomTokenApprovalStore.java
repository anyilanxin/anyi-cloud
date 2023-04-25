package com.anyilanxin.skillfull.auth.oauth2;

import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.approval.Approval;
import org.springframework.security.oauth2.provider.approval.TokenApprovalStore;
import org.springframework.security.oauth2.provider.token.TokenStore;

import java.util.Collection;

/**
 * 自定义ApprovalStore
 *
 * @author zxiaozhou
 * @date 2022-02-17 19:33
 * @since JDK1.8
 */
public class CustomTokenApprovalStore extends TokenApprovalStore {
    private TokenStore store;

    @Override
    public void setTokenStore(TokenStore store) {
        super.setTokenStore(store);
        this.store = store;
    }


    @Override
    public boolean revokeApprovals(Collection<Approval> approvals) {
        boolean success = true;
        for (Approval approval : approvals) {
            Collection<OAuth2AccessToken> tokens = store.findTokensByClientIdAndUserName(approval.getClientId(), approval.getUserId());
            for (OAuth2AccessToken token : tokens) {
                if (token.getScope().contains(approval.getScope())) {
                    OAuth2Authentication authentication = store.readAuthentication(token);
                    if (authentication != null && approval.getClientId().equals(authentication.getOAuth2Request().getClientId())) {
                        store.removeAccessToken(token);
                    }
                }
            }
        }
        return success;
    }


    @Override
    public Collection<Approval> getApprovals(String userId, String clientId) {
        return super.getApprovals(userId, clientId);
    }
}
