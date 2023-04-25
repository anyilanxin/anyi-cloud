package com.anyilanxin.skillfull.messagerpc.constant;

/**
 * 消息事件
 *
 * @author zxiaozhou
 * @date 2022-08-27 10:26
 * @since JDK11
 */
public interface SocketMessageEventContent {
    /**
     * 授权事件
     */
    String AUTH_EVENT = "AUTH_EVENT";

    /**
     * 异常事件
     */
    String ERROR_EVENT = "ERROR_EVENT";

    /**
     * 通知事件
     */
    String NOTICE_EVENT = "NOTICE_EVENT";

    /**
     * 业务事件
     */
    String BUSINESS_EVENT = "BUSINESS_EVENT";

    /**
     * 聊天事件
     */
    String CHAT_EVENT = "CHAT_EVENT";


    /**
     * 上下线通知
     */
    String UP_DOWN = "UP_DOWN";
}
