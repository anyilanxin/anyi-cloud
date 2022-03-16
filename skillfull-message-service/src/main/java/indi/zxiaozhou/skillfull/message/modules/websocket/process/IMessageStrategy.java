package indi.zxiaozhou.skillfull.message.modules.websocket.process;

import indi.zxiaozhou.skillfull.corecommon.base.SocketResult;
import indi.zxiaozhou.skillfull.message.modules.websocket.model.WebSocketLinkModel;

import java.util.concurrent.ConcurrentHashMap;

/**
 * 消息处理策略
 *
 * @author zxiaozhou
 * @date 2021-03-25 07:33
 * @since JDK1.8
 */
public interface IMessageStrategy {
    /**
     * 处理消息
     *
     * @param socketMap          ${@link ConcurrentHashMap<String, WebSocketLinkModel>}
     * @param model              ${@link SocketResult}
     * @param webSocketLinkModel ${@link WebSocketLinkModel}
     * @author zxiaozhou
     * @date 2021-03-25 07:34
     */
    void processMessage(final ConcurrentHashMap<String, WebSocketLinkModel> socketMap, final SocketResult model, final WebSocketLinkModel webSocketLinkModel);
}
