

package com.anyilanxin.anyicloud.gateway.modules.common.service.impl;

import com.anyilanxin.anyicloud.corewebflux.base.service.ICoreWebfluxService;
import com.anyilanxin.anyicloud.corewebflux.config.properfy.CoreWebFluxAppProperty;
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
public class CoreWebfluxServiceImpl implements ICoreWebfluxService {
    private final RedisTemplate<String, Object> redisTemplate;
    private final CoreWebFluxAppProperty property;

    @Override
    public void loadConstantDict(boolean force) {
        ICoreWebfluxService.loadConstantDict(force, redisTemplate, property.getServiceName());
    }
}
