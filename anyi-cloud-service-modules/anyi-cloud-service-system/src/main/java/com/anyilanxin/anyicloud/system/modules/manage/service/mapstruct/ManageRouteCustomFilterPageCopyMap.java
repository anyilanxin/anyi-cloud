

package com.anyilanxin.anyicloud.system.modules.manage.service.mapstruct;

import com.anyilanxin.anyicloud.corecommon.base.service.mapstruct.BaseThreeMap;
import com.anyilanxin.anyicloud.system.modules.manage.controller.vo.ManageRouteCustomFilterPageVo;
import com.anyilanxin.anyicloud.system.modules.manage.entity.ManageRouteCustomFilterEntity;
import com.anyilanxin.anyicloud.system.modules.manage.service.dto.ManageRouteCustomFilterPageDto;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

/**
 * 实体相互转换
 *
 * @author zxh
 * @copyright zhouxuanhong（https://anyilanxin.com）
 * @since 2021-12-19 00:22:17
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, builder = @Builder(disableBuilder = true))
public interface ManageRouteCustomFilterPageCopyMap extends BaseThreeMap<ManageRouteCustomFilterEntity, ManageRouteCustomFilterPageDto, ManageRouteCustomFilterPageVo> {
}
