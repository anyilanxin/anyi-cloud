package com.anyilanxin.skillfull.message.modules.manage.service.mapstruct;

import com.anyilanxin.skillfull.corecommon.base.service.mapstruct.BaseThreeMap;
import com.anyilanxin.skillfull.message.modules.manage.controller.vo.ManageSendRecordVo;
import com.anyilanxin.skillfull.message.modules.manage.entity.ManageSendRecordEntity;
import com.anyilanxin.skillfull.message.modules.manage.service.dto.ManageSendRecordDto;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

/**
 * 实体相互转换
 *
 * @author zxiaozhou
 * @copyright zxiaozhou（https://skillfull.divisu.com）
 * @since 2022-03-29 05:23:42
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, builder = @Builder(disableBuilder = true))
public interface ManageSendRecordCopyMap extends BaseThreeMap<ManageSendRecordEntity, ManageSendRecordDto, ManageSendRecordVo> {
}
