package com.anyilanxin.skillfull.coreredis.utils;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * 消息定于发布工具
 *
 * @author zhou
 * @date 2022-07-21 20:02
 * @since JDK11
 */
@RequiredArgsConstructor
@Component
public class SendRedisMsgUtils {

    private final StringRedisTemplate stringRedisTemplate;
    private static SendRedisMsgUtils utils;

    @PostConstruct
    private void init() {
        utils = this;
    }


    /**
     * 订阅消息发送工具
     *
     * @param channel
     * @param msg
     * @author zxiaozhou
     * @date 2022-07-21 20:03
     */
    public static void sendMsg(String channel, String msg) {
        utils.stringRedisTemplate.convertAndSend(channel, msg);
    }
}
