// +----------------------------------------------------------------------
// | SkillFull快速开发平台 [ SkillFull ]
// +----------------------------------------------------------------------
// | 版权所有 2020~2021 zxiaozhou
// +----------------------------------------------------------------------
// | 官方网站: https://www.divisu.com
// +----------------------------------------------------------------------
// | 作者: zxiaozhou <z7630853@163.com>
// +----------------------------------------------------------------------
package com.anyilanxin.skillfull.gateway.core.config;

import com.anyilanxin.skillfull.corecommon.constant.CommonCoreConstant;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.jsontype.impl.LaissezFaireSubTypeValidator;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.time.Duration;

import static java.util.Collections.singletonMap;

/**
 * redis配置
 *
 * @author zxiaozhou
 * @date 2020-08-28 10:23
 * @since JDK11
 */
@Configuration
@RequiredArgsConstructor
public class GatewayRedisCommonConfig {
    private final RedisConnectionFactory redisConnectionFactory;

    /**
     * redisTemplate配置
     *
     * @return RedisTemplate<String, Object> ${@link RedisTemplate<String,Object>}
     * @author zxiaozhou
     * @date 2020-06-30 17:47
     */
    @Bean
    @ConditionalOnMissingBean
    public RedisTemplate<String, Object> redisTemplate() {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        //首先解决key的序列化方式
        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
        RedisSerializer<String> redisSerializer = new StringRedisSerializer();
        redisTemplate.setValueSerializer(getSerializer());
        redisTemplate.setKeySerializer(stringRedisSerializer);
        redisTemplate.setHashKeySerializer(stringRedisSerializer);
        redisTemplate.setHashValueSerializer(redisSerializer);
        return redisTemplate;
    }


    /**
     * redisTemplate配置
     *
     * @return StringRedisTemplate ${@link StringRedisTemplate}
     * @author zxiaozhou
     * @date 2020-06-30 17:47
     */
    @Bean
    @ConditionalOnMissingBean
    public StringRedisTemplate stringRedisTemplate() {
        StringRedisTemplate stringRedisTemplate = new StringRedisTemplate();
        stringRedisTemplate.setValueSerializer(getSerializer());
        stringRedisTemplate.setConnectionFactory(redisConnectionFactory);
        return stringRedisTemplate;
    }


    /**
     * 缓存配置
     *
     * @return CacheManager ${@link CacheManager}
     * @author zxiaozhou
     * @date 2020-08-28 14:39
     */
    @Bean
    @ConditionalOnMissingBean
    public CacheManager cacheManager() {
        // 配置序列化
        RedisCacheConfiguration config = RedisCacheConfiguration.defaultCacheConfig().entryTtl(Duration.ofHours(1));
        RedisCacheConfiguration redisCacheConfiguration = config.serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(new StringRedisSerializer()))
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(getSerializer()));
        return RedisCacheManager.builder(RedisCacheWriter.lockingRedisCacheWriter(redisConnectionFactory)).cacheDefaults(redisCacheConfiguration)
                .withInitialCacheConfigurations(singletonMap(CommonCoreConstant.TEST_DEMO_CACHE, RedisCacheConfiguration.defaultCacheConfig().entryTtl(Duration.ofMinutes(5)).disableCachingNullValues()))
                .transactionAware().build();
    }


    @Bean
    @ConditionalOnMissingBean
    public RedisMessageListenerContainer redisMessageListenerContainer() {
        RedisMessageListenerContainer redisMessageListenerContainer = new RedisMessageListenerContainer();
        redisMessageListenerContainer.setConnectionFactory(redisConnectionFactory);
        return redisMessageListenerContainer;
    }


    private Jackson2JsonRedisSerializer<Object> getSerializer() {
        Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<>(Object.class);
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.activateDefaultTyping(LaissezFaireSubTypeValidator.instance, ObjectMapper.DefaultTyping.NON_FINAL);
        jackson2JsonRedisSerializer.setObjectMapper(objectMapper);
        return jackson2JsonRedisSerializer;
    }

}
