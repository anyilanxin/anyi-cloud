

package com.anyilanxin.anyicloud.message.modules.websocket.model;

/**
 * stream聊天消息
 *
 * @author zxh
 * @date 2022-03-29 17:02
 * @since 1.0.0
 */
public class StreamChatMsgModel {
    /**
     * 聊天会话id
     */
    private String chatSessionId;

    /**
     * 业务id,群id或者好友id
     */
    private String businessId;

    /**
     * 聊天类型：1-单聊，2-群聊
     */
    private Integer chatSessionType;

    /**
     * 发送用户id
     */
    private String userId;

    /**
     * 消息类型：1-文字，2-图片，3-文件，4-表情
     */
    private Integer msgType;

    /**
     * 聊天类型:1-单聊，2-群里
     */
    private Integer chatType;

    /**
     * 消息内容
     */
    private String sendMsgContent;

    /**
     * 消息发送人id
     */
    private String sendUserId;
}
