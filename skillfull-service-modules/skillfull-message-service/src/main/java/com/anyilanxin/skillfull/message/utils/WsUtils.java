// +----------------------------------------------------------------------
// | SkillFull快速开发平台 [ SkillFull ]
// +----------------------------------------------------------------------
// | 版权所有 2020~2021 zxiaozhou
// +----------------------------------------------------------------------
// | 官方网站: https://skillfull.divisu.com
// +----------------------------------------------------------------------
// | 作者: zxiaozhou <z7630853@163.com>
// +----------------------------------------------------------------------
package com.anyilanxin.skillfull.message.utils;

import cn.hutool.core.collection.CollectionUtil;
import com.alibaba.fastjson2.JSONObject;
import com.alibaba.fastjson2.JSONWriter;
import com.anyilanxin.skillfull.corecommon.model.auth.UserInfo;
import com.anyilanxin.skillfull.message.core.constant.impl.WebSocketSessionType;
import com.anyilanxin.skillfull.messagerpc.constant.impl.SocketMessageEventType;
import com.anyilanxin.skillfull.messagerpc.model.SocketMsgModel;
import com.anyilanxin.skillfull.messagerpc.model.SubscribeMsgModel;
import com.anyilanxin.skillfull.oauth2common.authinfo.SkillFullUserDetails;
import com.anyilanxin.skillfull.oauth2common.mapstruct.OauthUserAndUserDetailsCopyMap;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author zxiaozhou
 * @date 2022-05-11 00:46
 * @since JDK1.8
 */
@Slf4j
@RequiredArgsConstructor
@Component
public class WsUtils {
    private static WsUtils utils;
    private final OauthUserAndUserDetailsCopyMap detailsCopyMap;
    /**
     * 保存连接 session 的地方
     */
    public static ConcurrentHashMap<String, WebSocketSession> SESSION_POOL = new ConcurrentHashMap<>();

    @PostConstruct
    private void init() {
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
     * @author zxiaozhou
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
    public static SubscribeMsgModel createSubscribeMsgModel(WebSocketSession session, SocketMessageEventType eventType) {
        return createSubscribeMsgModel(session, null, eventType);
    }


    /**
     * 创建redis订阅信息
     */
    public static SubscribeMsgModel createSubscribeMsgModel(WebSocketSession session, SocketMsgModel socketMsgModel) {
        return createSubscribeMsgModel(session, socketMsgModel, null);
    }


    /**
     * 创建redis订阅信息
     */
    public static SubscribeMsgModel createSubscribeMsgModel(WebSocketSession session) {
        return createSubscribeMsgModel(session, null, null);
    }


    /**
     * 创建redis订阅信息
     */
    public static SubscribeMsgModel createSubscribeMsgModel(WebSocketSession session, SocketMsgModel socketMsgModel, SocketMessageEventType eventType) {
        SubscribeMsgModel subscribeMsgModel;
        if (Objects.nonNull(socketMsgModel)) {
            subscribeMsgModel = (SubscribeMsgModel) socketMsgModel;
        } else {
            subscribeMsgModel = new SubscribeMsgModel();
        }
        subscribeMsgModel.setSendTime(LocalDateTime.now());
        subscribeMsgModel.setSendSessionId(session.getId());
        Map<String, Object> attributes = session.getAttributes();
        if (CollectionUtil.isNotEmpty(attributes)) {
            Object objectUserId = attributes.get(WebSocketSessionType.USER_ID.getType());
            if (Objects.nonNull(objectUserId)) {
                subscribeMsgModel.setUserId(objectUserId.toString());
                Principal principal = session.getPrincipal();
                if (principal instanceof Authentication) {
                    Authentication userPrincipal = (Authentication) principal;
                    SkillFullUserDetails userDetails = (SkillFullUserDetails) (userPrincipal.getPrincipal());
                    subscribeMsgModel.setSendUserInfo(utils.detailsCopyMap.aToB(userDetails));
                }
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
     * @author zxiaozhou
     * @date 2022-08-27 16:07
     */
    public static void sendMsg(WebSocketSession session, SocketMsgModel msgModel) {
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
     * @author zxiaozhou
     * @date 2022-08-26 03:02
     */
    public static UserInfo getUserInfo(WebSocketSession session) {
        Principal principal = session.getPrincipal();
        if (principal instanceof Authentication) {
            Authentication userPrincipal = (Authentication) principal;
            SkillFullUserDetails userDetails = (SkillFullUserDetails) (userPrincipal.getPrincipal());
            return utils.detailsCopyMap.aToB(userDetails);
        }
        return null;
    }

}

