// +----------------------------------------------------------------------
// | SkillFull快速开发平台 [ SkillFull ]
// +----------------------------------------------------------------------
// | 版权所有 2020~2021 zxiaozhou
// +----------------------------------------------------------------------
// | 官方网站: https://www.divisu.com
// +----------------------------------------------------------------------
// | 作者: zxiaozhou <z7630853@163.com>
// +----------------------------------------------------------------------
package indi.zxiaozhou.skillfull.message.core.config;

import indi.zxiaozhou.skillfull.message.core.handler.WebSocketHandlerMapping;
import indi.zxiaozhou.skillfull.message.modules.websocket.model.WebSocketLinkModel;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.HandlerMapping;
import org.springframework.web.reactive.socket.server.support.WebSocketHandlerAdapter;

import java.util.concurrent.ConcurrentHashMap;

/**
 * websocket配置
 *
 * @author zxiaozhou
 * @date 2021-01-08 12:14
 * @since JDK11
 */

/**
 * websocket配置
 *
 * @author zxiaozhou
 * @date 2021-01-08 12:14
 * @since JDK11
 */
@Configuration
public class WebSocketConfig {

    /**
     * web socket mapping
     *
     * @return HandlerMapping ${@link HandlerMapping}
     * @author zxiaozhou
     * @date 2021-05-11 19:54
     */
    @Bean
    public HandlerMapping webSocketMapping() {
        return new WebSocketHandlerMapping();
    }

    /**
     * 存储web socket缓存(键:systemId)
     *
     * @return ConcurrentHashMap<String, SocketResult> ${@link ConcurrentHashMap<String, WebSocketLinkModel>}
     * @author zxiaozhou
     * @date 2021-01-25 02:48
     */
    @Bean
    public ConcurrentHashMap<String, WebSocketLinkModel> senderNewMsgMap() {
        return new ConcurrentHashMap<>(8);
    }


    @Bean
    public WebSocketHandlerAdapter handlerAdapter() {
        return new WebSocketHandlerAdapter();
    }
}
