

package com.anyilanxin.anyicloud.message.modules.manage.service.mapstruct;

import com.anyilanxin.anyicloud.corecommon.base.service.mapstruct.BaseThreeMap;
import com.anyilanxin.anyicloud.message.modules.manage.controller.vo.ManageAnnouncementPageVo;
import com.anyilanxin.anyicloud.message.modules.manage.entity.ManageAnnouncementEntity;
import com.anyilanxin.anyicloud.message.modules.manage.service.dto.ManageAnnouncementPageDto;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

/**
 * 实体相互转换
 *
 * @author zxh
 * @copyright zhouxuanhong（https://anyilanxin.com）
 * @since 2022-03-29 08:34:22
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, builder = @Builder(disableBuilder = true))
public interface ManageAnnouncementPageCopyMap extends BaseThreeMap<ManageAnnouncementEntity, ManageAnnouncementPageDto, ManageAnnouncementPageVo> {
}
