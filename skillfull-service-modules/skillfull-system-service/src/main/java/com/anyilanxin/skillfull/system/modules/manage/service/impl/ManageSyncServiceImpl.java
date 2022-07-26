// +----------------------------------------------------------------------
// | SkillFull快速开发平台 [ SkillFull ]
// +----------------------------------------------------------------------
// | 版权所有 2020~2021 zxiaozhou
// +----------------------------------------------------------------------
// | 官方网站: https://www.divisu.com
// +----------------------------------------------------------------------
// | 作者: zxiaozhou <z7630853@163.com>
// +----------------------------------------------------------------------
package com.anyilanxin.skillfull.system.modules.manage.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.CollectionUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.anyilanxin.skillfull.corecommon.base.model.stream.router.*;
import com.anyilanxin.skillfull.corecommon.base.model.system.SpecialUrlModel;
import com.anyilanxin.skillfull.corecommon.constant.CoreCommonCacheConstant;
import com.anyilanxin.skillfull.corecommon.constant.CoreCommonGatewayConstant;
import com.anyilanxin.skillfull.corecommon.constant.RedisSubscribeConstant;
import com.anyilanxin.skillfull.coreredis.utils.MsgSendUtils;
import com.anyilanxin.skillfull.system.modules.manage.entity.*;
import com.anyilanxin.skillfull.system.modules.manage.mapper.*;
import com.anyilanxin.skillfull.system.modules.manage.service.IManageSyncService;
import com.anyilanxin.skillfull.system.modules.manage.service.dto.ManageRouteCustomFilterDto;
import com.anyilanxin.skillfull.system.modules.manage.service.mapstruct.*;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.TimeUnit;

import static com.anyilanxin.skillfull.corecommon.constant.CommonCoreConstant.LOCK_EXPIRES;

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
    private final RedisTemplate<String, Object> redisTemplate;
    private final StringRedisTemplate stringRedisTemplate;
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

    @Override
    public void reloadRoute(boolean force) {
        // 非强制时检测锁情况
        if (!force) {
            Object redisLockValue = stringRedisTemplate.opsForValue().get(CoreCommonCacheConstant.SYSTEM_ROUTE_INFO_CACHE_LOCK);
            if (Objects.nonNull(redisLockValue)) {
                return;
            }
        }
        // 删除所有
        Set<String> keys = stringRedisTemplate.keys(CoreCommonCacheConstant.SYSTEM_ROUTE_INFO_CACHE_PREFIX + "");
        if (CollUtil.isNotEmpty(keys)) {
            stringRedisTemplate.delete(keys);
        }
        // 获取所有有效的服务信息
        LambdaQueryWrapper<ManageServiceEntity> serviceLambdaQueryWrapper = new LambdaQueryWrapper<>();
        serviceLambdaQueryWrapper.eq(ManageServiceEntity::getServiceState, 1);
        List<ManageServiceEntity> manageServiceEntities = serviceMapper.selectList(serviceLambdaQueryWrapper);
        if (CollUtil.isNotEmpty(manageServiceEntities)) {
            // 查询服务项所有有效的路由信息
            Set<String> serviceIds = new HashSet<>(manageServiceEntities.size());
            manageServiceEntities.forEach(v -> serviceIds.add(v.getServiceId()));
            // 获取路由信息并写入redis
            routerToRedis(serviceIds, false);
        }
        // 通知网关刷新
        MsgSendUtils.sendMsg(RedisSubscribeConstant.GATEWAY_ROUTER_INFO_RELOAD, "需要重写加载路由");
        // 加锁
        redisTemplate.opsForValue().set(CoreCommonCacheConstant.SYSTEM_ROUTE_INFO_CACHE_LOCK, true, LOCK_EXPIRES, TimeUnit.SECONDS);
    }


    @Override
    public void updateServiceRoute(String serviceId) {
        // 先删除当前服务所有路由
        Set<String> keys = stringRedisTemplate.keys(CoreCommonCacheConstant.SYSTEM_ROUTE_INFO_CACHE_PREFIX + serviceId + "*");
        if (CollUtil.isNotEmpty(keys)) {
            stringRedisTemplate.delete(keys);
            keys.forEach(v -> {
                // 通知网关刷新
                String routerId = v.replaceFirst(CoreCommonCacheConstant.SYSTEM_ROUTE_INFO_CACHE_PREFIX, "");
                MsgSendUtils.sendMsg(RedisSubscribeConstant.GATEWAY_ROUTER_INFO_DELETE, routerId);
            });
        }
        routerToRedis(Set.of(serviceId), true);
    }


    @Override
    public void deleteServiceRoute(String serviceId) {
        Set<String> keys = stringRedisTemplate.keys(CoreCommonCacheConstant.SYSTEM_ROUTE_INFO_CACHE_PREFIX + serviceId + "*");
        if (CollUtil.isNotEmpty(keys)) {
            stringRedisTemplate.delete(keys);
            keys.forEach(v -> {
                String routerId = v.replaceFirst(CoreCommonCacheConstant.SYSTEM_ROUTE_INFO_CACHE_PREFIX, "");
                // 通知网关删除
                MsgSendUtils.sendMsg(RedisSubscribeConstant.GATEWAY_ROUTER_INFO_DELETE, routerId);
            });

        }
    }


    /**
     * 获取系统有效的路由信息
     *
     * @return SystemRouterListModel ${@link SystemRouterListModel}
     * @author zxiaozhou
     * @date 2021-12-22 23:42
     */
    private void routerToRedis(Set<String> serviceIds, boolean update) {
        if (CollUtil.isNotEmpty(serviceIds)) {
            LambdaQueryWrapper<ManageRouteEntity> routeLambdaQueryWrapper = new LambdaQueryWrapper<>();
            routeLambdaQueryWrapper.in(ManageRouteEntity::getServiceId, serviceIds)
                    .eq(ManageRouteEntity::getRouteState, 1);
            List<ManageRouteEntity> manageRouteEntities = routeMapper.selectList(routeLambdaQueryWrapper);
            if (CollUtil.isNotEmpty(manageRouteEntities)) {
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
                // 数据整理与存入redis
                routerInfoModels.forEach(v -> {
                    String routeId = v.getRouteId();
                    List<RouteFilterModel> filter = filters.get(routeId);
                    if (CollectionUtil.isEmpty(filter)) {
                        filter = new ArrayList<>();
                    }
                    List<RouteFilterModel> customFilter = customFilters.get(routeId);
                    if (CollUtil.isNotEmpty(customFilter)) {
                        filter.addAll(customFilter);
                    }
                    v.setRouteFilters(filter);
                    v.setRoutePredicates(predicates.get(routeId));
                    v.setRouteId(v.getServiceCode() + ":" + v.getRouteId());
                    stringRedisTemplate.opsForValue().set(CoreCommonCacheConstant.SYSTEM_ROUTE_INFO_CACHE_PREFIX + v.getRouteId(), JSON.toJSONString(v, SerializerFeature.WriteMapNullValue));
                    if (update) {
                        MsgSendUtils.sendMsg(RedisSubscribeConstant.GATEWAY_ROUTER_INFO_UPDATE, routeId);
                    }
                });
            }
        }
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
        if (CollUtil.isNotEmpty(manageRoutePredicateEntities)) {
            manageRoutePredicateEntities.forEach(v -> {
                List<RoutePredicateModel> routePredicateModels = routePredicateMap.get(v.getRouteId());
                if (CollectionUtil.isEmpty(routePredicateModels)) {
                    routePredicateModels = new ArrayList<>();
                }
                RoutePredicateModel routePredicateModel = predicateCopyMap.bToA(v);
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
        if (CollUtil.isNotEmpty(manageRouteFilterEntities)) {
            manageRouteFilterEntities.forEach(v -> {
                List<RouteFilterModel> routeFilterModels = routeFilterMap.get(v.getRouteId());
                if (CollectionUtil.isEmpty(routeFilterModels)) {
                    routeFilterModels = new ArrayList<>();
                }
                RouteFilterModel routeFilterModel = filterCopyMap.bToA(v);
                routeFilterModels.add(routeFilterModel);
                routeFilterMap.put(v.getRouteId(), routeFilterModels);
            });
        }
        return routeFilterMap;
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
        // 最终返回结果
        Map<String, List<RouteFilterModel>> customFilters = new HashMap<>(routeIds.size() * 2);
        // 获取有效的自定义过滤器
        List<ManageRouteCustomFilterDto> manageRouteCustomFilters = routeCustomFilterMapper.selectListByRouterIds(routeIds);
        if (CollUtil.isNotEmpty(manageRouteCustomFilters)) {
            // 获取特殊url
            Set<String> filterIds = new HashSet<>(manageRouteCustomFilters.size());
            manageRouteCustomFilters.forEach(v -> {
                if (v.getHaveSpecial() == 1) {
                    filterIds.add(v.getFilterId());
                }
            });
            Map<String, RouteMetaSpecialUrlModel> specialUrls = getSpecialUrls(filterIds);
            // 生成网关过滤器
            routeIds.forEach(v -> {
                List<RouteFilterModel> routeFilterModels = new ArrayList<>();
                manageRouteCustomFilters.forEach(sv -> {
                    if (sv.getRouteId().equals(v)) {
                        RouteMetaSpecialUrlModel metaSpecialUrlModel = specialUrls.get(sv.getFilterId());
                        Map<String, String> ruleMap = new HashMap<>();
                        if (sv.getHaveSpecial() == 1) {
                            if (Objects.nonNull(metaSpecialUrlModel)) {
                                ruleMap.put(CoreCommonGatewayConstant.PARAM_SPECIAL_URL_KEY, JSONObject.toJSONString(metaSpecialUrlModel));
                            }
                        }
                        RouteFilterModel routeFilterModel = customFilterCopyMap.bToA(sv);
                        if (CollUtil.isNotEmpty(ruleMap)) {
                            routeFilterModel.setRules(ruleMap);
                        }
                        routeFilterModels.add(routeFilterModel);
                    }
                });
                if (CollUtil.isNotEmpty(routeFilterModels)) {
                    customFilters.put(v, routeFilterModels);
                }
            });
        }
        return customFilters;
    }


    /**
     * 获取特殊url
     *
     * @return Map<String, RouteMetaSpecialUrlModel> ${@link Map<String, RouteMetaSpecialUrlModel>},map<filterId,RouteMetaSpecialUrlModel>
     * @author zxiaozhou
     * @date 2022-03-05 14:42
     */
    public Map<String, RouteMetaSpecialUrlModel> getSpecialUrls(Set<String> filterIds) {
        LambdaQueryWrapper<ManageSpecialUrlEntity> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.in(ManageSpecialUrlEntity::getCustomFilterId, filterIds);
        List<ManageSpecialUrlEntity> manageSpecialUrlEntities = specialUrlMapper.selectList(lambdaQueryWrapper);
        // map<filterId,RouteMetaSpecialUrlModel>
        Map<String, RouteMetaSpecialUrlModel> filterTypeUrlMap = new HashMap<>();
        if (CollUtil.isNotEmpty(manageSpecialUrlEntities)) {
            manageSpecialUrlEntities.forEach(v -> {
                RouteMetaSpecialUrlModel metaSpecialUrlModel = filterTypeUrlMap.get(v.getCustomFilterId());
                if (Objects.isNull(metaSpecialUrlModel)) {
                    metaSpecialUrlModel = new RouteMetaSpecialUrlModel();
                    metaSpecialUrlModel.setWhiteSpecialUrls(new ArrayList<>());
                    metaSpecialUrlModel.setBlackSpecialUrls(new ArrayList<>());
                }
                SpecialUrlModel specialUrlModel = specialUrlCopyMap.bToA(v);
                String requestMethod = v.getRequestMethod();
                if (v.getLimitMethod() == 1 && StringUtils.isNotBlank(requestMethod)) {
                    specialUrlModel.setRequestMethodSet(new HashSet<>(Arrays.asList(requestMethod.split("[,，]"))));
                }
                List<SpecialUrlModel> blackSpecialUrls = metaSpecialUrlModel.getBlackSpecialUrls();
                List<SpecialUrlModel> whiteSpecialUrls = metaSpecialUrlModel.getWhiteSpecialUrls();
                if (specialUrlModel.getSpecialUrlType() == 1) {
                    whiteSpecialUrls.add(specialUrlModel);
                } else {
                    blackSpecialUrls.add(specialUrlModel);
                }
                metaSpecialUrlModel.setWhiteSpecialUrls(whiteSpecialUrls);
                metaSpecialUrlModel.setBlackSpecialUrls(blackSpecialUrls);
                filterTypeUrlMap.put(v.getCustomFilterId(), metaSpecialUrlModel);
            });
        }
        return filterTypeUrlMap;
    }
}
