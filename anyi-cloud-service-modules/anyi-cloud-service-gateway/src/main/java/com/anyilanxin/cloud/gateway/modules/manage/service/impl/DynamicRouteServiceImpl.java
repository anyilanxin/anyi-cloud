/*
 * Copyright (c) 2023-present ZHOUXUANHONG(安一老厨)<anyilanxin@aliyun.com>
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
 *   1.请不要删除和修改根目录下的LICENSE.txt文件；
 *   2.请不要删除和修改 AnYi Cloud 源码头部的版权声明；
 *   3.请保留源码和相关描述文件的项目出处，作者声明等；
 *   4.分发源码时候，请注明软件出处 https://github.com/anyilanxin/anyi-cloud；
 *   5.在修改包名，模块名称，项目代码等时，请注明软件出处 https://github.com/anyilanxin/anyi-cloud；
 *   6.本软件不允许在国家法律规定范围外使用，如出现违法行为原作者本人不承担任何法律风险；
 *   7.进行商用时，不得基于AnYi Cloud的基础，修改包装而成一个与AnYi Cloud EE、AnYi Zeebe EE、AnYi Standalone EE功能类似的程序，进行销售或发布，参与同类软件产品市场的竞争；
 *   8.本软件使用的第三方依赖皆为开源软件，如需要修改第三方源码请遵循第三方源码附带开源协议；
 *   9.本软件流程部分请遵循camunda开源协议：
 *     https://docs.camunda.org/manual/latest/introduction/third-party-libraries
 *     https://github.com/camunda/camunda-bpm-platform/blob/master/LICENSE
 *   10.若您的项目无法满足以上几点，可申请商业授权。
 */

package com.anyilanxin.cloud.gateway.modules.manage.service.impl;

import com.alibaba.fastjson2.JSONObject;
import com.anyilanxin.cloud.corecommon.constant.CoreCommonCacheConstant;
import com.anyilanxin.cloud.corecommon.exception.AnYiResponseException;
import com.anyilanxin.cloud.corecommon.model.stream.router.RouteFilterModel;
import com.anyilanxin.cloud.corecommon.model.stream.router.RoutePredicateModel;
import com.anyilanxin.cloud.corecommon.model.stream.router.SystemRouterModel;
import com.anyilanxin.cloud.gateway.core.constant.CommonGatewayConstant;
import com.anyilanxin.cloud.gateway.core.constant.typeimpl.*;
import com.anyilanxin.cloud.gateway.modules.manage.controller.vo.GatewayRouteVo;
import com.anyilanxin.cloud.gateway.modules.manage.service.IDynamicRouteService;
import com.anyilanxin.cloud.gatewayadapter.model.RouteResponseModel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.dromara.hutool.core.collection.CollUtil;
import org.springframework.cloud.gateway.event.RefreshRoutesEvent;
import org.springframework.cloud.gateway.filter.FilterDefinition;
import org.springframework.cloud.gateway.handler.predicate.PredicateDefinition;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.cloud.gateway.route.RouteDefinitionRepository;
import org.springframework.cloud.gateway.support.NotFoundException;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.redis.core.ReactiveStringRedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.net.URI;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 动态路由服务
 *
 * @author zxh
 * @date 2020-09-10 21:10
 * @since 1.0.0
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class DynamicRouteServiceImpl implements IDynamicRouteService {
    private final ApplicationEventPublisher publisher;
    private final ReactiveStringRedisTemplate reactiveStringRedisTemplate;
    private final RouteDefinitionRepository routeDefinitionRepository;

    @Override
    public void addRoute(SystemRouterModel vo) throws RuntimeException {
        try {
            var routeDefinition = getRouteDefinition(vo);
            if (Objects.nonNull(routeDefinition)) {
                routeDefinitionRepository.save(Mono.just(routeDefinition)).subscribe();
            }
        } catch (Exception e) {
            throw new AnYiResponseException("添加路由失败:" + e.getMessage());
        }
        notifyChanged();
    }


    @Override
    public void updateRoute(SystemRouterModel vo) throws RuntimeException {
        addRoute(vo);
    }


    @Override
    public void deleteRoute(String routeId) throws RuntimeException {
        routeDefinitionRepository.delete(Mono.just(routeId)).then(Mono.defer(() -> Mono.just(ResponseEntity.ok().build()))).onErrorResume(t -> t instanceof NotFoundException, t -> Mono.just(ResponseEntity.notFound().build())).subscribe();
        notifyChanged();
    }


    @Override
    public Flux<RouteResponseModel> getRoutes() throws RuntimeException {
        return routeDefinitionRepository.getRouteDefinitions().map(v -> {
            // 处理基础部分
            var dto = new RouteResponseModel();
            dto.setRouteCode(v.getId());
            dto.setRouteOrder(v.getOrder());
            var url = v.getUri().toString().toLowerCase();
            dto.setIsLoadBalancer(0);
            if (url.startsWith(CommonGatewayConstant.LB_PREFIX)) {
                dto.setIsLoadBalancer(1);
                var startMatch = LbType.getStartMatch(url);
                if (Objects.nonNull(startMatch)) {
                    dto.setLoadBalancerType(startMatch.getTypeName());
                    dto.setServiceName(url.replace(startMatch.getTypeName(), ""));
                }
            } else {
                dto.setUrl(url);
            }
            // 处理断言
            var routePredicates = new ArrayList<RouteResponseModel.RoutePredicate>();
            var predicates = v.getPredicates();
            if (!CollectionUtils.isEmpty(predicates)) {
                predicates.forEach(sv -> {
                    var predicate = new RouteResponseModel.RoutePredicate();
                    predicate.setPredicateType(sv.getName());
                    var rules = new HashSet<RouteResponseModel.Rule>();
                    var args = sv.getArgs();
                    if (!CollectionUtils.isEmpty(args)) {
                        args.forEach((k, ssv) -> {
                            var rule = new RouteResponseModel.Rule();
                            rule.setRuleName(k);
                            rule.setRuleValue(ssv);
                            rules.add(rule);
                        });
                    }
                    predicate.setRules(rules);
                    routePredicates.add(predicate);
                });
                dto.setRoutePredicates(routePredicates);
            }
            // 处理过滤器
            var routeFilters = new ArrayList<RouteResponseModel.RouteFilter>();
            var filters = v.getFilters();
            if (!CollectionUtils.isEmpty(filters)) {
                filters.forEach(sv -> {
                    var filter = new RouteResponseModel.RouteFilter();
                    filter.setFilterType(sv.getName());
                    var rules = new HashSet<RouteResponseModel.Rule>();
                    var args = sv.getArgs();
                    if (!CollectionUtils.isEmpty(args)) {
                        args.forEach((k, ssv) -> {
                            var rule = new RouteResponseModel.Rule();
                            rule.setRuleValue(ssv);
                            rule.setRuleName(k);
                            rules.add(rule);
                        });
                    }
                    filter.setRules(rules);
                    routeFilters.add(filter);
                });
                dto.setRouteFilters(routeFilters);
            }
            return dto;
        });
    }


    @Override
    public void loadRoute() throws RuntimeException {
        reactiveStringRedisTemplate.keys(CoreCommonCacheConstant.SYSTEM_ROUTE_INFO_CACHE_PREFIX + "*").flatMap(key -> reactiveStringRedisTemplate.opsForValue().get(key).mapNotNull(item -> {
            var vo = JSONObject.parseObject(item, SystemRouterModel.class);
            return getRouteDefinition(vo);
        })).collectList().mapNotNull(v -> v.stream().collect(Collectors.toMap(RouteDefinition::getId, sv -> sv))).switchIfEmpty(Mono.empty()).map(v -> routeDefinitionRepository.getRouteDefinitions().collectList().map(items -> {
            items.forEach(item -> {
                var routeDefinition = v.get(item.getId());
                if (Objects.isNull(v.get(item.getId()))) {
                    deleteRoute(item.getId());
                } else {
                    if (JSONObject.toJSONString(item).equals(JSONObject.toJSONString(routeDefinition))) {
                        v.remove(item.getId());
                    }
                }
            });
            return v;
        }).map(sv -> {
            sv.forEach((ssk, ssv) -> {
                routeDefinitionRepository.save(Mono.just(ssv)).subscribe();
                notifyChanged();
            });
            return Mono.empty();
        }).subscribeOn(Schedulers.boundedElastic()).subscribe()).onErrorContinue((throwable, routeDefinition) -> {
            if (log.isErrorEnabled()) {
                log.error("get routes from redis error cause : {}", throwable.toString(), throwable);
            }
        }).subscribe();
    }


    /**
     * 把传递进来的参数转换成路由对象
     *
     * @param vo ${@link GatewayRouteVo}
     * @return RouteDefinition ${@link RouteDefinition}
     * @author zxh
     * @date 2020-09-10 21:15
     */
    private RouteDefinition getRouteDefinition(SystemRouterModel vo) {
        /*
         * 数据校验 1.如果是负载均衡器负载均衡器类型必填,服务名必填,并且均衡器类型只能为0,1,2
         * 2.如果非负载均衡器,url必填并且开头必须是http,https,ws,wss
         */
        var definition = new RouteDefinition();
        definition.setId(vo.getRouteId());
        definition.setOrder(vo.getRouteOrder());
        if (CollUtil.isNotEmpty(vo.getMetadata())) {
            definition.setMetadata(vo.getMetadata());
        }
        // 生成uri
        URI uri;
        if (vo.getIsLoadBalancer() == 1 && StringUtils.isNotBlank(vo.getLoadBalancerType())) {
            uri = URI.create(vo.getLoadBalancerType() + vo.getServiceCode());
        } else {
            var url = vo.getUrl();
            if (StringUtils.isBlank(url) || !ProtocolType.isHaveByType(url)) {
                log.error("------------DynamicRouteServiceImpl------设置路由判断失败------>getRouteDefinition--->\n参数:\n{}\n异常消息:\n{}", vo, "非负载均衡器时url必填,并且只能是:" + ProtocolType.getAllType());
                return null;
            }
            uri = UriComponentsBuilder.fromHttpUrl(url).build().toUri();
        }
        definition.setUri(uri);
        // 设置断言
        var pdList = new ArrayList<PredicateDefinition>();
        var routePredicates = vo.getRoutePredicates();
        if (CollUtil.isNotEmpty(routePredicates)) {
            for (RoutePredicateModel predicate : routePredicates) {
                // 验证断言类型是否存在
                if (!PredicateSysType.isHaveByType(predicate.getPredicateType())) {
                    log.error("------------DynamicRouteServiceImpl------断言设置失败------>getRouteDefinition--->\n参数:\n{}\n异常消息:\n{}", predicate, "断言类型:" + predicate.getPredicateType() + "不存在,当前只能为:" + PredicateSysType.getAllType());
                    continue;
                }
                var rules = predicate.getRules();
                var predicateDefinition = new PredicateDefinition();
                if (CollUtil.isNotEmpty(rules)) {
                    predicateDefinition.setArgs(rules);
                }
                predicateDefinition.setName(predicate.getPredicateType());
                pdList.add(predicateDefinition);
            }
        }
        definition.setPredicates(pdList);
        // 设置过滤器
        var filters = new ArrayList<FilterDefinition>();
        var routeFilters = vo.getRouteFilters();
        if (!CollectionUtils.isEmpty(routeFilters)) {
            for (RouteFilterModel routeFilter : routeFilters) {
                // 验证过滤器类型是否存在
                if (!FilterSysType.isHaveByType(routeFilter.getFilterType()) && !FilterCustomType.isHaveByType(routeFilter.getFilterType())) {
                    var msg = "过滤器类型:" + routeFilter.getFilterType() + "不存在,当前只能为:" + FilterSysType.getAllType() + ",或" + FilterCustomType.getAllType();
                    log.error("------------DynamicRouteServiceImpl------过滤器判设置失败------>getRouteDefinition--->\n参数:\n{}\n异常消息:\n{}", routeFilter, msg);
                    continue;
                }
                var filterTypeInfo = routeFilter.getFilterType();
                var rules = routeFilter.getRules();
                var filterTypes = filterTypeInfo.split("[,，]");
                for (String filterType : filterTypes) {
                    var filterDefinition = new FilterDefinition();
                    filterDefinition.setName(filterType);
                    if (CollUtil.isNotEmpty(rules)) {
                        filterDefinition.setArgs(rules);
                    }
                    filters.add(filterDefinition);
                }
            }
        }
        definition.setFilters(filters);
        return definition;
    }


    /**
     * 刷新路由
     *
     * @author zxh
     * @date 2020-09-11 12:05
     */
    private void notifyChanged() {
        publisher.publishEvent(new RefreshRoutesEvent(this));
    }
}
