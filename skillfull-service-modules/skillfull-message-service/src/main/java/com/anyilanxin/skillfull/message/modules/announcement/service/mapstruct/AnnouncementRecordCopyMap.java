package com.anyilanxin.skillfull.message.modules.announcement.service.mapstruct;

import com.anyilanxin.skillfull.corecommon.base.service.mapstruct.BaseThreeMap;
import com.anyilanxin.skillfull.message.modules.announcement.controller.vo.AnnouncementRecordVo;
import com.anyilanxin.skillfull.message.modules.announcement.entity.AnnouncementRecordEntity;
import com.anyilanxin.skillfull.message.modules.announcement.service.dto.AnnouncementRecordDto;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

/**
 * 实体相互转换
 *
 * @author zxiaozhou
 * @copyright zxiaozhou（https://skillfull.divisu.com）
 * @since 2022-03-29 08:35:34
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, builder = @Builder(disableBuilder = true))
public interface AnnouncementRecordCopyMap extends BaseThreeMap<AnnouncementRecordEntity, AnnouncementRecordDto, AnnouncementRecordVo> {
}
