package com.anyilanxin.skillfull.process.modules.base.service.mapstruct;

import com.anyilanxin.skillfull.corecommon.base.service.mapstruct.BaseThreeMap;
import com.anyilanxin.skillfull.process.modules.base.controller.vo.ProcessCategoryVo;
import com.anyilanxin.skillfull.process.modules.base.entity.ProcessCategoryEntity;
import com.anyilanxin.skillfull.process.modules.base.service.dto.ProcessCategoryDto;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

/**
 * 实体相互转换
 *
 * @author zxiaozhou
 * @since 2021-11-19 10:47:01
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, builder = @Builder(disableBuilder = true))
public interface ProcessCategoryCopyMap extends BaseThreeMap<ProcessCategoryEntity, ProcessCategoryDto, ProcessCategoryVo> {
}
