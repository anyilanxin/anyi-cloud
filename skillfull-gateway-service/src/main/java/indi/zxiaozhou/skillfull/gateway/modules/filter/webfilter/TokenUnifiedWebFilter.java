// +----------------------------------------------------------------------
// | SkillFull快速开发平台 [ SkillFull ]
// +----------------------------------------------------------------------
// | 版权所有 2020~2021 zxiaozhou
// +----------------------------------------------------------------------
// | 官方网站: https://www.divisu.com
// +----------------------------------------------------------------------
// | 作者: zxiaozhou <z7630853@163.com>
// +----------------------------------------------------------------------
package indi.zxiaozhou.skillfull.gateway.modules.filter.webfilter;

import indi.zxiaozhou.skillfull.gateway.utils.GatewayTokenUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

/**
 * token统一web filter过滤器
 *
 * @author zxiaozhou
 * @date 2021-08-19 11:21
 * @since JDK1.8
 */
@Slf4j
public class TokenUnifiedWebFilter implements WebFilter {
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        return chain.filter(exchange.mutate().request(GatewayTokenUtils.unifiedToken(exchange)).build());
    }
}
