

package com.anyilanxin.anyicloud.system.modules.manage.service.mapstruct;

import com.anyilanxin.anyicloud.corecommon.base.service.mapstruct.BaseThreeMap;
import com.anyilanxin.anyicloud.system.modules.manage.controller.vo.ManageRouteFilterPageVo;
import com.anyilanxin.anyicloud.system.modules.manage.entity.ManageRouteFilterEntity;
import com.anyilanxin.anyicloud.system.modules.manage.service.dto.ManageRouteFilterPageDto;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

/**
 * 实体相互转换
 *
 * @author zxh
 * @copyright zhouxuanhong（https://anyilanxin.com）
 * @since 2021-12-19 10:37:42
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, builder = @Builder(disableBuilder = true))
public interface ManageRouteFilterPageCopyMap extends BaseThreeMap<ManageRouteFilterEntity, ManageRouteFilterPageDto, ManageRouteFilterPageVo> {
}
