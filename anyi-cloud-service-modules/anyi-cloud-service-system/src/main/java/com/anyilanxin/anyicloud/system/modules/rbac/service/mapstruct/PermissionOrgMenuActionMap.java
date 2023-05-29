

package com.anyilanxin.anyicloud.system.modules.rbac.service.mapstruct;

import com.anyilanxin.anyicloud.corecommon.base.service.mapstruct.BaseThreeMap;
import com.anyilanxin.anyicloud.system.modules.rbac.service.dto.RbacOrgRoleMenuButtonDto;
import com.anyilanxin.anyicloud.system.modules.rbac.service.dto.RbacOrgRoleMenuDto;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

/**
 * 权限表(Permission)Vo与Entity相互转换
 *
 * @author zxh
 * @since 2020-09-26 17:16:16
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, builder = @Builder(disableBuilder = true))
public interface PermissionOrgMenuActionMap extends BaseThreeMap<RbacOrgRoleMenuButtonDto.Action, RbacOrgRoleMenuButtonDto, RbacOrgRoleMenuDto> {
}
