

package com.anyilanxin.anyicloud.system.modules.common.service.mapstruct;

import com.anyilanxin.anyicloud.corecommon.base.service.mapstruct.BaseMap;
import com.anyilanxin.anyicloud.system.modules.common.entity.CommonAreaEntity;
import com.anyilanxin.anyicloud.system.modules.common.service.dto.CommonAreaPageDto;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

/**
 * 区域表(CommonArea)PageDto与Entity相互转换
 *
 * @author zxh
 * @since 2020-11-02 09:25:07
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, builder = @Builder(disableBuilder = true))
public interface CommonAreaPageDtoMap extends BaseMap<CommonAreaPageDto, CommonAreaEntity> {
}
