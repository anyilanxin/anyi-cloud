

package com.anyilanxin.anyicloud.message.strategy.msgsubscribe;

import cn.hutool.core.collection.CollectionUtil;
import com.alibaba.fastjson2.JSONObject;
import com.anyilanxin.anyicloud.message.utils.WsUtils;
import com.anyilanxin.anyicloud.messagerpc.model.SubscribeMsgModel;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 消息处理
 *
 * @author zxh
 * @date 2022-08-27 11:58
 * @since 1.0.0
 */
@Component
public class MsgSubscribeContent {
    private static final Map<String, IMsgSubscribeStrategy> STRATEGY = new ConcurrentHashMap<>();

    @Autowired
    public MsgSubscribeContent(final Map<String, IMsgSubscribeStrategy> strategyMap) {
        STRATEGY.putAll(strategyMap);
    }


    /**
     * socket redis消息订阅处理
     *
     * @param msg
     * @author zxh
     * @date 2022-08-27 12:37
     */
    public void socketMsgHandle(String msg) {
        if (StringUtils.isNotBlank(msg) && CollectionUtil.isNotEmpty(STRATEGY)) {
            SubscribeMsgModel subscribeMsgModel = JSONObject.parseObject(msg, SubscribeMsgModel.class);
            IMsgSubscribeStrategy iMsgSubscribeStrategy = STRATEGY.get(subscribeMsgModel.getMessageEvent());
            if (Objects.nonNull(iMsgSubscribeStrategy)) {
                iMsgSubscribeStrategy.handleMsg(subscribeMsgModel, WsUtils.SESSION_POOL);
            }
        }
        System.out.println("-----msg------" + msg);
    }
}
