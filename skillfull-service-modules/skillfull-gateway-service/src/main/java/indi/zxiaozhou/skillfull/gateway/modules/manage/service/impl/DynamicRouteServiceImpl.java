// +----------------------------------------------------------------------
// | SkillFull快速开发平台 [ SkillFull ]
// +----------------------------------------------------------------------
// | 版权所有 2020~2021 zxiaozhou
// +----------------------------------------------------------------------
// | 官方网站: https://www.divisu.com
// +----------------------------------------------------------------------
// | 作者: zxiaozhou <z7630853@163.com>
// +----------------------------------------------------------------------
package indi.zxiaozhou.skillfull.gateway.modules.manage.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.alibaba.fastjson.JSONObject;
import indi.zxiaozhou.skillfull.corecommon.base.model.stream.router.*;
import indi.zxiaozhou.skillfull.corecommon.constant.SysBaseConstant;
import indi.zxiaozhou.skillfull.corecommon.exception.ResponseException;
import indi.zxiaozhou.skillfull.gateway.core.constant.typeimpl.*;
import indi.zxiaozhou.skillfull.gateway.modules.manage.controller.vo.GatewayRouteVo;
import indi.zxiaozhou.skillfull.gateway.modules.manage.service.IDynamicRouteService;
import indi.zxiaozhou.skillfull.gatewayapi.model.RouteResponseModel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.cloud.gateway.event.RefreshRoutesEvent;
import org.springframework.cloud.gateway.filter.FilterDefinition;
import org.springframework.cloud.gateway.handler.predicate.PredicateDefinition;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.cloud.gateway.route.RouteDefinitionLocator;
import org.springframework.cloud.gateway.route.RouteDefinitionWriter;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.*;

import static indi.zxiaozhou.skillfull.corecommon.constant.CoreCommonCacheConstant.SYSTEM_ROUTE_INFO_CACHE;
import static indi.zxiaozhou.skillfull.gateway.core.constant.CommonGatewayConstant.LB_PREFIX;

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
    private final RouteDefinitionWriter routeDefinitionWriter;
    private final ApplicationEventPublisher publisher;
    private final RouteDefinitionLocator routeDefinitionLocator;
    private final RedisTemplate<String, Object> redisTemplate;


    @Override
    public void addRoute(SystemRouterModel vo, boolean isRefresh) throws RuntimeException {
        try {
            RouteDefinition routeDefinition = getRouteDefinition(vo);
            if (Objects.nonNull(routeDefinition)) {
                routeDefinitionWriter.save(Mono.just(routeDefinition)).subscribe();
                if (isRefresh) {
                    notifyChanged();
                }
            }
        } catch (Exception e) {
            throw new ResponseException("添加路由失败:" + e.getMessage());
        }
    }


    @Override
    public void updateRoute(SystemRouterModel vo, boolean isRefresh) throws RuntimeException {
        try {
            RouteDefinition definition = getRouteDefinition(vo);
            if (Objects.nonNull(definition)) {
                deleteRoute(definition.getId(), false);
                routeDefinitionWriter.save(Mono.just(definition)).subscribe();
                if (isRefresh) {
                    notifyChanged();
                }
            }
        } catch (Exception e) {
            throw new ResponseException("更新路由失败:" + e.getMessage());
        }

    }


    @Override
    public void deleteRoute(String routeId, boolean isRefresh) throws RuntimeException {
        try {
            routeDefinitionWriter.delete(Mono.just(routeId)).subscribe();
            if (isRefresh) {
                notifyChanged();
            }
        } catch (Exception e) {
            throw new ResponseException("删除失败,服务可能不存在");
        }
    }


    @Override
    public Flux<RouteResponseModel> getRoutes() throws RuntimeException {
        return routeDefinitionLocator.getRouteDefinitions().map(v -> {
            // 处理基础部分
            RouteResponseModel dto = new RouteResponseModel();
            dto.setRouteCode(v.getId());
            dto.setRouteOrder(v.getOrder());
            Map<String, Object> metadata = v.getMetadata();
            if (CollectionUtil.isNotEmpty(metadata)) {
                // 按钮权限指令
                Object objectAction = metadata.get(SysBaseConstant.ROUTE_META_ACTION_KEY);
                if (Objects.nonNull(objectAction) && objectAction instanceof RouteMetaActionModel) {
                    RouteMetaActionModel actionModel = (RouteMetaActionModel) objectAction;
                    dto.setRouteActions(actionModel.getRouteActions());
                }
                Map<String, RouteMetaSpecialUrlModel> specialUrls = new HashMap<>(metadata.size());
                metadata.forEach((sk, sv) -> {
                    if (Objects.nonNull(objectAction)) {
                        if (objectAction instanceof RouteMetaActionModel) {
                            RouteMetaActionModel actionModel = (RouteMetaActionModel) objectAction;
                            dto.setRouteActions(actionModel.getRouteActions());
                        } else if (objectAction instanceof RouteMetaSpecialUrlModel) {
                            specialUrls.put(sk, (RouteMetaSpecialUrlModel) objectAction);
                        }
                    }
                });
                dto.setSpecialUrls(specialUrls);
            }
            String url = v.getUri().toString().toLowerCase();
            dto.setIsLoadBalancer(0);
            if (url.startsWith(LB_PREFIX)) {
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
    public void loadRoute(SystemRouterListModel routerListModel) throws RuntimeException {
        log.debug("------------DynamicRouteServiceImpl------------>loadRoute:{}", "开始加载路由:\n" + JSONObject.toJSONString(routerListModel));
        // 删除历史路由
        Flux<RouteDefinition> routeDefinitions = routeDefinitionLocator.getRouteDefinitions();
        if (Objects.nonNull(routeDefinitions)) {
            routeDefinitions.collectList().subscribe(v -> v.forEach(sv -> deleteRoute(sv.getId(), false)));
            this.notifyChanged();
        }
        // 加载新路由
        if (Objects.isNull(routerListModel)) {
            Object object = redisTemplate.opsForValue().get(SYSTEM_ROUTE_INFO_CACHE);
            if (Objects.nonNull(object) && object instanceof SystemRouterListModel) {
                routerListModel = (SystemRouterListModel) object;
            }
        }
        if (Objects.nonNull(routerListModel)) {
            List<SystemRouterModel> routerInfoModels = routerListModel.getRouterInfoModels();
            if (CollectionUtil.isNotEmpty(routerInfoModels)) {
                // 路由写入内存
                routerInfoModels.forEach(v -> {
                    try {
                        addRoute(v, false);
                    } catch (Exception e) {
                        log.error("------------DynamicRouteServiceImpl-----加载路由失败------->loadRoute--->\n参数:\n{}\n异常消息:\n{}", v, e.getMessage());
                    }
                });
                this.notifyChanged();
            }
        }

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
        definition.setId(vo.getRouteCode());
        definition.setOrder(vo.getRouteOrder());
        if (CollectionUtil.isNotEmpty(vo.getMetadata())) {
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
        for (RoutePredicateModel predicate : routePredicates) {
            // 验证断言类型是否存在
            if (!PredicateSysType.isHaveByType(predicate.getPredicateType())) {
                log.error("------------DynamicRouteServiceImpl------断言设置失败------>getRouteDefinition--->\n参数:\n{}\n异常消息:\n{}", predicate, "断言类型:" + predicate.getPredicateType() + "不存在,当前只能为:" + PredicateSysType.getAllType());
                continue;
            }
            Map<String, String> rules = predicate.getRuleMap();
            PredicateDefinition predicateDefinition = new PredicateDefinition();
            if (CollectionUtil.isNotEmpty(rules)) {
                predicateDefinition.setArgs(rules);
            }
            predicateDefinition.setName(predicate.getPredicateType());
            pdList.add(predicateDefinition);
        }
        definition.setPredicates(pdList);
        //设置过滤器
        List<FilterDefinition> filters = new ArrayList<>();
        List<RouteFilterModel> routeFilters = vo.getRouteFilters();
        if (!CollectionUtils.isEmpty(routeFilters)) {
            for (RouteFilterModel routeFilter : routeFilters) {
                // 验证过滤器类型是否存在
                if (!FilterSysType.isHaveByType(routeFilter.getFilterType()) &&
                        !FilterCustomPreType.isHaveByType(routeFilter.getFilterType()) &&
                        !FilterCustomPostType.isHaveByType(routeFilter.getFilterType())) {
                    String msg = "过滤器类型:" + routeFilter.getFilterType() + "不存在,当前只能为:" + FilterSysType.getAllType() +
                            ",或" + FilterCustomPreType.getAllType() +
                            ",或" + FilterCustomPostType.getAllType();
                    log.error("------------DynamicRouteServiceImpl------过滤器判设置失败------>getRouteDefinition--->\n参数:\n{}\n异常消息:\n{}", routeFilter, msg);
                    continue;
                }
                FilterDefinition filterDefinition = new FilterDefinition();
                filterDefinition.setName(routeFilter.getFilterType());
                Map<String, String> rules = routeFilter.getRuleMap();
                if (CollectionUtil.isNotEmpty(rules)) {
                    filterDefinition.setArgs(rules);
                }
                filters.add(filterDefinition);
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
