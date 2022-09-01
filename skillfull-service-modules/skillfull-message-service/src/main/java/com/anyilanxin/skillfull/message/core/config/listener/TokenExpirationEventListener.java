package com.anyilanxin.skillfull.message.core.config.listener;

import com.alibaba.fastjson2.JSONObject;
import com.alibaba.fastjson2.JSONWriter;
import com.anyilanxin.skillfull.corecommon.constant.CoreCommonCacheConstant;
import com.anyilanxin.skillfull.corecommon.constant.Status;
import com.anyilanxin.skillfull.coreredis.constant.RedisSubscribeConstant;
import com.anyilanxin.skillfull.coreredis.listener.RedisKeyExpirationEventMessageListener;
import com.anyilanxin.skillfull.coreredis.utils.SendRedisMsgUtils;
import com.anyilanxin.skillfull.messagerpc.constant.impl.SocketMessageEventType;
import com.anyilanxin.skillfull.messagerpc.model.AuthMsgModel;
import com.anyilanxin.skillfull.messagerpc.model.SubscribeMsgModel;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.lang.Nullable;

import java.nio.charset.StandardCharsets;

/**
 * 权限信息被删除监听
 *
 * @author zxiaozhou
 * @date 2021-07-09 22:02
 * @since JDK1.8
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
