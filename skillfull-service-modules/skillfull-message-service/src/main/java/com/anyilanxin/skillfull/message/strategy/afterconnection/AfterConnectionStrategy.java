package com.anyilanxin.skillfull.message.strategy.afterconnection;

import org.springframework.scheduling.annotation.Async;
import org.springframework.web.socket.WebSocketSession;

/**
 * afterConnection处理
 *
 * @author zxiaozhou
 * @date 2022-08-27 11:32
 * @since JDK11
 */
public interface AfterConnectionStrategy {

    /**
     * 链接后处理
     *
     * @param session
     * @author zxiaozhou
     * @date 2022-08-28 21:10
     */
    @Async
    void handleAfterMsg(WebSocketSession session);
}
