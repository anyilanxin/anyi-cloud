

package com.anyilanxin.skillfull.storage.core.config.listener;

import com.anyilanxin.anyicloud.coremvc.base.service.ICoreWebmvcService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;

/**
 * redis监听配置
 *
 * @author zxh
 * @date 2021-07-09 21:47
 * @since 1.0.0
 */
@Configuration
@RequiredArgsConstructor
public class RedisListenerConfiguration {
    private final RedisMessageListenerContainer redisMessageListenerContainer;
    private final ICoreWebmvcService coreCommonService;

    @Bean
    public ConstantDeleteEventListener constantDeleteEventListener() {
        return new ConstantDeleteEventListener(redisMessageListenerContainer, coreCommonService);
    }
}
