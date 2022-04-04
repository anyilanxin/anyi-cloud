package indi.zxiaozhou.skillfull.message.core.config;

import indi.zxiaozhou.skillfull.corecommon.base.service.ICoreCommonService;
import indi.zxiaozhou.skillfull.message.core.config.listener.ConstantDeleteEventListener;
import indi.zxiaozhou.skillfull.message.core.config.listener.LoginExpiredEventListener;
import indi.zxiaozhou.skillfull.message.modules.websocket.model.WebSocketLinkModel;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;

import java.util.concurrent.ConcurrentHashMap;

/**
 * redis监听配置
 *
 * @author zxiaozhou
 * @date 2021-07-09 21:47
 * @since JDK1.8
 */

@Configuration
@RequiredArgsConstructor
public class RedisMessageConfiguration {
    private final RedisConnectionFactory redisConnectionFactory;
    private final ConcurrentHashMap<String, WebSocketLinkModel> senderMsgMap;
    private final ICoreCommonService coreCommonService;
    private final RedisMessageListenerContainer redisMessageListenerContainer;


    @Bean
    public ConstantDeleteEventListener constantDeleteEventListener() {
        return new ConstantDeleteEventListener(redisMessageListenerContainer, coreCommonService);
    }


    @Bean
    public LoginExpiredEventListener loginExpiredListener() {
        return new LoginExpiredEventListener(redisMessageListenerContainer, senderMsgMap);
    }


}
