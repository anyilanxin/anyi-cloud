// +----------------------------------------------------------------------
// | SkillFull快速开发平台 [ SkillFull ]
// +----------------------------------------------------------------------
// | 版权所有 2020~2021 zxiaozhou
// +----------------------------------------------------------------------
// | 官方网站: https://skillfull.divisu.com
// +----------------------------------------------------------------------
// | 作者: zxiaozhou <z7630853@163.com>
// +----------------------------------------------------------------------
package com.anyilanxin.skillfull.message.strategy.msgsubscribe.impl;

import com.anyilanxin.skillfull.message.strategy.msgsubscribe.IMsgSubscribeStrategy;
import com.anyilanxin.skillfull.messagerpc.constant.SocketMessageEventContent;
import com.anyilanxin.skillfull.messagerpc.model.SubscribeMsgModel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketSession;

import java.util.concurrent.ConcurrentHashMap;

/**
 * 业务消息
 *
 * @author zxiaozhou
 * @date 2022-08-27 15:15
 * @since JDK11
 */
@Component(value = SocketMessageEventContent.BUSINESS_EVENT)
@Slf4j
@RequiredArgsConstructor
public class MsgSubscribeBusinessImpl implements IMsgSubscribeStrategy {
    @Override
    public void handleMsg(SubscribeMsgModel subscribeMsgModel, ConcurrentHashMap<String, WebSocketSession> sessions) {

    }
}
