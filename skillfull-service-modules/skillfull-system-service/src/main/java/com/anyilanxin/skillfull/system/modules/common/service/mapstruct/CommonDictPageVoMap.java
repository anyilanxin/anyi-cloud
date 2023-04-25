package com.anyilanxin.skillfull.system.modules.common.service.mapstruct;

import com.anyilanxin.skillfull.corecommon.base.service.mapstruct.BaseMap;
import com.anyilanxin.skillfull.system.modules.common.controller.vo.CommonDictPageVo;
import com.anyilanxin.skillfull.system.modules.common.entity.CommonDictEntity;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

/**
 * 数据字典表(CommonDict)PageVo与Entity相互转换
 *
 * @author zxiaozhou
 * @since 2020-11-02 09:25:21
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, builder = @Builder(disableBuilder = true))
public interface CommonDictPageVoMap extends BaseMap<CommonDictPageVo, CommonDictEntity> {
}
