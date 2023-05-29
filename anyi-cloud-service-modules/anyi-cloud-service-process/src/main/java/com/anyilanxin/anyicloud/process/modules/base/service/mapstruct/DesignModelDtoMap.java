

package com.anyilanxin.anyicloud.process.modules.base.service.mapstruct;

import com.anyilanxin.anyicloud.corecommon.base.service.mapstruct.BaseMap;
import com.anyilanxin.anyicloud.process.modules.base.entity.DesignModelEntity;
import com.anyilanxin.anyicloud.process.modules.base.service.dto.DesignModelDto;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

/**
 * 流程模型管理(DesignModel)Dto与Entity相互转换
 *
 * @author zxh
 * @since 2020-10-15 22:17:55
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, builder = @Builder(disableBuilder = true))
public interface DesignModelDtoMap extends BaseMap<DesignModelDto, DesignModelEntity> {
}
