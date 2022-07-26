// +----------------------------------------------------------------------
// | SkillFull快速开发平台 [ SkillFull ]
// +----------------------------------------------------------------------
// | 版权所有 2020~2021 zxiaozhou
// +----------------------------------------------------------------------
// | 官方网站: https://www.divisu.com
// +----------------------------------------------------------------------
// | 作者: zxiaozhou <z7630853@163.com>
// +----------------------------------------------------------------------
package com.anyilanxin.skillfull.gateway.core.config;

import com.anyilanxin.skillfull.gateway.core.config.properties.CustomSecurityProperties;
import com.anyilanxin.skillfull.gateway.filter.partial.post.CorsWebGatewayFilterFactory;
import com.anyilanxin.skillfull.gateway.filter.partial.post.LogResponseGatewayFilterFactory;
import com.anyilanxin.skillfull.gateway.filter.partial.pre.AuthorizeGatewayFilterFactory;
import com.anyilanxin.skillfull.gateway.filter.partial.pre.LogRequestGatewayFilterFactory;
import com.anyilanxin.skillfull.gateway.filter.partial.pre.checkauth.AccessDecisionVoter;
import com.anyilanxin.skillfull.gateway.filter.webfilter.CorsOptionsWebFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.annotation.Bean;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
    public AuthorizeGatewayFilterFactory authorizeGatewayFilterFactory(final Map<String, AccessDecisionVoter<?>> decisionVoter,
                                                                       final CustomSecurityProperties securityProperties) {
        List<AccessDecisionVoter<?>> accessDecisionVoters = new ArrayList<>();
        decisionVoter.forEach((k, v) -> accessDecisionVoters.add(v));
        return new AuthorizeGatewayFilterFactory(accessDecisionVoters, securityProperties);
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
