

package com.anyilanxin.anyicloud.system.modules.rbac.service.mapstruct;

import com.anyilanxin.anyicloud.corecommon.base.service.mapstruct.BaseThreeMap;
import com.anyilanxin.anyicloud.system.modules.rbac.controller.vo.RbacRoleMenuVo;
import com.anyilanxin.anyicloud.system.modules.rbac.entity.RbacRoleMenuEntity;
import com.anyilanxin.anyicloud.system.modules.rbac.service.dto.RbacRoleMenuDto;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

/**
 * 实体相互转换
 *
 * @author zxh
 * @copyright zhouxuanhong（https://anyilanxin.com）
 * @since 2022-05-02 16:12:21
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, builder = @Builder(disableBuilder = true))
public interface RbacRoleMenuCopyMap extends BaseThreeMap<RbacRoleMenuEntity, RbacRoleMenuDto, RbacRoleMenuVo> {
}
