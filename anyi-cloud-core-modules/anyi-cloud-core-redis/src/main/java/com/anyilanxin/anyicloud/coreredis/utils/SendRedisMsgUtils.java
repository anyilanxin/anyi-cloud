

package com.anyilanxin.anyicloud.coreredis.utils;

import javax.annotation.PostConstruct;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

/**
 * 消息定于发布工具
 *
 * @author zhou
 * @date 2022-07-21 20:02
 * @since 1.0.0
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
     * @author zxh
     * @date 2022-07-21 20:03
     */
    public static void sendMsg(String channel, String msg) {
        utils.stringRedisTemplate.convertAndSend(channel, msg);
    }
}
