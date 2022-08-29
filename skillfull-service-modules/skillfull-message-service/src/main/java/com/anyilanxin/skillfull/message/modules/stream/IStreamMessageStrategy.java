// +----------------------------------------------------------------------
// | SkillFull快速开发平台 [ SkillFull ]
// +----------------------------------------------------------------------
// | 版权所有 2020~2021 zxiaozhou
// +----------------------------------------------------------------------
// | 官方网站: https://skillfull.divisu.com
// +----------------------------------------------------------------------
// | 作者: zxiaozhou <z7630853@163.com>
// +----------------------------------------------------------------------
package com.anyilanxin.skillfull.message.modules.stream;

import com.anyilanxin.skillfull.messagerpc.model.StreamMsgModel;

import javax.websocket.Session;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 消息处理
 *
 * @author zxiaozhou
 * @date 2022-03-30 19:39
 * @since JDK1.8
 */
public interface IStreamMessageStrategy {

    /**
     * 处理stream流
     *
     * @param streamMsgModel
     * @param socketSessionsCache
     */
    void processMessage(StreamMsgModel streamMsgModel, ConcurrentHashMap<String, Session> socketSessionsCache);
}
