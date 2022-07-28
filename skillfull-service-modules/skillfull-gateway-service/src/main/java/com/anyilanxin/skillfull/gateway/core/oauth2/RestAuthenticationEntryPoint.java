package com.anyilanxin.skillfull.gateway.core.oauth2;

import cn.hutool.json.JSONUtil;
import com.anyilanxin.skillfull.corecommon.base.Result;
import com.anyilanxin.skillfull.corecommon.constant.Status;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.server.ServerAuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import static com.anyilanxin.skillfull.corecommon.utils.I18nUtil.getLocalMessage;

/**
 * 自定义返回结果：没有登录或token过期时
 *
 * @author Honghui [wanghonghui_work@163.com] 2021/3/16
 */
@Component
@Slf4j
public class RestAuthenticationEntryPoint implements ServerAuthenticationEntryPoint {
    private final static Map<String, String> LOCAL = new HashMap<>(64);

    static {
        LOCAL.put("Not Authenticated", "RestAuthenticationEntryPoint.NotAuthenticated");
    }

    @Override
    public Mono<Void> commence(ServerWebExchange exchange, AuthenticationException e) {
        log.error("------------RestAuthenticationEntryPoint------------>commence--->异常消息:\n{}", e.getMessage());
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(HttpStatus.UNAUTHORIZED);
        response.getHeaders().add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        Result<String> result = new Result<>(Status.ACCESS_ERROR, getLocalMessage(LOCAL, e.getMessage()));
        String body = JSONUtil.toJsonStr(result);
        DataBuffer buffer = response.bufferFactory().wrap(body.getBytes(StandardCharsets.UTF_8));
        return response.writeWith(Mono.just(buffer));
    }
}
