// +----------------------------------------------------------------------
// | SkillFull快速开发平台 [ SkillFull ]
// +----------------------------------------------------------------------
// | 版权所有 2020~2021 zxiaozhou
// +----------------------------------------------------------------------
// | 官方网站: https://skillfull.divisu.com
// +----------------------------------------------------------------------
// | 作者: zxiaozhou <z7630853@163.com>
// +----------------------------------------------------------------------
package com.anyilanxin.skillfull.message.strategy.afterconnection.impl;

import com.anyilanxin.skillfull.message.strategy.afterconnection.AfterConnectionStrategy;
import com.anyilanxin.skillfull.message.utils.WsUtils;
import com.anyilanxin.skillfull.messagerpc.constant.impl.SocketMessageEventType;
import com.anyilanxin.skillfull.messagerpc.model.NoticeMsgModel;
import com.anyilanxin.skillfull.messagerpc.model.SocketMsgModel;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketSession;

/**
 * 发送注意通知
 *
 * @author zxiaozhou
 * @date 2022-08-28 21:15
 * @since JDK11
 */
@Component
public class IAfterConnectionSentNoticeImpl implements AfterConnectionStrategy {
    @Override
    public void handleAfterMsg(WebSocketSession session) {
        for (int i = 0; i < 5; i++) {
            SocketMsgModel msgModel = new SocketMsgModel(SocketMessageEventType.NOTICE_EVENT);
            NoticeMsgModel model = new NoticeMsgModel();
            model.setType(1);
            model.setShowType(1);
            model.setData("请维护好环境，别乱删数据！！！！！！！！！有问题进群：666029437；");
            msgModel.setData(model);
            WsUtils.sendMsg(session, msgModel);
        }
    }
}
