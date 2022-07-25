// +----------------------------------------------------------------------
// | SkillFull快速开发平台 [ SkillFull ]
// +----------------------------------------------------------------------
// | 版权所有 2020~2021 zxiaozhou
// +----------------------------------------------------------------------
// | 官方网站: https://www.divisu.com
// +----------------------------------------------------------------------
// | 作者: zxiaozhou <z7630853@163.com>
// +----------------------------------------------------------------------
package com.anyilanxin.skillfull.message.modules.stream.impl;

import com.alibaba.fastjson.JSON;
import com.anyilanxin.skillfull.message.modules.chat.entity.ChatMessageInfoEntity;
import com.anyilanxin.skillfull.message.modules.chat.mapper.ChatMessageInfoMapper;
import com.anyilanxin.skillfull.message.modules.stream.IStreamMessageStrategy;
import com.anyilanxin.skillfull.message.modules.websocket.model.WebSocketMsgModel;
import com.anyilanxin.skillfull.message.modules.websocket.model.chat.ChatMsgModel;
import com.anyilanxin.skillfull.message.modules.websocket.model.chat.ChatSendMsgModel;
import com.anyilanxin.skillfull.messageapi.constant.StreamSocketBusinessConstant;
import com.anyilanxin.skillfull.messageapi.constant.impl.StreamSocketBusinessType;
import com.anyilanxin.skillfull.messageapi.model.StreamMsgModel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.websocket.Session;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 聊天消息
 *
 * @author zxiaozhou
 * @date 2022-03-29 17:36
 * @since JDK1.8
 */
@Component(StreamSocketBusinessConstant.CHART_MSG)
@RequiredArgsConstructor
public class ChatMsgMessageStrategyImpl implements IStreamMessageStrategy {
    private final ChatMessageInfoMapper messageInfoMapper;

    @Override
    @Transactional
    public void processMessage(StreamMsgModel streamMsgModel, ConcurrentHashMap<String, Session> socketSessionsCache) {
        ChatMessageInfoEntity chatMessageInfoEntity = messageInfoMapper.selectById(streamMsgModel.getUniqueId());
        WebSocketMsgModel msgData = JSON.parseObject(streamMsgModel.getMsgData(), WebSocketMsgModel.class);
        if (Objects.isNull(chatMessageInfoEntity)) {
            Session session = socketSessionsCache.get(streamMsgModel.getSessionId());
            ChatSendMsgModel sendMsgModel = JSON.parseObject(msgData.getData(), ChatSendMsgModel.class);
            chatMessageInfoEntity = ChatMessageInfoEntity.builder()
                    .chatMessageId(streamMsgModel.getUniqueId())
                    .msgType(sendMsgModel.getMsgType())
                    .sendUserId(session.getUserProperties().get("userId").toString())
                    .msgSendTime(LocalDateTime.now())
                    .chatType(sendMsgModel.getChatType())
                    .sendMsgContent(sendMsgModel.getSendMsgContent())
                    .chatBusinessId(sendMsgModel.getReceiverId())
                    .build();
            messageInfoMapper.insert(chatMessageInfoEntity);
            // 回复消息
            ChatMsgModel chat = getChat(chatMessageInfoEntity);
            chat.setType(1);
            WebSocketMsgModel msgModel = WebSocketMsgModel.builder()
                    .businessType(StreamSocketBusinessType.CHART_MSG.getType())
                    .uniqueId(msgData.getUniqueId())
                    .data(JSON.toJSONString(chat))
                    .build();
            session.getAsyncRemote().sendText(JSON.toJSONString(msgModel));
        }
        // 处理消息方
        ChatMsgModel chat = getChat(chatMessageInfoEntity);
        // 遍历session
        socketSessionsCache.forEach((k, v) -> {
            String userId = (String) v.getUserProperties().get("userId");
            if (chat.getChatType() == 1) {
                if (userId.equals(chat.getChatBusinessId())) {
                    WebSocketMsgModel msgModel = WebSocketMsgModel.builder()
                            .businessType(StreamSocketBusinessType.CHART_MSG.getType())
                            .uniqueId(msgData.getUniqueId())
                            .data(JSON.toJSONString(chat))
                            .build();
                    v.getAsyncRemote().sendText(JSON.toJSONString(msgModel));
                }
            } else {
                // 处理群聊
            }
        });
    }

    /**
     * 获取消息实体
     *
     * @param messageInfo
     * @return
     */
    private ChatMsgModel getChat(ChatMessageInfoEntity messageInfo) {
        ChatMsgModel model = ChatMsgModel.builder()
                .msgType(messageInfo.getMsgType())
                .chatType(messageInfo.getChatType())
                .sendMsgContent(messageInfo.getSendMsgContent())
                .sendUserId(messageInfo.getSendUserId())
                .msgSendTime(messageInfo.getMsgSendTime())
                .chatBusinessId(messageInfo.getChatBusinessId())
                .receiverId(messageInfo.getChatBusinessId())
                .type(2)
                .build();
        return model;
    }


}
