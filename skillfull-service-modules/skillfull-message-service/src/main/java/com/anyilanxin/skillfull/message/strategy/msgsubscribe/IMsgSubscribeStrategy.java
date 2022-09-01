// +----------------------------------------------------------------------
// | SkillFull快速开发平台 [ SkillFull ]
// +----------------------------------------------------------------------
// | 版权所有 2020~2021 zxiaozhou
// +----------------------------------------------------------------------
// | 官方网站: https://skillfull.divisu.com
// +----------------------------------------------------------------------
// | 作者: zxiaozhou <z7630853@163.com>
// +----------------------------------------------------------------------
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
