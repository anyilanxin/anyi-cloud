

package com.anyilanxin.anyicloud.system.modules.rbac.service.mapstruct;

import com.anyilanxin.anyicloud.corecommon.base.service.mapstruct.BaseMap;
import com.anyilanxin.anyicloud.system.modules.rbac.entity.RbacMenuEntity;
import com.anyilanxin.anyicloud.system.modules.rbac.service.dto.RbacRoleMenuDto;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

/**
 * 角色-权限表(RbacRolePermission)Dto与Entity相互转换
 *
 * @author zxh
 * @since 2020-10-08 13:29:16
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, builder = @Builder(disableBuilder = true))
public interface RbacRoleAndMenuDtoMap extends BaseMap<RbacRoleMenuDto, RbacMenuEntity> {
}
