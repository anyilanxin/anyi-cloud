package com.anyilanxin.skillfull.auth.oauth2.store.code;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.oauth2.common.exceptions.InvalidGrantException;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.code.RandomValueAuthorizationCodeServices;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * 内存存储code
 *
 * @author zxiaozhou
 * @date 2022-02-13 01:11
 * @since JDK1.8
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
