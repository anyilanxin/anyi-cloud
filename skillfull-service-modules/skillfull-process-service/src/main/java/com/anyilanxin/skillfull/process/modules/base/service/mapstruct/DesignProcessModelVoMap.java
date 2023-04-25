package com.anyilanxin.skillfull.process.modules.base.service.mapstruct;

import com.anyilanxin.skillfull.corecommon.base.service.mapstruct.BaseMap;
import com.anyilanxin.skillfull.process.modules.base.controller.vo.DesignModelVo;
import com.anyilanxin.skillfull.process.modules.base.entity.DesignModelEntity;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

/**
 * 流程模型管理(DesignModel)Vo与Entity相互转换
 *
 * @author zxiaozhou
 * @since 2020-10-15 22:17:55
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, builder = @Builder(disableBuilder = true))
public interface DesignProcessModelVoMap extends BaseMap<DesignModelVo, DesignModelEntity> {
}
