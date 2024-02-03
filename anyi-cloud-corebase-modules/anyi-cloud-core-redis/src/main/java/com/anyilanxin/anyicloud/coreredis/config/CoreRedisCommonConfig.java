/*
 * Copyright (c) 2023-present ZHOUXUANHONG(安一老厨)<anyilanxin@aliyun.com>
 *
 * AnYi Cloud Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * AnYi Cloud 采用APACHE LICENSE 2.0开源协议，您在使用过程中，需要注意以下几点：
 *   1.请不要删除和修改根目录下的LICENSE.txt文件；
 *   2.请不要删除和修改 AnYi Cloud 源码头部的版权声明；
 *   3.请保留源码和相关描述文件的项目出处，作者声明等；
 *   4.分发源码时候，请注明软件出处 https://github.com/anyilanxin/anyi-cloud；
 *   5.在修改包名，模块名称，项目代码等时，请注明软件出处 https://github.com/anyilanxin/anyi-cloud；
 *   6.本软件不允许在国家法律规定范围外使用，如出现违法行为原作者本人不承担任何法律风险；
 *   7.进行商用时，不得基于AnYi Cloud的基础，修改包装而成一个与AnYi Cloud EE、AnYi Zeebe EE、AnYi Standalone EE功能类似的程序，进行销售或发布，参与同类软件产品市场的竞争；
 *   8.本软件使用的第三方依赖皆为开源软件，如需要修改第三方源码请遵循第三方源码附带开源协议；
 *   9.本软件流程部分请遵循camunda开源协议：
 *     https://docs.camunda.org/manual/latest/introduction/third-party-libraries
 *     https://github.com/camunda/camunda-bpm-platform/blob/master/LICENSE
 *   10.若您的项目无法满足以上几点，可申请商业授权。
 */

package com.anyilanxin.anyicloud.coreredis.config;

import com.anyilanxin.anyicloud.corecommon.constant.CommonCoreConstant;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.jsontype.impl.LaissezFaireSubTypeValidator;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.connection.ReactiveRedisConnectionFactory;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.ReactiveStringRedisTemplate;
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
 * @author zxh
 * @date 2020-08-28 10:23
 * @since 1.0.0
 */
@AutoConfiguration
@RequiredArgsConstructor
public class CoreRedisCommonConfig {
    private final RedisConnectionFactory redisConnectionFactory;

    /**
     * redisTemplate配置
     *
     * @return RedisTemplate<String, Object> ${@link RedisTemplate<String,Object>}
     * @author zxh
     * @date 2020-06-30 17:47
     */
    @Bean
    public RedisTemplate<String, Object> redisTemplate() {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        // 首先解决key的序列化方式
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
     * @author zxh
     * @date 2020-06-30 17:47
     */
    @Bean
    public StringRedisTemplate stringRedisTemplate() {
        return new StringRedisTemplate(redisConnectionFactory);
    }


    /**
     * 缓存配置
     *
     * @return CacheManager ${@link CacheManager}
     * @author zxh
     * @date 2020-08-28 14:39
     */
    @Bean
    public CacheManager cacheManager() {
        // 配置序列化
        RedisCacheConfiguration config = RedisCacheConfiguration.defaultCacheConfig().entryTtl(Duration.ofHours(1));
        RedisCacheConfiguration redisCacheConfiguration = config.serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(new StringRedisSerializer())).serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(getSerializer()));
        return RedisCacheManager.builder(RedisCacheWriter.lockingRedisCacheWriter(redisConnectionFactory)).cacheDefaults(redisCacheConfiguration).withInitialCacheConfigurations(singletonMap(CommonCoreConstant.TEST_DEMO_CACHE, RedisCacheConfiguration.defaultCacheConfig().entryTtl(Duration.ofMinutes(5)).disableCachingNullValues())).transactionAware().build();
    }


    @Bean
    public RedisMessageListenerContainer redisMessageListenerContainer() {
        RedisMessageListenerContainer redisMessageListenerContainer = new RedisMessageListenerContainer();
        redisMessageListenerContainer.setConnectionFactory(redisConnectionFactory);
        return redisMessageListenerContainer;
    }


    private Jackson2JsonRedisSerializer<Object> getSerializer() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.activateDefaultTyping(LaissezFaireSubTypeValidator.instance, ObjectMapper.DefaultTyping.NON_FINAL);
        return new Jackson2JsonRedisSerializer<>(objectMapper, Object.class);
    }


    @Bean
    public ReactiveStringRedisTemplate reactiveStringRedisTemplate(ReactiveRedisConnectionFactory factory) {
        RedisSerializer<String> serializer = new StringRedisSerializer();
        RedisSerializationContext<String, String> serializationContext = RedisSerializationContext.<String, String>newSerializationContext().key(serializer).value(serializer).hashKey(serializer).hashValue(serializer).build();
        return new ReactiveStringRedisTemplate(factory, serializationContext);
    }
}
