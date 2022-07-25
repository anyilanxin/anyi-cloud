// +----------------------------------------------------------------------
// | SkillFull快速开发平台 [ SkillFull ]
// +----------------------------------------------------------------------
// | 版权所有 2020~2021 zxiaozhou
// +----------------------------------------------------------------------
// | 官方网站: https://www.divisu.com
// +----------------------------------------------------------------------
// | 作者: zxiaozhou <z7630853@163.com>
// +----------------------------------------------------------------------
package com.anyilanxin.skillfull.auth.modules.detail.servive.impl;

import com.anyilanxin.skillfull.auth.modules.login.service.IClientAuthService;
import com.anyilanxin.skillfull.corecommon.base.model.system.ClientAndResourceAuthModel;
import com.anyilanxin.skillfull.corecommon.utils.I18nUtil;
import com.anyilanxin.skillfull.oauth2common.authinfo.SkillFullClientDetails;
import com.anyilanxin.skillfull.oauth2common.utils.Oauth2CommonUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.oauth2.common.exceptions.InvalidClientException;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.ClientRegistrationException;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * 数据库获取客户端信息
 *
 * @author zxiaozhou
 * @date 2022-02-11 09:45
 * @since JDK1.8
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class JdbcClientDetailsService implements ClientDetailsService {
    private final IClientAuthService service;


    @Override
    public ClientDetails loadClientByClientId(String clientId) throws ClientRegistrationException {
        ClientAndResourceAuthModel data = service.getByClientId(clientId);
        if (data.getClientStatus() == 0) {
            throw new InvalidClientException(I18nUtil.get("JdbcClientDetailsService.clientIdNotEnabled", data.getClientId()));
        } else if (data.getClientStatus() == 2) {
            throw new InternalAuthenticationServiceException(I18nUtil.get("JdbcClientDetailsService.clientIdIsDisabled", data.getClientId()));
        }
        SkillFullClientDetails customClientDetails = Oauth2CommonUtils.toClientDetails(data);
        Map<String, Object> map = new HashMap<>();
        map.put("sdfsdf", "sdfsdfsdfsdf000000");
        customClientDetails.setAdditionalInformation(map);
        return customClientDetails;
    }
}
