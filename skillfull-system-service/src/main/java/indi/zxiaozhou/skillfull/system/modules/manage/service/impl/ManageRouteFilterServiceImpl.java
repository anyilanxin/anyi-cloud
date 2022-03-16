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
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import indi.zxiaozhou.skillfull.corecommon.constant.Status;
import indi.zxiaozhou.skillfull.corecommon.exception.ResponseException;
import indi.zxiaozhou.skillfull.system.modules.manage.controller.vo.ManageRouteFilterVo;
import indi.zxiaozhou.skillfull.system.modules.manage.entity.ManageRouteFilterEntity;
import indi.zxiaozhou.skillfull.system.modules.manage.mapper.ManageRouteFilterMapper;
import indi.zxiaozhou.skillfull.system.modules.manage.service.IManageRouteFilterService;
import indi.zxiaozhou.skillfull.system.modules.manage.service.dto.ManageRouteFilterDto;
import indi.zxiaozhou.skillfull.system.modules.manage.service.mapstruct.ManageRouteFilterCopyMap;
import indi.zxiaozhou.skillfull.system.modules.manage.service.mapstruct.ManageRouteFilterPageCopyMap;
import indi.zxiaozhou.skillfull.system.modules.manage.service.mapstruct.ManageRouteFilterQueryCopyMap;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * 路由过滤器(ManageRouteFilter)业务层实现
 *
 * @author zxiaozhou
 * @copyright zxiaozhou（https://divisu.com）
 * @date 2021-12-19 10:37:42
 * @since JDK1.8
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ManageRouteFilterServiceImpl extends ServiceImpl<ManageRouteFilterMapper, ManageRouteFilterEntity> implements IManageRouteFilterService {
    private final ManageRouteFilterCopyMap map;
    private final ManageRouteFilterPageCopyMap pageMap;
    private final ManageRouteFilterQueryCopyMap queryMap;
    private final ManageRouteFilterMapper mapper;


    @Override
    @Transactional(rollbackFor = {Exception.class, Error.class})
    public void save(List<ManageRouteFilterVo> vos, String routerId, String serviceId, boolean override) throws RuntimeException {
        if (override) {
            LambdaQueryWrapper<ManageRouteFilterEntity> lambdaQueryWrapper = new LambdaQueryWrapper<>();
            lambdaQueryWrapper.eq(ManageRouteFilterEntity::getRouteId, routerId);
            List<ManageRouteFilterEntity> list = this.list(lambdaQueryWrapper);
            if (CollectionUtil.isNotEmpty(list)) {
                Set<String> filterIds = new HashSet<>(list.size());
                list.forEach(v -> filterIds.add(v.getFilterId()));
                int i = mapper.physicalDeleteBatchIds(filterIds);
                if (i <= 0) {
                    throw new ResponseException(Status.DATABASE_BASE_ERROR, "删除历史数据失败");
                }
            }
        }
        // 保存新数据
        if (CollectionUtil.isNotEmpty(vos)) {
            List<ManageRouteFilterEntity> list = new ArrayList<>(vos.size());
            vos.forEach(v -> {
                ManageRouteFilterEntity manageRouteFilterEntity = map.vToE(v);
                manageRouteFilterEntity.setRouteId(routerId);
                manageRouteFilterEntity.setServiceId(serviceId);
                list.add(manageRouteFilterEntity);
            });
            boolean b = this.saveBatch(list);
            if (!b) {
                throw new ResponseException(Status.DATABASE_BASE_ERROR, "保存数据失败");
            }
        }
    }


    @Override
    @Transactional(rollbackFor = {Exception.class, Error.class}, readOnly = true)
    public List<ManageRouteFilterDto> getByRouteId(String routeId) throws RuntimeException {
        LambdaQueryWrapper<ManageRouteFilterEntity> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(ManageRouteFilterEntity::getRouteId, routeId);
        return map.eToD(this.list(lambdaQueryWrapper));
    }

    @Override
    @Transactional(rollbackFor = {Exception.class, Error.class}, readOnly = true)
    public Map<String, List<ManageRouteFilterDto>> getByRouteId(Set<String> routeIds) throws RuntimeException {
        LambdaQueryWrapper<ManageRouteFilterEntity> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.in(ManageRouteFilterEntity::getRouteId, routeIds);
        List<ManageRouteFilterEntity> list = this.list(lambdaQueryWrapper);
        Map<String, List<ManageRouteFilterDto>> stringListMap = new HashMap<>();
        if (CollectionUtil.isNotEmpty(list)) {
            list.forEach(v -> {
                List<ManageRouteFilterDto> manageRouteFilterDtos = stringListMap.get(v.getRouteId());
                if (CollectionUtil.isEmpty(manageRouteFilterDtos)) {
                    manageRouteFilterDtos = new ArrayList<>();
                }
                manageRouteFilterDtos.add(map.eToD(v));
                stringListMap.put(v.getRouteId(), manageRouteFilterDtos);
            });
        }
        return stringListMap;
    }

    @Override
    @Transactional(rollbackFor = {Exception.class, Error.class}, readOnly = true)
    public ManageRouteFilterDto getById(String filterId) throws RuntimeException {
        ManageRouteFilterEntity byId = super.getById(filterId);
        if (Objects.isNull(byId)) {
            throw new ResponseException(Status.DATABASE_BASE_ERROR, "未找到符合条件数据");
        }
        return map.eToD(byId);
    }

    @Override
    @Transactional(rollbackFor = {Exception.class, Error.class})
    public void deleteByRouterId(String routerId) throws RuntimeException {
        LambdaQueryWrapper<ManageRouteFilterEntity> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(ManageRouteFilterEntity::getRouteId, routerId);
        this.remove(lambdaQueryWrapper);
    }


    @Override
    @Transactional(rollbackFor = {Exception.class, Error.class})
    public void deleteByRouterIds(Set<String> routerIds) throws RuntimeException {
        LambdaQueryWrapper<ManageRouteFilterEntity> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.in(ManageRouteFilterEntity::getRouteId, routerIds);
        this.remove(lambdaQueryWrapper);
    }
}
