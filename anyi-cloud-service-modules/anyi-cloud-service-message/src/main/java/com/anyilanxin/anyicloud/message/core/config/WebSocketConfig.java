

package com.anyilanxin.anyicloud.message.core.config;

import com.anyilanxin.anyicloud.message.core.handler.WebSocketHandshakeHandler;
import com.anyilanxin.anyicloud.message.core.handler.WebSocketMainHandler;
import com.anyilanxin.anyicloud.message.strategy.afterconnection.AfterConnectionContent;
import com.anyilanxin.anyicloud.message.strategy.msgsubscribe.MsgSubscribeContent;
import com.anyilanxin.anyicloud.oauth2mvc.user.IGetLoginUserInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

/**
 * websocket配置
 *
 * @author zxh
 * @date 2021-01-08 12:14
 * @since 1.0.0
 */
@Configuration(proxyBeanMethods = false)
@EnableWebSocket
@RequiredArgsConstructor
public class WebSocketConfig implements WebSocketConfigurer {
    private final AfterConnectionContent afterConnectionContent;
    private final MsgSubscribeContent msgSubscribeContent;
    private final IGetLoginUserInfo loginUserInfo;

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(new WebSocketMainHandler(afterConnectionContent, msgSubscribeContent, loginUserInfo), "socket").setHandshakeHandler(new WebSocketHandshakeHandler()).setAllowedOrigins("*");
    }
}
