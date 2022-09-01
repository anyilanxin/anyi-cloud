package com.anyilanxin.skillfull.gateway.core.config.listener;

import com.anyilanxin.skillfull.coreredis.constant.RedisSubscribeConstant;
import com.anyilanxin.skillfull.corewebflux.base.service.ICoreWebfluxService;
import com.anyilanxin.skillfull.gateway.core.handler.RedisSubscribeListenerHandle;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.ReactiveRedisConnectionFactory;
import org.springframework.data.redis.connection.ReactiveSubscription;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.ReactiveRedisMessageListenerContainer;
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
public class RedisListenerConfiguration {
    private final RedisMessageListenerContainer redisMessageListenerContainer;
    private final ICoreWebfluxService coreCommonService;
    private final RedisSubscribeListenerHandle redisSubscribeListenerHandle;


    @Bean
    public ConstantDeleteEventListener constantDeleteEventListener() {
        return new ConstantDeleteEventListener(redisMessageListenerContainer, coreCommonService);
    }


    @Bean
    ReactiveRedisMessageListenerContainer container(ReactiveRedisConnectionFactory connectionFactory) {
        ReactiveRedisMessageListenerContainer container = new ReactiveRedisMessageListenerContainer(connectionFactory);
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            container.destroyLater().subscribe();
        }));
        container.receive(reloadTopic())
                .map(ReactiveSubscription.Message::getMessage)
                .subscribe(redisSubscribeListenerHandle::reloadRouter);
        container.receive(updateTopic())
                .map(ReactiveSubscription.Message::getMessage)
                .subscribe(redisSubscribeListenerHandle::updateRouter);
        container.receive(deleteTopic())
                .map(ReactiveSubscription.Message::getMessage)
                .subscribe(redisSubscribeListenerHandle::deleteRouter);
        return container;
    }


    @Bean
    public ChannelTopic reloadTopic() {
        return new ChannelTopic(RedisSubscribeConstant.GATEWAY_ROUTER_INFO_RELOAD);
    }


    @Bean
    public ChannelTopic updateTopic() {
        return new ChannelTopic(RedisSubscribeConstant.GATEWAY_ROUTER_INFO_UPDATE);
    }


    @Bean
    public ChannelTopic deleteTopic() {
        return new ChannelTopic(RedisSubscribeConstant.GATEWAY_ROUTER_INFO_DELETE);
    }


}
