package com.anyilanxin.skillfull.gateway.core.config;

import com.alibaba.fastjson2.JSONObject;
import com.anyilanxin.skillfull.corecommon.constant.CoreCommonCacheConstant;
import com.anyilanxin.skillfull.corecommon.model.stream.router.SystemRouterModel;
import com.anyilanxin.skillfull.gateway.modules.manage.service.IDynamicRouteService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.core.ReactiveStringRedisTemplate;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

/**
 * @author zhou
 * @date 2022-07-21 15:33
 * @since JDK11
 */
@Component
@Slf4j
@RequiredArgsConstructor
public class RedisSubscribeListener {
    private final ReactiveStringRedisTemplate reactiveStringRedisTemplate;
    private final IDynamicRouteService routeService;

    public void reloadRouter(String msg) {
        log.info("-----------reloadApi------------->收到消息:{}", msg);
        routeService.loadRoute();
    }


    public void deleteRouter(String msg) {
        log.info("-----------deleteApi------------->收到消息:{}", msg);
        routeService.deleteRoute(msg);
    }


    public void updateRouter(String msg) {
        log.info("----------updateApi-------------->收到消息:{}", msg);
        reactiveStringRedisTemplate.opsForValue().get(CoreCommonCacheConstant.SYSTEM_ROUTE_INFO_CACHE_PREFIX + msg).flatMap(item -> {
                    if (StringUtils.isNotBlank(item)) {
                        SystemRouterModel vo = JSONObject.parseObject(item, SystemRouterModel.class);
                        routeService.updateRoute(vo);
                    }
                    return Mono.empty();
                })
                .onErrorContinue((throwable, routeDefinition) -> {
                    if (log.isErrorEnabled()) {
                        log.error("get routes from redis error cause : {}", throwable.toString(), throwable);
                    }
                }).subscribe();
    }
}
