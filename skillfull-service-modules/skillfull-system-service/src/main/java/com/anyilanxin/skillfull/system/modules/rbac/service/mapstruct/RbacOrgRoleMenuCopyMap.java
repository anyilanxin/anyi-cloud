// +----------------------------------------------------------------------
// | SkillFull快速开发平台 [ SkillFull ]
// +----------------------------------------------------------------------
// | 版权所有 2020~2021 zxiaozhou
// +----------------------------------------------------------------------
// | 官方网站: https://skillfull.divisu.com
// +----------------------------------------------------------------------
// | 作者: zxiaozhou <z7630853@163.com>
// +----------------------------------------------------------------------
package com.anyilanxin.skillfull.system.modules.rbac.service.mapstruct;

import com.anyilanxin.skillfull.corecommon.base.service.mapstruct.BaseThreeMap;
import com.anyilanxin.skillfull.system.modules.rbac.controller.vo.RbacOrgRoleMenuVo;
import com.anyilanxin.skillfull.system.modules.rbac.entity.RbacOrgRoleMenuEntity;
import com.anyilanxin.skillfull.system.modules.rbac.service.dto.RbacOrgRoleMenuDto;
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
public interface RbacOrgRoleMenuCopyMap extends BaseThreeMap<RbacOrgRoleMenuEntity, RbacOrgRoleMenuDto, RbacOrgRoleMenuVo> {
}
