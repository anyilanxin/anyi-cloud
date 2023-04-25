package com.anyilanxin.skillfull.process.modules.base.service.mapstruct;

import com.anyilanxin.skillfull.corecommon.base.service.mapstruct.BaseThreeMap;
import com.anyilanxin.skillfull.process.modules.base.controller.vo.DesignModelVo;
import com.anyilanxin.skillfull.process.modules.base.entity.DesignModelEntity;
import com.anyilanxin.skillfull.process.modules.base.service.dto.DesignModelDto;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

/**
 * 实体相互转换
 *
 * @author zxiaozhou
 * @since 2021-11-25 05:22:56
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, builder = @Builder(disableBuilder = true))
public interface DesignModelCopyMap extends BaseThreeMap<DesignModelEntity, DesignModelDto, DesignModelVo> {
}
