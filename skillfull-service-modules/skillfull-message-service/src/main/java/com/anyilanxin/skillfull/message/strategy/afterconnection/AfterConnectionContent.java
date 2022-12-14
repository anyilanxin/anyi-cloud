// +----------------------------------------------------------------------
// | SkillFull快速开发平台 [ SkillFull ]
// +----------------------------------------------------------------------
// | 版权所有 2020~2021 zxiaozhou
// +----------------------------------------------------------------------
// | 官方网站: https://skillfull.divisu.com
// +----------------------------------------------------------------------
// | 作者: zxiaozhou <z7630853@163.com>
// +----------------------------------------------------------------------
package com.anyilanxin.skillfull.message.strategy.afterconnection;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketSession;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * afterConnection处理
 *
 * @author zxiaozhou
 * @date 2022-08-27 11:32
 * @since JDK11
 */
@Component
@Slf4j
public class AfterConnectionContent {
    private static final Map<String, AfterConnectionStrategy> STRATEGY = new ConcurrentHashMap<>();

    @Autowired
    public AfterConnectionContent(final Map<String, AfterConnectionStrategy> strategyMap) {
        STRATEGY.putAll(strategyMap);
    }


    /**
     * 链接后处理
     *
     * @author zxiaozhou
     * @date 2022-08-27 12:42
     */
    @Async
    public void afterConnectionHandle(WebSocketSession session) {
        STRATEGY.forEach((k, v) -> {
            try {
                v.handleAfterMsg(session);
            } catch (Exception e) {
                log.error("------------------------afterConnectionHandle--->\n参数:\n{}\n异常消息:\n{}", session.getAttributes(), e.getMessage());
            }
        });
    }
}
