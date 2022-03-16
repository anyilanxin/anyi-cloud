// +----------------------------------------------------------------------
// | SkillFull快速开发平台 [ SkillFull ]
// +----------------------------------------------------------------------
// | 版权所有 2020~2021 zxiaozhou
// +----------------------------------------------------------------------
// | 官方网站: https://www.divisu.com
// +----------------------------------------------------------------------
// | 作者: zxiaozhou <z7630853@163.com>
// +----------------------------------------------------------------------
package indi.zxiaozhou.skillfull.auth.modules.common.service.mapstruct;

import indi.zxiaozhou.skillfull.auth.modules.common.controller.vo.CommonDataMapPageVo;
import indi.zxiaozhou.skillfull.auth.modules.common.entity.CommonDataMapEntity;
import indi.zxiaozhou.skillfull.auth.modules.common.service.dto.CommonDataMapPageDto;
import indi.zxiaozhou.skillfull.corecommon.base.service.mapstruct.BaseThreeMap;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

/**
 * 实体相互转换
 *
 * @author zxiaozhou
 * @since 2021-07-28 11:53:28
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, builder = @Builder(disableBuilder = true))
public interface CommonDataMapPageCopyMap extends BaseThreeMap<CommonDataMapEntity, CommonDataMapPageDto, CommonDataMapPageVo> {
}
