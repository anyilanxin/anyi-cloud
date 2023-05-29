

package com.anyilanxin.anyicloud.message.modules.announcement.service.mapstruct;

import com.anyilanxin.anyicloud.corecommon.base.service.mapstruct.BaseThreeMap;
import com.anyilanxin.anyicloud.message.modules.announcement.controller.vo.AnnouncementRecordPageVo;
import com.anyilanxin.anyicloud.message.modules.announcement.entity.AnnouncementRecordEntity;
import com.anyilanxin.anyicloud.message.modules.announcement.service.dto.AnnouncementRecordPageDto;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

/**
 * 实体相互转换
 *
 * @author zxh
 * @copyright zhouxuanhong（https://anyilanxin.com）
 * @since 2022-03-29 08:35:35
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, builder = @Builder(disableBuilder = true))
public interface AnnouncementRecordPageCopyMap extends BaseThreeMap<AnnouncementRecordEntity, AnnouncementRecordPageDto, AnnouncementRecordPageVo> {
}
