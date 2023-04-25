package com.anyilanxin.skillfull.message.strategy.msgsubscribe.impl;

import com.anyilanxin.skillfull.message.core.constant.impl.WebSocketSessionType;
import com.anyilanxin.skillfull.message.strategy.msgsubscribe.IMsgSubscribeStrategy;
import com.anyilanxin.skillfull.message.strategy.msgsubscribe.mapstruct.SocketMsgModelCopyMap;
import com.anyilanxin.skillfull.message.utils.WsUtils;
import com.anyilanxin.skillfull.messagerpc.constant.SocketMessageEventContent;
import com.anyilanxin.skillfull.messagerpc.model.SocketMsgModel;
import com.anyilanxin.skillfull.messagerpc.model.SubscribeMsgModel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketSession;

import java.util.concurrent.ConcurrentHashMap;

/**
 * 上下线消息
 *
 * @author zxiaozhou
 * @date 2022-08-27 15:15
 * @since JDK11
 */
@Component(value = SocketMessageEventContent.UP_DOWN)
@Slf4j
@RequiredArgsConstructor
public class MsgSubscribeUpOrDownImpl implements IMsgSubscribeStrategy {
    private final SocketMsgModelCopyMap copyMap;

    @Override
    public void handleMsg(SubscribeMsgModel subscribeMsgModel, ConcurrentHashMap<String, WebSocketSession> sessions) {
        log.info("-----------上线下消息------------->handleMsg:\n{}", subscribeMsgModel);
        // 获取当前用户关联的好友id列表或者群列表
        SocketMsgModel socketMsgModel = copyMap.bToA(subscribeMsgModel);
        String receiveUserId = subscribeMsgModel.getUserId();
        sessions.forEach((v, k) -> {
            String userId = k.getAttributes().get(WebSocketSessionType.USER_ID.getType()).toString();
            if (!receiveUserId.equals(userId)) {
                WsUtils.sendMsg(k, socketMsgModel);
            }
            WsUtils.sendMsg(k, socketMsgModel);
        });
    }
}
