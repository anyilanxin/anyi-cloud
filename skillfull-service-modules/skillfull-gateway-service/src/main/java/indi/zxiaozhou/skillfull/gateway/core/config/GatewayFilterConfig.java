// +----------------------------------------------------------------------
// | SkillFull快速开发平台 [ SkillFull ]
// +----------------------------------------------------------------------
// | 版权所有 2020~2021 zxiaozhou
// +----------------------------------------------------------------------
// | 官方网站: https://www.divisu.com
// +----------------------------------------------------------------------
// | 作者: zxiaozhou <z7630853@163.com>
// +----------------------------------------------------------------------
package indi.zxiaozhou.skillfull.gateway.core.config;

import indi.zxiaozhou.skillfull.gateway.modules.filter.partial.post.CorsWebGatewayFilterFactory;
import indi.zxiaozhou.skillfull.gateway.modules.filter.partial.post.EncryptGatewayFilterFactory;
import indi.zxiaozhou.skillfull.gateway.modules.filter.partial.post.LogResponseGatewayFilterFactory;
import indi.zxiaozhou.skillfull.gateway.modules.filter.partial.post.TokenRefreshGatewayFilterFactory;
import indi.zxiaozhou.skillfull.gateway.modules.filter.partial.pre.AuthorizeGatewayFilterFactory;
import indi.zxiaozhou.skillfull.gateway.modules.filter.partial.pre.BlacklistGatewayFilterFactory;
import indi.zxiaozhou.skillfull.gateway.modules.filter.partial.pre.LogRequestGatewayFilterFactory;
import indi.zxiaozhou.skillfull.gateway.modules.filter.partial.pre.VerifySignGatewayFilterFactory;
import indi.zxiaozhou.skillfull.gateway.modules.filter.webfilter.CorsOptionsWebFilter;
import indi.zxiaozhou.skillfull.gateway.modules.filter.webfilter.TokenUnifiedWebFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.annotation.Bean;

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
    public TokenUnifiedWebFilter tokenUnifiedWebFilter() {
        return new TokenUnifiedWebFilter();
    }

    @Bean
    public CorsOptionsWebFilter corsOptionsWebFilter() {
        return new CorsOptionsWebFilter();
    }

    @Bean
    public TokenRefreshGatewayFilterFactory tokenRefreshGatewayFilterFactory() {
        return new TokenRefreshGatewayFilterFactory();
    }


    @Bean
    public AuthorizeGatewayFilterFactory authorizeGatewayFilterFactory() {
        return new AuthorizeGatewayFilterFactory();
    }


    @Bean
    public VerifySignGatewayFilterFactory verifySignGatewayFilterFactory() {
        return new VerifySignGatewayFilterFactory();
    }


    @Bean
    public DecryptGatewayFilterFactory decryptGatewayFilterFactory() {
        return new DecryptGatewayFilterFactory();
    }


    @Bean
    public EncryptGatewayFilterFactory encryptGatewayFilterFactory() {
        return new EncryptGatewayFilterFactory();
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

    @Bean
    public BlacklistGatewayFilterFactory blacklistGatewayFilterFactory() {
        return new BlacklistGatewayFilterFactory();
    }

}
