/**
* Copyright (c) 2021-2022 ZHOUXUANHONG(安一老厨)<anyilanxin@aliyun.com>
*
* <p>AnYi Cloud Licensed under the Apache License, Version 2.0 (the "License"); you may not use
* this file except in compliance with the License. You may obtain a copy of the License at
*
* <p>http://www.apache.org/licenses/LICENSE-2.0
*
* <p>Unless required by applicable law or agreed to in writing, software distributed under the
* License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
* express or implied. See the License for the specific language governing permissions and
* limitations under the License.
*
* <p>AnYi Cloud 采用APACHE LICENSE 2.0开源协议，您在使用过程中，需要注意以下几点：
*
* <p>1.请不要删除和修改根目录下的LICENSE文件。 2.请不要删除和修改 AnYi Cloud 源码头部的版权声明。 3.请保留源码和相关描述文件的项目出处，作者声明等。
* 4.分发源码时候，请注明软件出处 https://github.com/anyilanxin/anyi-cloud 5.在修改包名，模块名称，项目代码等时，请注明软件出处
* https://github.com/anyilanxin/anyi-cloud 6.若您的项目无法满足以上几点，可申请商业授权
*/
package com.anyilanxin.skillfull.system.modules.manage.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.CollectionUtil;
import com.anyilanxin.skillfull.corecommon.constant.Status;
import com.anyilanxin.skillfull.corecommon.exception.ResponseException;
import com.anyilanxin.skillfull.corecommon.utils.I18nUtil;
import com.anyilanxin.skillfull.system.modules.manage.controller.vo.ManageRoutePredicateVo;
import com.anyilanxin.skillfull.system.modules.manage.entity.ManageRoutePredicateEntity;
import com.anyilanxin.skillfull.system.modules.manage.mapper.ManageRoutePredicateMapper;
import com.anyilanxin.skillfull.system.modules.manage.service.IManageRoutePredicateService;
import com.anyilanxin.skillfull.system.modules.manage.service.dto.ManageRoutePredicateDto;
import com.anyilanxin.skillfull.system.modules.manage.service.mapstruct.ManageRoutePredicateCopyMap;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import java.util.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
* 路由断言(ManageRoutePredicate)业务层实现
*
* @author zxiaozhou
* @copyright zxiaozhou（https://skillfull.divisu.com）
* @date 2021-12-19 10:37:42
* @since JDK1.8
*/
@Slf4j
@Service
@RequiredArgsConstructor
public class ManageRoutePredicateServiceImpl
        extends ServiceImpl<ManageRoutePredicateMapper, ManageRoutePredicateEntity>
        implements IManageRoutePredicateService {
    private final ManageRoutePredicateCopyMap map;
    private final ManageRoutePredicateMapper mapper;

    @Override
    @Transactional(rollbackFor = {Exception.class, Error.class})
    public void save(
            List<ManageRoutePredicateVo> vos, String routerId, String serviceId, boolean override)
            throws RuntimeException {
        if (override) {
            LambdaQueryWrapper<ManageRoutePredicateEntity> lambdaQueryWrapper =
                    new LambdaQueryWrapper<>();
            lambdaQueryWrapper.eq(ManageRoutePredicateEntity::getRouteId, routerId);
            List<ManageRoutePredicateEntity> list = this.list(lambdaQueryWrapper);
            if (CollUtil.isNotEmpty(list)) {
                Set<String> predicateIds = new HashSet<>(list.size());
                list.forEach(v -> predicateIds.add(v.getPredicateId()));
                int i = mapper.physicalDeleteBatchIds(predicateIds);
                if (i <= 0) {
                    throw new ResponseException(Status.DATABASE_BASE_ERROR, "删除历史数据失败");
                }
            }
        }
        // 保存新数据
        if (CollUtil.isNotEmpty(vos)) {
            List<ManageRoutePredicateEntity> list = new ArrayList<>(vos.size());
            vos.forEach(
                    v -> {
                        ManageRoutePredicateEntity manageRoutePredicateEntity = map.vToE(v);
                        manageRoutePredicateEntity.setRouteId(routerId);
                        manageRoutePredicateEntity.setServiceId(serviceId);
                        list.add(manageRoutePredicateEntity);
                    });
            boolean b = this.saveBatch(list);
            if (!b) {
                throw new ResponseException(
                        Status.DATABASE_BASE_ERROR, I18nUtil.get("ServiceImpl.SaveDataFail"));
            }
        }
    }

    @Override
    @Transactional(
            rollbackFor = {Exception.class, Error.class},
            readOnly = true)
    public List<ManageRoutePredicateDto> getByRouteId(String routeId) throws RuntimeException {
        LambdaQueryWrapper<ManageRoutePredicateEntity> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(ManageRoutePredicateEntity::getRouteId, routeId);
        return map.eToD(this.list(lambdaQueryWrapper));
    }

    @Override
    @Transactional(
            rollbackFor = {Exception.class, Error.class},
            readOnly = true)
    public Map<String, List<ManageRoutePredicateDto>> getByRouteId(Set<String> routeIds)
            throws RuntimeException {
        LambdaQueryWrapper<ManageRoutePredicateEntity> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.in(ManageRoutePredicateEntity::getRouteId, routeIds);
        List<ManageRoutePredicateEntity> list = this.list(lambdaQueryWrapper);
        Map<String, List<ManageRoutePredicateDto>> stringListMap = new HashMap<>();
        if (CollUtil.isNotEmpty(list)) {
            list.forEach(
                    v -> {
                        List<ManageRoutePredicateDto> manageRoutePredicateDtos =
                                stringListMap.get(v.getRouteId());
                        if (CollectionUtil.isEmpty(manageRoutePredicateDtos)) {
                            manageRoutePredicateDtos = new ArrayList<>();
                        }
                        manageRoutePredicateDtos.add(map.eToD(v));
                        stringListMap.put(v.getRouteId(), manageRoutePredicateDtos);
                    });
        }
        return stringListMap;
    }

    @Override
    @Transactional(
            rollbackFor = {Exception.class, Error.class},
            readOnly = true)
    public ManageRoutePredicateDto getById(String predicateId) throws RuntimeException {
        ManageRoutePredicateEntity byId = super.getById(predicateId);
        if (Objects.isNull(byId)) {
            throw new ResponseException(
                    Status.DATABASE_BASE_ERROR, I18nUtil.get("ServiceImpl.QueryDataFail"));
        }
        return map.eToD(byId);
    }

    @Override
    @Transactional(rollbackFor = {Exception.class, Error.class})
    public void deleteByRouterId(String routerId) throws RuntimeException {
        LambdaQueryWrapper<ManageRoutePredicateEntity> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(ManageRoutePredicateEntity::getRouteId, routerId);
        this.remove(lambdaQueryWrapper);
    }

    @Override
    @Transactional(rollbackFor = {Exception.class, Error.class})
    public void deleteByRouterIds(Set<String> routerIds) throws RuntimeException {
        LambdaQueryWrapper<ManageRoutePredicateEntity> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.in(ManageRoutePredicateEntity::getRouteId, routerIds);
        this.remove(lambdaQueryWrapper);
    }
}
