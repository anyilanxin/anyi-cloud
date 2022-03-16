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
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import indi.zxiaozhou.skillfull.corecommon.base.model.system.ManageSwaggerInfoModel;
import indi.zxiaozhou.skillfull.corecommon.constant.Status;
import indi.zxiaozhou.skillfull.corecommon.exception.ResponseException;
import indi.zxiaozhou.skillfull.coredatabase.base.service.dto.PageDto;
import indi.zxiaozhou.skillfull.system.modules.manage.controller.vo.ManageServicePageVo;
import indi.zxiaozhou.skillfull.system.modules.manage.controller.vo.ManageServiceVo;
import indi.zxiaozhou.skillfull.system.modules.manage.entity.ManageServiceEntity;
import indi.zxiaozhou.skillfull.system.modules.manage.mapper.ManageServiceMapper;
import indi.zxiaozhou.skillfull.system.modules.manage.service.*;
import indi.zxiaozhou.skillfull.system.modules.manage.service.dto.*;
import indi.zxiaozhou.skillfull.system.modules.manage.service.mapstruct.ManageServiceCopyMap;
import indi.zxiaozhou.skillfull.system.modules.manage.service.mapstruct.ManageServicePageCopyMap;
import indi.zxiaozhou.skillfull.system.modules.manage.service.mapstruct.ManageServiceQueryCopyMap;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

/**
 * 服务管理(ManageService)业务层实现
 *
 * @author zxiaozhou
 * @copyright zxiaozhou（https://divisu.com）
 * @date 2021-12-19 00:22:20
 * @since JDK1.8
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ManageServiceServiceImpl extends ServiceImpl<ManageServiceMapper, ManageServiceEntity> implements IManageServiceService {
    private final ManageServiceCopyMap map;
    private final ManageServicePageCopyMap pageMap;
    private final ManageServiceQueryCopyMap queryMap;
    private final ManageServiceMapper mapper;
    private final IManageRouteService routeService;
    private final ICustomNacosNamingService nacosNamingService;
    private final INacosService nacosService;
    private final IManageCustomFilterService customFilterService;
    private final IManageSyncService syncService;


    @Override
    @Transactional(rollbackFor = {Exception.class, Error.class})
    public void save(ManageServiceVo vo) throws RuntimeException {
        ManageServiceEntity entity = map.vToE(vo);
        boolean result = super.save(entity);
        if (!result) {
            throw new ResponseException(Status.DATABASE_BASE_ERROR, "保存数据失败");
        }
        // 刷新网关
        syncService.syncGateway(true);
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
            throw new ResponseException(Status.DATABASE_BASE_ERROR, "更新数据失败");
        }
        // 刷新网关
        syncService.syncGateway(true);
    }


    @Override
    @Transactional(rollbackFor = {Exception.class, Error.class}, readOnly = true)
    public PageDto<ManageServicePageDto> pageByModel(ManageServicePageVo vo) throws RuntimeException {
        IPage<ManageServicePageDto> resultPage = mapper.pageByModel(vo.getPage(), vo);
        List<ManageServicePageDto> records = resultPage.getRecords();
        if (!CollectionUtils.isEmpty(records)) {
            // 获取所有服务并补充返回参数中实例健康情况
            List<NacosServiceInfoDto> servicesOfServer = nacosNamingService.getServicesOfServer(null, null, null);
            records.forEach(v -> {
                if (CollectionUtil.isNotEmpty(servicesOfServer)) {
                    String serviceCode = v.getServiceCode();
                    servicesOfServer.forEach(sv -> {
                        if (serviceCode.equals(sv.getName())) {
                            v.setInstanceNum(sv.getIpCount());
                            v.setHealthyNum(sv.getHealthyInstanceCount());
                            v.setUnhealthyNum(sv.getClusterCount() - sv.getHealthyInstanceCount());
                        }
                    });
                }
            });
        }
        return new PageDto<>(resultPage, records);
    }

    @Override
    @Transactional(rollbackFor = {Exception.class, Error.class}, readOnly = true)
    public Map<String, ManageSwaggerInfoModel> selectSwaggerInfo() throws RuntimeException {
        LambdaQueryWrapper<ManageServiceEntity> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(ManageServiceEntity::getEnableSwagger, 1);
        List<ManageServiceEntity> list = this.list(lambdaQueryWrapper);
        if (CollectionUtil.isNotEmpty(list)) {
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
            throw new ResponseException(Status.DATABASE_BASE_ERROR, "未找到符合条件数据");
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
            throw new ResponseException(Status.DATABASE_BASE_ERROR, "删除数据失败");
        }
        // 删除路由
        routeService.deleteByServiceId(serviceId);
        // 删除自定义过滤器
        customFilterService.deleteByServiceId(serviceId);
        // 刷新网关
        syncService.syncGateway(true);
    }


    @Override
    @Transactional(rollbackFor = {Exception.class, Error.class}, readOnly = true)
    public SystemStatDto systemStat() throws RuntimeException {
        SystemStatDto dto = new SystemStatDto();
        List<ManageServiceEntity> list = this.list();
        AtomicReference<Integer> healthyInstanceCountAtom = new AtomicReference<>(0);
        Set<String> manageServiceNames = new HashSet<>(list.size());
        Set<String> allServiceNames = new HashSet<>();
        list.forEach(v -> {
            manageServiceNames.add(v.getServiceCode());
            allServiceNames.add(v.getServiceCode());
        });
        AtomicReference<Integer> instanceCountAtom = new AtomicReference<>(0);
        List<NacosServiceInfoDto> servicesOfServer = nacosNamingService.getServicesOfServer(null, null, null);
        if (CollectionUtil.isNotEmpty(servicesOfServer)) {
            servicesOfServer.forEach(sv -> {
                allServiceNames.add(sv.getName());
                healthyInstanceCountAtom.updateAndGet(v1 -> v1 + sv.getHealthyInstanceCount());
                instanceCountAtom.updateAndGet(v1 -> v1 + sv.getIpCount());
            });
        }
        int healthyInstanceCount = Objects.nonNull(healthyInstanceCountAtom.get()) ? healthyInstanceCountAtom.get() : 0;
        int instanceCount = Objects.nonNull(instanceCountAtom.get()) ? instanceCountAtom.get() : 0;
        dto.setHealthyInstanceCount(healthyInstanceCount);
        dto.setNoHealthyInstanceCount(instanceCount - healthyInstanceCount);
        dto.setManageTotalService(manageServiceNames.size());
        dto.setNotManageTotalService(allServiceNames.size() - dto.getManageTotalService());
        return dto;
    }


    @Override
    public List<ValidServiceInfoDto> getValidServiceInfo() {
        LambdaQueryWrapper<ManageServiceEntity> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(ManageServiceEntity::getServiceState, 1);
        List<ManageServiceEntity> list = this.list(lambdaQueryWrapper);
        if (CollectionUtil.isNotEmpty(list)) {
            List<ValidServiceInfoDto> result = new ArrayList<>();
            list.forEach(v -> {
                ValidServiceInfoDto dto = ValidServiceInfoDto.builder()
                        .label(v.getServiceName())
                        .value(v.getServiceCode())
                        .build();
                result.add(dto);
            });
            return result;
        }
        return Collections.emptyList();
    }
}
