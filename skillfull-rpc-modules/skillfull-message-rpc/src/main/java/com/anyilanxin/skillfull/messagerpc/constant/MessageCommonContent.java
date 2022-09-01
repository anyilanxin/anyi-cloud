package com.anyilanxin.skillfull.messagerpc.constant;

/**
 * message公共常量
 *
 * @author zxiaozhou
 * @date 2022-08-10 21:06
 * @since JDK11
 */
public interface MessageCommonContent {
    /**
     * ping
     */
    String PING = "PING";

    /**
     * 正常关闭状态码
     */
    int normalCloseCode = 1000;

    /**
     * pong
     */
    String PONG = "PONG";
}
