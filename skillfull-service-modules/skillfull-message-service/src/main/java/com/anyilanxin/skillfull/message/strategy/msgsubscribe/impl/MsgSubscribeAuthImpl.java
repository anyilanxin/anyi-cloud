package com.anyilanxin.skillfull.message.strategy.msgsubscribe.impl;

import com.anyilanxin.skillfull.message.core.constant.impl.WebSocketSessionType;
import com.anyilanxin.skillfull.message.strategy.msgsubscribe.IMsgSubscribeStrategy;
import com.anyilanxin.skillfull.message.strategy.msgsubscribe.mapstruct.SocketMsgModelCopyMap;
import com.anyilanxin.skillfull.messagerpc.constant.SocketMessageEventContent;
import com.anyilanxin.skillfull.messagerpc.model.AuthMsgModel;
import com.anyilanxin.skillfull.messagerpc.model.SocketMsgModel;
import com.anyilanxin.skillfull.messagerpc.model.SubscribeMsgModel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 鉴权消息
 *
 * @author zxiaozhou
 * @date 2022-08-27 15:15
 * @since JDK11
 */
@Component(value = SocketMessageEventContent.AUTH_EVENT)
@Slf4j
@RequiredArgsConstructor
public class MsgSubscribeAuthImpl implements IMsgSubscribeStrategy {
    private final SocketMsgModelCopyMap copyMap;

    @Override
    public void handleMsg(SubscribeMsgModel subscribeMsgModel, ConcurrentHashMap<String, WebSocketSession> sessions) {
        SocketMsgModel socketMsgModel = copyMap.bToA(subscribeMsgModel);
        log.info("------------------收到鉴权消息------>handleMsg:\n{}", socketMsgModel);
        Object data = socketMsgModel.getData();
        if (Objects.nonNull(data)) {
            AuthMsgModel msgModel = (AuthMsgModel) data;
            sessions.forEach((k, v) -> {
                String token = v.getAttributes().get(WebSocketSessionType.TOKEN.getType()).toString();
                if (token.equals(msgModel.getToken())) {
                    CloseStatus closeStatus = new CloseStatus(msgModel.getType().getCode(), msgModel.getType().getMessage());
                    try {
                        v.close(closeStatus);
                    } catch (IOException e) {
                        log.error("------------------发送消息失败------handleMsg--->\n参数:\n{}\n异常消息:\n{}", msgModel, e.getMessage());
                    }
                }
            });
        }
    }
}
