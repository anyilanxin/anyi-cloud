package com.anyilanxin.skillfull.message.modules.manage.service.mapstruct;

import com.anyilanxin.skillfull.corecommon.base.service.mapstruct.BaseThreeMap;
import com.anyilanxin.skillfull.message.modules.manage.controller.vo.ManageAnnouncementPageVo;
import com.anyilanxin.skillfull.message.modules.manage.entity.ManageAnnouncementEntity;
import com.anyilanxin.skillfull.message.modules.manage.service.dto.ManageAnnouncementPageDto;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

/**
 * 实体相互转换
 *
 * @author zxiaozhou
 * @copyright zxiaozhou（https://skillfull.divisu.com）
 * @since 2022-03-29 08:34:22
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, builder = @Builder(disableBuilder = true))
public interface ManageAnnouncementPageCopyMap extends BaseThreeMap<ManageAnnouncementEntity, ManageAnnouncementPageDto, ManageAnnouncementPageVo> {
}
