

package com.anyilanxin.anyicloud.system.modules.rbac.service.mapstruct;

import com.anyilanxin.anyicloud.corecommon.base.service.mapstruct.BaseThreeMap;
import com.anyilanxin.anyicloud.system.modules.rbac.controller.vo.RbacOrgRoleMenuPageVo;
import com.anyilanxin.anyicloud.system.modules.rbac.entity.RbacOrgRoleMenuEntity;
import com.anyilanxin.anyicloud.system.modules.rbac.service.dto.RbacOrgRoleMenuPageDto;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

/**
 * 实体相互转换
 *
 * @author zxh
 * @copyright zhouxuanhong（https://anyilanxin.com）
 * @since 2022-07-05 00:22:57
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, builder = @Builder(disableBuilder = true))
public interface RbacOrgRoleMenuPageCopyMap extends BaseThreeMap<RbacOrgRoleMenuEntity, RbacOrgRoleMenuPageDto, RbacOrgRoleMenuPageVo> {
}
