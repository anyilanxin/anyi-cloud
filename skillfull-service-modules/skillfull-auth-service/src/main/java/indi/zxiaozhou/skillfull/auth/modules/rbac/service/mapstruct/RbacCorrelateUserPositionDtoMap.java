// +----------------------------------------------------------------------
// | SkillFull快速开发平台 [ SkillFull ]
// +----------------------------------------------------------------------
// | 版权所有 2020~2021 zxiaozhou
// +----------------------------------------------------------------------
// | 官方网站: https://www.divisu.com
// +----------------------------------------------------------------------
// | 作者: zxiaozhou <z7630853@163.com>
// +----------------------------------------------------------------------
package indi.zxiaozhou.skillfull.auth.modules.rbac.service.mapstruct;

import indi.zxiaozhou.skillfull.auth.modules.rbac.service.dto.RbacCorrelateUserPositionDto;
import indi.zxiaozhou.skillfull.auth.modules.rbac.service.dto.RbacUserDto;
import indi.zxiaozhou.skillfull.corecommon.base.service.mapstruct.BaseMap;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

/**
 * 用户关联关系表(RbacCorrelateUser)Dto与Entity相互转换
 *
 * @author zxiaozhou
 * @since 2020-11-02 09:37:36
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, builder = @Builder(disableBuilder = true))
public interface RbacCorrelateUserPositionDtoMap extends BaseMap<RbacUserDto.PositionInfoDto, RbacCorrelateUserPositionDto> {
}