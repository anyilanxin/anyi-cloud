

package com.anyilanxin.anyicloud.system.modules.rbac.service.mapstruct;

import com.anyilanxin.anyicloud.corecommon.base.service.mapstruct.BaseThreeMap;
import com.anyilanxin.anyicloud.system.modules.rbac.controller.vo.RbacMenuPageVo;
import com.anyilanxin.anyicloud.system.modules.rbac.entity.RbacMenuEntity;
import com.anyilanxin.anyicloud.system.modules.rbac.service.dto.RbacMenuPageDto;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

/**
 * 实体相互转换
 *
 * @author zxh
 * @copyright zhouxuanhong（https://anyilanxin.com）
 * @since 2022-05-03 00:29:06
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, builder = @Builder(disableBuilder = true))
public interface RbacMenuPageCopyMap extends BaseThreeMap<RbacMenuEntity, RbacMenuPageDto, RbacMenuPageVo> {
}
