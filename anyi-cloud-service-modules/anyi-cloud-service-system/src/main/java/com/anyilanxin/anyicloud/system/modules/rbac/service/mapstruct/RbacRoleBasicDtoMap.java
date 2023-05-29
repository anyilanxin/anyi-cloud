

package com.anyilanxin.anyicloud.system.modules.rbac.service.mapstruct;

import com.anyilanxin.anyicloud.corecommon.base.service.mapstruct.BaseMap;
import com.anyilanxin.anyicloud.system.modules.rbac.entity.RbacRoleEntity;
import com.anyilanxin.anyicloud.system.modules.rbac.service.dto.RbacRoleBasicDto;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

/**
 * 角色表(RbacRole)Dto与Entity相互转换
 *
 * @author zxh
 * @since 2020-10-08 13:44:04
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, builder = @Builder(disableBuilder = true))
public interface RbacRoleBasicDtoMap extends BaseMap<RbacRoleBasicDto, RbacRoleEntity> {
}
