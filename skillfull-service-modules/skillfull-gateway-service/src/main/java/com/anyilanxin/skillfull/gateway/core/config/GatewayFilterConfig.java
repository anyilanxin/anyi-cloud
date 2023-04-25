package com.anyilanxin.skillfull.gateway.core.config;

import com.anyilanxin.skillfull.gateway.core.config.properties.CustomSecurityProperties;
import com.anyilanxin.skillfull.gateway.filter.partial.post.CorsWebGatewayFilterFactory;
import com.anyilanxin.skillfull.gateway.filter.partial.post.LogResponseGatewayFilterFactory;
import com.anyilanxin.skillfull.gateway.filter.partial.pre.AuthorizeGatewayFilterFactory;
import com.anyilanxin.skillfull.gateway.filter.partial.pre.LogRequestGatewayFilterFactory;
import com.anyilanxin.skillfull.gateway.filter.webfilter.CorsOptionsWebFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.util.AntPathMatcher;

/**
 * 网关过滤器配置
 *
 * @author zxiaozhou
 * @date 2021-06-17 11:58
 * @since JDK1.8
 */
@SpringBootConfiguration
@RequiredArgsConstructor
public class GatewayFilterConfig {


    @Bean
    public CorsOptionsWebFilter corsOptionsWebFilter() {
        return new CorsOptionsWebFilter();
    }


    @Bean
    public AuthorizeGatewayFilterFactory authorizeGatewayFilterFactory(final CustomSecurityProperties securityProperties,
                                                                       final AntPathMatcher antPathMatcher) {
        return new AuthorizeGatewayFilterFactory(securityProperties, antPathMatcher);
    }


    @Bean
    public LogRequestGatewayFilterFactory logRequestGatewayFilterFactory() {
        return new LogRequestGatewayFilterFactory();
    }


    @Bean
    public LogResponseGatewayFilterFactory logResponseGatewayFilterFactory() {
        return new LogResponseGatewayFilterFactory();
    }


    @Bean
    public CorsWebGatewayFilterFactory corsWebGatewayFilterFactory() {
        return new CorsWebGatewayFilterFactory();
    }
}
