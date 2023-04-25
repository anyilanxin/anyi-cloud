package com.anyilanxin.skillfull.system.modules.common.service.mapstruct;

import com.anyilanxin.skillfull.corecommon.base.service.mapstruct.BaseMap;
import com.anyilanxin.skillfull.system.modules.common.service.dto.CommonCategoryDto;
import com.anyilanxin.skillfull.system.modules.common.service.dto.CommonCategoryTreeDto;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

/**
 * 分类字典表(CommonCategory)Dto与Entity相互转换
 *
 * @author zxiaozhou
 * @since 2021-01-07 23:40:25
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, builder = @Builder(disableBuilder = true))
public interface CommonCategoryTreeDtoMap extends BaseMap<CommonCategoryTreeDto, CommonCategoryDto> {
}
