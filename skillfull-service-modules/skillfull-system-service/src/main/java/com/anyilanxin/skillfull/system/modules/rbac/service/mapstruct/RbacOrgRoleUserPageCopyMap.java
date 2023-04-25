package com.anyilanxin.skillfull.system.modules.rbac.service.mapstruct;

import com.anyilanxin.skillfull.corecommon.base.service.mapstruct.BaseThreeMap;
import com.anyilanxin.skillfull.system.modules.rbac.controller.vo.RbacOrgRoleUserPageVo;
import com.anyilanxin.skillfull.system.modules.rbac.entity.RbacOrgRoleUserEntity;
import com.anyilanxin.skillfull.system.modules.rbac.service.dto.RbacOrgRoleUserPageDto;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

/**
 * 实体相互转换
 *
 * @author zxiaozhou
 * @copyright zxiaozhou（https://skillfull.divisu.com）
 * @since 2022-07-05 00:22:57
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, builder = @Builder(disableBuilder = true))
public interface RbacOrgRoleUserPageCopyMap extends BaseThreeMap<RbacOrgRoleUserEntity, RbacOrgRoleUserPageDto, RbacOrgRoleUserPageVo> {
}
