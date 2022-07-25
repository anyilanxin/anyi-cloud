// +----------------------------------------------------------------------
// | SkillFull快速开发平台 [ SkillFull ]
// +----------------------------------------------------------------------
// | 版权所有 2020~2021 zxiaozhou
// +----------------------------------------------------------------------
// | 官方网站: https://www.divisu.com
// +----------------------------------------------------------------------
// | 作者: zxiaozhou <z7630853@163.com>
// +----------------------------------------------------------------------
package com.anyilanxin.skillfull.corecommon.config;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.loadbalancer.annotation.LoadBalancerClients;
import org.springframework.cloud.loadbalancer.core.ReactorLoadBalancer;
import org.springframework.cloud.loadbalancer.core.ServiceInstanceListSupplier;
import org.springframework.cloud.loadbalancer.support.LoadBalancerClientFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;

/**
 * LoadBalanced注入
 *
 * @author zxiaozhou
 * @date 2021-12-30 19:04
 * @since JDK1.8
 */

@AutoConfiguration
@LoadBalancerClients(defaultConfiguration = CustomLoadBalancerConfiguration.class)
public class CustomLoadBalancerConfiguration {

    /**
     * 灰度策略
     *
     * @param environment               ${@link Environment}
     * @param loadBalancerClientFactory ${@link LoadBalancerClientFactory}
     * @return ReactorLoadBalancer<ServiceInstance> ${@link ReactorLoadBalancer<ServiceInstance>}
     * @author zxiaozhou
     * @date 2021-12-31 09:19
     */
    @Bean
    ReactorLoadBalancer<ServiceInstance> randomLoadBalancer(Environment environment, LoadBalancerClientFactory loadBalancerClientFactory) {
        String name = environment.getProperty(LoadBalancerClientFactory.PROPERTY_NAME);
        return new GrayRoundRobinLoadBalancer(loadBalancerClientFactory.getLazyProvider(name, ServiceInstanceListSupplier.class), name, environment);
    }


}
