// +----------------------------------------------------------------------
// | SkillFull快速开发平台 [ SkillFull ]
// +----------------------------------------------------------------------
// | 版权所有 2020~2021 zxiaozhou
// +----------------------------------------------------------------------
// | 官方网站: https://www.divisu.com
// +----------------------------------------------------------------------
// | 作者: zxiaozhou <z7630853@163.com>
// +----------------------------------------------------------------------
package com.anyilanxin.skillfull.oauth2common.mapstruct;


import com.anyilanxin.skillfull.corecommon.auth.model.UserInfo;
import com.anyilanxin.skillfull.corecommon.base.service.mapstruct.BaseMap;
import com.anyilanxin.skillfull.oauth2common.authinfo.SkillFullUserDetails;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

/**
 * 权限表(Permission)Vo与Entity相互转换
 *
 * @author zxiaozhou
 * @since 2020-09-26 17:16:16
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, builder = @Builder(disableBuilder = true))
public interface OauthUserAndUserDetailsCopyMap extends BaseMap<SkillFullUserDetails, UserInfo> {
}