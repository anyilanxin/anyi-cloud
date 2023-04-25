package com.anyilanxin.skillfull.system.modules.authcenter.service.mapstruct;


import com.anyilanxin.skillfull.corecommon.base.service.mapstruct.BaseThreeMap;
import com.anyilanxin.skillfull.corecommon.model.auth.UserRouteMetaModel;
import com.anyilanxin.skillfull.corecommon.model.auth.UserRouteModel;
import com.anyilanxin.skillfull.system.modules.rbac.service.dto.RbacMenuDto;
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
public interface UserMenuCopyMap extends BaseThreeMap<UserRouteModel, RbacMenuDto, UserRouteMetaModel> {
}
