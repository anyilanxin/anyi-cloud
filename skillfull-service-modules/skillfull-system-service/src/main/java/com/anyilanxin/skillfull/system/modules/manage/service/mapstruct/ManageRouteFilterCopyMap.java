package com.anyilanxin.skillfull.system.modules.manage.service.mapstruct;

import com.anyilanxin.skillfull.corecommon.base.service.mapstruct.BaseThreeMap;
import com.anyilanxin.skillfull.system.modules.manage.controller.vo.ManageRouteFilterVo;
import com.anyilanxin.skillfull.system.modules.manage.entity.ManageRouteFilterEntity;
import com.anyilanxin.skillfull.system.modules.manage.service.dto.ManageRouteFilterDto;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

/**
 * 实体相互转换
 *
 * @author zxiaozhou
 * @copyright zxiaozhou（https://skillfull.divisu.com）
 * @since 2021-12-19 10:37:42
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, builder = @Builder(disableBuilder = true))
public interface ManageRouteFilterCopyMap extends BaseThreeMap<ManageRouteFilterEntity, ManageRouteFilterDto, ManageRouteFilterVo> {
}
