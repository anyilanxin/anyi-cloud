/*
 * Copyright (c) 2023-present ZHOUXUANHONG(安一老厨)<anyilanxin@aliyun.com>
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
 *   1.请不要删除和修改根目录下的LICENSE.txt文件；
 *   2.请不要删除和修改 AnYi Cloud 源码头部的版权声明；
 *   3.请保留源码和相关描述文件的项目出处，作者声明等；
 *   4.分发源码时候，请注明软件出处 https://github.com/anyilanxin/anyi-cloud；
 *   5.在修改包名，模块名称，项目代码等时，请注明软件出处 https://github.com/anyilanxin/anyi-cloud；
 *   6.本软件不允许在国家法律规定范围外使用，如出现违法行为原作者本人不承担任何法律风险；
 *   7.进行商用时，不得基于AnYi Cloud的基础，修改包装而成一个与AnYi Cloud EE、AnYi Zeebe EE、AnYi Standalone EE功能类似的程序，进行销售或发布，参与同类软件产品市场的竞争；
 *   8.本软件使用的第三方依赖皆为开源软件，如需要修改第三方源码请遵循第三方源码附带开源协议；
 *   9.本软件流程部分请遵循camunda开源协议：
 *     https://docs.camunda.org/manual/latest/introduction/third-party-libraries
 *     https://github.com/camunda/camunda-bpm-platform/blob/master/LICENSE
 *   10.若您的项目无法满足以上几点，可申请商业授权。
 */

package com.anyilanxin.cloud.message.utils;

import com.alibaba.fastjson2.JSONObject;
import com.alibaba.fastjson2.JSONWriter;
import com.anyilanxin.cloud.corecommon.model.auth.AnYiUserInfo;
import com.anyilanxin.cloud.message.core.constant.impl.WebSocketSessionType;
import com.anyilanxin.cloud.messageadapter.constant.enums.SocketMessageEventType;
import com.anyilanxin.cloud.messageadapter.model.SocketMsgContent1Model;
import com.anyilanxin.cloud.messageadapter.model.SubscribeMsgContent1Model;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dromara.hutool.core.collection.CollUtil;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author zxh
 * @date 2022-05-11 00:46
 * @since 1.0.0
 */
@Slf4j
@RequiredArgsConstructor
@Component
public class WsUtils {
    private static WsUtils utils;
    // private final OauthUserAndUserDetailsCopyMap detailsCopyMap;
    /**
     * 保存连接 session 的地方
     */
    public static ConcurrentHashMap<String, WebSocketSession> SESSION_POOL = new ConcurrentHashMap<>();

    @PostConstruct
    void init() {
        utils = this;
    }


    /**
     * 添加 session
     *
     * @param session
     */
    public static void add(WebSocketSession session) {
        // 添加 session
        SESSION_POOL.put(getSessionId(session), session);
    }


    /**
     * 删除 session,会返回删除的 session
     *
     * @param session
     * @return
     */
    public static WebSocketSession remove(WebSocketSession session) {
        // 删除 session
        return SESSION_POOL.remove(getSessionId(session));
    }


    /**
     * 删除并同步关闭连接
     *
     * @param session
     */
    public static void removeAndClose(WebSocketSession session) {
        remove(session);
        if (session != null) {
            try {
                // 关闭连接
                session.close();
            } catch (IOException e) {
                // todo: 关闭出现异常处理
                e.printStackTrace();
            }
        }
    }


    /**
     * 获取自定义session id
     *
     * @param session
     * @return String
     * @author zxh
     * @date 2022-08-27 15:28
     */
    private static String getSessionId(WebSocketSession session) {
        return session.getAttributes().get(WebSocketSessionType.CUSTOM_SESSION_ID.getType()).toString();
    }


    /**
     * 获得 session
     *
     * @param session
     * @return
     */
    public static WebSocketSession get(WebSocketSession session) {
        // 获得 session
        return SESSION_POOL.get(getSessionId(session));
    }


    /**
     * 获得 session
     *
     * @retur
     */
    public static ConcurrentHashMap<String, WebSocketSession> getAll() {
        // 获得 session
        return SESSION_POOL;
    }


    /**
     * 创建redis订阅信息
     */
    public static SubscribeMsgContent1Model createSubscribeMsgModel(WebSocketSession session, SocketMessageEventType eventType) {
        return createSubscribeMsgModel(session, null, eventType);
    }


    /**
     * 创建redis订阅信息
     */
    public static SubscribeMsgContent1Model createSubscribeMsgModel(WebSocketSession session, SocketMsgContent1Model socketMsgModel) {
        return createSubscribeMsgModel(session, socketMsgModel, null);
    }


    /**
     * 创建redis订阅信息
     */
    public static SubscribeMsgContent1Model createSubscribeMsgModel(WebSocketSession session) {
        return createSubscribeMsgModel(session, null, null);
    }


    /**
     * 创建redis订阅信息
     */
    public static SubscribeMsgContent1Model createSubscribeMsgModel(WebSocketSession session, SocketMsgContent1Model socketMsgModel, SocketMessageEventType eventType) {
        SubscribeMsgContent1Model subscribeMsgModel;
        if (Objects.nonNull(socketMsgModel)) {
            subscribeMsgModel = (SubscribeMsgContent1Model) socketMsgModel;
        } else {
            subscribeMsgModel = new SubscribeMsgContent1Model();
        }
        subscribeMsgModel.setSendTime(LocalDateTime.now());
        subscribeMsgModel.setSendSessionId(session.getId());
        Map<String, Object> attributes = session.getAttributes();
        if (CollUtil.isNotEmpty(attributes)) {
            Object objectUserId = attributes.get(WebSocketSessionType.USER_ID.getType());
            if (Objects.nonNull(objectUserId)) {
                subscribeMsgModel.setUserId(objectUserId.toString());
                Principal principal = session.getPrincipal();
                // if (principal instanceof Authentication) {
                // Authentication userPrincipal = (Authentication) principal;
                // AnYiUserDetails userDetails = (AnYiUserDetails)
                // (userPrincipal.getPrincipal());
                // subscribeMsgModel.setSendUserInfo(utils.detailsCopyMap.aToB(userDetails));
                // }
            }
            Object objectToken = attributes.get(WebSocketSessionType.TOKEN.getType());
            if (Objects.nonNull(objectToken)) {
                subscribeMsgModel.setSendToken(objectToken.toString());
            }
            Object objectCustomSessionId = attributes.get(WebSocketSessionType.CUSTOM_SESSION_ID.getType());
            if (Objects.nonNull(objectCustomSessionId)) {
                subscribeMsgModel.setSendCustomSessionId(objectCustomSessionId.toString());
            }
        }
        if (Objects.nonNull(eventType)) {
            subscribeMsgModel.setMessageEvent(eventType.getType());
        }
        return subscribeMsgModel;
    }


    /**
     * 发送消息
     *
     * @param session
     * @param msgModel
     * @author zxh
     * @date 2022-08-27 16:07
     */
    public static void sendMsg(WebSocketSession session, SocketMsgContent1Model msgModel) {
        try {
            if (Objects.nonNull(session) && Objects.nonNull(msgModel)) {
                session.sendMessage(new TextMessage(JSONObject.toJSONString(msgModel, JSONWriter.Feature.WriteMapNullValue)));
            }
        } catch (IOException exception) {
            log.error("------------------发送消息失败------sendMsg--->\n参数:\n{}\n异常消息:\n{}", msgModel, exception.getMessage());
        }
    }


    /**
     * 获取用户信息
     *
     * @param session
     * @return Principal
     * @author zxh
     * @date 2022-08-26 03:02
     */
    public static AnYiUserInfo getUserInfo(WebSocketSession session) {
        Principal principal = session.getPrincipal();
        // if (principal instanceof Authentication) {
        // Authentication userPrincipal = (Authentication) principal;
        // AnYiUserDetails userDetails = (AnYiUserDetails)
        // (userPrincipal.getPrincipal());
        // return utils.detailsCopyMap.aToB(userDetails);
        // }
        return null;
    }

}
