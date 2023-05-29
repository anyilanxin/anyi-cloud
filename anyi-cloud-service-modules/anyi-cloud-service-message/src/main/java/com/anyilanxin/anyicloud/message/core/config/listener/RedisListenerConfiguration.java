

package com.anyilanxin.anyicloud.message.core.config.listener;

import com.anyilanxin.anyicloud.coreredis.constant.RedisSubscribeConstant;
import com.anyilanxin.anyicloud.message.strategy.msgsubscribe.MsgSubscribeContent;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import org.springframework.data.redis.serializer.RedisSerializer;

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
    private final MsgSubscribeContent subscribeContent;

    @Bean
    RedisMessageListenerContainer container(RedisConnectionFactory connectionFactory) {
        RedisMessageListenerContainer redisMessageListenerContainer = new RedisMessageListenerContainer();
        redisMessageListenerContainer.setConnectionFactory(connectionFactory);

        MessageListenerAdapter messageLogListenerAdapter = messageLogListenerAdapter();
        messageLogListenerAdapter.setSerializer(RedisSerializer.string());
        redisMessageListenerContainer.addMessageListener(messageLogListenerAdapter, new ChannelTopic(RedisSubscribeConstant.MESSAGE_SOCKET_HANDLE));
        return redisMessageListenerContainer;
    }


    @Bean
    MessageListenerAdapter messageLogListenerAdapter() {
        return new MessageListenerAdapter(subscribeContent, "socketMsgHandle");
    }


    @Bean
    TokenExpirationEventListener tokenExpirationEventListener(RedisConnectionFactory connectionFactory) {
        return new TokenExpirationEventListener(container(connectionFactory));
    }
}
