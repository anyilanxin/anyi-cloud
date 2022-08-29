package com.anyilanxin.skillfull.process.core.config;

import lombok.RequiredArgsConstructor;
import org.camunda.bpm.model.bpmn.impl.BpmnParser;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * process配置
 *
 * @author zxiaozhou
 * @date 2021-07-09 21:47
 * @since JDK1.8
 */

@Configuration
@RequiredArgsConstructor
public class ProcessConfiguration {


    @Bean
    public BpmnParser bpmnParser() {
        return new BpmnParser();
    }
}
