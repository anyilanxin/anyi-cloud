// +----------------------------------------------------------------------
// | SkillFull快速开发平台 [ SkillFull ]
// +----------------------------------------------------------------------
// | 版权所有 2020~2021 zxiaozhou
// +----------------------------------------------------------------------
// | 官方网站: https://skillfull.divisu.com
// +----------------------------------------------------------------------
// | 作者: zxiaozhou <z7630853@163.com>
// +----------------------------------------------------------------------
package com.anyilanxin.skillfull.oauth2common.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.DefaultAccessTokenConverter;

import java.util.Map;

/**
 * token转换器（用于控制jwt内容）
 *
 * @author zxiaozhou
 * @date 2022-02-14 04:55
 * @since JDK1.8
 */
@Slf4j
public class CustomAccessTokenConverter extends DefaultAccessTokenConverter {
    @Override
    public Map<String, ?> convertAccessToken(OAuth2AccessToken token, OAuth2Authentication authentication) {
        // 精简jwt内容防止过大
        Map<String, ?> stringMap = super.convertAccessToken(token, authentication);
        stringMap.remove("scope");
        stringMap.remove("authorities");
        stringMap.remove("token_query_name");
        stringMap.remove("bearer_token_header_name");
        return stringMap;
    }
}
