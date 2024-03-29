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

package com.anyilanxin.anyicloud.system.modules.manage.service.impl;

import com.anyilanxin.anyicloud.corecommon.constant.AnYiResultStatus;
import com.anyilanxin.anyicloud.corecommon.exception.AnYiResponseException;
import com.anyilanxin.anyicloud.corecommon.model.common.AnYiPageResult;
import com.anyilanxin.anyicloud.corecommon.model.system.ManageSwaggerInfoModel;
import com.anyilanxin.anyicloud.corecommon.utils.AnYiI18nUtil;
import com.anyilanxin.anyicloud.database.utils.PageUtils;
import com.anyilanxin.anyicloud.system.modules.manage.controller.vo.ManageServicePageQuery;
import com.anyilanxin.anyicloud.system.modules.manage.controller.vo.ManageServiceVo;
import com.anyilanxin.anyicloud.system.modules.manage.entity.ManageServiceEntity;
import com.anyilanxin.anyicloud.system.modules.manage.mapper.ManageServiceMapper;
import com.anyilanxin.anyicloud.system.modules.manage.service.IManageCustomFilterService;
import com.anyilanxin.anyicloud.system.modules.manage.service.IManageRouteService;
import com.anyilanxin.anyicloud.system.modules.manage.service.IManageServiceService;
import com.anyilanxin.anyicloud.system.modules.manage.service.IManageSyncService;
import com.anyilanxin.anyicloud.system.modules.manage.service.dto.ManageServiceDto;
import com.anyilanxin.anyicloud.system.modules.manage.service.dto.ManageServicePageDto;
import com.anyilanxin.anyicloud.system.modules.manage.service.dto.SystemStatDto;
import com.anyilanxin.anyicloud.system.modules.manage.service.dto.ValidServiceInfoDto;
import com.anyilanxin.anyicloud.system.modules.manage.service.mapstruct.ManageServiceCopyMap;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dromara.hutool.core.collection.CollUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * 服务管理(ManageService)业务层实现
 *
 * @author zxh
 * @copyright zhouxuanhong（https://anyilanxin.com）
 * @date 2021-12-19 00:22:20
 * @since 1.0.0
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ManageServiceServiceImpl extends ServiceImpl<ManageServiceMapper, ManageServiceEntity> implements IManageServiceService {
    private final ManageServiceCopyMap map;
    private final ManageServiceMapper mapper;
    private final IManageRouteService routeService;
    private final IManageCustomFilterService customFilterService;
    private final IManageSyncService syncService;

    @Override
    @Transactional(rollbackFor = {Exception.class, Error.class})
    public void save(ManageServiceVo vo) throws RuntimeException {
        ManageServiceEntity entity = map.vToE(vo);
        boolean result = super.save(entity);
        if (!result) {
            throw new AnYiResponseException(AnYiResultStatus.DATABASE_BASE_ERROR, AnYiI18nUtil.get("ServiceImpl.SaveDataFail"));
        }
        // 刷新网关
        syncService.updateServiceRoute(entity.getServiceId());
    }


    @Override
    @Transactional(rollbackFor = {Exception.class, Error.class})
    public void updateById(String serviceId, ManageServiceVo vo) throws RuntimeException {
        // 查询数据是否存在
        this.getById(serviceId);
        // 更新数据
        ManageServiceEntity entity = map.vToE(vo);
        entity.setServiceId(serviceId);
        boolean result = super.updateById(entity);
        if (!result) {
            throw new AnYiResponseException(AnYiResultStatus.DATABASE_BASE_ERROR, AnYiI18nUtil.get("ServiceImpl.UpdateDataFail"));
        }
        // 刷新网关
        syncService.updateServiceRoute(entity.getServiceId());
    }


    @Override
    @Transactional(rollbackFor = {Exception.class, Error.class}, readOnly = true)
    public AnYiPageResult<ManageServicePageDto> pageByModel(ManageServicePageQuery vo) throws RuntimeException {
        IPage<ManageServicePageDto> resultPage = mapper.pageByModel(PageUtils.getPage(vo), vo);
        List<ManageServicePageDto> records = resultPage.getRecords();
        return PageUtils.toPageData(resultPage, records);
    }


    @Override
    @Transactional(rollbackFor = {Exception.class, Error.class}, readOnly = true)
    public Map<String, ManageSwaggerInfoModel> selectSwaggerInfo() throws RuntimeException {
        LambdaQueryWrapper<ManageServiceEntity> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(ManageServiceEntity::getEnableSwagger, 1);
        List<ManageServiceEntity> list = this.list(lambdaQueryWrapper);
        if (CollUtil.isNotEmpty(list)) {
            Map<String, ManageSwaggerInfoModel> result = new HashMap<>(list.size());
            list.forEach(v -> {
                ManageSwaggerInfoModel dto = new ManageSwaggerInfoModel();
                dto.setIsLoadBalancer(v.getIsLoadBalancer());
                dto.setServiceCode(v.getServiceCode());
                dto.setSwaggerConfigUrl(v.getSwaggerConfigUrl());
                result.put(v.getServiceCode(), dto);
            });
            return result;
        }
        return null;
    }


    @Override
    @Transactional(rollbackFor = {Exception.class, Error.class}, readOnly = true)
    public ManageServiceDto getById(String serviceId) throws RuntimeException {
        ManageServiceEntity byId = super.getById(serviceId);
        if (Objects.isNull(byId)) {
            throw new AnYiResponseException(AnYiResultStatus.DATABASE_BASE_ERROR, AnYiI18nUtil.get("ServiceImpl.QueryDataFail"));
        }
        return map.eToD(byId);
    }


    @Override
    @Transactional(rollbackFor = {Exception.class, Error.class})
    public void deleteById(String serviceId) throws RuntimeException {
        // 查询数据是否存在
        this.getById(serviceId);
        // 删除数据
        boolean b = this.removeById(serviceId);
        if (!b) {
            throw new AnYiResponseException(AnYiResultStatus.DATABASE_BASE_ERROR, AnYiI18nUtil.get("ServiceImpl.DeleteDataFail"));
        }
        // 删除路由
        routeService.deleteByServiceId(serviceId);
        // 删除自定义过滤器
        customFilterService.deleteByServiceId(serviceId);
        // 刷新网关
        syncService.deleteServiceRoute(serviceId);
    }


    @Override
    @Transactional(rollbackFor = {Exception.class, Error.class}, readOnly = true)
    public SystemStatDto systemStat() throws RuntimeException {
        return mapper.systemStat();
    }


    @Override
    public List<ValidServiceInfoDto> getValidServiceInfo() {
        LambdaQueryWrapper<ManageServiceEntity> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(ManageServiceEntity::getServiceState, 1);
        List<ManageServiceEntity> list = this.list(lambdaQueryWrapper);
        if (CollUtil.isNotEmpty(list)) {
            List<ValidServiceInfoDto> result = new ArrayList<>();
            list.forEach(v -> {
                ValidServiceInfoDto dto = ValidServiceInfoDto.builder().label(v.getServiceName()).value(v.getServiceCode()).serviceCode(v.getServiceCode()).build();
                result.add(dto);
            });
            return result;
        }
        return Collections.emptyList();
    }
}
