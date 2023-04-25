package com.anyilanxin.skillfull.oauth2common.config;

import com.anyilanxin.skillfull.oauth2common.serializer.FastjsonRedisTokenStoreSerializationStrategy;
import com.anyilanxin.skillfull.oauth2common.tokenstore.CustomAuthenticationKeyGenerator;
import com.anyilanxin.skillfull.oauth2common.tokenstore.CustomRedisTokenStore;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.provider.token.AccessTokenConverter;
import org.springframework.security.oauth2.provider.token.TokenStore;

import static com.anyilanxin.skillfull.corecommon.constant.CoreCommonCacheConstant.AUTH_PREFIX;

/**
 * token store配置
 *
 * @author zxiaozhou
 * @date 2022-02-19 18:10
 * @since JDK1.8
 */
@AutoConfiguration
@RequiredArgsConstructor
public class Oauth2CommonConfig {
    private final RedisConnectionFactory redisConnectionFactory;


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    /**
     * token存储配置
     */
    @Bean
    public TokenStore tokenStore() {
        CustomRedisTokenStore redisTokenStore = new CustomRedisTokenStore(redisConnectionFactory);
        redisTokenStore.setAuthenticationKeyGenerator(new CustomAuthenticationKeyGenerator());
        redisTokenStore.setSerializationStrategy(new FastjsonRedisTokenStoreSerializationStrategy());
        redisTokenStore.setPrefix(AUTH_PREFIX);
        return redisTokenStore;
    }

    @Bean
    public AccessTokenConverter tokenConverter() {
        return new CustomAccessTokenConverter();
    }


}
