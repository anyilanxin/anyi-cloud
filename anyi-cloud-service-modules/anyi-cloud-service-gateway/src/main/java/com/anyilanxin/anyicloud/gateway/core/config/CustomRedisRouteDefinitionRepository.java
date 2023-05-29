

package com.anyilanxin.anyicloud.gateway.core.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.route.RedisRouteDefinitionRepository;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.cloud.gateway.route.RouteDefinitionRepository;
import org.springframework.cloud.gateway.support.NotFoundException;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.data.redis.core.ReactiveValueOperations;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * 自定义redis存储路由(方便自定义redis key)
 *
 * @author zhou
 * @date 2022-07-18 11:33
 * @since 1.0.0
 */
public class CustomRedisRouteDefinitionRepository implements RouteDefinitionRepository {
    private static final Logger log = LoggerFactory.getLogger(RedisRouteDefinitionRepository.class);
    private static final String ROUTEDEFINITION_REDIS_KEY_PREFIX_QUERY = "SKILLFULL_GATEWAY_ROUTEDEFINITION:";
    private ReactiveRedisTemplate<String, RouteDefinition> reactiveRedisTemplate;
    private ReactiveValueOperations<String, RouteDefinition> routeDefinitionReactiveValueOperations;

    public CustomRedisRouteDefinitionRepository(ReactiveRedisTemplate<String, RouteDefinition> reactiveRedisTemplate) {
        this.reactiveRedisTemplate = reactiveRedisTemplate;
        this.routeDefinitionReactiveValueOperations = reactiveRedisTemplate.opsForValue();
    }


    public Flux<RouteDefinition> getRouteDefinitions() {
        return this.reactiveRedisTemplate.keys(this.createKey("*")).flatMap((key) -> {
            return this.reactiveRedisTemplate.opsForValue().get(key);
        }).onErrorContinue((throwable, routeDefinition) -> {
            if (log.isErrorEnabled()) {
                log.error("get routes from redis error cause : {}", throwable.toString(), throwable);
            }
        });
    }


    public Mono<Void> save(Mono<RouteDefinition> route) {
        return route.flatMap((routeDefinition) -> {
            return this.routeDefinitionReactiveValueOperations.set(this.createKey(routeDefinition.getId()), routeDefinition).flatMap((success) -> {
                return success ? Mono.empty() : Mono.defer(() -> {
                    return Mono.error(new RuntimeException(String.format("Could not add route to redis repository: %s", routeDefinition)));
                });
            });
        });
    }


    public Mono<Void> delete(Mono<String> routeId) {
        return routeId.flatMap((id) -> {
            return this.routeDefinitionReactiveValueOperations.delete(this.createKey(id)).flatMap((success) -> {
                return success ? Mono.empty() : Mono.defer(() -> {
                    return Mono.error(new NotFoundException(String.format("Could not remove route from redis repository with id: %s", routeId)));
                });
            });
        });
    }


    private String createKey(String routeId) {
        return ROUTEDEFINITION_REDIS_KEY_PREFIX_QUERY + routeId;
    }
}
