package com.anyilanxin.skillfull.auth.modules.common.service.impl;

import com.anyilanxin.skillfull.coremvc.base.service.ICoreWebmvcService;
import com.anyilanxin.skillfull.coremvc.config.properties.CoreWebMvcProperty;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

/**
 * 公共接口定义实现
 *
 * @author zxiaozhou
 * @date 2021-07-27 10:10
 * @since JDK1.8
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
