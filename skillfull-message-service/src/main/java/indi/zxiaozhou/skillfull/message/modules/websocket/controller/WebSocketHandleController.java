// +----------------------------------------------------------------------
// | SkillFull快速开发平台 [ SkillFull ]
// +----------------------------------------------------------------------
// | 版权所有 2020~2021 zxiaozhou
// +----------------------------------------------------------------------
// | 官方网站: https://www.divisu.com
// +----------------------------------------------------------------------
// | 作者: zxiaozhou <z7630853@163.com>
// +----------------------------------------------------------------------
package indi.zxiaozhou.skillfull.message.modules.websocket.controller;

import indi.zxiaozhou.skillfull.message.core.annotation.WebSocketController;
import indi.zxiaozhou.skillfull.message.core.annotation.WebSocketMapping;
import indi.zxiaozhou.skillfull.message.modules.websocket.handle.IWebSocketHandler;
import indi.zxiaozhou.skillfull.message.modules.websocket.model.WebSocketLinkModel;
import indi.zxiaozhou.skillfull.message.modules.websocket.service.IWebSocketMsgService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.reactive.socket.WebSocketSession;
import reactor.core.publisher.Mono;

import javax.validation.constraints.NotNull;
import java.util.concurrent.ConcurrentHashMap;

/**
 * web socket处理控制层
 *
 * @author zxiaozhou
 * @date 2021-05-11 19:15
 * @since JDK1.8
 */
@RequiredArgsConstructor
@WebSocketController
@WebSocketMapping(value = "/websocket/msg")
public class WebSocketHandleController implements IWebSocketHandler {
    private final IWebSocketMsgService socketMsgService;
    private final ConcurrentHashMap<String, WebSocketLinkModel> senderNewMsgMap;


    @Override
    public Mono<Void> handle(@NotNull WebSocketSession session) {
        return handle(session, socketMsgService::onMessage, senderNewMsgMap);
    }
}
