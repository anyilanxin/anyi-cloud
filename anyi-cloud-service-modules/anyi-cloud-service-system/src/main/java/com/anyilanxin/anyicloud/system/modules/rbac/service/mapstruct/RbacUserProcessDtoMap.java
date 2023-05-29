

package com.anyilanxin.anyicloud.system.modules.rbac.service.mapstruct;

import com.anyilanxin.anyicloud.corecommon.base.service.mapstruct.BaseMap;
import com.anyilanxin.anyicloud.processrpc.model.UserDetailRequestModel;
import com.anyilanxin.anyicloud.system.modules.rbac.entity.RbacUserEntity;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

/**
 * 用户表(User)Dto与Entity相互转换
 *
 * @author zxh
 * @since 2020-09-26 17:16:18
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, builder = @Builder(disableBuilder = true))
public interface RbacUserProcessDtoMap extends BaseMap<UserDetailRequestModel, RbacUserEntity> {
}
