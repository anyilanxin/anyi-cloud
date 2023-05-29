

package com.anyilanxin.anyicloud.message.strategy.msgsubscribe;

import com.anyilanxin.anyicloud.messagerpc.model.SubscribeMsgModel;

import java.util.concurrent.ConcurrentHashMap;

import org.springframework.web.socket.WebSocketSession;

/**
 * 消息处理
 *
 * @author zxh
 * @date 2022-08-27 11:58
 * @since 1.0.0
 */
public interface IMsgSubscribeStrategy {
    /**
     * 处理消息
     *
     * @param subscribeMsgModel 消息信息
     * @param sessions          当前系统所有session
     * @author zxh
     * @date 2022-08-27 15:15
     */
    void handleMsg(SubscribeMsgModel subscribeMsgModel, ConcurrentHashMap<String, WebSocketSession> sessions);
}
