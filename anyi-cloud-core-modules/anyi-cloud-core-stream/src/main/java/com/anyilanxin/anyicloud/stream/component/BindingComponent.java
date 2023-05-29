

package com.anyilanxin.anyicloud.stream.component;

import com.anyilanxin.anyicloud.stream.constant.BindingStreamConstant;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * stream工具类
 *
 * @author zxh
 * @date 2021-05-29 17:11
 * @since 1.0.0
 */
@RequiredArgsConstructor
@Component
@Slf4j
@Async
public class BindingComponent {
    private static final String TTL_KEY = "x-message-ttl";
    private final StreamBridge streamBridge;

    /**
     * 发送消息并设置未消费过期时间
     *
     * @param bindingName ${@link String} bind名称
     * @param t           ${@link Object} 数据
     * @param ttl         ${@link Long} 过期时间,单位:s
     * @author zxh
     * @date 2021-07-29 17:52
     */
    public <T> void out(String bindingName, T t, long ttl) {
        MessageBuilder<T> messageBuilder = MessageBuilder.withPayload(t);
        if (ttl > 0) {
            messageBuilder.setHeader(TTL_KEY, ttl);
        }
        Message<T> stringMessage = messageBuilder.build();
        log.debug("------------StreamUtil------发送消息------>out:{}", stringMessage);
        streamBridge.send(bindingName + BindingStreamConstant.OUT_SUFFIX, stringMessage);
    }


    /**
     * 发送消息
     *
     * @param bindingName ${@link String} bind名称
     * @param t           ${@link Object} 数据
     * @author zxh
     * @date 2021-08-30 15:32
     */
    public <T> void out(String bindingName, T t) {
        out(bindingName, t, 0);
    }
}
