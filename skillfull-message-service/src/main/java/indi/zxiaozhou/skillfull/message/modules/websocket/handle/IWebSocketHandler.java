// +----------------------------------------------------------------------
// | SkillFull快速开发平台 [ SkillFull ]
// +----------------------------------------------------------------------
// | 版权所有 2020~2021 zxiaozhou
// +----------------------------------------------------------------------
// | 官方网站: https://www.divisu.com
// +----------------------------------------------------------------------
// | 作者: zxiaozhou <z7630853@163.com>
// +----------------------------------------------------------------------
package indi.zxiaozhou.skillfull.message.modules.websocket.handle;

import indi.zxiaozhou.skillfull.corecommon.base.SocketResult;
import indi.zxiaozhou.skillfull.corecommon.constant.BindingStreamConstant;
import indi.zxiaozhou.skillfull.corecommon.constant.Status;
import indi.zxiaozhou.skillfull.corecommon.constant.impl.BusinessType;
import indi.zxiaozhou.skillfull.corecommon.constant.impl.SocketMsgType;
import indi.zxiaozhou.skillfull.corecommon.exception.ResponseException;
import indi.zxiaozhou.skillfull.corecommon.utils.TokenUtils;
import indi.zxiaozhou.skillfull.corewebflux.utils.ServletUtils;
import indi.zxiaozhou.skillfull.message.modules.websocket.model.SocketMsgModel;
import indi.zxiaozhou.skillfull.message.modules.websocket.model.WebSocketLinkModel;
import indi.zxiaozhou.skillfull.message.utils.SocketUtils;
import indi.zxiaozhou.skillfull.message.utils.WebSocketRedisUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.reactive.socket.WebSocketHandler;
import org.springframework.web.reactive.socket.WebSocketMessage;
import org.springframework.web.reactive.socket.WebSocketSession;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Sinks;

import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;

/**
 * socket handler服务层接口
 *
 * @author zxiaozhou
 * @date 2021-05-11 18:43
 * @since JDK1.8
 */
public interface IWebSocketHandler extends WebSocketHandler {
    Logger log = LoggerFactory.getLogger(IWebSocketHandler.class);
    String PING = "PING";
    String PONG = "PONG";

    /**
     * 通用ws方法
     *
     * @param session       ${@link WebSocketSession} session
     * @param messageHandle ${@link Consumer <String>}  消息处理器
     * @param socketMap     ${@link ConcurrentHashMap<String,WebSocketLinkModel>} 链接信息缓存
     * @return Mono<Void> ${@link Mono<Void>}
     * @author zxiaozhou
     * @date 2021-04-29 18:41
     */
    default Mono<Void> handle(WebSocketSession session, Consumer<SocketMsgModel> messageHandle, ConcurrentHashMap<String, WebSocketLinkModel> socketMap) {
        Sinks.Many<WebSocketMessage> hotSource = Sinks.many().unicast().onBackpressureBuffer();
        Flux<WebSocketMessage> hotFlux = hotSource.asFlux();
        Mono<Void> output = session.send(hotFlux);
        // 获取token
        String token = ServletUtils.getSubToken(session.getHandshakeInfo());
        // 接收处理数据
        Mono<Void> input = session.receive()
                .doOnNext(msg -> {
                    WebSocketLinkModel webSocketLinkModel;
                    // 检测用户是否登录
                    try {
                        webSocketLinkModel = new WebSocketLinkModel(hotSource, session);
                    } catch (Exception e) {
                        if (e instanceof ResponseException) {
                            ResponseException responseException = (ResponseException) e;
                            // 移除链接缓存
                            if (responseException.getResult().getCode() == Status.TOKEN_EXPIRED.getCode()) {
                                SocketResult result = new SocketResult(BusinessType.TOKEN_EXPIRED)
                                        .setMessage(Status.TOKEN_EXPIRED.getMessage());
                                sendMsgAndClose(session, result.toString());
                                removeCache(token, socketMap);
                                return;
                            }
                        }
                        SocketResult result = new SocketResult(BusinessType.EXCEPTION_MSG)
                                .setMessage(e.getMessage());
                        hotSource.tryEmitNext(session.textMessage(result.toString()));
                        return;
                    }
                    // 处理消息
                    String payloadAsText = msg.getPayloadAsText();
                    log.info("------------IWebSocketHandler---新消息---payloadAsText------>handle:{}", payloadAsText);
                    // 处理客户端ping响应
                    if (msg.getType().equals(WebSocketMessage.Type.PING)) {
                        hotSource.tryEmitNext(session.pingMessage(dbf -> dbf.allocateBuffer(PONG.length()).write(PONG.getBytes())));
                    } else if (payloadAsText.equalsIgnoreCase(PING)) {
                        hotSource.tryEmitNext(session.textMessage(PONG));
                    } else if (msg.getType().equals(WebSocketMessage.Type.TEXT)) {
                        // 如果是TEXT，调用用户接口处理器
                        messageHandle.accept(new SocketMsgModel(webSocketLinkModel, payloadAsText));
                    }
                })
                .doOnSubscribe(conn -> this.onOpen(hotSource, session, socketMap))
                .then();
        // 合并
        return Mono.zip(input, output).doFinally(signalType -> removeCache(token, socketMap)).then();
    }


    /**
     * 打开链接处理鉴权
     *
     * @param hotSource ${@link Sinks.Many<WebSocketMessage>Sinks.Many<WebSocketMessage>} hotSource
     * @param session   ${@link WebSocketSession} session
     * @param socketMap ${@link ConcurrentHashMap<String,WebSocketLinkModel>} 缓存socket信息
     * @author zxiaozhou
     * @date 2021-04-29 21:47
     */
    private void onOpen(Sinks.Many<WebSocketMessage> hotSource, WebSocketSession session, ConcurrentHashMap<String, WebSocketLinkModel> socketMap) {
        WebSocketLinkModel linkModel;
        try {
            // 校验用户登录信息并缓存登录信息
            linkModel = new WebSocketLinkModel(hotSource, session);
            socketMap.put(linkModel.getLoginCode(), linkModel);
            WebSocketRedisUtil.addMsgConnect(linkModel);
        } catch (Exception e) {
            log.error("------------IWebSocketHandler------------>onOpen--->\n异常消息:{}", e.getMessage());
            if (e instanceof ResponseException) {
                ResponseException responseException = (ResponseException) e;
                if (responseException.getResult().getCode() == Status.TOKEN_EXPIRED.getCode()) {
                    SocketResult result = new SocketResult(BusinessType.TOKEN_EXPIRED)
                            .setMessage(Status.TOKEN_EXPIRED.getMessage());
                    sendMsgAndClose(session, result.toString());
                    return;
                }
            }
            SocketResult result = new SocketResult(BusinessType.EXCEPTION_MSG)
                    .setMessage(e.getMessage());
            hotSource.tryEmitNext(session.textMessage(result.toString()));
            return;
        }
        // 发送一个临时提醒消息
        SocketResult model = new SocketResult(BusinessType.NEW_TEMPORARY_NOTICE_MSG)
                .setMsgType(SocketMsgType.DESIGNATED_SYSTEM_PERSONNEL)
                .setMessage("请大家爱护系统环境，不要随便删除数据!!!!!!!!!!")
                .setSystemId(linkModel.getLoginCode());
        SocketUtils.out(BindingStreamConstant.SOCKET_PROCESS, model);
    }


    /**
     * 删除缓存并断开链接
     *
     * @param token     ${@link String} 用户token
     * @param socketMap ${@link ConcurrentHashMap<String,WebSocketLinkModel>} 缓存的所有socket信息
     * @author zxiaozhou
     * @date 2021-04-29 21:46
     */
    private void removeCache(String token, ConcurrentHashMap<String, WebSocketLinkModel> socketMap) {
        if (StringUtils.isNotBlank(token)) {
            WebSocketLinkModel model = socketMap.remove(TokenUtils.getLoginCode(token));
            if (Objects.nonNull(model)) {
                WebSocketRedisUtil.deleteMsgUser(model);
            }
        }
    }


    /**
     * 发送消息并断开链接
     *
     * @param msg     ${@link String} 消息
     * @param session ${@link WebSocketSession} session
     * @author zxiaozhou
     * @date 2021-05-11 21:28
     */
    private void sendMsgAndClose(WebSocketSession session, String msg) {
        session.send(Flux.create(sink -> sink.next(session.textMessage(msg)))).then().block();
    }
}
