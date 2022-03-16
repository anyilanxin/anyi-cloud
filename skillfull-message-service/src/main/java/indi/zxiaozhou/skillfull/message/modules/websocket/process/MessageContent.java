package indi.zxiaozhou.skillfull.message.modules.websocket.process;

import indi.zxiaozhou.skillfull.corecommon.base.SocketResult;
import indi.zxiaozhou.skillfull.corecommon.constant.SysBaseConstant;
import indi.zxiaozhou.skillfull.message.modules.websocket.model.WebSocketLinkModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 业务处理上下文
 *
 * @author zxiaozhou
 * @date 2021-03-25 07:36
 * @since JDK1.8
 */
@Slf4j
@Component
public class MessageContent {
    private final ConcurrentHashMap<String, WebSocketLinkModel> socketMap;
    private static final Map<String, IMessageStrategy> STRATEGY = new ConcurrentHashMap<>();

    @Autowired
    public MessageContent(final Map<String, IMessageStrategy> strategyMap,
                          ConcurrentHashMap<String, WebSocketLinkModel> socketMap) {
        strategyMap.forEach(STRATEGY::put);
        this.socketMap = socketMap;
    }


    /**
     * 处理
     *
     * @param model              ${@link SocketResult} 待处理消息
     * @param webSocketLinkModel ${@link WebSocketLinkModel} 当前链接想你想
     * @author zxiaozhou
     * @date 2020-12-23 10:50
     */
    public void processMessage(final SocketResult model, WebSocketLinkModel webSocketLinkModel) {
        log.debug("------------MessageContent------收到待处理消息------>processMessage:\n{}", model);
        String socketMsgStrategy = SysBaseConstant.SOCKET_MSG_TYPE_PREFIX + model.getMsgType();
        IMessageStrategy messageStrategy = STRATEGY.get(socketMsgStrategy);
        if (Objects.isNull(messageStrategy)) {
            log.error("------------MessageContent------未找到消息处理策略------>processMessage--->\n参数:{}", model);
        }
        messageStrategy.processMessage(socketMap, model, webSocketLinkModel);
    }
}
