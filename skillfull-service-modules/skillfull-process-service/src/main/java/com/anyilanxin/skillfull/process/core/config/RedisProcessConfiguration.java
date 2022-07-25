package com.anyilanxin.skillfull.process.core.config;

import com.anyilanxin.skillfull.coremvc.base.service.ICoreWebmvcService;
import com.anyilanxin.skillfull.process.core.config.listener.ConstantDeleteEventListener;
import lombok.RequiredArgsConstructor;
import org.camunda.bpm.model.bpmn.impl.BpmnParser;
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
public class RedisProcessConfiguration {
    private final RedisMessageListenerContainer redisMessageListenerContainer;
    private final ICoreWebmvcService coreCommonService;

    @Bean
    public ConstantDeleteEventListener keyExpiredListener() {
        return new ConstantDeleteEventListener(redisMessageListenerContainer, coreCommonService);
    }


    @Bean
    public BpmnParser bpmnParser() {
        return new BpmnParser();
    }
}
