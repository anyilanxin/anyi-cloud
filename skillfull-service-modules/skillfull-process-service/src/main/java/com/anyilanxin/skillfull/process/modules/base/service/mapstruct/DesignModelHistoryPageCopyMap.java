package com.anyilanxin.skillfull.process.modules.base.service.mapstruct;

import com.anyilanxin.skillfull.corecommon.base.service.mapstruct.BaseThreeMap;
import com.anyilanxin.skillfull.process.modules.base.controller.vo.DesignModelHistoryPageVo;
import com.anyilanxin.skillfull.process.modules.base.entity.DesignModelHistoryEntity;
import com.anyilanxin.skillfull.process.modules.base.service.dto.DesignModelHistoryPageDto;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

/**
 * 实体相互转换
 *
 * @author zxiaozhou
 * @since 2021-11-25 09:52:37
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, builder = @Builder(disableBuilder = true))
public interface DesignModelHistoryPageCopyMap extends BaseThreeMap<DesignModelHistoryEntity, DesignModelHistoryPageDto, DesignModelHistoryPageVo> {
}
