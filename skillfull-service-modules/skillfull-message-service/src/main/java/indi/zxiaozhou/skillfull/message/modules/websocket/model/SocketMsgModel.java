package indi.zxiaozhou.skillfull.message.modules.websocket.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;

/**
 * @program: webflux-websocket-chat-websocket-chat
 * @description:
 * @author: 71ang~
 * @create: 2020-07-14 13:39
 * @vsersion: V1.0
 */
@Slf4j
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class SocketMsgModel implements Serializable {
    private static final long serialVersionUID = 3126044575672218399L;
    private final WebSocketLinkModel webSocketLinkModel;
    private final String msg;

    
    public SocketMsgModel(WebSocketLinkModel webSocketLinkModel, String msg) {
        this.webSocketLinkModel = webSocketLinkModel;
        this.msg = msg;
    }

}