// +----------------------------------------------------------------------
// | SkillFull快速开发平台 [ SkillFull ]
// +----------------------------------------------------------------------
// | 版权所有 2020~2021 zxiaozhou
// +----------------------------------------------------------------------
// | 官方网站: https://www.divisu.com
// +----------------------------------------------------------------------
// | 作者: zxiaozhou <z7630853@163.com>
// +----------------------------------------------------------------------
package com.anyilanxin.skillfull.message.core.handler;

/**
 * @author zxiaozhou
 * @date 2022-05-11 00:44
 * @since JDK1.8
 */

import com.anyilanxin.skillfull.corecommon.constant.Status;
import com.anyilanxin.skillfull.message.utils.WsSessionManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.web.socket.BinaryMessage;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.AbstractWebSocketHandler;

import java.security.Principal;
import java.time.LocalDateTime;

/**
 * WebSocket处理器
 */
@Slf4j
public class WebSocketAuthHandler extends AbstractWebSocketHandler {

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        log.info("------------afterConnectionEstablished------------>建立ws连接");
        Principal principal = session.getPrincipal();
        if (principal instanceof AnonymousAuthenticationToken) {
            CloseStatus status = new CloseStatus(Status.TOKEN_EXPIRED.getCode(), Status.TOKEN_EXPIRED.getMessage());
            session.close(status);
            log.error("------------afterConnectionEstablished------------>建立ws连接鉴权失败");
        } else {
            log.info("------------afterConnectionEstablished-----建立ws连接成功------->\nprincipal:{}", principal);
            WsSessionManager.add(session.getId(), session);
        }
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        log.info("------------handleTextMessage------------>发送文本消息");
        // 获得客户端传来的消息
        String payload = message.getPayload();
        log.info("------------handleTextMessage-----server 接收到消息------->{}", payload);
        session.sendMessage(new TextMessage("server 发送给的消息 " + payload + "，发送时间:" + LocalDateTime.now().toString()));
    }

    @Override
    protected void handleBinaryMessage(WebSocketSession session, BinaryMessage message) throws Exception {
        log.info("------------handleBinaryMessage------------>发送二进制消息");
    }


    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        log.info("------------handleTransportError------ws发生了异常------>{}", exception.getMessage());
        session.sendMessage(new TextMessage("server 发送给的消息 " + exception.getMessage() + "，发送时间:" + LocalDateTime.now().toString()));
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        log.info("------------afterConnectionClosed------关闭ws连接------>{}", status);
        WsSessionManager.removeAndClose(session.getId());
    }


}

