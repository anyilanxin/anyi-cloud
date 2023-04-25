/**
 * Copyright (c) 2021-2022 ZHOUXUANHONG(安一老厨)<anyilanxin@aliyun.com>
 *
 * AnYi Cloud Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * AnYi Cloud 采用APACHE LICENSE 2.0开源协议，您在使用过程中，需要注意以下几点：
 *
 * 1.请不要删除和修改根目录下的LICENSE文件。
 * 2.请不要删除和修改 AnYi Cloud 源码头部的版权声明。
 * 3.请保留源码和相关描述文件的项目出处，作者声明等。
 * 4.分发源码时候，请注明软件出处 https://github.com/anyilanxin/anyi-cloud
 * 5.在修改包名，模块名称，项目代码等时，请注明软件出处 https://github.com/anyilanxin/anyi-cloud
 * 6.若您的项目无法满足以上几点，可申请商业授权
 */
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
