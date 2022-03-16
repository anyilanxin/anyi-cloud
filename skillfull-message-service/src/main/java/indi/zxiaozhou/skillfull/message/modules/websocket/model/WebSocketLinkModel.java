package indi.zxiaozhou.skillfull.message.modules.websocket.model;

import indi.zxiaozhou.skillfull.corecommon.base.model.login.LoginTokenClaimSubModel;
import indi.zxiaozhou.skillfull.corecommon.base.model.login.LoginUserInfoModel;
import indi.zxiaozhou.skillfull.corecommon.utils.TokenUtils;
import indi.zxiaozhou.skillfull.corewebflux.utils.ContextHolderUtils;
import indi.zxiaozhou.skillfull.corewebflux.utils.ServletUtils;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.Accessors;
import lombok.experimental.SuperBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.reactive.socket.WebSocketMessage;
import org.springframework.web.reactive.socket.WebSocketSession;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

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
@Accessors(chain = true)
@SuperBuilder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Schema
public class WebSocketLinkModel implements Serializable {
    private static final long serialVersionUID = 3126044575672218399L;
    private Sinks.Many<WebSocketMessage> hotSource;
    private WebSocketSession session;
    private String userId;
    private String userName;
    private String loginCode;
    private String token;
    private String loginEndpoint;

    public WebSocketLinkModel(Sinks.Many<WebSocketMessage> hotSource, WebSocketSession session) {
        this.hotSource = hotSource;
        this.session = session;
        String token = ServletUtils.getSubToken(session.getHandshakeInfo());
        LoginTokenClaimSubModel tokenClaimSubModel = ContextHolderUtils.getTokenClaimSubModel(token);
        this.token = token;
        this.loginCode = TokenUtils.getLoginCode(token);
        LoginUserInfoModel userAndAuthModel = ContextHolderUtils.getUserAndAuthModel(token);
        this.userName = userAndAuthModel.getUserName();
        this.userId = userAndAuthModel.getUserId();
        this.loginEndpoint = tokenClaimSubModel.getLoginEndpoint();
        log.debug("------------WebSocketLinkModel------有新的链接------>\nrealName:{},userId:{},loginEndpoint:", userAndAuthModel.getRealName(), userAndAuthModel.getUserId(), tokenClaimSubModel.getLoginEndpoint());
    }


    /**
     * 发送消息字符串需要由MessageUtils的getWebSocketMsg()方法去构建
     *
     * @param data ${@link String} 待发送消息
     * @author zxiaozhou
     * @date 2021-01-25 16:10
     */
    public void sendData(String data) {
        log.info("------------WebSocketLinkModel------发送web socket消息------>sendData:{}", data);
        hotSource.tryEmitNext(session.textMessage(data));
    }


    /**
     * 发送消息并断开链接
     *
     * @param data ${@link String} 待发送消息
     * @author zxiaozhou
     * @date 2021-05-11 21:40
     */
    public void sendDataAndClose(String data) {
        session.send(Flux.create(sink -> sink.next(session.textMessage(data)))).then().block();
    }

}