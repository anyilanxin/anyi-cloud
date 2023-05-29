

package com.anyilanxin.anyicloud.auth.oauth2.store.code;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.oauth2.common.exceptions.InvalidGrantException;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.code.RandomValueAuthorizationCodeServices;

/**
 * 内存存储code
 *
 * @author zxh
 * @date 2022-02-13 01:11
 * @since 1.0.0
 */
public class RedisAuthorizationCodeServices extends RandomValueAuthorizationCodeServices {
    private final RedisTemplate<String, Object> redisTemplate;
    private int validitySeconds = 60 * 5; // default 5 minutes.

    public RedisAuthorizationCodeServices(RedisTemplate<String, Object> redisTemplate) {
        super();
        this.redisTemplate = redisTemplate;
    }


    @Override
    protected void store(String code, OAuth2Authentication authentication) {
        redisTemplate.opsForValue().set(code, authentication, validitySeconds, TimeUnit.SECONDS);
    }


    @Override
    protected OAuth2Authentication remove(String code) {
        Object object = redisTemplate.opsForValue().get(code);
        if (Objects.isNull(object)) {
            throw new InvalidGrantException("code无效或已经过期: " + code);
        }
        return (OAuth2Authentication) object;
    }


    public void setValiditySeconds(int validitySeconds) {
        this.validitySeconds = validitySeconds;
    }
}
