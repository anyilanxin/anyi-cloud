package indi.zxiaozhou.skillfull.storage.core.config;

import indi.zxiaozhou.skillfull.corecommon.base.service.ICoreCommonService;
import indi.zxiaozhou.skillfull.storage.core.config.listener.ConstantDeleteEventListener;
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
public class RedisStorageConfiguration {
    private final ICoreCommonService coreCommonService;
    private final RedisMessageListenerContainer redisMessageListenerContainer;


    @Bean
    public ConstantDeleteEventListener keyExpiredListener() {
        return new ConstantDeleteEventListener(redisMessageListenerContainer, coreCommonService);
    }

}
