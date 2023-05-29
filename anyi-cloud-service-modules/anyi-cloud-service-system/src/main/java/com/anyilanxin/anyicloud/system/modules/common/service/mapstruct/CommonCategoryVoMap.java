

package com.anyilanxin.anyicloud.system.modules.common.service.mapstruct;

import com.anyilanxin.anyicloud.corecommon.base.service.mapstruct.BaseMap;
import com.anyilanxin.anyicloud.system.modules.common.controller.vo.CommonCategoryVo;
import com.anyilanxin.anyicloud.system.modules.common.entity.CommonCategoryEntity;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

/**
 * 分类字典表(CommonCategory)Vo与Entity相互转换
 *
 * @author zxh
 * @since 2021-01-07 23:40:32
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, builder = @Builder(disableBuilder = true))
public interface CommonCategoryVoMap extends BaseMap<CommonCategoryVo, CommonCategoryEntity> {
}
