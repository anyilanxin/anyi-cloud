package com.anyilanxin.skillfull.system.modules.rbac.service.mapstruct;

import com.anyilanxin.skillfull.corecommon.base.service.mapstruct.BaseThreeMap;
import com.anyilanxin.skillfull.system.modules.rbac.controller.vo.RbacOrgPageVo;
import com.anyilanxin.skillfull.system.modules.rbac.entity.RbacOrgEntity;
import com.anyilanxin.skillfull.system.modules.rbac.service.dto.RbacOrgTreePageDto;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

/**
 * 实体相互转换
 *
 * @author zxiaozhou
 * @copyright zxiaozhou（https://skillfull.divisu.com）
 * @since 2022-05-02 16:39:45
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, builder = @Builder(disableBuilder = true))
public interface RbacOrgPageCopyMap extends BaseThreeMap<RbacOrgEntity, RbacOrgTreePageDto, RbacOrgPageVo> {
}
