

package com.anyilanxin.anyicloud.system.modules.rbac.service.mapstruct;

import com.anyilanxin.anyicloud.corecommon.base.service.mapstruct.BaseThreeMap;
import com.anyilanxin.anyicloud.system.modules.rbac.controller.vo.RbacRoleUserVo;
import com.anyilanxin.anyicloud.system.modules.rbac.entity.RbacRoleUserEntity;
import com.anyilanxin.anyicloud.system.modules.rbac.service.dto.RbacRoleUserDto;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

/**
 * 实体相互转换
 *
 * @author zxh
 * @copyright zhouxuanhong（https://anyilanxin.com）
 * @since 2022-07-02 23:01:21
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, builder = @Builder(disableBuilder = true))
public interface RbacRoleUserCopyMap extends BaseThreeMap<RbacRoleUserEntity, RbacRoleUserDto, RbacRoleUserVo> {
}
