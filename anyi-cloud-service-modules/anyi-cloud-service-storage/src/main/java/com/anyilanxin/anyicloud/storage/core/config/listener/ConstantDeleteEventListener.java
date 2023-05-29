

package com.anyilanxin.skillfull.storage.core.config.listener;

import com.anyilanxin.anyicloud.corecommon.constant.CoreCommonCacheConstant;
import com.anyilanxin.anyicloud.coremvc.base.service.ICoreWebmvcService;
import com.anyilanxin.anyicloud.coreredis.listener.RedisKeyDeleteEventMessageListener;

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
public class ConstantDeleteEventListener extends RedisKeyDeleteEventMessageListener {
    private final ICoreWebmvcService coreCommonService;

    public ConstantDeleteEventListener(RedisMessageListenerContainer listenerContainer, ICoreWebmvcService coreCommonService) {
        super(listenerContainer);
        this.coreCommonService = coreCommonService;
    }


    @Override
    public void onMessage(Message message, @Nullable byte[] pattern) {
        log.debug("------------ConstantDeleteEventListener------监听到变化------>onMessage:\n{}", message);
        String key = new String(message.getBody(), StandardCharsets.UTF_8);
        if (StringUtils.isNotBlank(key) && key.startsWith(CoreCommonCacheConstant.ENGINE_CONSTANT_DICT_CACHE) && !super.serviceLock(key)) {
            coreCommonService.loadConstantDict(true);
        }
    }
}
