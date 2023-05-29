

package com.anyilanxin.anyicloud.system.core.config.listener;

import com.anyilanxin.anyicloud.corecommon.constant.CoreCommonCacheConstant;
import com.anyilanxin.anyicloud.coreredis.listener.RedisKeyDeleteEventMessageListener;
import com.anyilanxin.anyicloud.system.modules.manage.service.IManageSyncService;

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
public class RouterDeleteEventListener extends RedisKeyDeleteEventMessageListener {
    private final IManageSyncService syncService;

    public RouterDeleteEventListener(RedisMessageListenerContainer listenerContainer, IManageSyncService syncService) {
        super(listenerContainer);
        this.syncService = syncService;
    }


    @Override
    public void onMessage(Message message, @Nullable byte[] pattern) {
        log.debug("------------RouterInfoDeleteEventListener------监听到变化------>onMessage:\n{}", message);
        String key = new String(message.getBody(), StandardCharsets.UTF_8);
        if (StringUtils.isNotBlank(key)) {
            if (key.equals(CoreCommonCacheConstant.SYSTEM_ROUTE_INFO_CACHE_PREFIX) && !super.serviceLock(key)) {
                syncService.reloadRoute(true);
            }
        }
    }
}
