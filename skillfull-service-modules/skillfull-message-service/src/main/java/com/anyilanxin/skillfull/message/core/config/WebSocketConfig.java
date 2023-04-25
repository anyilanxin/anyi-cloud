package com.anyilanxin.skillfull.message.core.config;

import com.anyilanxin.skillfull.message.core.handler.WebSocketHandshakeHandler;
import com.anyilanxin.skillfull.message.core.handler.WebSocketMainHandler;
import com.anyilanxin.skillfull.message.strategy.afterconnection.AfterConnectionContent;
import com.anyilanxin.skillfull.message.strategy.msgsubscribe.MsgSubscribeContent;
import com.anyilanxin.skillfull.oauth2mvc.user.IGetLoginUserInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

/**
 * websocket配置
 *
 * @author zxiaozhou
 * @date 2021-01-08 12:14
 * @since JDK11
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
        registry.addHandler(new WebSocketMainHandler(afterConnectionContent, msgSubscribeContent, loginUserInfo), "socket")
                .setHandshakeHandler(new WebSocketHandshakeHandler())
                .setAllowedOrigins("*");
    }


}
