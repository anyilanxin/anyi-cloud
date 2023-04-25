package com.anyilanxin.skillfull.system.modules.rbac.service.mapstruct;

import com.anyilanxin.skillfull.corecommon.base.service.mapstruct.BaseThreeMap;
import com.anyilanxin.skillfull.system.modules.rbac.controller.vo.RbacRoleClientPageVo;
import com.anyilanxin.skillfull.system.modules.rbac.entity.RbacRoleClientEntity;
import com.anyilanxin.skillfull.system.modules.rbac.service.dto.RbacRoleClientPageDto;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

/**
 * 实体相互转换
 *
 * @author zxiaozhou
 * @copyright zxiaozhou（https://skillfull.divisu.com）
 * @since 2022-05-02 16:12:20
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, builder = @Builder(disableBuilder = true))
public interface RbacRoleClientPageCopyMap extends BaseThreeMap<RbacRoleClientEntity, RbacRoleClientPageDto, RbacRoleClientPageVo> {
}
