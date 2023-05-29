

package com.anyilanxin.anyicloud.process.modules.base.service.mapstruct;

import com.anyilanxin.anyicloud.corecommon.base.service.mapstruct.BaseMap;
import com.anyilanxin.anyicloud.process.modules.base.controller.vo.ProcessCategoryQueryVo;
import com.anyilanxin.anyicloud.process.modules.base.entity.ProcessCategoryEntity;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

/**
 * 实体相互转换
 *
 * @author zxh
 * @since 2021-11-19 10:47:01
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, builder = @Builder(disableBuilder = true))
public interface ProcessCategoryQueryCopyMap extends BaseMap<ProcessCategoryQueryVo, ProcessCategoryEntity> {
}
