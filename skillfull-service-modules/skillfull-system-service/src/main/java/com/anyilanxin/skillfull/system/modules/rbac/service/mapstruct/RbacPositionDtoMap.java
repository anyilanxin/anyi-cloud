package com.anyilanxin.skillfull.system.modules.rbac.service.mapstruct;


import com.anyilanxin.skillfull.corecommon.base.service.mapstruct.BaseMap;
import com.anyilanxin.skillfull.system.modules.rbac.entity.RbacPositionEntity;
import com.anyilanxin.skillfull.system.modules.rbac.service.dto.RbacPositionDto;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

/**
 * 职位表(RbacPosition)Dto与Entity相互转换
 *
 * @author zxiaozhou
 * @since 2021-01-19 18:17:59
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, builder = @Builder(disableBuilder = true))
public interface RbacPositionDtoMap extends BaseMap<RbacPositionDto, RbacPositionEntity> {
}
