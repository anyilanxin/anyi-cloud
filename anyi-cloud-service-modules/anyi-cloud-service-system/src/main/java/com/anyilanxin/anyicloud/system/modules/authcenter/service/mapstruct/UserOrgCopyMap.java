

package com.anyilanxin.anyicloud.system.modules.authcenter.service.mapstruct;

import com.anyilanxin.anyicloud.corecommon.base.service.mapstruct.BaseThreeMap;
import com.anyilanxin.anyicloud.corecommon.model.auth.UserOrgTreeInfo;
import com.anyilanxin.anyicloud.system.modules.rbac.entity.RbacOrgEntity;
import com.anyilanxin.anyicloud.system.modules.rbac.service.dto.RbacOrgUserDto;
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
public interface UserOrgCopyMap extends BaseThreeMap<UserOrgTreeInfo, RbacOrgUserDto, RbacOrgEntity> {
}
