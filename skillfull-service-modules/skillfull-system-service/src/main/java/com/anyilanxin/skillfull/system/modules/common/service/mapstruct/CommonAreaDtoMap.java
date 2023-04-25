package com.anyilanxin.skillfull.system.modules.common.service.mapstruct;

import com.anyilanxin.skillfull.corecommon.base.service.mapstruct.BaseMap;
import com.anyilanxin.skillfull.system.modules.common.entity.CommonAreaEntity;
import com.anyilanxin.skillfull.system.modules.common.service.dto.CommonAreaDto;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

/**
 * 区域表(CommonArea)Dto与Entity相互转换
 *
 * @author zxiaozhou
 * @since 2020-11-02 09:25:07
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, builder = @Builder(disableBuilder = true))
public interface CommonAreaDtoMap extends BaseMap<CommonAreaDto, CommonAreaEntity> {
}
