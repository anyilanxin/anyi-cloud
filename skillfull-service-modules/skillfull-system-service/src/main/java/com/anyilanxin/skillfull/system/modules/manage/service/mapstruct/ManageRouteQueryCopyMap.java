package com.anyilanxin.skillfull.system.modules.manage.service.mapstruct;

import com.anyilanxin.skillfull.corecommon.base.service.mapstruct.BaseMap;
import com.anyilanxin.skillfull.system.modules.manage.controller.vo.ManageRouteQueryVo;
import com.anyilanxin.skillfull.system.modules.manage.entity.ManageRouteEntity;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

/**
 * 实体相互转换
 *
 * @author zxiaozhou
 * @copyright zxiaozhou（https://skillfull.divisu.com）
 * @since 2021-12-19 00:22:16
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, builder = @Builder(disableBuilder = true))
public interface ManageRouteQueryCopyMap extends BaseMap<ManageRouteQueryVo, ManageRouteEntity> {
}
