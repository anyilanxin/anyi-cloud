

package com.anyilanxin.anyicloud.message.strategy.msgsubscribe.impl;

import com.anyilanxin.anyicloud.message.strategy.msgsubscribe.IMsgSubscribeStrategy;
import com.anyilanxin.anyicloud.messagerpc.constant.SocketMessageEventContent;
import com.anyilanxin.anyicloud.messagerpc.model.SubscribeMsgModel;

import java.util.concurrent.ConcurrentHashMap;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketSession;

/**
 * 通知消息
 *
 * @author zxh
 * @date 2022-08-27 15:15
 * @since 1.0.0
 */
@Component(value = SocketMessageEventContent.NOTICE_EVENT)
@Slf4j
@RequiredArgsConstructor
public class MsgSubscribeNoticeImpl implements IMsgSubscribeStrategy {
    @Override
    public void handleMsg(SubscribeMsgModel subscribeMsgModel, ConcurrentHashMap<String, WebSocketSession> sessions) {
    }
}
