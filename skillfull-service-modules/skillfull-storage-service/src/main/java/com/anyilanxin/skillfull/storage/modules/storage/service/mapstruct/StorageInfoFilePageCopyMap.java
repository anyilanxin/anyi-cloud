package com.anyilanxin.skillfull.storage.modules.storage.service.mapstruct;

import com.anyilanxin.skillfull.corecommon.base.service.mapstruct.BaseThreeMap;
import com.anyilanxin.skillfull.storage.modules.storage.controller.vo.StorageInfoFilePageVo;
import com.anyilanxin.skillfull.storage.modules.storage.entity.StorageInfoFileEntity;
import com.anyilanxin.skillfull.storage.modules.storage.service.dto.StorageInfoFilePageDto;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

/**
 * 实体相互转换
 *
 * @author zxiaozhou
 * @copyright zxiaozhou（https://skillfull.divisu.com）
 * @since 2022-04-05 09:57:59
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, builder = @Builder(disableBuilder = true))
public interface StorageInfoFilePageCopyMap extends BaseThreeMap<StorageInfoFileEntity, StorageInfoFilePageDto, StorageInfoFilePageVo> {
}
