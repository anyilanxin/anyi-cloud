

package com.anyilanxin.anyicloud.system.modules.common.service.mapstruct;

import com.anyilanxin.anyicloud.corecommon.base.service.mapstruct.BaseMap;
import com.anyilanxin.anyicloud.system.modules.common.controller.vo.CommonAreaPageVo;
import com.anyilanxin.anyicloud.system.modules.common.entity.CommonAreaEntity;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

/**
 * 区域表(CommonArea)PageVo与Entity相互转换
 *
 * @author zxh
 * @since 2020-11-02 09:25:08
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, builder = @Builder(disableBuilder = true))
public interface CommonAreaPageVoMap extends BaseMap<CommonAreaPageVo, CommonAreaEntity> {
}
