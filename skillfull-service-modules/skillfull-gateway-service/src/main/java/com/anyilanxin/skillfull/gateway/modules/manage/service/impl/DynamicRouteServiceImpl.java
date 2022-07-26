// +----------------------------------------------------------------------
// | SkillFull快速开发平台 [ SkillFull ]
// +----------------------------------------------------------------------
// | 版权所有 2020~2021 zxiaozhou
// +----------------------------------------------------------------------
// | 官方网站: https://www.divisu.com
// +----------------------------------------------------------------------
// | 作者: zxiaozhou <z7630853@163.com>
// +----------------------------------------------------------------------
package com.anyilanxin.skillfull.gateway.modules.manage.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.alibaba.fastjson.JSONObject;
import com.anyilanxin.skillfull.corecommon.base.model.stream.router.RouteFilterModel;
import com.anyilanxin.skillfull.corecommon.base.model.stream.router.RoutePredicateModel;
import com.anyilanxin.skillfull.corecommon.base.model.stream.router.SystemRouterModel;
import com.anyilanxin.skillfull.corecommon.constant.CoreCommonCacheConstant;
import com.anyilanxin.skillfull.corecommon.exception.ResponseException;
import com.anyilanxin.skillfull.gateway.core.constant.CommonGatewayConstant;
import com.anyilanxin.skillfull.gateway.core.constant.typeimpl.*;
import com.anyilanxin.skillfull.gateway.modules.manage.controller.vo.GatewayRouteVo;
import com.anyilanxin.skillfull.gateway.modules.manage.service.IDynamicRouteService;
import com.anyilanxin.skillfull.gatewayapi.model.RouteResponseModel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
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
import java.util.*;

/**
 * 动态路由服务
 *
 * @author zxiaozhou
 * @date 2020-09-10 21:10
 * @since JDK11
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
            RouteDefinition routeDefinition = getRouteDefinition(vo);
            if (Objects.nonNull(routeDefinition)) {
                routeDefinitionRepository
                        .save(Mono.just(routeDefinition))
                        .subscribe();
            }
        } catch (Exception e) {
            throw new ResponseException("添加路由失败:" + e.getMessage());
        }
        notifyChanged();
    }


    @Override
    public void updateRoute(SystemRouterModel vo) throws RuntimeException {
        addRoute(vo);
    }


    @Override
    public void deleteRoute(String routeId) throws RuntimeException {
        routeDefinitionRepository
                .delete(Mono.just(routeId)).then(Mono.defer(() -> Mono.just(ResponseEntity.ok().build())))
                .onErrorResume(t -> t instanceof NotFoundException, t -> Mono.just(ResponseEntity.notFound().build())).subscribe();
        notifyChanged();
    }


    @Override
    public Flux<RouteResponseModel> getRoutes() throws RuntimeException {
        return routeDefinitionRepository.getRouteDefinitions().map(v -> {
            // 处理基础部分
            RouteResponseModel dto = new RouteResponseModel();
            dto.setRouteCode(v.getId());
            dto.setRouteOrder(v.getOrder());
            String url = v.getUri().toString().toLowerCase();
            dto.setIsLoadBalancer(0);
            if (url.startsWith(CommonGatewayConstant.LB_PREFIX)) {
                dto.setIsLoadBalancer(1);
                LbType startMatch = LbType.getStartMatch(url);
                if (Objects.nonNull(startMatch)) {
                    dto.setLoadBalancerType(startMatch.getTypeName());
                    dto.setServiceName(url.replace(startMatch.getTypeName(), ""));
                }
            } else {
                dto.setUrl(url);
            }
            // 处理断言
            List<RouteResponseModel.RoutePredicate> routePredicates = new ArrayList<>();
            List<PredicateDefinition> predicates = v.getPredicates();
            if (!CollectionUtils.isEmpty(predicates)) {
                predicates.forEach(sv -> {
                    RouteResponseModel.RoutePredicate predicate = new RouteResponseModel.RoutePredicate();
                    predicate.setPredicateType(sv.getName());
                    Set<RouteResponseModel.Rule> rules = new HashSet<>();
                    Map<String, String> args = sv.getArgs();
                    if (!CollectionUtils.isEmpty(args)) {
                        args.forEach((k, ssv) -> {
                            RouteResponseModel.Rule rule = new RouteResponseModel.Rule();
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
            List<RouteResponseModel.RouteFilter> routeFilters = new ArrayList<>();
            List<FilterDefinition> filters = v.getFilters();
            if (!CollectionUtils.isEmpty(filters)) {
                filters.forEach(sv -> {
                    RouteResponseModel.RouteFilter filter = new RouteResponseModel.RouteFilter();
                    filter.setFilterType(sv.getName());
                    Set<RouteResponseModel.Rule> rules = new HashSet<>();
                    Map<String, String> args = sv.getArgs();
                    if (!CollectionUtils.isEmpty(args)) {
                        args.forEach((k, ssv) -> {
                            RouteResponseModel.Rule rule = new RouteResponseModel.Rule();
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
        routeDefinitionRepository.getRouteDefinitions().flatMap(item -> {
                    deleteRoute(item.getId());
                    return Mono.empty();
                })
                .collectList()
                .publishOn(Schedulers.boundedElastic())
                .doOnNext(v -> reactiveStringRedisTemplate.keys(CoreCommonCacheConstant.SYSTEM_ROUTE_INFO_CACHE_PREFIX + "*")
                        .flatMap(key -> reactiveStringRedisTemplate.opsForValue().get(key).flatMap(item -> {
                            SystemRouterModel vo = JSONObject.parseObject(item, SystemRouterModel.class);
                            RouteDefinition routeDefinition = getRouteDefinition(vo);
                            if (Objects.nonNull(routeDefinition)) {
                                return routeDefinitionRepository.save(Mono.just(routeDefinition));
                            }
                            return Mono.empty();
                        }))
                        .onErrorContinue((throwable, routeDefinition) -> {
                            if (log.isErrorEnabled()) {
                                log.error("get routes from redis error cause : {}", throwable.toString(), throwable);
                            }
                        }).subscribe())
                .subscribe();
        notifyChanged();
    }


    /**
     * 把传递进来的参数转换成路由对象
     *
     * @param vo ${@link GatewayRouteVo}
     * @return RouteDefinition ${@link RouteDefinition}
     * @author zxiaozhou
     * @date 2020-09-10 21:15
     */
    private RouteDefinition getRouteDefinition(SystemRouterModel vo) {
        /*
         * 数据校验
         * 1.如果是负载均衡器负载均衡器类型必填,服务名必填,并且均衡器类型只能为0,1,2
         * 2.如果非负载均衡器,url必填并且开头必须是http,https,ws,wss
         */
        RouteDefinition definition = new RouteDefinition();
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
            String url = vo.getUrl();
            if (StringUtils.isBlank(url) || !ProtocolType.isHaveByType(url)) {
                log.error("------------DynamicRouteServiceImpl------设置路由判断失败------>getRouteDefinition--->\n参数:\n{}\n异常消息:\n{}", vo, "非负载均衡器时url必填,并且只能是:" + ProtocolType.getAllType());
                return null;
            }
            uri = UriComponentsBuilder.fromHttpUrl(url).build().toUri();
        }
        definition.setUri(uri);
        //设置断言
        List<PredicateDefinition> pdList = new ArrayList<>();
        List<RoutePredicateModel> routePredicates = vo.getRoutePredicates();
        if (CollUtil.isNotEmpty(routePredicates)) {
            for (RoutePredicateModel predicate : routePredicates) {
                // 验证断言类型是否存在
                if (!PredicateSysType.isHaveByType(predicate.getPredicateType())) {
                    log.error("------------DynamicRouteServiceImpl------断言设置失败------>getRouteDefinition--->\n参数:\n{}\n异常消息:\n{}", predicate, "断言类型:" + predicate.getPredicateType() + "不存在,当前只能为:" + PredicateSysType.getAllType());
                    continue;
                }
                Map<String, String> rules = predicate.getRules();
                PredicateDefinition predicateDefinition = new PredicateDefinition();
                if (CollUtil.isNotEmpty(rules)) {
                    predicateDefinition.setArgs(rules);
                }
                predicateDefinition.setName(predicate.getPredicateType());
                pdList.add(predicateDefinition);
            }
        }
        definition.setPredicates(pdList);
        //设置过滤器
        List<FilterDefinition> filters = new ArrayList<>();
        List<RouteFilterModel> routeFilters = vo.getRouteFilters();
        if (!CollectionUtils.isEmpty(routeFilters)) {
            for (RouteFilterModel routeFilter : routeFilters) {
                // 验证过滤器类型是否存在
                if (!FilterSysType.isHaveByType(routeFilter.getFilterType()) &&
                        !FilterCustomType.isHaveByType(routeFilter.getFilterType())) {
                    String msg = "过滤器类型:" + routeFilter.getFilterType() + "不存在,当前只能为:" + FilterSysType.getAllType() +
                            ",或" + FilterCustomType.getAllType();
                    log.error("------------DynamicRouteServiceImpl------过滤器判设置失败------>getRouteDefinition--->\n参数:\n{}\n异常消息:\n{}", routeFilter, msg);
                    continue;
                }
                String filterTypeInfo = routeFilter.getFilterType();
                Map<String, String> rules = routeFilter.getRules();
                String[] filterTypes = filterTypeInfo.split("[,，]");
                for (String filterType : filterTypes) {
                    FilterDefinition filterDefinition = new FilterDefinition();
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
     * @author zxiaozhou
     * @date 2020-09-11 12:05
     */
    private void notifyChanged() {
        publisher.publishEvent(new RefreshRoutesEvent(this));
    }
}
