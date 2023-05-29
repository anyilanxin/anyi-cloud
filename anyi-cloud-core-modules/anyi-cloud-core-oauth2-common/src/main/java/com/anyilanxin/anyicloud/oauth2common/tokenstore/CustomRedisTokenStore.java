

package com.anyilanxin.anyicloud.oauth2common.tokenstore;

import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;

/**
 * token redis存储
 *
 * @author zxh
 * @date 2022-02-14 17:05
 * @since 1.0.0
 */
public class CustomRedisTokenStore extends RedisTokenStore {
    public CustomRedisTokenStore(RedisConnectionFactory connectionFactory) {
        super(connectionFactory);
    }


    public OAuth2Authentication readAuthentication(OAuth2AccessToken token) {
        OAuth2Authentication oAuth2Authentication = this.readAuthentication(token.getValue());
        return oAuth2Authentication;
    }
}
