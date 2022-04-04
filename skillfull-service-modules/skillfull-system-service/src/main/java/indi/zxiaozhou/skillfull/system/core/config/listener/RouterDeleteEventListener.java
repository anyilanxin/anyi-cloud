package indi.zxiaozhou.skillfull.system.core.config.listener;

import indi.zxiaozhou.skillfull.corecommon.constant.CoreCommonCacheConstant;
import indi.zxiaozhou.skillfull.coremvc.listener.RedisKeyDeleteEventMessageListener;
import indi.zxiaozhou.skillfull.system.modules.manage.service.IManageSyncService;
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
        if (StringUtils.isNotBlank(key) && key.equals(CoreCommonCacheConstant.SYSTEM_ROUTE_INFO_CACHE) && !super.serviceLock(key)) {
            syncService.syncRoute(true);
        }
    }
}
