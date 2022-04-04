// +----------------------------------------------------------------------
// | SkillFull快速开发平台 [ SkillFull ]
// +----------------------------------------------------------------------
// | 版权所有 2020~2021 zxiaozhou
// +----------------------------------------------------------------------
// | 官方网站: https://www.divisu.com
// +----------------------------------------------------------------------
// | 作者: zxiaozhou <z7630853@163.com>
// +----------------------------------------------------------------------
package indi.zxiaozhou.skillfull.system.modules.manage.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import indi.zxiaozhou.skillfull.corecommon.base.model.common.ActionModel;
import indi.zxiaozhou.skillfull.corecommon.base.model.common.SpecialUrlModel;
import indi.zxiaozhou.skillfull.corecommon.base.model.stream.router.*;
import indi.zxiaozhou.skillfull.corecommon.constant.BindingStreamConstant;
import indi.zxiaozhou.skillfull.corecommon.constant.CoreCommonCacheConstant;
import indi.zxiaozhou.skillfull.corecommon.constant.CoreCustomGatewayFilterParamKeyConstant;
import indi.zxiaozhou.skillfull.coremvc.component.BindingComponent;
import indi.zxiaozhou.skillfull.system.modules.manage.entity.*;
import indi.zxiaozhou.skillfull.system.modules.manage.mapper.*;
import indi.zxiaozhou.skillfull.system.modules.manage.service.IManageCustomFilterService;
import indi.zxiaozhou.skillfull.system.modules.manage.service.IManageRouteCustomFilterService;
import indi.zxiaozhou.skillfull.system.modules.manage.service.IManageSyncService;
import indi.zxiaozhou.skillfull.system.modules.manage.service.dto.ManageRouteCustomFilterDto;
import indi.zxiaozhou.skillfull.system.modules.manage.service.mapstruct.*;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.TimeUnit;

import static indi.zxiaozhou.skillfull.corecommon.constant.CommonCoreConstant.LOCK_EXPIRES;

/**
 * 同步接口实现
 *
 * @author zxiaozhou
 * @copyright zxiaozhou（https://divisu.com）
 * @date 2021-12-19 00:22:16
 * @since JDK1.8
 */
@Service
@RequiredArgsConstructor
public class ManageSyncServiceImpl implements IManageSyncService {
    private final BindingComponent bindingComponent;
    private final RedisTemplate<String, Object> redisTemplate;
    private final ManageServiceMapper serviceMapper;
    private final ManageRouteFilterMapper filterMapper;
    private final ManageRouteMapper routeMapper;
    private final ManageRoutePredicateMapper predicateMapper;
    private final RouterCopyMap routerCopyMap;
    private final RouterSpecialUrlCopyMap specialUrlCopyMap;
    private final RouterCustomFilterCopyMap customFilterCopyMap;
    private final ManageRouteCustomFilterMapper routeCustomFilterMapper;
    private final RouterFilterCopyMap filterCopyMap;
    private final RouterPredicateCopyMap predicateCopyMap;
    private final ManageSpecialUrlMapper specialUrlMapper;
    private final IManageCustomFilterService customFilterService;
    private final IManageRouteCustomFilterService routeCustomFilterService;

    @Override
    public void syncRoute(boolean force) {
//        // 非强制时检测锁情况
        if (!force) {
            Object redisLockValue = redisTemplate.opsForValue().get(CoreCommonCacheConstant.SYSTEM_ROUTE_INFO_CACHE + "_LOCK");
            if (Objects.nonNull(redisLockValue)) {
                return;
            }
        }
        // 获取路由信息并写入redis
        SystemRouterListModel systemRouterListModel = getSystemRouterListModel();
        System.out.println("-------systemRouterListModel-------" + JSONObject.toJSONString(systemRouterListModel));
        redisTemplate.opsForValue().set(CoreCommonCacheConstant.SYSTEM_ROUTE_INFO_CACHE, systemRouterListModel);
        // 通知网关刷新
        bindingComponent.out(BindingStreamConstant.ROUTER_PROCESS, systemRouterListModel);
        // 加锁
        redisTemplate.opsForValue().set(CoreCommonCacheConstant.SYSTEM_ROUTE_INFO_CACHE + "_LOCK", true, LOCK_EXPIRES, TimeUnit.SECONDS);
    }


    @Override
    public void syncGateway(boolean force) {
        this.syncRoute(force);
    }

    /**
     * 获取系统有效的路由信息
     *
     * @return SystemRouterListModel ${@link SystemRouterListModel}
     * @author zxiaozhou
     * @date 2021-12-22 23:42
     */
    private SystemRouterListModel getSystemRouterListModel() {
        // 获取所有有效的服务信息
        LambdaQueryWrapper<ManageServiceEntity> serviceLambdaQueryWrapper = new LambdaQueryWrapper<>();
        serviceLambdaQueryWrapper.eq(ManageServiceEntity::getServiceState, 1);
        List<ManageServiceEntity> manageServiceEntities = serviceMapper.selectList(serviceLambdaQueryWrapper);
        if (CollectionUtil.isNotEmpty(manageServiceEntities)) {
            // 查询服务项所有有效的路由信息
            Set<String> serviceIds = new HashSet<>(manageServiceEntities.size());
            manageServiceEntities.forEach(v -> serviceIds.add(v.getServiceId()));
            LambdaQueryWrapper<ManageRouteEntity> routeLambdaQueryWrapper = new LambdaQueryWrapper<>();
            routeLambdaQueryWrapper.in(ManageRouteEntity::getServiceId, serviceIds)
                    .eq(ManageRouteEntity::getRouteState, 1);
            List<ManageRouteEntity> manageRouteEntities = routeMapper.selectList(routeLambdaQueryWrapper);
            if (CollectionUtil.isNotEmpty(manageRouteEntities)) {
                // 路由信息
                List<SystemRouterModel> routerInfoModels = new ArrayList<>();
                Set<String> routeIds = new HashSet<>(manageRouteEntities.size());
                manageRouteEntities.forEach(v -> {
                    routeIds.add(v.getRouteId());
                    SystemRouterModel systemRouterModel = routerCopyMap.bToA(v);
                    routerInfoModels.add(systemRouterModel);
                });
                // 获取路由过滤器
                Map<String, List<RouteFilterModel>> filters = getFilters(routeIds);
                // 获取断言
                Map<String, List<RoutePredicateModel>> predicates = getPredicates(routeIds);
                // 获取自定义过滤器
                Map<String, List<RouteFilterModel>> customFilters = getCustomFilters(routeIds);
                // 数据整理
                routerInfoModels.forEach(v -> {
                    String routeId = v.getRouteId();
                    List<RouteFilterModel> filter = filters.get(routeId);
                    if (CollectionUtil.isEmpty(filter)) {
                        filter = new ArrayList<>();
                    }
                    List<RouteFilterModel> customFilter = customFilters.get(routeId);
                    if (CollectionUtil.isNotEmpty(customFilter)) {
                        filter.addAll(customFilter);
                    }
                    v.setRouteFilters(filter);
                    v.setRoutePredicates(predicates.get(routeId));
                });
                return new SystemRouterListModel(routerInfoModels);
            }
        }
        return new SystemRouterListModel();
    }


    /**
     * 获取路由断言
     *
     * @param routeIds ${@link Set<String>}
     * @return Map<String, List < RoutePredicateModel>> ${@link Map<String, List<RoutePredicateModel>>}
     * @author zxiaozhou
     * @date 2021-12-23 19:32
     */
    private Map<String, List<RoutePredicateModel>> getPredicates(Set<String> routeIds) {
        Map<String, List<RoutePredicateModel>> routePredicateMap = new HashMap<>(routeIds.size() * 2);
        LambdaQueryWrapper<ManageRoutePredicateEntity> predicateLambdaQueryWrapper = new LambdaQueryWrapper<>();
        predicateLambdaQueryWrapper.in(ManageRoutePredicateEntity::getRouteId, routeIds);
        List<ManageRoutePredicateEntity> manageRoutePredicateEntities = predicateMapper.selectList(predicateLambdaQueryWrapper);
        if (CollectionUtil.isNotEmpty(manageRoutePredicateEntities)) {
            manageRoutePredicateEntities.forEach(v -> {
                List<RoutePredicateModel> routePredicateModels = routePredicateMap.get(v.getRouteId());
                if (CollectionUtil.isEmpty(routePredicateModels)) {
                    routePredicateModels = new ArrayList<>();
                }
                RoutePredicateModel routePredicateModel = predicateCopyMap.bToA(v);
                if (StringUtils.isNotBlank(v.getRules())) {
                    routePredicateModel.setRuleMap(JSONObject.parseObject(v.getRules(), new TypeReference<HashMap<String, String>>() {
                    }));
                }
                routePredicateModels.add(routePredicateModel);
                routePredicateMap.put(v.getRouteId(), routePredicateModels);
            });
        }
        return routePredicateMap;
    }


    /**
     * 获取路由过滤器
     *
     * @param routeIds ${@link Set<String>}
     * @return Map<String, List < RouteFilterModel>> ${@link Map<String, List<RouteFilterModel>>}
     * @author zxiaozhou
     * @date 2021-12-23 19:32
     */
    private Map<String, List<RouteFilterModel>> getFilters(Set<String> routeIds) {
        Map<String, List<RouteFilterModel>> routeFilterMap = new HashMap<>(routeIds.size() * 2);
        LambdaQueryWrapper<ManageRouteFilterEntity> filterLambdaQueryWrapper = new LambdaQueryWrapper<>();
        filterLambdaQueryWrapper.in(ManageRouteFilterEntity::getRouteId, routeIds);
        List<ManageRouteFilterEntity> manageRouteFilterEntities = filterMapper.selectList(filterLambdaQueryWrapper);
        if (CollectionUtil.isNotEmpty(manageRouteFilterEntities)) {
            manageRouteFilterEntities.forEach(v -> {
                List<RouteFilterModel> routeFilterModels = routeFilterMap.get(v.getRouteId());
                if (CollectionUtil.isEmpty(routeFilterModels)) {
                    routeFilterModels = new ArrayList<>();
                }
                RouteFilterModel routeFilterModel = filterCopyMap.bToA(v);
                if (StringUtils.isNotBlank(v.getRules())) {
                    routeFilterModel.setRuleMap(JSONObject.parseObject(v.getRules(), new TypeReference<HashMap<String, String>>() {
                    }));
                }
                routeFilterModels.add(routeFilterModel);
                routeFilterMap.put(v.getRouteId(), routeFilterModels);
            });
        }
        return routeFilterMap;
    }

    /**
     * 获取权限服务按钮权限信息
     *
     * @return Map<String, Map < String, Set < ActionModel>>> ${@link Map<String, Map<String, Set<ActionModel>>>} map<serviceId,map<path,ActionModel>>
     * @author zxiaozhou
     * @date 2021-12-23 19:36
     */
    private Map<String, Map<String, Set<ActionModel>>> getRouteActions() {
        // 获取权限系统按钮权限信息:map<serviceCode,map<path,ActionModel>>
        Object object = redisTemplate.opsForValue().get(CoreCommonCacheConstant.SYSTEM_AUTH_ACTION_CACHE);
        if (Objects.nonNull(object) && object instanceof AuthActionModel) {
            AuthActionModel actionModel = (AuthActionModel) object;
            Map<String, Map<String, Set<ActionModel>>> actions = actionModel.getRouteActions();
            // map中serviceCode转为serviceId方便后续使用
            if (CollectionUtil.isNotEmpty(actions)) {
                Set<String> serviceCodes = actions.keySet();
                LambdaQueryWrapper<ManageServiceEntity> lambdaQueryWrapper = new LambdaQueryWrapper<>();
                lambdaQueryWrapper.in(ManageServiceEntity::getServiceCode, serviceCodes)
                        .eq(ManageServiceEntity::getServiceState, 1);
                List<ManageServiceEntity> manageServiceEntities = serviceMapper.selectList(lambdaQueryWrapper);
                if (CollectionUtil.isNotEmpty(manageServiceEntities)) {
                    Map<String, String> serviceIdMap = new HashMap<>();
                    manageServiceEntities.forEach(v -> serviceIdMap.put(v.getServiceCode(), v.getServiceId()));
                    Map<String, Map<String, Set<ActionModel>>> result = new HashMap<>(actions.size());
                    actions.forEach((k, v) -> {
                        String serviceId = serviceIdMap.get(k);
                        if (StringUtils.isNotBlank(serviceId)) {
                            result.put(serviceId, v);
                        }
                    });
                    return result;
                }
            }
        }
        return Collections.emptyMap();
    }


    /**
     * 获取自定义过滤器
     *
     * @param routeIds ${@link Set<String>}
     * @return Map<String, List < RouteFilterModel>> ${@link Map<String, List<RouteFilterModel>>}
     * @author zxiaozhou
     * @date 2021-12-23 19:32
     */
    private Map<String, List<RouteFilterModel>> getCustomFilters(Set<String> routeIds) {
        // 获取按钮权限信息:map<serviceId,map<path,ActionModel>>
        Map<String, Map<String, Set<ActionModel>>> actions = getRouteActions();
        // 最终返回结果
        Map<String, List<RouteFilterModel>> customFilters = new HashMap<>(routeIds.size() * 2);
        // 获取有效的自定义过滤器
        List<ManageRouteCustomFilterDto> manageRouteCustomFilters = routeCustomFilterMapper.selectListByRouterIds(routeIds);
        if (CollectionUtil.isNotEmpty(manageRouteCustomFilters)) {
            // 获取特殊url
            Set<String> filterIds = new HashSet<>(manageRouteCustomFilters.size());
            Map<String, Integer> filterSpecialUrlTypeMap = new HashMap<>();
            manageRouteCustomFilters.forEach(v -> {
                if (v.getHaveSpecial() == 1) {
                    filterIds.add(v.getFilterId());
                    filterSpecialUrlTypeMap.put(v.getFilterId(), v.getSpecialUrlType());
                }
            });
            LambdaQueryWrapper<ManageSpecialUrlEntity> lambdaQueryWrapper = new LambdaQueryWrapper<>();
            lambdaQueryWrapper.in(ManageSpecialUrlEntity::getCustomFilterId, filterIds);
            List<ManageSpecialUrlEntity> manageSpecialUrlEntities = specialUrlMapper.selectList(lambdaQueryWrapper);
            // map<filterId,RouteMetaSpecialUrlModel>
            Map<String, RouteMetaSpecialUrlModel> filterTypeUrlMap = new HashMap<>();
            if (CollectionUtil.isNotEmpty(manageSpecialUrlEntities)) {
                manageSpecialUrlEntities.forEach(v -> {
                    RouteMetaSpecialUrlModel metaSpecialUrlModel = filterTypeUrlMap.get(v.getCustomFilterId());
                    if (Objects.isNull(metaSpecialUrlModel)) {
                        metaSpecialUrlModel = new RouteMetaSpecialUrlModel();
                        metaSpecialUrlModel.setSpecialUrlType(filterSpecialUrlTypeMap.get(v.getCustomFilterId()));
                        metaSpecialUrlModel.setSpecialUrl(new HashSet<>());
                    }
                    SpecialUrlModel specialUrlModel = specialUrlCopyMap.bToA(v);
                    String requestMethod = v.getRequestMethod();
                    if (v.getLimitMethod() == 1 && StringUtils.isNotBlank(requestMethod)) {
                        specialUrlModel.setRequestMethodSet(new HashSet<>(Arrays.asList(requestMethod.split(","))));
                    }
                    Set<SpecialUrlModel> specialUrl = metaSpecialUrlModel.getSpecialUrl();
                    specialUrl.add(specialUrlModel);
                    filterTypeUrlMap.put(v.getCustomFilterId(), metaSpecialUrlModel);
                });
            }
            // 生成网关过滤器
            routeIds.forEach(v -> {
                List<RouteFilterModel> routeFilterModels = new ArrayList<>();
                manageRouteCustomFilters.forEach(sv -> {
                    if (sv.getRouteId().equals(v)) {
                        Map<String, String> ruleMap = new HashMap<>();
                        if (sv.getHaveSpecial() == 1) {
                            RouteMetaSpecialUrlModel metaSpecialUrlModel = filterTypeUrlMap.get(sv.getFilterId());
                            if (Objects.nonNull(metaSpecialUrlModel)) {
                                ruleMap.put(CoreCustomGatewayFilterParamKeyConstant.PARAM_SPECIAL_URL_KEY, JSONObject.toJSONString(metaSpecialUrlModel));
                            }
                        }
                        // 如果是鉴权过滤器还有鉴权指令(具体类型为网关常量:FilterCustomPreType中的值)
                        if ("Authorize".equals(sv.getFilterType())) {
                            Map<String, Set<ActionModel>> stringSetMap = actions.get(sv.getServiceId());
                            if (CollectionUtil.isNotEmpty(stringSetMap)) {
                                ruleMap.put(CoreCustomGatewayFilterParamKeyConstant.PARAM_ACTION_KEY, JSONObject.toJSONString(stringSetMap));
                            }
                        }
                        RouteFilterModel routeFilterModel = customFilterCopyMap.bToA(sv);
                        if (CollectionUtil.isNotEmpty(ruleMap)) {
                            routeFilterModel.setRuleMap(ruleMap);
                        }
                        routeFilterModels.add(routeFilterModel);
                    }
                });
                if (CollectionUtil.isNotEmpty(routeFilterModels)) {
                    customFilters.put(v, routeFilterModels);
                }
            });
        }
        return customFilters;
    }
}
