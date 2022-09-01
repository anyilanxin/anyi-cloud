// +----------------------------------------------------------------------
// | SkillFull快速开发平台 [ SkillFull ]
// +----------------------------------------------------------------------
// | 版权所有 2020~2021 zxiaozhou
// +----------------------------------------------------------------------
// | 官方网站: https://skillfull.divisu.com
// +----------------------------------------------------------------------
// | 作者: zxiaozhou <z7630853@163.com>
// +----------------------------------------------------------------------
package com.anyilanxin.skillfull.message.strategy.msgsubscribe;

import cn.hutool.core.collection.CollectionUtil;
import com.alibaba.fastjson2.JSONObject;
import com.anyilanxin.skillfull.message.utils.WsUtils;
import com.anyilanxin.skillfull.messagerpc.model.SubscribeMsgModel;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 消息处理
 *
 * @author zxiaozhou
 * @date 2022-08-27 11:58
 * @since JDK11
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
     * @author zxiaozhou
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
