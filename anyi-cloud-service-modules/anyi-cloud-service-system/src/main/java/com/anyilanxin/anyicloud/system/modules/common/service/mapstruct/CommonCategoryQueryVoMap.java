

package com.anyilanxin.anyicloud.system.modules.common.service.mapstruct;

import com.anyilanxin.anyicloud.corecommon.base.service.mapstruct.BaseMap;
import com.anyilanxin.anyicloud.system.modules.common.controller.vo.CommonCategoryQueryVo;
import com.anyilanxin.anyicloud.system.modules.common.entity.CommonCategoryEntity;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

/**
 * 分类字典表(CommonCategory)QueryVo与Entity相互转换
 *
 * @author zxh
 * @since 2021-01-07 23:40:39
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, builder = @Builder(disableBuilder = true))
public interface CommonCategoryQueryVoMap extends BaseMap<CommonCategoryQueryVo, CommonCategoryEntity> {
}
