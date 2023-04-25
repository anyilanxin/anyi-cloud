package com.anyilanxin.skillfull.system.modules.rbac.service.mapstruct;

import com.anyilanxin.skillfull.corecommon.base.service.mapstruct.BaseThreeMap;
import com.anyilanxin.skillfull.system.modules.rbac.controller.vo.RbacOrgUserVo;
import com.anyilanxin.skillfull.system.modules.rbac.entity.RbacOrgUserEntity;
import com.anyilanxin.skillfull.system.modules.rbac.service.dto.RbacOrgUserDto;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

/**
 * 实体相互转换
 *
 * @author zxiaozhou
 * @copyright zxiaozhou（https://skillfull.divisu.com）
 * @since 2022-07-02 23:01:20
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, builder = @Builder(disableBuilder = true))
public interface RbacOrgUserCopyMap extends BaseThreeMap<RbacOrgUserEntity, RbacOrgUserDto, RbacOrgUserVo> {
}
