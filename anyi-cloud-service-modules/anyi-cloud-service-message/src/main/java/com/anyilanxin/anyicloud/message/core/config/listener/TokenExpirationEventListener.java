

package com.anyilanxin.anyicloud.message.core.config.listener;

import com.alibaba.fastjson2.JSONObject;
import com.alibaba.fastjson2.JSONWriter;
import com.anyilanxin.anyicloud.corecommon.constant.CoreCommonCacheConstant;
import com.anyilanxin.anyicloud.corecommon.constant.Status;
import com.anyilanxin.anyicloud.coreredis.constant.RedisSubscribeConstant;
import com.anyilanxin.anyicloud.coreredis.listener.RedisKeyExpirationEventMessageListener;
import com.anyilanxin.anyicloud.coreredis.utils.SendRedisMsgUtils;
import com.anyilanxin.anyicloud.messagerpc.constant.impl.SocketMessageEventType;
import com.anyilanxin.anyicloud.messagerpc.model.AuthMsgModel;
import com.anyilanxin.anyicloud.messagerpc.model.SubscribeMsgModel;

import java.nio.charset.StandardCharsets;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.lang.Nullable;

/**
 * 权限信息被删除监听
 *
 * @author zxh
 * @date 2021-07-09 22:02
 * @since 1.0.0
 */
@Slf4j
public class TokenExpirationEventListener extends RedisKeyExpirationEventMessageListener {

    public TokenExpirationEventListener(RedisMessageListenerContainer listenerContainer) {
        super(listenerContainer);
    }


    @Override
    public void onMessage(Message message, @Nullable byte[] pattern) {
        String key = new String(message.getBody(), StandardCharsets.UTF_8);
        log.debug("------------------------>onMessage:\n{}", key);
        if (StringUtils.isNotBlank(key) && key.startsWith(CoreCommonCacheConstant.AUTH_PREFIX + "auth:") && !super.serviceLock(key)) {
            // 广播集群再转给socket处理
            SubscribeMsgModel subscribeMsgModel = new SubscribeMsgModel(SocketMessageEventType.AUTH_EVENT);
            AuthMsgModel model = new AuthMsgModel();
            model.setToken(key.replaceFirst(CoreCommonCacheConstant.AUTH_PREFIX + "auth:", ""));
            model.setType(Status.TOKEN_EXPIRED);
            model.setMessage(Status.ERROR.getMessage());
            subscribeMsgModel.setData(model);
            SendRedisMsgUtils.sendMsg(RedisSubscribeConstant.MESSAGE_SOCKET_HANDLE, JSONObject.toJSONString(subscribeMsgModel, JSONWriter.Feature.WriteMapNullValue));
        }
    }
}
