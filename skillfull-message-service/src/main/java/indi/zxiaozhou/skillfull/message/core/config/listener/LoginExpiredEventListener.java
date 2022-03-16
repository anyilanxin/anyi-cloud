// +----------------------------------------------------------------------
// | SkillFull快速开发平台 [ SkillFull ]
// +----------------------------------------------------------------------
// | 版权所有 2020~2021 zxiaozhou
// +----------------------------------------------------------------------
// | 官方网站: https://www.divisu.com
// +----------------------------------------------------------------------
// | 作者: zxiaozhou <z7630853@163.com>
// +----------------------------------------------------------------------
package indi.zxiaozhou.skillfull.message.core.config.listener;

import indi.zxiaozhou.skillfull.corecommon.base.SocketResult;
import indi.zxiaozhou.skillfull.corecommon.constant.Status;
import indi.zxiaozhou.skillfull.corecommon.constant.SysBaseConstant;
import indi.zxiaozhou.skillfull.corecommon.constant.impl.BusinessType;
import indi.zxiaozhou.skillfull.message.modules.websocket.model.WebSocketLinkModel;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.listener.KeyExpirationEventMessageListener;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;

import java.nio.charset.StandardCharsets;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
 * redis登录信息过期监听
 *
 * @author zxiaozhou
 * @date 2021-01-25 21:22
 * @since JDK11
 */
@Slf4j
public class LoginExpiredEventListener extends KeyExpirationEventMessageListener {
    private final ConcurrentHashMap<String, WebSocketLinkModel> senderMsgMap;

    public LoginExpiredEventListener(RedisMessageListenerContainer listenerContainer,
                                     ConcurrentHashMap<String, WebSocketLinkModel> senderMsgMap) {
        super(listenerContainer);
        this.senderMsgMap = senderMsgMap;
    }


    @Override
    public void onMessage(Message message, byte[] pattern) {
        log.debug("------------LoginExpiredEventListener------监听到编号------>onMessage:\n{}", message);
        // 登录过期key校验
        String key = new String(message.getBody(), StandardCharsets.UTF_8);
        if (StringUtils.isNotBlank(key)) {
            if (key.startsWith(SysBaseConstant.LOGIN_ONLINE_PREFIX)) {
                String[] s = key.split(":");
                String loginCode = s[s.length - 1];
                WebSocketLinkModel remove = senderMsgMap.remove(loginCode);
                if (Objects.nonNull(remove)) {
                    SocketResult socketResult = new SocketResult(BusinessType.TOKEN_EXPIRED).setMessage(Status.TOKEN_EXPIRED.getMessage());
                    remove.sendDataAndClose(socketResult.toString());
                }
            }

        }
    }
}
