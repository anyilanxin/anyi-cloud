

package com.anyilanxin.anyicloud.system.modules.rbac.service.mapstruct;

import com.anyilanxin.anyicloud.corecommon.base.service.mapstruct.BaseThreeMap;
import com.anyilanxin.anyicloud.system.modules.rbac.controller.vo.RbacRolePageVo;
import com.anyilanxin.anyicloud.system.modules.rbac.entity.RbacRoleEntity;
import com.anyilanxin.anyicloud.system.modules.rbac.service.dto.RbacRolePageDto;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

/**
 * 实体相互转换
 *
 * @author zxh
 * @copyright zhouxuanhong（https://anyilanxin.com）
 * @since 2022-05-02 19:29:58
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, builder = @Builder(disableBuilder = true))
public interface RbacRolePageCopyMap extends BaseThreeMap<RbacRoleEntity, RbacRolePageDto, RbacRolePageVo> {
}
