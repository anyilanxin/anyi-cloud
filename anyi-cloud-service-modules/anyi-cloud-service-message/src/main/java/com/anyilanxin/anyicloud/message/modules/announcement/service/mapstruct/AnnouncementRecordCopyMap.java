

package com.anyilanxin.anyicloud.message.modules.announcement.service.mapstruct;

import com.anyilanxin.anyicloud.corecommon.base.service.mapstruct.BaseThreeMap;
import com.anyilanxin.anyicloud.message.modules.announcement.controller.vo.AnnouncementRecordVo;
import com.anyilanxin.anyicloud.message.modules.announcement.entity.AnnouncementRecordEntity;
import com.anyilanxin.anyicloud.message.modules.announcement.service.dto.AnnouncementRecordDto;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

/**
 * 实体相互转换
 *
 * @author zxh
 * @copyright zhouxuanhong（https://anyilanxin.com）
 * @since 2022-03-29 08:35:34
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, builder = @Builder(disableBuilder = true))
public interface AnnouncementRecordCopyMap extends BaseThreeMap<AnnouncementRecordEntity, AnnouncementRecordDto, AnnouncementRecordVo> {
}
