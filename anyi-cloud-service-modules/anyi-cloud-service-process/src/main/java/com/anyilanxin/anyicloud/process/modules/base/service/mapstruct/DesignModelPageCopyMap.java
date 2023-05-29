

package com.anyilanxin.anyicloud.process.modules.base.service.mapstruct;

import com.anyilanxin.anyicloud.corecommon.base.service.mapstruct.BaseThreeMap;
import com.anyilanxin.anyicloud.process.modules.base.controller.vo.DesignModelPageVo;
import com.anyilanxin.anyicloud.process.modules.base.entity.DesignModelEntity;
import com.anyilanxin.anyicloud.process.modules.base.service.dto.DesignModelPageDto;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

/**
 * 实体相互转换
 *
 * @author zxh
 * @since 2021-11-25 05:22:56
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, builder = @Builder(disableBuilder = true))
public interface DesignModelPageCopyMap extends BaseThreeMap<DesignModelEntity, DesignModelPageDto, DesignModelPageVo> {
}
