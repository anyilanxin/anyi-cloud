package com.anyilanxin.skillfull.system.modules.common.service.mapstruct;

import com.anyilanxin.skillfull.corecommon.base.service.mapstruct.BaseMap;
import com.anyilanxin.skillfull.system.modules.common.controller.vo.CommonDictItemVo;
import com.anyilanxin.skillfull.system.modules.common.entity.CommonDictItemEntity;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

/**
 * 数据字典配置项表(CommonDictItem)Vo与Entity相互转换
 *
 * @author zxiaozhou
 * @since 2020-11-02 09:25:29
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, builder = @Builder(disableBuilder = true))
public interface CommonDictItemVoMap extends BaseMap<CommonDictItemVo, CommonDictItemEntity> {
}
