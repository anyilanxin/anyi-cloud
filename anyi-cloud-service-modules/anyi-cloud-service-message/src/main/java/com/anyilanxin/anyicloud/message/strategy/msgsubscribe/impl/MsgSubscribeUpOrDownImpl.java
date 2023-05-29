

package com.anyilanxin.anyicloud.message.strategy.msgsubscribe.impl;

import com.anyilanxin.anyicloud.message.core.constant.impl.WebSocketSessionType;
import com.anyilanxin.anyicloud.message.strategy.msgsubscribe.IMsgSubscribeStrategy;
import com.anyilanxin.anyicloud.message.strategy.msgsubscribe.mapstruct.SocketMsgModelCopyMap;
import com.anyilanxin.anyicloud.message.utils.WsUtils;
import com.anyilanxin.anyicloud.messagerpc.constant.SocketMessageEventContent;
import com.anyilanxin.anyicloud.messagerpc.model.SocketMsgModel;
import com.anyilanxin.anyicloud.messagerpc.model.SubscribeMsgModel;

import java.util.concurrent.ConcurrentHashMap;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketSession;

/**
 * 上下线消息
 *
 * @author zxh
 * @date 2022-08-27 15:15
 * @since 1.0.0
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
