

package com.anyilanxin.anyicloud.system.modules.common.service.mapstruct;

import com.anyilanxin.anyicloud.corecommon.base.service.mapstruct.BaseMap;
import com.anyilanxin.anyicloud.system.modules.common.entity.CommonCategoryEntity;
import com.anyilanxin.anyicloud.system.modules.common.service.dto.CommonCategoryPageDto;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

/**
 * 分类字典表(CommonCategory)PageDto与Entity相互转换
 *
 * @author zxh
 * @since 2021-01-07 23:40:28
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, builder = @Builder(disableBuilder = true))
public interface CommonCategoryPageDtoMap extends BaseMap<CommonCategoryPageDto, CommonCategoryEntity> {
}
