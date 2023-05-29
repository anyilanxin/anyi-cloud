

package com.anyilanxin.anyicloud.gateway.filter.webfilter;

import com.anyilanxin.anyicloud.gateway.utils.CorsWebUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.cors.reactive.CorsUtils;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

/**
 * 跨域处理(只处理OPTIONS请求,其余交给其他过滤器处理，以便动态控制)
 *
 * @author zxh
 * @date 2021-08-19 11:24
 * @since 1.0.0
 */
@Slf4j
@Component
public class CorsOptionsWebFilter implements WebFilter {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        if (CorsUtils.isCorsRequest(request) && request.getMethod() == HttpMethod.OPTIONS) {
            ServerHttpResponse response = exchange.getResponse();
            response.setStatusCode(HttpStatus.OK);
            return CorsWebUtils.corsHandle(chain, exchange);
        }
        return chain.filter(exchange);
    }
}
