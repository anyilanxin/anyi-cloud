package com.anyilanxin.skillfull.system.modules.rbac.service.mapstruct;


import com.anyilanxin.skillfull.corecommon.base.service.mapstruct.BaseMap;
import com.anyilanxin.skillfull.system.modules.rbac.entity.RbacRoleEntity;
import com.anyilanxin.skillfull.system.modules.rbac.service.dto.RbacRoleBasicDto;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

/**
 * 角色表(RbacRole)Dto与Entity相互转换
 *
 * @author zxiaozhou
 * @since 2020-10-08 13:44:04
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, builder = @Builder(disableBuilder = true))
public interface RbacRoleBasicDtoMap extends BaseMap<RbacRoleBasicDto, RbacRoleEntity> {
}
