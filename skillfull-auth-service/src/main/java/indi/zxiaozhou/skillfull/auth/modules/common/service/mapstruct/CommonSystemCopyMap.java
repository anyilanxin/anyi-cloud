package indi.zxiaozhou.skillfull.auth.modules.common.service.mapstruct;

import indi.zxiaozhou.skillfull.auth.modules.common.controller.vo.CommonSystemVo;
import indi.zxiaozhou.skillfull.auth.modules.common.entity.CommonSystemEntity;
import indi.zxiaozhou.skillfull.auth.modules.common.service.dto.CommonSystemDto;
import indi.zxiaozhou.skillfull.corecommon.base.service.mapstruct.BaseThreeMap;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

/**
 * 实体相互转换
 *
 * @author zxiaozhou
 * @since 2021-07-28 10:13:05
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, builder = @Builder(disableBuilder = true))
public interface CommonSystemCopyMap extends BaseThreeMap<CommonSystemEntity, CommonSystemDto, CommonSystemVo> {
}
