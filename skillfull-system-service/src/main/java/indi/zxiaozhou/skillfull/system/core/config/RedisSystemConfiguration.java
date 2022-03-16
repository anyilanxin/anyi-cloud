package indi.zxiaozhou.skillfull.system.core.config;

import indi.zxiaozhou.skillfull.corecommon.base.service.ICoreCommonService;
import indi.zxiaozhou.skillfull.coremvc.listener.RedisKeyDeleteEventMessageListener;
import indi.zxiaozhou.skillfull.system.core.config.listener.ConstantDeleteEventListener;
import indi.zxiaozhou.skillfull.system.core.config.listener.RouterDeleteEventListener;
import indi.zxiaozhou.skillfull.system.modules.manage.service.IManageSyncService;
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
    private final ICoreCommonService coreCommonService;


    @Bean
    public RedisKeyDeleteEventMessageListener routerInfoDeleteEventListener() {
        return new RouterDeleteEventListener(redisMessageListenerContainer, syncService);
    }


    @Bean
    public ConstantDeleteEventListener keyExpiredListener() {
        return new ConstantDeleteEventListener(redisMessageListenerContainer, coreCommonService);
    }

}
