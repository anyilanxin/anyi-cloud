// +----------------------------------------------------------------------
// | SkillFull快速开发平台 [ SkillFull ]
// +----------------------------------------------------------------------
// | 版权所有 2020~2021 zxiaozhou
// +----------------------------------------------------------------------
// | 官方网站: https://www.divisu.com
// +----------------------------------------------------------------------
// | 作者: zxiaozhou <z7630853@163.com>
// +----------------------------------------------------------------------
package com.anyilanxin.skillfull.message.modules.stream;

import com.anyilanxin.skillfull.messagerpc.model.StreamMsgModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.websocket.Session;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 存储引擎上下文
 *
 * @author zxiaozhou
 * @date 2022-03-30 19:42
 * @since JDK1.8
 */
@Component
@Slf4j
public class StreamMessageHandleContent {
    private static final Map<String, IStreamMessageStrategy> STRATEGY = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<String, Session> socketSessionsCache;


    @Autowired
    public StreamMessageHandleContent(final Map<String, IStreamMessageStrategy> strategyMap,
                                      final ConcurrentHashMap<String, Session> socketSessionsCache) {
        STRATEGY.putAll(strategyMap);
        this.socketSessionsCache = socketSessionsCache;
    }


    public void processStream(StreamMsgModel streamMsgModel) {
        IStreamMessageStrategy iMessageStrategy = STRATEGY.get(streamMsgModel.getBusinessType().getType());
        if (Objects.nonNull(iMessageStrategy)) {
            iMessageStrategy.processMessage(streamMsgModel, socketSessionsCache);
        } else {
            log.error("------------MessageHandleContent------------>processStream--->{}", "未查到处理方法");
        }
    }
}
