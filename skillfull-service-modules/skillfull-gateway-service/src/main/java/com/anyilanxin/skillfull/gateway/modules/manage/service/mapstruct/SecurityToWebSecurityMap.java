// +----------------------------------------------------------------------
// | SkillFull快速开发平台 [ SkillFull ]
// +----------------------------------------------------------------------
// | 版权所有 2020~2021 zxiaozhou
// +----------------------------------------------------------------------
// | 官方网站: https://www.divisu.com
// +----------------------------------------------------------------------
// | 作者: zxiaozhou <z7630853@163.com>
// +----------------------------------------------------------------------
package com.anyilanxin.skillfull.gateway.modules.manage.service.mapstruct;

import com.anyilanxin.skillfull.corecommon.base.model.system.ConfigDataSecurityModel;
import com.anyilanxin.skillfull.corecommon.base.model.system.UserDataSecurityModel;
import com.anyilanxin.skillfull.corecommon.base.model.web.WebSecurityModel;
import com.anyilanxin.skillfull.corecommon.base.service.mapstruct.BaseThreeMap;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

/**
 * 系统加解密信息与web端互转
 *
 * @author zxiaozhou
 * @since 2020-09-12 16:33:37
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, builder = @Builder(disableBuilder = true))
public interface SecurityToWebSecurityMap extends BaseThreeMap<UserDataSecurityModel, WebSecurityModel, ConfigDataSecurityModel> {
}