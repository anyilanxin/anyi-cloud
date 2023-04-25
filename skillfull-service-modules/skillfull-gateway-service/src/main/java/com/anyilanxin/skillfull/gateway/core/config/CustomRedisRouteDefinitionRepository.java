/*
 * Copyright (c) 2021-2022 ZHOUXUANHONG(安一老厨)<anyilanxin@aliyun.com>
 *
 * AnYi Cloud Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * AnYi Cloud 采用APACHE LICENSE 2.0开源协议，您在使用过程中，需要注意以下几点：
 *   1.请不要删除和修改根目录下的LICENSE文件；
 *   2.请不要删除和修改 AnYi Cloud 源码头部的版权声明；
 *   3.请保留源码和相关描述文件的项目出处，作者声明等；
 *   4.分发源码时候，请注明软件出处 https://github.com/anyilanxin/anyi-cloud；
 *   5.在修改包名，模块名称，项目代码等时，请注明软件出处 https://github.com/anyilanxin/anyi-cloud；
 *   6.本软件不允许在国家法律规定范围外使用，如出现违法行为原作者本人不承担任何法律风险；
 *   7.本软件使用的第三方依赖皆为开源软件，如需要修改第三方源码请遵循第三方源码附带开源协议；
 *   8.本软件流程部分请遵循camunda开源协议：
 *     https://docs.camunda.org/manual/latest/introduction/third-party-libraries
 *     https://github.com/camunda/camunda-bpm-platform/blob/master/LICENSE
 *   9.若您的项目无法满足以上几点，可申请商业授权。
 */


package com.anyilanxin.skillfull.gateway.core.config;

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
 * @since JDK11
 */
public class CustomRedisRouteDefinitionRepository implements RouteDefinitionRepository {
    private static final Logger log = LoggerFactory.getLogger(RedisRouteDefinitionRepository.class);
    private static final String ROUTEDEFINITION_REDIS_KEY_PREFIX_QUERY =
            "SKILLFULL_GATEWAY_ROUTEDEFINITION:";
    private ReactiveRedisTemplate<String, RouteDefinition> reactiveRedisTemplate;
    private ReactiveValueOperations<String, RouteDefinition> routeDefinitionReactiveValueOperations;

    public CustomRedisRouteDefinitionRepository(
            ReactiveRedisTemplate<String, RouteDefinition> reactiveRedisTemplate) {
        this.reactiveRedisTemplate = reactiveRedisTemplate;
        this.routeDefinitionReactiveValueOperations = reactiveRedisTemplate.opsForValue();
    }

    public Flux<RouteDefinition> getRouteDefinitions() {
        return this.reactiveRedisTemplate
                .keys(this.createKey("*"))
                .flatMap(
                        (key) -> {
                            return this.reactiveRedisTemplate.opsForValue().get(key);
                        })
                .onErrorContinue(
                        (throwable, routeDefinition) -> {
                            if (log.isErrorEnabled()) {
                                log.error(
                                        "get routes from redis error cause : {}", throwable.toString(), throwable);
                            }
                        });
    }

    public Mono<Void> save(Mono<RouteDefinition> route) {
        return route.flatMap(
                (routeDefinition) -> {
                    return this.routeDefinitionReactiveValueOperations
                            .set(this.createKey(routeDefinition.getId()), routeDefinition)
                            .flatMap(
                                    (success) -> {
                                        return success
                                                ? Mono.empty()
                                                : Mono.defer(
                                                () -> {
                                                    return Mono.error(
                                                            new RuntimeException(
                                                                    String.format(
                                                                            "Could not add route to redis repository: %s",
                                                                            routeDefinition)));
                                                });
                                    });
                });
    }

    public Mono<Void> delete(Mono<String> routeId) {
        return routeId.flatMap(
                (id) -> {
                    return this.routeDefinitionReactiveValueOperations
                            .delete(this.createKey(id))
                            .flatMap(
                                    (success) -> {
                                        return success
                                                ? Mono.empty()
                                                : Mono.defer(
                                                () -> {
                                                    return Mono.error(
                                                            new NotFoundException(
                                                                    String.format(
                                                                            "Could not remove route from redis repository with id: %s",
                                                                            routeId)));
                                                });
                                    });
                });
    }

    private String createKey(String routeId) {
        return ROUTEDEFINITION_REDIS_KEY_PREFIX_QUERY + routeId;
    }
}
