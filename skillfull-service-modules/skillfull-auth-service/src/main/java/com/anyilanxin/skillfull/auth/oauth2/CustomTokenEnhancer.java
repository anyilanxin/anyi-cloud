package com.anyilanxin.skillfull.auth.oauth2;

import cn.hutool.core.collection.CollUtil;
import com.anyilanxin.skillfull.auth.core.constant.AuthCommonConstant;
import com.anyilanxin.skillfull.corecommon.constant.AuthConstant;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;

import java.util.HashMap;
import java.util.Map;

/**
 * token增强
 *
 * @author zxiaozhou
 * @date 2022-02-14 03:05
 * @since JDK1.8
 */
@Slf4j
public class CustomTokenEnhancer implements TokenEnhancer {
    @Override
    public OAuth2AccessToken enhance(OAuth2AccessToken oAuth2AccessToken, OAuth2Authentication oAuth2Authentication) {
        if (oAuth2AccessToken instanceof DefaultOAuth2AccessToken) {
            DefaultOAuth2AccessToken token = (DefaultOAuth2AccessToken) oAuth2AccessToken;
            Map<String, Object> additionalInformation = new HashMap<>();
            // 如果非客户端模式则加入端信息
            if (!oAuth2Authentication.isClientOnly()) {
                Map<String, String> requestParameters = oAuth2Authentication.getOAuth2Request().getRequestParameters();
                if (CollUtil.isNotEmpty(requestParameters)) {
                    String endpoint = requestParameters.get(AuthCommonConstant.ENDPOINT_REQUEST_KEY);
                    if (StringUtils.isBlank(endpoint)) {
                        endpoint = AuthCommonConstant.ENDPOINT_DEFAULT;
                    }
                    additionalInformation.put(AuthCommonConstant.ENDPOINT_REQUEST_KEY, endpoint);
                }
            }
            additionalInformation.put("token_query_name", AuthConstant.ACCESS_TOKEN_QUERY_NAME);
            additionalInformation.put("bearer_token_header_name", AuthConstant.BEARER_TOKEN_HEADER_NAME);
            token.setAdditionalInformation(additionalInformation);
            return token;
        }
        return oAuth2AccessToken;
    }


}
