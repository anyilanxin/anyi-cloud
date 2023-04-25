package com.anyilanxin.skillfull.message.strategy.msgsubscribe;

import com.anyilanxin.skillfull.messagerpc.model.SubscribeMsgModel;
import org.springframework.web.socket.WebSocketSession;

import java.util.concurrent.ConcurrentHashMap;

/**
 * 消息处理
 *
 * @author zxiaozhou
 * @date 2022-08-27 11:58
 * @since JDK11
 */
public interface IMsgSubscribeStrategy {
    /**
     * 处理消息
     *
     * @param subscribeMsgModel 消息信息
     * @param sessions          当前系统所有session
     * @author zxiaozhou
     * @date 2022-08-27 15:15
     */
    void handleMsg(SubscribeMsgModel subscribeMsgModel, ConcurrentHashMap<String, WebSocketSession> sessions);
}
