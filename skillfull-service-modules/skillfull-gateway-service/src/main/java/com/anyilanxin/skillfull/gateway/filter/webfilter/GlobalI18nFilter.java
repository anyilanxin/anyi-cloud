package com.anyilanxin.skillfull.gateway.filter.webfilter;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.i18n.SimpleLocaleContext;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Locale;

/**
 * 处理网关国际化
 *
 * @author zhou
 * @date 2022-07-13 16:01
 * @since JDK11
 */
@Slf4j
@Component
public class GlobalI18nFilter implements GlobalFilter, Ordered {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        String acceptLanguage = exchange.getRequest().getHeaders().getFirst(HttpHeaders.ACCEPT_LANGUAGE);
        if (StringUtils.isNotBlank(acceptLanguage)) {
            String[] split = acceptLanguage.split("_");
            Locale locale;
            if (split.length > 1) {
                locale = new Locale(split[0], split[1]);
            } else {
                locale = new Locale(split[0]);
            }
            LocaleContextHolder.setLocaleContext(new SimpleLocaleContext(locale), true);
        }
        return chain.filter(exchange);
    }


    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE;
    }
}

