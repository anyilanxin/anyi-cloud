

package com.anyilanxin.anyicloud.system.modules.common.service.mapstruct;

import com.anyilanxin.anyicloud.corecommon.base.service.mapstruct.BaseMap;
import com.anyilanxin.anyicloud.system.modules.common.entity.CommonDictEntity;
import com.anyilanxin.anyicloud.system.modules.common.service.dto.CommonDictDto;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

/**
 * 数据字典表(CommonDict)Dto与Entity相互转换
 *
 * @author zxh
 * @since 2020-11-02 09:25:21
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, builder = @Builder(disableBuilder = true))
public interface CommonDictDtoMap extends BaseMap<CommonDictDto, CommonDictEntity> {
}
