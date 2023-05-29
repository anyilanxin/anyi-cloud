

package com.anyilanxin.anyicloud.system.core.config.listener;

import com.anyilanxin.anyicloud.coremvc.base.service.ICoreWebmvcService;
import com.anyilanxin.anyicloud.coreredis.listener.RedisKeyDeleteEventMessageListener;
import com.anyilanxin.anyicloud.system.modules.manage.service.IManageSyncService;
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
    private final IManageSyncService syncService;
    private final ICoreWebmvcService coreCommonService;

    @Bean
    public RedisKeyDeleteEventMessageListener routerInfoDeleteEventListener() {
        return new RouterDeleteEventListener(redisMessageListenerContainer, syncService);
    }


    @Bean
    public ConstantDeleteEventListener keyExpiredListener() {
        return new ConstantDeleteEventListener(redisMessageListenerContainer, coreCommonService);
    }
}
