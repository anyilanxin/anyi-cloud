/*
 * Copyright (c) 2021-2022 ZHOUXUANHONG(安一老厨)<anyilanxin@aliyun.com>
 *
 * AnYi Cloud Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * AnYi Cloud 采用APACHE LICENSE 2.0开源协议，您在使用过程中，需要注意以下几点：
 *   1.请不要删除和修改根目录下的LICENSE文件；
 *   2.请不要删除和修改 AnYi Cloud 源码头部的版权声明；
 *   3.请保留源码和相关描述文件的项目出处，作者声明等；
 *   4.分发源码时候，请注明软件出处 https://github.com/anyilanxin/anyi-cloud；
 *   5.在修改包名，模块名称，项目代码等时，请注明软件出处 https://github.com/anyilanxin/anyi-cloud；
 *   6.本软件不允许在国家法律规定范围外使用，如出现违法行为原作者本人不承担任何法律风险；
 *   7.本软件使用的第三方依赖皆为开源软件，如需要修改第三方源码请遵循第三方源码附带开源协议；
 *   8.本软件流程部分请遵循camunda开源协议：
 *     https://docs.camunda.org/manual/latest/introduction/third-party-libraries
 *     https://github.com/camunda/camunda-bpm-platform/blob/master/LICENSE
 *   9.若您的项目无法满足以上几点，可申请商业授权。
 */


package com.anyilanxin.skillfull.message.core.handler;

/**
 * @author zxiaozhou
 * @date 2022-05-11 00:44
 * @since JDK1.8
 */

import com.alibaba.fastjson2.JSONObject;
import com.alibaba.fastjson2.JSONWriter;
import com.anyilanxin.skillfull.corecommon.constant.Status;
import com.anyilanxin.skillfull.corecommon.model.auth.UserInfo;
import com.anyilanxin.skillfull.coreredis.constant.RedisSubscribeConstant;
import com.anyilanxin.skillfull.coreredis.utils.SendRedisMsgUtils;
import com.anyilanxin.skillfull.message.core.constant.impl.WebSocketSessionType;
import com.anyilanxin.skillfull.message.strategy.afterconnection.AfterConnectionContent;
import com.anyilanxin.skillfull.message.strategy.msgsubscribe.MsgSubscribeContent;
import com.anyilanxin.skillfull.message.utils.WsUtils;
import com.anyilanxin.skillfull.messagerpc.constant.MessageCommonContent;
import com.anyilanxin.skillfull.messagerpc.constant.impl.SocketMessageEventType;
import com.anyilanxin.skillfull.messagerpc.model.ErrorMsgModel;
import com.anyilanxin.skillfull.messagerpc.model.SocketMsgModel;
import com.anyilanxin.skillfull.messagerpc.model.SubscribeMsgModel;
import com.anyilanxin.skillfull.messagerpc.model.UpOrDownModel;
import com.anyilanxin.skillfull.oauth2mvc.user.IGetLoginUserInfo;

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
        log.debug(
                "------------afterConnectionEstablished---建立ws连接-----userId---->\n{}",
                session.getAttributes().get(WebSocketSessionType.USER_ID.getType()));
        log.debug(
                "------------afterConnectionEstablished---建立ws连接-----token----->\n{}",
                session.getAttributes().get(WebSocketSessionType.TOKEN.getType()));
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
            SubscribeMsgModel subscribeMsgModel =
                    WsUtils.createSubscribeMsgModel(session, socketMsgModel);
            SendRedisMsgUtils.sendMsg(
                    RedisSubscribeConstant.MESSAGE_SOCKET_HANDLE,
                    JSONObject.toJSONString(subscribeMsgModel, JSONWriter.Feature.WriteMapNullValue));
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
    protected void handleBinaryMessage(WebSocketSession session, BinaryMessage message)
            throws Exception {
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
     * @author zxiaozhou
     * @date 2022-08-27 15:01
     */
    private void upOrDownNotice(WebSocketSession session, int type) {
        SubscribeMsgModel subscribeMsgModel =
                WsUtils.createSubscribeMsgModel(session, SocketMessageEventType.UP_DOWN);
        UpOrDownModel upOrDownModel = new UpOrDownModel();
        upOrDownModel.setType(type);
        UserInfo userInfo = WsUtils.getUserInfo(session);
        if (Objects.nonNull(userInfo)) {
            upOrDownModel.setRealName(userInfo.getRealName());
            upOrDownModel.setAvatar(userInfo.getAvatar());
        }
        upOrDownModel.setUserId(
                session.getAttributes().get(WebSocketSessionType.USER_ID.getType()).toString());
        subscribeMsgModel.setData(upOrDownModel);
        SendRedisMsgUtils.sendMsg(
                RedisSubscribeConstant.MESSAGE_SOCKET_HANDLE,
                JSONObject.toJSONString(subscribeMsgModel, JSONWriter.Feature.WriteMapNullValue));
    }

    /**
     * 检测登录状态
     *
     * @param session
     * @author zxiaozhou
     * @date 2022-08-26 03:02
     */
    private boolean checkLogin(WebSocketSession session) throws IOException {
        try {
            Object objectToken = session.getAttributes().get(WebSocketSessionType.TOKEN.getType());
            if (Objects.nonNull(objectToken)) {
                loginUserInfo.getUserInfo(objectToken.toString());
            } else {
                CloseStatus status =
                        new CloseStatus(Status.TOKEN_EXPIRED.getCode(), Status.TOKEN_EXPIRED.getMessage());
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
     * @author zxiaozhou
     * @date 2022-08-26 03:02
     */
    private Principal checkLoginReturnUser(WebSocketSession session) throws IOException {
        Principal principal = session.getPrincipal();
        if (principal instanceof AnonymousAuthenticationToken) {
            CloseStatus status =
                    new CloseStatus(Status.TOKEN_EXPIRED.getCode(), Status.TOKEN_EXPIRED.getMessage());
            session.close(status);
            return null;
        }
        return principal;
    }
}
