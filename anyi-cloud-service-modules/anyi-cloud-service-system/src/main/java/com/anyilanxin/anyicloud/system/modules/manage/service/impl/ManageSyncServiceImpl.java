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

package com.anyilanxin.anyicloud.system.modules.manage.service.impl;

import static com.anyilanxin.anyicloud.corecommon.constant.CommonCoreConstant.LOCK_EXPIRES;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.CollectionUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.anyilanxin.anyicloud.corecommon.constant.CoreCommonCacheConstant;
import com.anyilanxin.anyicloud.corecommon.constant.CoreCommonGatewayConstant;
import com.anyilanxin.anyicloud.corecommon.model.stream.router.*;
import com.anyilanxin.anyicloud.corecommon.model.system.SpecialUrlModel;
import com.anyilanxin.anyicloud.coreredis.constant.RedisSubscribeConstant;
import com.anyilanxin.anyicloud.coreredis.utils.SendRedisMsgUtils;
import com.anyilanxin.anyicloud.system.modules.manage.entity.*;
import com.anyilanxin.anyicloud.system.modules.manage.mapper.*;
import com.anyilanxin.anyicloud.system.modules.manage.service.IManageSyncService;
import com.anyilanxin.anyicloud.system.modules.manage.service.dto.ManageRouteCustomFilterDto;
import com.anyilanxin.anyicloud.system.modules.manage.service.mapstruct.*;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;

import java.util.*;
import java.util.concurrent.TimeUnit;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

/**
 * 同步接口实现
 *
 * @author 安一老厨
 * @copyright zhouxuanhong（https://anyilanxin.com）
 * @date 2021-12-19 00:22:16
 * @since 1.0.0
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
        SendRedisMsgUtils.sendMsg(RedisSubscribeConstant.GATEWAY_ROUTER_INFO_RELOAD, "需要重写加载路由");
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
                SendRedisMsgUtils.sendMsg(RedisSubscribeConstant.GATEWAY_ROUTER_INFO_DELETE, routerId);
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
                SendRedisMsgUtils.sendMsg(RedisSubscribeConstant.GATEWAY_ROUTER_INFO_DELETE, routerId);
            });
        }
    }


    /**
     * 获取系统有效的路由信息
     *
     * @return SystemRouterListModel ${@link SystemRouterListModel}
     * @author 安一老厨
     * @date 2021-12-22 23:42
     */
    private void routerToRedis(Set<String> serviceIds, boolean update) {
        if (CollUtil.isNotEmpty(serviceIds)) {
            LambdaQueryWrapper<ManageRouteEntity> routeLambdaQueryWrapper = new LambdaQueryWrapper<>();
            routeLambdaQueryWrapper.in(ManageRouteEntity::getServiceId, serviceIds).eq(ManageRouteEntity::getRouteState, 1);
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
                        SendRedisMsgUtils.sendMsg(RedisSubscribeConstant.GATEWAY_ROUTER_INFO_UPDATE, routeId);
                    }
                });
            }
        }
    }


    /**
     * 获取路由断言
     *
     * @param routeIds ${@link Set<String>}
     * @return Map<String, List < RoutePredicateModel>> ${@link Map<String,
     * List<RoutePredicateModel>>}
     * @author 安一老厨
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
     * @author 安一老厨
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
     * @author 安一老厨
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
     * @return Map<String, RouteMetaSpecialUrlModel> ${@link Map<String,
     * RouteMetaSpecialUrlModel>},map<filterId,RouteMetaSpecialUrlModel>
     * @author 安一老厨
     * @date 2022-03-05 14:42
     */
    public Map<String, RouteMetaSpecialUrlModel> getSpecialUrls(Set<String> filterIds) {
        if (CollUtil.isEmpty(filterIds)) {
            return Collections.emptyMap();
        }
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
