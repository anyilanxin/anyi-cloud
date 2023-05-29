

package com.anyilanxin.anyicloud.message.core.handler;

/**
 * @author zxh
 * @date 2022-05-11 00:44
 * @since 1.0.0
 */

import com.alibaba.fastjson2.JSONObject;
import com.alibaba.fastjson2.JSONWriter;
import com.anyilanxin.anyicloud.corecommon.constant.Status;
import com.anyilanxin.anyicloud.corecommon.model.auth.UserInfo;
import com.anyilanxin.anyicloud.coreredis.constant.RedisSubscribeConstant;
import com.anyilanxin.anyicloud.coreredis.utils.SendRedisMsgUtils;
import com.anyilanxin.anyicloud.message.core.constant.impl.WebSocketSessionType;
import com.anyilanxin.anyicloud.message.strategy.afterconnection.AfterConnectionContent;
import com.anyilanxin.anyicloud.message.strategy.msgsubscribe.MsgSubscribeContent;
import com.anyilanxin.anyicloud.message.utils.WsUtils;
import com.anyilanxin.anyicloud.messagerpc.constant.MessageCommonContent;
import com.anyilanxin.anyicloud.messagerpc.constant.impl.SocketMessageEventType;
import com.anyilanxin.anyicloud.messagerpc.model.ErrorMsgModel;
import com.anyilanxin.anyicloud.messagerpc.model.SocketMsgModel;
import com.anyilanxin.anyicloud.messagerpc.model.SubscribeMsgModel;
import com.anyilanxin.anyicloud.messagerpc.model.UpOrDownModel;
import com.anyilanxin.anyicloud.oauth2mvc.user.IGetLoginUserInfo;

import java.io.IOException;
import java.security.Principal;
import java.util.Objects;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.oauth2.common.exceptions.UnauthorizedUserException;
import org.springframework.web.socket.*;
import org.springframework.web.socket.handler.AbstractWebSocketHandler;

/**
 * WebSocket处理器
 */
@Slf4j
@RequiredArgsConstructor
public class WebSocketMainHandler extends AbstractWebSocketHandler {
    private final AfterConnectionContent afterConnectionContent;
    private final MsgSubscribeContent msgSubscribeContent;
    private final IGetLoginUserInfo loginUserInfo;

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        log.info("------------afterConnectionEstablished------------>建立ws连接");
        Principal principal = checkLoginReturnUser(session);
        if (Objects.isNull(principal)) {
            return;
        }
        log.debug("------------afterConnectionEstablished---建立ws连接-----userId---->\n{}", session.getAttributes().get(WebSocketSessionType.USER_ID.getType()));
        log.debug("------------afterConnectionEstablished---建立ws连接-----token----->\n{}", session.getAttributes().get(WebSocketSessionType.TOKEN.getType()));
        log.debug("------------afterConnectionEstablished---建立ws连接-----userInfo-->\n:{}", principal);
        WsUtils.add(session);
        afterConnectionContent.afterConnectionHandle(session);
        // 发送消息广播集群
        upOrDownNotice(session, 1);
    }


    @Override
    protected void handlePongMessage(WebSocketSession session, PongMessage message) throws Exception {
        boolean result = checkLogin(session);
        if (!result) {
            return;
        }
        session.sendMessage(new PongMessage());
    }


    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        boolean result = checkLogin(session);
        if (!result) {
            return;
        }
        String payload = message.getPayload();
        log.info("------------handleTextMessage-----server 接收到消息------->{}", payload);
        if (MessageCommonContent.PING.equalsIgnoreCase(payload)) {
            session.sendMessage(new TextMessage(MessageCommonContent.PONG));
            return;
        }
        try {
            SocketMsgModel socketMsgModel = JSONObject.parseObject(payload, SocketMsgModel.class);
            SubscribeMsgModel subscribeMsgModel = WsUtils.createSubscribeMsgModel(session, socketMsgModel);
            SendRedisMsgUtils.sendMsg(RedisSubscribeConstant.MESSAGE_SOCKET_HANDLE, JSONObject.toJSONString(subscribeMsgModel, JSONWriter.Feature.WriteMapNullValue));
        } catch (Exception exception) {
            SocketMsgModel socketMsgModel = new SocketMsgModel(SocketMessageEventType.ERROR_EVENT);
            ErrorMsgModel model = new ErrorMsgModel();
            model.setData(exception.getMessage());
            WsUtils.sendMsg(session, socketMsgModel);
        }
        // 获得客户端传来的消息
        log.info("------------handleTextMessage-----server 接收到消息------->{}", payload);
    }


    @Override
    protected void handleBinaryMessage(WebSocketSession session, BinaryMessage message) throws Exception {
        boolean result = checkLogin(session);
        if (!result) {
            return;
        }
        log.info("------------handleBinaryMessage------------>发送二进制消息");
    }


    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) {
        log.info("------------handleTransportError------ws发生了异常------>{}", exception.getMessage());
        SocketMsgModel subscribeMsgModel = new SocketMsgModel(SocketMessageEventType.ERROR_EVENT);
        ErrorMsgModel errorMsgModel = new ErrorMsgModel();
        errorMsgModel.setData(exception.getMessage());
        subscribeMsgModel.setData(errorMsgModel);
        WsUtils.sendMsg(session, subscribeMsgModel);
    }


    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        log.info("------------afterConnectionClosed------关闭ws连接------>{}", status);
        WsUtils.removeAndClose(session);
        // 发送消息广播集群
        upOrDownNotice(session, 0);
    }


    /**
     * 上下线处理
     *
     * @param session
     * @param type    0-下线，1-上线
     * @author zxh
     * @date 2022-08-27 15:01
     */
    private void upOrDownNotice(WebSocketSession session, int type) {
        SubscribeMsgModel subscribeMsgModel = WsUtils.createSubscribeMsgModel(session, SocketMessageEventType.UP_DOWN);
        UpOrDownModel upOrDownModel = new UpOrDownModel();
        upOrDownModel.setType(type);
        UserInfo userInfo = WsUtils.getUserInfo(session);
        if (Objects.nonNull(userInfo)) {
            upOrDownModel.setRealName(userInfo.getRealName());
            upOrDownModel.setAvatar(userInfo.getAvatar());
        }
        upOrDownModel.setUserId(session.getAttributes().get(WebSocketSessionType.USER_ID.getType()).toString());
        subscribeMsgModel.setData(upOrDownModel);
        SendRedisMsgUtils.sendMsg(RedisSubscribeConstant.MESSAGE_SOCKET_HANDLE, JSONObject.toJSONString(subscribeMsgModel, JSONWriter.Feature.WriteMapNullValue));
    }


    /**
     * 检测登录状态
     *
     * @param session
     * @author zxh
     * @date 2022-08-26 03:02
     */
    private boolean checkLogin(WebSocketSession session) throws IOException {
        try {
            Object objectToken = session.getAttributes().get(WebSocketSessionType.TOKEN.getType());
            if (Objects.nonNull(objectToken)) {
                loginUserInfo.getUserInfo(objectToken.toString());
            } else {
                CloseStatus status = new CloseStatus(Status.TOKEN_EXPIRED.getCode(), Status.TOKEN_EXPIRED.getMessage());
                session.close(status);
                return false;
            }
        } catch (UnauthorizedUserException e) {
            CloseStatus status = new CloseStatus(Status.TOKEN_EXPIRED.getCode(), e.getMessage());
            session.close(status);
            return false;
        }
        return true;
    }


    /**
     * 检测登录权限并返回用户信息
     *
     * @param session
     * @return Principal
     * @author zxh
     * @date 2022-08-26 03:02
     */
    private Principal checkLoginReturnUser(WebSocketSession session) throws IOException {
        Principal principal = session.getPrincipal();
        if (principal instanceof AnonymousAuthenticationToken) {
            CloseStatus status = new CloseStatus(Status.TOKEN_EXPIRED.getCode(), Status.TOKEN_EXPIRED.getMessage());
            session.close(status);
            return null;
        }
        return principal;
    }
}
