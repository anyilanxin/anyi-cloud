package com.anyilanxin.skillfull.system.core.config;

import com.anyilanxin.skillfull.coremvc.base.service.ICoreWebmvcService;
import com.anyilanxin.skillfull.coreredis.listener.RedisKeyDeleteEventMessageListener;
import com.anyilanxin.skillfull.system.core.config.listener.ConstantDeleteEventListener;
import com.anyilanxin.skillfull.system.core.config.listener.RouterDeleteEventListener;
import com.anyilanxin.skillfull.system.modules.manage.service.IManageSyncService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;

/**
 * redis监听配置
 *
 * @author zxiaozhou
 * @date 2021-07-09 21:47
 * @since JDK1.8
 */

@Configuration
@RequiredArgsConstructor
public class RedisSystemConfiguration {
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
