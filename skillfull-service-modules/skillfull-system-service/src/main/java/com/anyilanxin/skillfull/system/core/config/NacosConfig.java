package com.anyilanxin.skillfull.system.core.config;

import com.alibaba.cloud.nacos.NacosDiscoveryProperties;
import com.alibaba.nacos.api.NacosFactory;
import com.alibaba.nacos.api.exception.NacosException;
import com.alibaba.nacos.api.naming.NamingMaintainService;
import com.alibaba.nacos.api.naming.NamingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * nacos配置
 *
 * @author zxiaozhou
 * @date 2021-01-28 17:51
 * @since JDK11
 */
@Component
@Slf4j
@RequiredArgsConstructor
public class NacosConfig {
    private final NacosDiscoveryProperties properties;

    /**
     * 初始化NamingService
     *
     * @return NamingService ${@link NamingService}
     * @author zxiaozhou
     * @date 2021-01-28 18:16
     */
    @Bean
    public NamingService getNamingService() throws NacosException {
        return NacosFactory.createNamingService(properties.getNacosProperties());
    }


    /**
     * 初始化NamingMaintainService
     *
     * @return NamingMaintainService ${@link NamingMaintainService}
     * @author zxiaozhou
     * @date 2021-01-28 18:16
     */
    @Bean
    public NamingMaintainService getNamingMaintainService() throws NacosException {
        return NacosFactory.createMaintainService(properties.getNacosProperties());
    }
}
