

package com.anyilanxin.anyicloud.auth.modules.detail.servive.impl;

import com.anyilanxin.anyicloud.auth.modules.login.service.IClientAuthService;
import com.anyilanxin.anyicloud.auth.utils.Oauth2LogUtils;
import com.anyilanxin.anyicloud.corecommon.utils.I18nUtil;
import com.anyilanxin.anyicloud.oauth2common.authinfo.SkillFullClientDetails;
import com.anyilanxin.anyicloud.oauth2common.utils.Oauth2CommonUtils;

import java.util.HashMap;
import java.util.Map;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.oauth2.common.exceptions.InvalidClientException;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.ClientRegistrationException;
import org.springframework.stereotype.Service;

/**
 * 数据库获取客户端信息
 *
 * @author zxh
 * @date 2022-02-11 09:45
 * @since 1.0.0
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class JdbcClientDetailsService implements ClientDetailsService {
    private final IClientAuthService service;

    @Override
    public ClientDetails loadClientByClientId(String clientId) throws ClientRegistrationException {
        SkillFullClientDetails customClientDetails = Oauth2CommonUtils.toClientDetails(service.getByClientId(clientId));
        Map<String, Object> map = new HashMap<>();
        map.put("sdfsdf", "sdfsdfsdfsdf000000");
        customClientDetails.setAdditionalInformation(map);
        Oauth2LogUtils.setClientDetailInfo(customClientDetails);
        if (customClientDetails.getClientStatus() == 0) {
            throw new InvalidClientException(I18nUtil.get("JdbcClientDetailsService.clientIdNotEnabled", customClientDetails.getClientId()));
        } else if (customClientDetails.getClientStatus() == 2) {
            throw new InternalAuthenticationServiceException(I18nUtil.get("JdbcClientDetailsService.clientIdIsDisabled", customClientDetails.getClientId()));
        }
        return customClientDetails;
    }
}
