// +----------------------------------------------------------------------
// | SkillFull快速开发平台 [ SkillFull ]
// +----------------------------------------------------------------------
// | 版权所有 2020~2021 zxiaozhou
// +----------------------------------------------------------------------
// | 官方网站: https://skillfull.divisu.com
// +----------------------------------------------------------------------
// | 作者: zxiaozhou <z7630853@163.com>
// +----------------------------------------------------------------------
package com.anyilanxin.skillfull.message.modules.stream.impl;

import com.anyilanxin.skillfull.message.modules.stream.IStreamMessageStrategy;
import com.anyilanxin.skillfull.messagerpc.constant.StreamSocketBusinessConstant;
import com.anyilanxin.skillfull.messagerpc.model.StreamMsgModel;
import org.springframework.stereotype.Component;

import javax.websocket.Session;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 业务消息
 *
 * @author zxiaozhou
 * @date 2022-03-29 17:36
 * @since JDK1.8
 */
@Component(StreamSocketBusinessConstant.BUSINESS_MSG)
public class BusinessMsgMessageStrategyImpl implements IStreamMessageStrategy {
    @Override
    public void processMessage(StreamMsgModel streamMsgModel, ConcurrentHashMap<String, Session> socketSessionsCache) {

    }
}
