// +----------------------------------------------------------------------
// | SkillFull快速开发平台 [ SkillFull ]
// +----------------------------------------------------------------------
// | 版权所有 2020~2021 zxiaozhou
// +----------------------------------------------------------------------
// | 官方网站: https://www.divisu.com
// +----------------------------------------------------------------------
// | 作者: zxiaozhou <z7630853@163.com>
// +----------------------------------------------------------------------
package indi.zxiaozhou.skillfull.corecommon.base.model.mapstruct;

import indi.zxiaozhou.skillfull.corecommon.base.model.login.LoginOnlineInfoModel;
import indi.zxiaozhou.skillfull.corecommon.base.model.login.LoginSocketInfoModel;
import indi.zxiaozhou.skillfull.corecommon.base.service.mapstruct.BaseMap;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

/**
 * 数据复转
 *
 * @author zxiaozhou
 * @since 2020-09-12 16:33:37
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, builder = @Builder(disableBuilder = true))
public interface OnlineSocketMap extends BaseMap<LoginSocketInfoModel, LoginOnlineInfoModel> {
}