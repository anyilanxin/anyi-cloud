package com.anyilanxin.skillfull.gateway.utils;

import cn.hutool.core.collection.CollUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.cors.reactive.CorsUtils;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

/**
 * 跨域处理工具类
 *
 * @author zxiaozhou
 * @date 2021-08-19 09:15
 * @since JDK1.8
 */
@Slf4j
public class CorsWebUtils {

    /**
     * 跨域处理工具(OPTIONS请求)
     *
     * @param exchange ${@link ServerWebExchange}
     * @return Mono<Void> ${@link Mono<Void>}
     * @author zxiaozhou
     * @date 2021-07-14 22:22
     */
    public static Mono<Void> corsHandle(WebFilterChain chain, ServerWebExchange exchange) {
        ServerHttpRequest request = exchange.getRequest();
        if (CorsUtils.isCorsRequest(request)) {
            ServerHttpResponse response = exchange.getResponse();
            addCorsHeaders(exchange);
            if (request.getMethod() == HttpMethod.OPTIONS) {
                response.setStatusCode(HttpStatus.OK);
                return Mono.empty();
            }
        }
        return chain.filter(exchange);
    }


    /**
     * 跨域处理工具(非OPTIONS请求)
     *
     * @param exchange ${@link ServerWebExchange}
     * @return Mono<Void> ${@link Mono<Void>}
     * @author zxiaozhou
     * @date 2021-07-14 22:22
     */
    public static Mono<Void> corsHandle(GatewayFilterChain chain, ServerWebExchange exchange) {
        ServerHttpRequest request = exchange.getRequest();
        if (CorsUtils.isCorsRequest(request)) {
            addCorsHeaders(exchange);
        }
        return chain.filter(exchange);
    }


    public static void addCorsHeaders(ServerWebExchange exchange) {
        ServerHttpRequest request = exchange.getRequest();
        HttpHeaders requestHeaders = request.getHeaders();
        ServerHttpResponse response = exchange.getResponse();
        HttpHeaders headers = response.getHeaders();
        headers.setAccessControlAllowOrigin("*");
        if (CollUtil.isNotEmpty(requestHeaders.getAccessControlRequestHeaders())) {
            headers.setAccessControlAllowHeaders(requestHeaders.getAccessControlRequestHeaders());
        }
        if (CollUtil.isNotEmpty(requestHeaders.getAccessControlAllowMethods())) {
            headers.setAccessControlAllowMethods(requestHeaders.getAccessControlAllowMethods());
        }
        headers.setAccessControlAllowCredentials(true);
        if (CollUtil.isNotEmpty(requestHeaders.getAccessControlExposeHeaders())) {
            headers.setAccessControlExposeHeaders(requestHeaders.getAccessControlExposeHeaders());
        }
        headers.setAccessControlMaxAge(18000L);
    }
}
