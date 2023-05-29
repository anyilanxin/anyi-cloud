

package com.anyilanxin.anyicloud.messagerpc.constant;

/**
 * stream与socket消息类型
 *
 * @author zxh
 * @date 2020-09-12 10:52
 * @since 1.0.0
 */
public interface StreamSocketBusinessConstant {

    /**
     * 流程消息
     */
    String PROCESS_MSG = "PROCESS_MSG";

    /**
     * 授权消息
     */
    String AUTH_MSG = "AUTH_MSG";

    /**
     * 异常消息
     */
    String ERROR_MSG = "ERROR_MSG";

    /**
     * 系统消息
     */
    String SYSTEM_NOTICE = "SYSTEM_NOTICE";

    /**
     * 业务消息
     */
    String BUSINESS_MSG = "BUSINESS_MSG";

    /**
     * 聊天消息
     */
    String CHART_MSG = "CHART_MSG";

    /**
     * 聊天消息stream广播(消息服务内部使用)
     */
    String CHART_RADIO_MSG = "CHART_RADIO_MSG";
}
