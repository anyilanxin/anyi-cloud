

package com.anyilanxin.skillfull.storage.modules.common.service.impl;

import com.anyilanxin.anyicloud.coremvc.base.service.ICoreWebmvcService;
import com.anyilanxin.anyicloud.coremvc.config.properties.CoreWebMvcProperty;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

/**
 * 公共接口定义实现
 *
 * @author zxh
 * @date 2021-07-27 10:10
 * @since 1.0.0
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class CoreWebmvcServiceImpl implements ICoreWebmvcService {
    private final RedisTemplate<String, Object> redisTemplate;
    private final CoreWebMvcProperty property;

    @Override
    public void loadConstantDict(boolean force) {
        ICoreWebmvcService.loadConstantDict(force, redisTemplate, property.getServiceName());
    }
}
