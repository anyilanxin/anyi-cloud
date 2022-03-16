// +----------------------------------------------------------------------
// | SkillFull快速开发平台 [ SkillFull ]
// +----------------------------------------------------------------------
// | 版权所有 2020~2021 zxiaozhou
// +----------------------------------------------------------------------
// | 官方网站: https://www.divisu.com
// +----------------------------------------------------------------------
// | 作者: zxiaozhou <z7630853@163.com>
// +----------------------------------------------------------------------
package indi.zxiaozhou.skillfull.logging.modules.manage.service.mapstruct;

import indi.zxiaozhou.skillfull.corecommon.base.model.stream.OperateLogModel;
import indi.zxiaozhou.skillfull.corecommon.base.service.mapstruct.BaseThreeMap;
import indi.zxiaozhou.skillfull.logging.modules.manage.entity.OperateEntity;
import indi.zxiaozhou.skillfull.logging.modules.manage.service.dto.OperateDto;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

/**
 * 实体相互转换
 *
 * @author zxiaozhou
 * @copyright zxiaozhou（https://divisu.com）
 * @since 2022-01-26 19:51:07
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, builder = @Builder(disableBuilder = true))
public interface OperateCopyMap extends BaseThreeMap<OperateEntity, OperateDto, OperateLogModel> {
}
