

package com.anyilanxin.anyicloud.message.strategy.afterconnection;

import org.springframework.scheduling.annotation.Async;
import org.springframework.web.socket.WebSocketSession;

/**
 * afterConnection处理
 *
 * @author zxh
 * @date 2022-08-27 11:32
 * @since 1.0.0
 */
public interface AfterConnectionStrategy {

    /**
     * 链接后处理
     *
     * @param session
     * @author zxh
     * @date 2022-08-28 21:10
     */
    @Async
    void handleAfterMsg(WebSocketSession session);
}
