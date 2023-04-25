package com.anyilanxin.skillfull.corewebflux.config;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * @author zxiaozhou
 * @date 2021-06-16 08:05
 * @since JDK1.8
 */
@AutoConfiguration
public class CoreWebFluxCommonConfig {


    @Bean
    @LoadBalanced
    @ConditionalOnMissingBean
    public WebClient.Builder webClientBuilder() {
        return WebClient.builder();
    }
}
