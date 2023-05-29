

package com.anyilanxin.anyicloud.auth.modules.login.service.mapstruct;

import com.anyilanxin.anyicloud.auth.modules.login.service.dto.RbacUserDto;
import com.anyilanxin.anyicloud.corecommon.base.service.mapstruct.BaseMap;
import com.anyilanxin.anyicloud.corecommon.model.system.UserAndResourceAuthModel;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

/**
 * 权限表(Permission)Vo与Entity相互转换
 *
 * @author zxh
 * @since 2020-09-26 17:16:16
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, builder = @Builder(disableBuilder = true))
public interface UserAuthCopyMap extends BaseMap<UserAndResourceAuthModel, RbacUserDto> {
}
