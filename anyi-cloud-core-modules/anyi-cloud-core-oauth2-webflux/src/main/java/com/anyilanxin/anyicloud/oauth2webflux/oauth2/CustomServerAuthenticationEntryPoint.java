

package com.anyilanxin.anyicloud.oauth2webflux.oauth2;

import static com.anyilanxin.anyicloud.corecommon.utils.I18nUtil.getLocalMessage;

import cn.hutool.json.JSONUtil;
import com.anyilanxin.anyicloud.corecommon.base.Result;
import com.anyilanxin.anyicloud.corecommon.constant.Status;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

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

/**
 * 自定义返回结果：没有登录或token过期时
 *
 * @author zxh
 * @date 2022-08-30 19:07
 * @since 1.0.0
 */
@Component
@Slf4j
public class CustomServerAuthenticationEntryPoint implements ServerAuthenticationEntryPoint {
    private static final Map<String, String> LOCAL = new HashMap<>(64);

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
