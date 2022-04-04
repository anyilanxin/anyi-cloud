package indi.zxiaozhou.skillfull.storage.modules.local.service.mapstruct;

import indi.zxiaozhou.skillfull.corecommon.base.service.mapstruct.BaseThreeMap;
import indi.zxiaozhou.skillfull.storage.modules.local.controller.vo.LocalConfigVo;
import indi.zxiaozhou.skillfull.storage.modules.local.entity.LocalConfigEntity;
import indi.zxiaozhou.skillfull.storage.modules.local.service.dto.LocalConfigDto;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

/**
 * 本地文件配置(LocalConfig)Vo与Entity相互转换
 *
 * @author zxiaozhou
 * @since 2021-07-21 16:10:05
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, builder = @Builder(disableBuilder = true))
public interface LocalConfigMap extends BaseThreeMap<LocalConfigEntity, LocalConfigDto, LocalConfigVo> {
}
