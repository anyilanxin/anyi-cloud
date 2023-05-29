

package com.anyilanxin.anyicloud.message.strategy.afterconnection.impl;

import com.anyilanxin.anyicloud.message.strategy.afterconnection.AfterConnectionStrategy;
import com.anyilanxin.anyicloud.message.utils.WsUtils;
import com.anyilanxin.anyicloud.messagerpc.constant.impl.SocketMessageEventType;
import com.anyilanxin.anyicloud.messagerpc.model.NoticeMsgModel;
import com.anyilanxin.anyicloud.messagerpc.model.SocketMsgModel;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketSession;

/**
 * 发送注意通知
 *
 * @author zxh
 * @date 2022-08-28 21:15
 * @since 1.0.0
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
