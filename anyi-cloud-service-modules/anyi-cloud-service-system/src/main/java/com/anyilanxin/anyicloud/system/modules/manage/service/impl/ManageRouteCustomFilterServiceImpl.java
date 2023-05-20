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

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.CollectionUtil;
import com.anyilanxin.anyicloud.corecommon.constant.Status;
import com.anyilanxin.anyicloud.corecommon.exception.ResponseException;
import com.anyilanxin.anyicloud.corecommon.utils.I18nUtil;
import com.anyilanxin.anyicloud.system.modules.manage.controller.vo.ManageRouteCustomFilterVo;
import com.anyilanxin.anyicloud.system.modules.manage.entity.ManageCustomFilterEntity;
import com.anyilanxin.anyicloud.system.modules.manage.entity.ManageRouteCustomFilterEntity;
import com.anyilanxin.anyicloud.system.modules.manage.mapper.ManageRouteCustomFilterMapper;
import com.anyilanxin.anyicloud.system.modules.manage.service.IManageCustomFilterService;
import com.anyilanxin.anyicloud.system.modules.manage.service.IManageRouteCustomFilterService;
import com.anyilanxin.anyicloud.system.modules.manage.service.dto.ManageCustomFilterSimpleDto;
import com.anyilanxin.anyicloud.system.modules.manage.service.dto.ManageRouteCustomFilterDto;
import com.anyilanxin.anyicloud.system.modules.manage.service.dto.RouterCustomFilterDto;
import com.anyilanxin.anyicloud.system.modules.manage.service.mapstruct.ManageCustomFilterSimpleCopyMap;
import com.anyilanxin.anyicloud.system.modules.manage.service.mapstruct.ManageRouteCustomFilterCopyMap;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import java.util.*;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 路由-自定义过滤器表(ManageRouteCustomFilter)业务层实现
 *
 * @author zxh
 * @copyright zhouxuanhong（https://anyilanxin.com）
 * @date 2021-12-19 00:22:17
 * @since 1.0.0
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ManageRouteCustomFilterServiceImpl extends ServiceImpl<ManageRouteCustomFilterMapper, ManageRouteCustomFilterEntity> implements IManageRouteCustomFilterService {
    private final ManageRouteCustomFilterCopyMap map;
    private final ManageRouteCustomFilterMapper mapper;
    private final ManageCustomFilterSimpleCopyMap simpleCopyMap;
    private final IManageCustomFilterService customFilterService;

    @Override
    @Transactional(rollbackFor = {Exception.class, Error.class})
    public void save(List<ManageRouteCustomFilterVo> customFilters, String routerId, boolean override) throws RuntimeException {
        if (override) {
            deleteByRouterId(routerId);
        }
        // 保存新数据
        if (CollUtil.isNotEmpty(customFilters)) {
            List<ManageRouteCustomFilterEntity> list = new ArrayList<>(customFilters.size());
            customFilters.forEach(v -> {
                ManageRouteCustomFilterEntity entity = ManageRouteCustomFilterEntity.builder().customFilterId(v.getCustomFilterId()).routeId(routerId).filterType(v.getFilterType()).build();
                list.add(entity);
            });
            boolean b = this.saveBatch(list);
            if (!b) {
                throw new ResponseException(Status.DATABASE_BASE_ERROR, I18nUtil.get("ServiceImpl.SaveDataFail"));
            }
        }
    }


    @Override
    public Map<String, List<ManageCustomFilterSimpleDto>> getByRouterIds(Set<String> routerIds) throws RuntimeException {
        if (CollectionUtil.isEmpty(routerIds)) {
            return Collections.emptyMap();
        }
        // 查询所有自定义过滤器id
        LambdaQueryWrapper<ManageRouteCustomFilterEntity> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.in(ManageRouteCustomFilterEntity::getRouteId, routerIds);
        List<ManageRouteCustomFilterEntity> list = this.list(lambdaQueryWrapper);
        Map<String, List<ManageCustomFilterSimpleDto>> resultMap = new HashMap<>();
        if (CollUtil.isNotEmpty(list)) {
            Map<String, List<String>> customFilterIdMap = new HashMap<>();
            Set<String> customFilterIds = new HashSet<>(list.size());
            list.forEach(v -> {
                customFilterIds.add(v.getCustomFilterId());
                List<String> strings = customFilterIdMap.get(v.getRouteId());
                if (CollectionUtil.isEmpty(strings)) {
                    strings = new ArrayList<>();
                }
                strings.add(v.getCustomFilterId());
                customFilterIdMap.put(v.getRouteId(), strings);
            });
            LambdaQueryWrapper<ManageCustomFilterEntity> customFilterLambdaQueryWrapper = new LambdaQueryWrapper<>();
            customFilterLambdaQueryWrapper.in(ManageCustomFilterEntity::getCustomFilterId, customFilterIds).eq(ManageCustomFilterEntity::getFilterStatus, 1);
            List<ManageCustomFilterEntity> customFilterEntityList = customFilterService.list(customFilterLambdaQueryWrapper);
            if (CollUtil.isNotEmpty(customFilterEntityList)) {
                List<ManageCustomFilterSimpleDto> manageCustomFilterSimpleDtos = simpleCopyMap.aToB(customFilterEntityList);
                customFilterIdMap.forEach((k, v) -> {
                    List<ManageCustomFilterSimpleDto> collect = manageCustomFilterSimpleDtos.stream().filter(sv -> v.contains(sv.getCustomFilterId())).collect(Collectors.toList());
                    resultMap.put(k, collect);
                });
            }
        }
        return resultMap;
    }


    @Override
    public List<ManageCustomFilterSimpleDto> getByRouterId(String routerId) throws RuntimeException {
        // 查询所有自定义过滤器id
        LambdaQueryWrapper<ManageRouteCustomFilterEntity> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(ManageRouteCustomFilterEntity::getRouteId, routerId);
        List<ManageRouteCustomFilterEntity> list = this.list(lambdaQueryWrapper);
        List<ManageCustomFilterSimpleDto> result = new ArrayList<>();
        if (CollUtil.isNotEmpty(list)) {
            // 获取自定义过滤器
            Set<String> customFilterIds = new HashSet<>();
            list.forEach(v -> customFilterIds.add(v.getCustomFilterId()));
            LambdaQueryWrapper<ManageCustomFilterEntity> customFilterLambdaQueryWrapper = new LambdaQueryWrapper<>();
            customFilterLambdaQueryWrapper.in(ManageCustomFilterEntity::getCustomFilterId, customFilterIds).eq(ManageCustomFilterEntity::getFilterStatus, 1);
            List<ManageCustomFilterEntity> customFilterEntityList = customFilterService.list(customFilterLambdaQueryWrapper);
            result = simpleCopyMap.aToB(customFilterEntityList);
        }
        return result;
    }


    @Override
    @Transactional(rollbackFor = {Exception.class, Error.class}, readOnly = true)
    public ManageRouteCustomFilterDto getById(String routeCustomFilterId) throws RuntimeException {
        ManageRouteCustomFilterEntity byId = super.getById(routeCustomFilterId);
        if (Objects.isNull(byId)) {
            throw new ResponseException(Status.DATABASE_BASE_ERROR, I18nUtil.get("ServiceImpl.QueryDataFail"));
        }
        return map.eToD(byId);
    }


    @Override
    @Transactional(rollbackFor = {Exception.class, Error.class})
    public void deleteByRouterId(String routerId) throws RuntimeException {
        LambdaQueryWrapper<ManageRouteCustomFilterEntity> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(ManageRouteCustomFilterEntity::getRouteId, routerId);
        List<ManageRouteCustomFilterEntity> list = this.list(lambdaQueryWrapper);
        if (CollUtil.isNotEmpty(list)) {
            Set<String> routeCustomFilterIds = new HashSet<>(list.size());
            list.forEach(v -> routeCustomFilterIds.add(v.getRouteCustomFilterId()));
            int i = mapper.physicalDeleteBatchIds(routeCustomFilterIds);
            if (i <= 0) {
                throw new ResponseException(Status.DATABASE_BASE_ERROR, "删除历史数据失败");
            }
        }
    }


    @Override
    @Transactional(rollbackFor = {Exception.class, Error.class})
    public void deleteByRouterIds(Set<String> routerIds) throws RuntimeException {
        LambdaQueryWrapper<ManageRouteCustomFilterEntity> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.in(ManageRouteCustomFilterEntity::getRouteId, routerIds);
        this.remove(lambdaQueryWrapper);
    }


    @Override
    public Map<String, RouterCustomFilterDto> getGatewayByRouterIds(Set<String> routerIds) throws RuntimeException {

        return new HashMap<>();
    }
}
