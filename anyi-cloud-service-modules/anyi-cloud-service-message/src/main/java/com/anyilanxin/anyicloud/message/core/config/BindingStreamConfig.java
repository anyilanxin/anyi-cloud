

package com.anyilanxin.anyicloud.message.core.config;

import com.anyilanxin.anyicloud.messagerpc.model.SocketMsgModel;
import com.anyilanxin.anyicloud.stream.constant.BindingStreamConstant;

import java.util.function.Consumer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * stream配置
 *
 * @author zxh
 * @date 2021-05-29 16:59
 * @since 1.0.0
 */
@Slf4j
@Configuration
@RequiredArgsConstructor
public class BindingStreamConfig {
    // private final StreamMessageHandleContent handleContent;

    /**
     * 处理socket广播
     *
     * @author zxh
     * @date 2021-05-29 17:01
     */
    @Bean(value = BindingStreamConstant.SOCKET_PROCESS)
    public Consumer<SocketMsgModel> socketProcess() {
        return payload -> {
            log.debug("------------BindingStreamConfig------收到消息------>socketProcess:\n{}", payload);
            // handleContent.processStream(payload);
        };
    }
}
