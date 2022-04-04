package indi.zxiaozhou.skillfull.auth.core.config;

import indi.zxiaozhou.skillfull.auth.core.config.listener.AuthInfoDeleteEventListener;
import indi.zxiaozhou.skillfull.auth.core.config.listener.ConstantDeleteEventListener;
import indi.zxiaozhou.skillfull.auth.modules.rbac.service.IRbacPermissionService;
import indi.zxiaozhou.skillfull.corecommon.base.service.ICoreCommonService;
import indi.zxiaozhou.skillfull.coremvc.listener.RedisKeyDeleteEventMessageListener;
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
public class RedisAuthConfiguration {
    private final RedisMessageListenerContainer redisMessageListenerContainer;
    private final IRbacPermissionService permissionService;
    private final ICoreCommonService coreCommonService;


    @Bean
    public ConstantDeleteEventListener constantDeleteEventListener() {
        return new ConstantDeleteEventListener(redisMessageListenerContainer, coreCommonService);
    }


    @Bean
    public RedisKeyDeleteEventMessageListener authInfoDeleteEventListener() {
        return new AuthInfoDeleteEventListener(redisMessageListenerContainer, permissionService);
    }

}
